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


/**
 * Auto-growing textareas; technique ripped from Facebook
 *
 * http://github.com/jaz303/jquery-grab-bag/tree/master/javascripts/jquery.autogrow-textarea.js
 */
$.fn.autogrow = function(options) {
  return this.filter('textarea').each(function() {
    var self = this;
    var $self = $(self);
    var minHeight = $self.height();
    var noFlickerPad = $self.hasClass('autogrow-short') ? 0 :
      parseInt($self.css('lineHeight')) || 0;

    var shadow = $('<div></div>').css({
      position: 'absolute',
      top: -10000,
      left: -10000,
      width: $self.width(),
      fontSize: $self.css('fontSize'),
      fontFamily: $self.css('fontFamily'),
      fontWeight: $self.css('fontWeight'),
      lineHeight: $self.css('lineHeight'),
      resize: 'none',
      'word-wrap': 'break-word'
    }).appendTo(document.body);

    var update = function(event) {
      var times = function(string, number) {
        for (var i = 0, r = ''; i < number; i++) r += string;
        return r;
      };

      var val = self.value.replace(/</g, '&lt;')
        .replace(/>/g, '&gt;')
        .replace(/&/g, '&amp;')
        .replace(/\n$/, '<br/>&nbsp;')
        .replace(/\n/g, '<br/>')
        .replace(/ {2,}/g, function(space) {
          return times('&nbsp;', space.length - 1) + ' ';
        });

      // Did enter get pressed?  Resize in this keydown event so that the
      // flicker doesn't occur.
      if (event && event.data && event.data.event === 'keydown' &&
          event.keyCode === 13) {
        val += '<br />';
      }

      shadow.css('width', $self.width());
      // Append '...' to resize pre-emptively.
      shadow.html(val + (noFlickerPad === 0 ? '...' : ''));
      $self.height(Math.max(shadow.height() + noFlickerPad, minHeight));
    };

    $self.change(update).keyup(update).keydown({event: 'keydown'},update);
    $(window).resize(update);

    update();
  });
};


/*
 * CUSTOM PIP-DB JAVASCRIPT
 */
