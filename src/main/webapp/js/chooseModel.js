$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: './brands.do',
        data: '',
        dataType: 'json'
    }).done(function (data) {
        let brands = data.brands;
        addBrandsToFront(brands)
    });
});

let addBrandsToFront = function (brands) {
    let title = $("<option></option>").text("Выберите марку").prop("value", -1);
    $("#brand").append(title);
    brands.forEach(function(item) {
        let option = $("<option></option>").text(item.name).prop("value", item.id);
        $("#brand").append(option);
    });
    $("#brand").prop("disabled", false);
};

$(document).ready(function () {
    $("#brand").change(function () {
        if (this.value != -1) {
            $.ajax({
                type: 'GET',
                url: './models.do',
                data: 'brand=' + this.value,
                dataType: 'json'
            }).done(function (data) {
                let models = data.models;
                changeModels(models)
            });
        } else {
            $("#model").find('option').remove();
            $("#model").prop("disabled", true);
        }
    });
});

let changeModels = function (models) {
    $("#model").find('option').remove();
    let title = $("<option></option>").text("Выберите модель").prop("value", -1);
    $("#model").append(title);
    models.forEach(function(item) {
        let option = $("<option></option>").text(item.modelName).prop("value", item.id);
        $("#model").append(option);
    });
    $("#model").prop("disabled", false);
};

$(document).ready(function () {
    getFilteredAds();
});

$(document).ready(function () {
    $("#showFilteredAdsButton").click(function () {
        getFilteredAds();
   });
});

let getFilteredAds = function () {
    $("#cars").empty();
    let data = $('form').serialize();
    $.post('./getFilteredAds.do', data)
        .done(function (respData) {
            renderAds(respData);
        });
}

let renderAds = function (data) {
    let ads = data.ads;
    ads.forEach(function (ad) {
        let image = $("<div></div>")
            .addClass("img-1")
            .prop("style", "background-image:url(" + "./getPhoto.do?id=" + ad.photos[0] + ")")
        let link = $("<a></a>")
            .text(ad.model.brand.name + " " + ad.model.modelName + ", " + ad.year)
            .prop("href", "./ad.do?id=" + ad.id);
        let priceDiv = $("<div></div>").text(ad.price + " руб");
        let options = {
            year: 'numeric',
            month: 'long',
            day: 'numeric',
        };
        let publicationDate = new Date(ad.publicationDate);
        let publicationDiv = $("<div></div>").text(publicationDate.toLocaleString("ru", options));
        let advert = $("<div></div>")
            .addClass("col-md-3")
            .append(image)
            .append(link)
            .append(priceDiv)
            .append(publicationDiv);
        $("#cars").append(advert);
    });
}