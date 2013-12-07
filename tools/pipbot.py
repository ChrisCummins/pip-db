#!/usr/bin/env python

import json
import subprocess
import re
import os
import sys
from git import Repo
from sys import argv
from sys import exit

projectdir = "/home/chris/src/pip-db/"
prefixdir = "/home/chris/.local/"
etcdir = prefixdir + "etc/pipbot/"

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
            "    pipbot deploy   <target> <build>\n"
            "    pipbot undeploy <target> <build>\n"
            "        Build, deploy or undeploy a website configuration\n"
            "\n"
            "    pipbot build summary\n"
            "        Show the current project configuration\n"
            "\n"
            "    pipbot burndown\n"
            "        Compare the current branch against master\n"
            "\n"
            "    pipbot burndown stable\n"
            "        Compare the master branch against stable\n"
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
            "    pipbot pause  <issue|feature|release>\n"
            "    pipbot finish <issue|feature|release>\n"
            "        Start, pause or complete work on an upstream issue,\n"
            "        downstream feature, or product release branch.\n"
            "\n"
            "    pipbot sloccount\n"
            "        Show the number of source lines of code\n")


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


def get_json_from_file(name, path):
    json_file = open(path)
    json_data = json.load(json_file)
    json_file.close()

    for d in json_data:
        if d == name:
            return json_data[d]


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
    try:
        target_json = get_json_from_file(target, etcdir + "targets.json")
    except IOError:
        print "Unable to open JSON file '" + etcdir + "targets.json'"
        return 100

    try:
        build_json = get_json_from_file(build, etcdir + "build.json")
    except IOError:
        print "Unable to open JSON file '" + etcdir + "build.json'"
        return 100

    if target_json == None:
        print "Couldn't find target configuration '" + target + "'"
        return 1

    if build_json == None:
        print "Couldn't find build configuration '" + build + "'"
        return 1

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
        perform_action("Cleaning working tree", "make clean")
        perform_action("Building", "make all")
        return 0
    except:
        return 2


def build(args):

    if len(args) < 1:
        print "Usage: pipbot build <target> <build>"

    if args[0] == "summary":
        print get_config_summary()

    if len(args) == 2:
        return build_target(args[0], args[1])

def deploy(args):

    # Support 'deploy <target> <build>' syntax
    if len(args) == 2:
        build_target(args[0], args[1])

    try:
        perform_action("Deploying", "make -C build/ install")
    except:
        return 2


def undeploy(args):

    # Support 'undeploy <target> <build>' syntax
    if len(args) == 2:
        build_target(args[0], args[1])

    try:
        perform_action("Undeploying", "make -C build/ uninstall")
    except:
        return 2


def burndown(args):
    #TODO: actually parse args

    print "coor blimey!"
    return 0


def start_new_release(version):
    try:
        print "Starting new release " + version
        run("./tools/mkrelease " + version, False)
        return 0
    except:
        return 2

def start_new_feature(feature):
    try:
        print "Starting new feature branch '" + feature + "'"
        run("git flow feature start " + feature, False)
        repo = Repo(projectdir)
        branch = repo.active_branch
        run("git push -u origin " + branch.name, False)
        return 0
    except:
        return 2

def start_new_issue(issue_number):
    return start_new_feature(issue_number)

def start(args):

    def print_usage_and_return():
        print "Usage: pipbot start <issue|feature|release>"
        return 1

    if len(args) != 1:
        return print_usage_and_return()

    target = args[0]

    if re.match("^[0-9]+\.[0-9]+\.[0-9]$", target):
        return start_new_release(target)

    elif re.match("^[0-9]+$", target):
        return start_new_issue(target)

    elif re.match("^[a-zA-Z0-9_]+$", target):
        return start_new_feature(target)

    else:
        return print_usage_and_return()


def pause(args):

    def print_usage_and_return():
        print "Usage: pipbot pause <issue|feature|release>"
        return 1

    if len(args) != 1:
        return print_usage_and_return()

    target = args[0]

    if (re.match("^[0-9]+\.[0-9]+\.[0-9]$", target) or
        re.match("^[0-9]+$", target) or
        re.match("^[a-zA-Z0-9_]+$", target)):
        try:
            repo = Repo(projectdir)
            branch = repo.active_branch

            if not re.match("(release|feature)/" + target, branch.name):
                print "Target branch does not match current!"
                return 1

            run("git push -u origin " + branch.name, False)
            run("git checkout master", False)
            return 0
        except:
            return 2
    else:
        return print_usage_and_return()


def finish_release(version):
    try:
        print "Finishing release " + version
        repo = Repo(projectdir)
        branch = repo.active_branch
        run("git flow release finish " + version, False)
        run("git push origin :" + branch.name, False)
        return 0
    except:
        return 2


def finish_feature(feature):
    try:
        print "Closing feature branch '" + feature + "'"
        repo = Repo(projectdir)
        branch = repo.active_branch
        run("git flow feature finish " + feature, False)
        run("git push origin :" + branch.name, False)
        return 0
    except:
        return 2


def finish_issue(issue_number):
    # TODO: Close upstream issue
    return finish_feature(issue_number)


def finish(args):

    def print_usage_and_return():
        print "Usage: pipbot finish <issue|feature|release>"
        return 1

    if len(args) != 1:
        return print_usage_and_return()

    target = args[0]

    repo = Repo(projectdir)
    branch = repo.active_branch

    if not re.match("(release|feature)/" + target, branch.name):
        print "Target branch does not match current!"
        return 1

    if re.match("^[0-9]+\.[0-9]+\.[0-9]$", target):
        return finish_release(target)

    elif re.match("^[0-9]+$", target):
        return finish_issue(target)

    elif re.match("^[a-zA-Z0-9_]+$", target):
        return finish_feature(target)

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
                    projectdir + "configure.ac")
        match = re.match(r"^.*(?P<value>\d+).*$", line)
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

    os.chdir(projectdir)

    if len(argv) < 2:
        enter_repl_loop()
        ret = 0
    else:
        argv.pop(0)
        ret = process_batch_command(argv)

    exit(ret)
