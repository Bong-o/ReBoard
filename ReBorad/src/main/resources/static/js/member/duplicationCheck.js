function checkId() {
	event.preventDefault();
	
	const getId = $("#new-userid").val();
	
	$.ajax({
		type: "GET",
		url: "/member/checkId/" + getId,
		success: function(res) {
			console.log(res);
			if(res === true) { // 중복이 있을 때
				$("#id-msg").css("color", "red");
				$("#id-msg").text("사용 불가능한 아이디입니다.");
				$("#submit").prop("disabled", true);
			} else { // 중복이 없을 때
				$("#id-msg").css("color", "green");
				$("#id-msg").text("사용 가능한 아이디입니다.");
				$("#submit").prop("disabled", false);
			}
			
		},
		error: function(err) {
		}					
	})
}