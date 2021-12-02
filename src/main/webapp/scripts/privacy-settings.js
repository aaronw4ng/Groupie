let currentUserId = ""

if (sessionStorage.getItem("username")) {
    const currentUsername = sessionStorage.getItem("username")
}
if (sessionStorage.getItem("userId")) {
    currentUserId = sessionStorage.getItem("userId")
}
else {
    document.location.href = "../index.jsp"
}

let USERS_LIST = []

window.onload = function() {
    $.ajax({
        method: "POST",
        url: "../GetAllUsersServlet",
        data: {
            userId: currentUserId
        },
        success: function(result) {
            if (result) {
                console.log("all users found: ", result)
                USERS_LIST = JSON.parse(result)
                displayBlockedUsers(USERS_LIST)
                userInputField.value=""
                showAvailability()
            }
            else {
                console.log("Error fetching users list")
            }
        }
    })
    let userInputField = document.querySelector('#block-users-container')

}

let blockedUsersContainer = document.querySelector("#blocked-users-container")

function showAvailability() {
    $.ajax({
        method: "POST",
        url: "../getUserAvailability",
        data: {
            userId: currentUserId,
        },
        success: function(result) {
            if (result) {
                console.log("retrieved availability")
                let json = JSON.parse(result)
                console.log("availability: "+result)

                let availContainer = document.querySelector("#avail-status")
                let availMessage = ``;
                if(json.isAvailable) {
                    availMessage = `
                        <h2>You are currently <span class="strong-result">available</span> indefinitely.</h2>
                    `
                } else if (json.until != "INDEFINITE"){
                    //let untilStr = ${json.until}.toString()
                    availMessage = `
                        <h2>You are currently <span class="strong-result">unavailable</span> until: </h2>
                        <h2><span class="strong-result">date:</span> ${json.until.substring(0,10)}</h2>
                        <h2><span class="strong-result">time:</span> ${json.until.substring(11)}</h2>
                    `
                }
                else {
                    availMessage = `
                        <h2>You are currently <span class="strong-result">unavailable indefinitely</span>.</h2>
                    `
                }
                availContainer.innerHTML = availMessage
                }
            else {
                console.log("Error getting availability")
            }
        }

    })

}

function displayBlockedUsers(list) {
        blockedUsersContainer.innerHTML = ""
        list.filter(user => user.didIBlock).forEach(user => {
            let cardResults = `
                <div>
                    <ul><p class="strong-result"onclick="setUnblockOption(event)" id="${user.userId}">${user.userName}</p></ul>
                </div>
            `;
            blockedUsersContainer.innerHTML += cardResults
        })
}

function toggleAvailability(event) {
    if (event.currentTarget.id == "avail-but") {
        callSetAvailabile()
        showAvailability()
        }
    else if (event.currentTarget.id == "unavail-but")
    {
        localStorage.setItem("time-unavail", document.querySelector("#hours-unavail").value)
        callSetUnavailabile()
        showAvailability()

    }

}
function callSetUnavailabile() {
        let time = localStorage.getItem("time-unavail")
        if (!time || time == 0) time = -1
        $.ajax({
            method: "POST",
            url: "../setAvailability",
            data: {
                userId: currentUserId,
                availability: false,
                hours: time
            },
            success: function(result) {
                if (result) {
                    console.log("successfully set user unavailable for " + time + " hours")
                    }
                else {
                    console.log("Error blocking user: " + userToBlock)
                }
            }

        })

}

function callSetAvailabile() {
        $.ajax({
            method: "POST",
            url: "../setAvailability",
            data: {
                userId: currentUserId,
                availability: true
            },
            success: function(result) {
                if (result) {
                    console.log("successfully set user available")
                    }
                else {
                    console.log("Error blocking user: " + userToBlock)
                }
            }

        })

}

// Store user to unblock in local storage
function setUnblockOption(event) {
    var targetUserId = event.currentTarget.id;
    localStorage.setItem("unblockUser", targetUserId);

    document.getElementById(targetUserId).style.backgroundColor = "darkred";
    document.getElementById(targetUserId).style.backgroundColor = "darkred";
    document.getElementById(targetUserId).style.fontWeight = "bold";
    }

function setBlockOption(event) {
    var currentInput = event.currentTarget.value
    let targetUser = USERS_LIST.filter( (user) => {
              return user.userName.toLowerCase().includes(currentInput.toLowerCase())
              });
    if (targetUser) {
        localStorage.setItem("blockUser", targetUser[0].userId);
        console.log("found "+ currentInput+"at id: "+ targetUser[0].userId)
    }
}

function handleBlock() {
    // Get the Id of user to block from local storage
    let userToBlock = localStorage.getItem("blockUser");
    if (!userToBlock) {
        console.log("not a valid user")
        return;
    }
    console.log("calling ajax on " + userToBlock);
    $.ajax({
        method: "POST",
        url: "../blockUser",
        data: {
            userId: currentUserId,
            blockedUserId: userToBlock
        },
        success: function(result) {
            if (result) {
                console.log("successfully blocked user")
                location.reload()
                }
            else {
                console.log("Error blocking user: " + userToBlock)
            }
        }

    })
}


function handleUnblock() {
{
    // Get the Id of user to block from event listener
    let userToUnblock = localStorage.getItem("unblockUser");
    $.ajax({
        method: "POST",
        url: "../unblockUser",
        data: {
            userId: currentUserId,
            blockedUserId: userToUnblock
        },
        success: function(result) {
            if (result) {
                console.log("successfully unblocked user")
                location.reload()
                }
            else {
                console.log("Error unblocking user: " + userToBlock)
            }
        }

    })
}
}
