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

let REC_PROPOSALS_FINAL = []
let REC_PROPOSALS_DRAFT = []


// Retrieve all proposals on window load
// TODO: pass more IDs for data!
window.onload = function() {
    $.ajax({
        method: "POST",
        url: "../GetAllNonDraftProposalsServlet",
        data: {
            userId: currentUserId
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
        url: "../GetAllDraftProposalsServlet",
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

// Handle button toggle on click to change button color and call filter/sort functions
function toggle(b) {
    b.value = (b.value=="ON")?"OFF":"ON"
    if (b.value == "ON") {
        b.className = "on"
        if (b.textContent == "finalized") displayResults(REC_PROPOSALS_FINAL)
        else if (b.textContent == "unfinalized") displayResults(REC_PROPOSALS_DRAFT)
    }
    else {
        b.className = "off"
    }
}

function clearFilters() {
    displayResults(REC_PROPOSALS_FINAL)
    displayResults(REC_PROPOSALS_DRAFT)
    document.querySelectorAll('.on').forEach((item) => {
           item.className = "off"
    })
}

// Parse results into card display for HTML
// Received proposals: Display title of proposal and
function displayResults(filteredResults) {
    for (i = 0; i < filteredResults.length; i++) {
        let resultsCard = `
              <div class="border">
              // TODO: Get Proposal Name from DB
              //  <h4>${filteredResults[i].proposalId}</h4>
                <h3>events</h3>
                // TODO: Get list of events from db
                  <ul><a href="#">Event 1</a></ul>
                  <ul><a href="#">Event 2</a></ul>
                <h3>date/time:</h3>
                // TODO: Get time if finalized, or print 'not finalized'
                <ul><p class="strong-result">not finalized</p></ul>
              </div>
        `
    }
}

// TODO: Sort events by start date
function sortResults() {
}


