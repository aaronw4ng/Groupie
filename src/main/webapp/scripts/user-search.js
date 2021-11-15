// TEST INPUT
const testUsers = [{username: "Gary", id: 1, available: false }, {username: "HairyGary", id: 2, available: true }, {username: "urCuteKnees", id: 3, available: true }, {username: "urMom33", id: 4, available: true }, {username: "yewwww", id: 5, available: true }, {username: "bingBONG", id: 6, available: false }, {username: "Matthew1", id: 7, available: false }, {username: "Matt1", id: 8, available: true }]
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
    let filteredUsers = testUsers.filter( (user) => {
        return user.username.toLowerCase().includes(target.toLowerCase())
    })
    console.log(filteredUsers)

    // Display Filtered Users Cards
    filteredUsers.forEach(user => {
        let cardString = ``
        if (user.available) {
            cardString = `
            <div class="user-card" data-user="${user.username}" data-id="${user.id}" onclick="handleSelectUser(event)">
                <p><i class="fas fa-users"></i> ${user.username}</p>
            </div>
            `;
        }
        else {
            cardString = `
            <div class="user-card unavailable">
                <p><i class="fas fa-users-slash"></i> ${user.username}</p>
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
    event.stopPropagation()
    let userInputField = document.querySelector("#user-search-input")
    // Tear Down previous searches 
    userInputField.value = ""
    userResultsContainer.innerHTML = ""

    // TODO: Check to see if the user is already added
    let user = event.srcElement.dataset.user
    // console.log("IN USERS? : " + userInSelected(event.srcElement.dataset.user))
    if (event.target.className == "user-card" && !userInSelected(user)) {
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
