'use strict';

function Selected(a)
{
    if(a == 6 || a == 7 || a == 8 || a == 9 || a == 10){
        document.getElementById('mr').style.display = 'block';
    } else if (a == 1 || a == 2 || a == 3 || a == 4 || a == 5){
        document.getElementById('mr').style.display = 'none';
    }
}

function show(state)
{
    document.getElementById('window').style.display = state;
    document.getElementById('wrap').style.display = state;
}

function ShowGif(state){
    document.getElementById('gif').style.display = state;
}

$('#formx').submit(function(e){
    e.preventDefault();

    var text = $('#text').val();
    var mSel = document.getElementById('method');
    var method = mSel.options[mSel.selectedIndex].value;
    var procent = $('#volume').val();
    var markers = $('#markersInText').val();

    if (text == '' || procent == '' || method == '') {
        $('#textReferat').val("Пожалуйста, введите текст, выберите метод реферирования и укажите объём реферата! Если Вам непонятно как пользоваться сервисом, откройте справочную информацию.");
        ShowGif('none');
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
            url: "http://boberpul2.asuscomm.com:8088/summary/do",
            // url: "http://localhost:8088/summary/do",
            type: "POST",
            //data: $('#formx').serialize(),
            contentType: "application/json",

            data: data,
            success: function (returnData) {
                ShowGif('none');
                var result = JSON.parse(JSON.stringify(returnData));
                var markersDecode = "";

                if(result.summary == undefined || result.summary == null || result.summary == "" ){
                    $('#textReferat').val("Пожалуйста, увеличьте текст");
                } else {
                    var textDecode = decodeURIComponent(escape(atob(result.summary)));

                    for (var i = 0; i < result.keyWords.length; ++i) {
                        markersDecode += decodeURIComponent(escape(atob(result.keyWords[i]))) + ", ";
                    }

                    $('#textReferat').val(textDecode);
                    $('#textKeyWords').val(markersDecode);
                }
            },
            error: function () {
                ShowGif('none');
                $('#textReferat').val("К сожалению, при обработке текста произошла ошибка! Попробуйте позже или обратитеcь к разработчикам TASystem@yandex.ru");
            }
        });
    }
});

