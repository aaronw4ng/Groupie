// Get selected proposal from session storage
if (sessionStorage.getItem("selectedProposal")) {
    let proposalJSON = JSON.parse(sessionStorage.getItem("selectedProposal"))
}

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