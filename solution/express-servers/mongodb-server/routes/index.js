var express = require('express');
var router = express.Router();

const reviewController = require('../controllers/reviews_controller');
const releasesController = require('../controllers/releases_controller');

router.get('/api/reviews/getReviews', async (req, res, next) => {
    const movieTitle = req.query.movie;

    try {
        const reviews = await reviewController.getReviewsByMovieTitle(movieTitle);
        res.status(200).send(reviews);
    } catch (err) {
        res.status(500).json({error: err});
    }
});

router.get('/api/reviews/bestMoviesEver', reviewController.getBestMoviesEver);
router.get('/api/releases/getLatestMovies', releasesController.getLatestMovies);


module.exports = router;
