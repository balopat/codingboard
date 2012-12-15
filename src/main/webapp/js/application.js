$(function() {
  "use strict";

  var codesnippets = $("#codesnippets");
  var socket = $.atmosphere;
  var subSocket;
  var transport = 'ssp';



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
    if (transport == 'local') {
        alert('no I won`t open a new client');
        request.close();
    }
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
      var json = jQuery.parseJSON(message);
      if (json.codesnippetPosted.formtoken = lastPostUUId) {
          alert('yey!')
              return;
      }
      console.log("got a message")
      console.log(json)
      var codesnippet = json.codesnippet
      codesnippets.prepend("<div> " +
               "<div>" +
               " <b>" + codesnippet.description  + " </b>" +
               " </div>" +
               " <div>" +
               "   <pre class=\"brush: " + codesnippet.language + " \">"+codesnippet.code+"</pre>" +
               "   </div>" +
               "   <div>" +
               "     <i>"+codesnippet.timestamp+"</i>" +
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
