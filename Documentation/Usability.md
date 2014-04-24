# Testing notes

I've implemented the placeholder fixes for the next round of user
testing, so the versioning is:

```
Usability tests 0-2    v0.6.1     7dbcc65dfa0542b7d1f2b9eb1609a90d42ffcdc9
Usability tests 3+     v0.6.2     0a776f01716ccb3a5c23e97e8aa4320728740cc8
```

## Darren

### Task 1

 * Participant noticed an error in dataset.
   [Dolichyl monophosphatase](http://www.pip-db.org/r/YjBmZmMyOGU) had
   been entered incorrectly during the data entry phase. In this case,
   Darren would need to correct the error in the Excel sheet and
   re-upload the dataset.
 * Participant noticed there's no contact details for report factual
   errors in dataset.

### Task 2

 * Some confusion over the EC number entry form. Should the
   participant type all digits into the first box? It isn't
   immediately obvious.
 * Participant didn't enter all fields initially, so clicked back
   button to add in the missing form. At this point, the inputs had
   lost their state and the participant had to re-enter every single
   form input again.
 * To select a temperature range of >4C, the participant entered 4C
   for the lower bound and 20000C for the upper bound. It is not
   obvious that leaving the upper bound blank is equivalent to > lower
   bound.

### Task 3

 * On several occasions, the indicator to show many results will be
   returned by a given search is out-of-sync with current form state.

### Task 4

 * Copy and pasting text into the FASTA sequence field does not cause
   it to immediately auto-grow.
 * The participant was mislead by the number of results indicator,
   expecting more results than were actually returned.

Things to change as a result of usability test:

 * No of results indicator needs to show when it's state does not
   match that of the current form.
 * Advanced search form should return state when pressing back
   button. A workaround for this would be to add a "Modify search
   parameters" button to the results page, which would load the
   advanced page with all input fields populated.

## Shahzad

Shahzad is a PhD student in his third year, having recently submitted
his thesis. His specialisation is computer science, although he is
familiar with the biological background and terminology. Shahzad had
never seen the project before so was coming to the evaluation "cold".

### Task 1

 * Participant was unsure what the main search bar does. Initially, he
   entered "Isoelectric point of Lactoferrin", expecting the search to
   have a semantic understanding, beyond just being a name look-up.
 * On records page, participant notes that the pI range using a dash
   ("8.8 - 9") is not explicitly obvious, and that the data could be
   annotated to state that it is a minimum to maximum range value.

### Task 2

 * Participant attempted to narrow results by source by entering
   "Lactoferrin + Human" into the search box. Again, there's
   uncertainty over the function of the text box. This may be in-part
   due to an inadequate introduction to the project at the start of
   the evaluation. Participant showed the PDB website as an example of
   a search box which is self-explanatory. Key differences included
   the placeholder text, and the tabs to indicate the specific field
   being searched.
 * Participant was observed pressing the "Show >>" link next to the
   FASTA sequence entry on the record page repeatedly. This may be
   because of the slightly misleading location of the text at the
   bottom of the details table, implying that pressing the link will
   reveal more information about the record, as on the results page.
 * Participant was unable to locate the Download button after several
   visual sweeps of the page. On the record page, the participant
   suggested that the download button be placed beneath the details
   table. For the results page, the participant commented that the
   location was OK, but the text should be annotated explicitly
   "Download all".
 * Participant incorrectly entered "2.1.7" into the first text box for
   the EC field. This search parameter is silently ignored by the
   search engine, with no visual feedback to indicate the parameter is
   unused.

### Task 3

 * Participant commented on the effectiveness of the results count
   indicator on the advanced search page, but was confused when the
   counter became out of sync with the current search state.
 * Participant was unsure of what the bar on the no of results counter
   indicator showed since the bar is non-linear.

### Task 4

 * The participant used the browser back button to return to the
   search form, which caused the form state to reload from the URL.

Things to change as a result of usability test:

 * Search forms should have informative placeholder text to indicate
   the kind of values expected in them.
 * The advanced search form shouldn't be setting the state of the
   form, the browser should.
 * I should go back over the site and do a ground-up re-evaluation of
   the usability, from the perspective of a someone who's coming to
   the site cold.
 * I should consider adding tabs for performing different category
   searches from the homepage.

## Dan

Dan is an Undergraduate business and psychology student in his final
year. As someone with no background in biology or computer science,
their purpose is to provide feedback on the immediate usability and
intuitiveness of the site. As a result, a longer introduction to the
project was given, which explained some of the background terminology,
and provided a minimum of information required to complete the tasks.

### Tasks 2

 * Participant attempted to negate Kinase search term by entering "NOT
   Kinase" into the protein name field.

 * Participant tried to set the pI search range without first turning
   the slider widget on, and was unsure why the slider grab handles
   weren't responding to user input.

### Task 3

 * Participant successfully identified external links section but
   didn't identify the PubMed article until prompted. This may have
   been due to a lack of understanding of what PubMed is.

### Task 4

 * Participant attempted to enter the FASTA sequence into the primary
   search box.

Things to change as a result of usability test:

 * Improve the quality of the input descriptions on the advanced
   page. Make it more apparent the types of input expeceted.
 * Change the wording of usability scenarios to remove direct
   references to CSV format, and instead describe that the participant
   would like to import the search results into a spreadsheet program.
 * Clicking on the pI slider grab handles should turn the widget on,
   if not already activated.

## Mahmood

Mahmood is a student completing his first year of a PhD in Biology.

### Task 1

 * Participant asked whether we could do the tests on his own PC,
   which I wasn't prepped for (no screen recording equipment).
 * Participant had no issue with the first task.

### Task 2

 * Participant commented that the placeholder text was very helpful in
   understanding how to fill in the form fields. Especially the
   E.C. field examples.
 * Participant was confused at how to get back from the Download page.

### Task 3

 * Slow internet connection meant that it the No of results counter
   was very out-of-sync with form state, especially when scrubbing the
   pI slider.

Things to change as a result of usability test:

 * Add a back button to the download page.

## Ben

Ben is a PhD student completing his second year in the Life and Health
Sciences department.

### Task 1

 * After clicking the "See other records like this", the participant
   was unsure of what the actual search term that was being used to
   show the results.

### Task 2

 * Participant was unsure whether it was possible to search for
   multiple sources simultaneously (it's not).
 * Participant was unsure whether it was necessary to enter a term
   into an input field to accept any value - i.e. a wild card.

### Task 3

 * Participant expected there to be an option to sort results by pI.

### Task 4

 * Participant was unsure whether to leave in the header line in the
   FASTA sequence.
 * Participant would have liked the ability to type in exact pI values
   rather than scrubbing the slider.

Things to change as a result of usability test:

 * Re-order the advanced search fields so that the "None" field
   appears directly after the "Any" field.
