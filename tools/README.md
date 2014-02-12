# Tools

Auxiliary tools which may be useful during development and testing but are not
required for the production system.

## Table of Contents

* [dsa - Dataset Analyser](#dsa)
* [png - Plausible Nonsense Generator](#png)
* [sloccount - Source code line count](#sloccount)

## dsa
    Usage: ./dsa <csv-dataset>

An automated dataset analysis tool.

### Example Usage

    $ ./dsa ../png/dataset.csv
    Dataset: '../png/dataset.csv' (512 records)

    +-----------------------------+-----------+---------+--------+--------+
    | Row                         | Populated | %       | Unique | %      |
    +-----------------------------+-----------+---------+--------+--------+
    | Dataset                     | 512       | 100.00% | 9      | 1.76%  |
    | E.C.                        | 490       | 95.70%  | 490    | 95.70% |
    | Protein                     | 511       | 99.80%  | 10     | 1.95%  |
    | Alternative name(s)         | 335       | 65.43%  | 10     | 1.95%  |
    | Source                      | 512       | 100.00% | 36     | 7.03%  |
    | Organ                       | 351       | 68.55%  | 8      | 1.56%  |
    | M.W                         | 289       | 56.45%  | 116    | 22.66% |
    | Subunut No.                 | 144       | 28.12%  | 93     | 18.16% |
    | Subunut M.W                 | 177       | 34.57%  | 101    | 19.73% |
    | No. of Iso-enzymes          | 305       | 59.57%  | 299    | 58.40% |
    | pI Maximum                  | 1         | 0.20%   | 2      | 0.39%  |
    | pI Range min                | 5         | 0.98%   | 6      | 1.17%  |
    | pI Range max                | 5         | 0.98%   | 6      | 1.17%  |
    | pI value of major component | 79        | 15.43%  | 77     | 15.04% |
    | pI                          | 427       | 83.40%  | 360    | 70.31% |
    | Temperature                 | 141       | 27.54%  | 59     | 11.52% |
    | Method                      | 459       | 89.65%  | 9      | 1.76%  |
    | Valid sequence(s) available | 512       | 100.00% | 15     | 2.93%  |
    | Protein Sequence            | 321       | 62.70%  | 322    | 62.89% |
    | Species Taxonomy            | 495       | 96.68%  | 496    | 96.88% |
    | Full Text                   | 308       | 60.16%  | 309    | 60.35% |
    | Abstract                    | 174       | 33.98%  | 175    | 34.18% |
    | PubMed                      | 471       | 91.99%  | 472    | 92.19% |
    | Notes                       | 7         | 1.37%   | 5      | 0.98%  |
    +-----------------------------+-----------+---------+--------+--------+
    24 records returned

## png
    Usage: ./png
               -o --output [file]     Output to file
               -f --format [format]   Output format
               -c --conf [file]       Use configuration file
               -p --payload [file]    Use payload file
               -s --size [number]     Number of records to generate

A program for generating nonsense datasets for testing purposes.

### Example Usage

    $ ./png --debug -s 16 -c ./pngrc -o test.csv
    debug: using payload file: './png-payload.js'
    debug: using configuration file: './pngrc'
    debug: writing output to file: 'test.csv'
    debug: generating 16 records
    debug: output file format: CSV
    $ wc -l test.csv
    17 test.csv

## sloccount
    Usage: ./sloccount

A script to count the physical source lines of code (SLOC) contained in the
project.

### Example Usage

    $ ./sloccount
    pip-db 0.0.2 - Source lines of code
    Wed Nov 20 01:32:20 GMT 2013


    Build system: 1164
    491  configure.ac  (42.18%)
    480  Makefile.am   (41.24%)
    193  autogen.sh    (16.58%)

    Page sources: 11622
    7593  Less CSS    (65.33%)
    2030  HTML        (17.47%)
    1999  JavaScript  (17.20%)

    Controller sources: 1206
    721  PHP (lib)    (59.78%)
    485  PHP (pages)  (40.22%)

    External libraries: 14151
    14151  PHP (twig)  (100.00%)

    Documentation: 2980
    1764  Markdown  (59.19%)
    1216  LaTeX     (40.81%)

    Tools: 2219
    1516  JavaScript  (68.32%)
    703   Shell       (31.68%)

    Total physical source lines of code: 33342
