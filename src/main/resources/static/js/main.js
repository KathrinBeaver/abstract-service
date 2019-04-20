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

function b64EncodeUnicode(str) {
    // first we use encodeURIComponent to get percent-encoded UTF-8,
    // then we convert the percent encodings into raw bytes which
    // can be fed into btoa.
    return btoa(encodeURIComponent(str).replace(/%([0-9A-F]{2})/g,
        function toSolidBytes(match, p1) {
            return String.fromCharCode('0x' + p1);
        }));
}

function b64DecodeUnicode(str) {
    // Going backwards: from bytestream, to percent-encoding, to original string.
    return decodeURIComponent(atob(str).split('').map(function (c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
}


$('#formx').submit(function (e) {
    e.preventDefault();
    var text = $('#text').val();
    var mSel = document.getElementById('method');
    var method = mSel.options[mSel.selectedIndex].value;
    var procent = $('#volume').val();
    //var procent = document.getElementById('volume').valueOf();
    var markers = $('#markersInText').val();


    var textEncode = btoa(unescape(encodeURIComponent(text)));
    // var textEncode = b64EncodeUnicode(text);
    var markersEncode = btoa(unescape(encodeURIComponent(markers)));
    // var markersEncode = b64EncodeUnicode(markers);

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
        //data: $('#formx').serialize(),
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
            $('#textReferat').val("Всё плохо! Ничего не получается!");
        }
    });
});

