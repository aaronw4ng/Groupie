var idleTime = 0;
window.onload = function() {
    resetTimer;

    document.onmousemove = resetTimer;
    document.onkeypress = resetTimer;

    let idleTimeout;
    function resetTimer() {
        clearTimeout(idleTimeout);
        // console.log("resetting");
        idleTimeout = setTimeout(() => location.href = "../index.jsp", 60000);
        idleTime = 0;
    }

    var idleAction = setInterval(function() {
        if (idleTime >= 50) {
            $('.popup').show();
            if (idleTime == 1) $('#seconds-inactive').html(idleTime + " second");
            else $('#seconds-inactive').html(idleTime + " seconds");
            idleTime++;
        }
        else {
            $('.popup').hide();
            idleTime++;
        }
    }, 1000);
};
