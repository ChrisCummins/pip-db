function Tuple(isHeader) {

  function rchance(chance) {
    return Math.random() < chance;
  }

  /* Return random entry from array */
  function rentry(array) {
    return payload[array][Math.floor(Math.random() * (array.length + 1))];
  }

  function rint(low, high) {
    return Math.floor(Math.random() * (high - low + 1) + low);
  }

  function rstring(length) {
    var string = '';
    var space = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz01234567890';

    for (var i = 0; i < length; i++)
      string += space.charAt(rint(0, space.length));

    return string;
  }

  function rec() {
    return (rint(1, 3) + '.' +
            rint(1, 10) + '.' +
            rint(1, 20) + '.' +
            rint(1, 120));
  }

  function rbinomial() {
    var genus = rentry('latin');
    var species = rentry('latin');

    return genus + ' ' + species;
  }

  function rmw() {
    return rint(23, 150) * 1000;
  }

  function rpi() {
    return (Math.random() * 14).toFixed(2);
  }

  if (isHeader) {
    /* Generate header titles */
    this.p = {
      'dataset' : 'Dataset',
      'ec' : 'E.C.',
      'protein' : 'Protein',
      'alt' : 'Alternative name(s)',
      'source' : 'Source',
      'organ' : 'Organ',
      'mw' : 'M.W',
      'subno' : 'Subunut No.',
      'submw' : 'Subunut M.W',
      'isoenzymes' : 'No. of Iso-enzymes',
      'piMax' : 'pI Maximum',
      'piRangeMin' : 'pI Range min',
      'piRangeMax' : 'pI Range max',
      'piMajor' : 'pI value of major component',
      'pi' : 'pI',
      'temp' : 'Temperature',
      'method' : 'Method',
      'valid' : 'Valid sequence(s) available',
      'sequence' : 'Protein Sequence',
      'species' : 'Species Taxonomy',
      'full' : 'Full Text',
      'abstract' : 'Abstract',
      'pubmed' : 'PubMed',
      'notes' : 'Notes'
    };

  } else {

    this.p = {
      'dataset' : '',
      'ec' : '',
      'protein' : '',
      'alt' : '',
      'source' : '',
      'organ' : '',
      'mw' : '',
      'subno' : '',
      'submw' : '',
      'isoenzymes' : '',
      'piMax' : '',
      'piRangeMin' : '',
      'piRangeMax' : '',
      'piMajor' : '',
      'pi' : '',
      'temp' : '',
      'method' : '',
      'valid' : '',
      'sequence' : '',
      'species' : '',
      'full' : '',
      'abstract' : '',
      'pubmed' : '',
      'notes' : ''
    };

    this.p['dataset'] = rentry('datasets');

    this.p['ec'] = rchance(0.8) ? rec() : '';

    this.p['protein'] = rentry('proteins');

    this.p['alt'] = rchance(0.9) ? rentry('proteins') : '';

    this.p['source'] = rbinomial();

    this.p['organ'] = rchance(0.6) ? rentry('organs') : '';

    this.p['mw'] = rchance(0.9) ? rmw() : '';

    if (rchance(0.3)) {
      this.p['subno'] = rint(1, 148);
      this.p['submw'] = rmw();
    }

    this.p['isoenzymes'] = rchance(0.9) ? rint(1, 10000) : '';

    if (rchance(0.95)) {
      if (rchance(0.8))
        this.p['pi'] = rpi();
      else {
        if (rchance(0.5))
          this.p['piMax'] = rpi();
        else if (rchance(0.5))
          this.p['piMajor'] = rpi();
        else {
          this.p['piRangeMin'] = rpi();
          this.p['piRangeMax'] = rpi();
        }
      }
    }

    if (rchance(0.8)) {
      if (rchance(0.7))
        this.p['temp'] = rint(10, 30) + 'C';
      else {
        this.p['temp'] = rint(10, 20) + ' - ' + rint(21, 30) + 'C';
      }
    }

    this.p['method'] = rchance(0.8) ? rentry('methods') : 'Not available';

    this.p['valid'] = rentry('validsequences');

    this.p['sequence'] = rchance(0.6) ?
      'http://www.ncbi.nlm.nih.gov/protein/' + rstring(8) : '';

    this.p['species'] = rchance(0.95) ?
      'http://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?lvl=0&id='
      + rstring(8) : '';

    if (rchance(0.8)) {
      if (rchance(0.95))
        this.p['full'] = 'http://www.ncbi.nlm.nih.gov/pmc/articles/PMC1177738/pdf/biochemj' + rstring(8);
      else
        this.p['abstract'] = 'http://www.sciencedirect.com/science/article/pii/' + rstring(16);
    }

    if (rchance(0.9))
      this.p['pubmed'] = 'http://www.ncbi.nlm.nih.gov/pubmed/' + rstring(8);

    if (rchance(0.02))
      this.p['notes'] = rentry('notes');
  }
}

Tuple.prototype.toCSV = function() {
  var string = '';

  for (var key in this.p) {
    string += this.p[key] + '\t';
  }

  return string;
}
