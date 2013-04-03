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
    $.getJSON("https://api.github.com/repos/balopat/codingboard/contributors?callback=?", function(contributors){
        var contributorsHTML = "";
        for (var i=0; i<contributors.data.length; i++){
            contributorsHTML += wrapContributor(contributors.data[i])
        }
        $('#contributors').html(contributorsHTML)
    });

});