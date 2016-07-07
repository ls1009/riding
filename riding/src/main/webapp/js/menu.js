/*$.post("auth/log.do", {
}, function(result) {
	if (result.status == "success") {
		$('#logout').css("display", "");
		$('#login').css("display", "none");
	} else {
		$('#logout').css("display", "none");
		$('#login').css("display", "");
	}
}, "json");	
*/

$(".upmenu1").mouseover(function(event){
	$("#logout").attr("src","img/lockoff.png");
});
$(".upmenu1").mouseout(function(event){
	$("#logout").attr("src","img/lockon.png");
});