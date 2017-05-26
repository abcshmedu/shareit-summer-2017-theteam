'use strict';

/**
 * Ugly java script code for simple tests of shareit's REST interface.
 *  @author Axel Böttcher <axel.boettcher@hm.edu>
 */

var submitAuthenticateUser = function () {
    var json = JSON.stringify({
        username: $("input[name=username]").val(),
        password: $("input[name=password]").val()
    });
    var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/auth/',
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: json
    }).done((data) => {
        Cookies.set('token', '?token=' + data['key']);
        $("input[name=username]").val("");
    $("input[name=password]").val("");
    errorText.removeClass("visible");
    errorText.addClass("hidden");

    var template = "<h2>Token</h2><table class='u-full-width'>" +
        "<tbody>{{#data}}<tr><td><strong>KEY: </strong>{{key}}</td></tr><tr><td><strong>created: </strong>{{created}}</td></tr><tr><td><strong>valid until: </strong>{{validUntil}}</td></tr>{{/data}}</tbody></table>";
    Mustache.parse(template);
    var output = Mustache.render(template, {data: data});
    $("#content").html(output);
})
    .
    fail((error) => {
    errorText.addClass("visible");
    errorText.text(error.responseJSON.message);
    errorText.removeClass("hidden");
});
}


/**
 * This function is used for transfer of new book info.
 */
var submitNewBook = function () {
    var json = JSON.stringify({
        title: $("input[name=title]").val(),
        author: $("input[name=author]").val(),
        isbn: $("input[name=isbn]").val()
    });
    var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/media/books/' + Cookies.get('token'),
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: json
    })
        .done(() => {
        $("input[name=title]"
    ).
    val("");
    $("input[name=author]").val("");
    $("input[name=isbn]").val("");

    errorText.removeClass("visible");
    errorText.addClass("hidden");
})
    .
    fail((error) => {
        errorText.addClass("visible");
    errorText.text(error.responseJSON.message);
    errorText.removeClass("hidden");
})
    ;

}

var updateBook = function (isbn) {
    var json = JSON.stringify({
        title: $("input[name=title]").val(),
        author: $("input[name=author]").val(),
        isbn: $("input[name=isbn]").val()
    });
    var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/media/books/' + isbn + '/' + Cookies.get('token'),
        type: 'PUT',
        contentType: 'application/json; charset=UTF-8',
        data: json
    })
        .done(() => {
        $("input[name=title]"
    ).
    val("");
    $("input[name=author]").val("");
    $("input[name=isbn]").val("");

    errorText.removeClass("visible");
    errorText.addClass("hidden");
})
    .
    fail((error) => {
        errorText.addClass("visible");
    errorText.text(error.responseJSON.message);
    errorText.removeClass("hidden");
})
    ;
}


var updateDisc = function (barcode) {
    var json = JSON.stringify({
        barcode: $("input[name=barcode]").val(),
        director: $("input[name=director]").val(),
        fsk: $("input[name=fsk]").val(),
        title: $("input[name=title]").val()
    });
    var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/media/discs/' + barcode + Cookies.get('token'),
        type: 'PUT',
        contentType: 'application/json; charset=UTF-8',
        data: json
    })
        .done(() => {
        $("input[name=barcode]"
    ).
    val("");
    $("input[name=director]").val("");
    $("input[name=fsk]").val("");
    $("input[name=title]").val("");

    errorText.removeClass("visible");
    errorText.addClass("hidden");
})
    .
    fail((error) => {
        errorText.addClass("visible");
    errorText.text(error.responseJSON.message);
    errorText.removeClass("hidden");
})
    ;
}

/**
 * This function is used for transfer of new disc info.
 */
var submitNewDisc = function () {
    var json = JSON.stringify({
        barcode: $("input[name=barcode]").val(),
        director: $("input[name=director]").val(),
        fsk: $("input[name=fsk]").val(),
        title: $("input[name=title]").val()
    });
    var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/media/discs' + Cookies.get('token'),
        type: 'POST',
        contentType: 'application/json; charset=UTF-8',
        data: json
    })
        .done(() => {
        $("input[name=barcode]"
    ).
    val("");
    $("input[name=director]").val("");
    $("input[name=fsk]").val("");
    $("input[name=title]").val("");

    errorText.removeClass("visible");
    errorText.addClass("hidden");
})
    .
    fail((error) => {
        errorText.addClass("visible");
    errorText.text(error.responseJSON.message);
    errorText.removeClass("hidden");
})
    ;
}

