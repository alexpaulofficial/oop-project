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
form.addEventListener("submit", function (event) {
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
    var oggetto = JSON.parse(ogg);

    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/data/filter";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            $("#filteredData tbody tr").remove()
            var json = JSON.parse(xhr.responseText);
            var items = [];

            async function GetData() {
                xlabels = [];
                ylikes = [];
                if (document.getElementById('graph_sel').value == "like") {
                    $.each(json, function (key, val) {
                        xlabels.push(Intl.DateTimeFormat('it', { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: false, timeZone: 'UTC' }).format(Date.parse(val.created_at)));
                        ylikes.push(val.favorite_count);
                    });
                    xlabels.reverse();
                    ylikes.reverse();
                }

                if (document.getElementById('graph_sel').value == "retweet") {
                    $.each(json, function (key, val) {
                        xlabels.push(Intl.DateTimeFormat('it', { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: false, timeZone: 'UTC' }).format(Date.parse(val.created_at)));
                        ylikes.push(val.retweet_count);
                    });
                    xlabels.reverse();
                    ylikes.reverse();
                }

            }

            $.each(json, function (key, val) {
                items.push("<tr>");
                items.push("<td id=''" + key + "''>" + val.id + "</td>");
                items.push("<td id=''" + key + "''>" + val.text + "</td>");
                items.push("<td id=''" + key + "''>" + Intl.DateTimeFormat('it', { year: 'numeric', month: 'numeric', day: 'numeric', hour: 'numeric', minute: 'numeric', second: 'numeric', hour12: false }).format(Date.parse(val.created_at)) + "</td>");
                items.push("<td id=''" + key + "''>" + val.favorite_count + "</td>");
                items.push("<td id=''" + key + "''>" + val.retweet_count + "</td>");
                items.push("</tr>");
            });
            $("<tbody/>", { html: items.join("") }).appendTo("table");

            var xlabels = [];
            var ylikes = [];
            var type = new String(document.getElementById('graph_sel').value);
            $('#graph_sel').on('change', function () {
                console.log(document.getElementById('graph_sel').value);
                type = document.getElementById('graph_sel').value;
                chartIt();

            });
            $.ajaxSetup({
                async: false
            });

            chartIt();
            async function chartIt() {
                // Graphs
                await GetData();
                var ctx = document.getElementById('myChart')
                // eslint-disable-next-line no-unused-vars
                var myChart = new Chart(ctx, {
                    type: 'line',
                    data: {
                        labels: xlabels,
                        datasets: [{
                            data: ylikes,
                            lineTension: 0,
                            backgroundColor: 'rgba(255,64,0, 0.2)',
                            borderColor: '#ff4000',
                            borderWidth: 1.5,
                            pointBackgroundColor: '#ff4000',
                            pointBorderColor: 'rgba(0,0,0, 0.7)'
                        }]
                    },
                    options: {
                        scales: {
                            yAxes: [{
                                ticks: {
                                    beginAtZero: true
                                }
                            }]
                        },
                        legend: {
                            display: false
                        }
                    }
                })
            }
        }
    };
    var invia = JSON.stringify(oggetto);
    xhr.send(invia);
});