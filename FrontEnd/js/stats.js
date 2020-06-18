$('#field_select').on('change', function () {
    if (document.getElementById('field_select').value == "time") {
        document.getElementById('parametri').type = 'date';
        if (document.getElementById('type_select').value == "$bt") {
            document.getElementById('parametri2').type = 'date';
        }
    }
    if (document.getElementById('field_select').value == "likes") {
        document.getElementById('parametri').type = 'text';
        if (document.getElementById('type_select').value == "$bt") {
            document.getElementById('parametri2').type = 'text';
        }
    }
    if (document.getElementById('field_select').value == "retweets") {
        document.getElementById('parametri').type = 'text';
        if (document.getElementById('type_select').value == "$bt") {
            document.getElementById('parametri2').type = 'text';
        }
    }

});
$('#type_select').on('change', function () {
    if (document.getElementById('type_select').value == "$bt") {
        document.getElementById('parametri').placeholder = 'Parametro 1';
        if (document.getElementById('parametri').type == 'date') {
            document.getElementById('parametri2').type = 'date';
        }
        if (document.getElementById('parametri').type == 'text') {
            document.getElementById('parametri2').type = 'text';
        }
    }
    if (document.getElementById('type_select').value != "$bt") {
        document.getElementById('parametri2').type = 'hidden';
    }


});

const form = document.getElementById("form-filtri");

// ...and take over its submit event.
form.addEventListener("submit", function(event) {
    event.preventDefault();

    var parametro = document.getElementById('parametri').value;
    var parametro1 = document.getElementById('parametri').value;
    var parametro2 = document.getElementById('parametri2').value;
    if (document.getElementById('parametri').type == 'date') {
        parametro1 = `"${Intl.DateTimeFormat('en', { year: 'numeric', month: 'short', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: false, timeZone: 'Europe/London' }).format(Date.parse(document.getElementById('parametri').value))}"`;
        if (document.getElementById('parametri2').type == 'date') {
            parametro2 = `"${Intl.DateTimeFormat('en', { year: 'numeric', month: 'short', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: false, timeZone: 'Europe/London' }).format(Date.parse(document.getElementById('parametri2').value))}"`;
        }
    }
    if (document.getElementById('type_select').value == "$bt") {
        parametro = "[" + parametro1 + ", " + parametro2 + "]";
    }
    if (document.getElementById('type_select').value != "$bt" && document.getElementById('parametri').type == 'date') {
        parametro = `${parametro1}`;
    }

    var ogg = `{"filter_field":"${document.getElementById('field_select').value}", "filter_type":"${document.getElementById('type_select').value}", "parameters":${parametro}}`;
    if (document.getElementById('field_select').value == "data") {
        ogg = '{"filter_field" : "data", "filter_type" : "$gt", "parameters" : 10}';
    }
    var oggetto = JSON.parse(ogg);

    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/data/stats";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#filteredData tbody tr").remove()
            var json = JSON.parse(xhr.responseText);
            var items = [];

            $.each(json, function(key, val) {
                items.push("<tr>");

                items.push("<td id=''" + key + "''>" + key + "</td>");
                items.push("<td id=''" + key + "''>" + val + "</td>");
                items.push("</tr>");
            });
            $("<tbody/>", { html: items.join("") }).appendTo("table");
        }
    };
    var invia = JSON.stringify(oggetto);
    xhr.send(invia);
});