// GLOBALS
let userID
let eventID
let proposalID

if (sessionStorage.contains("userId")) {
    userID = sessionStorage.getItem("userId")
}
if (sessionStorage.contains("eventId")) {
    eventID = sessionStorage.getItem("eventId")
}
if (sessionStorage.contains("proposalId")) {
    proposalID = sessionStorage.getItem("proposalId")
}
// END GLOBALS

// ** FILL IN EVENT INFO **
function fillEventInfo() {
    let eventNameInfo = document.querySelector("#event-name-header")
    let startDateInfo = document.querySelector("#start-date")
    let endDateInfo = document.querySelector("#end-date")
    let cityInfo = document.querySelector("#city")
    let zipcodeInfo = document.querySelector("#zipcode")
    let venueInfo = document.querySelector("#venue")
    let eventLinkInfo = document.querySelector("#event-link")

    // TODO: dynamically set values from SS
}

function handleExcitementStarClick(event) {
    console.log(event)
    if (event.srcElement.className == "excitement-star") {
        console.log(event.srcElement.dataset.star)
        fillStars(event.srcElement.dataset.star)
    }
    else {
        console.log(event.srcElement.parentElement.dataset.star)
        fillStars(event.srcElement.parentElement.dataset.star)
    }
}

function fillStars(starID) {

    let starsElements = document.querySelectorAll(".excitement-star")

    for (let i = 0; i < starsElements.length; i++) {
        if (i <= (starID - 1)) {
            starsElements[i].firstElementChild.className = "fas fa-star"
            starsElements[i].firstElementChild.className += " highlight-response"
        }
        else {
            starsElements[i].firstElementChild.className = "far fa-star"
            if (starsElements[i].firstElementChild.classList.contains("highlight-response")) {
                starsElements[i].firstElementChild.classList.remove("highlight-response")
            }
        }
    }
    sessionStorage.setItem("excitement", starID)
}

function toggleExcitementSelection(isEnabled) {
    let starsElements = document.querySelectorAll(".excitement-star")
    for (let i = 0; i < starsElements.length; i++) {
        starsElements[i].disabled = isEnabled
    }
}

function handleAvailableClick(event) {
    console.log(event.srcElement)
    let availableBtns = document.querySelectorAll(".btn-availability")
    console.log(availableBtns)
    let availability = ""
    for (let i = 0; i < availableBtns.length; i++) {
        if (availableBtns[i].firstElementChild.classList.contains("highlight-response")) {
            availableBtns[i].firstElementChild.classList.remove("highlight-response")
        }
    }
    event.srcElement.classList.add("highlight-response")
    if (event.srcElement.id == "mark-available" || event.srcElement.parentElement.id == "mark-available") {
        console.log("AVAILABLE")
        fillStars(1)
        sessionStorage.setItem("availability", "yes")
        toggleExcitementSelection(false)
    }
    else if (event.srcElement.id == "mark-maybe" || event.srcElement.parentElement.id == "mark-maybe") {
        fillStars(1)
        sessionStorage.setItem("availability", "maybe")
        toggleExcitementSelection(false)
    }
    else {        
        fillStars(0)
        sessionStorage.setItem("availability", "no")
        sessionStorage.setItem("excitement", "0")
        toggleExcitementSelection(true)
    }
}

// SEND RESPONSE
function handleSaveUserRespClick(event) {
    let userAvailability = sessionStorage.getItem("availability")
    let userExcitement = sessionStorage.getItem("excitement")
    $.ajax({
        url : "../IndicateResponse",
        data : {
            proposalId: proposalID,
            eventId: eventID,
            userId: userID,
            availability: userAvailability,
            excitement: userExcitement
        },
        success: function(result) {
            if (result == "true") {
                alert("Response saved!")
                // TODO: move user back to list of all proposal page
            }
            else {
                alert("Error saving response.")
            }
        }
    })
}

// BACK BTN PRESS
function handleBackBtnClick(event) {
    function cleanSessionStorage() {
        for (var i = 0; i < sessionStorage.length; i++) {
            if (sessionStorage.key(i) !== "username" && sessionStorage.key(i) !== "userId") {
                sessionStorage.removeItem(sessionStorage.key(i))
            }
        }
    }
    cleanSessionStorage()
    document.location.href = "#"
}