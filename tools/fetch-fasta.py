#!/usr/bin/env python

import json
import re
import sys
import threading
import urllib

# URL regular expression
url_re = re.compile('http[s]?://(?:[a-zA-Z]|[0-9]|[$-_@.&+]|[!*\(\),]|(?:%[0-9a-fA-F][0-9a-fA-F]))+', re.IGNORECASE)

# UniProt sequence page expression
uniprot_sequence_re = re.compile('http://(www|ebi[0-9]).uniprot.org/uniprot/[A-Z0-9]{6}', re.IGNORECASE)

ncbi_url_re = re.compile('http://www.ncbi.nlm.nih.gov/protein/[^?]+')

# NCBI uidlist meta tag expression
ncbi_meta_re = re.compile('<meta name="ncbi_uidlist" content="([0-9]+)" />', re.IGNORECASE)

def warning(msg):
    sys.stderr.write("warning: " + str(msg) + '\n')

def print_result(url, name, data):
    print json.dumps({"url": url, "name": name, "data": data})

def line_to_urls(line):
    return url_re.findall(line)

def get_url_set(input_stream):
    url_list = []

    for line in sys.stdin:
        url_list = url_list + line_to_urls(line)

    return set(url_list)

def get_uniprot_sequence_url_set(url_set):
    return set(filter(uniprot_sequence_re.match, url_set))

def get_ncbi_url_set(url_set):
    return set(filter(ncbi_url_re.match, url_set))

def fetch(url):
    try:
        return urllib.urlopen(url)
    except IOError as e:
        print "error: IO({0}): {1}".format(e.errno, e.strerror)
    except Exception as e:
        print "error:", e

class Spider(threading.Thread):
    output_lock = threading.Lock()

    def __init__(self, url, fetch):
        threading.Thread.__init__(self)
        self.url = url
        self.fetch = fetch

    def str2fasta(self, string):
        lines = string.split("\n")
        name = lines[0]
        data = "\n".join(lines[1:]).replace("\n", "")

        if not name.startswith(">"):
            warning("sequence '" + self.url + "' name does not begin with '>'")

        return {"name": name, "data": data}

    def run(self):
        fasta = self.str2fasta(self.fetch(self.url).strip())

        if len(fasta):
            with self.output_lock:
                print_result(self.url, fasta["name"], fasta["data"])

def fetch_fasta_uniprot(url):
    return fetch(url + '.fasta').read()

def fetch_fasta_ncbi(url):
    def fetch_ncbi_uid(url):
        for line in fetch(url):
            m = ncbi_meta_re.search(line)
            if m:
                return m.group(1)

    def ncbi_uid_to_fasta_url(uid):
        return "http://www.ncbi.nlm.nih.gov/sviewer/viewer.cgi?tool=portal&sendto=on&log$=seqview&db=protein&dopt=fasta&sort=&val={0}&from=begin&to=end".format(uid)

    uid = fetch_ncbi_uid(url)

    if uid:
        return fetch(ncbi_uid_to_fasta_url(uid)).read()
    else:
        warning("unable to retrieve NCBI UID for '" + url + "'. Ignoring.")

def run():
    # Get the full list of URLs
    urls = get_url_set(sys.stdin)

    # Get the UniProt URLs
    uniprot_urls = get_uniprot_sequence_url_set(urls)
    urls = urls - uniprot_urls

    # Get the NCBI URLs
    ncbi_urls = get_ncbi_url_set(urls)
    urls = urls - ncbi_urls

    # Warn the user about ignored threads
    for url in urls:
        warning("cannot process URL '" + url + "'. Ignoring.")

    # Spawn worker threads
    threads = []

    for url in uniprot_urls:
         spider = Spider(url, fetch_fasta_uniprot)
         spider.start()
         threads.append(spider)

    for url in ncbi_urls:
         spider = Spider(url, fetch_fasta_ncbi)
         spider.start()
         threads.append(spider)

if __name__ == "__main__":
    try:
        run()
    except Exception as e:
        print "error:", e
