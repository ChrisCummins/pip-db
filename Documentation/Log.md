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

* Choice of process is important - look up
[RUP](http://en.wikipedia.org/wiki/IBM_Rational_Unified_Process) (IBM
Rational Unified Process) and consider using it. If not, suggest an
improved alternative and justify.
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
3. Read about [git and MySQL](http://ben.kulbertis.org/2011/10/synchronizing-a-mysql-database-with-git-and-git-hooks/).

Unified Process - The Unified Software Development Process is an
iterative and incremental software development process framework. The
basis of the process is that it is a use case driven iterative and
incremental process, in which Elaboration, Construction and Transition
are divided into a series of time boxed phases and iterated upon.

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

Found that the PHP sources for a couple of pages from Facebook were
leaked in 2007,
[now available](https://gist.github.com/nikcub/3833406) on
GitHub. Should keep tabs on this so as to see how a large database
backed website organises its code.

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

Read up on
[PHP templating](http://coding.smashingmagazine.com/2011/10/17/getting-started-with-php-templating/),
should investigate use of Twig framework to implement MVC architecture
in my project.

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

I should check with Kate that not using a development process will not
affect my marks/assessment before "making a stand", but I am confident
that being shouldered into having to adopt a process for this project
would at best be a distraction and at worse would jeopardise my
efforts by adding artificial constraints that get in the way of
progress. One exception to this is test driven development, which I
have first-hand experience of using from
[emu](https://github.com/ChrisCummins/emu) and
[t4](https://github.com/ChrisCummins/t4), and is a process which I
intend to use when implementing some of the data back-end.

Planning TODO:

-   Begin writing project plan.
-   Write a set of use cases for common tasks, and create mockups
    showing how to perform those tasks with pidb/protein-db.

Implementation TODO:

-   Add probability control to PNG and re-implement as a native
    application (or as a scriptable web app).
-   Read up on [Selenium](http://docs.seleniumhq.org/).
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

- Node.js testing framework -
  [mocha](https://github.com/visionmedia/mocha) and
  [should.js](https://github.com/visionmedia/should.js).
- [Blog rolling with mongoDB](http://howtonode.org/express-mongodb).
- [Real Time Web with Node.js](http://courseware.codeschool.com/node_slides.pdf).
- [Node.js vs PHP](http://webapplog.com/php-vs-node-js/).


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


### Friday 1st

Technical planning stage is coming to an end, will aim to start
implementation work properly on Sunday - starting with static HTML
pages and gradually introducing a greater level of interactivity to
them as the back end is implemented.

Generated different types of project plan based off of the main one,
each offering a dedicated subset of the main document. So for Darren,
I've generated a project plan which glosses over the technical details
but focuses on the bioinformatics aspect of the project, whereas for
Ian I have generated a project plan which focuses mostly on the
deadlines and work breakdown structure, since he won't be as
interested in the project background.


### Saturday 2nd

Read about ionicons, which are a useful set of
[MIT licensed icons](https://github.com/driftyco/ionicons) that will
be useful when designing the UI.


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

Notes on WordPress - found a
[detailed description of the WordPress database design](http://codex.wordpress.org/Database_Description).
There is also
[detailed documentation for all of the APIs](http://codex.wordpress.org/WordPress_APIs).

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

PHP best practises - read up on
[how PHP is not object orientated](http://michaelkimsal.com/blog/php-is-not-object-oriented/),
and
[PHP templating practises](http://coding.smashingmagazine.com/2011/10/17/getting-started-with-php-templating/).


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

Website design - found a good checklist of
[annoying web design decisions to avoid](http://harknesslabs.com/post/66570723467/stop-doing-this-crap-on-your-startups-website).

Unit testing - read about
[Zombie.js](http://www.slideshare.net/iloire/building-web-apps-with-nodejs-socketio-knockoutjs-and-zombiejs-codemotion-2012),
a Node.js framework for insanely fast browser testing (looks awesome!).


### Monday 11th

Found out about [SheetJS](http://oss.sheetjs.com/) - a pure-JavaScript
excel parser. This could be useful for client-side parsing of uploaded
data.


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

Read up on [MVC architecture in PHP](http://r.je/mvc-in-php.html) and
some
[decent arguments again static variables and methods](http://r.je/static-methods-bad-practice.html).
Google has a
[nice guide](http://misko.hevery.com/code-reviewers-guide/) to writing
testable code and code smells and symptoms of bad design to look for.


### Sunday 17th

Began implementing MySQL backend in library file `db.php`. This will
involve a set of wrapper functions around the `mysql_` PHP API.


### Monday 18th

Began implementing a user accounts back end. Unlike the previous FYP,
this will store only salted hashes of a user's password, rather than
plain-text original passwords. I will use PHP's blowfish algorithm for
the cryptography functionality, being stronger than standard
non-cryptographic hashing algorithms, like MD5 etc.


### Tuesday 19th

Implement a `./tools/mkrelease` script which can be used to bump version number
and create a release branch/tag. Bumped project version to 0.0.2.


### Wednesday 20th

Have been reading about Dependency Injection in PHP, have found a good
[intro article](http://net.tutsplus.com/tutorials/php/dependency-injection-in-php/)
which recommends a tool
[Symphony](http://symfony.com/doc/2.0/components/dependency_injection/introduction.html).


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


### Sunday 24th

Encountered some serious flaws while attempting to transfer a compiled
site over the PSO servers. Chiefly, the URL prefixing pattern was
causing all of the hyperlinks to break across the entire site. Because
the website is hosted on a user account, the URL is prefixed with:
`~cummince`, and so any absolute paths to a URL like
`/path/to/styles.css` was breaking, as it should have been
`~/cummins/path/to/styles.css`. This seems like it is a problem that
should be fixed in the configuration script, so I will add a
`WWW_PREFIX` environment variable to the `configure.ac` script which
can optionally set a prefix to all of the site links. Note this is
different from `WWW_ROOT`, which refers to the file path.


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

Found a good article on
[test coverage in SQLite](http://www.sqlite.org/testing.html). Some
interesting edge-cases are tested such as out-of-memory errors, I/O
error testing, and fuzz testing. Should refer to this when designing
the pip-db testing strategy.

Project name - refactored source code to new project name `pip-db`, and rename
git repository. New url: https://github.com/ChrisCummins/pip-db.


## December 2013


### Sunday 1st

The focus for the next iteration of development is tooling and version
control. This starts with transitioning to the
[Vincent Driessen branching model](http://nvie.com/posts/a-successful-git-branching-model/),
which has been adopted for the `0.1.1` release.


### Monday 2nd

Have been sketching out an initial implementation of `pipbot` as a
Node.js application. First functionality is to perform release
branching, so this involves deprecating part of `tools/mkrelease.sh`
and porting this over to Node.

### Tuesday 3rd

Have changed pipbot implementation language to Python and continued
development of build and deploy features. Pipbot accepts a `<target,
build>` name pair and reads configure arguments from a couple of JSON
files in order to prepare the source tree.


### Wednesday 4rd

Have been re-working the project's documentation to make it clearer
and more accessible. The idea is that the `Documentation/` directory
should contain a set of Markdown files which can be viewed using
GitHub's online viewer tools. This logbook is getting ported from
LaTeX to Markdown, and several new documents will be created to cover
things like the directory structure, code styles, branching model,
etc.


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

### Saturday 7th

Added the ability for pipbot to generate brief burndowns over a date
range, for example:

```
$ pipbot burndown 7 days
Comparing 'master' against 'master'...

  There are 16 new commits on master
  The last commit on master was 6 days, 21 hours ago
```

And also to generate comparisons between the current master branch and
the last release on stable:

```
$ pipbot burndown release
Comparing 'master' against 'stable'...

  The last release was 0.1.11
  There are 98 new commits on master
  The last commit on stable was 20 days, 14 hours ago
```


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
$query = PipQueryBuilder::build( new PipSearchQueryValues() );

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

### Thursday 12th

Notes from meeting with Ian:

- Each activity in the project plan needs a unique identifier (e.g. number
  system like 1.2 1.3).
- Each risk and mitigation strategy needs to uniquely identified.
- Sections should be numbered.
- The Risk Assessment should be a toplevel section.
- The initial reasearch should be *justified*, it needs to explain why the
  project plan is like it is.
- Mention the level of tooling work in the project plan, and explain why it is
  being done. E.g., we can afford to have a very short deployment process
  because we've spent all this time developing tools to manage complex
  deployments.
- Mention more about GitHub and the cross-referencing issue tracker, milestones
  etc.
- Add deployment complexities to the risks list. I.e., Deploying a complex and
  configurable PHP/MySQL project.
- All of the non-functional requirements are related to technical
  aspects. There's nothing about user experience. There should be at least one
  non-functional user expereince requirement such as "Users should be able to
  perform 90% of their queries without requiring training".
- Next project meeting should be organised by me, presumably in the week after
  my January exam.

### Friday 13th

Found a nice article on
[avoiding the assignment statement](http://loup-vaillant.fr/tutorials/avoid-assignment),
which seems especially relevant for PHP programming, given that weak
typing can make it hard to see if a new variable is being declared or
if an existing variable is being modified. Example:

```
$c = 0;

...

$c = 5; // Is $c a new variable, or an existing one???
```

Also, the same author has written a nice write-up on
[the downsides of a pervasive mutable state](http://loup-vaillant.fr/articles/classes-suck),
as inferred by class based programming.

### Sunday 15th

Refactored pipbot so that it uses a single configuration file in
`~/.pipbot/config.json` which contains the full build/deploy/pipbot
config, rather than having lots of separate configuration files in
`~/.local/etc/pipbot`.


### Tuesday 17th

I have created a
[Markdown version](https://github.com/ChrisCummins/pip-db/blob/master/Documentation/ProjectPlan.md)
of the project plan, by porting the LaTeX sources to Markdown using
`pandoc`, and then hand-editing the generated sources and using
`doctoc` to generate a table of contents. The idea is that this adds a
version of the project plan suitable for quick online viewing, without
having to build a PDF file from sources.


### Wednesday 18th

Have been reading up on improving page rendering times. I found a
great article by Google on
[blocking of page rendering caused by JavaScript](https://developers.google.com/speed/docs/insights/BlockingJS)
which provides a nice inline snippet for deferring the download of
JavaScript sources that aren't directly needed to render the page.

Additionally, read a good
[blog post](http://www.appneta.com/blog/bootstrap-pagespeed/) on using
Google's Page Insights tool as a performance metric for optimising
Bootstrap. Definitely useful future reference material here, and I
should consider using PageSpeed scores and also DOMContentLoaded times
as a metrics for the optimisation sections of development.

Further reading on established practises for web performance:

 - https://developers.google.com/speed/docs/best-practices/rules_intro
 - http://stevesouders.com/hpws/rules.php
 - http://developer.yahoo.com/performance/rules.html


### Monday 23rd

The aim of holiday implementation work is to reduce the bug-count on
the M1 prototype, so I've begun fixing some of the implementation
issues filed in the tracker, starting with the broken pagination
problem for advanced queries.


### Saturday 28th

I have been revamping my personal http://chriscummins.cc site, and
started off by importing a lot of the pip-db PHP logic and templating
engine, intending to use this as a starting point and to build a new
front end. (see December 6th entry). Having re-evaluated the purpose
of the site, I decided that a static HTML generator would be much more
easy to manage, and covers all of the use cases I can think off.

Having decided on using a Jekyll/GitHub pages combination, I could
apply a few of the interesting features from this static site
generation techniques to pip-db. Chiefly, the use of Markdown and
other easy-to-read file formats for content makes a lot of sense, and
help abstract the site data from the application logic.


### Sunday 29th

It's the end of TP1 and this logbook has already reached 58KB in
size. I need to consider a more manageable way of working with large
Markdown documents. Perhaps I should use a similar structure to Jekyll
blog posts used in GitHub pages - i.e. create a new Markdown file for
each day?

This would result in a directory with around 100 separate Markdown
files in it, with filenames like `2013-12-29.md` and
`2013-12-30.md`. The advantage of that is that it produces many
smaller and separate files, although this would make it harder to do
whole-document re-factors.


### Monday 30th

Registered the `pip-db.org` domain with GoDaddy for 1 year (no
renewal), and set up a 301 permanent forward to
`pso.aston.ac.uk/~cummince/`. When next term starts, should ask Ian
how to get Uni to reimburse the £9.10 fee.


### Tuesday 31st

Restructured the directory tree so as to put the design files inside
of the Documentation tree.


## January 2014


### Thursday 2nd

Began a LISP

 * PostgreSQL
 * Travis CI
 * lein
 * View logic
 * resources

 * MVC


### Monday 20th

Reflected on one of the written goals for Christmas holiday work:

> The aim of holiday implementation work is to reduce the bug-count on
> the M1 prototype...

Over the holiday, 5 issues were closed from the bug tracker, but it is
clear that the emphasis of work shifted away from just bug hunting and
further towards re-imagining the entire software stack (heroku, LISP,
lein, etc.).


### Tuesday 21st

Have been reading up on
[Marginalia](https://github.com/gdeer81/marginalia) with the intent of
using it as an API documentation system, being a pseudo
[literate programming](http://en.wikipedia.org/wiki/Literate_programming)
tool.


### Wednesday 22nd

Notes for meeting with first weekly meeting of the year with Ian:

 1. Review of TP1 work
    * Milestones OK, getting used to GitHub issue tracker/workflow.
    * Comparison screenshots
    * Project Plan
 2. LISP
    * Total project rewrite
       * Purely functional programming paradigm
       * Built on top of JVM
       * *Very young language* (scope for contributions)
       * Asynchronous, single threaded, but almost entirely immutable
         data structures
       * Model View Controller architecture
    * Functionally identical to PHP/MySQL prototype
    * Test coverage!
       * Continuous integration (Travis CI)
       * lein cloverage
 3. Upcoming work:
    * D2 milestone - generating designs in Balsamiq (#212).

From project report feedback:

> It is important to ensure there is enough scope for originality to
> access the highest possible marks at the end of the project.

Areas for originality:

 * Distributed Systems development using functional programming.
 * Tooling, continuous integration and automated deployment.
 * No-one is developing these systems using Clojure.

> One area which perhaps needed more intention was in understanding
> the users and their typical usage pattern which would affect both
> the user interface design and the database structure.

Recurring theme of assessments so far. How do I express that the we
don't necessarily *have* a user-base, and that user's behaviour will
be *analysed* and acted upon, not pre-determined beforehand?


### Thursday 23rd

Notes from weekly meeting with Ian:

 * Maintainability - what are the implications of using an esoteric
   programming language? People know PHP, most people aren't familiar
   with LISP. How are future students going to add new features?
 * Stability - if Clojure is such a young and dynamic project, how
   quickly are things going to break? How are you going to ensure that
   this project
 * Deployment - how does this affect the client? Can we still host the
   site on the PSO servers? Does it still use Apache? Does it take up
   a lot of disk space/CPU? Create a table of deployment options.
 * Areas for originality - there's room for originality in dealing
   with uploading new data. Analysis of the existing dataset and using
   that to form models and expectations for new data.

Notes for next meeting:

 * Maintainability:
    * What are the implications of using an esoteric programming
      language?
       - Future developers must learn the language (Clojure).
       - I don't *think* this is an unreasonable requirement (for
         example, the PHP APIs break all the time: `mysql` vs
         `mysqli` APIs).
       - In essence, I am creating a *Domain Specific Language*.
     * How are future students going to add new features?
       - Use a literate programming style to document Clojure sources
         (Marginalia).
       - Linters, and a self contained build system can provide early
         warning.
       - Make Clojure accessible (clojure-koans!). It honestly is a
         great language.
       - New features can be written in Java and called from Clojure.
 * Stability:
    * List *all* dependencies: Lein (bash script).
    * Each dependency is strictly versioned, and versions are
      immutable.
    * Show how project.clj file defines all of the dependencies
      (including the language itself!), and how this pulls in remote
      dependencies.
    * Show how the test suite and Travis CI can be used to test
      forward and backwards compatibility (different versions of Java,
      clojure, etc).
 * Deployment:
     * Heroku - automated deployments using a single
       command. Transparently scalable.
 * pip-db.org domain

From the [Clojure rationale](http://clojure.org/rationale):

> Customers and stakeholders have substantial investments in, and are
> comfortable with the performance, security and stability of,
> industry-standard platforms like the JVM. While Java developers may
> envy the succinctness, flexibility and productivity of dynamic
> languages, they have concerns about running on customer-approved
> infrastructure, access to their existing code base and libraries,
> and performance. In addition, they face ongoing problems dealing
> with concurrency using native threads and locking. Clojure is an
> effort in pragmatic dynamic language design in this context. **It
> endeavours to be a general-purpose language suitable in those areas
> where Java is suitable**. It reflects the reality that, for the
> concurrent programming future, pervasive, unmoderated mutation
> simply has to go.

### Friday 24th

From
[Marginalia core](http://gdeer81.github.io/marginalia/#marginalia.core):

> If literate programming stands as a comprehensive programming
> methodology at one of end of the spectrum and no documentation
> stands as its antithesis, then Marginalia falls somewhere
> between. That is, you should always aim for comprehensive
> documentation, but the shortest path to a useful subset is the
> commented source code itself.

### The one true way

1. Start by running Marginalia against your code
2. Cringe at the sad state of your code commentary
3. Add docstrings and code comments as appropriate
4. Generate the documentation again
5. Read the resulting documentation
6. Make changes to code and documentation so that the "dialog" flows sensibly
7. Repeat from step #4 until complete

### Saturday 25th

```
$  git push heroku master
Fetching repository, done.
Counting objects: 436, done.
Delta compression using up to 4 threads.
Compressing objects: 100% (208/208), done.
Writing objects: 100% (394/394), 725.84 KiB | 409 KiB/s, done.
Total 394 (delta 219), reused 272 (delta 134)

-----> Clojure app detected
-----> Installing OpenJDK 1.6...done
Warning: no :min-lein-version found in project.clj; using 1.7.1.
-----> Using cached Leiningen 1.7.1
       To use Leiningen 2.x, add this to project.clj: :min-lein-version "2.0.0"
       Downloading: rlwrap-0.3.7
       Writing: lein script
-----> Building with Leiningen
       Found bin/build; running it instead of default lein invocation.
       Running: bin/build
       ------> Running autogen.sh
               Checking whether you have the necessary tools...
               Checking for autoconf (need at least version 2.65)... ok
               Checking for automake (need at least version 1.11)... ok
               Your system has the required tools, running autoreconf...
               configure.ac:83: installing `build/install-sh'
               configure.ac:83: installing `build/missing'

               You can now run `./configure'.
       ------> Running configure
               checking for a BSD-compatible install... /usr/bin/install -c
               checking whether build environment is sane... yes
               checking for a thread-safe mkdir -p... /bin/mkdir -p
               checking for gawk... no
               checking for mawk... mawk
               checking whether make sets $(MAKE)... yes
               checking for gawk... (cached) mawk
               checking for a sed that does not truncate output... /bin/sed
               checking for java... java
               checking for /tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/public... no
               mkdir: created directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/public'
               checking for build/node/bin/node... no
               checking for node_modules/less/bin/lessc... no
               npm http GET https://registry.npmjs.org/less
               npm http 200 https://registry.npmjs.org/less
               npm http GET https://registry.npmjs.org/less/-/less-1.6.1.tgz
               npm http 200 https://registry.npmjs.org/less/-/less-1.6.1.tgz
               npm http GET https://registry.npmjs.org/mkdirp
               npm http GET https://registry.npmjs.org/clean-css
               npm http GET https://registry.npmjs.org/source-map
               npm http GET https://registry.npmjs.org/mime
               npm http GET https://registry.npmjs.org/request
               npm http 200 https://registry.npmjs.org/mkdirp
               npm http 200 https://registry.npmjs.org/mime
               npm http GET https://registry.npmjs.org/mkdirp/-/mkdirp-0.3.5.tgz
               npm http GET https://registry.npmjs.org/mime/-/mime-1.2.11.tgz
               npm http 200 https://registry.npmjs.org/source-map
               npm http 200 https://registry.npmjs.org/mkdirp/-/mkdirp-0.3.5.tgz
               npm http 200 https://registry.npmjs.org/mime/-/mime-1.2.11.tgz
               npm http GET https://registry.npmjs.org/source-map/-/source-map-0.1.31.tgz
               npm http 200 https://registry.npmjs.org/clean-css
               npm http 200 https://registry.npmjs.org/source-map/-/source-map-0.1.31.tgz
               npm http GET https://registry.npmjs.org/clean-css/-/clean-css-2.0.7.tgz
               npm http 200 https://registry.npmjs.org/request
               npm http 200 https://registry.npmjs.org/clean-css/-/clean-css-2.0.7.tgz
               npm http GET https://registry.npmjs.org/request/-/request-2.33.0.tgz
               npm http 200 https://registry.npmjs.org/request/-/request-2.33.0.tgz
               npm http GET https://registry.npmjs.org/qs
               npm http GET https://registry.npmjs.org/json-stringify-safe
               npm http GET https://registry.npmjs.org/forever-agent
               npm http GET https://registry.npmjs.org/node-uuid
               npm http GET https://registry.npmjs.org/tough-cookie
               npm http GET https://registry.npmjs.org/tunnel-agent
               npm http GET https://registry.npmjs.org/http-signature
               npm http GET https://registry.npmjs.org/form-data
               npm http GET https://registry.npmjs.org/oauth-sign
               npm http GET https://registry.npmjs.org/hawk
               npm http GET https://registry.npmjs.org/aws-sign2
               npm http 200 https://registry.npmjs.org/json-stringify-safe
               npm http 200 https://registry.npmjs.org/forever-agent
               npm http 200 https://registry.npmjs.org/tunnel-agent
               npm http 200 https://registry.npmjs.org/node-uuid
               npm http 200 https://registry.npmjs.org/oauth-sign
               npm http 200 https://registry.npmjs.org/qs
               npm http 200 https://registry.npmjs.org/aws-sign2
               npm http GET https://registry.npmjs.org/json-stringify-safe/-/json-stringify-safe-5.0.0.tgz
               npm http GET https://registry.npmjs.org/forever-agent/-/forever-agent-0.5.0.tgz
               npm http 200 https://registry.npmjs.org/form-data
               npm http GET https://registry.npmjs.org/tunnel-agent/-/tunnel-agent-0.3.0.tgz
               npm http GET https://registry.npmjs.org/node-uuid/-/node-uuid-1.4.1.tgz
               npm http 200 https://registry.npmjs.org/http-signature
               npm http GET https://registry.npmjs.org/oauth-sign/-/oauth-sign-0.3.0.tgz
               npm http GET https://registry.npmjs.org/qs/-/qs-0.6.6.tgz
               npm http 200 https://registry.npmjs.org/json-stringify-safe/-/json-stringify-safe-5.0.0.tgz
               npm http 200 https://registry.npmjs.org/tunnel-agent/-/tunnel-agent-0.3.0.tgz
               npm http 200 https://registry.npmjs.org/forever-agent/-/forever-agent-0.5.0.tgz
               npm http GET https://registry.npmjs.org/aws-sign2/-/aws-sign2-0.5.0.tgz
               npm http 200 https://registry.npmjs.org/node-uuid/-/node-uuid-1.4.1.tgz
               npm http 200 https://registry.npmjs.org/qs/-/qs-0.6.6.tgz
               npm http GET https://registry.npmjs.org/form-data/-/form-data-0.1.2.tgz
               npm http 200 https://registry.npmjs.org/oauth-sign/-/oauth-sign-0.3.0.tgz
               npm http GET https://registry.npmjs.org/http-signature/-/http-signature-0.10.0.tgz
               npm http 200 https://registry.npmjs.org/aws-sign2/-/aws-sign2-0.5.0.tgz
               npm http 200 https://registry.npmjs.org/form-data/-/form-data-0.1.2.tgz
               npm http 200 https://registry.npmjs.org/http-signature/-/http-signature-0.10.0.tgz
               npm http 200 https://registry.npmjs.org/tough-cookie
               npm http GET https://registry.npmjs.org/tough-cookie/-/tough-cookie-0.12.1.tgz
               npm http GET https://registry.npmjs.org/commander
               npm http 200 https://registry.npmjs.org/tough-cookie/-/tough-cookie-0.12.1.tgz
               npm http 200 https://registry.npmjs.org/commander
               npm http 200 https://registry.npmjs.org/hawk
               npm http GET https://registry.npmjs.org/commander/-/commander-2.0.0.tgz
               npm http GET https://registry.npmjs.org/hawk/-/hawk-1.0.0.tgz
               npm http 200 https://registry.npmjs.org/commander/-/commander-2.0.0.tgz
               npm http 200 https://registry.npmjs.org/hawk/-/hawk-1.0.0.tgz
               npm http GET https://registry.npmjs.org/amdefine
               npm http 200 https://registry.npmjs.org/amdefine
               npm http GET https://registry.npmjs.org/amdefine/-/amdefine-0.1.0.tgz
               npm http 200 https://registry.npmjs.org/amdefine/-/amdefine-0.1.0.tgz
               npm http GET https://registry.npmjs.org/combined-stream
               npm http GET https://registry.npmjs.org/async
               npm http GET https://registry.npmjs.org/assert-plus/0.1.2
               npm http GET https://registry.npmjs.org/asn1/0.1.11
               npm http GET https://registry.npmjs.org/ctype/0.5.2
               npm http 200 https://registry.npmjs.org/combined-stream
               npm http 200 https://registry.npmjs.org/async
               npm http 200 https://registry.npmjs.org/asn1/0.1.11
               npm http 200 https://registry.npmjs.org/ctype/0.5.2
               npm http GET https://registry.npmjs.org/combined-stream/-/combined-stream-0.0.4.tgz
               npm http GET https://registry.npmjs.org/async/-/async-0.2.10.tgz
               npm http GET https://registry.npmjs.org/ctype/-/ctype-0.5.2.tgz
               npm http GET https://registry.npmjs.org/asn1/-/asn1-0.1.11.tgz
               npm http 200 https://registry.npmjs.org/assert-plus/0.1.2
               npm http 200 https://registry.npmjs.org/ctype/-/ctype-0.5.2.tgz
               npm http 200 https://registry.npmjs.org/combined-stream/-/combined-stream-0.0.4.tgz
               npm http 200 https://registry.npmjs.org/asn1/-/asn1-0.1.11.tgz
               npm http 200 https://registry.npmjs.org/async/-/async-0.2.10.tgz
               npm http GET https://registry.npmjs.org/assert-plus/-/assert-plus-0.1.2.tgz
               npm http 200 https://registry.npmjs.org/assert-plus/-/assert-plus-0.1.2.tgz
               npm http GET https://registry.npmjs.org/punycode
               npm http GET https://registry.npmjs.org/cryptiles
               npm http GET https://registry.npmjs.org/hoek
               npm http 200 https://registry.npmjs.org/punycode
               npm http GET https://registry.npmjs.org/boom
               npm http GET https://registry.npmjs.org/sntp
               npm http 200 https://registry.npmjs.org/cryptiles
               npm http 200 https://registry.npmjs.org/sntp
               npm http GET https://registry.npmjs.org/punycode/-/punycode-1.2.3.tgz
               npm http GET https://registry.npmjs.org/cryptiles/-/cryptiles-0.2.2.tgz
               npm http GET https://registry.npmjs.org/sntp/-/sntp-0.2.4.tgz
               npm http 200 https://registry.npmjs.org/boom
               npm http 200 https://registry.npmjs.org/punycode/-/punycode-1.2.3.tgz
               npm http 200 https://registry.npmjs.org/cryptiles/-/cryptiles-0.2.2.tgz
               npm http 200 https://registry.npmjs.org/sntp/-/sntp-0.2.4.tgz
               npm http GET https://registry.npmjs.org/boom/-/boom-0.4.2.tgz
               npm http 200 https://registry.npmjs.org/hoek
               npm http 200 https://registry.npmjs.org/boom/-/boom-0.4.2.tgz
               npm http GET https://registry.npmjs.org/hoek/-/hoek-0.9.1.tgz
               npm http 200 https://registry.npmjs.org/hoek/-/hoek-0.9.1.tgz
               npm http GET https://registry.npmjs.org/delayed-stream/0.0.5
               npm http 200 https://registry.npmjs.org/delayed-stream/0.0.5
               npm http GET https://registry.npmjs.org/delayed-stream/-/delayed-stream-0.0.5.tgz
               npm http 200 https://registry.npmjs.org/delayed-stream/-/delayed-stream-0.0.5.tgz
               less@1.6.1 node_modules/less
               ├── mime@1.2.11
               ├── mkdirp@0.3.5
               ├── clean-css@2.0.7 (commander@2.0.0)
               ├── source-map@0.1.31 (amdefine@0.1.0)
               └── request@2.33.0 (json-stringify-safe@5.0.0, forever-agent@0.5.0, aws-sign2@0.5.0, qs@0.6.6, tunnel-agent@0.3.0, oauth-sign@0.3.0, node-uuid@1.4.1, tough-cookie@0.12.1, form-data@0.1.2, hawk@1.0.0, http-signature@0.10.0)
               checking for xdg-open... no
               configure: WARNING: PDF opening feature disabled.
               checking for emacsclient... no
               configure: WARNING: Text editor launching features disabled.
               configure: creating ./config.status
               config.status: creating Makefile
               config.status: creating Documentation/Makefile
               config.status: creating Documentation/design/Makefile
               config.status: creating Documentation/design/d1/Makefile
               config.status: creating Documentation/design/d1/pages/Makefile
               config.status: creating Documentation/design/d1/pages/renders/Makefile
               config.status: creating Documentation/design/d1/previous/Makefile
               config.status: creating Documentation/design/d1/previous/renders/Makefile
               config.status: creating Documentation/design/d1/tasks/Makefile
               config.status: creating Documentation/design/d1/tasks/renders/Makefile
               config.status: creating Documentation/plan/Makefile
               config.status: creating scripts/Makefile
               config.status: creating scripts/git/Makefile
               config.status: creating scripts/git/hooks/Makefile
               config.status: creating scripts/git/hooks/pre-commit
               config.status: creating tools/Makefile
               config.status: creating tools/dsa.sh
               config.status: creating resources/Makefile
               config.status: creating resources/css/Makefile
               config.status: creating resources/fonts/Makefile
               config.status: creating resources/img/Makefile
               config.status: creating resources/js/Makefile
               creating config.summary

               pip-db - 0.2.4
               --------------

                • Resources:
                       Minify CSS:                     yes
                       Minify JS:                      yes

                • Extras:
                       Build tools:                    no
                       Build LaTeX documentation:      no
                       Build Markdown documentation:   no
                       Build Markdown contents pages:  no
                       Install custom git hooks:       no

               You can now run `make'.
       ------> Running make
               Making all in resources
               make[1]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources'
               Making all in css
               make[2]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/css'
                 LESS   login.css
                 CSS    login.css
                 LESS   styles.css
                 CSS    styles.css
               make[2]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/css'
               Making all in fonts
               make[2]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/fonts'
                 FONT   glyphicons-halflings-regular.eot
                 FONT   glyphicons-halflings-regular.ttf
                 FONT   glyphicons-halflings-regular.woff
                 FONT   glyphicons-halflings-regular.svg
               make[2]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/fonts'
               Making all in img
               make[2]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/img'
                 IMAGE  advanced.png
                 IMAGE  details.png
                 IMAGE  homepage.png
                 IMAGE  login.png
                 IMAGE  results.png
                 IMAGE  upload.png
               make[2]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/img'
               Making all in js
               make[2]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/js'
                 JS     modernizr-2.7.0.min.js
                 JS     search.inline.js
                 JS     advanced.inline.js
                 JS     record.inline.js
                 JS     bootstrap-3.0.1.min.js
                 JS     jquery-1.10.2.min.js
                 JS     google-analytics.inline.js
                 JS     index.inline.js
                 JS     moment.min.js
                 JS     main.js
               make[2]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources/js'
               make[2]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources'
               make[2]: Nothing to be done for `all-am'.
               make[2]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources'
               make[1]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/resources'
               Making all in scripts
               make[1]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts'
               Making all in git
               make[2]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts/git'
               Making all in hooks
               make[3]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts/git/hooks'
               make[3]: Nothing to be done for `all'.
               make[3]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts/git/hooks'
               make[3]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts/git'
               make[3]: Nothing to be done for `all-am'.
               make[3]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts/git'
               make[2]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts/git'
               make[2]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts'
               make[2]: Nothing to be done for `all-am'.
               make[2]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts'
               make[1]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406/scripts'
               make[1]: Entering directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406'
               make[1]: Nothing to be done for `all-am'.
               make[1]: Leaving directory `/tmp/build_48bc7414-a749-4fd8-8cf6-d9cf7d74d406'
-----> Discovering process types
       Procfile declares types -> web

-----> Compressing... done, 97.8MB
-----> Launching... done, v26
       http://pip-db.herokuapp.com deployed to Heroku

To git@heroku.com:pip-db.git
   9e4436b..9cac266  master -> master
```

### Monday 27th

Comparison of Clojure vs Java:

http://hammerprinciple.com/therighttool/items/clojure/java

Comparison of Clojure vs PHP:

http://hammerprinciple.com/therighttool/items/clojure/php

### Wednesday 29th

Notes for meeting with Ian:

 * Maintainability: implications of language choice
    * Future developers must learn language
       * HOW - Marginalia / Literate programming
       * WHY - Great language - clojure-koans

 * Stability
    * Two dependencies: lein & java
    * project.clj - immutable versions
    * Tests using Travis CI - build matrix

 * Deployment
    * Heroku

### Thursday 30th

Notes from weekly meeting with Ian:

 * Maintainability should be tested on a person. Get a volunteer
   student to try and implement a new feature, e.g. a new dataset file
   format extension.

 * It may be a good idea to package the dependency JARs locally so as
   to future-proof against projects becoming unavailable.

 * Contact Kate regarding reimbursement of domain expense.


## February 2014


### Sundary 2nd

Read up on Heroku's
[dyno sleeping policy](https://devcenter.heroku.com/articles/dynos#dyno-sleeping),
which is causing a 5-10 second delay on HTTP requests when pip-db has
been inactive for more than an hour. Apps with more than one dyno
don't have the behaviour, but obviously this would cost money. A
workaround is to ping the server at regular intervals (there are free
services for this), but this seems somewhat unethical and exploitative
of the free service offered by Heroku.


### Monday 3rd

Emailed Darren with a status update on the project progress. Suggested
a meeting in a fortnight to review D2 progress. Topics to discuss
would include the redesign of the index page, a functioning pI
backend, and formal user testing.

### Friday 7th

Confirmed meeting with Darren for 1pm on Friday. Re-booked meeting
with Ian for Wednesday 4:30pm.

### Monday 10th

Implement autocomplete functionality using jQuery UI and a hand-picked
subset of the most common results for each different field (protein
name, source, location). I suspect that the production version will
use AJAX and a server-side string matching algorithm implemented in
Clojure to provide fast suggestions, but for now we can get away with
client side processing by just passing ~800 suggestions to the client
through JavaScript and getting them to process it.

### Tuesday 11th

Notes for meeting with Ian:

 * Deployment fixed - user error! (of course). Causes by silent
   failing during build, and red herring warnings.

    * Idle status on heroku web apps causes few seconds load delay.

 * Design work - D2 overdue.

    * Balsamiq mockups - more adventurous designs.
    * Refining the searching experience: sliders and autocomplete.
    * Upload and Download previews (embedded Excel emulator in JS).

 * Design implementations - demo.

 * NEXT: Back-end work - meeting with Darren on Friday.

### Wednesday 12th

TODO:

1. Add 'check test coverage' to release checklist.
2. Set `project.clj` version in `mkrelease.sh`.
3. Update test coverage with new UI/util functions.
4. Implement method to display pI in results page.
5. Update log (notes to/from meeting with Darren).
6. Implement record ID hash on DB.
7. Check for 404 on invalid record URL.
8. Implement prototype about page.
9. Implement prototype upload preview.
10. Implement download results.

Notes from meeting with Ian:

 * Data integrity needs to be the focus of my time. Until the data
   storage problem is solved, this project's useless.

 * Prepare an agenda for Friday meeting with Darren and make sure I
   get through everything. Again, the focus should be data storage and
   integrity.

 * Before making data storage suggestions to Darren, I *need* solid
   recommendations and options. I can't expect Darren to come up with
   the solution for me, but rather to help pick from a range of
   potential solutions.

 * I should make an updated Gantt chart for the remaining time, taking
   into account the changes in project scope / direction.

 * I should give some thought as to how to accept the Greek letters in
   searches. Should the server accept substitute Latin characters
   (e.g. 'b' in place of β).

### Thursday 13th

Notes for meeting with Darren:

 * How should we display results with only pI major component? Example
   search: `Alcohol dehydrogenase`.

 * NCBI BLAST search: I know *how* to perform a query, now I need to
   learn *what* query to perform. It looks like there's two parts: a
   database and a query.

### Friday 14th

Have been reading up on NCBI BLAST
searching. [Quick start](http://www.ncbi.nlm.nih.gov/blast/Doc/node2.html)
to the API.

Example search:
`http://www.ncbi.nlm.nih.gov/blast/Blast.cgi?QUERY=555&DATABASE=nr&HITLIST_SIZE=10&FILTER=L&EXPECT=10&FORMAT_TYPE=XML&PROGRAM=blastn&CLIENT=web&SERVICE=plain&NCBI_GI=on&PAGE=Nucleotides&CMD=Put`

Fetch search results:
`http://www.ncbi.nlm.nih.gov/blast/Blast.cgi?CMD=Get&RID=???&FORMAT_TYPE=XML`

Notes from meeting with Darren:

 * The two issues to focus on: data searching and BLAST.

 * Darren offer to provide copy for the front page text.

 * The Advanced Search pI slider should operate in .1 steps, and
   should show the currently selected value.

 * On the record page, rename the "Abstract" button to "Publisher's
   Abstract".

 * On the record page, the pubmed link should be the first external
   button.

 * Don't try and filter/process the record names dataset.

 * For the experimental method, create a combined reduced list. Merge
   the obvious candidates, and then use this reduced list in the
   search page. Darren has offered to run it past him.

 * For the pI dataset, store numerical values for pI min and pI max,
   if possible. For "aproximate" values, like "~7.5", don't store
   anything.

 * To implement BLAST searches:

      1. Scrape through the UniProt sequences for each record. Each
         record has a protein sequence link. On each linked page,
         there is a link to download the raw sequence. Download that,
         and add it to the records table.

      2. Create a BLAST database from the scraped sequences in the
         records table.

      3. Point the BLAST program to this database and post-process the
         outputs so as to link back to the records with the records
         table.

### Sunday 16th

I've been putting some thought into the URL design of the site, as I
think this will heavily influence the design of the data backend. The
definitive site routes I've planned for are:

Page     | URL
-------  | -------
Index    | http://www.pip-db.org/
Search   | http://www.pip-db.org/s
Download | http://www.pip-db.org/d
Record   | http://www.pip-db.org/r/:id
Log in   | http://www.pip-db.org/login
Log out  | http://www.pip-db.org/logout
Upload   | http://www.pip-db.org/upload

The format of the resulting data for the search, record, and download
pages can be supplied in two forms: either by append the file format
to the URL (e.g. adding ".json" to the end of a URL to return JSON
data), or by setting the "format" variable in the request. The default
format will always be HTML. Examples:

Page     | Format | URL
-------  | ------ | -------
Search   | HTML   | http://www.pip-db.org/s?q=alkaline
Search   | XML    | http://www.pip-db.org/s.xml?q=alkaline
Search   | JSON   | http://www.pip-db.org/s?q=alkaline&format=json
Record   | HTML   | http://www.pip-db.org/r/100
Record   | XML    | http://www.pip-db.org/r/100.xml
Record   | JSON   | http://www.pip-db.org/r/100?format=json
Download | HTML   | http://www.pip-db.org/d?q=alkaline
Download | XML    | http://www.pip-db.org/d.xml?q=alkaline
Download | JSON   | http://www.pip-db.org/d?q=alkaline&format=json

### Monday 17th

I've started work on mining the FASTA sequences for blast searching,
and I've noticed that not all of the records within the dataset
contain a link to a UniProt sequence, but instead they link to a
UniProt search result page which lists multiple different
sequences. I've contacted Darren for advice on what to do in those
circumstances.

In the mean time I can begin implementing a web crawler which can mine
a set of URLs and build a set of FASTA sequences. This would be a good
candidate for later abstracting into a separate project.

Notes on implementing web crawling of FASTA sequences:

For UniProt records, simple append ".fasta" to the URL. Example:

URL: http://www.uniprot.org/uniprot/A0A9I9
FASTA: http://www.uniprot.org/uniprot/A0A9I9.fasta

The process for NCBI records is more involved. Example:

1. Go to the URL: http://www.ncbi.nlm.nih.gov/protein/AAA40744.1
2. Look for the "VERSION string: `VERSION     P02630.1  GI:131104`
3. Grab the number after the "GI:" attribute: `131104`
4. Append this to the base URL http://www.ncbi.nlm.nih.gov/protein/,
   and set the `report=fasta` GET variable:
   http://www.ncbi.nlm.nih.gov/protein/131104?report=fasta

In order to perform this pragmatically, we can fetch the HTML contents
from the URL and grep for the FASTA URL within it. See:

```
<a class="dblinks" href="/protein/131104?report=fasta" name="EntrezSystem2.PEntrez.Protein.Sequence_ResultsPanel.SequenceViewer.Sequence_ViewerTitle.ReportShortCut" sid="15" id="ReportShortCut15">FASTA</a>
```

Further investigation has revealed an alternative approach to crawling
NCBI records:

1. Fetch the URL: http://www.ncbi.nlm.nih.gov/protein/P02630.1
2. Grep for the meta "ncbi_uidlist" tag content:
   `<meta name="ncbi_uidlist" content="131104" />`
3. Parse the value and insert it into this URL:
   http://www.ncbi.nlm.nih.gov/sviewer/viewer.cgi?tool=portal&sendto=on&log$=seqview&db=protein&dopt=fasta&sort=&val=131104&from=begin&to=end

That will produce a plaintext file.

### Tuesday 18th

Ran a first instance of my crawler implementation `fetch-fast`,
processing 5,773 records and fetching 1736 FASTA records:

```
$ time cat sequence-urls.txt| ./fetch-fasta.py 2>report.error >report.json
cat sequence-urls.txt  0.00s user 0.00s system 0% cpu 0.064 total
./fetch-fasta.py 2> report.error > report.json  4.00s user 4.12s system 1% cpu 7:23.99 total
```

Over 5 minutes faster than a single threaded implementation:

```
$ time cat ~/src/pip-db-priv/sequence-urls.txt | ./fetch-fasta.py 2>report.error >report.json
cat ~/src/pip-db-priv/sequence-urls.txt  0.00s user 0.00s system 0% cpu 0.063 total
./fetch-fasta.py 2> report.error > report.json  2.48s user 3.89s system 0% cpu 12:42.85 total
```

I should consider implementing a compilation watcher so as to be able
to automatically rebuild the project when necessary.

Notes for weekly FYP meeting with Ian:

 * Review of meeting with Darren:

    * All very positive, Darren seems happy with the work done so far.

    * Commitments from Darren:
       * Text for the front page
       * 5 volunteers for user testing (probably week 23)
       * Help in simplifying the dataset

    * Covered both insecurities about current progress: data integrity
      and BLAST.

 * Three steps to "BLAST off":

    1. Crawl the FASTA sequences
    2. Setup a BLAST instance and create database
    3. Implement communication between pip-db and BLAST instance

 * Revised Gantt chart

    * Now with bug tracker links
    * No iteration labels

 * Step one of BLAST is done: multithreaded web crawler in Python:

    * Multithreaded (IO bound, minimise execution time)
    * Set based (minimise duplication)
    * Sockets level (minimise bandwidth)
    * Package and release in "Project segmentation" phase
    * As a service?

 * Tooling: `pipbot watch`.

 * Download page.

 * NEXT: numerical pI, MW and EC fields

### Wednesday 19th

Different kinds of search results:

 * **Matching results** - the number of results which matched a query.
 * **Returned results** - the number of results returned to the
   user. If *returned_results > max_returned_results*, then
   *returned_results < matching_results*.
 * **Visible results** - in case of results pagination, this shows the
   number of results currently visible.
   *visible_results < returned_results*.

Plan for search results data structure:

```
{
  "query": {
    "q": <q query string>
    "q_any": <q_any query string>
    ...
  }
  "no_of_records": <no of records in database>
  "no_of_matches": <no of records which matched query>
  "no_of_returned_records": <no of records returned to the user>
  "max_no_of_returned_records": <max number of records returned for a query>
  "records:" [
    {
      <record fields>
    },
    {
      <record fields>
    },
    ...
  ]
}
```

The `model/search` namespace should contain a function `(search
[params])` which returns a data structure of that type.

Notes on connecting to local Postgres instance:

```
$ psql pip-db                 # Start prompt
> \c pip-db                   # Connect to database
> \dt                         # Describe tables
> \d+ records                 # Describe table
> SELECT * from users;        # Show table contents
> DROP TABLE IF EXISTS users; # Delete table
```

To connect to remote Heroku database:

```
$ psql -h ec2-54-197-249-167.compute-1.amazonaws.com -U wvihkhkcwenphx -d dcmcfqgrb9iovm
```

### Thursday 20th

Notes from meeting with Ian:

 * The advantage of iteration labels is that it gives you specific
   dates by which you can expect a set of actions to be completed. In
   may case, that feature is handled by GitHub milestones.

 * When writing up the report, split into two sections: system
   construction, and system construction tooling (wording?). i.e. make
   the distinction between the two parts of the project - build an
   application, and building the supporting tools.

Yet Another Protein Schema format:

```
{
  "names": [<name1>,<name2>...],
  "ec": [<ec1>,<ec2>,<ec3>,<ec4>],
  "source": {
    "binomial": <latin binomial>,
    "common": <common name>
  },
  "location": <location>,
  "mw": {
    "min": <mw min>,
    "max": <mw max>
  },
  "sequence": <fasta>,
  "subunit": {
    "no": <subunit no>,
    "mw": <subunit mw>
  },
  "iso_enzymes": <no of iso-enzymes>,
  "pi": {
    "min": <min>,
    "max": <max>,
    "major": <pi of major component>
  },
  "temp": {
    "min": <min temp>,
    "max": <max temp>
  },
  "method": <experimental method>,
  "references": {
    "original_text": {
      "full": <url>,
      "abstract": <url>
    },
    "pubmed": <url>,
    "taxonomy": <url>,
    "sequence": <url>
  },
  "notes": <string>
}
```

Preparing and exporting dataset from Excel:

1. Ensure that header line is a *single line*.
2. Save as -> "Unicode text".
3. Run `iconv -f UTF-16 -t UTF-8 dataset.txt > dataset-utf8.txt`

The Authoritative list of dataset headers:

```
Sheet
EC
Protein
Alternative name(s)
Source
Organ and/or Subcellular locaction
M.W
Subunit No.
Subunit M.W
No. of Iso-enzymes
pI maximum value
pi Min Value
pi Max Value
pI value of major component
pI
Temperature (ºC)
Method
Valid sequence(s) available
UniportKB/ Swiss-Prot/ Protein sequence
Species Taxonomy
Full text
(Paid article) Only abstract available
Pubmed Link
Notes
```

A sensitive 3-layered approach to data integrity:

1. **Pre-processing** - Applied to existing dataset
    * Destructive
    * DO AS LITTLE AS POSSIBLE
        * Remove "Not given" values
        * Trim trailing/leading whitespace
        * Standardise capitalisation


2. **Storage-processing** - Upload time
    * Non-destructive
    * Adding data, not modifying or taking it away
        * Adding numerical pI fields
        * Symbolically linking similar results

3. **Post-processing** - Applied
    * Non-destructive
    * Applied lazily (on demand, each time a user requests it)
        * So we don't want to be doing too much, that's time consuming/wasted
        * Focuses on things that are likely to change
            * Relative timestamps [seconds-since-epoch] -> "8 minutes ago"
        * OR focuses on things that are easier to compute than to store
            * Switching between JSON/XML

### Friday 21st

TODO:

 * #298 YAPS: Use a flat JSON schema
 * #266 Add the ability to return data in XML/JSON formats
 * #297 models/search: Use exact matches for record ID query
 * #285 download: Table preview contains mis-aligned cells
 * #283 Name request handlers after the HTTP method they handle
 * #294 record: Add a "See other records like this" hover button
 * #299 Use regular expressions to match route parameters

New YAPS format:

```
{
  "names": [<name1>,<name2>...],
  "ec": <ec>,
  "source": <source>,
  "location": <location>,
  "mw_min": <mw min>,
  "mw_max": <mw max>,
  "sequence": <fasta>,
  "sub_no": <subunit no>,
  "sub_mw": <subunit mw>,
  "iso_enzymes": <no of iso-enzymes>,
  "pi_min": <min>,
  "pi_max": <max>,
  "pi_major": <pi of major component>,
  "temp_min": <min temp>,
  "temp_max": <max temp>,
  "method": <experimental method>,
  "ref_full": <url>,
  "ref_abstract": <url>,
  "ref_pubmed": <url>,
  "ref_taxonomy": <url>,
  "ref_sequence": <url>,
  "notes": <string>
}
```

### Saturday 22nd

While refactoring the database back-end I noticed a massive bottleneck
in the data upload performance caused by the need to create a new
database connection for every record inserted into the database,
instead of globbing all new record data into a single database
transaction. Below is the commit message for the patch I wrote to
address this:

```
    db: Implement multiple row insertion per connection

    This modifies the implementation of the back-end's "add-record" function
    so that it instead accepts a variable number of records, and retains a
    single database connection to insert all of the values. This in effect
    requires a O(1) time complexity overhead for establishing and closing
    database connections, instead of the O(n) complexity required in order
    to add individual row's one at a time.

    The result of this change is a staggering decrease in data upload
    times. For a dataset of 5773 records, the record insertion time is as
    follows:

         Single row insertion per connection:      42.608 s
         Multiple row insertion per connection:    03.512 s

    This demonstrates an approximate ~1200 % increase in performance as a
    result of this patch.
```

On the Heroku instance, the same operation requires 10.785 s, so we
should aim to factor in an ~ 300 % drop in performance between the
development server and deployment.

### Monday 24th

Got an email from Heroku, warning about the excessive amount of rows
used in my implementation of autocomplete:

> The database HEROKU_POSTGRESQL_RED_URL on Heroku app pip-db has
> exceeded its allocated storage capacity. Immediate action is
> required.

> The database contains 18,230 rows, exceeding the Dev plan limit of
> 10,000. INSERT privileges to the database will be automatically
> revoked in 7 days. This will cause service failures in most
> applications dependent on this database.

For now, I will re-implement autocomplete so as to not require so many
table entries, but in the long run I should consider an alternative
hosting strategy for the application.

### Tuesday 25th

Noticed a deadline coming up soon (7th March) for "Joint assessment of
interim review and oral presentation by supervisor and
moderator". Should ask Ian about this at next meeting.

### Wednesday 26th

I've refactored and reimplemented the autocomplete logic so that
completion table sizes are limited to 1000 rows each. This has brought
me under the 10,000 row limit, but only just:

> The database HEROKU_POSTGRESQL_RED_URL on Heroku app pip-db is
> approaching its allocated storage capacity.

> The database contains 9,801 rows. The Dev plan allows a maximum of
> 10,000 rows. If the databases exceeds 10,000 rows then INSERT
> privileges will be revoked, preventing more data from being
> written. INSERT privileges are automatically reinstated if rows are
> removed and the database once again complies with the plan limit.

I think I'll bump the limit down to ~800 to give myself a bit of row
overhead when implementing sessions etc.

Implemented FASTA crawling functionality to `csv2yaps`. New run time:

```
./tools/csv2yaps/csv2yaps.js ../pip-db-priv/dataset.txt >   6.18s user 4.39s system 2% cpu 7:31.20 total
```

Notes for meeting with Ian:

 * YAPS - *Yet Another Protein Schema* - Flat JSON encoded, designed
   to closely match dataset format. Example YAPS file:

```
{
  "Encoding": "yaps",
  "Version": 3,
  "Date": "2014-02-26 23:28:09",
  "Author": "chris@vm-ubuntu",
  "Agent": "/home/chris/src/pip-db/tools/csv2yaps/csv2yaps.js",
  "Source": "/home/chris/src/pip-db-priv/dataset-test.txt",
  "No-Of-Records": 1,
  "Records": [
    {
      "Protein-Names": [
        "Acetoacetyl-CoA thiolase",
        "Acetyl-CoA acetyltransferase"
      ],
      "EC": "2.3.1.9",
      "Source": "Saccharomyces cerevisiae (Yeast)",
      "Location": "Cytosol",
      "MW-Min": "140000",
      "MW-Max": "140000",
      "No-Of-Iso-Enzymes": "1",
      "pI-Min": "5.3",
      "pI-Max": "5.3",
      "Temperature-Min": "4",
      "Temperature-Max": "4",
      "Method": "Isoelectric focusing",
      "Full-Text": "http://www.jbc.org/content/246/14/4424.full.pdf",
      "PubMed": "http://www.ncbi.nlm.nih.gov/pubmed/5571830",
      "Species-Taxonomy": "http://www.ncbi.nlm.nih.gov/Taxonomy/Browser/wwwtax.cgi?lvl=0&id=4932",
      "Protein-Sequence": "http://www.uniprot.org/uniprot/P41338",
      "Sequence": ">sp|P41338|THIL_YEAST Acetyl-CoA acetyltransferase OS=Saccharomyces cerevisiae (strain ATCC 204508 / S288c) GN=ERG10 PE=1 SV=3\nMSQNVYIVSTARTPIGSFQGSLSSKTAVELGAVALKGALAKVPELDASKDFDEIIFGNVL\nSANLGQAPARQVALAAGLSNHIVASTVNKVCASAMKAIILGAQSIKCGNADVVVAGGCES\nMTNAPYYMPAARAGAKFGQTVLVDGVERDGLNDAYDGLAMGVHAEKCARDWDITREQQDN\nFAIESYQKSQKSQKEGKFDNEIVPVTIKGFRGKPDTQVTKDEEPARLHVEKLRSARTVFQ\nKENGTVTAANASPINDGAAAVILVSEKVLKEKNLKPLAIIKGWGEAAHQPADFTWAPSLA\nVPKALKHAGIEDINSVDYFEFNEAFSVVGLVNTKILKLDPSKVNVYGGAVALGHPLGCSG\nARVVVTLLSILQQEGGKIGVAAICNGGGGASSIVIEKI"
    }
}
```

 * Tool `csv2yaps` performs 1st stage (destructive) data
   modifications, and mines FASTA sequences. Detects header types and
   assigns them to properties, offering flexibility of incoming data
   formatting. Example session:

```
$ ./tools/csv2yaps/csv2yaps.js ../pip-db-priv/dataset-test.txt >../pip-db-priv/dataset-test.json
At line 1:	[WARNING]	Ignoring unrecognised column "Sheet"
At line 1:	[WARNING]	Ignoring unrecognised column "Valid sequence(s) available"
At line 2:	[WARNING]	Ignoring value "Not Given (N.G.)" for property "pI-Min"
At line 2:	[WARNING]	Ignoring value "Not Given (N.G.)" for property "pI-Max"
At line 2:	[WARNING]	Ignoring value "Not Given (N.G.)" for property "pI-Max"
At line 2:	[WARNING]	Ignoring value "Not Given (N.G.)" for property "pI-Major-Component"
At line 3:	[WARNING]	Ignoring value "Not available (N.A.)" for property "Protein-Names"
At line 3:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 3:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 3:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 3:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 3:	[WARNING]	Ignoring value "No entry" for property "Protein-Sequence"
At line 4:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 4:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 4:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 4:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 4:	[WARNING]	Ignoring value "Not Given (N.G)" for property "Temperature"
At line 5:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 5:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 5:	[WARNING]	Ignoring value "N.G" for property "pI-Min"
At line 5:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 5:	[WARNING]	Ignoring value "N.G" for property "pI-Max"
At line 5:	[WARNING]	Ignoring value "N.G" for property "pI-Major-Component"
At line 5:	[WARNING]	Ignoring value "N.G" for property "Temperature"
At line 5:	[WARNING]	Ignoring value "Unavailable" for property "Full-Text"
At line 6:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 6:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 6:	[WARNING]	Ignoring value "N.G" for property "pI-Min"
At line 6:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 6:	[WARNING]	Ignoring value "N.G" for property "pI-Max"
At line 6:	[WARNING]	Ignoring value "N.G" for property "pI-Major-Component"
At line 6:	[WARNING]	Ignoring value "N.G" for property "Temperature"
At line 6:	[WARNING]	Ignoring value "Unavailable" for property "Full-Text"
At line 7:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 7:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 7:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 7:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 7:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 7:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 7:	[WARNING]	Ignoring value "No entry" for property "Protein-Sequence"
At line 8:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 8:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 8:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 8:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 8:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 8:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 8:	[WARNING]	Ignoring value "No entry" for property "Protein-Sequence"
At line 9:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 9:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 9:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 9:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 9:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 9:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 9:	[WARNING]	Ignoring value "Not available" for property "Method"
At line 9:	[WARNING]	Ignoring value "Unavailable" for property "Full-Text"
At line 9:	[WARNING]	Ignoring value "No entry" for property "Species-Taxonomy"
At line 9:	[WARNING]	Ignoring value "No entry" for property "Protein-Sequence"
At line 10:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 10:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 10:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 10:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 10:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 10:	[WARNING]	Ignoring value "Not available" for property "Method"
At line 10:	[WARNING]	Ignoring value "Unavailable" for property "Full-Text"
At line 10:	[WARNING]	Ignoring value "No entry" for property "Species-Taxonomy"
At line 10:	[WARNING]	Ignoring value "No entry" for property "Protein-Sequence"
At line 11:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 11:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 11:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 11:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 11:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 11:	[WARNING]	Ignoring value "Not available" for property "Method"
At line 11:	[WARNING]	Ignoring value "Unavailable" for property "Full-Text"
At line 11:	[WARNING]	Ignoring value "No entry" for property "Species-Taxonomy"
At line 11:	[WARNING]	Ignoring value "No entry" for property "Protein-Sequence"
At line 12:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 12:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 12:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 12:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 12:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 12:	[WARNING]	Ignoring value "N.G." for property "Temperature"
At line 12:	[WARNING]	Ignoring value "Not available" for property "Protein-Sequence"
At line 13:	[WARNING]	Ignoring value "N.G." for property "No-Of-Iso-Enzymes"
At line 13:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 13:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 13:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 13:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 13:	[WARNING]	Ignoring value "N.G." for property "Temperature"
At line 14:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 14:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 14:	[WARNING]	Ignoring value "N.G." for property "No-Of-Iso-Enzymes"
At line 14:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 14:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 14:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 14:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 14:	[WARNING]	Ignoring value "N.G." for property "Temperature"
At line 14:	[WARNING]	Ignoring value "Not available" for property "Protein-Sequence"
At line 15:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 15:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 15:	[WARNING]	Ignoring value "N.G." for property "No-Of-Iso-Enzymes"
At line 15:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 15:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 15:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 15:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 15:	[WARNING]	Ignoring value "N.G." for property "Temperature"
At line 15:	[WARNING]	Ignoring value "Not Available" for property "Full-Text"
At line 16:	[WARNING]	Ignoring value "n/a" for property "Temperature"
At line 17:	[WARNING]	Ignoring value "n/a" for property "Temperature"
At line 17:	[ERROR!!]	Line is too short. No column for property "Notes". Results may be corrupted.
At line 18:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 18:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 18:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 18:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 18:	[WARNING]	Ignoring value "N.G" for property "Temperature"
At line 18:	[WARNING]	Ignoring value "Unavailable" for property "Full-Text"
At line 19:	[WARNING]	Ignoring value "N.A." for property "Protein-Names"
At line 19:	[WARNING]	Ignoring value "N/a" for property "EC"
At line 19:	[WARNING]	Ignoring value "N.G." for property "MW"
At line 19:	[WARNING]	Ignoring value "N.G." for property "Subunit-No"
At line 19:	[WARNING]	Ignoring value "N.G." for property "Subunit-MW"
At line 19:	[WARNING]	Ignoring value "N.G." for property "No-Of-Iso-Enzymes"
At line 19:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 19:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 19:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 19:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 19:	[WARNING]	Ignoring value "Not available" for property "Method"
At line 19:	[WARNING]	Ignoring value "Unavailable" for property "Full-Text"
At line 19:	[WARNING]	Ignoring value "No entry" for property "PubMed"
At line 20:	[WARNING]	Ignoring value "N.G." for property "Subunit-No"
At line 20:	[WARNING]	Ignoring value "N.G." for property "pI-Min"
At line 20:	[WARNING]	Ignoring value "N.G." for property "pI-Max"
At line 20:	[WARNING]	Ignoring value "N.G." for property "pI-Major-Component"
At line 20:	[WARNING]	Ignoring value "N.G." for property "Temperature"
At line 21:	[WARNING]	Ignoring value "n/a" for property "MW"
warning: cannot process URL 'http://www.uniprot.org/uniprot/?query=Cupiennius+salei+Haemocyanin&sort=score'. Ignoring.
warning: cannot process URL 'http://www.uniprot.org/uniprot/?query=Eurypelma+californicum+Haemocyanin&sort=score'. Ignoring.
```

 * In keeping with the JSON-centric approach to data handling,
   implemented a set of APIs for querying pip-db. Example:

     http://www.pip-db.org/s?q=alkaline -> http://www.pip-db.org/api/s?q=alkaline
     http://www.pip-db.org/r/Njk5NWJkMWQ -> http://www.pip-db.org/api/r/Njk5NWJkMWQ

   The browser-friendly website is now almost entirely static HTML
   wrapped around this JSON API.

 * Record IDs are truncated b64 encoded SHA-1s of the YAPS
   record. Allows for verification (data integrity), deters data
   mining.

 * Implemented autocomplete using frequency tables. Limited to 850
   rows /table so as not to bust the 10,000 row Heroku limit. Created
   at upload time, and implemented using AJAX calls. Example:

     http://www.pip-db.org/api/ac?s=locations&t=h
     http://www.pip-db.org/api/ac?s=locations&t=he
     http://www.pip-db.org/api/ac?s=locations&t=hear

 * Getting more comfortable with Clojure - found some massive
   bottlenecks:

```
db: Implement multiple row insertion per connection
This modifies the implementation of the back-end's "add-record" function
so that it instead accepts a variable number of records, and retains a
single database connection to insert all of the values. This in effect
requires a O(1) time complexity overhead for establishing and closing
database connections, instead of the O(n) complexity required in order
to add individual row's one at a time.

The result of this change is a staggering decrease in data upload
times. For a dataset of 5773 records, the record insertion time is as
follows:

     Single row insertion per connection:      42.608 s
     Multiple row insertion per connection:    03.512 s

This demonstrates an approximate ~1200 % increase in performance as a
result of this patch.
```

And SLOC count is falling (1:1 client/server side code):

```
$ ./scripts/sloccount.sh
Commit: 779d79f56e42b68c8ffe83c2e08c0aad59c925c2
Date:   Wed Feb 26 17:43:34 2014 +0000
Rel:    pip-db 0.5.3

Build system: 1003
   486  configure.ac       (48.45%)
   344  Makefile.am        (34.30%)
   173  autogen.sh         (17.25%)

Resources: 9497
  7882  Less CSS           (82.99%)
  1615  JavaScript         (17.01%)

Sources: 2190
  1673  Clojure            (76.39%)
   517  Clojure (tests)    (23.61%)

Documentation: 29886
 29010  Markdown           (97.07%)
   876  LaTeX              (2.93%)

Tools: 11146
  6123  JavaScript         (54.93%)
  3848  Ruby               (34.52%)
  1049  Python             (9.41%)
   126  Shell              (1.13%)
```

 * AJAX search results indicator. Show HTTP headers used to implement.

 * Focus shifting towards release: for the first time there's no of
   bug reports > no of feature requests.

 * Next: BLAST searching.


## March 2014


### Sunday 2nd

Deploying to heroku failed with 0.5.4 release branch. Checking the
logs revealed a stack trace dump while running the app caused by a
missing inline JavaScript source. JavaScript scripts are generated at
build time and it seemed like the slug compilation phase skipped
executing `./bin/build`. Note that remote shells can be invoked with:

```
$ heroku run bash
```

Log output showing that incorrect build command is being run:

```
2014-03-03T04:14:46.213270+00:00 heroku[web.1]: State changed from crashed to starting
2014-03-03T04:14:53.811177+00:00 heroku[web.1]: Starting process with command `lein trampoline run`
```


### Monday 3rd

To convert UNIX timestamps to date cells in Excel:

```
=(A1/86400)+25569
```


### Tuesday 4th

Feedback from Gareth Bunch (1st person to work on the data):

> Ive had a look at the website, its a lot different to the excel
> sheet. It seems easy to navigate and looks very
> professional. Hopefully it is a successful and useful tool that can
> be used in the years to come.

Darren has also asked Fraser and Ben to take a look at it.

Idea for further development of plausible nonsense generator - develop
a tool which uses Markov chains to generate plausible yet nonsensical
datasets, when trained with an input dataset. Inputs and outputs would
both be CSV.


### Wednesday 5th

Notes from Midterm project review:

 * Again, re-iterating the point that the *structure* of the submitted
   report is going to be absolutely critical. I need to ensure that
   the report is structured so as to emphasise and give appropriate
   levels of details to the right sections.

 * I need to make sure that I have a firm and convincing justification
   for the LISP re-write. Perhaps it would be reasonable to contain a
   discussion of the differences/benefits.

Found a nice basic guide to formatting a thesis in LaTeX:
https://www.sharelatex.com/blog/2013/08/02/thesis-series-pt1.html

Notes for tomorrow's meeting with Ian:

 * Report: latex report format OK (typeface and line spacing)?

 * What assumptions should I make about the reader's knowledge of the
   tools/languages? (especially the esoteric ones)

> The report should be targeted at a competent graduate engineer.
> Also, it should contain enough context information to be readily
> understood by a project manager or an investment manager
> specialising in your field of work.

 * What does this mean?

> Project specification: As drawn up at the start of the project, and
> signed. This is mandatory.

 * Usage instructions? + Executables?

### Thursday 6th

Notes from weekly meeting with Ian:

 * Presentation was poor, didn't have coherent message or communicate
   exactly what I'd been doing.

 * For report: put the *big picture* list of achievements right up
   front and centre, don't make the examiner waffle through 80 pages
   of report in order to figure out whether the project is any good or
   not.

 * Report formatting - LaTeX is OK.

 * Report structure needs to have more top-level chapters.

 * Consider putting the process and tooling section before the website
   section.

 * Always consider second examiner when writing report - don't
   underestimate the amount of context required, and assume that
   they're electronic engineers, not computer scientists. Every new
   tool/language needs an explanation.

 * I can use footnotes to introduce new concepts and technologies (for
   example: "version control is maintained using a git*", footnote:
   "git is a blah blah blah...").

 * "User" means website users (so usage instructions cover how to use
   the website). Also, usage instructions means documentation on how
   to upload and manage the dataset (for Darren).

 * Add "uploading new data" to list of tasks for test cases.

 * For next time: Build up the *big picture list* of 6 or so main
   successes of the project.

 * For next time: Revise proposed structure of report and for each
   section add a short paragraph explaining the desired contents and
   message of the section.


### Saturday 8th

Found out about the HTTP archive, which will be useful for providing
real, empirical evidence for measuring the effectiveness of page
optimisations:

http://httparchive.org/trends.php?s=All&minlabel=Nov+15+2010&maxlabel=Mar+1+2014


### Sunday 9th

Auditing the size of the project:

```
$ du -d1
376    ./autom4te.cache
932    ./tools
112    ./src
48016  ./Documentation
200    ./scripts
76     ./test
8      ./bin
27124  ./build
12572  ./node_modules
61852  ./pg
78444  ./.git
5700   ./target
1680   ./resources
237492 .
```

And ranked by size:

```
$ du -d1 | sort -n
8      ./bin
76     ./test
112    ./src
200    ./scripts
376    ./autom4te.cache
932    ./tools
1680   ./resources
5700   ./target
12572  ./node_modules
27124  ./build
48016  ./Documentation
61852  ./pg
78444  ./.git
237492 .
```

By using conditional Makefile generation in autoconf and by
destructively removing build files upon completion of `bin/build`,
slug size is reduced to 188M:

```
$ du -d1 | sort -n
8      ./bin
76     ./test
112    ./src
200    ./scripts
376    ./autom4te.cache
768    ./tools
1796   ./resources
47764  ./Documentation
61852  ./pg
78468  ./.git
191820 .
```


### Wednesday 12th

Major steps in pip-db life cycle:

1. Mockups first design (inspired by Google's aesthetic)
1. Prototype in PHP & MySQL
1. Autotooled build system, content hashing and minification
1. PHP templating (and differences from previous FYP)
1. MySQL schema (and differences from previous FYP)
    * Tools: dsa - Dataset Analaysis
1. Data integrity
    * Tools: png - Plausible Nonsense Generator
1. Branching model + Issue tracker workflow
    * Tools: pipbot
1. END OF M1 (user testing & review)
1. Clojure research & intro
1. Clojure port of M1 prototype
1. Heroku deployment
1. Working with HOFs and immutable data structures
1. RESTful API design
1. D2 user interface refinements

Feedback from Ben Stone, via Darren:

> I haven't encountered any problems running searches.  The only minor
> issue I have found is that the CSV file download produces tab
> delimited rather than comma delimited data and the file name has an
> extra '.' character before the extension.

> The presentation of data is clear. The only suggestion that I would
> make is that it might be helpful to include a glossary of terms or
> to annotate the search results to display explanations of
> abbreviations e.g. when the mouse hovers over them.  Examples might
> be to explain what s.p.c. means for subunit molecular weight or what
> the 'm' means as a suffix to pI values.


### Thursday 13th

A recap on the output format of my `fetch-fasta` tool:

```
$ echo http://www.uniprot.org/uniprot/Q5XI22 | fetch-fasta
{"url": "http://www.uniprot.org/uniprot/Q5XI22", "fasta": ">sp|Q5XI22|THIC_RAT Acetyl-CoA acetyltransferase, cytosolic OS=Rattus norvegicus GN=Acat2 PE=1 SV=1\nMNAGSDPVVIISAARTAIGSFNGALSTVPVHNLGTTVIKEVLQRAKVAPEEVSEVIFGHV\nLTAGCGQNPTRQASVGAGIPYSVPAWSCQMICGSGLKAVCLAAQSIAMGDSTIVVAGGME\nNMSKAPHLAHLRSGVKMGEVPLADSILCDGLTDAFHNYHMGITAENVAKKWQVSREAQDK\nVAVVSQNRAEHAQKAGHFDKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT\nDGTGTVTPANASGMNDGAAAVVLMKKTEAESRMLKPLAQVVSWSQAGVEPSVMGVGPIPA\nIKQAVAKAGWSLEDVDVFEINEAFAAVSAAIAKELGLSPEKVNIDGGAIALGHPLGASGC\nRILVTLLHTLERVGGTRGVAALCIGGGMGIAMCVQRG"}
```

Which generates the plain text FASTA sequence:

```
>sp|Q5XI22|THIC_RAT Acetyl-CoA acetyltransferase, cytosolic OS=Rattus norvegicus GN=Acat2 PE=1 SV=1
MNAGSDPVVIISAARTAIGSFNGALSTVPVHNLGTTVIKEVLQRAKVAPEEVSEVIFGHV
LTAGCGQNPTRQASVGAGIPYSVPAWSCQMICGSGLKAVCLAAQSIAMGDSTIVVAGGME
NMSKAPHLAHLRSGVKMGEVPLADSILCDGLTDAFHNYHMGITAENVAKKWQVSREAQDK
VAVVSQNRAEHAQKAGHFDKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT
DGTGTVTPANASGMNDGAAAVVLMKKTEAESRMLKPLAQVVSWSQAGVEPSVMGVGPIPA
IKQAVAKAGWSLEDVDVFEINEAFAAVSAAIAKELGLSPEKVNIDGGAIALGHPLGASGC
RILVTLLHTLERVGGTRGVAALCIGGGMGIAMCVQRG
```

As explained
[here](http://prodata.swmed.edu/pcma/info/fasta_format_file_example.htm),
FASTA sequences only actually contain two components:

> FASTA format: A sequence record in a FASTA format consists of a
> single-line description (sequence name), followed by line(s) of
> sequence data. The first character of the description line is a
> greater-than (">") symbol.

So, in order to save space, we should remove the hard line-wrapping of
the of the sequence data, so the FASTA output would be:

```
>sp|Q5XI22|THIC_RAT Acetyl-CoA acetyltransferase, cytosolic OS=Rattus norvegicus GN=Acat2 PE=1 SV=1
MNAGSDPVVIISAARTAIGSFNGALSTVPVHNLGTTVIKEVLQRAKVAPEEVSEVIFGHVLTAGCGQNPTRQASVGAGIPYSVPAWSCQMICGSGLKAVCLAAQSIAMGDSTIVVAGGMENMSKAPHLAHLRSGVKMGEVPLADSILCDGLTDAFHNYHMGITAENVAKKWQVSREAQDKVAVVSQNRAEHAQKAGHFDKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLTDGTGTVTPANASGMNDGAAAVVLMKKTEAESRMLKPLAQVVSWSQAGVEPSVMGVGPIPAIKQAVAKAGWSLEDVDVFEINEAFAAVSAAIAKELGLSPEKVNIDGGAIALGHPLGASGCRILVTLLHTLERVGGTRGVAALCIGGGMGIAMCVQRG
```

This also enables us to validate the FASTA sequence by ensuring that:

1. It is *exactly* two lines long.
2. The first line begins with a '>' symbol.

We could do this verification on the server side at upload time or on
the client side (`csv2yaps` or `fetch-fasta`). My preference would be
client side, since this helps keep a minimal server implementation,
and allows for catching formatting errors at an early stage, giving
the user the option to correct the error.

**Generating BLAST+ protein database**

Wrote another tool `yaps2fsa` which converts a YAPS data set into a
FASTA file which can be used to generate BLAST+ databases.

```
$ ./makeblastdb -in ~/src/pip-db-priv/dataset.fsa -dbtype 'prot' -out pip-db


Building a new DB, current time: 03/13/2014 11:40:26
New DB name:   pip-db
New DB title:  /home/chris/src/pip-db-priv/dataset.fsa
Sequence type: Protein
Keep Linkouts: T
Keep MBits: T
Maximum file size: 1000000000B
Adding sequences from FASTA; added 2822 sequences in 0.11529 seconds.
```

Example XML query:

```
$ ./blastp -db pip-db -query ~/example-query.fas -outfmt 5
```

where:

```
$ cat ~/example-query.fas
DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT
DGTGTVTPANASGMNDGAAAVVLMKKTEAESRMLKPLAQVVSWSQAGVEPSVMGVGPIPA
IKQAVAKAGWSLEDVDVFEINEAFAAVSAAIAKELGLSPEKVNIDGGAIALGHPLGASGC
RILVTLLHTLERVGGTRGVAALCIGGGMGIAMCVQRG
```

From the blastp help pages:

```
 -outfmt <String>
   alignment view options:
     0 = pairwise,
     1 = query-anchored showing identities,
     2 = query-anchored no identities,
     3 = flat query-anchored, show identities,
     4 = flat query-anchored, no identities,
     5 = XML Blast output,
     6 = tabular,
     7 = tabular with comment lines,
     8 = Text ASN.1,
     9 = Binary ASN.1,
    10 = Comma-separated values,
    11 = BLAST archive format (ASN.1)
```

Requested a meeting with Darren to discuss BLAST+ query results, and
how to best display the resulting data to the user.

Note that the BLAST+ database path is configured in environment
variable `$BLASTDB`.


### Friday 14th

Notes from meeting with Darren:

3 tasks, 5 users

 * Darren's very happy with overall progress of the project.

 * Download results should omit:

    1. Full text + Abstract only links
    2. Species Taxonomy link
    3. FASTA sequence
    4. Notes

 * I should look into guaranteeing availability of service if the
   website is going to be mentioned in research papers. Does Heroku
   guarantee uptime? Is the Uni going to re-register pip-db.org?
   Should we use a redirect from the Aston server?

 * For BLAST+ searches, the useful output is:

```
                                                                      Score     E
Sequences producing significant alignments:                          (Bits)  Value

  sp|Q5XI22|THIC_RAT Acetyl-CoA acetyltransferase, cytosolic OS=R...    402   1e-141
  sp|Q3T0R7|THIM_BOVIN 3-ketoacyl-CoA thiolase, mitochondrial OS=...    190   6e-59
  sp|Q3T0R7|THIM_BOVIN 3-ketoacyl-CoA thiolase, mitochondrial OS=...    190   6e-59
  sp|Q3T0R7|THIM_BOVIN 3-ketoacyl-CoA thiolase, mitochondrial OS=...    190   6e-59
  sp|P13437|THIM_RAT 3-ketoacyl-CoA thiolase, mitochondrial OS=Ra...    184   7e-57
  sp|P13437|THIM_RAT 3-ketoacyl-CoA thiolase, mitochondrial OS=Ra...    184   7e-57
  sp|P41338|THIL_YEAST Acetyl-CoA acetyltransferase OS=Saccharomy...    145   3e-42
  sp|Q29RZ0|THIL_BOVIN Acetyl-CoA acetyltransferase, mitochondria...    137   9e-39
  sp|Q29RZ0|THIL_BOVIN Acetyl-CoA acetyltransferase, mitochondria...    137   9e-39
  sp|Q29RZ0|THIL_BOVIN Acetyl-CoA acetyltransferase, mitochondria...    137   9e-39
  sp|Q29RZ0|THIL_BOVIN Acetyl-CoA acetyltransferase, mitochondria...    137   9e-39
  sp|P17764|THIL_RAT Acetyl-CoA acetyltransferase, mitochondrial ...    128   8e-36
  sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens...  25.0    3.0
  sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens...  25.0    3.0
  sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens...  25.0    3.0
  sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens...  25.0    3.0
  sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens...  25.0    3.0
  sp|Q71EW5|AHY_PELAE Acetylene hydratase OS=Pelobacter acetyleni...  25.4    3.2
  sp|Q29387|EF1G_PIG Elongation factor 1-gamma (Fragment) OS=Sus ...  24.3    6.0
```

And then for each result, the alignment string:

```
Query  61   AAVVLMKKTEAESRMLKPLAQVVSWSQAGVEPSVMGVGPIPAIKQAVAKAGWSLEDVDVF  120
             AV++  +   +     PLA++V +  +G +P++MG+GP+PAI  A+ K G SL+D+D+
Sbjct  258  GAVIIASEDAVKKHNFTPLARIVGYFVSGCDPTIMGIGPVPAISGALKKTGLSLKDMDLV  317
```

So the BLAST+ results page should include columns for score and E
value, and a "click to reveal" alignment string output.

**Deciphering BLAST+ search output**

Query used for tests (XML formatted):

```
BLASTDB=~/src/pip-db-priv/blast-db blastp -db pip-db -query ~/example-query.fas -outfmt 5 | less
```

Each "hit" is represented by a top-level node:

```
<Hit>
  <Hit_num>1</Hit_num>
  <Hit_id>gnl|BL_ORD_ID|1</Hit_id>
  <Hit_def>sp|Q5XI22|THIC_RAT Acetyl-CoA acetyltransferase, cytosolic OS=Rattus norvegicus GN=Acat2 PE=1 SV=1</Hit_def>
  <Hit_accession>1</Hit_accession>
  <Hit_len>397</Hit_len>
  <Hit_hsps>
    <Hsp>
      <Hsp_num>1</Hsp_num>
      <Hsp_bit-score>402.519</Hsp_bit-score>
      <Hsp_score>1033</Hsp_score>
      <Hsp_evalue>1.15272e-141</Hsp_evalue>
      <Hsp_query-from>1</Hsp_query-from>
      <Hsp_query-to>199</Hsp_query-to>
      <Hsp_hit-from>199</Hsp_hit-from>
      <Hsp_hit-to>397</Hsp_hit-to>
      <Hsp_query-frame>0</Hsp_query-frame>
      <Hsp_hit-frame>0</Hsp_hit-frame>
      <Hsp_identity>199</Hsp_identity>
      <Hsp_positive>199</Hsp_positive>
      <Hsp_gaps>0</Hsp_gaps>
      <Hsp_align-len>199</Hsp_align-len>
      <Hsp_qseq>DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLTDGTGTVTPANASGMNDGAAAVVLMKKTEAESRMLKPLAQVVSWSQAGVEPSVMGVGPIPAIKQAVAKAGWSLEDVDVFEINEAFAAVSAAIAKELGLSPEKVNIDGGAIALGHPLGASGCRILVTLLHTLERVGGTRGVAALCIGGGMGIAMCVQ
      <Hsp_hseq>DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLTDGTGTVTPANASGMNDGAAAVVLMKKTEAESRMLKPLAQVVSWSQAGVEPSVMGVGPIPAIKQAVAKAGWSLEDVDVFEINEAFAAVSAAIAKELGLSPEKVNIDGGAIALGHPLGASGCRILVTLLHTLERVGGTRGVAALCIGGGMGIAMCVQ
      <Hsp_midline>DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLTDGTGTVTPANASGMNDGAAAVVLMKKTEAESRMLKPLAQVVSWSQAGVEPSVMGVGPIPAIKQAVAKAGWSLEDVDVFEINEAFAAVSAAIAKELGLSPEKVNIDGGAIALGHPLGASGCRILVTLLHTLERVGGTRGVAALCIGGGMGIAM
    </Hsp>
  </Hit_hsps>
</Hit>
```

**BLAST TODO**

1. Read up on `blastp` flags and determine the *exact* command we'll
   use to run queries from the web.
2. Read up on Clojure
   [subprocess execution](http://clojuredocs.org/clojure_core/1.2.0/clojure.java.shell/sh).
3. Mockup the FASTA sequence input form for search queries.
4. Mockup the BLAST+ search results page.
5. Incorporate `pip-db-blast` BLAST+ script to `pip-db`.
6. Implement FASTA sequence input form.
7. Run example tests to pipe BLAST+ output to user.
8. Implement wrapper around BLAST+ search output.
9. Tie-in blast database generation to data upload process.

Changes to notify Darren about:

 * Remove unwanted columns from Download page output.
 * Fixed double dot '..' issue with downloaded file name.
 * CSV downloads are now comma separated, not tab.

Notes on `blastp` invocation:

 * Queries can be fed in through `stdin` or file:

```
# this:
$ blastp -query ~/foo.fsa
# is equvalient to:
$ echo ~/foo.fsa | blastp
```

 * Maximum evalue can be set with `-evalue 5.0`.

```
BLASTDB=~/src/pip-db-priv/blast-db blastp -db pip-db -outfmt 5 -evalue 10
```

 * Managed to get blastp invocation working in a Clojure REPL by using
   a relative address for the BLAST+ db. XML output:

```
user=> (use '[clojure.java.shell :only [sh]])

user => (require '[clojure.xml :as xml]
                 '[clojure.zip :as zip])

user => (defn zip-str [s]
          (zip/xml-zip (xml/parse (java.io.ByteArrayInputStream. (.getBytes s)))))

user=> (def r (sh "blastp" "-db" "pip-db" "-outfmt" "5"
                  "-evalue" "10"
                  :in "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT"
                  :env {"BLASTDB" "../pip-db-priv/blast-db"}))

user => (def a ((zip-str (r :out)) 0))
```

And for tabular output:

```
user=> (def r (sh "blastp" "-db" "pip-db"
                  "-outfmt" "6 stitle score evalue qseq sseq"
                  "-evalue" "10"
                  :in "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT"
                  :env {"BLASTDB" "../pip-db-priv/blast-db"}))

user=> (def a (str/split (r :out) #"\n"))

user=> (defn str->result [a]
         (let [c (str/split a #"\t")]
           {:title (c 0)
            :score (c 1)
            :evalue (c 2)
            :qseq (c 3)
            :sseq (c 4)}))

user=> (def records (map str->result (str/split (r :out) #"\n")))

user=> (clojure.pprint/write (apply vector records))
[{:title
  "sp|Q5XI22|THIC_RAT Acetyl-CoA acetyltransferase, cytosolic OS=Rattus norvegicus GN=Acat2 PE=1 SV=1",
  :score "229",
  :evalue "2e-25",
  :qseq "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT",
  :sseq "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT"}
 {:title
  "sp|Q3T0R7|THIM_BOVIN 3-ketoacyl-CoA thiolase, mitochondrial OS=Bos taurus GN=ACAA2 PE=2 SV=1",
  :score "99",
  :evalue "2e-07",
  :qseq "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "DNEMAPVEVKTRKGKQTMQVDEHPRPQTTMEQLNKLPPVF"}
 {:title
  "sp|Q3T0R7|THIM_BOVIN 3-ketoacyl-CoA thiolase, mitochondrial OS=Bos taurus GN=ACAA2 PE=2 SV=1",
  :score "99",
  :evalue "2e-07",
  :qseq "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "DNEMAPVEVKTRKGKQTMQVDEHPRPQTTMEQLNKLPPVF"}
 {:title
  "sp|Q3T0R7|THIM_BOVIN 3-ketoacyl-CoA thiolase, mitochondrial OS=Bos taurus GN=ACAA2 PE=2 SV=1",
  :score "99",
  :evalue "2e-07",
  :qseq "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "DNEMAPVEVKTRKGKQTMQVDEHPRPQTTMEQLNKLPPVF"}
 {:title
  "sp|P13437|THIM_RAT 3-ketoacyl-CoA thiolase, mitochondrial OS=Rattus norvegicus GN=Acaa2 PE=2 SV=1",
  :score "78",
  :evalue "1e-04",
  :qseq "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "NEEMAPIEVKTKKGKQTMQVDEHARPQTTLEQLQNLPPVF"}
 {:title
  "sp|P13437|THIM_RAT 3-ketoacyl-CoA thiolase, mitochondrial OS=Rattus norvegicus GN=Acaa2 PE=2 SV=1",
  :score "78",
  :evalue "1e-04",
  :qseq "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "NEEMAPIEVKTKKGKQTMQVDEHARPQTTLEQLQNLPPVF"}
 {:title
  "sp|Q29387|EF1G_PIG Elongation factor 1-gamma (Fragment) OS=Sus scrofa GN=EEF1G PE=2 SV=2",
  :score "49",
  :evalue "0.62",
  :qseq "IDEFPRHGSNLEAMSKLKPYF",
  :sseq "LDEFKRKYSNEDTLSVALPYF"}
 {:title
  "sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens GN=BPGM PE=1 SV=2",
  :score "48",
  :evalue "0.84",
  :qseq "VHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "IYNDRRYKVCDVPLDQLPRSESLKDVLERLLPYW"}
 {:title
  "sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens GN=BPGM PE=1 SV=2",
  :score "48",
  :evalue "0.84",
  :qseq "VHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "IYNDRRYKVCDVPLDQLPRSESLKDVLERLLPYW"}
 {:title
  "sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens GN=BPGM PE=1 SV=2",
  :score "48",
  :evalue "0.84",
  :qseq "VHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "IYNDRRYKVCDVPLDQLPRSESLKDVLERLLPYW"}
 {:title
  "sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens GN=BPGM PE=1 SV=2",
  :score "48",
  :evalue "0.84",
  :qseq "VHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "IYNDRRYKVCDVPLDQLPRSESLKDVLERLLPYW"}
 {:title
  "sp|P07738|PMGE_HUMAN Bisphosphoglycerate mutase OS=Homo sapiens GN=BPGM PE=1 SV=2",
  :score "48",
  :evalue "0.84",
  :qseq "VHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYF",
  :sseq "IYNDRRYKVCDVPLDQLPRSESLKDVLERLLPYW"}
 {:title
  "sp|P41338|THIL_YEAST Acetyl-CoA acetyltransferase OS=Saccharomyces cerevisiae (strain ATCC 204508 / S288c) GN=ERG10 PE=1 SV=3",
  :score "46",
  :evalue "1.4",
  :qseq "DKEIVPVHVSSRKGL--TEVKIDEFP",
  :sseq "DNEIVPVTIKGFRGKPDTQVTKDEEP"}
 {:title
  "sp|Q4KM73|KCY_RAT UMP-CMP kinase OS=Rattus norvegicus GN=Cmpk1 PE=1 SV=2",
  :score "42",
  :evalue "4.3",
  :qseq "IDEFPRHGSNLEAMSK",
  :sseq "IDGFPRNQDNLQGWNK"}
 {:title
  "sp|P11766|ADHX_HUMAN Alcohol dehydrogenase class-3 OS=Homo sapiens GN=ADH5 PE=1 SV=4",
  :score "41",
  :evalue "7.0",
  :qseq "EIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSK",
  :sseq "ESVPKLVSEYMS-KKIKVDEFVTHNLSFDEINK"}
 {:title
  "tr|O44028|O44028_TETPY Glyceraldehyde-3-phosphate dehydrogenase (NAD-dependent) OS=Tetrahymena pyriformis GN=gapC PE=3 SV=1",
  :score "40",
  :evalue "8.2",
  :qseq "DKEIVPVHVSSRKGLTEVKID-EFPRHGSNLEAMSK",
  :sseq "NESIIPTSTGARKALKEVLPEVEGKLHGMALRVPTK"}
 {:title
  "sp|O00085|PHYA1_ASPTE 3-phytase A OS=Aspergillus terreus GN=phyA PE=1 SV=1",
  :score "40",
  :evalue "9.8",
  :qseq "DKEIVPVHVSSRKGLTEVKIDEFPRHGSNLEAMSKLKPYFLT",
  :sseq "DESPFPLDVPEDCHITFVQV--LARHGARSPTHSKTKAYAAT"}]
```

### Saturday 15th

Completed BLAST+ back-end implementation:

 * [Example search](http://www.pip-db.org/s?a=a&q=alkaline)
 * [Example search with sequence](http://www.pip-db.org/s?a=a&q=alkaline&seq=GVKANEGTVGVSAATERSRCNTTQGNEVTSILRWAKDAGKSVGIVTTTRVNHATPSAAYAHSADRDWYSDNEMPPEALSQGCK)


### Monday 18th

Notes for meeting with Ian:

 * Meeting with Darren:

    * For testing: 3 tasks, 5 users
    * BLAST+ searching

 * BLAST+ searching:

    * Reducing hosted Heroku size

 * Big picture list:

1. Mockups first design (inspired by Google's aesthetic)
1. Prototype in PHP & MySQL
1. Autotooled build system, content hashing and minification
1. PHP templating (and differences from previous FYP)
1. MySQL schema (and differences from previous FYP)
    * Tools: dsa - Dataset Analaysis
1. Data integrity
    * Tools: png - Plausible Nonsense Generator
1. Branching model + Issue tracker workflow
    * Tools: pipbot
1. END OF M1 (user testing & review)
1. Clojure research & intro
1. Clojure port of M1 prototype
1. Heroku deployment
1. Working with HOFs and immutable data structures
1. RESTful API design
1. D2 user interface refinements
1. BLAST+ searching


### Tuesday 18th

Notes from meeting with Ian:

 * BLAST+ searching:
    * Are we handling errors?
    * Mock `blastp` for testing
    * Just parse the XML, stoopid.
 * Report
    * Perhaps consider mind-mapping the thematic elements of the
      report
    * Three main chapters from big picture list:
       1. User oriented design (mockups, prototyping)
       2. Infrastructure (tooling, process)
       3. Product (UI, API etc)


### Saturday 22rd

I'm entering the fourth week allocated to report writing, which is
half of the allocated total time. Since the report is not even close
to being half way completed, I need to pick up the effort a bit or
else risk falling behind.


### Sunday 23rd

* Process
  * Development process
    * Open Source
    * Version Control
    * Branching model
  * Project Plan Recap
  * User oriented design
    * Mockups
    * Prototype & it's purpose / role in process

* Infrastructure
  * Git / GitHub workflow
  * Build systems

* Product
  * PHP prototype (technical overview)
  * LISP rewrite
    * PHP vs Clojure
  * Web programming with HOFs
  * Usage

### Thursday 27th

Notes from weekly meeting with Ian:

 * Need to start planning evaluation
   * Two aspects: project evaluation and system evaluation
     * Project: Did we stick to the plan (no), did we stick to deadlines etc.
     * System: Have non-functional & functional requirements been met, etc.

 * TODO: Chase up project plan / report question with Kate.

 * TODO: Chase up dates for testing & task list with Darren.

 * TODO: Create table of contents for report.


## April 2014


### Thursday 3rd

Notes from weekly meeting with Ian:

 * Introduction chapter should be numbered.
 * Introduction chapter is *really* high-level, so include
   objectives/aims, not requirements.
 * Requirements and requirements analysis is easily a 10 page section,
   and should be a top-level chapter before 'Process'.
 * There should be a top-level 'Evaluation' chapter before Conclusions.
 * Second year SE module covered requirements analysis.
 * Infrastructure - for each tool, make sure to *justify* the purpose:
   provide citations to studies, and where possible provide evidence
   of quantitative results.
 * Evaluation - evaluate Process, Infrastructure and Product all in
   one place.
 * TODO: *Evaluation plan* is the priority for now, since it relies on
   other people's timetable. The report writing can wait.


### Sunday 6th

Tried implementing AJAX file upload to no avail. I should look into
using one of the many off-the-shelf jQuery
plugins. [This one](http://malsup.com/jquery/form/#getting-started)
looks promising.


### Monday 7th

Emailed Darren regarding user testing and list of searches. Got
response:

> 1. Specific search: “lactoferrin”
>
> 2. Broad search: “kinase”
>
> 3. Fasta:
>
> >sp|P02754|LACB_BOVIN Beta-lactoglobulin OS=Bos taurus GN=LGB PE=1 SV=3
> MKCLLLALALTCGAQALIVTQTMKGLDIQKVAGTWYSLAMAASDISLLDAQSAPLRVYVE
> ELKPTPEGDLEILLQKWENGECAQKKIIAEKTKIPAVFKIDALNENKVLVLDTDYKKYLL
> FCMENSAEPEQSLACQCLVRTPEVDDEALEKFDKALKALPMHIRLSFNPTQLEEQCHI

I need to use those to flesh out an evaluation plan.

**User Testing Tasks List:**

1.
  1. Search for record with protein name *Lactoferrin*.
  2. Look up other records from the same *source*.

2.
  1. Search for all records containing the word *Kinase*, from *Human*
     source and with enzyme commission number *2.7.1.-*, and
     temperature greater than or equal to *4C*.

3.
  1. Search for all records with pI of less than *3* which don't
     contain the word *Kinase*.

  2. Find the result with the lowest *pI* and make a note of all of
     the protein's names.

4.
  1. Search for all records with a pI between *6-8* using the BLAST+
     sequence:

```
>sp|P02754|LACB_BOVIN Beta-lactoglobulin OS=Bos taurus GN=LGB PE=1 SV=3
MKCLLLALALTCGAQALIVTQTMKGLDIQKVAGTWYSLAMAASDISLLDAQSAPLRVYVE
ELKPTPEGDLEILLQKWENGECAQKKIIAEKTKIPAVFKIDALNENKVLVLDTDYKKYLL
FCMENSAEPEQSLACQCLVRTPEVDDEALEKFDKALKALPMHIRLSFNPTQLEEQCHI
```

  2. Make a copy of the sequence alignment strings for the result with
     the highest Score.


### Tuesday 8th

Read up on the differences between
[Usability testing](http://en.wikipedia.org/wiki/Usability_testing)
and
[Qualitative research](http://en.wikipedia.org/wiki/Qualitative_research). Ideally,
the product evaluation should include both.

Notes from meeting with Darren:

 * Overall impressions of the project is glowing. He's very happy with
   look and feel and behaviour.

 * Will get in touch over the next few weeks to discuss administration
   and how to add new data.

 * We *don't* want the ability to modify data in-place, or add
   individual records. That will make for awkward version control
   issues with having to synchronize the offline/online copies of the
   data. Instead, there should be a simple 1-click upload which wipes
   the existing tables and replaces them with entirely new
   ones. Perhaps the site should save a copy of the dataset file so
   that users can download it and modify later?

 * For BLAST+ search results, alignment and position information would
   be useful.

 * Darren has emailed half a dozen potential volunteers:

> Hi
>
> A final year CS student Chris Cummins has been implementing a web
> version of some data that my placement students have previously
> compiled. As the final step in the process, he needs to show this to
> a few people unfamiliar with the database and ask them to undertake
> a few tasks and thus assess the ease-of-use of his database
> implementation. I was wondering if you could spare him 10-15 minutes
> of your time, at some point in the coming days, to enable him to
> undertake this exercise. Please reply to Chris, who I have copied
> in.
>
> Thank you for your time. I appreciate it.
>
> BW
>
> DRF

 * I should contact Frasier directly with regards to user testing.

 * I *need* to come up with a much more rigorous script for the
   usability tests. The usability tests with Darren went very well,
   but largely only because of the help from him and his prior
   knowledge of the project. If he were coming to this cold, there
   would be have been a lot less value to the exercise.

 * Not sure if Aston branding is required / a good idea, but the
   homepage should definitely have Darren's contact details, and
   mistakes that are uncovered should be report to him. Perhaps the
   record page should include a "Is there a mistake? Get in touch"
   link.

Modifications needed for usability tests round 2:

 * Add a test which requires entering a Greek character.

 * Tests must cover download functionality, and maybe import into
   spreadsheet application.

 * Tests should be introduced with a short outline, then given a
   *specific* set of activities, with a definite end point.

 * List of tests should be printed.

 * State upfront the desire to record the session and specify what
   you'll do with the recording (evidence of testing).

 * Don't explain anything during the tests. Instead, let the user get
   confused and then hold a Q&A at the end to discuss anything.

 * Encourage the user to think out load and talk me through their
   thought processes and reactions.

 * Tests need a definite "END OF TEST".

Organised usability testing with Shahzad Mumtaz tomorrow at
1:30. Shazad is a computer science PhD student under Ian's
supervision, so should chat with him about his Aston PhD experience
while I have the opportunity.

**Notes from usability testing with Darren:**

TASK 1

 * User noticed an error in dataset.
   [Dolichyl monophosphatase](http://www.pip-db.org/r/YjBmZmMyOGU) had
   been entered incorrectly during the data entry phase. In this case,
   Darren would need to correct the error in the Excel sheet and
   re-upload the dataset.
 * User noticed there's no contact details for report factual errors
   in dataset.

TASK 2

 * Some confusion over the EC number entry form. Should the user type
   all digits into the first box? It isn't immediately obvious.
 * User didn't enter all fields initially, so clicked back button to
   add in the missing form. At this point, the inputs had lost their
   state and the user had to re-enter every single form input again.
 * To select a temperature range of >4C, the user entered 4C for the
   lower bound and 20000C for the upper bound. It is not obvious that
   leaving the upper bound blank is equivalent to > lower bound.

TASK 3

 * On several occasions, the indicator to show many results will be
   returned by a given search is out-of-sync with current form state.

TASK 4

 * Copy and pasting text into the FASTA sequence field does not cause
   it to immediately auto-grow.
 * User was mislead by the number of results indicator, expecting more
   results than were actually returned.

Things to change as a result of usability test:

 * No of results indicator needs to show when it's state does not
   match that of the current form.
 * Advanced search form should return state when pressing back
   button. A workaround for this would be to add a "Modify search
   parameters" button to the results page, which would load the
   advanced page with all input fields populated.
