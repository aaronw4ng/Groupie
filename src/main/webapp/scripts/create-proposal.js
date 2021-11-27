// *** GLOBALS ***
// Get current user in session
let currentUsername = ""
if (sessionStorage.getItem("username")) {
    currentUsername = sessionStorage.getItem("username")
}
else {
    document.location.href = "../index.jsp"
}

// if proposal doesn't have proposalID then it is new
let isNewProposal = true
// pass -1 as proposal id if new proposal
let proposalIDInput = -1
if (sessionStorage.getItem("proposalId")) {
    proposalIDInput = parseInt(sessionStorage.getItem("proposalId"))
    isNewProposal = false
}

if (sessionStorage.getItem("proposalName")) {
    document.querySelector("#input-proposal-name").value = sessionStorage.getItem("proposalName")
}

let eventsContainer = document.querySelector(".events-container")

let searchInput = document.querySelector("#user-search-input")

// *** END GLOBALS ***

function handleAddUsersClick(event) {
    event.preventDefault()
    if (searchInput.style.display === "none") {
        searchInput.style.display = "block"
    }
    else {
        searchInput.style.display = "none"
    }
}

function handleFindEventClick(event) {
    event.preventDefault()
    document.location.href = "./event-search.jsp"
}

// Save proposal name in session storage
function handleProposalNameBlur(event) {
    event.preventDefault()
    console.log(event.target.value)
    sessionStorage.setItem("proposalName", event.target.value)
}

// Present user with deletion confirmation
function handleDeleteEvent(event) {
    event.preventDefault()
    // If user clicks 'OK'
    if (confirm("Are you sure you want to delete this event?")) {
        // Delete the card UI
        // event.srcElement.parentElement.remove()
        // Delete from session storage
        // proposed event array
        let proposedEvents = JSON.parse(sessionStorage.getItem("selected"))
        // index of current event in proposed event array
        let index = event.srcElement.parentElement.dataset.idx

        proposedEvents.splice(index, 1)
        populateEventsContainer(proposedEvents)
        sessionStorage.setItem("selected", JSON.stringify(proposedEvents))
    }
}

// Format Events - formats events list to be sent to the servlets
function formatEvents(eventsList) {
    for (item in eventsList) {
        eventsList[item] = JSON.parse(eventsList[item])
    }
    return JSON.stringify(eventsList)

}

// Clean Up Session Storage 
function cleanUpSessionStorage() {
    let keys = ["users", "proposalName", "selected", "events"]
    for (key in keys) {
        if (sessionStorage.getItem(keys[key])) {
            sessionStorage.removeItem(keys[key])
        }
    }

}

function handleCreateProposalClick(event) {
    event.preventDefault()
    // Get all necessary input
    let users = JSON.stringify(getUsersList())
    let selectedEvents = JSON.parse(sessionStorage.getItem("selected"))
    selectedEvents = formatEvents(selectedEvents)
    let titleInput = document.querySelector("#input-proposal-name").value
    console.log(users, selectedEvents, titleInput)
    // Check for empty users/events
    if (selectedEvents.length === 0) {
        alert("Must have one or more event selected.")
        return
    }
    else if (JSON.parse(users).length === 0) {
        alert("Must have one or more users selected.")
        return
    }
    
    $.ajax({
        method: "POST",
        url: "../createProposal",
        data: {
            owner: currentUsername,
            title: titleInput,
            descript: "",
            invited: users,
            events: selectedEvents,
            isNew: isNewProposal,
            proposalId: proposalIDInput
        },
        success: function(result) {
            if (result) {
                cleanUpSessionStorage()
                alert("Proposal sent successfully!")
            }
            else {
                alert("Proposal failed to send.")
            }
        }
    })
    
}

function handleSaveDraftClick(event) {
    event.preventDefault()
    let users = JSON.stringify(getUsersList())
    let selectedEvents = JSON.parse(sessionStorage.getItem("selected"))
    selectedEvents = formatEvents(selectedEvents)
    let titleInput = document.querySelector("#input-proposal-name").value
    console.log("Saving Draft...")
    console.log(users, selectedEvents, titleInput, proposalIDInput)

    // TODO: owner user ID, description
    
    $.ajax({
        method: "POST",
        url: "../saveDraftProposal",
        data: {
            isNew: isNewProposal,
            proposalId: proposalIDInput,
            owner: currentUsername,
            title: titleInput,
            descript: "",
            invited: users,
            events: selectedEvents
        },
        success: function(result) {
            console.log(result)
            if (result === "true") {
                alert("Draft successfully saved!")
            }
            else {
                alert("Unable to save draft.")
            }
        }
    })

}

function displaySelectedEvents() {
    if ( sessionStorage.getItem("selected") ) {
        const proposedEvents = JSON.parse(sessionStorage.getItem("selected"))
        console.log(proposedEvents.length)
        if (proposedEvents.length != 0) {      
            populateEventsContainer(proposedEvents)
        }
    }
    
}

function populateEventsContainer(proposedEvents) {
    eventsContainer.innerHTML = ""
    for (let i = 0; i < proposedEvents.length; i++) {
        let proposedJson = JSON.parse(proposedEvents[i])
        let cardString = `
        <div data-idx="${i}" class="event-card">
            <div>
                <h1>${proposedJson.eventName}</h1>
            </div>
            <button onclick="handleDeleteEvent(event)" class="remove-btn event-remove">X</button>
        </div>
        `;
        eventsContainer.innerHTML += cardString
    } 
}

displaySelectedEvents()