function validate() {
    let result = true;
    if ($("#phone").val() === "") {
        $("#phoneLabel").text("Номер телефона (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#phoneLabel").text("Номер телефона").css("color", "#000000");
    }
    if ($("#password").val() === "") {
        $("#passwordLabel").text("Пароль (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#passwordLabel").text("Пароль").css("color", "#000000");
    }
    return result;
}

function goToRegistration() {
   window.location.href = "reg.do";
}