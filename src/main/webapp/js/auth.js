$(document).ready(function () {
    $.ajax({
        type: 'GET',
        url: './auth.do',
        data: '',
        dataType: 'json'
    }).done(function (data) {
        if (data.username != undefined) {
            let lk = '<a class="col-md-1 offset-md-6" id="lk" href="./lk.do">' + data.username + '</a>';
            let logOut = '<a class="col-md-1" id="exit" href="./logout.do">Выйти</a>';
            $("#registration").remove();
            $(logOut).insertAfter("#logo");
            $(lk).insertAfter("#logo");
            $("#addButton").click(function () {
                window.location.href = "add.do";
            });
        }
    });
});