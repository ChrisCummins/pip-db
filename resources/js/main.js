/*
 * Site-common JavaScript functionality.
 *
 * This script is sourced from every HTML page.
 */

/*
 * Copyright (c) 2012 Ben Olson (https://github.com/bseth99/jquery-ui-extensions)
 * jQuery UI LabeledSlider @VERSION
 *
 * Permission is hereby granted, free of charge, to any person
 * obtaining a copy of this software and associated documentation
 * files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following
 * conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 * HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 * OTHER DEALINGS IN THE SOFTWARE.
 *
 * Depends:
 *  jquery.ui.core.js
 *  jquery.ui.widget.js
 *  jquery.ui.mouse.js
 *  jquery.ui.slider.js
 */
(function ($, undefined) {
    'use strict';

    $.widget("ui.labeledslider", $.ui.slider, {

        version: "@VERSION",

        options: {
            tickInterval: 0,
            tweenLabels: true,
            tickLabels: null,
            tickArray: []
        },

        uiSlider: null,
        tickInterval: 0,
        tweenLabels: true,

        _create: function () {

            this._detectOrientation();

            this.uiSlider =
                this.element
                .wrap('<div class="ui-slider-wrapper ui-widget"></div>')
                .before('<div class="ui-slider-labels">')
                .parent()
                .addClass(this.orientation)
                .css('font-size', this.element.css('font-size'));

            this._super();

            this.element.removeClass('ui-widget');

            this._alignWithStep();

            if (this.orientation === 'horizontal') {
                this.uiSlider.width(this.element.css('width'));
            } else {
                this.uiSlider.height(this.element.css('height'));
            }

            this._drawLabels();
        },

        _drawLabels: function () {

            var labels = this.options.tickLabels || {},
                $lbl = this.uiSlider.children('.ui-slider-labels'),
                dir = this.orientation === 'horizontal' ? 'left' : 'bottom',
                min = this.options.min,
                max = this.options.max,
                inr = this.tickInterval,
                cnt = (max - min) / inr,
                tickArray = this.options.tickArray,
                i = 0;

            $lbl.html('');

            if (tickArray.length > 0) {
                // tickArray provided, print labels only in the array
                for (i = 0; i < tickArray.length; i++) {
                    var label = labels[tickArray[i]];
                    label = label || tickArray[i];

                    $('<div>').addClass('ui-slider-label-ticks').addClass('ui-slider-label-tick-' + tickArray[i])
                        .css(dir, (Math.round((tickArray[i] - min) / cnt * 10000) / 100) + '%')
                        .html('<span>' + label + '</span>')
                        .appendTo($lbl);
                }
            } else {
                for ( ; i <= cnt; i++) {
                    $('<div>').addClass('ui-slider-label-ticks')
                        .css(dir, (Math.round(i / cnt * 10000) / 100) + '%')
                        .html('<span>' + (labels[i*inr+min] ? labels[i*inr+min] : (this.options.tweenLabels ? i*inr+min : '')) + '</span>')
                        .appendTo($lbl);
                }
            }
        },

        _setOption: function (key, value) {

            this._super(key, value);

            switch (key) {

            case 'tickInterval':
            case 'tickLabels':
            case 'tickArray':
            case 'min':
            case 'max':
            case 'step':

                this._alignWithStep();
                this._drawLabels();
                break;

            case 'orientation':

                this.element
                    .removeClass('horizontal vertical')
                    .addClass(this.orientation);

                this._drawLabels();
                break;
            }
        },

        _alignWithStep: function () {
            if (this.options.tickInterval < this.options.step)
                this.tickInterval = this.options.step;
            else
                this.tickInterval = this.options.tickInterval;
        },

        _destroy: function () {
            this._super();
            this.uiSlider.replaceWith(this.element);
        },

        widget: function () {
            return this.uiSlider;
        }

    });
}(jQuery));


/*
 * CUSTOM PIP-DB JAVASCRIPT
 */
