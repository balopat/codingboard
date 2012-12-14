$(function() {
  "use strict";

  var codesnippets = $("#codesnippets");
  var socket = $.atmosphere;
  var subSocket;
  var transport = 'websocket';



  /**
   * Make a persistent connection to our server-side atmosphere method.
   */
  var request = {
    url: "/roomgateway",
    contentType: "application/json",
    logLevel: 'debug',
    shared: true,
    transport: transport,
    trackMessageLength : true,
    fallbackTransport: 'long-polling'
  };

  /**
   * This runs when the connection is first made.
   */
  request.onOpen = function(response) {
    transport = response.transport;
  };


  /**
   * Runs when the client reconnects to Atmosphere.
   */
  request.onReconnect = function(rq, rs) {
    socket.info("Reconnecting")
  };

  /**
   * This is what runs when an Atmosphere message is pushed from the
   * server to this client.
   */
  request.onMessage = function(rs) {
    var message = rs.responseBody;
    try {
      var json = jQuery.parseJSON(message).codeSnippet;
      console.log("got a message")
      console.log(json)
      codesnippets.prepend("<div> " +
               "<div>" +
               " <b>" + json.description  + " </b>" +
               " </div>" +
               " <div>" +
               "   <pre class=\"brush: " + json.language + " \">"+json.code+"</pre>" +
               "   </div>" +
               "   <div>" +
               "     <i>"+json.timestamp+"</i>" +
               "     </div>" +
               "     </div>")
      SyntaxHighlighter.all()
    } catch (e) {
      console.log('This doesn\'t look like a valid JSON object: ', 
        message.data);
      return;
    }

  };

  /**
   * When the connection closes, run this:
   */
  request.onClose = function(rs) {
    loggedIn = false;
  };

  /**
   * Run this when a connection error occurs.
   */
  request.onError = function(rs) {
    content.html($('<p>', {
      text: 'Sorry, but there\'s some problem with your ' + 
      'socket or the server is down'
    }));
  };

  // Subscribe to receive events from Atmosphere.
  subSocket = socket.subscribe(request);

});
