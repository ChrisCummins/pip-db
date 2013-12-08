# Submitting Patches

Read [SubmitChecklist.md](SubmitChecklist.md) for a list of items to check
before submitting code.

Commit patches with small diffs which form single *logical changes*. Don't
commit patches which address more than one logical issue at a time, instead
separating those changes into two or more patches. On the other hand, if there
is a single change in numerous files, group those changes into a single
patch. Thus a single logical change is contained within a single patch.

Read
[A Note About Git Commit Messages](http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html)
for a guide to writing comprehensive git commit messages. Commit messages should
be describe the technical detail of the change(s) in the patch, being as
specific as possible. Avoid generic comments such as "fix bug in X" or "add new
X". If the description starts to get long, that's a sign that the patch needs to
be split.

If a patch fixes a logged issue, add a line an empty line at the end of the
commit message, and beneath that, the text `Issue #X.` on a single line.
Example:

```
Capitalized, short (50 chars or less) summary

More detailed explanatory description, which is wrapped to 72
characters. This forms of the body of the commit message. In this case,
the commit addresses a specific issue in the tracker, so the commit
message ends with the issue number.

Issue #110.
```

If you want to refer to a specific commit, don't just refer to the SHA-1 ID of
the commit. Also include the full commit message contents, to make it easier
for to know what it is about.
Example:

```
    commit 101c14a8b725f101be3b0788c37079a8450d0896
    Author: Chris Cummins <chrisc.101@gmail.com>
    Date:   Thu Dec 5 19:55:49 2013 +0000

        scripts: Add a README.md

        Issue #189.
```

### Suggested Reading

[How to Get Your Change Into the Linux Kernel](https://raw.github.com/torvalds/linux/master/Documentation/SubmittingPatches)
(what this document is based on).
