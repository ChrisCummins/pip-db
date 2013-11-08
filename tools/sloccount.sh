#!/bin/bash

# Lookup the root directory for the project. If unable to locate root, exit
# script.
#
#     @return The absolute path to the project root directory
get_project_root() {
	local root=""

	while [[ "$(pwd)" != "/" ]]; do
		if test -f configure.ac; then
			echo "$(pwd)"
			return
		fi
		cd ..
	done

	echo "fatal: Unable to locate project base directory." >&2
	exit 3
}

# Returns a list of paths to files which match the pattern *.$1, relative to the
# project root directory. This excludes files from the build/www directory.
#
#     $1 The file extension to match
#     $2 (optional) The subdirectory to look in, defaults to the project root
find_files_with_extension() {
	local ext="$1"
	local subdir="/$2"

	cd "$(get_project_root)"

	find ".$subdir" -type f -name '*.'"$ext" | grep -v '/build/www/' | sort
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

		total=$((total+$sloccount))
	done

	echo $total
}

# Calculate the percentage of two values.
#
#     $1 Value
#     $2 Total
get_perc() {
	if [[ $2 > 0 ]]; then
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

	local percentage=$(get_perc $linecount $total_linecount)

	if [[ $linecount > 0 ]]; then
		echo "$linecount $file ($percentage%)"
	fi
}

# Returns a list of sloccounts for the build system.
get_build_sloccounts() {
	local autogen=$(get_lc_of_files "./autogen.sh")
	local configure=$(get_lc_of_files "./configure.ac")
	local makefile=$(get_lc_of_files "$(find_files_with_extension am)")
	local total=$((autogen+$configure+$makefile))

	print_sloccount $autogen $total "autogen.sh"
	print_sloccount $configure $total "configure.ac"
	print_sloccount $makefile $total "Makefile.am"
}

# Returns a list of sloccounts for the website source code.
get_www_sloccounts() {
	local html=$(get_lc_of_files "$(find_files_with_extension html www/)")
	local php=$(get_lc_of_files "$(find_files_with_extension php www/)")
	local css=$(get_lc_of_files "$(find_files_with_extension css www/)")
	local less=$(get_lc_of_files "$(find_files_with_extension less www/)")
	local js=$(get_lc_of_files "$(find_files_with_extension js www/)")
	local total=$((html+$php+$css+$less+$js))

	print_sloccount $html  $total "HTML"
	print_sloccount $php   $total "PHP"
	print_sloccount $css   $total "CSS"
	print_sloccount $less  $total "Less"
	print_sloccount $js    $total "JavaScript"
}

# Returns a list of sloccounts for the Documentation.
get_doc_sloccounts() {
	local latex=$(get_lc_of_files "$(find_files_with_extension tex)")
	local md=$(get_lc_of_files "$(find_files_with_extension md)")
	local total=$((latex+$md))

	print_sloccount $latex $total "LaTeX"
	print_sloccount $md    $total "Markdown"
}

# Returns a list of sloccounts for the tools.
get_tools_sloccounts() {
	local sh=$(get_lc_of_files "$(find_files_with_extension sh tools/)")
	local js=$(get_lc_of_files "$(find_files_with_extension js tools/)")
	local py=$(get_lc_of_files "$(find_files_with_extension py tools/)")
	local total=$((sh+$js+$py))

	print_sloccount $sh    $total "Shell"
	print_sloccount $js    $total "JavaScript"
	print_sloccount $py    $total "Python"
}

main() {
	echo ""
	echo "Build system:"
	get_build_sloccounts | sort -rn | column -t

	echo ""
	echo "Web sources:"
	get_www_sloccounts | sort -rn | column -t

	echo ""
	echo "Documentation:"
	get_doc_sloccounts | sort -rn | column -t

	echo ""
	echo "Tools:"
	get_tools_sloccounts | sort -rn | column -t
}
main $@
