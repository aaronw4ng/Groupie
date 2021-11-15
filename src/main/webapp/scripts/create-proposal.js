let eventsContainer = document.querySelector(".events-container")

// GET ALL USERS
window.onload = function() {
    // TODO: call GetAllUsers servlet on window load, get user ID
    // $.ajax({
    //     method: "GET",
    //     url: "",
    //     data: {
    //         userID: 1
    //     }
    // })
}

function handleAddUsersClick(event) {
    event.preventDefault()
    let searchInput = document.querySelector("#user-search-input")
    if (searchInput.style.display !== "none") {
        searchInput.style.display = "none"
    }
    else {
        searchInput.style.display = "block"
    }
}

function handleFindEventClick(event) {
    event.preventDefault()
    console.log("EVENT SEARCH")
    document.location.href = "./event-search.jsp"
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
        console.log(index)
        proposedEvents.splice(index, 1)
        populateEventsContainer(proposedEvents)
        sessionStorage.setItem("selected", JSON.stringify(proposedEvents))
    }
}

function handleCreateProposalClick(event) {
    event.preventDefault()
    let users = getUsersList()
    let selectedEvents = JSON.parse(sessionStorage.getItem("selected"))
    let titleInput = document.querySelector("#input-proposal-name").value

    // TODO: get owner user ID and description?

    $.ajax({
        method: "POST",
        url: "../createProposal",
        data: {
            owner: 1,
            title: titleInput,
            description: "",
            invited: users,
            events: selectedEvents,
            is_Draft: false
        },
        success: function(result) {
            if (result) {
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

    // if proposal doesn't have proposalID then it is new
    let isNewProposal = true
    // pass -1 as proposal id if new proposal
    let proposalIDInput = -1
    if (sessionStorage.getItem("proposalID")) {
        proposalIDInput = parseInt(sessionStorage.getItem("proposalID"))
        isNewProposal = false
    }
    let users = getUsersList()
    let selectedEvents = JSON.parse(sessionStorage.getItem("selected"))
    let titleInput = document.querySelector("#input-proposal-name").value
    console.log("Saving Draft...")
    console.log(users, selectedEvents, titleInput)

    // TODO: owner user ID, description
    /*
    $.ajax({
        method: "POST",
        url: "",
        data: {
            isNew: isNewProposal,
            proposalID: proposalIDInput,
            owner: 1,
            title: titleInput,
            description: "",
            invited: users,
            events: selectedEvents
        },
        success: function(result) {

        }
    })
    */

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