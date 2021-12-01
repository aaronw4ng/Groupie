// User access
if (!sessionStorage.getItem("username")) {
    document.location.href = "../index.jsp"
}

// ** GLOBALS **
// Get selected proposal from session storage

let isOwner = false

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
function displayRemoveButtons() {
    let removeButtons = document.querySelectorAll(".btn-delete")
    if (isOwner) {
        for (var i = 0; i < removeButtons.length; i++) {
            removeButtons[i].style.display = "block"
        }
    }
}

function handleDeleteClick(event, type) {
    if (confirm("Are you sure you want to delete this " + type + "?")) {
        if (type === "event") {
            console.log("EVENT")
            let allEvents = document.querySelectorAll(".event-card")
            // if deleting last non host user
            if (allEvents.length <= 1) {
                $.ajax({
                    method: "POST",
                    url: "../deleteProposal",
                    data: {
                        proposalId: ssProposalID,
                    },
                    success: function (result) {
                        if (result === "true") {
                            console.log("Proposal Deleted successfully")
                        }
                    }
                })
            }

            // console.log(event)
            console.log(event.srcElement.parentElement.dataset.eventId)
            let removedEventID = event.srcElement.parentElement.dataset.eventId
            $.ajax({
                method: "POST",
                url: "../removeEventFromSentProposalServlet",
                data: {
                    proposalId: ssProposalID,
                    eventId: removedEventID
                },
                success: function (result) {
                    if (result === "true") {
                        console.log("Deleted successfully")
                        event.srcElement.parentElement.remove()
                    }
                }
            })
        }
        else {
            console.log("USER")
            let allUsers = document.querySelectorAll(".user-card")
            // if deleting last non host user
            if (allUsers.length <= 2) {
                $.ajax({
                    method: "POST",
                    url: "../deleteProposal",
                    data: {
                        proposalId: ssProposalID,
                    },
                    success: function (result) {
                        if (result === "true") {
                            console.log("Proposal Deleted successfully")
                        }
                    }
                })
            }
            console.log(event.srcElement.parentElement.dataset.userId)
            let selectedUserID = event.srcElement.parentElement.dataset.userId
            $.ajax({
                method: "POST",
                url: "../removeInviteeFromSentProposal",
                data: {
                    proposalId: ssProposalID,
                    userId: selectedUserID
                },
                success: function (result) {
                    if (result === "true") {
                        console.log("Deleted successfully")
                        event.srcElement.parentElement.remove()
                    }
                }
            })
        }
    }
}

// TODO: add autologout

startAutoLogoutRoutine()
displayRemoveButtons()