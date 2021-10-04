$(document).ready(function () {
    $.ajax({
        type: 'POST',
        url: './lk.do',
        data: '',
        dataType: 'json'
    }).done(function (data) {
        let ads = data.ads;
        ads.forEach(function (ad) {
            let title = $("<a></a>")
                .prop("href", "./ad.do?id=" + ad.id)
                .addClass("adTitle")
                .text(ad.model.brand.name + " " + ad.model.modelName + ", " + ad.year);
            let description = $("<div></div>")
                .addClass("col-md-8 align-self-center")
                .append(title)
                .append("<p>" + ad.description + "</p>");
            let status = $("<div></div>")
                .addClass("col-md-2 align-self-center");
            let image = $("<img>")
                .prop("src", "./getPhoto.do?id=" + ad.photos[0])
                .addClass("col-md-12");
            let imageRef = $("<a></a>")
                .prop("href", "./ad.do?id=" + ad.id)
                .addClass("col-md-2 p-1")
                .append(image);
            let row = $("<div></div>")
                .addClass("row")
                .append(imageRef)
                .append(description)
                .append(status)
                .append("<br>");
            if (ad.status) {
                status.text("Продано");
                row.addClass("sold");
            } else {
                status.text("Продаётся");
                row.addClass("unsold");
            }
            $("#adTableTitle").after(row);
        });
    });
});

let showAll = function () {
    $(".sold").show();
    $(".unsold").show();
    $("#showAllButton").removeClass("btn-outline-primary").addClass("btn-primary");
    $("#showOnlySoldButton").removeClass("btn-primary").addClass("btn-outline-primary");
    $("#showOnlyUnsoldButton").removeClass("btn-primary").addClass("btn-outline-primary");
};

let showOnlySold = function () {
    $(".sold").show();
    $(".unsold").hide();
    $("#showAllButton").removeClass("btn-primary").addClass("btn-outline-primary");
    $("#showOnlySoldButton").removeClass("btn-outline-primary").addClass("btn-primary");
    $("#showOnlyUnsoldButton").removeClass("btn-primary").addClass("btn-outline-primary");
};

let showOnlyUnsold = function () {
    $(".sold").hide();
    $(".unsold").show();
    $("#showAllButton").removeClass("btn-primary").addClass("btn-outline-primary");
    $("#showOnlySoldButton").removeClass("btn-primary").addClass("btn-outline-primary");
    $("#showOnlyUnsoldButton").removeClass("btn-outline-primary").addClass("btn-primary");
};
