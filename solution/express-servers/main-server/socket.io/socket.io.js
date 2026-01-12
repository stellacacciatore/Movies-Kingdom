exports.init = function(io) {

    const chat= io
        .of('/single-movie')
        .on('connection', function (socket) {
            try {
                socket.on('create or join', function (room, userId) {
                    socket.room   = room;
                    socket.userId = userId;
                    socket.join(room);
                    chat.to(room).emit('joined', room, userId);
                });

                socket.on('new message', function (room, userId, chatText) {
                    chat.to(room).emit('chat', room, userId, chatText);
                });

                socket.on('send new', function (room, userId, text) {
                    chat.to(room).emit('new message', room, userId, text);
                });
                socket.on('disconnect', function(){
                    if (socket.room && socket.userId) {
                        chat.to(socket.room).emit('disconnected', socket.room, socket.userId);
                    }
                });
            } catch (e) {
            }
        });
}