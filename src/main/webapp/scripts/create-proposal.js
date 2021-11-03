function handleFindEventClick(event) {
    event.preventDefault()
    console.log("EVENT SEARCH")
    document.location.href = "./event-search.jsp"
}

function displaySelectedEvents() {
    const proposalEvents = sessionStorage.getItem("events")
    if (proposalEvents.length != 0) {
        for (let i = 0; i < proposalEvents.length; i++) {
            // TODO : dynamically create new event card to display under events
        }
    }
}