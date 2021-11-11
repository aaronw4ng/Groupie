let eventsContainer = document.querySelector(".events-container")

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
            <button onclick="handleDeleteEvent(event)" class="remove-btn">X</button>
        </div>
        `;
        eventsContainer.innerHTML += cardString
    } 
}

displaySelectedEvents()