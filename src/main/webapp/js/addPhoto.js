function validate() {
    let result = true;
    if ($("#file").val() === "") {
        $("#fileLabel").text("Файл (выберите файл)").css("color", "#740000");
        result = false;
    } else {
        $("#usernameLabel").text("Файл").css("color", "#000000");
    }
    return result;
}

function getURLParameter(sParam)
{
    let sPageURL = window.location.search.substring(1);
    let sURLVariables = sPageURL.split('&');
    for (let i = 0; i < sURLVariables.length; i++)
    {
        let sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
        {
            return sParameterName[1];
        }
    }
}

function getAdvertId() {
    return getURLParameter('advertId');
}

$(document).ready(function () {
    $("#advertId").attr("value", getAdvertId());
});