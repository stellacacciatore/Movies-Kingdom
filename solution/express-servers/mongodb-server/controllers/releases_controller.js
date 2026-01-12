const releases = require('../models/releases');

/**
 * Retrieves the latest 20 theatrical movie releases before January 1, 2024.
 * Responds with a JSON array of movie IDs, sorted by release date descending.
 *
 * @async
 * @function getLatestMovies
 * @param {import('express').Request} req - Express request object.
 * @param {import('express').Response} res - Express response object.
 * @returns {Promise<void>} Responds with a JSON array of movie IDs or an error message.
 */
exports.getLatestMovies = async (req, res) => {
    try {
        const latest = await releases.find({
            date: { $lt: new Date('2024-01-01T00:00:00Z') }
        })
            .sort({ date: -1 })
            .limit(20)
            .select('movie_id')
            .where({ type: 'Theatrical' })
            .lean();

        const movieIds = latest.map(release => release.movie_id);
        res.json(movieIds);
    } catch (err) {
        res.status(500).json({ error: err.message });
    }
};

