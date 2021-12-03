// User access
let currUsername = ""
if (!sessionStorage.getItem("username")) {
    document.location.href = "../index.jsp"
}
else {
    currUsername = sessionStorage.getItem("username")
}

// ** GLOBALS **
// Get selected proposal from session storage

let proposalJSON
let isOwner
let ssProposalID // sessionStorage proposalId
if (sessionStorage.getItem("selectedProposal")) {
    proposalJSON = JSON.parse(sessionStorage.getItem("selectedProposal"))
    console.log(proposalJSON)
    isOwner = (proposalJSON.user.username === currUsername)
    console.log("OWNER? " + isOwner)
    ssProposalID = proposalJSON.proposalId
    displayAllProposalInfo()
}
// ** END GLOBALS **

function displayAllProposalInfo() {
    let proposalTitle = document.querySelector("#proposal-name")
    let eventsContainer = document.querySelector(".events-container")
    let usersContainer = document.querySelector(".users-container")
    const proposalEvents = proposalJSON.events
    const proposalUsers = proposalJSON.invitees

    proposalTitle.innerHTML = proposalJSON.title

    eventsContainer.innerHTML = ""
    for (i in proposalEvents) {
        let event = proposalEvents[i]
        let eventString = `
        <div data-event-id="${event.eventId}" data-event='${JSON.stringify(event)}'' class="event-card">
            <h1>${event.eventName}</h1>
        </div>
        `;
        eventsContainer.innerHTML += eventString
    }

    usersContainer.innerHTML = ""
    for (i in proposalUsers) {
        let user = proposalUsers[i]
        let userString 
        if (user.username === proposalJSON.user.username) {
            userString = `
            <div class="user-card">
                <p id="proposal-host-header"><i class="fas fa-crown"></i> proposal host</p>
                <h1>${user.username}</h1>
            </div>
            `;
        }
        else {
            userString = `
            <div data-user-id="${user.userId}" class="user-card">
                <h1>${user.username}</h1>
                <button id="btn-delete-user-${i}" class="btn-delete" onclick="handleDeleteClick(event, 'user')">X</button>
            </div>
            `;
        }
        usersContainer.innerHTML += userString
    }


}

// BACK BTN PRESS
function handleBackBtnClick(event) {
    if (sessionStorage.getItem("selectedProposal")) {
        sessionStorage.removeItem("selectedProposal")
    }
    document.location.href = "./view-proposals.jsp"
}

startAutoLogoutRoutine()