let stompClient = null;
let currentRoom = null;

// 소켓 연결
function connect() {
    const socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);

    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        if (currentRoom) {
            subscribeToRoom(currentRoom);
            sendEnterMessage(currentRoom);
        }
    });
}

// 방 구독
function subscribeToRoom(roomId) {
    stompClient.subscribe(`/topic/chatroom.${roomId}`, function (chat) {
        const message = JSON.parse(chat.body);
        showMessage(message);
    });
}

// 입장 메시지 전송 함수
function sendEnterMessage(roomId) {
    const currentUser = document.getElementById("user-infoId").getAttribute("data-name");
    const enterMessage = {
        type: "ENTER",
        roomId: roomId,
        sender: currentUser,
        content: "",
        timestamp: new Date().toISOString()
    };
    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(enterMessage));
}

// 방 입장
function enterRoom(roomId) {
    currentRoom = roomId;

    document.getElementById('chat-title').innerText = '현재 방: ' + roomId;
    document.getElementById('message-container').innerHTML = '';

    document.querySelectorAll("#chat-room-list li").forEach(li => li.classList.remove("disabled"));
    const clickedRoom = Array.from(document.querySelectorAll("#chat-room-list li"))
        .find(li => li.innerText.includes(roomId));
    if (clickedRoom) {
        clickedRoom.classList.add("disabled");
    }

    if (stompClient && stompClient.connected) {
        subscribeToRoom(roomId);
        sendEnterMessage(roomId);
    } else {
        connect();
    }
}

// 메시지 전송
function sendMessage() {
    const input = document.getElementById("message-input");
    const text = input.value.trim();
    const currentUser = document.getElementById("user-infoId").getAttribute("data-name");

    if (!text || !currentRoom || !stompClient) return;

    const chatMessage = {
		type: "TALK",
        roomId: currentRoom,
        sender: currentUser,
        content: text,
        timestamp: new Date().toISOString()
    };

    stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
    input.value = '';
}

// 메시지 출력
function showMessage(message) {
    const container = document.getElementById("message-container");
    const currentUser = document.getElementById("user-infoId").getAttribute("data-name");
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

// 브라우저 종료 시 퇴장 메시지
window.onbeforeunload = function () {
    if (currentRoom && stompClient && stompClient.connected) {
        const currentUser = document.getElementById("user-infoId").getAttribute("data-name");
        const exitMessage = {
            type: "EXIT",
            roomId: currentRoom,
            sender: currentUser,
            content: "",
            timestamp: new Date().toISOString()
        };
        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(exitMessage));
    }
};
