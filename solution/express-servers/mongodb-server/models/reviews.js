const mongoose = require('mongoose');

const reviewSchema = new mongoose.Schema({
    rotten_tomatoes_link: String,
    movie_title: String,
    critic_name: String,
    top_critic: Boolean,
    publisher_name: String,
    review_type: String,
    review_date: String,
    review_content: String,
    normalized_review_score: Number
});

reviewModel = mongoose.model('reviews', reviewSchema);
module.exports = reviewModel

