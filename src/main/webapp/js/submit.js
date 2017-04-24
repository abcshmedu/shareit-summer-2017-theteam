'use strict';

/**
 * Ugly java script code for simple tests of shareit's REST interface.
 *  @author Axel Böttcher <axel.boettcher@hm.edu>
 */

/**
 * This function is used for transfer of new book info.
 */
var submitNewBook = function() {
	var json = JSON.stringify({
			title: $("input[name=title]").val(),
			author: $("input[name=author]").val(),
			isbn: $("input[name=isbn]").val()
	});
	var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/media/books/',
        type:'POST',
        contentType: 'application/json; charset=UTF-8',
        data: json
        })
        .done(() => {
			$("input[name=title]").val("");
			$("input[name=author]").val("");
			$("input[name=isbn]").val("");
        	
        	errorText.removeClass("visible");
        	errorText.addClass("hidden");
        })
        .fail((error) => {
        	errorText.addClass("visible");
        	errorText.text(error.responseJSON.detail);
        	errorText.removeClass("hidden");
        });

}

/**
 * This function is used for transfer of new disc info.
 */
var submitNewDisc = function() {
	var json = JSON.stringify({
			barcode: $("input[name=barcode]").val(),
			director: $("input[name=director]").val(),
			fsk: $("input[name=fsk]").val(),
			title: $("input[name=title]").val()
	});
	var errorText = $("#errormessage");
    $.ajax({
        url: '/shareit/media/discs/',
        type:'POST',
        contentType: 'application/json; charset=UTF-8',
        data: json
        })
        .done(() => {
			$("input[name=barcode]").val("");
			$("input[name=director]").val("");
			$("input[name=fsk]").val("");
			$("input[name=title]").val("");

        	errorText.removeClass("visible");
        	errorText.addClass("hidden");
        })
        .fail((error) => {
        	errorText.addClass("visible");
        	errorText.text(error.responseJSON.detail);
        	errorText.removeClass("hidden");
        });
}

/**
 * Creates a list of all books using a Mustache-template.
 */
var listBooks = function() {
	$.ajax({
        url: '/shareit/media/books',
        type:'GET'
	})
	.done((data) => {
		var template = "<h2>Books</h2><table class='u-full-width'>" +
            "<thead><tr><th>Title</th><th>Author</th><th>ISBN</th></tr></thead>" +
            "<tbody>{{#data}}<tr><td>{{title}}</td><td>{{author}}</td><td>{{isbn}}</td></tr>{{/data}}</tbody></table>";
		Mustache.parse(template);
		var output = Mustache.render(template, {data: data});
		$("#content").html("<h2>Books</h2>").html(output);
	});// no error handling
}
/**
 * Creates a list of all discs using a Mustache-template.
 */
var listDiscs = function() {
	$.ajax({
        url: '/shareit/media/discs',
        type:'GET'
	})
	.done((data) => {
		var template = "<h2>Discs</h2><table class='u-full-width'>" +
            "<thead><tr><th>Title</th><th>Barcode</th><th>Director</th><th>FSK</th></tr></thead>" +
			"<tbody>{{#data}}<tr><td>{{title}}</td><td>{{barcode}}</td><td>{{director}}</td><td>{{fsk}}</td></tr>{{/data}}</tbody></table>";
		Mustache.parse(template);
		var output = Mustache.render(template, {data: data});
		$("#content").html(output);
	});// no error handling
}

/**
 * Call backer for "navigational buttons" in left column. Used to set content in main part.
 */
var changeContent = function(content) {
	$.ajax({
        url: content,
        type:'GET'
	})
	.done((data) => {
		$("#content").html(data);
	});// no error handling
}
