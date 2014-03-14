#!/usr/bin/env bash
#
# Update the BLAST+ binaries located in bin/. This fetches the latest
# NCBI release for x64 linux systems.

echo "Checking latest BLAST+ release version..."

# Get the path of the latest release
ftp -n ftp.ncbi.nlm.nih.gov >index.tmp <<END_SCRIPT
quote USER anonymous
quote PASS
cd blast/executables/blast+/LATEST
ls
quit
END_SCRIPT

FILE=`grep "x64-linux.tar.gz" index.tmp | awk '{print $NF}'`
rm -f index.tmp

test -n "$FILE" || { echo "Failed to retrieve path!"; exit 1; }

if [ -f "$FILE" ]; then
    echo "Using cached file '$FILE'"
else
    echo "Downloading '$FILE'..."
    wget "ftp.ncbi.nlm.nih.gov/blast/executables/blast+/LATEST/$FILE"
fi

DIR=`echo "$FILE" | sed 's/-x64-linux\.tar\.gz//'`

echo "Installing BLAST+ binaries..."

if [ ! -d "$DIR" ]; then
    echo "Unpacking '$FILE'..." | indent
    tar zxvf "$FILE" | indent
fi

mv -v "$DIR/bin/blastp" "$DIR/bin/makeblastdb" bin/

if [ -z "$1" ]; then
    echo "Tidying up...";
    rm -rfv "$FILE" "$DIR";
fi
