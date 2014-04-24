#!/usr/bin/env python

from datetime import datetime
from re import match
from re import sub
from os import makedirs
from sys import argv
from sys import exit

def parse_lines(lines, outdir):
    try:
        makedirs(outdir)
    except:
        print "Failed to create directory '%s'. Aborting" % outdir
        exit(3)

    curr_month = ""
    curr_date = None
    buf = []
    line_lengths = []

    # Process line by line
    for l in lines:

        # Set a new month
        if match("^##\s+", l):
            curr_month = sub("^##\s+", "", l)
            continue

        # Set a new date
        if match("^###\s+", l):
            day = sub("^###\s+", "", l)

            # Abort if we don't have a month set
            if curr_month == "":
                print "Log entry '%s' appears before a month is set" % day
                exit(4)

            # Set next date
            try:
                date_str = sub("(th|nd|st|rd)$", "", day) + " " + curr_month
                next_date = datetime.strptime(date_str, "%A %d %B %Y").date()
            except ValueError as e:
                print e

            # Flush the current date buffer
            if curr_date != None:
                # Strip blank lines from end of buffer
                i = len(buf)
                for b in reversed(buf):
                    i -= 1
                    if b:
                        break
                    else:
                        del buf[i]

                line_lengths.append(len(buf))

                # Write buffer to file
                fname = curr_date.strftime('%Y-%m-%d')
                fo = open(outdir + "/" + fname + ".md", "w")
                for b in buf:
                    print>>fo, b
                fo.close()

                # Clear buffer
                buf = []

            # Set the new date
            curr_date = next_date
            continue

        # Build up current entry buffer
        if curr_date and (len(buf) > 0 or l):
            buf.append(l)

    print "Log length, {0}".format(len(lines))
    print "No of entries, {0}".format(len(line_lengths))
    print "Avg entry length, {0}".format(reduce(lambda x, y: x + y, line_lengths) / len(line_lengths))
    print "Min entry length, {0}".format(min(line_lengths))
    print "Max entry length, {0}".format(max(line_lengths))


if __name__ == "__main__":
    if len(argv) != 3:
        print "Usage: parselog.py <log> <outdir>"
        exit(3)

    log = argv[1]
    outdir = argv[2]

    try:
        with open(log) as f:
            parse_lines(f.read().splitlines(), outdir)
    except:
        print "Failed to read log '%s'. Aborting" % log
        exit(2)
