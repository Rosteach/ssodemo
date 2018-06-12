$(function() {
	// imitate logout button submitting
	$("#logout-btn").on("click", function(e) {
		e.preventDefault(); // cancel the link itself
		$("#logout").submit();
	});
	
	// create and stringify put data
//	function createPutData(){
//		return JSON.stringify({
//			username: $('#username').val(),
//			password: $('#password').val()
//		});
//	}

	// ajax call on user update operation
//	$('.user-update').click(function() {
//		$.ajax({
//			url : location.origin.concat('/api/users/').concat($(this).attr('userid')),
//			contentType : 'application/json',
//			method : 'PUT',
//			data: 
//		}).done(function(data) {
//			console.log(data);
//		}).fail(function(data) {
//			console.log(data);
//		});
//	});
});