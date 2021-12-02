// Verify user
let currentUserId = ""

if (sessionStorage.getItem("username")) {
  const currentUsername = sessionStorage.getItem("username")
}
else {
    document.location.href = "../index.jsp"
}

// Store finalized & unfinaized proposals in separate arrays
let REC_PROPOSALS_FINAL = []
let REC_PROPOSALS_DRAFT = []

document.addEventListener("DOMContentLoaded", async function () {
    var calendarEl = document.getElementById("calendar")
    await getEvents()
    var calendar = new FullCalendar.Calendar(calendarEl, {
      headerToolbar: {
        left: "title",
        center: "",
        right: "prev,next",
      },
      initialDate: "2021-12-01",
      navLinks: false, // can click day/week names to navigate views
      businessHours: false, // display business hours
      editable: false,
      selectable: false,
      events: events,
    })
  
    calendar.render()
  })

// Retrieve all proposals on window load

// window.onload = function() {
//     $.ajax({
//         method: "POST",
//         url: "../getAllNonDraftProposals",
//         data: {
//             userId: currentUserId,
//             isOwner: true
//         },
//         success: function(result) {
//         if (result) {
//             console.log("found props successfully")
//             REC_PROPOSALS_FINAL = JSON.parse(result)
//             console.log(REC_PROPOSALS_FINAL)
//             displayResults(REC_PROPOSALS_FINAL)
//         }
//         else {
//             console.log("error finding props")
//         }
//       }
//     }),
//     $.ajax({
//         method: "POST",
//         url: "../getAllDraftProposals",
//         data: {
//             userId: currentUserId
//         },
//         success: function(result) {
//         if (result) {
//             console.log("found props successfully")
//             REC_PROPOSALS_DRAFT = JSON.parse(result)
//             // displayResults(REC_PROPOSALS_DRAFT)
//         }
//         else {
//             console.log("error finding props")
//         }
//       }
//     })
// }
let proposalResultsContainer = document.querySelector(
  "#proposal-results-container"
  )
const getEvents = async () => {
    
        proposalResultsContainer.innerHTML = ""

  console.log("Getting events....")
  const userId = sessionStorage.getItem("userId")
  const draftProposals = await getDraftProposals(userId)
  const finalizedOwnedProposals = await getFinalizedOwnedProposals(userId)
  const finalizedInvitedProposals = await getFinalizedInvitedProposals(userId)

  console.log("Draft Proposals: " + draftProposals)
  console.log("Finalized Owned Proposals: " + finalizedOwnedProposals)
  console.log("Finalized Invited Proposals: " + finalizedInvitedProposals)

  events = [
    {
      title: "Event",
      start: "2021-12-04T21:00:00",
    },
    {
      title: "Event",
      start: "2021-12-11T21:30:00",
    },
    {
      title: "Event",
      start: "2021-12-14T21:30:00",
    },
    {
      title: "Event",
      start: "2021-12-19T21:30:00",
    },
  ]
}

const getDraftProposals = userId => {
  let draftProposals = ""
  $.ajax({
    method: "POST",
    data: {
      userId: userId,
    },
    url: "../getAllDraftProposals",
    success: function (result) {
      draftProposals = result
      // displayResults(JSON.parse(draftProposals))
    },
  })
  return draftProposals
}

const getFinalizedOwnedProposals = userId => {
  let finalizedOwnedProposals = ""
  $.ajax({
    method: "POST",
    data: {
      userId: userId,
      isOwner: true,
    },
    url: "../getAllNonDraftProposals",
    success: function (result) {
      finalizedOwnedProposals = result
      displayResults(JSON.parse(finalizedOwnedProposals))
    },
  })
  return finalizedOwnedProposals
}

const getFinalizedInvitedProposals = userId => {
  let finalizedInvitedProposals = ""
  $.ajax({
    method: "POST",
    data: {
      userId: userId,
      isOwner: false,
    },
    url: "../getAllNonDraftProposals",
    success: function (result) {
      finalizedInvitedProposals = result
    },
  })
  return finalizedInvitedProposals
}

function handleProposalOptionClick(event) {
  console.log(event.value)
}

// Handle button toggle on click to change button color and call filter/sort functions
// function toggle(b) {
//   b.value = b.value == "ON" ? "OFF" : "ON"
//   if (b.value == "ON") {
//     b.className = "on"
//     //if (b.textContent == "finalized") filterResults(final, REC_PROPOSALS_FINAL)
//     //else if (b.textContent == "unfinalized")
//   } else {
//     b.className = "off"
//   }
// }

function clearFilters() {
  //displayResults(REC_PROPOSALS_FINAL)
  document.querySelectorAll(".on").forEach(item => {
    item.className = "off"
  })
}

function filterResults(type, proposalId) {
  // Check column is_finalized and remove from list if is_finalized != input type
}

// Parse results into card display for HTML
// Received proposals: Display title of proposal and


function displayResults(filteredResults) {
  let i = 0
  filteredResults.forEach(result => {
    console.log(JSON.stringify(result))
    let resultsCard = `
        <div data-json='${JSON.stringify(
          result
        )}' class="proposal-card" onclick="handleProposalResultClick(event)">
          <h1 class="proposal-title" id="proposal-card-${i}">${result.title}</h1>
        </div>
        `
    proposalResultsContainer.innerHTML += resultsCard
    i++
  })
}

function handleProposalResultClick(event) {
  let proposalJSON = ""
  if (event.srcElement.className === "proposal-title") {
    console.log(event.srcElement.parentElement.dataset.json)
    proposalJSON = event.srcElement.parentElement.dataset.json
  } else {
    console.log(event.srcElement.dataset.json)
    proposalJSON = event.srcElement.dataset.json
  }
  sessionStorage.setItem("selectedProposal", proposalJSON)
  document.location.href = "./proposal-details.jsp"
}

function isFinalized(proposal) {}

function sortResults() {}
