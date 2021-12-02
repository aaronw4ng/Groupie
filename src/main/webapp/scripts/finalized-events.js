var events = []
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

const getEvents = async () => {
  console.log("Getting events....")
  const userId = sessionStorage.getItem("userId")
  const draftProposals = await getDraftProposals(userId)
  const finalizedOwnedProposals = await getFinalizedOwnedProposals(userId)
  const finalizedInvitedPropsals = await getFinalizedInvitedProposals(userId)

  console.log("Draft Proposals: " + draftProposals)
  console.log("Finalized Owned Proposals: " + finalizedOwnedProposals)
  console.log("Finalized Invited Proposals: " + finalizedInvitedPropsals)

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
