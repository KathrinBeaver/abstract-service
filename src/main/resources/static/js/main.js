'use strict';

function Selected(a) {
    if (a == 6 || a == 7 || a == 8 || a == 9 || a == 10) {
        document.getElementById('mr').style.display = 'block';
    } else if (a == 1 || a == 2 || a == 3 || a == 4 || a == 5) {
        document.getElementById('mr').style.display = 'none';
    }
}

function show(state) {
    document.getElementById('window').style.display = state;
    document.getElementById('wrap').style.display = state;
}

$('#formx').submit(function (e) {
    e.preventDefault();
    var text = $('#text').val();
    var mSel = document.getElementById('method');
    var method = mSel.options[mSel.selectedIndex].value;
    var procent = $('#volume').val();
    var markers = $('#markersInText').val();

    if (text == '') {
        $('#textReferat').val("Пожалуйста, введите текст!");
    }
    else {
        var textEncode = btoa(unescape(encodeURIComponent(text)));
        var markersEncode = btoa(unescape(encodeURIComponent(markers)));

        var summary = {
            "text": textEncode,
            "keyWords": markersEncode,
            "procentOfText": procent,
            "numberMethod": method
        };
        var data = JSON.stringify(summary);

        $.ajax({
            // url: "http://localhost:8088/summary/do",
            url: "http://boberpul2.asuscomm.com:8088/summary/do",
            type: "POST",
            contentType: "application/json",

            data: data,
            success: function (returnData) {

                var result = JSON.parse(JSON.stringify(returnData));
                var textDecode = decodeURIComponent(escape(atob(result.summary)));
                var markersDecode = "";

                for (var i = 0; i < result.keyWords.length; ++i) {
                    markersDecode += decodeURIComponent(escape(atob(result.keyWords[i]))) + ", ";
                }
                $('#textReferat').val(textDecode);
                $('#textKeyWords').val(markersDecode);
            },
            error: function () {
                $('#textReferat').val("К сожалению, при обработке текста произошла ошибка! Попробуйте позже или обратитеcь к разработчикам TASystem@yandex.ru");
            }
        });
    }
});

