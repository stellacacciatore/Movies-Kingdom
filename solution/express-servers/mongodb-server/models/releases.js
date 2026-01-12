const mongoose = require('mongoose');

const releaseSchema = new mongoose.Schema({
    movie_id: Number,
    country: String,
    date: Date,
    type: String
});

const releaseModel = mongoose.model('releases', releaseSchema);
module.exports = releaseModel;

