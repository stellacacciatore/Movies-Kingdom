const {checkParam} = require("./movieFilters");

function getPaginationRange(currentPage, totalPages) {
    const delta = 2;
    const start = Math.max(0, currentPage - delta);
    const end = Math.min(totalPages - 1, currentPage + delta);

    const pages = [];
    for (let i = start; i <= end; i++) {
        pages.push(i);
    }

    return {
        pages,
        showStartEllipsis: start > 0,
        showEndEllipsis: end < totalPages - 1,
        firstPage: 0,
        lastPage: totalPages - 1
    };
}


function handleErrors(err, res) {
    let error_type = "Generic Error";
    let error_message = "An error occurred. Please try again later.";

    if (err.status) {
        switch (err.status) {
            case 401:
                error_type = "Bad Request";
                error_message = err.message;
                break;
            case 400:
                error_type = "Client Side Error";
                error_message = "Bad Request. Please check your input.";
                break;
            case 404:
                error_type = "Generic Error";
                error_message = "Resource not found. Please check the URL.";
                break;
            case 500:
                error_type = "Server Side Error";
                error_message = "Internal Server Error. Please try again later.";
                break;
            default:
                error_type = "Unknown Error";
        }
    } else if (err.code) {
        switch (err.code) {
            case 'ECONNREFUSED':
                error_message = 'Connection refused. Please check if the server is running.';
                break;
            case 'ETIMEDOUT':
                error_message = 'Request timed out. Please try again later.';
                break;
            case 'ECONNRESET':
                error_message = 'Connection reset by peer. Please try again later.';
                break;
            default:
                error_message = 'An unknown error occurred:';
        }
    }

    res.render('pages/error', {
        error_type: error_type,
        error_message: error_message
    });
}

function getQueryParams(req) {
    return {
        page: req.query.page | 0,
        sort: req.query.sort,
        filter: req.query.filter || 'null'
    };
}

function checkAllParams(req) {
    const type = req.query.type ? req.query.type : null;
    const { page, sort, filter } = getQueryParams(req);
    if (type && checkParam('type', type) === false) {
        const err = new Error('Invalid TYPE parameter');
        err.status = 401;
        throw err;
    }
    if (sort && sort !== 'null' && checkParam('sort', sort) === false) {
        const err = new Error('Invalid SORT parameter');
        err.status = 401;
        throw err;
    }
    if (filter && filter !== 'null' && checkParam(filter, filter) === false) {
        const err = new Error('Invalid FILTER parameter');
        err.status = 401;
        throw err;
    }
    if (page && isNaN(page)) {
        const err = new Error('Invalid PAGE parameter');
        err.status = 401;
        throw err;
    }
}

module.exports = {
    getPaginationRange,
    handleErrors,
    getQueryParams,
    checkAllParams
};