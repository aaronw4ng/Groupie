// Verify user
let currentUserId = ""

if (sessionStorage.getItem("username")) {
    const currentUsername = sessionStorage.getItem("username")
}
if (sessionStorage.getItem("userId")) {
    currentUserId = sessionStorage.getItem("userId")
}
else {
}

// Store finalized & unfinaized proposals in separate arrays
let REC_PROPOSALS_FINAL = []
let REC_PROPOSALS_DRAFT = []


// Retrieve all proposals on window load
/*
window.onload = function() {
    $.ajax({
        method: "POST",
        url: "../getAllNonDraftProposals",
        data: {
            userID: currentUserId,
            isOwner: false
        },
        success: function(result) {
        if (result) {
            console.log("found props successfully")
            REC_PROPOSALS_FINAL = JSON.parse(result)
            displayResults(REC_PROPOSALS_FINAL)
        }
        else {
            console.log("error finding props")
        }
      }
    }),
    $.ajax({
        method: "POST",
        url: "../getAllDraftProposals",
        data: {
            userId: currentUserId
        },
        success: function(result) {
        if (result) {
            console.log("found props successfully")
            REC_PROPOSALS_DRAFT = JSON.parse(result)
            displayResults(REC_PROPOSALS_DRAFT)
        }
        else {
            console.log("error finding props")
        }
      }
    })
}
*/
// Handle button toggle on click to change button color and call filter/sort functions
function toggle(b) {
    b.value = (b.value=="ON")?"OFF":"ON"
    if (b.value == "ON") {
        b.className = "on"
        //if (b.textContent == "finalized") filterResults(final, REC_PROPOSALS_FINAL)
        //else if (b.textContent == "unfinalized")
    }
    else {
        b.className = "off"
    }
}

function clearFilters() {
    //displayResults(REC_PROPOSALS_FINAL)
    document.querySelectorAll('.on').forEach((item) => {
           item.className = "off"
    })
}

function filterResults(type, proposalId) {
    // Check column is_finalized and remove from list if is_finalized != input type
}

// Parse results into card display for HTML
// Received proposals: Display title of proposal and
function displayResults(filteredResults) {
    filteredResults.forEach(result => {
        let resultsCard = `
            <div class="border">
             // Get Proposal Name from DB
               <h4>${result.proposalId.name}</h4>
               <h3>events</h3>
               // Get list of events from db
                 <ul><a href="#">${result.proposalId.eventId}</a></ul>
               <h3>date/time:</h3>
        `
        if (${result.proposalId.is_finalized} == false) {
            let resultsCard += `
                    <ul><p class="strong-result" style="color:darkred;">not finalized</p></ul>
                </div>
            `
        } else {
            let resultsCard += `
                      <ul><p class="strong-result" style="color:goldenrod;">${result.proposalId.best_event_id}</p></ul>
                      <ul><p class="strong-result" style="color:goldenrod;">${result.proposalId.best_event_id.name.startDateTime.substring(0,10)}</p></ul>

                </div>
            `
        }

    })
}

function isFinalized(proposal) {

}

function sortResults() {
}

