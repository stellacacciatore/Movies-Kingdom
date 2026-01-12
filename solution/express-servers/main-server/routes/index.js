const express = require('express');
const router = express.Router();
const axios = require('axios');
const { handleErrors} = require('../utils/helpers');
const { springBootServerUrl, mongoDBServerUrl} = require("../utils/movieFilters");

router.get('/', async (req, res) => {
    try {
        const [homepageResponse, latestReleasesResponse] = await Promise.all([
            axios.get(springBootServerUrl + '/api/movie/getHomepageMovies'),
            axios.get(mongoDBServerUrl + '/api/releases/getLatestMovies')
        ]);

        const latestMoviesResponse = await axios.post(
            springBootServerUrl + '/api/movie/getLatestReleasesMovies',
            latestReleasesResponse.data
        );

        const carouselMoviesData = homepageResponse.data.carouselMovies;
        const mostAwardedMoviesData = homepageResponse.data.mostAwardedMovies;
        const topRatedMoviesData = homepageResponse.data.topRatedMovies;
        const latestMoviesData = latestMoviesResponse.data;

        res.render('pages/homepage', {
            movies: carouselMoviesData,
            mostAwarded: mostAwardedMoviesData,
            latestMovies: latestMoviesData,
            topRatedMovies: topRatedMoviesData,
        });

    } catch (error) {
        handleErrors(error, res);
    }
});

router.get('/theme-finder', async (req, res) => {
    let themes = req.query.themes;

    if (!themes) {
        console.error('\x1b[31m%s\x1b[0m', "ERROR: themes not defined");
    }

    if (!Array.isArray(themes)) {
        themes = [themes];
    }

    try {
        const response = await axios.post(springBootServerUrl + '/api/themes/getThemes', themes)
        const movieCardsData = response.data.moviesMatchedWithTheme;

        res.render('pages/movies-page', {
            movies: movieCardsData,
            pageTitle: 'Movies for themes "' + themes.toString() + '"',
        })

    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING THEMES REQUESTED BY USER: '
            + error.message + ': ', error.response.data);
        handleErrors(error, res);
    }
})

module.exports = router;