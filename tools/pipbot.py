#!/usr/bin/env python

import calendar
import json
import subprocess
import re
import os
import sys
import time
import datetime
import dateutil.relativedelta
from git import Repo
from sys import argv
from sys import exit
from os.path import expanduser

pipbot_root = expanduser("~/.pipbot/")
pipbot_config_file = pipbot_root + "config.json"

REPL = False


def get_logo():
    return ("                  ,--.    ,--.\n"
            "                 ((O ))--((O ))\n"
            "               ,'_`--'____`--'_`.\n"
            "              _:  ____________  :_\n"
            "             | | ||::::::::::|| | |\n"
            "             | | ||::::::::::|| | |\n"
            "             | | ||::::::::::|| | |\n"
            "             |_| |/__________\\| |_|\n"
            "               |________________|\n"
            "            __..-'            `-..__\n"
            "         .-| : .----------------. : |-.\n"
            "       ,\\ || | |\\______________/| | || /.\n"
            "      /`.\\:| | ||  __  __  __  || | |;/,'\\\n"
            "     :`-._\\;.| || '--''--''--' || |,:/_.-':\n"
            "     |    :  | || .----------. || |  :    |\n"
            "     |    |  | || '--pipbot--' || |  |    |\n"
            "     |    |  | ||   _   _   _  || |  |    |\n"
            "     :,--.;  | ||  (_) (_) (_) || |  :,--.;\n"
            "     (`-'|)  | ||______________|| |  (|`-')\n"
            "      `--'   | |/______________\\| |   `--'\n"
            "             |____________________|\n"
            "              `.________________,'\n"
            "               (_______)(_______)\n"
            "               (_______)(_______)\n"
            "               (_______)(_______)\n"
            "               (_______)(_______)\n"
            "              |        ||        |\n"
            "              '--------''--------'\n")


def get_welcome_text():
    return "Hello there. My name is pipbot."


def get_goodbye_text():
    return "Goodbye!"


def get_help_text():
    return ("These are some of the things I can do:\n"
            "\n"
            "    pipbot build    <target> <build>\n"
            "    pipbot deploy   [<target> <build>]\n"
            "    pipbot undeploy [<target> <build>]\n"
            "        Build, deploy or undeploy a website configuration\n"
            "\n"
            "    pipbot build summary\n"
            "        Show the current project configuration\n"
            "\n"
            "    pipbot burndown\n"
            "        Show the changes made on a feature branch\n"
            "\n"
            "    pipbot burndown release\n"
            "        Show the changes made since the last release\n"
            "\n"
            "    pipbot burndown <number> days\n"
            "    pipbot burndown <number> hours\n"
            "        Show the changes made in the last <number> of days/hours\n"
            "\n"
            "    pipbot show <issue-number|commit-id|<target> <build>>\n"
            "        Tell me more about a particular thing\n"
            "\n"
            "    pipbot version\n"
            "        Show the current project version\n"
            "\n"
            "    pipbot issue <command ..>\n"
            "        Issue tracker commands:\n"
            "          list        List all issues\n"
            "          show        Show an issue's details\n"
            "          open        Open (or reopen) an issue\n"
            "          close       Close an issue\n"
            "          edit        Modify an existing issue\n"
            "          comment     Leave a comment on an issue\n"
            "          label       Create, list, modify, or delete labels\n"
            "          assign      Assign an issue to yourself (or someone else)\n"
            "          milestone   Manage project milestones\n"
            "\n"
            "    pipbot start  <issue|feature|release>\n"
            "    pipbot pause  [issue|feature|release]\n"
            "    pipbot finish [issue|feature|release]\n"
            "        Start, pause or complete work on an upstream issue,\n"
            "        downstream feature, or product release branch.\n"
            "\n"
            "    pipbot sloccount\n"
            "        Show the number of source lines of code\n")


def load_json_from_file(path):
    try:
        json_file = open(path)
        json_data = json.load(json_file)
        json_file.close()

        return json_data
    except IOError:
        fatal("Unable to open JSON file '" + path + "'!")


def get_pipbot_configuration():
    return load_json_from_file(pipbot_config_file)


def get_project_root():
    config = get_pipbot_configuration()
    return expanduser(config["project"]["path"])


def fatal(msg):
    print msg
    exit(1)


def is_int(s):
    try:
        int(s)
        return True
    except ValueError:
        return False


