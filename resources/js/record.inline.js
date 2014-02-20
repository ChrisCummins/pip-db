$(document).ready(function () {
    'use strict';

    // UI elements
    var $refPanel = $('.panel-reference');
    var $refStyle = $(' .reference-style', $refPanel);
    var $refText = $(' .reference-text', $refPanel);
    var $refStyleDropdown = $(' .reference-dropdown', $refPanel);

    /*
     * Lookup a property by key. The key must be a string which matches
     * a data-key tag. The property's value is returned as a string. Example:
     *
     *   getProperty("name")
     *     -> "Alkaline phosphatase"
     *
     * If a property matching the key is not found, undefined is
     * returned. This is a list lookup, with linear O(n) time
     * performance.
     */
    var getProperty = function (key) {
        var value;

        $('#properties .property').each(function (index) {
            var property = $(this).attr('data-key');

            if (key === property) {
                value = $(this).children('.value').text();
                return false;           // Break loop
            }
        });

        return value;
    };

    /*
     * "Reference this page" citation styles
     */

    var pageName = getProperty('name');
    var pageUrl = window.location.href.replace(window.location.hash, '');
    var pageUrlHtml = '<a class="url" href="' + pageUrl + '">' +
        pageUrl + '</a>';
    var pageTimestamp = $('#date-added').attr('data-date');

    // The ref styles in an assoc array, where key is the style name
    // and value is the HTML reference text.
    var refStyles = {
        'Vancouver': (function () {
            return 'pip-db.org [' + pageName + ']. Aston University; ' +
                moment(pageTimestamp).format('YYYY') +
                ' [cited ' + moment().format('Do MMMM YYYY') +
                ']. Available from: ' + pageUrlHtml + '.'
        })(),
        'ACS Style': (function () {
            return 'Aston University. ' +
                moment(pageTimestamp).format('YYYY') +
                '. "' + pageName +
                '" pip-db.org. Website accessed ' +
                moment().format('MMMM Do, YYYY') + '. ' +
                pageUrlHtml + '.';
        })(),
        'Harvard': (function () {
            return 'Aston University. (' +
                moment(pageTimestamp).format('YYYY') +
                '). <i>' + pageName + '</i>. Available: ' +
                pageUrlHtml + '. Last accessed ' +
                moment().format('Do MMMM YYYY') + '.';
        })()
    };

    // Instantiate the list of ref styles
    for (var style in refStyles) {
        var a = '<a href="#' + style.split(' ')[0].toLowerCase() +
            '">' + style + '</a>';

        $refStyleDropdown.append('<li>' + a + '</li>');
    };

    // Set a new active ref style
    var setActiveRefStyle = function (newRefStyle) {

        var transitionDuration = 300; // Text fade duration (ms)

        // Set and show new text
        var fadeInNewText = function () {
            $refText.hide();
            $refText.html(refStyles[newRefStyle]);
            $refText.fadeIn(transitionDuration);
        };

        // Set the new ref style as active
        $(' li', $refStyleDropdown).removeClass('active');
        $(' li a:contains(' + newRefStyle + ')').parent().addClass('active');

        if ($refText.text()) // Fade out the old text if there is any
            $refText.fadeOut(transitionDuration, fadeInNewText);
        else
            fadeInNewText();
    };

    // Respond to user selecting new ref style
    $(' li a', $refStyleDropdown).click(function () {
        setActiveRefStyle($(this).text());
    });

    // Set the first style as active
    setActiveRefStyle((function () {
        for (var style in refStyles)
            return style;
    })());

    // Record added footer
    $('#date-added').html('Added ' + moment(pageTimestamp).fromNow() + '.');

});
