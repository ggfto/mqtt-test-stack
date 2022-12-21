let interval = 0;
let autoSend = false;

$(function () {
    translate();
    toggleDisabled("#stop", true);
    toggleClass(null, ["a", "ab"], []);
});

$(".tabs ul li").click(function () {
    $(`#${$(".is-active").data("key")}`).addClass("hidden");
    $(".is-active").removeClass("is-active");
    $(this).addClass("is-active");
    $(`#${$(".is-active").data("key")}`).removeClass("hidden");
});

$("#publish").click(function () {
    toggleDisabled("#publish", true, `${translatePhrase(getLanguage(), "publishing", "Publishing")}...`);
    toggleDisabled("#stop", false);
    let repeatTime = $("#interval").val();
    if (repeatTime != undefined) interval = parseInt(repeatTime);
    readData();
});

$("#stop").click(function () {
    autoSend = false;
    interval = 0;
    toggleDisabled("#publish", false, translatePhrase(getLanguage(), "publish", "Publish"));
    toggleDisabled("#stop", true);
});

$("#template-enable").change(function () {
    if (this.checked) {
        $("#template-field").removeClass("hidden");
    } else {
        $("#template-field").addClass("hidden");
    }
});

$("#apply-template").click(function () {
    applyTemplate($("#template-data").val());
});

function toggleDisabled(elementIdentifier, state, text) {
    let element = $(`${elementIdentifier}`);
    if(text) {
        element.text(text);
    }
    element.prop("disabled", state);
}

function toggleClass(elementIdentifier, toAdd, toRemove) {
    let element = $(`${elementIdentifier}`);
    for(item of toRemove) element.removeClass(item);
    for(item of toAdd) element.addClass(item);
}

function readData() {
    if (interval > 0) {
        autoSend = true;
        setTimeout(function () {
            sendData();
            readData();
        }, interval);
    } else {
        sendData();
    }
}

function sendData() {
    postData('./publish', JSON.stringify({
        topic: $("#topic").val(),
        message: JSON.stringify(JSON.parse($("#message").val())),
        retained: $("#retained").is(":checked"),
        qos: 0
    }));
}

function postData(url, data) {
    console.log(data);
    fetch(url, {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: data
    }).then(function (response) {
        if (response.status == 200) {
            notify("success", "Sucesso!", 1000);
        } else {
            notify("danger", "Erro ao enviar request!", 5000);
        }
    });
}

function notify(type, content, duration) {
    let durationTime = 3000;
    if (duration != null) durationTime = duration;
    let color = "#3e8ed0";
    if (type == "success") {
        color = "#48c78e";
    } else if (type == "danger") {
        color = "#f14668";
    }
    Toastify({
        text: content,
        duration: durationTime,
        newWindow: true,
        close: true,
        gravity: "bottom", // `top` or `bottom`
        position: "right", // `left`, `center` or `right`
        stopOnFocus: true, // Prevents dismissing of toast on hover
        style: {
            background: color,
        },
        onClick: function () { } // Callback after click
    }).showToast();
}

function applyTemplate(dataIn) {
    let data = dataIn;
    if(data == undefined || data == "") data = template;
    console.log(JSON.stringify(data));
}