// ** GLOBALS **
// Get selected proposal from session storage

let ssProposalID // sessionStorage proposalId
if (sessionStorage.getItem("selectedProposal")) {
    let proposalJSON = JSON.parse(sessionStorage.getItem("selectedProposal"))
    ssProposalID = proposalJSON.proposalId
}
// ** END GLOBALS **

function displayAllProposalInfo() {
    let proposalTitle = document.querySelector("#proposal-name")
    let eventsContainer = document.querySelector(".events-container")
    let usersContainer = document.querySelector(".users-container")
    const proposalEvents = proposalJSON.events

    proposalTitle.innerHTML = proposalJSON.title

    for (i in proposalEvents) {
        let event = proposalEvents[i]
        // TODO: display event title in container
    }

}

// BACK BTN PRESS
function handleBackBtnClick(event) {
    function cleanSessionStorage() {
        for (var i = 0; i < sessionStorage.length; i++) {
            if (sessionStorage.key(i) !== "username" && sessionStorage.key(i) !== "userId") {
                sessionStorage.removeItem(sessionStorage.key(i))
            }
        }
    }
    cleanSessionStorage()
    document.location.href = "#"
}

// TODO: Dynamically fill events with their ids as metadata

// TODO: Dynamically fill users with their ids as metadata

// TODO: if current user is owner, remove buttons on all events and users (if event/user is last then call DeleteProposal)
// when deletes are clicked, have a confirm(); if true then call Remove(Event/Invitee)FromSentProposal servlet with accompanying proposal id and respective event/invitee id

// TODO: add autologout