const express = require('express');
const router = express.Router();
const axios = require('axios');
const { getQueryParams, handleErrors, getPaginationRange, checkAllParams } = require('../utils/helpers');
const {springBootServerUrl, yearOptions, sortOptions, languagesOptions,genreOptions} = require("../utils/movieFilters");

router.get('/search', async (req, res) => {
    const queriedMovie = req.query.movie;
    const { page, sort, filter } = getQueryParams(req);

    if (!queriedMovie) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING SEARCH PAGE BECAUSE EITHER THE MOVIE OR PAGE QUERIED ARE NULL');
    }

    try {
        checkAllParams(req);
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR CHECKING PARAMETERS IN /SEARCH: '
            + error.message);
        return handleErrors(error, res);
    }

    const url = springBootServerUrl+'/api/movie/getSearchedMovies?movie='+queriedMovie+'&page='+page+'&sort='+sort+'&filter=' + filter;

    try {
        const response = await axios.get(url);
        const movieCardsData = response.data;

        const {number: currentPage, totalPages} = movieCardsData;
        const pagination = getPaginationRange(currentPage, totalPages);

        res.render('pages/movies-page', {
            query: queriedMovie,
            movies: movieCardsData.content,
            pageData: movieCardsData,
            pageTitle: `Results found for '${queriedMovie}'`  + ( (filter && filter !== 'null') ? ' in ' + filter.split(',')[1] : ''),
            paginationBaseUrl: '/search?movie',
            sortMethod: sort,
            filterMethod: filter,
            pagination,
            yearOptions,
            sortOptions,
            genreOptions,
            languagesOptions
        });
    } catch (error) {
        console.error('\x1b[31m%s\x1b[0m', 'ERROR FETCHING MOVIES IN ROUTE /SEARCH: '
            + error.message + ':', error.response.data);
        handleErrors(error, res);
    }
});

module.exports = router;
