
$(document).ready(function() {

    var connection = new WebSocket("ws://" + location.host + "/wsstat");

    connection.onopen = function (event) {
	console.log("### onOPEN");
	connection.send(">>>>>>>>>>>>> opened");
    }
    connection.onmessage = function (event) {
	console.log("### onMessage : " + event.data);
	var stat = JSON.parse(event.data);
	$("#showcount").text(stat.id);
	$("#showactsby").text(stat.actsby?"ACT":"SBY");
	$("#showccalls").text(stat.calls);
	$("#showcpuusage").text(stat.cpuusage);
    }
    connection.onclose = function (event) {
	console.log("### onClose");
    }
    connection.onerror = function (event) {
	console.log("### onError " * event.data);
    }

});
