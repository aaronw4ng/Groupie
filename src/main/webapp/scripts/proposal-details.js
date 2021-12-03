// User access
let currUsername = ""
if (!sessionStorage.getItem("username")) {
    document.location.href = "../index.jsp"
}
else {
    currUsername = sessionStorage.getItem("username").toLowerCase()
    console.log(currUsername)
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

function cleanSessionStorage() {
    for (var i = 0; i < sessionStorage.length; i++) {
        if (sessionStorage.key(i) !== "username" && sessionStorage.key(i) !== "userId") {
            sessionStorage.removeItem(sessionStorage.key(i))
        }
    }
}

function handleEventCardClick(event) {
    if (sessionStorage.getItem("selectedEvent")) {
        sessionStorage.removeItem("selectedEvent")
    }
    if (event.srcElement.className === "event-card") {
        sessionStorage.setItem("selectedEvent", event.srcElement.dataset.event)
    }
    else {
        sessionStorage.setItem("selectedEvent", event.srcElement.parentElement.dataset.event)
    }
    document.location.href = "./event-details.jsp"
}

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
        <div data-event-id="${event.eventId}" data-event='${JSON.stringify(event)}'' class="event-card" onclick="handleEventCardClick(event)">
            <h1>${event.eventName}</h1>
            <button id="btn-delete-event-${i}" class="btn-delete" onclick="handleDeleteClick(event, 'event')">X</button>
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
        let deleteProposalBtn = document.querySelector("#btn-delete-proposal")
        deleteProposalBtn.style.display = "block"
    }
}

function handleDeleteClick(event, type) {
    event.stopPropagation()
    if (confirm("Are you sure you want to delete this " + type + "?")) {
        if (type === "event") {
            console.log("EVENT")
            let allEvents = document.querySelectorAll(".event-card")
            // if deleting last non host user
            if (allEvents.length <= 1) {
                if (confirm("Are you sure you want to delete this " + type + "? Doing so will delete the proposal.")) {
                    $.ajax({
                        method: "POST",
                        url: "../deleteProposal",
                        data: {
                            proposalId: ssProposalID,
                        },
                        success: function (result) {
                            if (result === "true") {
                                console.log("Proposal Deleted successfully")
                                document.location.href = "./view-proposals.jsp"
                            }
                        }
                    })
                }
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

function handleDeleteProposalClick(event) {
    if (confirm("Are you sure you want to delete the proposal? This cannot be undone.")) {
        $.ajax({
            method: "POST",
            url: "../deleteProposal",
            data: {
                proposalId: ssProposalID,
            },
            success: function (result) {
                if (result === "true") {
                    console.log("Proposal Deleted successfully")
                    document.location.href = "./view-proposals.jsp"
                }
            }
        })
    }
}

// TODO: add autologout

displayRemoveButtons()
startAutoLogoutRoutine()