
$(document).ready(function(){

    var connection = new WebSocket("ws://" + location.host + "/wsstat");
    function onOpen(event) {
	console.log("### onOPEN");
	connection.send(">>>>>>>>>>>>> opened");
    }
    function onMessage(event) {
	console.log("### onMessage : " + event.data);
	$("#statusdisplay").text("##onMessage : " + event.data);
    }

    function onClose(event) {
	console.log("### onClose");
    }

    function onError(event) {
	console.log("### onError " * event.data);
    }

    connection.onopen = onOpen;
    connection.onmessage = onMessage;
    connection.onclose = onClose;
    connection.onerror = onError;

});
