function handleSubmit(event) {
    event.preventDefault();
    const keywordInput = document.querySelector("#event-search-input").value
    const zipcodeInput = document.querySelector("#event-zip-input").value
    const cityInput = document.querySelector("#event-city-input").value

    $.ajax({
        method: "POST",
        url: "../searchEvents",
        data: {
            keyword: keywordInput,
            zipCode: zipcodeInput,
            city: cityInput
        },

        success: function(result) {
            if (result == "No results found!") {
                // Display in Results container and return
            }
            // Else, parse through results array and display all event results
            console.log(result)
        }
    })
}