/**
 * Creates a list of all books using a Mustache-template.
 */
var listBooks = function () {
    $.ajax({
        url: '/shareit/media/books'+Cookies.get('token'),
        type: 'GET'
    })
        .done((data) => {
        var template = "<h2>Books</h2><table class='u-full-width'>" +
            "<thead><tr><th>Title</th><th>Author</th><th>ISBN</th></tr></thead>" +
            "<tbody>{{#data}}<tr><td>{{title}}</td><td>{{author}}</td><td><a onclick='listBook({{isbn}})'>{{isbn}}</a></td></tr>{{/data}}</tbody></table>";
    Mustache.parse(template);
    var output = Mustache.render(template, {data: data});
    $("#content").html(output);
})
    ;// no error handling
}
/**
 * Creates a list of all discs using a Mustache-template.
 */
var listDiscs = function () {
    $.ajax({
        url: '/shareit/media/discs'+Cookies.get('token'),
        type: 'GET'
    })
        .done((data) => {
        var template = "<h2>Discs</h2><table class='u-full-width'>" +
            "<thead><tr><th>Title</th><th>Barcode</th><th>Director</th><th>FSK</th></tr></thead>" +
            "<tbody>{{#data}}<tr><td>{{title}}</td><td><a onclick='listDisc({{barcode}})' >{{barcode}}</a></td><td>{{director}}</td><td>{{fsk}}</td></tr>{{/data}}</tbody></table>";
    Mustache.parse(template);
    var output = Mustache.render(template, {data: data});
    $("#content").html(output);
})
    ;// no error handling
}

/**
 * Call backer for "navigational buttons" in left column. Used to set content in main part.
 */
var changeContent = function (content) {
    $.ajax({
        url: content,
        type: 'GET'
    })
        .done((data) => {
        $("#content"
    ).
    html(data);
})
    ;// no error handling
}

var listDisc = function (barcode) {
    $.ajax({
        url: '/shareit/media/discs/' + barcode + Cookies.get('token'),
        type: 'GET'
    })
        .done((data) => {
        var template = "<h2>Update disc</h2>" +
            "<table border='0'><tbody>{{#data}}" +
            "<tr><td><label for='barcode'>Barcode:</label></td> <td><input type='text' name='barcode' value='{{barcode}}'></td></tr>" +
            "<tr><td><label for='director'>Director:</label></td> <td><input type='text' name='director' value='{{director}}'></td></tr>" +
            "<tr><td><label for='fsk'>FSK:</label></td> <td><input type='number' name='fsk' value='{{fsk}}'></td></tr>" +
            "<tr><td><label for='title'>Title:</label></td> <td><input type='text' name='title' value='{{title}}'></td></tr>" +
            "{{/data}}</tbody></table>" +
            "<div><span id='errormessage' class='hidden error'>ERROR</span></div>" +
            "<input type='button' class='button-primary' id='registerButton' onclick='updateDisc(" + barcode + ")' value='Update'/>";
    Mustache.parse(template);
    var output = Mustache.render(template, {data: data});
    $("#content").html(output);
})
    ;// no error handling
}
var listBook = function (isbn) {
    $.ajax({
        url: '/shareit/media/books/' + isbn + Cookies.get('token'),
        type: 'GET'
    })
        .done((data) => {
        var template = "<h2>Update book</h2>" +
            "<table border='0'><tbody>{{#data}}" +
            "<tr><td><label for='title'>Titel:</label></td> <td><input type='text' name='title' value='{{title}}'></td></tr>" +
            "<tr><td><label for='author'>Autor:</label></td> <td><input type='text' name='author' value='{{author}}'></td></tr>" +
            "<tr><td><label for='isbn'>ISBN:</label></td> <td><input type='text' name='isbn' value='{{isbn}}'></td></tr>" +
            "{{/data}}</tbody></table>" +
            "<div><span id='errormessage' class='hidden error'>ERROR</span></div>" +
            "<input type='button' class='button-primary' id='registerButton' onclick='updateBook(" + isbn + ")' value='Update'/>";
    Mustache.parse(template);
    var output = Mustache.render(template, {data: data});
    $("#content").html(output);
})
    ;// no error handling
}