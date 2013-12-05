# Patch Submission Checklist

Here are some basic things that should be done before submitting patches.

* Does your code run without errors under strict conditions?
* Does your code contain any style errors?
* Are your variables named descriptively?
* Does your code break any of the unit tests?
* Have you tested all exit points from your code?
* Have you tested every exception?
* If you've created a new function, did you document the function parameters in
  an appropriate style?
* Does your patch contain style-only changes mixed with real changes? These
  should be separated.
* Has the documentation been updated to reflect any changes?

### Back-end
* Have you mixed up front-end logic in the back-end logic?
* Where appropriate, have you used the pip-db wrapper functions insted of their
  native equivalents?

### Front-end

* Tested in an actual browser?
* Will it work in the supported browsers?
* Does it validate through a linter?
* Are there any implied globals?

### Suggested Reading
[MediaWiki Pre-commit checklist](http://www.mediawiki.org/wiki/Manual:Pre-commit_checklist)
