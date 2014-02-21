$(document).ready(function () {
    'use strict';

    // JSON data response map
    var record = data['records'][0];
    var names = record['names'].split(' / ');


    // UI elements
    var $title = $('.page-title h3');
    var $download = $('#download');
    var $table = $('#properties');
    var $tbody = $(' tbody', $table);
    var $notesPanel = $('.panel-notes');
    var $notesPanelBody = $(' .panel-body', $notesPanel);
    var $externPanel = $('.panel-extern');
    var $externPanelList = $(' .panel-body ul', $externPanel);
    var $refPanel = $('.panel-reference');
    var $refStyle = $(' .reference-style', $refPanel);
    var $refText = $(' .reference-text', $refPanel);
    var $refStyleDropdown = $(' .reference-dropdown', $refPanel);

    var transitionDuration = 300;

    /*
     * Properties table
     */

    var addPropertyRow = function (prop, description, value) {
        var getRow = function (prop, name, val) {
            return '<tr class="' + prop + '"><td class="description">' + name +
                '</td><td class="value">' + val + '</td></tr>';
        };

        value = value || record[prop];
        if (value)
            $tbody.append(getRow(prop, description, value));
    };

    var getRangeText = function (low, high) {
        if (low && high) {
            if (low === high)
                return low;
            else
                return low + ' - ' + high;
        } else if (low)
            return low;
        else if (high)
            return high;
    };

    var getPiText = function () {
        /*
         * We allow for some flexibility in displaying isoelectric points. We
         * will try first to show an exact value, else a range of values, or
         * just an individual result within that range.
         */
        var piMin   = record['pi_min'];
        var piMax   = record['pi_max'];
        var piMajor = record['pi_major'];

        if (piMin && piMax) {
            if (piMin === piMax)
                return piMin;
            else
                return piMin + ' - ' + piMax;
        } else if (piMin)
            return '> ' + piMin;
        else if (piMax)
            return '< ' + piMax;
        else if (piMajor)
            return piMajor + 'm';
    };

    var addExternLink = function (prop, name, url) {
        var getButton = function (prop, name, url) {
            return '<li><a class="btn btn-success btn-block ' + prop +
                '" href="' + url + '" target="_blank">' + name + '</a></li>';
        };

        url = url || record[prop];
        if (url)
            $externPanelList.append(getButton(prop, name, url));
    };

    // Page title
    $title.hide();
    $title.text(names[0]);

    // Properties
    addPropertyRow('name', 'Protein Name', names[0]);
    for (var i = 1; i < names.length; i++)
        addPropertyRow('name', 'Alternative Protein Name', names[i]);
    addPropertyRow('ec', 'E.C.');
    addPropertyRow('source', 'Source');
    addPropertyRow('location', 'Organ and/or Subcellular location');
    addPropertyRow('mw', 'M.W', getRangeText(record.mw_min, record.mw_max));
    addPropertyRow('sub_no', 'Subunit No.');
    addPropertyRow('sub_mw', 'Subunit M.W');
    addPropertyRow('iso_enzymes', 'No. of Iso-enzymes');
    addPropertyRow('pi', 'pI', getPiText());
    addPropertyRow('temp', 'Temperature (ºC)',
                   getRangeText(record.temp_min, record.temp_max));
    addPropertyRow('method', 'Experimental Method');

    // Extern links
    addExternLink('ref_full', 'Full Text');
    addExternLink('ref_abstract', 'Publisher\'s Abstract');
    addExternLink('ref_pubmed', 'PubMed');
    addExternLink('ref_taxonomy', 'Species Taxonomy');
    addExternLink('ref_sequence', 'Protein Sequence');

    // Add notes
    if (record.notes) {
        $notesPanelBody.text(record.notes);
    };


    /*
     * "Reference this page" citation styles
     */

    var pageName = record.names;
    var pageUrl = window.location.href.replace(window.location.hash, '');
    var pageUrlHtml = '<a class="url" href="' + pageUrl + '">' +
        pageUrl + '</a>';

    // The ref styles in an assoc array, where key is the style name
    // and value is the HTML reference text.
    var refStyles = {
        'Vancouver': (function () {
            return 'pip-db.org [' + pageName + ']. Aston University; ' +
                moment(record.created_at).format('YYYY') +
                ' [cited ' + moment().format('Do MMMM YYYY') +
                ']. Available from: ' + pageUrlHtml + '.'
        })(),
        'ACS Style': (function () {
            return 'Aston University. ' +
                moment(record.created_at).format('YYYY') +
                '. "' + pageName +
                '" pip-db.org. Website accessed ' +
                moment().format('MMMM Do, YYYY') + '. ' +
                pageUrlHtml + '.';
        })(),
        'Harvard': (function () {
            return 'Aston University. (' +
                moment(record.created_at).format('YYYY') +
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
    $('#date-added').html('Added ' + moment(record.created_at).fromNow() + '.');

    // Show the UI elements which have content
    $(document).ready(function () {
        $title.fadeIn(transitionDuration);
        $table.fadeIn(transitionDuration);
        if ($externPanelList.html()) // Extern Panel
            $externPanel.fadeIn(transitionDuration);
        if ($notesPanelBody.text()) // Notes Panel
            $notesPanel.fadeIn(transitionDuration);
        $refPanel.fadeIn(transitionDuration);
        $download.show();
    });

});