(function () {
    'use strict';

    /*
     * Avoid `console` errors in browsers that lack a console.
     */
    var method;
    var noop = function () {};
    var methods = [
        'assert', 'clear', 'count', 'debug', 'dir', 'dirxml', 'error',
        'exception', 'group', 'groupCollapsed', 'groupEnd', 'info', 'log',
        'markTimeline', 'profile', 'profileEnd', 'table', 'time', 'timeEnd',
        'timeStamp', 'trace', 'warn'
    ];
    var length = methods.length;
    var console = (window.console = window.console || {});

    while (length--) {
        method = methods[length];

        // Only stub undefined methods.
        if (!console[method]) {
            console[method] = noop;
        }
    }

    /*
     * The search bar always grabs page focus.
     */
    var value = $("#q").val();
    $("#q").focus().val('').val(value);

    /*
     * Return a subset of values where each item is different from the
     * default. If no default is given for a value, then return it.
     */
    var stripDefaultValues = function (values, defaults) {
        var items = {}, itemsLength = 0;

        values.forEach(function (e) {
            if (e.value !== defaults[e.name]) {
                items[e.name] = e.value;
                itemsLength++;
            }
        });

        return {length: itemsLength, values: items};
    };

    /*
     * Perform the default value checks from stripDefaultValues but
     * simply return a boolean for whether or not the array did contain
     * a unique value. This executes much faster since it exits early,
     * with worst case times O(n).
     */
    var containsUniqueValues = function (values, defaults) {
        try {
            values.forEach(function (e) {
                if (e.value !== defaults[e.name]) {
                    throw new SuccessException;
                }
            });
        } catch (e) {
            return true;
        }

        return false;
    };

    var $searchForms = $('form[action="/s"]');
    var searchFormDefaults = {
        'q': '',
        'q_eq': '',
        'q_any': '',
        'q_ne': '',
        'q_s': '',
        'q_l': '',
        'ec1': '',
        'ec2': '',
        'ec3': '',
        'ec4': '',
        'pi_l': '',
        'pi_h': '',
        'mw_l': '',
        'mw_h': '',
        'm': 'Any',
        't_l': '',
        't_h': ''
    };

    /*
     * Append the advanced button value to the form values.
     */
    $(':submit', $searchForms).click(function () {
        var $form = $(this).closest('form');

        if ($(this).attr('value') === 'a') {
            $form.append($("<input type='hidden'>").attr({
                name: $(this).attr('name'),
                value: $(this).attr('value')
            }));
        }
    });

    $searchForms.submit(function (e) {
        e.preventDefault();

        var items = stripDefaultValues($(this).serializeArray(),
                                       searchFormDefaults);

        if (items.length) { // Only submit form if we have some unique values
            window.location = $(this).attr('action') +
                '?' + $.param(items.values);
        }
    });

    /*
     * Checks whether a search form has been filled in with data, and
     * activates the submit button if so.
     */
    var activateSubmitIfFormFilled = function ($form) {
        var $submit = $(' button[name="a"][value="s"]', $form);

        if (containsUniqueValues($form.serializeArray(), searchFormDefaults)) {
            $submit.removeClass('disabled'); // Enable button
        } else {
            $submit.addClass('disabled'); // Disable button
        }
    }

    // Bind form validation to text input keystrokes
    $($searchForms).bind('input propertychange', function (e) {
        activateSubmitIfFormFilled($(this).closest('form'));
    });

    // Bind form validation to dropdown selections
    $(' select', $searchForms).change(function (e) {
        activateSubmitIfFormFilled($(this).closest('form'));
    });

    // Validate form on load (in case of preloaded criteria)
    activateSubmitIfFormFilled($searchForms);

    /*
     * PI SLIDER
     */

    var $slider = $('#pi-slider');

    // Create our slider
    $slider.labeledslider({
        disabled: true,
        range: true,
        min: 0,
        max: 14,
        step: 0.5,
        values: [ 6, 8 ],
        tickInterval: 1,
        slide: function (event, ui) {
            setFormValuesFromSlider();
        }
    });

    var piActive = false; // Keep track of whether pI search is active

    // Update hidden form inputs
    var setFormValuesFromSlider = function () {
        if (piActive) {
            $('#pi_l').val($slider.labeledslider('values', 0));
            $('#pi_h').val($slider.labeledslider('values', 1));
        } else {
            $('#pi_l').val('');
            $('#pi_h').val('');
        }
    };

    // pI slider button press
    $('#pi-active').click(function (e) {
        piActive = !piActive;

        if (piActive) {
            $(this).text('On');
            $(this).addClass('btn-warning');
            $(this).removeClass('btn-primary');
            $slider.labeledslider('option', 'disabled', false);
            $('button[name="a"][value="s"]').removeClass('disabled');
        } else {
            $(this).text('Off');
            $(this).addClass('btn-primary');
            $(this).removeClass('btn-warning');
            $slider.labeledslider('option', 'disabled', true);
        }

        // Update form
        setFormValuesFromSlider();
        activateSubmitIfFormFilled($(this).closest('form'));
    });

    /*
     * AUTO-COMPLETE
     */

    // The 500 most common protein names
    var proteinNames = [
            "(Iso)ferritin",
            "Adenylate kinase",
            "Myoglobin",
            "Haemoglobin",
            "α-L-fucosidase",
            "α-L-Fucosidase",
            "Lectin",
            "Ferritin",
            "peroxidase",
            "Peroxidase",
            "Alkaline phosphatase",
            "Aldolase",
            "Cyclic nucleotide phosphodiesterase",
            "Cyclic Nucleotide Phosphodiesterase",
            "laccase",
            "Laccase",
            "β-Glucuronidase",
            "Acid phosphotase",
            "Catalase",
            "Pyruvate kinase",
            "Pyruvate Kinase",
            "beta-glucosidase",
            "Beta-glucosidase",
            "Carnitine acetyltransferase",
            "α-amylase",
            "α-Amylase",
            "Beta-lactamase",
            "Chitinase",
            "Carboxylesterase",
            "endo-1,4-beta-xylanase",
            "Endo-1,4-beta-xylanase",
            "Polyphenoloxidase",
            "Triacylglycerol lipase",
            "Lactate dehydrogenase",
            "N-acetyl-β-D-hexosaminidase",
            "pectate lyase",
            "Pectate lyase",
            "Pectinesterase",
            "superoxide dismutase",
            "Superoxide dismutase",
            "Superoxide Dismutase",
            "GSH transferase",
            "Protein kinase",
            "Kallikrein",
            "3-oxoacid CoA-transferase",
            "Alpha-galactosidase",
            "Carbonic anhydrase",
            "Leghaemoglobin",
            "Protein kinase, cAMP-dependent",
            "Purine nucleoside phosphorylase",
            "Triosephosphate isomerase",
            "Vitamin B12-binding protein",
            "Alcohol dehydrogenase",
            "Alcohol Dehydrogenase",
            "Cellulase",
            "Enolase",
            "glucose-6-phosphate dehydrogenase",
            "Glucose-6-phosphate dehydrogenase",
            "Nucleoside-diphosphate kinase B",
            "Ornithine transcarbamylase",
            "Renin",
            "α-mannosidase",
            "α-Mannosidase",
            "Beta-galactosidase",
            "Enterotoxin A",
            "Malate dehydrogenase",
            "Neuraminidase",
            "Thymidine kinase",
            "β-galactosidase",
            "β-Galactosidase",
            "γ-Glutamyl transpeptidase",
            "Acid phosphatase",
            "Calcium Binding protein",
            "Cytochrome c",
            "Cytochrome C",
            "Estrone sulfotransferase",
            "Interferon",
            "Phosphoglycerate kinase",
            "Prolactin",
            "Sphingomyelinase",
            "Albumin",
            "Antithrombin III",
            "Aspartate aminotransferase",
            "Enterotoxin B",
            "Erythrocruorin",
            "Glucan endo-1,3-beta-D-glucosidase",
            "Lactoperoxidase",
            "Lipoxygenase",
            "parvalbumin",
            "Parvalbumin",
            "Pepsin",
            "Phospholipase A",
            "Xylanase",
            "α-Fetoprotein",
            "Adenosine deaminase",
            "Angiotensinogen",
            "Arylsulphatase A",
            "Aryl β-glucosidase",
            "Chorionic gonadotrophin",
            "Dihydrofolate reductase",
            "Dnase",
            "Hypoxanthine-guanine phosphoribosyltransferase",
            "Nucleoside phosphorylase",
            "Phosphoglucomutase",
            "Phosphoglucose isomerase",
            "Protein-L-isoaspartate(D-aspartate) O-methyltransferase (PIMT2)",
            "β-Lactamase",
            "[3H]  Oestradiol-17β receptor",
            "4-phytase",
            "5'-nucleotidase",
            "5'-Nucleotidase",
            "Acetoacetyl-CoA thiolase",
            "Alpha-amylase",
            "Angiotensinogen (II)",
            "Bacitracin A",
            "Cathepsin B",
            "Chitosanase",
            "Choline acetyltransferase",
            "Cytochrome P-450",
            "Glyceraldehyde 3-phophate dehydrogenase",
            "Invertase",
            "Kallikrein",
            "Lactogen",
            "Leukotriene-A4 hydrolase",
            "Peroxiredoxin",
            "Phosphoglycerate mutase",
            "Phospholipase A2",
            "Phospholipid exchange protein",
            "Plasminogen",
            "Polypeptide p30",
            "Rhodopsin",
            "Serine-pyruvate aminotransferase",
            "Soluble epoxide hydrolase",
            "Acid phosphotase (type A)",
            "Alcohol oxidase",
            "Arylsulphatase B",
            "Bile-salt sulfotransferase",
            "DNA polymerase",
            "Esterase, non specific (NSE)",
            "Galactokinase",
            "Glutathione transferase",
            "Glyceraldehyde-3-phosphate dehydrogenase (phosphorylating)",
            "Hexokinase",
            "Hormone (luteinizing) (IR-LH)",
            "Hyaluronate lyase",
            "L-amino-acid oxidase",
            "Lipoyldehydrogenase",
            "Luteinizing hormone",
            "N-Acetylhexosaminidase",
            "Neutral Protease",
            "phosphodiesterase",
            "Phosphodiesterase",
            "Polygalacturonase",
            "Protease",
            "Ribonuclease",
            "Thyrotropin",
            "Urokinase",
            "Vitamin D-binding protein",
            "Vitellin",
            "δ-crystallin",
            "aldehyde dehydrogenase",
            "Aldehyde dehydrogenase",
            "Aldehyde Dehydrogenase",
            "Aldolase",
            "alpha-glucosidase",
            "Alpha-glucosidase",
            "Antigen hepatitis B corc and surface  (HBsAg)",
            "Ca2+ ATPase",
            "chitinase",
            "Chitinase",
            "Corticosteroid-binding protein",
            "Cytosol thyronine-binding protein",
            "Dnase B",
            "Endonuclease",
            "Factor IX (pool II)",
            "Fatty-acyl-ethyl-ester synthase",
            "Ferredoxin-NADP+ reductase",
            "Galactose-1-phosphate uridylyl transferase",
            "Glucan 1,4-alpha-glucosidase",
            "Glucose 1-dehydrogenase",
            "Glycogen phosphorylase b",
            "High-density lipoprotein",
            "Hydrogenase",
            "IMP: Pyrophosphate phosphoribosyl transferase",
            "Laccase B",
            "Ligandin",
            "Lipoprotein  (LDL fraction II)",
            "Lipoprotein (VLDL fraction II)",
            "Lysozyme",
            "Monophenol monooxygenase",
            "Nerve growth factor",
            "Norephinephrine N-methyl transferase",
            "Nucleoside diphosphokinase",
            "Nucleotide diphosphokinase",
            "Ovomucoid",
            "Ovotransferrin",
            "Phenylalanine hydroxylase",
            "Phytase",
            "Procarboxypeptidase A",
            "protease",
            "Protease",
            "Purine nucleoside phosphorylase (Subunits)",
            "rRNA N-glycosylase",
            "T cell replacing factor (TRF-II)",
            "Transferrin",
            "Transferrin TIC3",
            "α-1 antitrypsin",
            "α-Fucosidase",
            "α-Thrombin",
            "β-glucosidase",
            "β-Glucosidase",
            "Acetylcholinesterase (AChE)",
            "Acetyl-CoA: choline O-acetyltransferase",
            "Acid β-galactosidase",
            "Aldolase B",
            "Alkaline phosphatase (I varient)",
            "Apolipoprotein C-III",
            "Apolipoprotein E",
            "arylesterase",
            "Arylesterase",
            "Beta-amylase",
            "Beta-glucuronidase",
            "Cadmium-binding protein",
            "Chymotrypsin",
            "Chymotrypsinogen A",
            "Cobalophilin (Vitamin B-12 binding protein)",
            "Cobra venom factor",
            "Colony stimulating factor (CSF)",
            "Concanavalin A",
            "Creatine kinase",
            "Deoxyribonuclease",
            "Dextranase",
            "Dipeptidyl-peptidase II",
            "DNA polymerase-β",
            "Feruloyl esterase",
            "Galacturan 1,4-alpha-galacturonidase",
            "Glucagon-like polypeptide (I)",
            "Glutamate dehydrogenase",
            "Glutaryl-7-aminocephalosporanic-acid acylase",
            "Glutathione-S-arene oxidase transferase",
            "Glyceraldehyde-3-phosphate dehydrogenase",
            "Immunoglobulin G (monoclonal)",
            "Immunoreactive glucagon fraction",
            "Insulin",
            "L-Glutamylcyclotransferase",
            "Manganese peroxidase",
            "Mannanase",
            "Mannan endo-1,4-beta-mannosidase",
            "N-acetyl-β-D-hexosaminidase (B)",
            "Neurophysin precursor",
            "Oxoacyl CoA thiolase",
            "Pectin lyase",
            "Peroxidase (S1)",
            "Peroxidase (S3)",
            "Phenylalanine (histidine):pyruvate aminotransferase",
            "Phosphopyruvate hydratase",
            "Plasminogen activator",
            "Purine phosphoribosyltransferase",
            "Pyranose dehydrogenase (acceptor)",
            "Pyrethroid hydrolase",
            "Rh(e)-antigen",
            "RNA polymerase II",
            "Somatic extracts of adult worms (SEAW)",
            "Succinate thiokinase",
            "thioredoxin",
            "Thioredoxin",
            "Trypsin inhibitor",
            "Xylan 1,4-beta-xylosidase",
            "Xyloglucan:xyloglucosyl transferase",
            "α-Glycerol phosphate dehydrogenase",
            "β2-Microglobulin",
            "17α-Hydroxysteroid dehydrogenase",
            "3-phytase",
            "5-Aminolaevulinate synthetase",
            "6-phosphofructokinase",
            "Acetylcholinesterase",
            "Acid phosphotase (type B)",
            "Acid phosphotase (type C)",
            "Acid proteinase(Isorenin)",
            "Adenosylhomocysteinase",
            "Alcohol dehydrogenase (ADH)",
            "Alcohol dehydrogenase (NADP+)",
            "Aldehyde dehydrogenase",
            "Aldehyde dehydrogenase (NAD+)",
            "Antigen Rh (D)",
            "Apolipoprotein A-I",
            "Aryl-alcohol oxidase",
            "Arylamidase",
            "Arylsulphatase",
            "Arylsulphatase A",
            "beta-fructofuranosidase",
            "Beta-fructofuranosidase",
            "Beta-galactosidase I",
            "Beta-lactamase (Bla-B)",
            "Beta-mannosidase",
            "Cathepsin B1",
            "Cathepsin D I",
            "Cellulase (C1 component)",
            "Cholinergic Receptor",
            "Cholinesterase",
            "Cholinesterase",
            "Choloylglycine hydrolase",
            "Chymotrypsin like esterase",
            "Dextranase (isoenzyme 1)",
            "Diacetyl reductase",
            "Diisopropyl fluorophosphatase (DFPase)",
            "Estrogen receptor",
            "Factor IX (pool III)",
            "Fructose-1,6-diphosphatase I and II",
            "Fumarase",
            "Gentisate 1,2-dioxygenase",
            "Glucan endo-1,6-beta-glucosidase",
            "Glutamate decarboxylase",
            "glutathione reductase",
            "Glutathione reductase",
            "Glycerol 3-phosphate dehydrogenase",
            "Glycoprotein",
            "Gonadotropin",
            "Growth Hormone",
            "Herbage protein (Fraction II)",
            "Homoserine dehydrogenase",
            "Hormone (luteinizing)",
            "Hypoxanthine phosphoribosyltransferase",
            "Isomerase (acylthioester)",
            "Juvenile-hormone esterase",
            "laccase A",
            "Laccase A",
            "L-amino acid oxidase",
            "L-Amino acid oxidase",
            "L-Amino Acid oxidase",
            "L-Glutamate decarboxylase",
            "L-Glutaminase",
            "Licheninase",
            "Lipase",
            "Lipoamide dehydrogenase",
            "Lipoxydase (Lipoxygenase)",
            "Lysophospholipase",
            "Metalloproteins: Zn/Cu and Zn/Hg",
            "Mitogenic factor (MF)",
            "mRNA binding protein",
            "Myrosinase C",
            "N-acetyl-β-D-hexosaminidase (A)",
            "N-acylneuraminate cytidylyltransferase",
            "NADase (NAD glycohydrolase)",
            "Nicotinamide-nucleotide adenylyltransferase",
            "Nitrogenase",
            "P-enolpyruvate carboxykinase ferroactivator",
            "Phosphodiesterase II",
            "Phosphodiesterase-phosphomonoesterase",
            "Phosphofructokinase",
            "phospholipase A2",
            "Phospholipase A2",
            "Phospholipase D",
            "Phosphorylase b",
            "Phycoerythrin-546",
            "Phytohaemagglutinin",
            "Polynucleotide phosphorylase",
            "Polyribonucleotide nucleotidyltransferase",
            "Protamine kinase",
            "Renin substrate",
            "Rennett",
            "Serine proteinase",
            "(S)-norcoclaurine synthase",
            "Subtilisin (KV42)",
            "Sulphatase A",
            "Thyroid stimulating hormone (TSH)",
            "Thyrotropin (TSH)",
            "Thyroxine binding globulin",
            "Transcobalamin II-cyanocobalamin",
            "tRNA adenylyltransferase",
            "tyrosinase",
            "Tyrosinase",
            "Xyloglucan:xyloglucosyl transferase",
            "Zeins",
            "α1-Microglycoprotein",
            "β-Crystallin",
            "β-D-Glucosidase",
            "β-lactamase (penicillinase)",
            "β-N-Acetyl glucosaminidase",
            "γ-Crystallin",
            "2,3 Bisphosphoglycerate synthase",
            "2',3'-cyclic-nucleotide 3'-phosphodiesterase",
            "3-deoxy-manno-octulosonate cytidylyltransferase",
            "3-galactosyl-N-acetylglucosaminide 4-alpha-L-fucosyltransferase",
            "4-aminobutyrate transaminase",
            "4-Hydroxyphenylpyruvate dioxygenase",
            "4-oxalmesaconate hydratase",
            "Acetylcholine receptor protein (AChR)",
            "Acetylcholinesterase (minor form)",
            "Acetyl-CoA acetyltransferase ( I, A, B)",
            "Acid protease",
            "Aconitase",
            "Aconitate hydratase",
            "Adenosine deaminase",
            "Alanine-glyoxylate transaminase",
            "alcohol dehydrogenase",
            "Alcohol dehydrogenase",
            "Aldehyde dehydrogenase (Pyrroloquinoline-quinone)",
            "Aldose Reductase",
            "Alpha,alpha-trehalase",
            "Alpha,alpha-trehalose-phosphate synthase (UDP-forming)",
            "alpha-L-rhamnosidase",
            "Alpha-L-rhamnosidase",
            "Alpha-N-arabinofuranosidase",
            "Aminoazo dye binding protein A",
            "Aminolevulinate transaminase",
            "aminopeptidase",
            "Aminopeptidase",
            "Aminopeptidase B",
            "Apolipoprotein CIII",
            "Apyrase",
            "Aryldialkylphosphatase",
            "Bacteriorhodopsin",
            "Beta-lactamase (Bla-A)",
            "Biotin-independent malonate decarboxylase",
            "B Phycoerythrin",
            "Carboxylesterase E",
            "catechol oxidase",
            "Catechol oxidase",
            "Catechol oxidase (tyrosinase)",
            "Cathepsin D",
            "Cathepsin D II",
            "Cathepsin S",
            "Cellobiose quinone oxido-reductase",
            "Cholesterol esterase",
            "Conalbumin (Native)",
            "Conalbumin (γ-irradiated)",
            "Creatine kinase (CK): MM isoenzymes",
            "Creatine phosphokinase",
            "cx-Flagellin",
            "Cyclic AMP Dependent Protein Kinase",
            "Cyclic AMP phosphodiesterase",
            "Cyclooxygenase, Prostoglandin-forming",
            "Cytochrome c peroxidase",
            "Cytosol receptors for testosterone",
            "DDT dehydrochlorinase",
            "Deacetoxycephalosporin-C hydroxylase",
            "Deacetoxycephalosporin-C synthase",
            "Dextranase (isoenzyme 2)",
            "Dipeptidyl-peptidase IV",
            "DNA-binding protein2",
            "DNA-directed DNA polymerase",
            "Elastase",
            "Endopolygalacturonase",
            "Enolase A",
            "Erabutoxin c",
            "Erabutoxin C",
            "Factor IX (pool I)",
            "Ferredoxin hydrogenase",
            "Fibrinogen",
            "Flavocytochrome C",
            "Folate-binding protein",
            "Fructokinase",
            "Fucosyl transferase",
            "Glucagon-like polypeptide (III)",
            "Glucosamine-6-phosphate deaminase",
            "Glucosaminephosphate isomerase",
            "Glutathione S-transferase",
            "Glyoxalase (I)",
            "Green haemoprotein",
            "Haemagglutinin",
            "Haptoglobin",
            "Histidine decarboxylase",
            "Hormone (growth)",
            "I-Antigen 51 A",
            "Immunoglobulin M (H chains)",
            "Isoleucine aminopeptidase",
            "Kininogen",
            "Laccase (Lac I)",
            "Laccase (Lac II)",
            "L-Arabinose isomerase",
            "L-Asparaginase",
            "Lipoprotein  (LDL fraction I)",
            "Lipoprotein (VLDL fraction I)",
            "L-tyrosine methyl ester sulphotransferase",
            "Malate dehydrogenase (oxaloacetate-decarboxylating [NADP+])",
            "Mannitol 2-dehydrogenase (NADP+)",
            "Mannosyl-oligosaccharide glucosidase",
            "Metalloprotein",
            "Mevalonate kinase",
            "Myosin",
            "NAD+ ADP-ribosyltransferase",
            "NADH dehydrogenase",
            "NADP isocitrate dehydrogenase",
            "Neocarzinostatin",
            "Nitrate reductase",
            "Nucleoside phosphotransferase",
            "o-Diphenol-oxygen-oxidoreductase",
            "Pectinesterase",
            "Pectolytic enzyme-stimulating factor",
            "Penicillin amidase",
            "Phosphoglucose isomerase (Singh variant)",
            "Phospholipase C",
            "Phosphorylase a",
            "Photosystem I",
            "Plasminogen (F-1)",
            "Plasminogen (F-2)",
            "Poly(3-hydroxybutyrate) depolymerase"
        ];

    // The individual words within the 500 most common protein names
    var proteinNameWords =
        proteinNames.join(" ").toLowerCase().split(" ").filter(function(e) {
            return e.length > 2;
        });

    // Most common sources
    var sources = [
        "Ambystoma mexicanum (Axolotl)",
        "Arabidopsis thaliana (Mouse-ear cress)",
        "Armoracia rusticana (Horseradish)",
        "Ascaris suum (pig roundworm)",
        "Ascaris suum (Pig roundworm)",
        "Aspergillus niger",
        "Aspergillus oryzae",
        "Azotobacter vinelandii",
        "Bacillus licheniformis",
        "Bacillus species",
        "Bacillus subtilis",
        "Bos taurus (Beef)",
        "Bos taurus (Bovine)",
        "Bos taurus (Calf)",
        "Bos taurus (Cow)",
        "Bos taurus (Ox)",
        "Bos taurus",
        "Canis familiaris (Dog)",
        "Canis lupus familiaris (Dog)",
        "Cavia porcellus (Guinea Pig)",
        "Chrysosporium lucknowense",
        "Citrus sinensis (Sweet orange)",
        "Clostridium perfringens",
        "Cricetulus griseus (Chinese hamster)",
        "Cricetulus griseus (Chinese Hamster)",
        "Cucurbitaceae",
        "Dipetalonema viteae",
        "Drosophila melanogaster (Fruit fly)",
        "E.coli",
        "Equus caballus (Horse)",
        "Equus caballus",
        "Escherichia coli",
        "Escherichia coli",
        "Euroglyphus maynei",
        "Fasciola hepatica (Liver fluke)",
        "Fasciola hepatica",
        "Fomes annosus",
        "Gallus gallus (Chicken)",
        "Gallus gallus (Hen)",
        "Glycera dibranchiata (Bloodworm)",
        "Glycera gigantea (Bloodworm)",
        "Glycine max (Soybean)",
        "Hamster",
        "Homo sapiens (Human)",
        "Homo sapiens",
        "Homo sapiens",
        "Hordeum vulgare (Barley)",
        "Human",
        "Human",
        "Hypocrea jecorina",
        "Klebsiella pneumoniae",
        "Lecanicillium longisporum",
        "Lipomyces starkeyi (Oleaginous yeast)",
        "Monkey",
        "Mus musculus (Mouse)",
        "Mus musculus",
        "Mushroom",
        "Neurospora crassa",
        "Nicotiana tabacum (Common tobacco)",
        "Oryctolagus cuniculus (Rabbit)",
        "Oryctolagus cuniculus (Rabbit)",
        "Oryza sativa (Rice)",
        "Ovis aries (Sheep)",
        "Phlebia radiata (White-rot fungus)",
        "Physeter macrocephalus (Sperm-Whale)",
        "Pisum sativum (Garden pea)",
        "Pisum sativum (Garden Pea)",
        "Pisum sativum (Pea)",
        "Pisum sativum",
        "Polyporus versicolor",
        "Porphyromonas gingivalis",
        "Pseudomonas aeruginosa",
        "Pseudomonas fluorescens",
        "Pseudomonas putida",
        "Pseudopleuronectes americanus (Fish)",
        "Rana temporaria (Frog)",
        "Rattus norvegicus (Rat)",
        "Rattus norvegicus",
        "Saccharomyces cerevisiae (Baker's yeast)",
        "Saccharomyces cerevisiae (Baker's Yeast)",
        "Saccharomyces cerevisiae (Yeast)",
        "Saccharomyces cerevisiae",
        "Solanum lycopersicum (Tomato)",
        "Solanum lycopersicum (Tomato)",
        "Solanum tuberosum (Potato)",
        "Spinacia oleracea (Spinach)",
        "Staphylococcus aureus",
        "Streptococci (Group A /Streptococcus pyogenes)",
        "Streptomyces griseus",
        "Sus scrofa (Hog)",
        "Sus scrofa (Pig)",
        "Sus scrofa (Porcrine)",
        "Thermoplasma acidophilum",
        "Triticum aestivum (Wheat)",
        "Yeast",
        "Zea mays (Maize)"
    ];

    // Most common locations
    var locations = [
        "Adipose Tissue",
        "Amniotic fluid",
        "Amniotic Fluid",
        "Anterior pituitary",
        "Brain",
        "Brain",
        "Cell culture",
        "Cell wall",
        "Cerebellum",
        "Cerebrum",
        "Coelomic cells",
        "Colon",
        "Colonic carcinoma",
        "Colonic Carcinoma",
        "Cytoplasm",
        "Cytosol",
        "Egg",
        "Embryos",
        "Epididymis",
        "Erythrocyte membrane",
        "Erythrocyte",
        "Erythrocytes",
        "Extracellular",
        "Fibroblasts",
        "Fibroblasts, leucocytes, amniotic fluid",
        "Foetal liver",
        "Foetal Liver",
        "Fruit",
        "Fruiting bodies",
        "gastric mucosa",
        "Gastric mucosa",
        "Haemolymph",
        "Heart",
        "HeLa cells",
        "Intestine",
        "Intestine",
        "KB cells",
        "Kidney",
        "Larva",
        "Larvae",
        "Latex",
        "Leaf",
        "Leaves",
        "Lens",
        "Leukocytes",
        "Liver cytosol",
        "Liver lysosomes",
        "Liver Mitochondria",
        "Liver",
        "Liver",
        "Lung",
        "Melon (Infected)",
        "Milk",
        "Muscle",
        "Mycelium",
        "Nodules",
        "Pancreas",
        "Pancreatic juice",
        "Parotid",
        "Pituitary",
        "Placenta",
        "Plasma apo-VLDL",
        "Plasma",
        "Pollen",
        "Red Blood Cells",
        "Retina",
        "Root",
        "Saliva",
        "Seedling",
        "Seeds",
        "Seminal Plasma",
        "Serum apoHDL, apoVLDL",
        "Serum",
        "Shoot",
        "Skeletal Muscle",
        "Skin fibroblasts",
        "Sperm",
        "Spleen",
        "Stem",
        "Testis",
        "Two basic isoforms",
        "Urine",
        "Venom",
        "Wool"
    ];

    /*
     * Provide protein name suggestions.
     */
    $('#q, input[name="q_eq"]').autocomplete({
        source: function(request, response) {
            var results = $.ui.autocomplete.filter(proteinNames,
                                                   request.term);

            response(results.slice(0, 10));
        }
    });

    /*
     * Provide protein name word suggestions.
     */
    $('input[name="q_any"], input[name="q_ne"]').autocomplete({
        source: function(request, response) {
            var results = $.ui.autocomplete.filter(proteinNameWords,
                                                   request.term);

            response(results.slice(0, 10));
        }
    });

    /*
     * Provide source suggestions.
     */
    $('input[name="q_s"]').autocomplete({
        source: function(request, response) {
            var results = $.ui.autocomplete.filter(sources,
                                                   request.term);

            response(results.slice(0, 10));
        }
    });

    /*
     * Provide location suggestions.
     */
    $('input[name="q_l"]').autocomplete({
        source: function(request, response) {
            var results = $.ui.autocomplete.filter(locations,
                                                   request.term);

            response(results.slice(0, 10));
        }
    });

}());
