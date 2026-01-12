// routes/navbar.js
const express = require('express');
const router = express.Router();
const axios = require('axios');
const { getQueryParams, handleErrors, getPaginationRange, checkAllParams } = require('../utils/helpers');
const {
    springBootServerUrl,
    mongoDBServerUrl,
    yearOptions,
    sortOptions,
    languagesOptions,
    genreOptions,
    studioOptions,
    checkParam
} = require('../utils/movieFilters');

// Best of All Time
router.get('/bestOfAllTime', async (req, res) => {
    const { page, sort, filter } = getQueryParams(req);

    try {
        checkAllParams(req);
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR CHECKING PARAMETERS IN /GENRES: '
            + error.message);
        return handleErrors(error, res);
    }

    try {
        const bestTitlesUrl = mongoDBServerUrl + '/api/reviews/bestMoviesEver';
        const { data: bestMovieTitles } = await axios.get(bestTitlesUrl);

        const bestMoviesUrl =
            springBootServerUrl +
            '/api/movie/getBestMoviesOfAllTime?page=' + page +
            '&sort=' + sort +
            '&filter=' + filter;
        const { data: movieData } = await axios.post(bestMoviesUrl, bestMovieTitles);

         if(movieData.length > 0){
            console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING BEST MOVIES OF ALL TIME:' + ' length of best of all time' +
                'movies array is null.');
        }

        const { number: currentPage, totalPages } = movieData;
        const pagination = getPaginationRange(currentPage, totalPages);

        res.render('pages/movies-page', {
            movies: movieData.content,
            pageData: movieData,
            pageTitle: 'Best of All Time Movies' + ( (filter && filter !== 'null') ? ' in ' + filter.split(',')[1] : ''),
            paginationBaseUrl: '/bestOfAllTime?',
            sortMethod: sort,
            filterMethod: filter,
            pagination,
            yearOptions,
            languagesOptions,
            genreOptions,
            sortOptions
        });
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING BEST MOVIES OF ALL TIME: '
            + error.message + ':', error.response.data);
        handleErrors(error, res);
    }
});

// TV Series
router.get('/tvseries', async (req, res) => {
    const { page, sort, filter } = getQueryParams(req);

    if(isNaN(page)) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING STUDIOS BECAUSE THE PAGE QUERIED IS NOT A NUMBER');
    }

    try {
        checkAllParams(req);
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR CHECKING PARAMETERS IN /GENRES: '
            + error.message);
        return handleErrors(error, res);
    }

    try {
        const tvUrl =
            springBootServerUrl +
            '/api/movie/getAllTvSeries?page=' + page +
            '&sort=' + sort +
            '&filter=' + filter;
        const { data } = await axios.get(tvUrl);

        const { number: currentPage, totalPages } = data;
        const pagination = getPaginationRange(currentPage, totalPages);

        res.render('pages/movies-page', {
            movies: data.content,
            pageData: data,
            pageTitle: 'TV Series' + ( (filter && filter !== 'null') ? ' in ' + filter.split(',')[1] : ''),
            paginationBaseUrl: '/tvseries?',
            sortMethod: sort,
            filterMethod: filter,
            pagination,
            yearOptions,
            sortOptions,
            genreOptions,
            languagesOptions
        });
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIES IN /TVSERIES:n'
            + error.message + ':', error.response.data);
        handleErrors(error, res);
    }
});

// Studios
router.get('/studios', async (req, res) => {
    const queriedStudio = req.query.type;
    const { page, sort, filter } = getQueryParams(req);

    if (!queriedStudio) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR: STUDIO PARAMETER IS MISSING');
        return handleErrors({ message: 'Studio parameter is required' }, res);
    }

    try {
        checkAllParams(req);
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR CHECKING PARAMETERS IN /GENRES: '
            + error.message);
        return handleErrors(error, res);
    }

    try {
        const studioUrl =
            springBootServerUrl +
            '/api/studios/getMoviesFromStudio?studio=' + queriedStudio +
            '&page=' + page +
            '&sort=' + sort +
            '&filter=' + filter;
        const { data } = await axios.get(studioUrl);

        const { number: currentPage, totalPages } = data;
        const pagination = getPaginationRange(currentPage, totalPages);

        res.render('pages/movies-page', {
            query: queriedStudio,
            movies: data.content,
            pageData: data,
            pageTitle: 'Movies from ' + queriedStudio + ' studios' + ( (filter && filter !== 'null') ? ' in ' + filter.split(',')[1] : ''),
            paginationBaseUrl: '/studios?type',
            sortMethod: sort,
            filterMethod: filter.split(',')[0],
            pagination,
            yearOptions,
            genreOptions,
            sortOptions,
            languagesOptions
        });
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIES IN /STUDIOS: '
            + error.message + ':', error.response.data);
        handleErrors(error, res);
    }
});

// Genres
router.get('/genres', async (req, res) => {
    const queriedGenre = req.query.type;
    const { page, sort, filter } = getQueryParams(req);

    if (!queriedGenre) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR: GENRE PARAMETER IS MISSING');
        return handleErrors({ message: 'Genre parameter is required' }, res);
    }
    try {
        checkAllParams(req);
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR CHECKING PARAMETERS IN /GENRES: '
            + error.message);
        return handleErrors(error, res);
    }

    try {
        const genreUrl =
            springBootServerUrl +
            '/api/genre/getMoviesByGenre?genre=' + queriedGenre +
            '&page=' + page +
            '&sort=' + sort +
            '&filter=' + filter;
        const { data } = await axios.get(genreUrl);

        const { number: currentPage, totalPages } = data;
        const pagination = getPaginationRange(currentPage, totalPages);

        res.render('pages/movies-page', {
            query: queriedGenre,
            movies: data.content,
            pageData: data,
            pageTitle: 'Movies with ' + queriedGenre + ' genre' + ( (filter && filter !== 'null') ? ' in ' + filter.split(',')[1] : ''),
            paginationBaseUrl: '/genres?type',
            sortMethod: sort,
            filterMethod: filter,
            pagination,
            yearOptions,
            sortOptions,
            languagesOptions,
            isGenrePage: true
        });
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIES IN /GENRES: '
            + error.message + ':', error.response.data);
        handleErrors(error, res);
    }
});

module.exports = router;