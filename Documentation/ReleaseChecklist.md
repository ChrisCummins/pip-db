# Release Checklist

Here are some basic things that should be done before making a new
release.

* Run `git branch -a` and check for any unresolved working branches.
* Check the issue tracker for unresolved issues for this release.
* Decide on the new version number and bump it. Run `git log
  stable..master` and look through the changes, do they constitute a
  micro change (single issue fixes, little to no behavioural changes),
  a minor change (feature additions, significant refactors or
  behavioural changes), or a major change (complete overhauls,
  development iterations)?
* Run `make distclean`, `./autogen.sh`, `./configure`, and `make all
  install`. Does everything build properly?
* Run the test suite. Do any tests unexpectedly fail?
* Run the application. Does everything behave properly?
* Run `./scripts/indent.sh` and fix and commit any changes.
* Run the linter scripts in `./scripts/linter` and fix and commit any
  changes.

### After release

* Deploy stable HEAD to Heroku.
