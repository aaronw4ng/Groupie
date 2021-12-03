// Verify user
let currentUserId = ""
let currentUsername = ""

if (sessionStorage.getItem("username")) {
  currentUsername = sessionStorage.getItem("username")
}
else {
    document.location.href = "../index.jsp"
}

// Store finalized & unfinaized proposals in separate arrays
let REC_PROPOSALS_FINAL = []
let REC_PROPOSALS_DRAFT = []

let sentProposalResultsContainer = document.querySelector(
"#sent-results-container"
)
let receivedProposalResultsContainer = document.querySelector(
"#received-results-container"
)
let draftProposalResultsContainer = document.querySelector(
"#draft-results-container"
)

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
    startAutoLogoutRoutine()

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

function cleanSessionStorage() {
  for (var i = 0; i < sessionStorage.length; i++) {
      if (sessionStorage.key(i) !== "username" && sessionStorage.key(i) !== "userId") {
          sessionStorage.removeItem(sessionStorage.key(i))
      }
  }
}

const getEvents = async () => {
    
        sentProposalResultsContainer.innerHTML = ""
        receivedProposalResultsContainer.innerHTML = ""
        draftProposalResultsContainer.innerHTML = ""

  console.log("Getting events....")
  const userId = sessionStorage.getItem("userId")
  let draftProposals = await getDraftProposals(userId)
  let finalizedOwnedProposals = await getFinalizedOwnedProposals(userId)
  let finalizedInvitedProposals = await getFinalizedInvitedProposals(userId)


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
      console.log(draftProposals)
      displayResults(JSON.parse(draftProposals), draftProposalResultsContainer, "draft")
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
      console.log(finalizedOwnedProposals)
      displayResults(JSON.parse(finalizedOwnedProposals), sentProposalResultsContainer, "sent")
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
      console.log(finalizedInvitedProposals)
      displayResults(JSON.parse(finalizedInvitedProposals), receivedProposalResultsContainer, "received")
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


function displayResults(filteredResults, container, proposalType) {
  var i = 1
  filteredResults.forEach(result => {
    console.log(JSON.stringify(result))
    let resultsCard = `
        <div id="${proposalType}-container-${i}" data-json='${JSON.stringify(
          result
        )}' class="proposal-card" onclick="handleProposalResultClick(event, '${proposalType}')">
          <h1 class="proposal-title" id="proposal-card-${i}">${result.title}</h1>
        </div>
        `
    container.innerHTML += resultsCard
    i++
  })
}

function handleProposalResultClick(event, proposalType) {
  let proposalJSON = ""
  // clear
  if (sessionStorage.getItem("selectedProposal")) {
    sessionStorage.removeItem("selectedProposal")
  }
  if (sessionStorage.getItem("selected")) {
    sessionStorage.removeItem("selected")
  }
  if (sessionStorage.getItem("users")) {
    sessionStorage.removeItem("users")
  }
  if (sessionStorage.getItem("proposalName")) {
    sessionStorage.removeItem("proposalName")
  }
  if (sessionStorage.getItem("proposalId")) {
    sessionStorage.removeItem("proposalId")
  }

  if (event.srcElement.className === "proposal-title") {
    console.log(event.srcElement.parentElement.dataset.json)
    proposalJSON = event.srcElement.parentElement.dataset.json
  } else {
    console.log(event.srcElement.dataset.json)
    proposalJSON = event.srcElement.dataset.json
  }
  sessionStorage.setItem("selectedProposal", proposalJSON)
  if (proposalType === "draft") {
    let propID = JSON.parse(proposalJSON).proposalId
    let propName = JSON.parse(proposalJSON).title
    let selectedEvents = JSON.parse(proposalJSON).events
    let selectedInvitees = JSON.parse(proposalJSON).invitees 
    selectedInvitees = selectedInvitees.filter(user => {
      console.log("Username = " + user.username + " Curr= " + currentUsername)
      return (user.username !== currentUsername)
    })
    selectedInvitees = selectedInvitees.map(user => user.username)
    console.log(selectedInvitees)
    selectedEvents = selectedEvents.map(event => JSON.stringify(event))
    console.log(JSON.stringify(selectedEvents))
    sessionStorage.setItem("selected", JSON.stringify(selectedEvents))
    sessionStorage.setItem("users", JSON.stringify(selectedInvitees))
    sessionStorage.setItem("proposalId", propID)
    sessionStorage.setItem("proposalName", propName)
    document.location.href = "./create-proposal.jsp"
  }
  else {
    console.log(JSON.parse(proposalJSON).isFinalized)
    if (JSON.parse(proposalJSON).isFinalized) {
      // direct to finalized page
      document.location.href = "./finalized-proposal.jsp"
    }
    else {
      document.location.href = "./proposal-details.jsp"
    }

  }
}

function isFinalized(proposal) {}

function sortResults() {}
