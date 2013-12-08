# Workflow

Pip-db uses
[A successful Git branching
model](http://nvie.com/posts/a-successful-git-branching-model/),
written by Vincent Driessen. The two main branches are called
[stable](https://github.com/ChrisCummins/pip-db/tree/stable) and
[master](https://github.com/ChrisCummins/pip-db/tree/master). The
[stable](https://github.com/ChrisCummins/pip-db/tree/stable) branch is the main
branch where the source code of `HEAD` always reflects a *production-ready*
state. The [master](https://github.com/ChrisCummins/pip-db/tree/master) branch
is the main branch where the code code of `HEAD` always reflects a state with
the latest delivered development changes for the next release.

### Issue-driven development

The issue tracker is used to keep an accountable history of all of the bugs,
features and modifications which are planned and made on pip-db. Before starting
work on a new feature or improvement, an issue which describes the required
changes should be filed. A feature branch is then created for this issue number,
and each patch submitted should reference the issue number in it's commit
message. See [SubmittingPatches.md](SubmittingPatches.md) for a guide to writing
suitable commit messages.
