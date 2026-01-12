const reviews = require('../models/reviews');

/**
 * Retrieves up to 10 reviews for a given movie title, sorted by review date (descending).
 * The search is case-insensitive and only reviews with non-null content are returned.
 *
 * @param {string} title - The movie title to search for.
 * @returns {Promise<Array<Object>>} Promise resolving to an array of review objects.
 */
function getReviewsByMovieTitle(title) {
    return new Promise((resolve, reject) => {
        reviews.find({
            movie_title: new RegExp(`^${title}$`, 'i'),
            normalized_review_score: {$ne: null}
        })
            .sort({review_date: -1})
            .limit(20)
            .where('review_content').ne(null)
            .then(review => {
                resolve(review);
            })
            .catch((err) => {
                reject(err);
            })
    })
}

exports.getReviewsByMovieTitle = getReviewsByMovieTitle;

/**
 * Returns the top 50 best movies ever based on a weighted average of normalized review scores.
 * The weighted average is calculated using the number of reviews per movie and the global average score.
 * Responds with a JSON array of movie titles.
 *
 * @async
 * @function getBestMoviesEver
 * @param {import('express').Request} req - Express request object.
 * @param {import('express').Response} res - Express response object.
 * @returns {Promise<void>} Responds with a JSON array of the top 50 movie titles or an error message.
 */
exports.getBestMoviesEver = async (req, res) => {
    try {
        const [aggregated] = await reviews.aggregate([
            {
                $match: {
                    normalized_review_score: {$ne: '', $type: 'number'}
                }
            },
            {
                $facet: { // allows me to run more pipeline at the same time
                    movieStats: [
                        {
                            $group: {
                                _id: "$movie_title",
                                avgScore: {$avg: "$normalized_review_score"},
                                count: {$sum: 1}
                            }
                        }
                    ],
                    globalStats: [
                        {
                            $group: {
                                _id: null,
                                globalAvg: {$avg: "$normalized_review_score"}
                            }
                        }
                    ]
                }
            }
        ]);

        const movieStats = aggregated.movieStats;
        const globalAvg = aggregated.globalStats[0]?.globalAvg || 0;
        const m = movieStats.reduce((acc, m) => acc + m.count, 0) / (movieStats.length || 1);

        const weightedMovies = movieStats.map(mov => {
            const v = mov.count;
            const R = mov.avgScore;
            const weightedScore = (v / (v + m)) * R + (m / (v + m)) * globalAvg;
            return {movie_title: mov._id, weightedScore};
        });

        weightedMovies.sort((a, b) => b.weightedScore - a.weightedScore);
        const movieTitles = weightedMovies.slice(0, 50).map(m => m.movie_title);
        res.json(movieTitles);
    } catch (err) {
        res.status(500).json({error: err.message});
    }
}
