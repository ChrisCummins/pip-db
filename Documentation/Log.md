# Project Log


## October 2013


### Monday 7th

I've organised a meeting with Ian Nabney for Thursday 10th 10:30am in
MB216A. Topics for discussion:

* Dataset - getting a better understanding of the data which I will be working
with, the scientific background behind it, how often it will be updated, whether
it is confidential/open source etc.
* Users - what is the expected userbase, what are their expectations, concerns,
and technical experience? Etc.
* Time schedule - should I be using any development processes? How often should
I be meeting/reporting back? Etc.
* Difference(s) between the protein diffusion coefficient projects.
* Additional project requirements - what is the expected site traffic? High or
low? Do I need to organise dedicated servers?
* Any existing related databases to use as reference.
* Any confidentiality/plagiarism issues with hosting WIP code on github
(effectively open sourcing the whole project)?


### Wednesday 9th

Some personal goals for the project:

* To produce something of value to the molecular biology community.
* To exclusively use open source (free as in freedom and free as in free beer)
software and tools, without exception.
* To learn more about web development.
* To get a high mark (> 70%) mark for all assessments.
* To contribute back to at least one of the tools that I use in some
quantifiable way.


### Thursday 10th

Met with Ian. Notes:

* Choice of process is important - look up RUP (IBM Rational Unified Process)
(http://en.wikipedia.org/wiki/IBM_Rational_Unified_Process) and consider using
it. If not, suggest an improved alternative and justify.
* Choice of language is personal preference. Ian has experience with PHP making
it a solid choice - but a justified alternative is acceptable.
* I should meet with Dr. Darren Flower (Aston lecturer, effectively product
owner for this project) ASAP to discuss the project, and he can give me the data
and help answer questions about it.
* I can use github.com to host code, but shouldn't upload the bio data itself.
* First term work is for a lot more than just planning stage. I should be using
it to begin work properly and use it to brush up on MySQL/PHP skills as
required.
* Weekly meetings are essential, 10:30 on Thursdays.

TODO:

1. Read up on RUP.
2. Contact Darren to arrange meeting.
3. Read about git and MySQL
(http://ben.kulbertis.org/2011/10/synchronizing-a-mysql-database-with-git-and-git-hooks/).

Unified Process - The Unified Software Development Process is an iterative and
incremental software development process framework. The basis of the process is
that it is a use case driven iterative and incremental process, in which
Elaboration, Construction and Transition are divided into a series of time boxed
phases and iterated upo

IBM Rational Unified Process - An iterative software development process
framework created by IBM (Rational Software Corporation division), which is an
implementation of the Unified Process. It details six best practises for modern
software engineering:

1. Develop iteratively, with risk as the primary iteration driver
2. Manage requirements
3. Employ a component-based architecture
4. Model software visually
5. Continuously verify quality
6. Control changes

Each iteration contains nine disciplines. Six engineering disciplines:

1. Business modelling
2. Requirements
3. Analysis and Design
4. Implementation
5. Test
6. Deployment

And three supporting disciplines:

1. Configuration and change management
2. Project management
3. Environment

The RUP project life cycle consists of four phases:

1. Inception phase
2. Elaboration phase
3. Construction phase
4. Transition phase

Problems with RUP - It is isn't open source. A user license for the Rational
Method Composer must be bought from IBM for $1,080.00 dollars, and so I must
look for alternatively so as not to violate my personal goal of using purely
open source tools/frameworks.

OpenUP - The Open Unified Process, alternatively known as the Eclipse
Process Framework (EPF). This offers an open source minimalist implementation of
RUP.

> OpenUP is a lean Unified Process that applies iterative and incremental
> approaches within a structured life-cycle. OpenUP embraces a pragmatic, agile
> philosophy that focuses on the collaborative nature of software
> development. It is a tools-agnostic, low-ceremony process that can be extended
> to address a broad variety of project types.

Arranged meeting with Darren for 2pm in MB449 tomorrow.


### Friday 11th

Purposes of the project planning module:

1. Develop a project plan.
2. Produce a schedule of activities using GANTT charts or similar.
3. Consider which activities are critical to the success of your project.
4. Undertake a risk analysis.
5. Ensure that tangible milestones are identified to measure progress and
success.
6. Determine the resources needed to carry out and complete your project work.

From the guidance notes, a good project topic:

1. Provides a sufficient intellectual challenge. - will be determined by 3).
2. Contains a relatively straightforward core part which should be achievable
even in the worst-case scenario. - Yes. The creation of an online database.
3. Has an element of innovation, not necessarily in the research sense, but at
least in the sense of 'not covered or detailed in final-year lectures or in
standard textbooks'. - Not sure about this.
4. Yields results that can be verified in some way. - Yes.

Questions for next meeting with Ian:

- Availability of dedicated servers, and public IPs.
- Registration of a domain.
- Clarify the purpose of the project logbook: is it for my benefit or the
assessor's?
- Confirm access to the database module notes.

Questions for meeting with Darren:

-   The Dataset:
    -   What is it used for?
    -   Who is it used by?
        -   Confidential or open source?
        -   Publicly available or user account based access?
    -   How often is it used? (Site traffic)
    -   How often is it updated?
        -   User submissions or single admin account?
    -   Feedback from users?
        -   Reddit style popularity mechanism?
        -   Comments system?
-   The website:
    -   Possible volunteers for user testing?
    -   Associated with Aston?

Notes from meeting with Darren:

-   There are hundreds (/thousands) of existing databases which offer
    specific categories of information which tend to interlink with each
    other.
-   Many different use cases for looking up data. Some people will want
    a dump of the whole dataset so as to perform calculations, some
    other cases may be interested in performing more targeted searches,
    such as by type/range of pHs etc.
-   A BLAST search implementation would be useful.
-   Possible visualisations of results such as graphing distribution of
    pI ranges would be useful.
-   Fuzzy matching of search criteria would be useful.
-   All data contains citations to original source.
-   User submitted data could be potentially useful, but would need a
    way to distinguish it from the 'authoritative' source. I think this
    could be a really interesting topic of research - how can you
    authenticate user submitted data? Ranking by the weight of the
    citation? User rating? Peer reviewing?
-   Version control of the dataset would be useful. For example, viewing
    history (like Wikipedia's page history), and the ability to undo
    revisions.
-   Site traffic is impossible to predict. It could be used by lots of
    people, or it may not. To accommodate this, I should assume
    worst-case scenarios for the number of users - in cases of peer
    reviewing, assume low userbase; in cases of site traffic, assume
    heavy load.
-   Darren has volunteered for offering user testing of work-in-progress
    sites.
-   Darren would like to hear from me with progress reports, or any
    questions etc. Should keep him in the loop. Perhaps periodic
    progress reports sent by email?
-   Fraser is a final year Biology student who has worked with the
    dataset extensively, and should be able to help go through the data
    with me and explain what it means.
-   Darren will send me the dataset in the near future.

Site security concerns:

Confidentiality - Any user system will require user-names and passwords to
  remain confidential.

Integrity - Integrity of dataset is CRUCIAL. Any unwarranted modifications or
loss of data would invalidate the whole project.

Accessibility - Data must be accessible at all times. System downtime is
unacceptable, and this means that updates to dataset must occur transparently to
the user. This also includes making offline copies of the data for users.

Project Goal - A reasonable personal goal would be to get the basic search-able
database online by the end of the year. This provides ample time for adding the
"fun" stuff, such as visualisation, advanced searching techniques, extensive
user testing, site design etc. etc.

Have received dataset from Darren. Note that it is currently CONFIDENTIAL "in so
far as you shouldn't pass it on to anyone else, or otherwise make it available
to others".

Development Plan:

1.  Setup MySQL database and suitable tables.
2.  Create a web page to add entries to db and test.
3.  Create a script to import exiting data in spreadsheets.
4.  Create forms to search database.
5.  Create forms to download database/search queries.

UI Ideas:

1. Use an accordion model to hide further details of search requests and show them
on demand, instead of showing a list of links to separate details pages.
2. Use a minimalist front page if we don't have anything to show. Think
google.com.


### Sunday 13th

Found that the PHP sources for a couple of pages from Facebook were leaked in
2007, now available on github (https://gist.github.com/nikcub/3833406). Should
keep tabs on this so as to see how a large database backed website organises its
code.

Notes on Facebook source code:

-   Function `get_site_variable()` provides API into state vars.
-   `IS_DEV_SITE` and other globals provide some debugging.
-   `xxx_stats` variable arrays are used for grouping relevant data.
-   Directory structure:
    - `/html/`
    - `/libs/`
    - `/js/`
    - `/css/`
-   Templates are used and rendered last:
    `render_template($_SERVER['PHP_ROOT'].'/html/index.phpt');`.


### Monday 14th

Read up on PHP templating
(http://coding.smashingmagazine.com/2011/10/17/getting-started-with-php-templating/),
should investigate use of Twig framework to implement MVC architecture in my
project.

Amended meeting time with Fraser to 4pm on Wednesday.

Started generating project plan (Gantt chart). OpenUP reference:
http://epf.eclipse.org/wikis/openup/.


### Tuesday 15th

Created first iteration of site mockups to get a feel for the design.
Requirements are still changing rapidly so this helps formulate a TODO
list.

Back-end idea - Don't just store external URLs for references, instead break it
down logically into a website name, author, unique ID, etc.

Plan for site map:

- Home
    -   Help
    -   About Us
    -   Terms & Conditions
    -   Privacy Policy
    -   Contact Us
- Search Results
    -   Download Results
    -   Individual Record
    -   Edit Record
    -   Add New Record
- Members Page
    -   Register
    -   Login

Had meeting first with Fraser. Notes:

- Dataset consist of multiple sheets which can be combined, with an extra field
to note the sheet it originated from (1975, PubMed, etc.). Note that data in
sixth form sheet may be incorrect or less valid.
- Notes, by field:
    -   **E.C.** Enzyme Commission Number. Numerical classification
        scheme, consisting of four positive integer values. Many to one
        relationship of proteins and records to E.C. value. Can be
        unknown.
    -   **Protein / Alternative Name(s)** string names of equal value.
    -   **Source** Latin binomial. Case sensitivity is important:
        capitalise the Genus, everything else is lower case. Does have
        common names (property).
    -   **Organ and/or Subcellular location** a property of the source.
    -   **M.W.** Molecular Weight, in units of Daltons (Da).
    -   **Subunit No. / M.W (range)** properties of molecular weight.
    -   **No. of Iso-enzymes** Values can be vague (e.g. Many/several),
        not that important.
    -   **pI maximum value / pI range / pI value of major component /
        pI** various ways to specify pI value with varying precision.
    -   **Temperature** Important for replicating experimental results.
    -   **Method** Experimental method
    -   **Valid sequence(s) available** Values in the fields are
        shorthand, use the key at the bottom of each sheet to decode.
        Entries with '1' are more robust and should be ranked greater
        than entries with '0' values.
    -   **Protein sequence / Species Taxonomy / Original Texts /
        PubMed** External links.
    -   **Notes** text with additional details. Could be combed through
        to see if info could be added to other fields.

Prototyping idea - Write a "Plausible Nonsense Generator" which can create fake
but believable ideal datasets for testing with.


### Wednesday 16th

Notes from weekly meeting with Ian:

-   No news yet on availability of server/IP.
-   Registration of domains can be done whenever (he recommended quiet
    late, I'll recommend quite early), small cost will be reimbursed by
    department.
-   Refer to Kate for answers on: logbook style (is it for my benefit or
    assessors'?), the distribution of marks between testing and
    evaluation, term dates.
-   ‘Balsamiq' is a UI prototyping tool which is useful for generating
    wireframes and mockups. I should look into this.
-   First iteration of Gantt chart/project plan is lacking in User
    requirements and risk assessment time. More time should be dedicated
    to this, don't rush into implementation. Also need to propose a
    business case (although this will be pretty minimal: more science =
    better humanity).
-   A proper audit of the previous student's project should be done and
    recorded (this can be added to project plan). This can be chalked up
    as "Contextual Investigation"
-   Be more honest in risk assessment. Include things like "need to
    learn PHP", "I'm a MySQL n00b", etc.

TODO:

-   Second iteration of Gantt chart/project plan.
-   Write an assessment of project risks.
-   Talk with Darren about further user investigation.
-   Audit the previous FYP.
-   Download and test Balsamiq.


### Thursday 17th

An examination of pidb back-end:

```
    mysql> SHOW TABLES;
    +------------------+
    | Tables_in_pidb   |
    +------------------+
    | Locations        |
    | MethodCollection |
    | Methods          |
    | ProteinAltNames  |
    | Proteins         |
    | Records          |
    | Settings         |
    | Sources          |
    | TextCollection   |
    | Texts            |
    | Users            |
    +------------------+
    11 rows in set (0.00 sec)

    mysql> DESCRIBE Locations;
    +------------+--------------+------+-----+---------+----------------+
    | Field      | Type         | Null | Key | Default | Extra          |
    +------------+--------------+------+-----+---------+----------------+
    | LocationID | int(15)      | NO   | PRI | NULL    | auto_increment |
    | Location   | varchar(255) | NO   |     | NULL    |                |
    +------------+--------------+------+-----+---------+----------------+
    2 rows in set (0.00 sec)

    mysql> DESCRIBE MethodCollection;
    +----------+---------+------+-----+---------+-------+
    | Field    | Type    | Null | Key | Default | Extra |
    +----------+---------+------+-----+---------+-------+
    | RecordID | int(15) | NO   | PRI | NULL    |       |
    | MethodID | int(15) | NO   | PRI | NULL    |       |
    +----------+---------+------+-----+---------+-------+
    2 rows in set (0.02 sec)

    mysql> DESCRIBE Methods;
    +----------+--------------+------+-----+---------+----------------+
    | Field    | Type         | Null | Key | Default | Extra          |
    +----------+--------------+------+-----+---------+----------------+
    | MethodID | int(15)      | NO   | PRI | NULL    | auto_increment |
    | Method   | varchar(255) | NO   |     | NULL    |                |
    +----------+--------------+------+-----+---------+----------------+
    2 rows in set (0.00 sec)

    mysql> DESCRIBE ProteinAltNames;
    +-----------+--------------+------+-----+---------+-------+
    | Field     | Type         | Null | Key | Default | Extra |
    +-----------+--------------+------+-----+---------+-------+
    | ProteinID | int(15)      | NO   | PRI | NULL    |       |
    | AltName   | varchar(255) | NO   | PRI | NULL    |       |
    +-----------+--------------+------+-----+---------+-------+
    2 rows in set (0.02 sec)

    mysql> DESCRIBE Proteins;
    +--------------+--------------+------+-----+---------+----------------+
    | Field        | Type         | Null | Key | Default | Extra          |
    +--------------+--------------+------+-----+---------+----------------+
    | ProteinID    | int(15)      | NO   | PRI | NULL    | auto_increment |
    | Name         | varchar(255) | NO   |     | NULL    |                |
    | SequenceLink | text         | NO   |     | NULL    |                |
    +--------------+--------------+------+-----+---------+----------------+
    3 rows in set (0.00 sec)

    mysql> DESCRIBE Records;
    +----------------+--------------+------+-----+---------+----------------+
    | Field          | Type         | Null | Key | Default | Extra          |
    +----------------+--------------+------+-----+---------+----------------+
    | RecordID       | int(15)      | NO   | PRI | NULL    | auto_increment |
    | EC             | varchar(255) | NO   |     | NULL    |                |
    | ProteinID      | int(15)      | NO   |     | NULL    |                |
    | SourceID       | int(15)      | NO   |     | NULL    |                |
    | LocationID     | int(15)      | NO   |     | NULL    |                |
    | MWMin          | int(50)      | NO   |     | NULL    |                |
    | MWMax          | int(50)      | NO   |     | NULL    |                |
    | SubUnitNo      | int(5)       | NO   |     | NULL    |                |
    | SubUnitMW      | varchar(255) | NO   |     | NULL    |                |
    | IsoEnzymesMin  | varchar(255) | NO   |     | NULL    |                |
    | IsoEnzymesMax  | varchar(255) | NO   |     | NULL    |                |
    | PIMaxValue     | float        | NO   |     | NULL    |                |
    | PIMin          | float        | NO   |     | NULL    |                |
    | PIMax          | float        | NO   |     | NULL    |                |
    | PIMajorComp    | float        | NO   |     | NULL    |                |
    | PIValue        | float        | NO   |     | NULL    |                |
    | TemperatureMin | int(255)     | NO   |     | NULL    |                |
    | TemperatureMax | int(255)     | NO   |     | NULL    |                |
    | PubMed         | text         | NO   |     | NULL    |                |
    | Notes          | varchar(255) | NO   |     | NULL    |                |
    +----------------+--------------+------+-----+---------+----------------+
    20 rows in set (0.01 sec)

    mysql> DESCRIBE Settings;
    +-------+--------------+------+-----+---------+----------------+
    | Field | Type         | Null | Key | Default | Extra          |
    +-------+--------------+------+-----+---------+----------------+
    | ID    | int(15)      | NO   | PRI | NULL    | auto_increment |
    | Name  | varchar(255) | NO   |     | NULL    |                |
    | Value | varchar(255) | NO   |     | NULL    |                |
    +-------+--------------+------+-----+---------+----------------+
    3 rows in set (0.00 sec)

    mysql> DESCRIBE Sources;
    +--------------+--------------+------+-----+---------+----------------+
    | Field        | Type         | Null | Key | Default | Extra          |
    +--------------+--------------+------+-----+---------+----------------+
    | SourceID     | int(15)      | NO   | PRI | NULL    | auto_increment |
    | Name         | varchar(255) | NO   |     | NULL    |                |
    | TaxonomyLink | text         | NO   |     | NULL    |                |
    +--------------+--------------+------+-----+---------+----------------+
    3 rows in set (0.01 sec)

    mysql> DESCRIBE TextCollection;
    +----------+---------+------+-----+---------+-------+
    | Field    | Type    | Null | Key | Default | Extra |
    +----------+---------+------+-----+---------+-------+
    | RecordID | int(15) | NO   | PRI | NULL    |       |
    | TextID   | int(15) | NO   | PRI | NULL    |       |
    +----------+---------+------+-----+---------+-------+
    2 rows in set (0.00 sec)

    mysql> DESCRIBE Texts;
    +----------+--------------+------+-----+---------+----------------+
    | Field    | Type         | Null | Key | Default | Extra          |
    +----------+--------------+------+-----+---------+----------------+
    | TextID   | int(15)      | NO   | PRI | NULL    | auto_increment |
    | Link     | text         | NO   |     | NULL    |                |
    | TextType | varchar(255) | NO   |     | NULL    |                |
    +----------+--------------+------+-----+---------+----------------+
    3 rows in set (0.00 sec)

    mysql> DESCRIBE Users;
    +----------+--------------+------+-----+---------+----------------+
    | Field    | Type         | Null | Key | Default | Extra          |
    +----------+--------------+------+-----+---------+----------------+
    | UserID   | int(15)      | NO   | PRI | NULL    | auto_increment |
    | Name     | varchar(255) | NO   |     | NULL    |                |
    | Email    | varchar(255) | NO   |     | NULL    |                |
    | Password | varchar(32)  | NO   |     | NULL    |                |
    | Type     | varchar(50)  | NO   |     | NULL    |                |
    +----------+--------------+------+-----+---------+----------------+
    5 rows in set (0.00 sec)
```

### Friday 18th

Downloaded and tested Balsamiq (trial addition). It's absolutely perfect for my
needs, should but User license when trial expires. Should add a ‘plan' directory
to github repo for storing mockups.

TODO:

- Create mockups of previous FYP site designs.
- Create mockups of current site designs.

Database design - have created some first ideas for database designs using UML
database notation. Should add PDF exports of these to repo.

Data repetition within dataset - have been auditing the dataset that Darren sent
me. There’s an awful lot of repetition of data in almost every field, so the
database design should be heavily normalised to optimise for this.


### Saturday 19th

TODO:

-   Perform full audit of dataset
-   Develop Plausible Nonsense Generator

Plausible Nonsense Generator - Started developing PNG as a HTML/JS tool for
creating nonsense payloads by using a randomised fake dataset. Should postpone
further development on this pending completion of the real dataset audit so I
know how best to mimic it.


### Sunday 20th

Things to discuss at next FYP meeting with Ian:

-   Analysis of last FYP - Highlight technical differences (database
    design, MVC architecture) and user experience flaws. What are Ian’s
    opinions on the implementation. NOTE: I really don’t want anything
    to do with the past project beyond using it as a reference.
-   "Balsamiq" - super awesome. Show early prototypes.
-   Risk assessment - Examples of risk assessments (obviously Google
    returns nothing useful).
-   Database design - UML diagram.
-   Project planning - second iteration Gantt chart.


### Monday 21st

I'm starting to get aggravated with the entire software development process side
to the project. The more that I read about RUP and OpenUp, the less I feel that
either process would contribute anything positive to the project. Additionally,
I have been researching recommendations on software development processes for
solo projects and have found not one source that recommends their use for
individual work, with both the official documentation of development processes
anecdotal evidence suggesting that their main value is in organising teams. The
advice that I have seen for solo projects covers things that I already am doing
or intend to do:

-   Pick a good version control system and use it fastidiously.
-   Write down a list of goals and achievements.
-   Keep a log of your progress and decisions made.
-   Document your code.
-   Use a bug tracker.

I should check with Kate that not using a development process will not affect my
marks/assessment before "making a stand", but I am confident that being
shouldered into having to adopt a process for this project would at best be a
distraction and at worse would jeopardise my efforts by adding artificial
constraints that get in the way of progress. One exception to this is test
driven development, which I have first-hand experience of using from ‘emu’
(https://github.com/ChrisCummins/emu) and ‘t4’
(https://github.com/ChrisCummins/t4), and is a process which I intend to use
when implementing some of the data back-end.

Planning TODO:

-   Begin writing project plan.
-   Write a set of use cases for common tasks, and create mockups
    showing how to perform those tasks with pidb/protein-db.

Implementation TODO:

-   Add probability control to PNG and re-implement as a native
    application (or as a scriptable web app).
-   Read up on Selenium (http://docs.seleniumhq.org/).
-   Implement `make DEBUG=1 all` feature.
-   Move website sources into `www/` subdirectory.

Preparing CSV files from Dataset:

1. Save the desired sheet as text (tab delimiter).

Working with CSV dataset:

```
    # To remove header line from output
    $ cat dataset.csv | tail -n+2

    # To print first column
    $ cat dataset.csv | awk -F $'\t' '{print $2}'

    # To count non-empty fields
    $ cat dataset.csv | tail -n+2 | \
      awk -F $'\t' '{print $2}' | sed '/^$/d' | wc -l
```

### Tuesday 22nd

Began creating Objectives, Milestones, Success, Use Cases and Risks tables for
use in project planning meeting tomorrow.\

Created side-by-side mockups of common tasks in pidb and my first iteration
design using Balsamiq.


### Wedensday 23rd

Laptop TODO:

-   Install balsamiq trial
-   Install lessc
-   Install tablify
-   Test dsa, png and build system

Demos for Ian:

-   Balsamiq mockups
-   Build system
-   dsa
-   png

Notes from PP meeting with Kate:

-   Final poster assessment is A1 size - glossy paper looks best.
-   Gantt chart week numbers should include some dates for reference.
-   Gantt chart needs project plan marked on it.
-   Be clear in project plan of which activities depend on each other
    and which milestones are "blocking" achievements.
-   There are 26 work weeks in the term.
-   Deadline for preliminary project plan is November 1st.
-   Next PP meeting is a peer review of project plans on November 11th.


### Thursday 24th

Notes from weekly meeting with Ian:

-   Make a third iteration Gantt chart, and send to Ian.
-   Discuss "purpose of project" with Darren at next meeting, not just
    user interface.
-   Make a list of mitigation strategies for each of the risks in the
    Risk list.

Started using the GitHub issue tracker and imported the milestones and current
task list. Added GitHub pages for the repo, should populate that later. Ideas
for public page: a link to latest PDF build of this log, automatic burndown
charts, a dynamic list of top bugs and most recent commits, etc.


### Friday 25th

Prepared Balsamiq mockups for meeting with Darren at 2pm today, and installed
previous FYP onto laptop for demonstration.

Notes from meeting with Darren:

-   D1 initial design is OK.
-   The search engine should provide hard limits on the number of
    results it returns (perhaps as a percentage of the dataset size?);
    this is to prevent users from just downloading the whole dataset
    themselves and eradicating the need for the database.
-   In order to achieve this, search criteria should be split into two
    types: primary and secondary. Primary search criteria are those
    which can only return a finite subset of the dataset, whereas
    secondary criteria may return the entire set, and so should be
    combined with primary criteria. For example, a protein name is a
    primary criterion, whereas a pI range of 0-14 (all possible values)
    is a secondary criterion.
-   Incorporating Blast searching will involve generating a list of
    sequences for every record and then using that database with the
    blast search program. This is a high priority "bonus", as it will be
    a step above the previous attempts.
-   The design is a very subjective thing - as long as it is functional,
    I have a lot of creative control over how the user should interact
    with the site.
-   I have relative freedom over the project name, but Darren will be
    able to contribute suggestions if necessary.
-   I should see if Fraser has anything he would like to say about the
    design.


### Saturday 26th

Ported PNG to node.js yesterday. Node.js seems fantastic for what I want
(server-side JS), and the combination of that and MongoDB seems to be very
interesting - it’s worth reading more into it.

Reading List:

-   Node.js testing framework - mocha (https://github.com/visionmedia/mocha) and
    should.js (https://github.com/visionmedia/should.js).
-   Blog rolling with mongoDB (http://howtonode.org/express-mongodb).
-   Real Time Web with Node.js
    (http://courseware.codeschool.com/node_slides.pdf).
-   Node.js vs PHP (http://webapplog.com/php-vs-node-js/).


### Sunday 27th

Began replacing the static Makefile with an autotooled build system.  This will
provide greater portability and is a more suitable process for building the
project as it adds the extra configuration stage which can be used to specify
options such as whether to use content hashing, whether to build the TeX
sources, etc.

Build system TODO:

-   configure: Add --enable-(html|js|css)-minification=(yes|no) feature
-   configure: Check for node else fail
-   configure: Check for dsa runtime dependencies else fail
-   configure: Check for Apache/MySQL else warn
-   automake: Add make install command
-   configure: Add --prefix for www\_root
-   configure: Add --enable-content-hashing=(yes|no) feature
-   configure: Add summary
-   automake: Add log make open|edit rules


### Monday 28th

Completed port of build system over to autotools. There are a number of
additional features that would be nice to add, and these have been filed as
feature requests in the issue tracker.\

Received an email from Darren which clarified a number of concerns and suggested
some new project titles. In my desire to keep the project name as simple and
pronounceable as possible, I’ve suggested "pipsearch".


### Tuesday 29th

Have been writing up Project Plan draft for Friday deadline.

TODO:

-   Add PDF plan deadline to git repo after submission.


### Wednesday 30th

Notes for next meeting with Ian:

-   Purpose of project - recap what I learnt from meeting with Darren
-   Project name - pip-db. Availability of domain, and getting a server
    by next week.
-   Review - mitigation strategies
-   Review - initial design milestone, and changes that can/should be
    made
-   Review - build system (what it is and what it does)


### Thursday 31st

Notes from meeting with Ian:

-   TODO: Send Ian project plan.
-   TODO: Send Darren a project overview to check that my expectations
    match his.
-   Registering of a domain should go through a member of staff.
-   I should talk with Kat Samperi about acquiring a dedicated server.
-   TODO: Begin prototyping using Node.js AND PHP.
-   TODO: look into Selenium web testing for automation.
-   TODO: Look up some formal HCI practises and apply those to design
    decisions.
-   I should look into EBI and some of the well funded bioinformatics
    databases to see if there are any frameworks or existing
    technologies to aid in development (such as standard XML spec for
    proteins and such).


## November 2013


### Saturday 2nd

Read about ionicons, which are a useful set of MIT licensed icons that will be
useful when designing the UI (https://github.com/driftyco/ionicons).


### Sunday 3rd

Began work on implementing static page designs based on D1 mockups, using
Bootstrap.

TODO:

-   Homepage - DONE
-   Advanced Search - DONE
-   Search results
-   Details page
-   Login page
-   Add data


### Monday 4th

Incorporated Bootstrap Less CSS sources directly into project repository so that
I can hack deeply on the frontend framework rather than just monkey-patch it.


### Tuesday 5th

Completed basic sketches for all page mockups. Next task is to tidy up the
stylesheets and incorporate it better into the Boostrap sources.

Le grande plan for great success:

1.  Configure Bootstrap theme
2.  Port D1 mockups to Boostrap components
3.  Add MySQL user accounts backend
    -   Deliverable: setup.php which creates MySQL tables
4.  Add PHP user accounts controller
    -   Deliverable: API with unit tests
5.  Link frontend with controller for user accounts
    -   Deliverable: Functional login page and ubar
6.  Add MySQL payload backend
    -   Deliverable: setup.php which creates MySQL tables
7.  Add PHP payload controller
    -   Deliverable: API with unit tests
8.  Link frontend with controller for payload
    -   Deliverable: Functional details pages
9.  Develop search controller
    -   Deliverable: Functional advanced search page
10. Further search controller development
    -   Deliverable: inline search component


### Wednesday 6th

Added arguments to configure in the form `--enable-feature` which can be used for
enabling minifying, content hashing, local exporting, etc. Used Cogl’s
configure.ac as a template for the refactors.

TODO:

-   Research formal HCI methods for web design. Screen recording
    software for user testing?

Notes for next meeting with Ian:

-   Build system - le grande demonstration. Will write blog post
    detailing how it works/how to use it.
-   Frontend - Bootstrap, using Less CSS sources.
-   D1 mockups - static prototypes.
-   Next progress - the "grande plan for great success".


### Thursday 7th

Incorporated the site style into Bootstrap, and defined the first two PHP
functions:

```
    function get_header( $inline_search = false, $value = null,
                         $login_only = false );

    function get_footer();
```


### Friday 8th

Notes from meeting with Ian:

The theme of this week’s meeting is: PROCESS, PROCESS, PROCESS.

-   I need a more definite set of goals for this construction phase.
-   Bring a copy of the Gantt chart to each meeting.
-   Make more constructive notes on the research and planning work done.
-   HCI - The next round of design work is at the start of next term.
    The remainder of effort this term will be focused on implementation,
    although I will still be checking design with Darren in the
    meantime.
-   Software architecture planning - Discuss analysis of WordPress,
    previous FYP project, dataset analysis, and database designs.
-   Development plan - Le grande 8 step plan.
-   Development process - show the GitHub issue tracker and milestones.
    Issues are created on a "as needed" basis, hence the second term
    milestones have nothing. Show `./tools/worflow` and development flow
    diagram.

TODO:

-   Complete issues for ‘Draft Project Plan submission’.
-   Make a write-up of analysis of WordPress source code, previous FYP
    sources, and any other similar projects.
-   Further research of EBI.
-   Research PHP templating.

Notes on WordPress - found a detailed description of the WordPress database
design http://codex.wordpress.org/Database_Description. There is also detailed
documentation for all of the APIs (http://codex.wordpress.org/WordPress_APIs).

-   There are filesystem and database APIs to completely abstract the
    underlying system. The user should never have to write system or
    MySQL calls.
-   There is a global $wpdb variable which is used to talk to the
    WordPress database. API:

```
            $wpdb->delete( $table, $where, $where_format = null );
            $wpdb->get_col( 'query', column_offset );
            $wpdb->get_results( 'query', output_type );
            $wpdb->get_row('query', output_type, row_offset);
            $wpdb->get_var( 'query', column_offset, row_offset );
            $wpdb->insert( $table, $data, $format );
            $wpdb->query('query');
            $wpdb->replace( $table, $data, $format );
            $wpdb->update( $table, $data, $where, $format = null, $where_format = null );
            $wpdb->show_errors();
            $wpdb->hide_errors();
            $wpdb->get_col_info('type', offset);
            $wpdb->flush();
```

-   ID fields in each table are prefixed with the table name and are of
    type BIGINT(20).
-   A `wp_options` table stores the settings for the site.
-   A file `wp-admin/includes/schema.php` contains the database
    information and description.

PHP best practises - read up on how PHP is not object orientated
(http://michaelkimsal.com/blog/php-is-not-object-oriented/), and PHP templating
practises
(http://coding.smashingmagazine.com/2011/10/17/getting-started-with-php-templating/).


### Saturday 9th

Notes on last FYP - 2533 lines of PHP. File structure:

```
    -rw-r--r-- 1 chris chris  465 Dec 21  2011 ajax.js
    -rw-r--r-- 1 chris chris 1.9K Mar 22  2012 ajax.php
    -rw-r--r-- 1 chris chris  311 Mar 23  2012 back-up.php
    -rw-r--r-- 1 chris chris 3.6K Apr  3  2012 backup.php
    -rw-r--r-- 1 chris chris    0 Mar 23  2012 backup.sql
    -rw-r--r-- 1 chris chris  238 Apr 20  2012 bottom.php
    -rw-r--r-- 1 chris chris  110 Sep 10  2012 db-details.php
    -rw-r--r-- 1 chris chris  15K Apr 25  2012 display.php
    -rw-r--r-- 1 chris chris 2.8K Apr 20  2012 download.php
    -rw-r--r-- 1 chris chris 1.1K Feb  6  2012 funcs.php
    drwxr-xr-x 2 chris chris 4.0K Apr  3  2012 images
    -rw-r--r-- 1 chris chris 5.3K Mar 22  2012 index.php
    drwxr-xr-x 2 chris chris 4.0K Apr 25  2012 js
    -rw-r--r-- 1 chris chris 7.7K Apr 16  2012 login.php
    -rw-r--r-- 1 chris chris 1.8K Feb 15  2012 protein.php
    -rw-r--r-- 1 chris chris 3.1K Mar 14  2012 record.php
    -rw-r--r-- 1 chris chris 7.7K Apr 16  2012 restore.php
    -rw-r--r-- 1 chris chris  24K Apr 30  2012 search.php
    -rw-r--r-- 1 chris chris 4.4K Mar 22  2012 style.css
    -rw-r--r-- 1 chris chris 3.3K Apr 20  2012 top.php
    -rw-r--r-- 1 chris chris  22K Apr 20  2012 upload.php
    drwxr-xr-x 2 chris chris 4.0K Sep 10  2012 uploads

    ./images:
    total 620K
    -rw-r--r-- 1 chris chris 1.4K Apr  3  2012 homeico.png
    -rw-r--r-- 1 chris chris 7.4K Apr  3  2012 home.png
    -rw-r--r-- 1 chris chris  88K Feb 10  2012 logo.png
    -rw-r--r-- 1 chris chris 486K Feb  8  2012 logo.psd
    -rw-r--r-- 1 chris chris  31K Jan 16  2012 randprot.png

    ./js:
    total 108K
    -rw-r--r-- 1 chris chris  92K Jan 17  2012 jquery-1.7.1.min.js
    -rw-r--r-- 1 chris chris 5.3K Apr 25  2012 jquery.js
    -rw-r--r-- 1 chris chris 5.3K Apr 25  2012 jquery.php

    ./uploads:
    total 2.5M
    -rw-r--r-- 1 chris chris 553K Mar 13  2012 database_egle_bunkute2.csv
    -rw-r--r-- 1 chris chris 1.1M Mar 13  2012 pidb_1981.csv
    -rw-r--r-- 1 chris chris  26K Mar 13  2012 pidb_1995.csv
    -rw-r--r-- 1 chris chris 903K Mar 13  2012 pidb_brenda.csv
```

top.php

1.  Sets the session
2.  Connects to mysql
3.  Sets a `$root_dir` variable
4.  Prints HTML header

funcs.php

Defines `get_record($record_if)`.

bottom.php

Prints out a page footer and closes MySQL connection.

index.php

1.  Include `top.php`
2.  Output page contents with inline PHP logic
3.  Connects to mysql

search.php

515 lines of intermingled MySQL commands, PHP logic and HTML.

General notes:

-   Multiple copies of constants scattered throughout sources
-   Spaghetti code source files - a mixture of inline PHP and HTML.
-   MySQL commands embedded into page sources - no MVC separation.


### Sunday 10th

Website design - found a good checklist of annoying web design decisions to
avoid
(http://harknesslabs.com/post/66570723467/stop-doing-this-crap-on-your-startups-website).

Unit testing - read about Zombie.js, a Node.js framework for insanely fast
browser testing (looks awesome!)
(http://www.slideshare.net/iloire/building-web-apps-with-nodejs-socketio-knockoutjs-and-zombiejs-codemotion-2012).


### Monday 11th

Found out about SheetJS - a pure-JavaScript excel parser
(http://oss.sheetjs.com/). This could be useful for client-side parsing of
uploaded data.


### Tuesday 12th

Setup Twig for server-side rendering of HTML templates.


### Wednesday 13th

Imported Bootswatch Flatly theme.

TODO:

-   Fixup color scheme for flatly
-   Reduce vertical padding for items

### Thursday 14th

Notes from meeting with Ian:

-   TODO: Turn the "le grande 8 steps" into a set of prototype
    requirements (functional and non-functional), with each requirement
    being testable and QUANTIFIABLE with success criteria.
-   TODO: Create an elaboration and construction plan with requirements
    (functional and non-functional), with each requirement being
    testable and QUANTIFIABLE with success criteria.
-   When making decisions (design or implementation), use the smallest
    experiment which can isolate the decision.
-   TODO: Organise next meeting with Darren to discuss EBI technologies,
    usability design and database design.
-   TODO: So side-by-side implementations of object orientated vs.
    non-object orientated library function.
-   TODO: Create a set of functional and non functional requirements for
    the project based on objectives.
-   Design decisions must be quantifiable and testable, not vague like
    "should be user friendly".
-   TODO: For next meeting, demo google analytics on Myrmidon books
    website.
-   TODO: Add a requirements analysis to project plan.


### Friday 15th

TODO:

-   Read up on phpDocumentor.


### Saturday 16th

Read up on MVC architecture in PHP (http://r.je/mvc-in-php.html) and some decent
arguments again static variables and methods
(http://r.je/static-methods-bad-practice.html). Google has a nice guide to
writing testable code and code smells and symptoms of bad design to look for
(http://misko.hevery.com/code-reviewers-guide/).


### Sunday 17th

Began implementing MySQL backend in library file `db.php`.


### Tuesday 19th

Implement a `./tools/mkrelease` script which can be used to bump version number
and create a release branch/tag. Bumped project version to 0.0.2.


### Wednesday 20th

Have been reading about Dependency Injection in PHP, have found a good intro
article (http://net.tutsplus.com/tutorials/php/dependency-injection-in-php/)
which recommends a tool Symphony
(http://symfony.com/doc/2.0/components/dependency_injection/introduction.html).


### Thursday 21st

Notes from meeting with Ian:

-   Requirements should be uniquely identified, for example, the
    milestone requirements could be identified with M1.1, D3.2, etc.
-   Requirements should be incredibly DETAILED. They are contractual, so
    the ability to irrefutably determine whether a requirement has been
    met or not is essential.
-   Requirements for each iteration should be created at the start,
    before beginning work. The start of next term will begin with
    generating a set of requirements for the second iteration.
-   TODO: Write a set of testable requirements for this first
    elaboration/construction iteration.
-   TODO: Continue/complete development of prototype.


### Friday 22nd

Notes from meeting with Darren and Fraser:

-   Prototype looks very good, and is mostly what would be expected of
    the final design (save for the unnecessarily spartan homepage).
-   Advanced Search is a high priority TODO, as this will enable user
    testing.
-   Fraser suggested posting to the Life and Health Sciences department
    a request for user testers, or a survey which suggests whether
    people find it useful, or how they would use such a tool.
-   The Enzyme Commission number is a property of enzymes, and not all
    proteins listed in the dataset are enzymes, so not all will have EC
    numbers.
-   The ONLY two prerequisites for data being entered into the
    repository are that it should contain a protein and an isoelectric
    point.
-   Fraser noticed that some records which had Greek letters within
    their names (like beta) were displayed incorrectly.
-   I should look through the number of unique values within the dataset
    to determine whether the advanced search page should have a dropdown
    selector or a free-text field.
-   Enzyme Commission number is a hierarchical search property, so
    people may want to start searching with the top (leftmost) value,
    then specify additional tiers. E.g. 3..., 3.1.., 3.1.2., 3.1.2.4.
    It’s unlikely you’d ever want to search for ..2.0.
-   It’s hard to anticipate how users would want to query the database,
    so it would be nice to keep a record of all the queries that people
    make, and to use this to influence the design. Additionally, we
    could record IP address of the user so as to help collate the number
    of different users.
-   The purpose of the database is to act as a passive repository of
    information that has been gathered. It is a carbon copy of a set of
    research data, so shouldn’t make any assumptions or assertions about
    the data, i.e. don’t process the data. To this end, my
    super-overly-optimised database design is crap, and we’d be much
    better off with "documents", not "relational data".
-   One possibility for administering the dataset could be to allow
    anyone to create an account and upload data, but for this data to be
    help in a "holding bay", pending approval from an admin. The admin
    would then see that someone has submitted new data and could
    review/accept it.
-   The cost of domain registration should come from my project budget,
    but could be done in the Uni’s name. Should sort this out in next
    meeting with Ian.
-   Example use cases:

    1.  A user is writing a paper on a certain protein, and would like
        to quickly look up its isoelectric point. In this case, they
        would search for the protein by name and location (species).

    2.  A user would like to see all records for a given EC value (for
        example, 3.1).


### Saturday 23rd

Have been given an account and credentials on one of the PSO uni server by Kate
Samperi. URL: http://pso.aston.ac.uk/cummince.


### Monday 25th

Deployed the prototype website to the Aston server (note to self: I should look
into one of the task-runners like Grunt for generating deployment build, the
number of configure arguments is getting pretty unwieldy!). Notes on initial
feedback from Darren:

-   Generally looking positive. The search features that aren’t yet
    implemented make it quite hard to test (advanced search, empty
    fields etc.).
-   Homepage is too sparse (address this in D2).
-   The dropdown for experimental methods show lots of duplicates, i.e.
    the data isn't standardised. Perhaps it would be possible to keep
    two separate lists of methods - one which contains the methods that
    were entered "as is" (with all the duplicates), and a separate,
    standardised list which can be used for searching (but would be
    hidden from the user). I'll take a look into this - it could provide
    the right balance between keeping an honest record of the data "as
    recorded", and constructing a standardised and relational model for
    categorising the data.


### Wednesday 27th

External links - we could do some pretty advanced verification of external
URLs. First off, we could verify that external URLs are not dead (don’t return
404s), and we could also grab the page titles and use those as captions.  We
could even cache the entire page if we think there’s a chance of important links
going dead.


### Thursday 28th

Example possible interactive pipbot session:

```
    $ pipbot
    Hello there. My name is pipbot. How can I help?

    -> help
    These are some of the things I can do:
        pipbot build <build>
            Build a specific configuration.
        pipbot issue <verb>
            Open and close project issues.
        pipbot list <something>
            List an entity (builds, issues, etc.).
        pipbot show <something>
            Show something
        pipbot issue
            Open and close project issues.
        pipbot repeat
            Repeat the last instruction.

    -> build local debug
    Generating... [ok]
    Configuring... [ok]
    Cleaning up... [ok]
    Building... [ok]

    -> deploy
    Deploying local build to /var/www... [ok]

    -> deploy public production
    Generating... [ok]
    Configuring... [ok]
    Cleaning up... [ok]
    Building... [ok]
    Deploying remote build to pso.aston.ac.uk/~cummince... [ok]

    -> show 111
    Issue #111: Regression in build system

    -> new 111
    Creating wip/111 branch... [ok]

    -> fixed 111
    Merging wip/111 into master... [ok]

    -> push upstream
    Pushing master to origin... [ok]

    -> version
    Last release branch: 0.0.7
    Next release branch: 0.0.8

    -> release 0.0.9
    Releasing version 0.0.8... [ok]
    Bumping version to 0.0.9... [ok]

    -> wtf?
    The current version is 0.0.9
    You're on branch master
    You're up to date with origin
    You last deployed a7342cf3 to local
    You last deployed 3849bef3 to public

    -> show 3849bef3
    commit 3849bef3325a43ded55cc3359761e070636dad1fe
    Author: Chris Cummins <chrisc.101@gmail.com>
    Date:   Mon Nov 25 15:56:41 2013 +0000

        log: Fix regression in blah...
```


### Friday 29th

Found a good article on test coverage in SQLite
(http://www.sqlite.org/testing.html). Some interesting edge-cases are tested
such as out-of-memory errors, I/O error testing, and fuzz testing. Should refer
to this when designing the pip-db testing strategy.

Project name - refactored source code to new project name `pip-db`, and rename
git repository. New url: https://github.com/ChrisCummins/pip-db.


## December 2013

### Thursday 5th

Notes from meeting with Ian:

- I should tell Kate about the change in project scope - the majority of time
  has been spent working on tooling/process/frameworks, and not the actual
  project spec (just the website).
- The meeting with moderator and technician would be the best time to talk about
  buying the pip-db.org domain.
- Possible branching model in the database backend to accomodate for user
  submissions, errors in the dataset, etc.
- Lots of room for project expansion is opened up by the "honest record of data"
  requirement from Darren. In addition to storing the recorded fields, the
  backend could still build a relational model between each of the fields.
- TODO: Email Ian with URLs for pulic prototpye (with credentials), this log
  file, repository, issue tracker, etc.

### Friday 6th

Started adapting parts of the pip-db prototype for http://chriscummins.cc. Notes
on the things that I've nicked:

- Build system - a relatively painless process to copy accross the important
  parts (`autogen.sh`, `configure.ac`, and `Makefile.am`s). Patched up the image
  file extension support and ported back to pip-db.
- PHP library - I copied accross all the `www/lib/*.php` files, and then cleaned
  up `init.php` to remove the useless stuff.

Overall it actually went better than I was expecting, the pip-db infrastructure
is fairly robust and flexible. Some things though (like `pipbot`) are too
project-specific to be copied accross, even though they are useful.

### Sunday 8th

Implemented further advanced search fields (see 0.1.8 release). Since the
prototype dataset table consists of entirely text fields, we can't yet do
numerical comparisons - so a few of the fields (temperature, pI, etc) are
non-functional.

### Monday 9th

Potential class layout for constructing queries:

```
MySQLStatement : interface
  + get_mysql_query() : string

Select implements MySQLStatement
  - $table : string
  - $columns : string[]
  - $where : Condition
  - $prefix : string
  - $suffix : string
  + Select( $table : string,
            $columns : string[],
	    $where : Conidition,
	    $prefix = "" : string,
	    $suffix = "" : string )

Condition : abstract class implements MySQLStatement

StringMatchCondition extends Condition
  - $field : string
  - $value : string
  - $exact : boolean
  + StringMatchCondition( $field : string,
                          $value : string,
                          $exact = False : boolean )

ConditionLogic : abstract class
  + _AND = "AND" : string
  + _OR = "OR" : string
  + val() : string[]

CompositeCondition extends Condition
  - $conditions : Condition[]
  - $logic : ConditionLogic
  + CompositeCondition( $logic : ConditionLogic,
    			$conditions = array() : Condition[] )
  + add_condition( $condition : Condition )
```

And example usage:

```
$query = new PipQueryBuilder( new PipSearchQueryValues() );

$select = new Select( "records",
		      array( "record_id",
			     "name",
			     "source",
			     "organ",
			     "pi" ),
		      $query->get_query(),
		      "SQL_CALC_FOUND_ROWS",
		      ("LIMIT $starting_at," .
		       Pip_Search::ResultsPerPage) );
```