def show(args):

    def print_usage_and_return():
        print "Usage: pipbot show <issue-number|commit-id|<target> <build>>"
        return 1


    if len(args) < 1:
        return print_usage_and_return()

    # show <target> <build>
    if len(args) == 2:
        target = args[0]
        build = args[1]
        configure_string = "./configure " + get_configure_args(target, build)
        print " \\\n        ".join(configure_string.split(" "))
        return 0

    item = args[0]

    # Match issue numbers
    if is_int(item):
        try:
            run("./tools/ghi show " + item, False)
            return 0
        except:
            return 2
    # Match git commit hashes
    elif re.match("[0-9a-f]{8}[0-9a-f]*", item):
        try:
            run("git show " + item, False)
            return 0
        except:
            return 2
    else:
        return print_usage_and_return()


def grep(regex, path):
    file = open(path, "r")

    match = ""

    for line in file:
        if re.search(regex, line):
            match += line

    return match


def run(cmd, echo=True, stdout=True, stderr=True):
    if echo == True:
        print "$ " + cmd

    if stdout != True:
        cmd += " >/dev/null"

    if stderr != True:
        cmd += " 2>/dev/null"

    ret = os.system(cmd)
    if ret != 0:
        raise Exception('Command returned error code {0}'.format(ret))


def run_extern(command, args):
    try:
        run(command + " " + " ".join(args), False)
    except:
        return 2


def perform_action(action, cmd):
    sys.stdout.write(str(action) + "... ")
    sys.stdout.flush()
    run(cmd, False, False, False)
    print "[ok]"


def get_config_summary():
    file = open("config.summary", "r")
    return file.read()


def get_configure_args(target, build):

    config = get_pipbot_configuration()

    try:
        target_json = config["targets"][target]
    except KeyError:
        fatal("Couldn't find target configuration '" + target + "'")

    try:
        build_json = config["build"][build]
    except KeyError:
        fatal("Couldn't find build configuration '" + build + "'")

    return " ".join(build_json["configure"]["args"] +
                    target_json["configure"]["args"] +
                    build_json["configure"]["env"] +
                    target_json["configure"]["env"])


def build_target(target_name, build_name):

    configure_args = get_configure_args(target_name, build_name)

    if configure_args == 100 or configure_args == 1:
        return configure_args

    try:
        perform_action("Generating sources", "./autogen.sh")
        perform_action("Configuring " + target_name + " " + build_name + " build",
                       "./configure " + configure_args)
        perform_action("Cleaning build tree", "make -C build/ clean")
        perform_action("Cleaning source tree", "make -C www/ clean")
        perform_action("Building", "make -C www all")
        return 0
    except:
        return 2


def build(args):

    def print_usage_and_return():
        print "Usage: pipbot build <target> <build>"
        return 1

    if len(args) < 1:
        return print_usage_and_return()

    elif args[0] == "summary":
        print get_config_summary()

    elif len(args) == 2:
        return build_target(args[0], args[1])

    else:
        return print_usage_and_return()


def deploy(args):

    def print_usage_and_return():
        print "Usage: pipbot deploy [<target> <build>]"
        return 1

    # Support 'deploy <target> <build>' syntax
    if len(args) == 2:
        build_target(args[0], args[1])

    elif len(args) > 0:
        return print_usage_and_return()

    try:
        perform_action("Deploying", "make -C build/ install")
    except:
        return 2


def undeploy(args):

    def print_usage_and_return():
        print "Usage: pipbot undeploy [<target> <build>]"
        return 1

    # Support 'undeploy <target> <build>' syntax
    if len(args) == 2:
        build_target(args[0], args[1])

    elif len(args) > 0:
        return print_usage_and_return()

    try:
        perform_action("Undeploying", "make -C build/ uninstall")
    except:
        return 2


