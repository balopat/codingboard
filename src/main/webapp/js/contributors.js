function wrapContributor(contributor) {
      return "<span>" +
                "<img width='80px' height='80px' src='"+contributor.avatar_url+"' ></img>" +
          "<div>      " +
              "<a href='"+contributor.html_url+"' >" +
                 contributor.login +
               "</a>"  +
          "</div>" +
                "<div>      " +
                        "Number of contributions:"+ contributor.contributions +
                    "</div>" +
            "</span>"

}

$(document).ready(function(){
    $.getJSON("https://api.github.com/repos/balopat/codingboard/contributors", null, function(data){
        var names = "";

        for (var i=0; i<data.length; i++){
            names += wrapContributor(data[i])
        }
        $('#contributors').html(names)
    });

});
