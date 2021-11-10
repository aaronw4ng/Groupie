function handleSubmit(event) {
    event.preventDefault();
    const keywordInput = document.querySelector("#event-search-input").value
    const zipcodeInput = document.querySelector("#event-zip-input").value
    const cityInput = document.querySelector("#event-city-input").value

    // ********* TESTS **********
    // Test Return object
    const testObj = "[{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LA\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-11-27-2021\/event\/0A005B36DF5C3326\",\"startDateTime\":\"2021-11-28T03:30:00Z\",\"venues\":[{\"name\":\"SoFi Stadium\",\"address\":\"1001 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LA\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-12-02-2021\/event\/0A005B36E09A3388\",\"startDateTime\":\"2021-12-03T03:30:00Z\",\"venues\":[{\"name\":\"SoFi Stadium\",\"address\":\"1001 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LA\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-11-28-2021\/event\/0A005B36DFDE334D\",\"startDateTime\":\"2021-11-29T03:30:00Z\",\"venues\":[{\"name\":\"SoFi Stadium\",\"address\":\"1001 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LA\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-12-01-2021\/event\/0A005B36E0493377\",\"startDateTime\":\"2021-12-02T03:30:00Z\",\"venues\":[{\"name\":\"SoFi Stadium\",\"address\":\"1001 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LIVE PLAY\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-11-27-2021\/event\/0A005B52FF3F6615\",\"startDateTime\":\"2021-11-28T03:30:00Z\",\"venues\":[{\"name\":\"YouTube Theater\",\"address\":\"1011 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LIVE PLAY\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-12-02-2021\/event\/0A005B5200C1666D\",\"startDateTime\":\"2021-12-03T03:30:00Z\",\"venues\":[{\"name\":\"YouTube Theater\",\"address\":\"1011 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LIVE PLAY\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-11-28-2021\/event\/0A005B52002E664B\",\"startDateTime\":\"2021-11-29T03:30:00Z\",\"venues\":[{\"name\":\"YouTube Theater\",\"address\":\"1011 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]},{\"eventName\":\"BTS PERMISSION TO DANCE ON STAGE - LIVE PLAY\",\"url\":\"https:\/\/www.ticketmaster.com\/bts-permission-to-dance-on-stage-inglewood-california-12-01-2021\/event\/0A005B52007D6661\",\"startDateTime\":\"2021-12-02T03:30:00Z\",\"venues\":[{\"name\":\"YouTube Theater\",\"address\":\"1011 S. Stadium Dr\",\"city\":\"Inglewood\",\"state\":\"CA\",\"country\":\"US\"}]}]"
    sessionStorage.setItem("events", testObj)
    let json = JSON.parse(testObj)
    // Container of results
    let resultsContainer = document.querySelector(".event-results-container")
    // Result count text
    let resultsCount = document.querySelector("#event-search-result-count")
    // Upate Result count text
    let countString = "Showing " + json.length + " result(s)."
    resultsCount.innerHTML = countString

    // Add results cards to Container
    for (let i = 0; i < json.length; i++) {
        console.log(json[i].url)
        let resultCardString = `
        <div class="event-result-card">
            <div class="event-info">
                <h1 class="result-title">${json[i].eventName}</h1>
                <p class="result-date-range">${json[i].startDateTime}</p>
                <a class="result-url" href="${json[i].url}" target="_blank">ticketmaster page</a>
            </div>
            <button onclick="handleResultSelection(${i})">add event</button>
        </div>
        `;
        resultsContainer.innerHTML += resultCardString

    }
    // ********** END TESTS ***********

    // TODO: AJAX call to servlet once servlet made

    // $.ajax({
    //     method: "POST",
    //     url: "../searchEvents",
    //     data: {
    //         keyword: keywordInput,
    //         zipCode: zipcodeInput,
    //         city: cityInput
    //     },

    //     success: function(result) {
    //         if (result == "No results found!") {
    //             // Display in Results container and return
    //         }
    //         // Else, parse through results array and display all event results
    //         console.log(result)
    //     }
    // })
}

function handleResultSelection(index) {
    console.log("Event index: " + index)
    let eventsArr = sessionStorage.getItem("events")
    let json = JSON.parse(eventsArr)
    let selectedEvent = json[index]
    console.log(selectedEvent)
}

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