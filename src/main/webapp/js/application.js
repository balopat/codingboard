function updateIfRequired(){
    $.ajax( {
        url: "/rooms/" + room + "/refresh",
        lastPosted: lastPostUUId,
        type: "POST"
    } ).done (function(data){
        if (data.codesnippetPosted) {
            console.log('we have update!');
        }else {
            console.log('no update');
        }
        setTimeout('updateIfRequired()', 2000);
    })

}

updateIfRequired();
