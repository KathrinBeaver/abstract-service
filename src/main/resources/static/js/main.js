'use strict';

/*var singleUploadForm = document.querySelector('#singleUploadForm');
var singleFileUploadInput = document.querySelector('#singleFileUploadInput');
var singleFileUploadError = document.querySelector('#singleFileUploadError');
var singleFileUploadSuccess = document.querySelector('#singleFileUploadSuccess');

var multipleUploadForm = document.querySelector('#multipleUploadForm');
var multipleFileUploadInput = document.querySelector('#multipleFileUploadInput');
var multipleFileUploadError = document.querySelector('#multipleFileUploadError');
var multipleFileUploadSuccess = document.querySelector('#multipleFileUploadSuccess');

function uploadSingleFile(file) {
    var formData = new FormData();
    formData.append("file", file);

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadFile");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            singleFileUploadError.style.display = "none";
            singleFileUploadSuccess.innerHTML = "<p>File Uploaded Successfully.</p><p>DownloadUrl : <a href='" + response.fileDownloadUri + "' target='_blank'>" + response.fileDownloadUri + "</a></p>";
            singleFileUploadSuccess.style.display = "block";
        } else {
            singleFileUploadSuccess.style.display = "none";
            singleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}

function uploadMultipleFiles(files) {
    var formData = new FormData();
    for(var index = 0; index < files.length; index++) {
        formData.append("files", files[index]);
    }

    var xhr = new XMLHttpRequest();
    xhr.open("POST", "/uploadMultipleFiles");

    xhr.onload = function() {
        console.log(xhr.responseText);
        var response = JSON.parse(xhr.responseText);
        if(xhr.status == 200) {
            multipleFileUploadError.style.display = "none";
            var content = "<p>All Files Uploaded Successfully</p>";
            for(var i = 0; i < response.length; i++) {
                content += "<p>DownloadUrl : <a href='" + response[i].fileDownloadUri + "' target='_blank'>" + response[i].fileDownloadUri + "</a></p>";
            }
            multipleFileUploadSuccess.innerHTML = content;
            multipleFileUploadSuccess.style.display = "block";
        } else {
            multipleFileUploadSuccess.style.display = "none";
            multipleFileUploadError.innerHTML = (response && response.message) || "Some Error Occurred";
        }
    }

    xhr.send(formData);
}


singleUploadForm.addEventListener('submit', function(event){
    var files = singleFileUploadInput.files;
    if(files.length === 0) {
        singleFileUploadError.innerHTML = "Please select a file";
        singleFileUploadError.style.display = "block";
    }
    uploadSingleFile(files[0]);
    event.preventDefault();
}, true);


multipleUploadForm.addEventListener('submit', function(event){
    var files = multipleFileUploadInput.files;
    if(files.length === 0) {
        multipleFileUploadError.innerHTML = "Please select at least one file";
        multipleFileUploadError.style.display = "block";
    }
    uploadMultipleFiles(files);
    event.preventDefault();
}, true);*/

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
    return decodeURIComponent(atob(str).split('').map(function(c) {
        return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
}


$('#formx').submit(function(e){
    e.preventDefault();
    var text = $('#text').val();
    //var text = document.getElementById('text').text;
    // alert(text);
    //var method = $('#method');
    var mSel = document.getElementById('method');
    var method = mSel.options[mSel.selectedIndex].value;
    var procent = $('#volume').val();
    //var procent = document.getElementById('volume').valueOf();
    var markers = $('#markersInText').val();
    // alert(markers);
    //var markers = document.getElementById('markersInText').text;



    var textEncode = btoa(unescape(encodeURIComponent(text)));
    // var textEncode = b64EncodeUnicode(text);
    var markersEncode = btoa(unescape(encodeURIComponent(markers)));
    // var markersEncode = b64EncodeUnicode(markers);

    var summary = {
        "text": textEncode,
        "keyWords" : markersEncode,
        "procentOfText" : procent,
        "numberMethod" : method
    };
    var data = JSON.stringify(summary);

    /*  var test = "Привет";
      var testEncode = btoa(unescape(encodeURIComponent(test)));
      console.log(testEncode);*/


    /*function SummaryObject(text, markers, procent, method){

     }

    var data = new SummaryObject(textEncode, markersEncode, procent, method);*/

    $.ajax({
        // url: "http://localhost:8088/summary/do",
        url: "http://boberpul2.asuscomm.com:8088/summary/do",
        type: "POST",
        //data: $('#formx').serialize(),
        contentType: "application/json",

        data: data,
        success: function(returnData) {

            var result = JSON.parse(JSON.stringify(returnData));

            var textDecode = decodeURIComponent(escape(atob(result.summary)));
            //var markersDecode = atob(unescape(decodeURIComponent(result.keyWords)));
            var markersDecode = "";
            for (var i=0; i<result.keyWords.length; ++i) {
                markersDecode += decodeURIComponent(escape(atob(result.keyWords[i]))) + ", ";
            }
            $('#textReferat').val(textDecode);
            $('#textKeyWords').val(markersDecode);
        },
        error: function(){
            $('#textReferat').val("Всё плохо! Ничего не получается!");
        }
    });
});