def rd_to_string(rd):
    s = ""
    if rd.years > 1:
        s += "%d years, " % rd.years
    elif rd.years > 0:
        s += "%d year, " % rd.years
    if rd.months > 1:
        s += "%d months, " % rd.months
    elif rd.months > 0:
        s += "%d month, " % rd.months
    if rd.days > 1:
        s += "%d days, " % rd.days
    elif rd.days > 0:
        s += "%d day, " % rd.days
    if rd.hours > 1:
        s += "%d hours, " % rd.hours
    elif rd.hours > 0:
        s += "%d hour, " % rd.hours

    if (rd.years == 0 and
        rd.months == 0 and
        rd.days == 0 and
        rd.hours == 0 and
        rd.minutes > 1):
        s += "%d minutes, " % rd.minutes
    elif (rd.years == 0 and
          rd.months == 0 and
          rd.days == 0 and
          rd.hours == 0 and
          rd.minutes == 1):
        s += "%d minute, " % rd.minutes

    if (rd.years == 0 and
        rd.months == 0 and
        rd.days == 0 and
        rd.hours == 0 and
        rd.minutes == 0 and
        rd.seconds == 1):
        s += "%d second, " % rd.seconds
    elif (rd.years == 0 and
          rd.months == 0 and
          rd.days == 0 and
          rd.hours == 0 and
          rd.minutes == 0):
        s += "%d seconds, " % rd.seconds

    return s[:-2]


# Find the last commit that is older than the target time (in seconds since
# epoch).
def get_first_commit_since(time, commit_list):
    last_commit = commit_list.next()
    for commit in commit_list:
        if commit.committed_date < time:
            return last_commit
        else:
            last_commit = commit

    # If we reached the last commit without matching our start date, then
    # just use the last commit found.
    return commit


def burndown(args):
    def print_usage_and_return():
        print "Usage: pipbot burndown [<number> <days|hours>|release]"
        return 1

    argc = len(args)

    repo = Repo(get_project_root())

    if argc < 1:
        origin = { "name": "HEAD", "head": repo.head.reference.commit }
        target = { "name": "master", "head": repo.heads.master.commit }
    elif argc == 1 and args[0] == "release":
        origin = { "name": "master", "head": repo.heads.master.commit }
        target = { "name": "stable", "head": repo.heads.stable.commit }
    elif argc == 2 and (args[1] == "day" or args[1] == "days"):
        if not is_int(args[0]):
            return print_usage_and_return()

        current_date = calendar.timegm(time.gmtime())
        target_date = current_date - int(args[0]) * 86400

        origin = { "name": "master", "head": repo.heads.master.commit }
        target = {
            "name" : "master",
            "head": get_first_commit_since(target_date,
                                           repo.iter_commits("master"))
          }
    elif argc == 2 and (args[1] == "hour" or args[1] == "hours"):
        if not is_int(args[0]):
            return print_usage_and_return()

        current_date = calendar.timegm(time.gmtime())
        target_date = current_date - int(args[0]) * 3600

        origin = { "name": "master", "head": repo.heads.master.commit }
        target = {
            "name" : "master",
            "head": get_first_commit_since(target_date,
                                           repo.iter_commits("master"))
          }
    else:
        return print_usage_and_return()

    print ("Comparing '" + origin["name"] +
           "' against '" + target["name"] + "'...")
    print

    commit_count = origin["head"].count() - target["head"].count()

    if argc > 0 and args[0] == "release":
        print "  The last release was " + get_version_string()
    if commit_count > 1:
        print ("  There are " + str(commit_count) + " new commits on "
               + origin["name"])
    elif commit_count == 1:
        print ("  There is " + str(commit_count) + " new commit on "
               + origin["name"])
    else:
        print "  There are no new commits on " + origin["name"]

    commit_date = datetime.datetime.fromtimestamp(target["head"].committed_date)
    current_date = datetime.datetime.fromtimestamp(calendar.timegm(time.gmtime()))
    rd = dateutil.relativedelta.relativedelta(current_date, commit_date)
    print ("  The last commit on " + target["name"]
           + " was " + rd_to_string(rd) + " ago")

    return 0


def create_new_working_branch(branch, base, remote_name):

    repo = Repo(get_project_root())
    remote = repo.remotes[remote_name]

    if repo.is_dirty() == True:
        print "The working tree contains uncommitted changes, commit or stash "
        print "these and try again."
        return 1

    try:
        head = repo.create_head(branch, base)
        print "Summary of actions:"
        print "- A new branch " + branch + " was created, based on " + base + "."
    except OSError:
        print "A branch " + branch + " already exists!"
        return 1

    ret = remote.push(head)
    info = ret[0]
    print ("- A new remote branch " + branch + " was created on " +
           remote_name + ".")

    head.set_tracking_branch(info.remote_ref)
    print ("- Branch " + branch + " tracks remote branch " + branch +
           " from " + remote_name + ".")

    head.checkout()

    print "- You are now on branch " + branch + "."

    return 0


