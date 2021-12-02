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
            }
            else {
                console.log("Error fetching users list")
            }
        }
    })
    let userInputField = document.querySelector('#block-users-container')
    userInputField.value=""
}

let blockedUsersContainer = document.querySelector("#blocked-users-container")

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
