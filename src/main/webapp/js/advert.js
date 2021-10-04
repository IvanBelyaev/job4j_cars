$(document).ready(function () {
    $("#controlButtons").hide();
});

function getUserId() {
    let userId = -1;
    $.ajax({
        type: 'GET',
        url: './auth.do',
        data: '',
        dataType: 'json',
        global: false,
        async: false
    }).done(function (data){
        userId = data.id;
    });
    return userId;
};

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

$(document).ready(function () {
    $.ajax({
        type: 'POST',
        url: './ad.do',
        data: "id=" + getURLParameter("id"),
        dataType: 'json'
    }).done(function (car) {
        addCarInfo(car);
        let userId = getUserId();
        if (car.author.id == userId) {
            let deletePhotoButton = $("<span></span>")
                .addClass("closebtn")
                .on("click", function () { deletePhoto() })
                .prop("id", "deletePhotoButton")
                .text("×");
            deletePhotoButton.insertAfter("#big");
            $("#controlButtons").show();
        }
    });
});

function addCarInfo(car) {
    $("#adTitle").text(car.model.brand.name + " " + car.model.modelName + ", " + car.year);
    $("#price").text(car.price + " руб");
    let options = {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
    };
    let publicationDate = new Date(car.publicationDate);
    $("#publicationDate").text(publicationDate.toLocaleString("ru", options));
    $("#brand").text(car.model.brand.name);
    $("#model").text(car.model.modelName);
    $("#bodyType").text(car.bodyType.name);
    $("#description").text(car.description);

    let row = $("<div></div>").addClass("row");
    if (car.photos.length > 0) {
        $("#big").attr("src", "./getPhoto.do?id=" + car.photos[0]);
    }
    if (car.status == true) {
        $("#status").text("Продано").attr("status", "true");
    }
    $.each(car.photos, function( index, value ) {
        let img =
            $("<img />")
                .attr("src", "./getPhoto.do?id=" + value)
                .attr("onclick", "myFunction(this)")
                .attr("id", value)
                .addClass("col-md-1");
        row.append(img);
        if (index > 1 && index % 5 == 0) {
            $("#images").append(row);
            row = $("<div></div>").addClass("row");
            $("#images").append("<br>");
        }
        if (index == car.photos.length - 1 && index % 5 != 0) {
            $("#images").append(row);
        }
    });
}

function myFunction(img) {
    $("#big").attr("src", img.src);
}

$(document).ready(function () {
   $("#addButton").click(function () {
       window.location.href = 'addPhoto.do?advertId=' + getURLParameter("id");
   });
});

$(document).ready(function () {
    $("#deleteAdButton").click(function () {
        window.location.href = 'deleteAd.do?advertId=' + getURLParameter("id");
    });
});

$(document).ready(function () {
    $("#changeStatusButton").click(function () {
        let status = !($("#status").attr("status") === 'true');
        window.location.href =
            'changeAdStatus.do?advertId=' + getURLParameter("id") + "&status=" + status;
    });
});

function deletePhoto() {
    let photoId = $("#big").attr("src").split('=')[1];
    window.location.href = 'deletePhoto.do?advertId=' + getURLParameter("id") + '&photoId=' + photoId;
}