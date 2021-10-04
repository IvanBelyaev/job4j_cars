let validate = function () {
    let result = true;
    if ($("#brand").val() == -1) {
        $("#brandLabel").text("Марка (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#brandLabel").text("Марка").css("color", "#000000");
    }
    if (!$("#model").val() || $("#model").val() == -1) {
        $("#modelLabel").text("Модель (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#modelLabel").text("Модель").css("color", "#000000");
    }
    if (!$("#description").val()) {
        $("#descriptionLabel").text("Описание (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#descriptionLabel").text("Описание").css("color", "#000000");
    }
    if (!$("#year").val()) {
        $("#yearLabel").text("Год выпуска (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#yearLabel").text("Год выпуска").css("color", "#000000");
    }
    if (!$("#price").val()) {
        $("#priceLabel").text("Цена (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#priceLabel").text("Цена").css("color", "#000000");
    }
    if ($("#bodyType").val() == -1) {
        $("#bodyTypeLabel").text("Тип кузова (заполните поле)").css("color", "#740000");
        result = false;
    } else {
        $("#bodyTypeLabel").text("Тип кузова").css("color", "#000000");
    }
    return result;
};