def start_new_feature(feature):
    feature_branch_prefix = "feature/"
    feature_branch_base = "master"
    remote = "origin"

    branch = feature_branch_prefix + str(feature)

    ret = create_new_working_branch(branch, feature_branch_base, remote)
    if ret > 0:
        return ret

    print ""
    print "Now, start committing on your feature. When done, use:"
    print
    print "     pipbot finish " + str(feature)
    print ""


def start_new_issue(issue):

    issue_branch_prefix = "issue/"
    issue_branch_base = "master"
    remote = "origin"

    branch = issue_branch_prefix + str(issue)

    ret = create_new_working_branch(branch, issue_branch_base, remote)
    if ret > 0:
        return ret

    print ""
    print "Now, start committing on your issue. When done, use:"
    print
    print "     pipbot finish " + str(issue)
    print ""


def start_new_release(version):

    release_branch_prefix = "release/"
    release_branch_base = "master"
    remote = "origin"

    branch = release_branch_prefix + str(version)

    ret = create_new_working_branch(branch, release_branch_base, remote)
    if ret > 0:
        return ret

    try:
        run("./scripts/mkrelease.sh " + version, False, False)
        print ("- The version  number has been bumped to " + str(version) +
               " and committed")
    except:
        return 2

    print ""
    print "Now, start performing release fixes. When done, use:"
    print
    print "     pipbot finish " + str(version)
    print ""


def start(args):

    def print_usage_and_return():
        print "Usage: pipbot start <issue|feature|release>"
        return 1

    if len(args) != 1:
        return print_usage_and_return()

    target = args[0]

    if re.match("^[0-9]+\.[0-9]+\.[0-9]+$", target):
        return start_new_release(target)

    elif re.match("^[0-9]+$", target):
        return start_new_issue(target)

    elif re.match("^[a-zA-Z0-9_]+$", target):
        return start_new_feature(target)

    else:
        return print_usage_and_return()


def get_branch_name(tail):
    if re.match("^[0-9]+\.[0-9]+\.[0-9]+$", tail):
        return "release/" + tail
    elif re.match("^[0-9]+$", tail):
        return "issue/" + tail
    else:
        return "feature/" + tail


def pause(args):

    def print_usage_and_return():
        print "Usage: pipbot pause [issue|feature|release]"
        return 1

    argc = len(args)

    repo = Repo(get_project_root())
    remote = repo.remotes["origin"]

    if repo.is_dirty() == True:
        print "The working tree contains uncommitted changes, commit or stash "
        print "these and try again."
        return 1

    if argc == 0:

        target = repo.active_branch.name

        if not re.match("^(release|feature|issue)/", target):
            print "Not on a release, feature, or issue branch!"
            return 1

    elif argc == 1:

        target = get_branch_name(args[0])

        try:
            repo.heads[target]
        except:
            print "Branch " + target + " not found!"
            return 1

    elif argc > 1:
        return print_usage_and_return()

    head = repo.heads[target]
    remote.push(head)
    print "Summary of actions:"
    print "- Remote branch " + target + " on origin was updated."

    master = repo.heads.master
    master.checkout()
    print "- You are now on branch master."
    print ""


def finish_release(branch):

    version = branch.replace("release/", "")

    repo = Repo(get_project_root())
    remote = repo.remotes["origin"]

    if repo.is_dirty() == True:
        print "The working tree contains uncommitted changes, commit or stash "
        print "these and try again."
        return 1

    print "Summary of actions:"
    stable = repo.heads.stable
    stable.checkout()
    repo.git.merge(branch, '--no-ff')
    print ("- Branch " + branch + " was merged into stable.")

    tag = repo.create_tag(version)
    print ("- A release tag " + version + " was created.")

    remote.push(tag)
    print ("- Tag " + version + " was pushed to origin.")

    master = repo.heads.master
    master.checkout()
    repo.git.merge(branch, '--no-ff')
    print ("- Branch " + branch + " was merged into master.")

    remote.push(master)
    print ("- Merged changes on master were pushed to origin.")

    remote.push(stable)
    print ("- Merged changes on stable were pushed to origin.")

    repo.delete_head(branch, force=True)
    print ("- Branch " + branch + " was deleted.")

    ret = remote.push(":" + branch)
    print ("- Remote branch " + branch + " on origin was deleted.")

    print "- You are now on branch master."
    print ""

    return 0


