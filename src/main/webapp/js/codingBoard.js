function scheduleTimerUpdate(timerLabelId, timeLeftInSeconds) {
    var id = "#" + timerLabelId;

    var minutes = Math.floor(timeLeftInSeconds / 60);
    var m = (minutes == 0 ? "" : minutes + " minutes ");
    $(id).html(m + timeLeftInSeconds % 60 + " seconds");

    if (timeLeftInSeconds <= 0) {
        $(id).html('This CodingBoard has expired!');
    } else {
        setTimeout(function(){ scheduleTimerUpdate(timerLabelId, timeLeftInSeconds - 1) }, 1000);
    }
}