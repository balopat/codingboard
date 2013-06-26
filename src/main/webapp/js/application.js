function updateIfRequired(){
    $.ajax( {
        url: "/boards/" + board + "/refresh",
        data: {
            lastCodeSnippetId: lastCodeSnippetId
        },
        type: "POST"
    } ).done (function(data){
        var codeSnippets = $('#codeSnippets');
        var codeSnippet = $.parseJSON(data).codeSnippet;
        if (codeSnippet) {
            codeSnippets.prepend("<div> " +
            "<div>" +
            " <b>" + codeSnippet.description  + " </b>" +
            " </div>" +
            " <div>" +
            "   <pre class=\"brush: " + codeSnippet.language + " \">"+codeSnippet.code+"</pre>" +
            "   </div>" +
            "   <div>" +
            "     <i>"+codeSnippet.timestamp+"</i>" +
            "     </div>" +
            "     </div>");
            SyntaxHighlighter.highlight();
            lastCodeSnippetId = codeSnippet.id;
        }else {
            console.log('no update');
        }
        setTimeout('updateIfRequired()', 2000);
    })
}

function enableTheme(themeName) {
    var link = $('#theme-link-' + themeName.toLowerCase());
    $('link[title^=theme-]').prop('disabled', true);
    $('link[title=theme-' + themeName.toLowerCase() + ']').prop('disabled', false);

    $('#themes a')
        .prop('disabled', false)
        .css('color', '')
        .css('text-decoration', '');
    link.prop('disabled', true)
        .css('color', '#ccc')
        .css('text-decoration', 'none');
}

$(document).ready(function() {
    var themes = ['Default', 'Django', 'Eclipse', 'Emacs', 'FadeToGrey', 'MDUltra', 'Midnight', 'RDark'];
    var themesElement = $('#themes');
    $.each(themes, function(i, theme) {
        var link = $('<a href="#">').attr('id', 'theme-link-' + theme.toLowerCase()).text(theme);
        link.click(function() {
            enableTheme(theme);
        });
        themesElement.append(link).append(' ');
    });
});

$(document).ready(function() {
    updateIfRequired();
    enableTheme('Midnight');
});
