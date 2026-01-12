const express = require('express');
const router = express.Router();
const axios = require("axios");
const { getQueryParams, handleErrors, getPaginationRange} = require('../utils/helpers');
const {
    springBootServerUrl,
    mongoDBServerUrl,
    yearOptions,
    sortOptions,
    languagesOptions,
    genreOptions
} = require("../utils/movieFilters");

router.get('/single-movie-page', async (req, res) => {
    const movieId = req.query.movieId;
    let movieData;

    if (!movieId) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIE PAGE BECAUSE THE ID QUERIED IS NULL');
    }

    const springBootMovieUrl = springBootServerUrl + '/api/movie/getSingleMovie?id=' + movieId;

    try {
        movieData = await axios.get(springBootMovieUrl);

        const encodedTitle = encodeURIComponent(movieData.data.movie_title);
        const mongoDBMovieUrl = mongoDBServerUrl + '/api/reviews/getReviews?movie=' + encodedTitle;
        const reviewData = await axios.get(mongoDBMovieUrl);

        res.render('pages/single-movie-page', {
            movie: movieData.data,
            reviews: reviewData.data
        });
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIES IN /SINGLE-MOVIE-PAGE: '
            + error.message + ':', error.response.data);
        handleErrors(error, res);
    }
});

router.get('/themes-page', async (req, res) => {
    const theme = req.query.theme;
    const { page, sort, filter } = getQueryParams(req);

    try {
        const response = await axios.get(springBootServerUrl + '/api/themes/getPreciseTheme?theme=' + theme + '&page=' + page + '&sort=' + sort + '&filter=' + filter)
        const themeData = response.data;

        const {number: currentPage, totalPages} = themeData;
        const pagination = getPaginationRange(currentPage, totalPages);

        res.render('pages/movies-page', {
            query: theme,
            movies: themeData.content,
            pageData: themeData,
            pageTitle: 'Movies with Theme: ' + theme,
            paginationBaseUrl: '/themes-page?theme',
            sortMethod: sort,
            filterMethod: filter,
            pagination,
            yearOptions,
            sortOptions,
            genreOptions,
            languagesOptions
        })
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIES IN /THEMES-PAGE: '
            + error.message + ': ', error.response.data);
        handleErrors(error, res);
    }
})

router.get('/director-page', async (req, res) => {
    const director = req.query.director;
    const { page, sort, filter } = getQueryParams(req);

    try {
        const response = await axios.get(springBootServerUrl + '/api/crew/getDirector?director=' + director + '&page=' + page + '&sort=' + sort + '&filter=' + filter)
        const directorData = response.data;

        const {number: currentPage, totalPages} = directorData;
        const pagination = getPaginationRange(currentPage, totalPages);

        res.render('pages/movies-page', {
            query: director,
            movies: directorData.content,
            pageData: directorData,
            pageTitle: 'Movies from Director: ' + director,
            paginationBaseUrl: '/director-page?director',
            sortMethod: sort,
            filterMethod: filter,
            pagination,
            yearOptions,
            sortOptions,
            genreOptions,
            languagesOptions
        })
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIES IN /DIRECTOR-PAGE: '
            + error.message + ': ', error.response.data);
        handleErrors(error, res);
    }
})

router.get('/actor-page', async (req, res) => {
    const actor = req.query.actor;
    const { page, sort, filter } = getQueryParams(req);

    try {
        const response = await axios.get(springBootServerUrl + '/api/actors/getActorsByName?actor=' + actor + '&page=' + page + '&sort=' + sort + '&filter=' + filter)

        const moviesFromActor = response.data;

        const {number: currentPage, totalPages} = moviesFromActor;
        const pagination = getPaginationRange(currentPage, totalPages);

        res.render('pages/movies-page', {
            query: actor,
            movies: moviesFromActor.content,
            pageData: moviesFromActor,
            pageTitle: 'Movies from actor ' + actor,
            paginationBaseUrl: '/actor-page?actor',
            sortMethod: sort,
            filterMethod: filter,
            pagination,
            sortOptions,
            yearOptions,
            genreOptions,
            languagesOptions
        })
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIES IN /ACTOR-PAGE: '
            + error.message + ': ', error.response.data);
        handleErrors(error, res);
    }
})

module.exports = router;