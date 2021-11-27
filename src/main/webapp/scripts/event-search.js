// User access
if (!sessionStorage.getItem("username")) {
    document.location.href = "../index.jsp"
}

if (sessionStorage.getItem("selected") == null) {
    sessionStorage.setItem("selected", "[]")
}

function handleSubmit(event) {
    event.preventDefault();
    const keywordInput = document.querySelector("#event-search-input").value
    const zipcodeInput = document.querySelector("#event-zip-input").value
    const cityInput = document.querySelector("#event-city-input").value
    const genreInput = document.querySelector("#event-genre-input").value
    let startDateInput = document.querySelector("#start").value
    let endDateInput = document.querySelector("#end").value

    console.log("KEYWORD:", keywordInput, "ZIPCODE:", zipcodeInput, "CITY:", cityInput, "START:", startDateInput, "END:", endDateInput)
    
    // Date Formatting for Servlet
    startDateInput = formatInputDate(startDateInput);
    endDateInput = formatInputDate(endDateInput);

    // ********* TESTS **********
    // Test Return object
    // const testObj = "[{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LA\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-11-27-2021\/event\/0A005B36DF5C3326\",\"startDateTime\":\"2021-11-28T03:30:00Z\",\"venues\":[{\"name\":\"SoFi Stadium\",\"address\":\"1001 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LA\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-12-02-2021\/event\/0A005B36E09A3388\",\"startDateTime\":\"2021-12-03T03:30:00Z\",\"venues\":[{\"name\":\"SoFi Stadium\",\"address\":\"1001 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LA\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-11-28-2021\/event\/0A005B36DFDE334D\",\"startDateTime\":\"2021-11-29T03:30:00Z\",\"venues\":[{\"name\":\"SoFi Stadium\",\"address\":\"1001 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LA\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-12-01-2021\/event\/0A005B36E0493377\",\"startDateTime\":\"2021-12-02T03:30:00Z\",\"venues\":[{\"name\":\"SoFi Stadium\",\"address\":\"1001 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LIVE PLAY\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-11-27-2021\/event\/0A005B52FF3F6615\",\"startDateTime\":\"2021-11-28T03:30:00Z\",\"venues\":[{\"name\":\"YouTube Theater\",\"address\":\"1011 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LIVE PLAY\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-12-02-2021\/event\/0A005B5200C1666D\",\"startDateTime\":\"2021-12-03T03:30:00Z\",\"venues\":[{\"name\":\"YouTube Theater\",\"address\":\"1011 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LIVE PLAY\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-11-28-2021\/event\/0A005B52002E664B\",\"startDateTime\":\"2021-11-29T03:30:00Z\",\"venues\":[{\"name\":\"YouTube Theater\",\"address\":\"1011 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LIVE PLAY\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-12-01-2021\/event\/0A005B52007D6661\",\"startDateTime\":\"2021-12-02T03:30:00Z\",\"venues\":[{\"name\":\"YouTube Theater\",\"address\":\"1011 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]}]"
    // sessionStorage.setItem("events", testObj)
    // let json = JSON.parse(testObj)
    // ********** END TESTS ***********

    // Container of results
    let resultsContainer = document.querySelector(".event-results-container")
    // Result count text
    let resultsCount = document.querySelector("#event-search-result-count")

    // Clear Results Container and Results Count container
    clearContainer(resultsContainer)
    resultsCount.innerHTML = ""


    // AJAX call to SearchEventsServlet

    $.ajax({
        method: "POST",
        url: "../searchEvents",
        data: {
            keyword: keywordInput,
            zipCode: zipcodeInput,
            city: cityInput,
            startDate: startDateInput,
            endDate: endDateInput,
            genre: genreInput
        },

        success: function(result) {
            console.log(result)
            if (result == "No results found!") {
                resultsContainer.innerHTML += `<p>No events found.</p>`
            }
            else {
                // Store response json string in session storage
                sessionStorage.setItem("events", result)
                let json = JSON.parse(result)
                // Upate Result count text (OPTIONAL)
                let countString = "Showing " + json.length + " result(s)."
                resultsCount.innerHTML = countString
                // Parse JSON & add results cards to Container
                for (let i = 0; i < json.length; i++) {
                    console.log(json[i])
                    let resultCardString = `
                    <div class="event-result-card">
                        <p class="result-title" id="result-title-id-${i}">${json[i].eventName}</p>
                        <hr>
                        <p class="event-info-title" id="event-info-title-id-${i}"><i class="fas fa-info-circle"></i> event info</p>
                        <div class="event-info">   
                            <div>
                                <p class="result-header">start date</p>
                                <p class="result-date-range" id="start-date-id-${i}">${json[i].startDateTime.substring(0,10)}</p>
                            </div>
                            <div>
                                <p class="result-header">event link</p>
                                <a class="result-url" id="event-link-${i}" href="${json[i].url}" target="_blank">ticketmaster page</a>
                            </div>
                            <div>
                                <p class="result-header">venue</p>
                                <p class="result-venue" id="venue-id-${i}">${json[i].venues[0].name}</p>
                            </div>
                            <div>
                                <p class="result-header">city</p>
                                <p class="result-city" id="city-id-${i}">${json[i].venues[0].name}</p>
                            </div>
                            <div>
                                <p class="result-header">genre</p>
                                <p class="result-genre" id="genre-id-${i}">${json[i].venues[0].name}</p>
                            </div>
                            <div>
                                <p class="result-header">zipcode</p>
                                <p class="result-zipcode" id="zipcode-id-${i}">${json[i].venues[0].name}</p>
                            </div>
                            <hr>
                        </div>
                        <button class="btn-add-result" id="btn-add-result-id-${i}" onclick="handleResultSelection(${i})">add event</button>
                    </div>
                    `;
                    resultsContainer.innerHTML += resultCardString

                }
            }
        }
    })
}

// Function to format data for servlet
function formatInputDate(dateInput) {
    if (dateInput != "") {
        let dateArr = dateInput.split("-")
        let yearString = dateArr[0]
        dateInput = yearString + "-" + dateArr[1] + "-" + dateArr[2] + "T00:00:00Z"
    }
    return dateInput;
}

function clearContainer(container) {
    // Make sure Result Count container stays inside div
    while(container.childElementCount > 1) {
        container.removeChild(container.lastChild)
    }
}

function handleResultSelection(index) {
    console.log("Event index: " + index)
    let eventsArr = sessionStorage.getItem("events")
    let json = JSON.parse(eventsArr)
    let selectedEvent = json[index]
    // back into JSON
    selectedEvent = JSON.stringify(selectedEvent)
    // Add selected event to selected array in session storage
    let selectedArr = JSON.parse(sessionStorage.getItem("selected"))
    selectedArr.push(selectedEvent)
    sessionStorage.setItem("selected", JSON.stringify(selectedArr))
    console.log(selectedArr)

    document.location.href = "./create-proposal.jsp"
}

// Search Filters

var button = document.getElementById('toggleFilters');
button.onclick = function() {
    var div = document.getElementById('search-filters');
    if (div.style.display !== 'none') {
        div.style.display = 'none';
    }
    else {
        div.style.display = 'block';
    }
};

function setFilters() {
    var div = document.getElementById('search-filters');
    div.style.display = 'none'
}

function setMaxEndDate(event) {
    let currDate = new Date(event.target.value)
    var newDate = new Date(currDate.setMonth(currDate.getMonth()+6));
    newDate = newDate.toISOString().substr(0,10)
    console.log(newDate)
    document.querySelector("#end").setAttribute("min", event.target.value)
    document.querySelector("#end").setAttribute("max", newDate)

}

setFilters()