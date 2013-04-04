function url(boardName) {
    return boardName.toLowerCase().replace(/[^a-z|0-9]/,"_");
}

function updateIfRequired(){
    $.ajax( {
        url: "/boards/" + url(board) + "/refresh",
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

updateIfRequired();
