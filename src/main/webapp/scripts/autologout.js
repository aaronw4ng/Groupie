function startAutoLogoutRoutine() {
    let popUp = document.querySelector(".popup")
    var idleTime = 0;
    resetTimer;

    document.onmousemove = resetTimer;
    document.onkeypress = resetTimer;

    let idleTimeout;
    function resetTimer() {
        clearTimeout(idleTimeout);
        // console.log("resetting");
        idleTimeout = setTimeout(() => {
            location.href = "../index.jsp"
            sessionStorage.clear()
        }
        , 60000);
        idleTime = 0;
    }

    var idleAction = setInterval(function() {
        if (idleTime >= 50) {
            popUp.classList.add("show")
            if (idleTime == 1) $('#seconds-inactive').html(idleTime + " second");
            else $('#seconds-inactive').html(idleTime + " seconds");
            idleTime++;
        }
        else {
            popUp.classList.remove("show")
            idleTime++;
        }
    }, 1000);
};
