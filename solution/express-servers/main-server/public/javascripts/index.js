function init() {
    const toggleBtn = document.getElementById("toggle-actors-btn");

    if (toggleBtn) {
        toggleBtn.addEventListener("click", showMoreActors);
    }

    let ratingElement = document.getElementById('movie-rating');
    if (ratingElement) {
        let rating = parseFloat(ratingElement.getAttribute('data-rating'));
        if (!isNaN(rating)) {
            document.getElementById('star-rating').innerHTML = renderStars(rating);
        }
    }
}

/**
 * sends the input value (a movie name) written by the user to the route which handles the request
 */
function findMovieBySearch(event) {
    event.preventDefault();

    const query = document.getElementById('search-input').value.trim();
    if (query.length > 0) {
        const movie = encodeURIComponent(query);
        window.location.href = `/search?movie=${movie}&page=0&sort=movieId,asc`;
    }
}

/**
 * sends the themes value to the route by using a map function to give them as an array of parameters
 */
function findMoviesByTheme(event) {
    event.preventDefault();

    const query = document.getElementById('theme-search').value.trim();
    if (query.length > 0) {
        const themes = extractThemes(query);
        window.location.href = '/theme-finder?' + themes.map(t => `themes=${encodeURIComponent(t)}`).join('&');
    }
}

function extractThemes(query) {
    return query.split(/,\s*/); // this regexp helps me to take away commas and space
}

/**
 * returns a new html set of span which shows the rating as star
 * @param rating a float value representing the rating
 * @returns {string} a new html part as a set of span
 */
function renderStars(rating) {
    let html = '';
    for (let i = 1; i <= 5; i++) {
        if (rating >= i) {
            html += '<span class="fa fa-star checked"></span>';
        } else if (rating >= i - 0.5) {
            html += '<span class="fa fa-star-half-o checked"></span>';
        } else {
            html += '<span class="fa fa-star"></span>';
        }
    }
    return html;
}

/**
 * Makes 10 more actors visible when clicking the button
 */
function showMoreActors() {

    const toggleLabel = document.getElementById("toggle-actors-label");

    const allActors = document.querySelectorAll(".actor-item");
    const hiddenActors = [...allActors].filter(actor => actor.classList.contains("d-none"));

    const currentlyVisible = allActors.length - hiddenActors.length;
    const nextToShow = 10;

    if (hiddenActors.length > 0) {
        // Show next 10 actors
        hiddenActors.slice(0, nextToShow).forEach(actor => actor.classList.remove("d-none"));

        // If all are now visible, change label to "Show less"
        if (document.querySelectorAll(".actor-item.d-none").length === 0) {
            toggleLabel.innerText = "Show less";
        }
    } else {
        // Reset: hide all but first 10
        allActors.forEach((actor, index) => {
            if (index >= 10) {
                actor.classList.add("d-none");
            }
        });
        toggleLabel.innerText = "Show more";
    }
}

