if (!sessionStorage.getItem("users")) {
    sessionStorage.setItem("users", "[]")
}
// TEST INPUT
const testUsers = [{username: "Gary", id: 1, available: false }, {username: "HairyGary", id: 2, available: true }, {username: "urCuteKnees", id: 3, available: true }, {username: "urMom33", id: 4, available: true }, {username: "yewwww", id: 5, available: true }, {username: "bingBONG", id: 6, available: false }, {username: "Matthew1", id: 7, available: false }, {username: "Matt1", id: 8, available: true }]
let userResultsContainer = document.querySelector("#user-results-container")

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
        let userInfo = user.username
        let cardString = ``
        if (user.available) {
            cardString = `
            <div class="user-card" data-user="${userInfo} "onclick="handleSelectUser(event)">
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

// User Selection 
function handleSelectUser(event) {
    console.log(event)
    // let currUserList = JSON.parse(sessionStorage.getItem("users"))
    // currUserList.push(user)
    // sessionStorage.setItem("users", JSON.stringify(currUserList))
}
