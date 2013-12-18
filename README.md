# pip-db

This repository contains the Protein Isoelectric Point Database, a website which
houses data accumulated by members of Aston University's Life and Health
Sciences department. The purpose of the website is to provide a powerful and
easy-to-usesearc engine for the mass of biological data which has been acquired,
making the valuable research accessible to all.

The project is developed by Chris Cummins (http://chriscummins.cc) as part of an
ongoing assessment towards an MEng Electronic Engineering & Computer Science
course at the University of Aston.
[Read the project plan](Documentation/ProjectPlan.md).

## Background

Bioinformatics is a multidisciplinary field which uses computational methods to
aid in biological research by creating systems for storing, organising and
analysing complex biological data. Within this field there are many online
databases categorising biological information at the molecular level, and one
such purpose of these is for storing the functional and physical properties of
proteins. Currently, no such database exists for one of the most widely-used,
important, and useful properties of proteins: the isoelectric point (pI). An
isoelectric point is the acidity (pH) at which a molecule carries no net charge;
below the isoelectric point, proteins have a net positive charge, above it a net
negative charge. Additionally, proteins are at their lowest solubility at their
isoelectric point, and this makes the isoelectric point a vitally important
property when both characterising and purifying proteins.

The dataset which has been compiled is a collection of entries stored as a
non-relational table, and for each entry it records the name of the protein, its
identity, origin, experimental conditions, its isoelectric point, and other
pertinent data. There are also links to a heterogeneous collection of databases
containing associated data, such as amino acid sequence, function, etc. A
web-accessible database that warehouses this data and offers a robust and
adaptable GUI for searching, viewing and downloading results would greatly
increase the accessibility of the dataset. For more detailed information about
pip-db, see [the documentation](Documentation/).

## Building the website

The website should build under most GNU/Linux environments, using the standard
GNU Autotools procedure:

```sh
$ ./autogen.sh
$ ./configure
$ make all
```

See `./configure --help` for a list of configuration options for building the
website.

### Requirements
* [Autoconf](http://www.gnu.org/software/autoconf/)
* [Automake](http://www.gnu.org/software/automake/)
* [Java](http://www.java.com/en/)
* [Node.js](http://nodejs.org/)
* [Less](http://lesscss.org/)
* [Git](http://git-scm.com/) *(optional, required only to build the extra tools)*
* [npm](https://npmjs.org/) *(optional, required only to build the extra tools)*
* [node-optimist](https://github.com/substack/node-optimist) *(optional, required only to build the extra tools)*
* [Python](http://www.python.org/) *(optional, required only to build the extra tools)*
* [pdftex](http://www.tug.org/applications/pdftex/) *(optional, required only to build the extra documentation)*
* [pandoc](http://johnmacfarlane.net/pandoc/) *(optional, required only to build the extra documentation)*
* [doctoc](https://github.com/thlorenz/doctoc) *(optional, required only to build the extra documentation)*

## Hosting the website

The website can be deployed on a LAMP stack, by exporting the contents of the
`/build/www` directory.

### Requirements
* [Apache](http://www.apache.org/)
* [MySQL](http://www.mysql.com/)
* [PHP](http://php.net/)

## License

This program is free software: you can redistribute it and/or modify it under
the terms of the GNU General Public License as published by the Free Software
Foundation, either version 3 of the License, or (at your option) any later
version. [Read the license](LICENSE).

This program is distributed in the hope that it will be useful, but WITHOUT ANY
WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A
PARTICULAR PURPOSE.  See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with
this program.  If not, see http://www.gnu.org/licenses/.
