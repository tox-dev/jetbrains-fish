#!/usr/bin/env fish

# Configuration
set -gx EDITOR nvim
set -gx PATH $HOME/.local/bin $PATH

# Greeting function with colors
function greet --description "Display a colorful greeting"
    set -l name $argv[1]
    set -l time_of_day (date +%H)

    set_color --bold cyan
    echo "Welcome, $name!"
    set_color normal

    if test $time_of_day -lt 12
        set_color yellow
        echo "Good morning!"
    else if test $time_of_day -lt 18
        set_color green
        echo "Good afternoon!"
    else
        set_color magenta
        echo "Good evening!"
    end
    set_color normal
end

# Process files with error handling
function process_files --argument-names directory pattern
    if not test -d $directory
        set_color red
        echo "Error: Directory '$directory' not found" >&2
        set_color normal
        return 1
    end

    set -l count 0
    for file in $directory/$pattern
        if test -f $file
            set count (math $count + 1)
            echo "Processing: $file"
        end
    end

    echo "Processed $count files"
end

# Git helpers
function git_branch
    command git branch --show-current 2>/dev/null
end

function git_status_prompt
    set -l branch (git_branch)
    if test -n "$branch"
        set_color brblue
        echo -n "[$branch]"
        set_color normal
    end
end

# Switch statement example
function handle_command --argument-names cmd
    switch $cmd
        case start
            echo "Starting service..."
        case stop
            echo "Stopping service..."
        case restart
            echo "Restarting service..."
        case '*'
            echo "Unknown command: $cmd"
            return 1
    end
end

# Run main
greet (whoami)
