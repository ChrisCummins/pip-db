# Tools

Auxiliary tools which may be useful during development and testing but are not
required for the production system.

## Table of Contents

* [dsa - Dataset Analyser](#dsa)
* [png - Plausible Nonsense Generator](#png)

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
