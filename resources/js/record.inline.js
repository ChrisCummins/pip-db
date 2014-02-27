$(document).ready(function () {
    'use strict';

    // JSON data response map
    var record = data['Records'][0];
    var names = record['Protein-Names'].split(' / ');


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

    var addPropertyRow = function (query, prop, description, value) {
        var getRow = function (query,  prop, name, val) {
            var s = '<tr class="property ' + prop + '"><td class="description">'
                + name + '</td><td class="value">' + val;

            if (query)
                s += '<div class="pull-right"><a class="similar" href="' + '/s?' + query +
                    '">See other records like this &gt;&gt;</a></div>';

            return s + '</td></tr>';
        };

        value = value || record[prop];
        if (value)
            $tbody.append(getRow(query, prop, description, value));
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
    addPropertyRow('q=' + encodeURIComponent(names[0]),
                   'Protein-Name', 'Protein Name', names[0]);

    for (var i = 1; i < names.length; i++) {
        addPropertyRow('q=' + encodeURIComponent(names[i]),
                       'Protein-Name', 'Alternative Protein Name', names[i]);
    }

    addPropertyRow((function () {
        var ec_arr = record['EC'] ? record['EC'].split('.') : [];
        var s = '';

        for (var i = 0; i < ec_arr.length; i++)
            s += 'ec' + (i + 1) + '=' + encodeURIComponent(ec_arr[i]) + '&';

        return s.substring(0, s.length - 1);
    })(), 'EC', 'E.C.');

    addPropertyRow('q_s=' + encodeURIComponent(record['Source']),
                   'Source', 'Source');
    addPropertyRow('q_l=' + encodeURIComponent(record['Location']),
                   'Location', 'Organ and/or Subcellular location');
    addPropertyRow('mw_l=' + encodeURIComponent(record['MW-Min']) + '&mw_h=' +
                   encodeURIComponent(record['MW-Max']), 'MW', 'M.W',
                   getRangeText(record['MW-Min'], record['MW-Max']));
    addPropertyRow(null, 'Subunit-No', 'Subunit No.');
    addPropertyRow(null, 'Subunit-MW', 'Subunit M.W');
    addPropertyRow(null, 'No-of-Iso-Enzymes', 'No. of Iso-enzymes');
    addPropertyRow('pi_l=' + encodeURIComponent(record['pI-Min']) + '&pi_h=' +
                   encodeURIComponent(record['pI-Max']), 'pI', 'pI',
                   getRangeText(record['pI-Min'], record['pI-Max']));
    addPropertyRow(null, 'pI-Major-Component', 'pI of Major Component');
    addPropertyRow('t_l=' + encodeURIComponent(record['Temperature-Min']) +
                   '&t_h=' + encodeURIComponent(record['Temperature-Max']),
                   'Temperature', 'Temperature (ºC)',
                   getRangeText(record['Temperature-Min'],
                                record['Temperature-Max']));
    addPropertyRow('m=' + encodeURIComponent(record['Method']),
                   'Method', 'Experimental Method');
    if (record['Sequence'])
        addPropertyRow(null, 'Sequence', 'FASTA Sequence',
                       '<textarea class="fasta" readonly>' +
                       record['Sequence'] + '</textarea>' +
                       '<a id="fasta" href="#">Show &gt;&gt;</a>');

    // Extern links
    addExternLink('Full-Text', 'Full Text');
    addExternLink('Abstract-Only', 'Publisher\'s Abstract');
    addExternLink('PubMed', 'PubMed');
    addExternLink('Species-Taxonomy', 'Species Taxonomy');
    addExternLink('Protein-Sequence', 'Protein Sequence');

    // Add notes
    if (record['Notes']) {
        $notesPanelBody.text(record['Notes']);
    };

    // Show and hide FASTA sequence
    $fastaContainer = $('.record table td textarea.fasta');
    $('#fasta').click(function () {
        if ($fastaContainer.is(':visible')) {
            $(this).text('Show >>');
            $fastaContainer.hide();
        } else {
            $(this).text('Hide <<');
            $fastaContainer.show();
            $fastaContainer.select();
        }
    });

    // "Show similar to this" callback
    $('.property').hover(function (e) {
        $(' .similar', this).show();
    }, function (e) {
        $(' .similar', this).hide();
    })

    /*
     * "Reference this page" citation styles
     */

    var pageName = record['Protein-Names'];
    var pageUrl = window.location.href.replace(window.location.hash, '');
    var pageUrlHtml = '<a class="url" href="' + pageUrl + '">' +
        pageUrl + '</a>';

    // The ref styles in an assoc array, where key is the style name
    // and value is the HTML reference text.
    var refStyles = {
        'Vancouver': (function () {
            return 'pip-db.org [' + pageName + ']. Aston University; ' +
                moment(record['Created-At']).format('YYYY') +
                ' [cited ' + moment().format('Do MMMM YYYY') +
                ']. Available from: ' + pageUrlHtml + '.'
        })(),
        'ACS Style': (function () {
            return 'Aston University. ' +
                moment(record['Created-At']).format('YYYY') +
                '. "' + pageName +
                '" pip-db.org. Website accessed ' +
                moment().format('MMMM Do, YYYY') + '. ' +
                pageUrlHtml + '.';
        })(),
        'Harvard': (function () {
            return 'Aston University. (' +
                moment(record['Created-At']).format('YYYY') +
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
    $('#date-added').html('Added ' + moment(record['Created-At']).fromNow() + '.');

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
