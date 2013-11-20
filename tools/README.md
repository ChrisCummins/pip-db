# Tools

Auxiliary tools which may be useful during development and testing but are not
required for the production system.

## Table of Contents

* [dsa - Dataset Analyser](#dsa)
* [mkrelease - Project release generator](#mkrelease)
* [png - Plausible Nonsense Generator](#png)
* [sloccount - Source code line count](#sloccount)
* [workflow - Workflow scripts](#workflow)

## dsa
    Usage: ./dsa <csv-dataset>

An automated dataset analysis tool.

### Example

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

## mkrelease
    Usage: ./mkrelease <version>

Creates a release branch/tag for the current version and bumps the project
version to `<version>`.

### Example

    $ ./mkrelease 0.0.2
    Getting current version... '0.0.1'
    Creating release branch... 'release/0.0.1'
    Counting objects: 82, done.
    Compressing objects: 100% (78/78), done.
    Writing objects: 100% (78/78), 7.97 KiB, done.
    Total 78 (delta 57), reused 0 (delta 0)
    To git@github.com:ChrisCummins/protein-db.git
     * [new branch]      release/0.0.1 -> release/0.0.1
    Creating release tag... '0.0.1'
    Counting objects: 1, done.
    Writing objects: 100% (1/1), 169 bytes, done.
    Total 1 (delta 0), reused 0 (delta 0)
    To git@github.com:ChrisCummins/protein-db.git
     * [new tag]         0.0.1 -> 0.0.1
    Updating version string... 'configure.ac'
    Creating version bump commit... '0.0.2'

## png
    Usage: ./png
               -o --output [file]     Output to file
               -f --format [format]   Output format
               -c --conf [file]       Use configuration file
               -p --payload [file]    Use payload file
               -s --size [number]     Number of records to generate

A program for generating nonsense datasets for testing purposes.

### Example

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

## workflow
    Usage: ./worflow <command> [args]

    Commands:
           n | new <issue-number>   Begin work on a new issue
           p | pause                Pause work on the current issue
           c | close                Complete work on the current issue

Automated workflow script for managing git branches.

### Example

    $ ./tools/workflow new 10
    Switched to a new branch 'wip/10'
    Counting objects: 59, done.
    Compressing objects: 100% (53/53), done.
    Writing objects: 100% (54/54), 6.50 KiB, done.
    Total 54 (delta 38), reused 0 (delta 0)
    To git@github.com:ChrisCummins/protein-db.git
     * [new branch]      wip/10 -> wip/10
    Branch wip/10 set up to track remote branch wip/10 from origin.

    Execute './workflow close' when completed.
    $ echo 'my hotfox' > fix
    $ git commit -a -m 'Fix for #10'
    [wip/10 672b654] Fix for #10
     1 file changed, 1 insertion(+)
     create mode 100644 tools/fix
    $ ./workflow close
    Switched to branch 'master'
    Your branch is ahead of 'origin/master' by 1 commit.
    First, rewinding head to replay your work on top of it...
    Fast-forwarded master to wip/10.
    Deleted branch wip/10 (was 672b654).
    To git@github.com:ChrisCummins/protein-db.git
     - [deleted]         wip/10

    Merged 'wip/10' into 'master'
