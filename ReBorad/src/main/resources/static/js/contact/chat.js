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
	const roomName = document.getElementById('roomName').textContent;
	const currentUser = document.getElementById("user-infoId").getAttribute("data-name");
	
    currentRoom = roomId;
	
    document.getElementById('chat-title').innerText = '현재 방: ' + roomName;
    document.getElementById('message-container').innerHTML = '';
	
	document.getElementById('message-container').style.display = 'block';
    document.getElementById('input-form').style.display = 'flex';

    document.querySelectorAll("#chat-room-list li").forEach(li => li.classList.remove("disabled"));
    const clickedRoom = Array.from(document.querySelectorAll("#chat-room-list li"))
        .find(li => li.innerText.includes(roomId));
    if (clickedRoom) {
        clickedRoom.classList.add("disabled");
    }
	
	$.ajax({
			url: '/api/chat/room/join',
		    type: 'POST',
		    data: { userId: currentUser,
					roomId: roomId
			},
	        success: function(response) {
	            console.log('입장 처리 완료:', response);
	        },
	        error: function() {
	            alert('방 입장 처리 실패');
	        }
    });
	
	$.ajax({
	        url: `/api/chat/room/${roomId}/messages`,
	        type: 'GET',
	        success: function(messages) {
				messages.forEach(function(message) {
	               showMessage(message); // 메시지 출력
	            });
	        },
	        error: function() {
	            console.warn('이전 메시지 불러오기 실패');
	        }
	    });

    if (stompClient && stompClient.connected) {
        subscribeToRoom(roomId);
        sendEnterMessage(roomId);
    } else {
        connect();
    }
}

// 방 생성 폼 표시
function showCreateRoomForm() {
    document.getElementById('create-room-form').style.display = 'block';
}

// 방 생성 폼 숨기기
function hideCreateRoomForm() {
    document.getElementById('create-room-form').style.display = 'none';
    document.getElementById('new-room-name').value = '';
    document.getElementById('new-room-password').value = '';
}

// 방 생성 처리
function createRoom() {
    const roomName = document.getElementById('new-room-name').value.trim();
    const roomPassword = document.getElementById('new-room-password').value.trim();

    if (roomName === '') {
        alert('방 이름을 입력하세요.');
        return;
    }

    $.ajax({
        url: '/api/chat/createRoom',
        type: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            name: roomName,
            password: roomPassword === '' ? null : roomPassword
        }),
        success: function (room) {
            // 방 생성 성공 후, 화면을 새로 고침하거나 방 목록을 다시 불러옵니다.
            alert('방이 생성되었습니다.');

            // 방 목록을 다시 불러오는 요청을 보내지 않고, 직접 목록을 갱신할 수도 있지만
            // 만약 서버에서 모든 방 목록을 최신 상태로 보내주고 싶다면 다시 /chatForm을 호출할 수 있습니다.
            window.location.reload(); // 새로 고침하여 방 목록을 자동으로 갱신
            hideCreateRoomForm(); // 방 생성 폼 닫기
        },
        error: function () {
            alert('방 생성 실패!');
        }
    });
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
    const currentUserId = document.getElementById("user-infoId").getAttribute("data-name");

    let senderId;
    if (typeof message.sender === 'object' && message.sender !== null) {
        senderId = message.sender.id;  // DB에서 불러온 경우 (sender: MemberEntity)
    } else {
        senderId = message.sender;  // WebSocket 실시간 메시지 (sender: String id)
    }

    const isOwnMessage = (senderId === currentUserId);

    const bubble = document.createElement("div");
    //bubble.className = 'message-wrapper';
	bubble.className = 'message-wrapper ' + (isOwnMessage ? 'right' : 'left');

    if (isOwnMessage) {
        bubble.innerHTML = `<div class="bubble-right">${message.content}</div>`;
    } else {
        bubble.innerHTML = `<div class="bubble-left"><strong>${senderId}:</strong> ${message.content}</div>`;
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
