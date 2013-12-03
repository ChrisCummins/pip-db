#!/usr/bin/env python

import json
import subprocess
import re
import os
import sys
from sys import argv
from sys import exit

projectdir = "/home/chris/src/pip-db/"
prefixdir = "/home/chris/.local/"
etcdir = prefixdir + "etc/pipbot/"

def print_help():
	print "                  ,--.    ,--."
	print "                 ((O ))--((O ))"
	print "               ,'_`--'____`--'_`."
	print "              _:  ____________  :_"
	print "             | | ||::::::::::|| | |"
	print "             | | ||::::::::::|| | |"
	print "             | | ||::::::::::|| | |"
	print "             |_| |/__________\\| |_|"
	print "               |________________|"
	print "            __..-'            `-..__"
	print "         .-| : .----------------. : |-."
	print "       ,\\ || | |\\______________/| | || /."
	print "      /`.\\:| | ||  __  __  __  || | |;/,'\\"
	print "     :`-._\\;.| || '--''--''--' || |,:/_.-':"
	print "     |    :  | || .----------. || |  :    |"
	print "     |    |  | || '--pipbot--' || |  |    |"
	print "     |    |  | ||   _   _   _  || |  |    |"
	print "     :,--.;  | ||  (_) (_) (_) || |  :,--.;"
	print "     (`-'|)  | ||______________|| |  (|`-')"
	print "      `--'   | |/______________\\| |   `--'"
	print "             |____________________|"
	print "              `.________________,'"
	print "               (_______)(_______)"
	print "               (_______)(_______)"
	print "               (_______)(_______)"
	print "               (_______)(_______)"
	print "              |        ||        |"
	print "              '--------''--------'"
	print ""
	print "Hello there. My name is pipbot. These are some of the things I can do:"
	print ""
	print "    pipbot build <command ..>"
	print "        <target> <build> Build a website configuration"
	print "        summary          Show the current project configuration"
	print ""
	print "    pipbot deploy [<target> <build>]"
	print "        Deploy a build website configuration to <target>"
	print ""
	print "    pipbot undeploy [<target> <build>]"
	print "        Remove a deployed website configuration from <target>"
	print ""
	print "    pipbot show <issue-number|commit-id>"
	print "        Tell me more about a particular thing"
	print ""
	print "    pipbot version"
	print "        Show the current project version"
	print ""
	print "    pipbot issue <command ..>"
	print "        Issue tracker commands:"
	print "          list        List all issues"
	print "          show        Show an issue's details"
	print "          open        Open (or reopen) an issue"
	print "          close       Close an issue"
	print "          edit        Modify an existing issue"
	print "          comment     Leave a comment on an issue"
	print "          label       Create, list, modify, or delete labels"
	print "          assign      Assign an issue to yourself (or someone else)"
	print "          milestone   Manage project milestones"
	print ""
	print "    pipbot new <feature>"
	print "        Start work on a new feature branch"
	print ""
	print "    pipbot pause"
	print "        Pause work on the current feature branch"
	print ""
	print "    pipbot close"
	print "        Complete work on the current feature branch"
	print ""
	print "    pipbot release <start|finish> <version>"
	print "        Start or complete a project release"
	print ""
	print "    pipbot sloccount"
	print "        Show the number of source lines of code"
	print ""


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
	if len(args) != 1:
		"Usage: show <item>"
		return 1

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

def perform_action(action, cmd):
	sys.stdout.write(str(action) + "... ")
	sys.stdout.flush()
	run(cmd, False, False, False)
	print "[ok]"

def get_config_summary():
	file = open("config.summary", "r")
	return file.read()


def build_target(target_name, build_name):

	target_json = get_json_from_file(target_name, etcdir + "targets.json")
	build_json = get_json_from_file(build_name, etcdir + "build.json")

	if target_json == None:
		print "Couldn't find target configuration '" + target_name + "'"
		return 1

	if build_json == None:
		print "Couldn't find build configuration '" + build_name + "'"
		return 1

	configure_args = " ".join(build_json["configure"]["args"] +
							  target_json["configure"]["args"] +
							  build_json["configure"]["env"] +
							  target_json["configure"]["env"])

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
		perform_action("Deploying", "make install")
	except:
		return 2


def undeploy(args):

	# Support 'undeploy <target> <build>' syntax
	if len(args) == 2:
		build_target(args[0], args[1])

	try:
		perform_action("Undeploying", "make uninstall")
	except:
		return 2


def new(name):

	try:
		if is_int(name) == True:
			run("./tools/ghi show " + name, False)

		run("./tools/workflow new " + name, False)
		return 0
	except:
		return 2

def pause():

	try:
		run("./tools/workflow pause", False)
		return 0
	except:
		return 2

def close():

	try:
		run("./tools/workflow close", False)
		return 0
	except:
		return 2


def release(args):

	def bad_usage():
		print "Usage: release <start|finish> <version>"
		return 1

	if len(args) != 2 or not re.match("(start|finish)", args[0]):
		return bad_usage()

	if args[0] == "start":
		cmd = "./tools/mkrelease " + args[1]
	elif args[0] == "finish":
		cmd = "git flow release finish " + args[1]
	else:
		return bad_usage()

	try:
		run(cmd, False)
		return 0
	except:
		return 2


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
		print_help()
		return 0

	elif command == "show":
		return show(args)

	elif command == "build":
		return build(args)

	elif command == "deploy":
		return deploy(args)

	elif command == "undeploy":
		return undeploy(args)

	elif command == "version":
		print get_version_string()
		return 0

	elif command == "issue":
		return issue(args)

	elif command == "new":
		if len(args) != 1:
			print "Usage: pipbot new <feature>"
			return 1

		return new(args[0])

	elif command == "pause":
		return pause()

	elif command == "close":
		return close()

	elif command == "release":
		return release(args)

	elif command == "sloccount":
		return sloccount()

	else:
		print "I don't understand!"
		return 1


if __name__ == "__main__":

	if len(argv) > 1:
		command = str(argv[1]);
	else:
		print_help()
		exit(0)

	if len(argv) > 2:
		argv.pop(0)
		argv.pop(0)
		args = argv
	else:
		args = []

	os.chdir(projectdir)

	ret = process_command(command, args)
	exit(ret)