(function () {
    'use strict';

    // UI Elements

    var $resultsCountProgressBar = $('#results-count');
    var $resultsLabel = $(' .ui-progressbar-label', $resultsCountProgressBar);

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
        'm': '',
        't_l': '',
        't_h': '',
        'seq': '',
        'f-name': ''
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

    /*
     * Form submission callback.
     */
    $searchForms.submit(function (e) {

        // GET requests:
        if ($(this).attr('method').toLowerCase() === 'get') {
            e.preventDefault();

            var items = stripDefaultValues($(this).serializeArray(),
                                           searchFormDefaults);

            // Only submit form if we have unique values:
            if (items.length) {
                window.location = $(this).attr('action') +
                    '?' + $.param(items.values);
            }
        // POST requests:
        } else {
            var text = $('#seq').val();
            var file = $('#f').val();

            // Only submit form if we have unique values:
            if (!text && !file)
                e.preventDefault();
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
        updateNoOfResults($(this).closest('form'));
    });

    // Bind form validation to dropdown selections
    $(' select', $searchForms).change(function (e) {
        activateSubmitIfFormFilled($(this).closest('form'));
        updateNoOfResults($(this).closest('form'));
    });

    // BLAST+ FASTA sequence file select callback:
    $('#f').change(function (e) {
        var newFile = $(this).val();

        $('#f-name').val(newFile);
        activateSubmitIfFormFilled($(this).closest('form'));
    });

    // Validate form on load (in case of preloaded criteria)
    activateSubmitIfFormFilled($searchForms);

    /*
     * PI SLIDER
     */

    var $slider = $('#pi-slider');
    var defaultSliderValues = [4, 10]; // Starting points for slider

    // Slider update callback
    var updateSliderTooltip = function (event, ui) {

        // Create tooltip HTML
        var tooltipHTML = function (value) {
            return '<div class="slider-tooltip">' +
                '<div class="slider-tooltip-inner">' +
                value + '</div>' + '<div class="slider-tooltip-arrow">' +
                '</div></div>';
        };

        var values = ui.values || defaultSliderValues;

        $('.ui-slider-handle:first-of-type').html(tooltipHTML(values[0]));
        $('.ui-slider-handle:last-of-type').html(tooltipHTML(values[1]));
    }

    $(' .ui-slider-handle', $slider).html(updateSliderTooltip)

    // Create our slider
    $slider.labeledslider({
        disabled: true,
        range: true,
        min: 0,
        max: 14,
        step: 0.1,
        values: defaultSliderValues,
        tickInterval: 1,
        create: updateSliderTooltip,
        slide: function (event, ui) {
            setFormValuesFromSlider();
            updateSliderTooltip(event, ui);
            updateNoOfResults($(this).closest('form'));
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
        } else {
            $(this).text('Off');
            $(this).addClass('btn-primary');
            $(this).removeClass('btn-warning');
            $slider.labeledslider('option', 'disabled', true);
        }

        // Update form
        setFormValuesFromSlider();
        activateSubmitIfFormFilled($(this).closest('form'));
        updateNoOfResults($(this).closest('form'));
    });

    /*
     * "No of results" progress bar
     */

    // Initialise jQuery UI component
    $($resultsCountProgressBar).progressbar({
        value: 100
    });

    /*
     * Updates the number of results returned for query value.
     */
    var updateNoOfResults = function ($form) {
        var items = stripDefaultValues($form.serializeArray(),
                                       searchFormDefaults);
        var url = '/api/s?' + $.param(items.values);

        $.ajax({
            url: url,
            beforeSend: function(xhr) {
                // Set API headers
                xhr.setRequestHeader('X-pip-db-Query-Terms', 'None');
                xhr.setRequestHeader('X-pip-db-Records', 'None');
            },
            success: function (response, textStatus, jqXHR) {
                var noOfRecordsSearched = response['No-Of-Records-Searched'];
                var noOfRecordsMatched  = response['No-Of-Records-Matched'];
                var percentage = (Math.log(noOfRecordsMatched + 1) /
                                  Math.log(noOfRecordsSearched + 1)) * 100;
                var label = noOfRecordsMatched === 1 ?
                    '1 record' : noOfRecordsMatched + ' records';

                $resultsCountProgressBar.progressbar("value", percentage);
                $resultsLabel.text(label);
            }
        });
    }

    // Get initial value on load
    updateNoOfResults($searchForms);

    /*
     * EXPERIMENTAL METHOD COMBO
     */

    var methodActive = false; // Keep track of whether method select is active
    var $methodSelector = $('#m-select');

    // Update hidden form inputs
    var setFormValuesFromMethod = function () {
        if (methodActive) {
            $('#m').val($(' option:selected', $methodSelector).text());
        } else {
            $('#m').val('');
        }
    };

    // Experimental method button press
    $('#m-active').click(function (e) {
        methodActive = !methodActive;

        if (methodActive) {
            $(this).text('On');
            $(this).addClass('btn-warning');
            $(this).removeClass('btn-primary');
            $methodSelector.removeAttr('disabled');
        } else {
            $(this).text('Off');
            $(this).addClass('btn-primary');
            $(this).removeClass('btn-warning');
            $methodSelector.attr('disabled', true);
        }

        // Update form
        setFormValuesFromMethod();
        activateSubmitIfFormFilled($(this).closest('form'));
        updateNoOfResults($(this).closest('form'));
    });

    $methodSelector.change(function (e) {
        setFormValuesFromMethod();
    });

    /*
     * AUTO-COMPLETE
     */

    /*
     * Return the autocomplete API URL for table 'src' and with text
     * 'value'.
     */
    var getAutocompleteUrl = function (src, value) {
        return '/api/ac?s=' + src + '&t=' + encodeURIComponent(value.trim());
    };

    /*
     * Provide protein name suggestions.
     */
    $('#q, input[name="q_eq"]').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: getAutocompleteUrl('names', request.term),
                success: function (data, textStatus, jqXHR) {
                    response(data);
                },
                error: function (jqXHR, textStatus, err) {
                    response([]);
                }
            });
        },
        close: function () {
            updateNoOfResults($(this).closest('form'));
        }
    });

    /*
     * Provide protein name word suggestions.
     */
    $('input[name="q_any"], input[name="q_ne"]').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: getAutocompleteUrl('words', request.term),
                success: function (data, textStatus, jqXHR) {
                    response(data);
                },
                error: function (jqXHR, textStatus, err) {
                    response([]);
                }
            });
        },
        close: function () {
            updateNoOfResults($(this).closest('form'));
        }
    });

    /*
     * Provide source suggestions.
     */
    $('input[name="q_s"]').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: getAutocompleteUrl('sources', request.term),
                success: function (data, textStatus, jqXHR) {
                    response(data);
                },
                error: function (jqXHR, textStatus, err) {
                    response([]);
                }
            });
        },
        close: function () {
            updateNoOfResults($(this).closest('form'));
        }
    });

    /*
     * Provide location suggestions.
     */
    $('input[name="q_l"]').autocomplete({
        source: function(request, response) {
            $.ajax({
                url: getAutocompleteUrl('locations', request.term),
                success: function (data, textStatus, jqXHR) {
                    response(data);
                },
                error: function (jqXHR, textStatus, err) {
                    response([]);
                }
            });
        },
        close: function () {
            updateNoOfResults($(this).closest('form'));
        }
    });

  /*
   * Provide method suggestions.
   */
  $('input[name="m"]').autocomplete({
    source: function(request, response) {
      $.ajax({
        url: getAutocompleteUrl('methods', request.term),
        success: function (data, textStatus, jqXHR) {
          response(data);
        },
        error: function (jqXHR, textStatus, err) {
          response([]);
        }
      });
    },
    close: function () {
      updateNoOfResults($(this).closest('form'));
    }
  });

  /*
   * FASTA input form growing.
   */
  $('#seq').autogrow();

}());
