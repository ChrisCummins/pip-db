$(function () {
    'use strict';

    var hashDiff = function (h1, h2) {
        var diff = {}, k;

        for (k in h2) {
            if (h1[k] !== h2[k]) { diff[k] = h2[k]; }
        }

        return diff;
    },
        convertSerializedArrayToHash = function (a) {
            var hash = {};

            a.forEach(function (element) {
                hash[element.name] = element.value;
            });

            return hash;
        },
        $form = $('#as'),
        startItems = convertSerializedArrayToHash($form.serializeArray());

    $form.submit(function (event) {

        event.preventDefault();

        var currentItems = convertSerializedArrayToHash($form.serializeArray()),
            itemsToSubmit = hashDiff(startItems, currentItems);

        window.location = $form.attr('action') + '?' + $.param(itemsToSubmit);
    });

});
