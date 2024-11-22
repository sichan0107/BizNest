var stompClient = null;

function connect() {
    var socket = new SockJS('/ws'); // 서버 WebSocket 엔드포인트 (서버와 연결할 uri)

    stompClient = Stomp.over(socket);

    // frame은 명령어, 헤더, body등으로 구성되어있다.
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);

        // subscribe() 는 클라이언트가 구독할 URI다. /join/room/1 이런식으로 채팅방 uri를 넣어도 된다.
        stompClient.subscribe('/topic/public', function (message) {
            showMessage(JSON.parse(message.body));
        });
    });
}

function sendMessage() {
    var messageContent = document.getElementById("message").value;
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify({
        sender: document.getElementById("username").value,
        content: messageContent,
        type: "CHAT"
    }));
}

function showMessage(message) {
    var messages = document.getElementById("messages");
    var messageElement = document.createElement('li');
    messageElement.innerText = message.sender + ": " + message.content;
    messages.appendChild(messageElement);
}
