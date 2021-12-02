// User access
if (!sessionStorage.getItem("username")) {
    document.location.href = "../index.jsp"
}

// GLOBALS
let userID
let eventJSON
let eventID
let proposalJSON
let proposalID

if (sessionStorage.getItem("userId")) {
    userID = sessionStorage.getItem("userId")
}
if (sessionStorage.getItem("selectedEvent")) {
    eventJSON = JSON.parse(sessionStorage.getItem("selectedEvent"))
    eventID = eventJSON.eventId
}
if (sessionStorage.getItem("selectedProposal")) {
    proposalJSON = JSON.parse(sessionStorage.getItem("selectedProposal"))
    proposalID = proposalJSON.proposalId
}
// END GLOBALS

// ** FILL IN EVENT INFO **
function fillEventInfo() {
    let eventNameInfo = document.querySelector("#event-name-header")
    let startDateInfo = document.querySelector("#start-date")
    let endDateInfo = document.querySelector("#end-date")
    let cityInfo = document.querySelector("#city")
    let addressInfo = document.querySelector("#address")
    let venueInfo = document.querySelector("#venue")
    let eventLinkInfo = document.querySelector("#event-link")

    // TODO: dynamically set values from SS
    eventNameInfo.innerHTML = eventJSON.eventName
    startDateInfo.innerHTML = eventJSON.startDateTime.substring(0,10)
    endDateInfo.innerHTML = "N/A"
    cityInfo.innerHTML = eventJSON.venues[0].city
    addressInfo.innerHTML = eventJSON.venues[0].address
    venueInfo.innerHTML = eventJSON.venues[0].name
    eventLinkInfo.setAttribute("href", eventJSON.url)


}

let responseContainer = document.querySelector("#responses-container")

function fillUserInfo() {
    responseContainer.innerHTML = ""
    let userResponses = eventJSON.responses 
    console.log(userResponses)
    for (var i = userResponses.length-1; i >= 0 ; i--) {
        console.log(i)
        let response = userResponses[i]
        console.log("RESPONSE UID: " + response.userId + " USER UID: " + userID)
        if (parseInt(response.userId) === parseInt(userID)) {
            let responseString = `
            <div id="user-response-card">
            <div class="response-card-col">
            <h1 id="username-header"><i class="fas fa-user-edit"></i></h1>
            <h1 id="username">${response.userName}</h1>
            </div>

            <hr id="response-card-split">
                
            <div class="response-card-col">
            <p class="user-response-prompt">are you available?</p>
            <div class="availability-container">
                <button id="mark-available" class="btn-availability" onclick="handleAvailableClick(event)"><i class="fas fa-check-circle"></i></button>
                <button id="mark-maybe" class="btn-availability" onclick="handleAvailableClick(event)"><i class="fas fa-question-circle"></i></button>
                <button id="mark-unavailable" class="btn-availability" onclick="handleAvailableClick(event)"><i class="fas fa-times-circle"></i></button>
            </div>
            </div>

            <div class="response-card-col">
            <p class="user-response-prompt">rate your excitement</p>
            <div class="excitement-container">
                <button data-star="1" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
                <button data-star="2" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
                <button data-star="3" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
                <button data-star="4" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
                <button data-star="5" class="excitement-star" onclick="handleExcitementStarClick(event)"><i class="far fa-star"></i></button>
            </div>
            </div>

            <div class="save-container">
            <button id="btn-save-user-resp" onclick="handleSaveUserRespClick(event)">save response</button>
            </div>
            

        </div>
            `;
            responseContainer.innerHTML += responseString

        }
        else {
            // Check if user has filled a response
            if (response.isFilledOut) {
                let availability = response.availability
                let availabilityIcon = ``
                switch(availability) {
                    case "yes" :
                        availabilityIcon = `<i class="fas fa-check-circle response-available"></i>`
                        break
                    case "no" :
                        availabilityIcon = `<i class="fas fa-times-circle response-unavailable"></i>`
                        break
                    default :
                        availabilityIcon = `<i class="fas fa-question-circle response-maybe"></i>`
                        break
                }
                // Get user excitement
                let userExcitement = parseInt(response.excitement)
                let excitementString = ``
                for (var j = 1; j <= 5; j++) {
                    if (j <= userExcitement) {
                        excitementString += `<i class="fas fa-star other-excitement-star"></i>`
                    }
                    else {
                        excitementString += `<i class="far fa-star other-excitement-star"></i>`
                    }
                }
                let responseString = `
                <div class="other-response-card">
                    <div class="response-card-col">
                        <h1 class="other-username" id="other-user-1">${response.userName}</h1>
                    </div>
                    <div class="response-card-col">
                        <p class="user-response-prompt">available?</p>
                        ${availabilityIcon}
                    </div>
                    <div class="response-card-col">
                        <p class="user-response-prompt">excitement?</p>
                        <div class="excitement-container">
                            ${excitementString}
                        </div>
                    </div>
                </div>
                `;
                responseContainer.innerHTML += responseString
            }
            else {
                let responseString = `
                <div class="other-response-card">
                    <div class="response-card-col">
                    <h1 class="other-username" id="other-user-1">${response.userName}</h1>
                    <p>Awaiting response.</p>
                    </div>
                </div>
                `;
                responseContainer.innerHTML += responseString
            }
        }
    }
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
        method: "POST",
        url : "../indicateResponse",
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
    if (sessionStorage.getItem("selectedEvent")) {
        sessionStorage.removeItem("selectedEvent")
    }
    document.location.href = "./proposal-details.jsp"
}

document.addEventListener("DOMContentLoaded", function () {
    fillEventInfo()
    fillUserInfo()
    startAutoLogoutRoutine()
})

