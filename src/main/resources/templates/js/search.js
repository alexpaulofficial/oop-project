window.addEventListener("load", function() {
    function sendData() {
        const XHR = new XMLHttpRequest();

        // Bind the FormData object and the form element
        const queryString = new String(document.getElementById('search').value);
        // Define what happens on successful data submission

        // Set up our request
        XHR.open("POST", "http://localhost:8080/data/twitter", true);

        XHR.onreadystatechange = function() {
                if (XHR.readyState == 4 && XHR.status == 200) {
                    alert("Tweet scaricati correttamente!");
                    location.reload();
                } else if (XHR.readyState == 4) {
                    alert(XHR.responseText);
                    location.reload();
                }
            }
            // Define what happens in case of error

        // The data sent is what the user provided in the form

        XHR.send(queryString);
    }

    // Access the form element...
    const form = document.getElementById("searchForm");

    // ...and take over its submit event.
    form.addEventListener("submit", function(event) {
        event.preventDefault();

        sendData();

    });
});