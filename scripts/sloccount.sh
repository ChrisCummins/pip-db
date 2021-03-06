#!/usr/bin/env bash
### sloccount.sh - tool to count source code lines in pip-db
#
# Copyright 2014 Chris Cummins.
#
# This file is part of pip-db.
#
# pip-db is free software: you can redistribute it and/or modify it
# under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.
#
# pip-db is distributed in the hope that it will be useful, but
# WITHOUT ANY WARRANTY; without even the implied warranty of
# MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
# General Public License for more details.
#
# You should have received a copy of the GNU General Public License
# along with pip-db.  If not, see <http://www.gnu.org/licenses/>.
#

# Lookup the root directory for the project. If unable to locate root, exit
# script.
#
#     @return The absolute path to the project root directory
get_project_root() {
  while [[ "$(pwd)" != "/" ]]; do
    if test -f configure.ac; then
      pwd
      return
    fi
    cd ..
  done

  echo "fatal: Unable to locate project base directory." >&2
  exit 3
}

# Lookup the package string as exported by Autoconf in the toplevel Makefile.
#
#     @return A package string, in the form: <name> <version>
get_package_string() {
  grep '^PACKAGE_STRING' "$(get_project_root)"/Makefile | sed 's/^PACKAGE_STRING *= *//'
}

# Returns a list of paths to files which match the pattern *.$1, relative to the
# project root directory. This excludes files from the build/www directory.
#
#     $1 The file extension to match
#     $2 (optional) The subdirectory to look in, defaults to the project root
#     $3 (optional) The maxdepth for file searching
find_files_with_extension() {
  local ext="$1"
  local subdir="/$2"
  local maxdepth=$3

  if [[ -n $maxdepth ]]; then
    local args="-maxdepth $maxdepth"
  fi

  cd "$(get_project_root)"

  find ".$subdir" "$args" -type f -name '*.'"$ext" 2>/dev/null | grep -v '/resources/public/' | sort
}

# Returns the line counts for a list of files.
#
#     $1 A space-separated list of file paths
get_lc_of_files() {
  local files="$1"
  local total=0

  cd "$(get_project_root)"

  for f in $files; do
    sloccount=$(wc -l "$f" | awk '{ print $1 }')

    total=$((total+sloccount))
  done

  echo $total
}

# Calculate the percentage of two values.
#
#     $1 Value
#     $2 Total
get_perc() {
  if (( $2 > 0 )); then
    echo "$1 / $2 * 100" | bc -l | xargs printf "%.2f"
  else
    echo "0.00"
  fi
}

# Print a formatted sloccount result for a file or set of files.
#
#     $1 The linecount
#     $2 The total linecount of the group
#     $3 The name of the file or set of files
print_sloccount() {
  local linecount=$1
  local total_linecount=$2
  local file="$3"

  local percentage=$(get_perc "$linecount" "$total_linecount")

  if (( linecount > 0 )); then
    echo -e "$(printf %6d "$linecount")\t$file\t($percentage%)"
  fi
}

# Returns a list of sloccounts for the build system.
get_build_sloccounts() {
  local autogen=$(get_lc_of_files "./autogen.sh")
  local configure=$(get_lc_of_files "./configure.ac")
  local makefile=$(get_lc_of_files "$(find_files_with_extension am)")
  local build=$(get_lc_of_files "./bin/build")
  local total=$((autogen+configure+makefile))

  print_sloccount "$autogen"   "$total" "autogen.sh       "
  print_sloccount "$configure" "$total" "configure.ac     "
  print_sloccount "$makefile"  "$total" "Makefile.am      "
  print_sloccount "$build"     "$total" "bin/build        "
}

# Returns a list of sloccounts for the resources.
get_resources_sloccounts() {
  local js=$(get_lc_of_files "$(find_files_with_extension js resources)")
  local less=$(get_lc_of_files "$(find_files_with_extension less resources)")
  local total=$((js+less))

  print_sloccount "$js"        "$total" "JavaScript       "
  print_sloccount "$less"      "$total" "Less CSS         "
}

