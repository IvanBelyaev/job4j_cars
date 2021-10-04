function validate() {
    let result = true;
    if ($("#username").val() === "") {
        $("#usernameLabel").text("Имя пользователя (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#usernameLabel").text("Имя пользователя").css("color", "#000000");
    }
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

function goToLogin() {
    window.location.href = "login.do";
}
