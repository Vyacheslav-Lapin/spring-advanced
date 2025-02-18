document.getElementById("sendMessageBtn").addEventListener("click", () => {
    const sender = document.getElementById('sender').value;
    const message = document.getElementById('message').value;
    stompClient.send("/app/sendMessage", {}, JSON.stringify({'sender': sender, 'content': message}));
});

function showMessage(message) {
    const messages = document.getElementById('messages');
    const messageElement = document.createElement('div');
    messageElement.appendChild(document.createTextNode(`${message.sender}: ${message.content}`));
    messages.appendChild(messageElement);
}

const socket = new SockJS('/chat');
let stompClient = Stomp.over(socket);
stompClient.connect({}, frame => {
    console.log('Connected:', frame);
    stompClient.subscribe('/topic/messages', chatMessage => showMessage(JSON.parse(chatMessage.body)));
});