# Returns a list of sloccounts for the sources.
get_src_sloccounts() {
  local src=$(get_lc_of_files "$(find_files_with_extension clj src)")
  local test=$(get_lc_of_files "$(find_files_with_extension clj test)")
  local total=$((src+test))

  print_sloccount "$src"       "$total" "Clojure          "
  print_sloccount "$test"      "$total" "Clojure (tests)  "
}

# Returns a list of sloccounts for the Documentation.
get_doc_sloccounts() {
  local latex=$(get_lc_of_files "$(find_files_with_extension tex)")
  local md=$(get_lc_of_files "$(find_files_with_extension md | grep -v node_modules)")
  local total=$((latex+md))

  print_sloccount "$latex"     "$total" "LaTeX            "
  print_sloccount "$md"        "$total" "Markdown         "
}

# Returns a list of sloccounts for the tools.
get_tools_sloccounts() {
  local sh=$(get_lc_of_files "$(find_files_with_extension sh tools/)")
  local js=$(get_lc_of_files "$(find_files_with_extension js tools/)")
  local py=$(get_lc_of_files "$(find_files_with_extension py tools/)")
  local rb=$(get_lc_of_files "$(find_files_with_extension rb tools/)")
  local total=$((sh+js+py+rb))

  print_sloccount "$sh"        "$total" "Shell            "
  print_sloccount "$js"        "$total" "JavaScript       "
  print_sloccount "$py"        "$total" "Python           "
  print_sloccount "$rb"        "$total" "Ruby             "
}

# Returns a list of sloccounts for the scripts.
get_scripts_sloccounts() {
    local sh=$(get_lc_of_files "$(find_files_with_extension sh scripts/)")
    local js=$(get_lc_of_files "$(find_files_with_extension js scripts/)")
    local py=$(get_lc_of_files "$(find_files_with_extension py scripts/)")
    local rb=$(get_lc_of_files "$(find_files_with_extension rb scripts/)")
    local total=$((sh+js+py+rb))

    print_sloccount "$sh"        "$total" "Shell            "
    print_sloccount "$js"        "$total" "JavaScript       "
    print_sloccount "$py"        "$total" "Python           "
    print_sloccount "$rb"        "$total" "Ruby             "
}

# Returns the sum of a list of integers
#
#     $1 A list of integers, one per line
sum_rows() {
  echo "$1" | awk '{s+=$1} END {print s}'
}

main() {
  local build=$(sum_rows "$(get_build_sloccounts)")
  local resources=$(sum_rows "$(get_resources_sloccounts)")
  local src=$(sum_rows "$(get_src_sloccounts)")
  local docs=$(sum_rows "$(get_doc_sloccounts)")
  local tools=$(sum_rows "$(get_tools_sloccounts)")
  local scripts=$(sum_rows "$(get_scripts_sloccounts)")
  local total=$((build+resources+src+docs+tools+scripts))

  echo -n 'Commit: '; git show HEAD | head -n1 | awk '{print $2}'
  git show HEAD | grep --color=never '^Date:'
  echo -n 'Rel:    '; echo "$(get_package_string)"

  echo ""
  echo "Build system: $build"
  get_build_sloccounts | sort -rn | column -t -s $'\t'

  echo ""
  echo "Client side: $resources"
  get_resources_sloccounts | sort -rn | column -t -s $'\t'

  echo ""
  echo "Server side: $src"
  get_src_sloccounts | sort -rn | column -t -s $'\t'

  echo ""
  echo "Documentation: $docs"
  get_doc_sloccounts | sort -rn | column -t -s $'\t'

  echo ""
  echo "Tools: $tools"
  get_tools_sloccounts | sort -rn | column -t -s $'\t'

  echo ""
  echo "Scripts: $scripts"
  get_scripts_sloccounts | sort -rn | column -t -s $'\t'

  echo ""
  echo "Total physical source lines of code: $total"
}
main $@
