// GET ALL USERS

// TODO: Implement userID in login/create account
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
    // TODO: call GetAllUsers servlet on window load, get user ID
    $.ajax({
        method: "POST",
        url: "../GetAllUsersServlet",
        data: {
            userId: currentUserId
        },
        success: function(result) {
            if (result) {
                console.log("SUCCESS RETURN", result)
                USERS_LIST = JSON.parse(result)
            }
            else {
                console.log("Error fetching users list")
            }
        }

    })
}

// TEST INPUT
// const testUsers = [{username: "Gary", id: 1, isAvailable: false }, {username: "HairyGary", id: 2, isAvailable: true }, {username: "urCuteKnees", id: 3, isAvailable: true }, {username: "urMom33", id: 4, isAvailable: true }, {username: "yewwww", id: 5, isAvailable: true }, {username: "bingBONG", id: 6, isAvailable: false }, {username: "Matthew1", id: 7, isAvailable: false }, {username: "Matt1", id: 8, isAvailable: true }, {username: "username1", id: 9, isAvailable: true }]
let userResultsContainer = document.querySelector("#user-results-container")
let addedUserContainer = document.querySelector("#added-user-container")

if (!sessionStorage.getItem("users")) {
    sessionStorage.setItem("users", "[]")
}
else {
    displaySelectedUser()
}

// OnKeyUp Function
function handleInputChange(event) {
    userResultsContainer.innerHTML = ""
    console.log(event.target.value)
    let target = event.target.value
    if (target != "") {
        searchUsers(target)
    }
    
}

function searchUsers(target) {
    // Filter users array for users that contain target string
    // TODO: change testUsers to usersList when implementation allows for it
    let filteredUsers = USERS_LIST.filter( (user) => {
        return user.userName.toLowerCase().includes(target.toLowerCase())
    })
    console.log(filteredUsers)

    // Display Filtered Users Cards
    filteredUsers.forEach(user => {
        let cardString = ``
        if (user.isAvailable) {
            cardString = `
            <div class="user-card" data-user="${user.userName}" data-id="${user.userId}" onclick="handleSelectUser(event)">
                <p><i class="fas fa-users"></i> ${user.userName}</p>
            </div>
            `;
        }
        else {
            cardString = `
            <div class="user-card unavailable">
                <p><i class="fas fa-users-slash"></i> ${user.userName}</p>
            </div>
            `;
        }
        userResultsContainer.innerHTML += cardString
    })

}

// Get Users list from session storage
function getUsersList() {
    return JSON.parse(sessionStorage.getItem("users"))
}

function userInSelected(username) {
    let count = 0
    let usersList = getUsersList() 
    console.log("USERLIST : " + usersList)
    // Check if user in users list
    usersList.forEach(user => {
        if (user == username) {
            console.log("SAME")
            count++
        }
    })
    return count
}

// User Selection 
function handleSelectUser(event) {
    let userInputField = document.querySelector("#user-search-input")
    // Tear Down previous searches 
    userInputField.value = ""
    userResultsContainer.innerHTML = ""

    let user
    // TODO: Check to see if the user is already added
    if (event.target.className == "user-card") {
        user = event.srcElement.dataset.user
    }
    else {
        user = event.srcElement.parentElement.dataset.user
    }
    // console.log("IN USERS? : " + userInSelected(event.srcElement.dataset.user))
    //  
    if (!userInSelected(user)) {
        // let id = event.srcElement.dataset.id
        let currUserList = getUsersList()
        currUserList.push(user)
        sessionStorage.setItem("users", JSON.stringify(currUserList))
    }
    displaySelectedUser()
    
}

// Display Selection in Added 
function displaySelectedUser() {
    addedUserContainer.innerHTML = ""
    let selected = getUsersList()
    selected.forEach(user => {
        let userString = `
        <div data-username="${user}" class="added-user">
          <p>${user}</p>
          <button class="remove-btn user-remove" onclick="handleUserRemoval(event)">X</button>
        </div>
        `;
        addedUserContainer.innerHTML += userString
    })

}

// Selected User Removal
function handleUserRemoval(event) { 
    event.preventDefault()
    let users = getUsersList()
    console.log(event)
    if (confirm("Are you sure you want to remove this user?")) {
        let userCard = event.srcElement.parentElement
        console.log("DATASET: " + userCard.dataset.username)
        userCard.remove()
        console.log("USERS: " + users)
        // Remove user from user lists
        users = users.filter(user => {
            return user !== userCard.dataset.username
        })
        sessionStorage.setItem("users", JSON.stringify(users))
    }
    
}
