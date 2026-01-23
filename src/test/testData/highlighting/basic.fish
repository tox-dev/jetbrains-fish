#!/usr/bin/env fish
# Basic Fish syntax highlighting test

# Variables
set greeting "Hello, World!"
set count 42
set list item1 item2 item3

# Functions
function say_hello --description 'Print a greeting'
    echo $greeting
    return 0
end

# Conditionals
if test $count -gt 10
    echo "Count is greater than 10"
else if test $count -eq 10
    echo "Count equals 10"
else
    echo "Count is less than 10"
end

# Switch
switch $count
    case 42
        echo "The answer!"
    case '*'
        echo "Something else"
end

# Loops
for i in (seq 1 5)
    echo "Number: $i"
end

while test $count -gt 0
    set count (math $count - 1)
end

# Command substitution
set result (math 1 + 2)
set files (ls *.fish)

# Pipes and redirections
cat file.txt | grep pattern > output.txt 2>&1
echo "test" >> append.txt

# Logical operators
test -f file.txt && echo "exists" || echo "not found"
true and echo "yes"
false or echo "no"

# Background jobs
long_task &

# Strings with escapes
echo "Tab:\t Newline:\n"
echo 'Single quoted: no $expansion'
echo "Double quoted: $greeting"

# Wildcards
ls *.fish
ls **/*.txt

# Home expansion
cd ~/projects

# Braces and brackets
set arr[1] value
echo $arr[1..3]

# Special variables
echo $status
echo $argv
echo $_
