let name = null;
let roomNum = null;
let singleMovie = io.connect('/single-movie');

function setUp() {
    document.getElementById('initial_form').style.display = 'block';
    document.getElementById('chat_interface').style.display = 'none';

    setUpSingleMovieChat();
}

function joinChat(roomNumber) {
    if(roomNumber == null) {
        return null;
    }
    roomNum = roomNumber.toString();
    name = document.getElementById('name').value;
    if (!name) name = 'UnknownUser-' + Math.random();
    singleMovie.emit('create or join', roomNum, name);
}

function setUpSingleMovieChat() {
    singleMovie.on('joined', function (room, userId) {
        if (userId === name) {
            showChat(userId);
        } else {
            writeOnHistory('<strong class="color-mattblue">' + userId + '</strong>' + ' joined.',false );
        }
    });

    singleMovie.on('new message', function (room, userId, text) {
        if (userId === name) {
            writeOnHistory('<strong class="color-mattblue">' + 'Me' + ':</strong> ' + text, true);
        } else {
            writeOnHistory('<strong class="color-mattblue">' + userId + ':</strong> ' + text, false);
        }
    });

    singleMovie.on('disconnected', function (room, userId) {
        writeOnHistory('<strong class="color-mattblue">' + userId + '</strong>' + ' left.', false);
    });
}

function writeOnHistory(text, isOwnMessage ) {
    let history = document.getElementById('history');
    let listItem = document.createElement('li');

    if (isOwnMessage) {
        listItem.classList.add('text-start', 'ms-auto', 'w-auto');
        document.getElementById('chat_input').value = '';
    } else {
        listItem.classList.add('text-start', 'me-auto');
    }
    listItem.innerHTML = text;
    history.appendChild(listItem);

}

function sendNewMessage() {
    let text = document.getElementById('chat_input').value;
    if(text == null || text === '') {
        return null;
    }
    singleMovie.emit('send new', roomNum, name, text);
}

function showChat(user) {
    document.getElementById('initial_form').style.display = 'none';
    document.getElementById('chat_interface').style.display = 'block';
    document.getElementById('name_of_user').innerHTML=user;
}

document.addEventListener("DOMContentLoaded", setUp);