let stompClient = null;
let currentRoom = null;

function connect() {
    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

	    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
		if (currentRoom) {
            subscribeToRoom(currentRoom);
        }
    });
}

function subscribeToRoom(roomId) {
    stompClient.subscribe(`/topic/chatroom.${roomId}`, function (chat) {
        const message = JSON.parse(chat.body);
        showMessage(message);
    });
}

function enterRoom(roomId) {
    currentRoom = roomId;
    document.getElementById('chat-title').innerText = '현재 방: ' + roomId;
    document.getElementById('message-container').innerHTML = '';

    if (stompClient && stompClient.connected) {
        subscribeToRoom(roomId);
    } else {
        connect();
    }
}

function sendMessage() {
    const input = document.getElementById("message-input");
    const text = input.value.trim();
    const currentUser = document.getElementById("user-info").getAttribute("data-name");
	
    if (!text || !currentRoom || !stompClient) return;

    const chatMessage = {
        roomId: currentRoom,
        sender: currentUser,
        content: text,
        timestamp: new Date().toISOString()
    };
	
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    input.value = '';
}

function showMessage(message) {
    const container = document.getElementById("message-container");
    const currentUser = document.getElementById("user-info").getAttribute("data-name");
    const isOwnMessage = (message.sender === currentUser);
	
    const bubble = document.createElement("div");
    bubble.className = 'message-wrapper';

    if (isOwnMessage) {
        bubble.innerHTML = `<div class="bubble-right">${message.content}</div>`;
    } else {
        bubble.innerHTML = `<div class="bubble-left"><strong>${message.sender}:</strong> ${message.content}</div>`;
    }

    container.appendChild(bubble);
    container.scrollTop = container.scrollHeight;
}

window.onload = connect;