def close_working_branch(branch, remote_name):

    repo = Repo(get_project_root())
    remote = repo.remotes[remote_name]

    if repo.is_dirty() == True:
        print "The working tree contains uncommitted changes, commit or stash "
        print "these and try again."
        return 1

    print "Summary of actions:"
    master = repo.heads.master
    master.checkout()

    repo.git.merge(branch, '--no-ff')
    print ("- Branch " + branch + " was merged into master.")

    repo.delete_head(branch, force=True)
    print ("- Branch " + branch + " was deleted.")

    ret = remote.push(":" + branch)
    print ("- Remote branch " + branch + " on " + remote_name + " was deleted.")

    remote.push(master)
    print ("- Merged changes on master were pushed to " + remote_name + ".")

    print "- You are now on branch master."

    return 0


def finish(args):

    def print_usage_and_return():
        print "Usage: pipbot finish [issue|feature|release]"
        return 1

    remote = "origin"

    argc = len(args)

    repo = Repo(get_project_root())

    if argc == 0:

        target = repo.active_branch.name

        if not re.match("^(release|feature|issue)/", target):
            print "Not on a release, feature, or issue branch!"
            return 1

    elif argc == 1:

        target = get_branch_name(args[0])

        try:
            repo.heads[target]
        except:
            print "Branch " + target + " not found!"
            return 1

    elif argc > 1:
        return print_usage_and_return()

    if re.match("^release/", target):
        return finish_release(target)

    elif (re.match("^issue/", target) or
          re.match("^feature/", target)):
        ret = close_working_branch(target, remote)
        if ret > 0:
            return ret

        print ""
        return 0

    else:
        return print_usage_and_return()


def issue(args):

    try:
        run("./tools/ghi " + " ".join(args), False)
        return 0
    except:
        return 2

def get_version():
    components = ["major", "minor", "micro"]
    values = []

    for component in components:
        line = grep("m4_define\(\\s*\\[pipdb_" + component + "_version\\]",
                    get_project_root() + "configure.ac")
        match = re.match(r"^.*,\s*\[?\s*(?P<value>\d+).*$", line)
        value = match.group("value")
        values.append(value)

    return values


def get_version_string():
    return ".".join([str(i) for i in get_version()])


def sloccount():

    try:
        run("./tools/sloccount", False)
        return 0
    except:
        return 2


def process_command(command, args):

    if command == "help":
        if REPL == True:
            print get_help_text()
        else:
            print get_logo() + "\n" + get_welcome_text() + " " + get_help_text()
        return 0

    elif command == "build":
        return build(args)

    elif command == "burndown":
        return burndown(args)

    elif command == "deploy":
        return deploy(args)

    elif command == "finish":
        return finish(args)

    elif command == "issue":
        return issue(args)

    elif command == "pause":
        return pause(args)

    elif command == "show":
        return show(args)

    elif command == "sloccount":
        return sloccount()

    elif command == "start":
        return start(args)

    elif command == "undeploy":
        return undeploy(args)

    elif command == "version":
        print get_version_string()
        return 0

    elif (command == "branch" or
          command == "checkout" or
          command == "fetch" or
          command == "pull" or
          command == "push" or
          command == "status"):
        return run_extern("git " + command, args)

    elif (command == "install" or
          command == "uninstall"):
        return run_extern("make " + command, args)

    elif (command == "./autogen.sh" or
          command == "autogen" or
          command == "autogen.sh" or
          command == "configure" or
          command == "git" or
          command == "gitk" or
          command == "ls" or
          command == "make" or
          command == "pwd"):
        return run_extern(command, args)

    else:
        print "I don't understand!"
        return 1


def process_batch_command(argv):
    if len(argv) > 0:
        command = str(argv[0]);
    else:
        command = "help"

    if len(argv) > 0:
        argv.pop(0)
        args = argv
    else:
        args = []

    return process_command(command, args)


def enter_repl_loop():

    def goodbye():
        print get_goodbye_text()
        return 0

    global REPL
    REPL = True

    print get_logo() + get_welcome_text()

    while True:
        try:
            sys.stdout.write("-> ")
            argv = [i.replace("\n", "") for i in sys.stdin.readline().split(" ")]

            if argv[0] == "exit" or argv[0] == "quit":
                return goodbye()

            ret = process_batch_command(argv)

        except KeyboardInterrupt:
            print
            return goodbye()


if __name__ == "__main__":

    os.chdir(get_project_root())

    if len(argv) < 2:
        enter_repl_loop()
        ret = 0
    else:
        argv.pop(0)
        ret = process_batch_command(argv)

    exit(ret)
