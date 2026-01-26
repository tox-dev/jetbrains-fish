package com.github.toxdev.fish.documentation

@Suppress("ktlint:standard:max-line-length")
object FishDocumentation {
    private const val DOLLAR = "$"

    val COMMAND_DOCS: Map<String, String> =
        mapOf(
            "if" to
                """
            <div class='definition'><pre><b>if</b> - conditionally execute a command</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>if CONDITION; COMMANDS_TRUE ...;
[else if CONDITION2; COMMANDS_TRUE2 ...;]
[else; COMMANDS_FALSE ...;]
end</pre>
            <p><b>Description</b></p>
            <p><code>if</code> will execute the command <code>CONDITION</code>. If the condition's exit status is 0,
            the commands <code>COMMANDS_TRUE</code> will execute. If the exit status is not 0 and <code>else</code>
            is given, <code>COMMANDS_FALSE</code> will be executed.</p>
            <p>You can use <code>and</code> or <code>or</code> in the condition.</p>
            </div>
                """.trimIndent(),
            "else" to
                """
                <div class='definition'><pre><b>else</b> - execute command if a condition is not met</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>if CONDITION; COMMANDS_TRUE ...; [else; COMMANDS_FALSE ...;] end</pre>
                <p><b>Description</b></p>
                <p><code>else</code> is used as part of an <code>if</code> block to execute commands when the condition is false.</p>
                </div>
                """.trimIndent(),
            "for" to
                """
                <div class='definition'><pre><b>for</b> - perform a set of commands multiple times</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>for VARNAME in [VALUES ...]; COMMANDS ...; end</pre>
                <p><b>Description</b></p>
                <p><code>for</code> is a loop construct. It will perform the commands specified by <code>COMMANDS</code>
                multiple times. On each iteration, the local variable specified by <code>VARNAME</code> is assigned
                a new value from <code>VALUES</code>.</p>
                <p><b>Example</b></p>
                <pre>for i in foo bar baz; echo ${DOLLAR}i; end</pre>
                </div>
                """.trimIndent(),
            "while" to
                """
            <div class='definition'><pre><b>while</b> - perform a set of commands while a condition is true</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>while CONDITION; COMMANDS ...; end</pre>
            <p><b>Description</b></p>
            <p><code>while</code> repeatedly executes <code>COMMANDS</code> as long as <code>CONDITION</code>
            has an exit status of 0. The loop can be exited with <code>break</code>.</p>
            <p><b>Example</b></p>
            <pre>while test ${DOLLAR}x -lt 10
    echo ${DOLLAR}x
    set x (math ${DOLLAR}x + 1)
end</pre>
            </div>
                """.trimIndent(),
            "switch" to
                """
            <div class='definition'><pre><b>switch</b> - conditionally execute a block of commands</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>switch VALUE; [case [GLOB ...]; [COMMANDS ...]; ...] end</pre>
            <p><b>Description</b></p>
            <p><code>switch</code> compares <code>VALUE</code> against each <code>case</code> glob pattern.
            When a match is found, the corresponding commands are executed.</p>
            <p><b>Example</b></p>
            <pre>switch ${DOLLAR}animal
    case cat
        echo "meow"
    case dog
        echo "woof"
    case '*'
        echo "unknown"
end</pre>
            </div>
                """.trimIndent(),
            "case" to
                """
                <div class='definition'><pre><b>case</b> - conditionally execute a block of commands</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>switch VALUE; [case [GLOB ...]; [COMMANDS ...]; ...] end</pre>
                <p><b>Description</b></p>
                <p><code>case</code> is used inside a <code>switch</code> block. When <code>VALUE</code> matches
                the <code>GLOB</code> pattern, the corresponding commands are executed.</p>
                </div>
                """.trimIndent(),
            "begin" to
                """
            <div class='definition'><pre><b>begin</b> - start a new block of commands</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>begin; [COMMANDS ...;] end</pre>
            <p><b>Description</b></p>
            <p><code>begin</code> is used to create a new block of commands. This is useful for grouping
            commands together for redirection or to create a new variable scope with <code>set -l</code>.</p>
            <p><b>Example</b></p>
            <pre>begin
    set -l tmpfile (mktemp)
    # use tmpfile
end  # tmpfile goes out of scope</pre>
            </div>
                """.trimIndent(),
            "end" to
                """
                <div class='definition'><pre><b>end</b> - end a block of commands</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p><code>end</code> ends a block started by <code>if</code>, <code>for</code>, <code>while</code>,
                <code>switch</code>, <code>begin</code>, or <code>function</code>.</p>
                </div>
                """.trimIndent(),
            "function" to
                """
            <div class='definition'><pre><b>function</b> - create a function</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>function NAME [OPTIONS]; BODY; end</pre>
            <p><b>Description</b></p>
            <p><code>function</code> creates a new function <code>NAME</code> with the body <code>BODY</code>.</p>
            <p><b>Options</b></p>
            <ul>
            <li><code>-a NAMES</code> or <code>--argument-names NAMES</code> - Assigns command-line arguments to named variables</li>
            <li><code>-d DESCRIPTION</code> or <code>--description DESCRIPTION</code> - A description of the function</li>
            <li><code>-w COMMAND</code> or <code>--wraps COMMAND</code> - Inherit completions from another command</li>
            <li><code>-e EVENT</code> or <code>--on-event EVENT</code> - Run when the named event is emitted</li>
            <li><code>-v VAR</code> or <code>--on-variable VAR</code> - Run when the variable changes</li>
            </ul>
            <p><b>Example</b></p>
            <pre>function greet -a name
    echo "Hello, ${DOLLAR}name!"
end</pre>
            </div>
                """.trimIndent(),
            "return" to
                """
                <div class='definition'><pre><b>return</b> - stop the current function</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>return [N]</pre>
                <p><b>Description</b></p>
                <p><code>return</code> halts the current function and returns control to the caller.
                If <code>N</code> is specified, it becomes the exit status of the function.</p>
                </div>
                """.trimIndent(),
            "break" to
                """
                <div class='definition'><pre><b>break</b> - stop the current loop</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>break</pre>
                <p><b>Description</b></p>
                <p><code>break</code> halts the innermost currently executing <code>for</code> or <code>while</code> loop.</p>
                </div>
                """.trimIndent(),
            "continue" to
                """
                <div class='definition'><pre><b>continue</b> - skip the current iteration of the loop</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>continue</pre>
                <p><b>Description</b></p>
                <p><code>continue</code> skips the remainder of the current iteration of the innermost loop
                and jumps to the next iteration.</p>
                </div>
                """.trimIndent(),
            "echo" to
                """
            <div class='definition'><pre><b>echo</b> - display a line of text</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>echo [OPTIONS] [STRING]</pre>
            <p><b>Description</b></p>
            <p><code>echo</code> displays <code>STRING</code> of text.</p>
            <p><b>Options</b></p>
            <ul>
            <li><code>-n</code> - Do not output a newline</li>
            <li><code>-s</code> - Do not separate arguments with spaces</li>
            <li><code>-E</code> - Disable interpretation of backslash escapes (default)</li>
            <li><code>-e</code> - Enable interpretation of backslash escapes</li>
            </ul>
            <p><b>Example</b></p>
            <pre>echo 'Hello World'
echo -e 'Top\nBottom'</pre>
            </div>
                """.trimIndent(),
            "set" to
                """
            <div class='definition'><pre><b>set</b> - display and change shell variables</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>set [OPTIONS] NAME [VALUE ...]</pre>
            <p><b>Description</b></p>
            <p><code>set</code> manipulates shell variables. If both <code>NAME</code> and <code>VALUE</code>
            are provided, <code>set</code> assigns the values to the variable.</p>
            <p><b>Scope Options</b></p>
            <ul>
            <li><code>-U</code> or <code>--universal</code> - Universal variable (persists across sessions)</li>
            <li><code>-g</code> or <code>--global</code> - Global variable</li>
            <li><code>-l</code> or <code>--local</code> - Local variable (block-scoped)</li>
            <li><code>-f</code> or <code>--function</code> - Function-scoped variable</li>
            </ul>
            <p><b>Other Options</b></p>
            <ul>
            <li><code>-x</code> or <code>--export</code> - Export variable to child processes</li>
            <li><code>-e</code> or <code>--erase</code> - Erase the variable</li>
            <li><code>-q</code> or <code>--query</code> - Test if variable exists</li>
            <li><code>-a</code> or <code>--append</code> - Append values</li>
            <li><code>-p</code> or <code>--prepend</code> - Prepend values</li>
            </ul>
            <p><b>Example</b></p>
            <pre>set -l name "World"
set -gx PATH /usr/local/bin ${DOLLAR}PATH</pre>
            </div>
                """.trimIndent(),
            "read" to
                """
                <div class='definition'><pre><b>read</b> - read a line of input into variables</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>read [OPTIONS] [VARIABLE ...]</pre>
                <p><b>Description</b></p>
                <p><code>read</code> reads from standard input and stores the result in shell variables.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-l</code> or <code>--local</code> - Make variables local</li>
                <li><code>-g</code> or <code>--global</code> - Make variables global</li>
                <li><code>-p PROMPT</code> or <code>--prompt PROMPT</code> - Display prompt</li>
                <li><code>-P MESSAGE</code> or <code>--prompt-str MESSAGE</code> - Use string as prompt</li>
                <li><code>-n NCHARS</code> or <code>--nchars NCHARS</code> - Read only N characters</li>
                <li><code>-s</code> or <code>--silent</code> - Suppress echoing (for passwords)</li>
                </ul>
                </div>
                """.trimIndent(),
            "test" to
                """
            <div class='definition'><pre><b>test</b> - perform tests on files and text</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>test [EXPRESSION]
[ EXPRESSION ]</pre>
            <p><b>Description</b></p>
            <p><code>test</code> evaluates an expression and returns 0 if true, 1 if false.</p>
            <p><b>File Tests</b></p>
            <ul>
            <li><code>-e FILE</code> - File exists</li>
            <li><code>-f FILE</code> - Is a regular file</li>
            <li><code>-d FILE</code> - Is a directory</li>
            <li><code>-r FILE</code> - Is readable</li>
            <li><code>-w FILE</code> - Is writable</li>
            <li><code>-x FILE</code> - Is executable</li>
            </ul>
            <p><b>String Tests</b></p>
            <ul>
            <li><code>-z STRING</code> - String is empty</li>
            <li><code>-n STRING</code> - String is not empty</li>
            <li><code>STRING1 = STRING2</code> - Strings are equal</li>
            <li><code>STRING1 != STRING2</code> - Strings are not equal</li>
            </ul>
            <p><b>Number Tests</b></p>
            <ul>
            <li><code>NUM1 -eq NUM2</code> - Equal</li>
            <li><code>NUM1 -ne NUM2</code> - Not equal</li>
            <li><code>NUM1 -lt NUM2</code> - Less than</li>
            <li><code>NUM1 -gt NUM2</code> - Greater than</li>
            </ul>
            </div>
                """.trimIndent(),
            "string" to
                """
                <div class='definition'><pre><b>string</b> - manipulate strings</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string SUBCOMMAND [OPTIONS] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p><code>string</code> performs operations on strings. If STRING is not given, reads from stdin.</p>
                <p><b>Subcommands</b></p>
                <ul>
                <li><code>string collect</code> - Collect lines into a single string</li>
                <li><code>string escape</code> - Escape special characters</li>
                <li><code>string join</code> - Join strings with delimiter</li>
                <li><code>string join0</code> - Join strings with NUL delimiter</li>
                <li><code>string length</code> - Get string length</li>
                <li><code>string lower</code> - Convert to lowercase</li>
                <li><code>string match</code> - Match against pattern</li>
                <li><code>string pad</code> - Pad strings to a given width</li>
                <li><code>string repeat</code> - Repeat strings</li>
                <li><code>string replace</code> - Replace substrings</li>
                <li><code>string shorten</code> - Shorten strings to a given width</li>
                <li><code>string split</code> - Split string on delimiter</li>
                <li><code>string split0</code> - Split string on NUL</li>
                <li><code>string sub</code> - Extract substring</li>
                <li><code>string trim</code> - Remove whitespace</li>
                <li><code>string unescape</code> - Unescape special characters</li>
                <li><code>string upper</code> - Convert to uppercase</li>
                </ul>
                </div>
                """.trimIndent(),
            "string length" to
                """
                <div class='definition'><pre><b>string length</b> - print string lengths</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string length [-q | --quiet] [-V | --visible] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Reports the length of each string argument in characters.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-q</code> or <code>--quiet</code> - Do not print lengths, only return status</li>
                <li><code>-V</code> or <code>--visible</code> - Count visible width (accounts for escape sequences)</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string length "hello"  # outputs: 5
string length -q "" &amp;&amp; echo "not empty"</pre>
                </div>
                """.trimIndent(),
            "string sub" to
                """
                <div class='definition'><pre><b>string sub</b> - extract substrings</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string sub [-s START] [-e END] [-l LENGTH] [-q] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Prints a substring of each string argument. Indices are 1-based and can be negative (counting from end).</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-s START</code> or <code>--start START</code> - Start index (default: 1)</li>
                <li><code>-e END</code> or <code>--end END</code> - End index (inclusive)</li>
                <li><code>-l LENGTH</code> or <code>--length LENGTH</code> - Number of characters</li>
                <li><code>-q</code> or <code>--quiet</code> - Do not print, only return status</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string sub -s 2 -l 3 "hello"  # outputs: ell
string sub -s -2 "hello"  # outputs: lo</pre>
                </div>
                """.trimIndent(),
            "string split" to
                """
                <div class='definition'><pre><b>string split</b> - split strings by delimiter</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string split [-m MAX] [-n] [-q] [-r] [-f FIELDS] SEP [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Splits each STRING on the separator SEP and prints each substring on a new line.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-m MAX</code> or <code>--max MAX</code> - Maximum number of splits</li>
                <li><code>-n</code> or <code>--no-empty</code> - Exclude empty strings from result</li>
                <li><code>-r</code> or <code>--right</code> - Split from right to left</li>
                <li><code>-f FIELDS</code> or <code>--fields FIELDS</code> - Print only specified fields (1-based, comma-separated)</li>
                <li><code>-q</code> or <code>--quiet</code> - Do not print, only return status</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string split , "a,b,c"  # outputs: a\nb\nc
string split -f1,3 , "a,b,c"  # outputs: a\nc</pre>
                </div>
                """.trimIndent(),
            "string split0" to
                """
                <div class='definition'><pre><b>string split0</b> - split on NUL character</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string split0 [-m MAX] [-n] [-q] [-r] [-f FIELDS] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Splits each STRING on NUL character. Useful for processing output from <code>find -print0</code>.</p>
                </div>
                """.trimIndent(),
            "string join" to
                """
                <div class='definition'><pre><b>string join</b> - join strings with delimiter</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string join [-q | --quiet] [-n | --no-empty] SEP [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Joins the STRING arguments into one string separated by SEP.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-n</code> or <code>--no-empty</code> - Exclude empty strings</li>
                <li><code>-q</code> or <code>--quiet</code> - Do not print, only return status</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string join , a b c  # outputs: a,b,c
string join \n one two  # outputs: one\ntwo</pre>
                </div>
                """.trimIndent(),
            "string join0" to
                """
                <div class='definition'><pre><b>string join0</b> - join with NUL character</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string join0 [-q] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Joins the STRING arguments with NUL character. Useful for piping to <code>xargs -0</code>.</p>
                </div>
                """.trimIndent(),
            "string trim" to
                """
                <div class='definition'><pre><b>string trim</b> - remove leading/trailing whitespace</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string trim [-l] [-r] [-c CHARS] [-q] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Removes leading and trailing whitespace from each STRING.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-l</code> or <code>--left</code> - Trim only leading characters</li>
                <li><code>-r</code> or <code>--right</code> - Trim only trailing characters</li>
                <li><code>-c CHARS</code> or <code>--chars CHARS</code> - Characters to trim (default: whitespace)</li>
                <li><code>-q</code> or <code>--quiet</code> - Do not print, only return status</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string trim "  hello  "  # outputs: hello
string trim -c "xy" "xxyhelloyx"  # outputs: hello</pre>
                </div>
                """.trimIndent(),
            "string match" to
                """
                <div class='definition'><pre><b>string match</b> - match strings against pattern</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string match [-a] [-e] [-i] [-g] [-n] [-q] [-r] [-v] PATTERN [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Tests each STRING against PATTERN and prints matching strings or groups.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-a</code> or <code>--all</code> - Report all matches (with -r)</li>
                <li><code>-e</code> or <code>--entire</code> - Print entire matching string</li>
                <li><code>-g</code> or <code>--groups-only</code> - Only print captured groups</li>
                <li><code>-i</code> or <code>--ignore-case</code> - Case-insensitive matching</li>
                <li><code>-n</code> or <code>--index</code> - Print indices instead of matches</li>
                <li><code>-r</code> or <code>--regex</code> - Use regular expression (PCRE2)</li>
                <li><code>-v</code> or <code>--invert</code> - Print non-matching strings</li>
                <li><code>-q</code> or <code>--quiet</code> - Do not print, only return status</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string match "*.txt" file.txt  # glob match
string match -r "(\d+)" "age: 42"  # regex capture</pre>
                </div>
                """.trimIndent(),
            "string replace" to
                """
                <div class='definition'><pre><b>string replace</b> - replace substrings</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string replace [-a] [-f] [-i] [-q] [-r] PATTERN REPLACEMENT [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Replaces the first occurrence of PATTERN with REPLACEMENT in each STRING.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-a</code> or <code>--all</code> - Replace all occurrences</li>
                <li><code>-f</code> or <code>--filter</code> - Only print strings with replacements</li>
                <li><code>-i</code> or <code>--ignore-case</code> - Case-insensitive matching</li>
                <li><code>-r</code> or <code>--regex</code> - Use regular expression (PCRE2)</li>
                <li><code>-q</code> or <code>--quiet</code> - Do not print, only return status</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string replace foo bar "foobar"  # outputs: barbar
string replace -ra "[0-9]" X "a1b2"  # outputs: aXbX</pre>
                </div>
                """.trimIndent(),
            "string upper" to
                """
                <div class='definition'><pre><b>string upper</b> - convert to uppercase</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string upper [-q] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Outputs each STRING converted to uppercase.</p>
                <p><b>Example</b></p>
                <pre>string upper hello  # outputs: HELLO</pre>
                </div>
                """.trimIndent(),
            "string lower" to
                """
                <div class='definition'><pre><b>string lower</b> - convert to lowercase</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string lower [-q] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Outputs each STRING converted to lowercase.</p>
                <p><b>Example</b></p>
                <pre>string lower HELLO  # outputs: hello</pre>
                </div>
                """.trimIndent(),
            "string escape" to
                """
                <div class='definition'><pre><b>string escape</b> - escape special characters</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string escape [-n] [-q] [--style=STYLE] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Escapes each STRING for safe use as an argument or variable value.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-n</code> or <code>--no-quoted</code> - Escape without enclosing quotes</li>
                <li><code>--style=STYLE</code> - Style: script (default), var, url, regex</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string escape "hello world"  # outputs: 'hello world'
string escape --style=url "a b"  # outputs: a%20b</pre>
                </div>
                """.trimIndent(),
            "string unescape" to
                """
                <div class='definition'><pre><b>string unescape</b> - expand escape sequences</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string unescape [-n] [-q] [--style=STYLE] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Expands escape sequences in each STRING. Reverse of <code>string escape</code>.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>--style=STYLE</code> - Style: script (default), var, url, regex</li>
                </ul>
                </div>
                """.trimIndent(),
            "string collect" to
                """
                <div class='definition'><pre><b>string collect</b> - join input into single string</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string collect [-a | --allow-empty] [-N | --no-trim-newlines] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Collects its input into a single output argument, preserving newlines.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-a</code> or <code>--allow-empty</code> - Output empty string if input is empty</li>
                <li><code>-N</code> or <code>--no-trim-newlines</code> - Don't trim trailing newlines</li>
                </ul>
                <p><b>Example</b></p>
                <pre>echo -e "one\ntwo" | string collect  # single string with newline</pre>
                </div>
                """.trimIndent(),
            "string repeat" to
                """
                <div class='definition'><pre><b>string repeat</b> - repeat strings</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string repeat [-n COUNT] [-m MAX] [-N] [-q] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Repeats each STRING the specified number of times.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-n COUNT</code> or <code>--count COUNT</code> - Number of repetitions</li>
                <li><code>-m MAX</code> or <code>--max MAX</code> - Maximum output length</li>
                <li><code>-N</code> or <code>--no-newline</code> - Don't output newline</li>
                <li><code>-q</code> or <code>--quiet</code> - Do not print, only return status</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string repeat -n 3 "ab"  # outputs: ababab
string repeat -n 10 -m 5 "x"  # outputs: xxxxx</pre>
                </div>
                """.trimIndent(),
            "string pad" to
                """
                <div class='definition'><pre><b>string pad</b> - pad strings to given width</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string pad [-r] [-c CHAR] [-w WIDTH] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Pads each STRING to WIDTH characters.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-r</code> or <code>--right</code> - Pad on the right (default: left)</li>
                <li><code>-c CHAR</code> or <code>--char CHAR</code> - Padding character (default: space)</li>
                <li><code>-w WIDTH</code> or <code>--width WIDTH</code> - Minimum width</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string pad -w 5 42  # outputs: "   42"
string pad -r -c 0 -w 4 1  # outputs: "1000"</pre>
                </div>
                """.trimIndent(),
            "string shorten" to
                """
                <div class='definition'><pre><b>string shorten</b> - shorten strings to given width</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>string shorten [-c CHARS] [-l] [-m MAX] [-N] [-q] [STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Shortens each STRING to MAX visible characters, adding an ellipsis if truncated.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-c CHARS</code> or <code>--char CHARS</code> - Ellipsis character(s) (default: …)</li>
                <li><code>-l</code> or <code>--left</code> - Shorten from the left</li>
                <li><code>-m MAX</code> or <code>--max MAX</code> - Maximum visible width</li>
                <li><code>-N</code> or <code>--no-newline</code> - Don't output newline</li>
                <li><code>-q</code> or <code>--quiet</code> - Do not print, only return status</li>
                </ul>
                <p><b>Example</b></p>
                <pre>string shorten -m 10 "hello world"  # outputs: hello wor…</pre>
                </div>
                """.trimIndent(),
            "cd" to
                """
                <div class='definition'><pre><b>cd</b> - change directory</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>cd [DIR]</pre>
                <p><b>Description</b></p>
                <p><code>cd</code> changes the current working directory. If <code>DIR</code> is not specified,
                changes to the home directory. If <code>DIR</code> is <code>-</code>, changes to the previous directory.</p>
                </div>
                """.trimIndent(),
            "source" to
                """
                <div class='definition'><pre><b>source</b> - evaluate contents of file</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>source FILE [ARGUMENTS ...]</pre>
                <p><b>Description</b></p>
                <p><code>source</code> evaluates the commands in <code>FILE</code> in the current shell.
                This is useful for loading functions and setting variables.</p>
                </div>
                """.trimIndent(),
            "eval" to
                """
                <div class='definition'><pre><b>eval</b> - evaluate a string as a command</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>eval [COMMANDS ...]</pre>
                <p><b>Description</b></p>
                <p><code>eval</code> combines arguments and evaluates them as a fish command.</p>
                </div>
                """.trimIndent(),
            "exec" to
                """
                <div class='definition'><pre><b>exec</b> - execute command in current process</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>exec COMMAND [ARGUMENTS ...]</pre>
                <p><b>Description</b></p>
                <p><code>exec</code> replaces the current fish shell with the specified command.
                The shell will terminate after the command completes.</p>
                </div>
                """.trimIndent(),
            "exit" to
                """
                <div class='definition'><pre><b>exit</b> - exit the shell</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>exit [STATUS]</pre>
                <p><b>Description</b></p>
                <p><code>exit</code> causes fish to exit. If <code>STATUS</code> is supplied,
                it becomes the exit status of the shell.</p>
                </div>
                """.trimIndent(),
            "and" to
                """
                <div class='definition'><pre><b>and</b> - execute command if previous succeeded</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>COMMAND1; and COMMAND2</pre>
                <p><b>Description</b></p>
                <p><code>and</code> executes <code>COMMAND2</code> only if <code>COMMAND1</code> succeeded (exit status 0).</p>
                </div>
                """.trimIndent(),
            "or" to
                """
                <div class='definition'><pre><b>or</b> - execute command if previous failed</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>COMMAND1; or COMMAND2</pre>
                <p><b>Description</b></p>
                <p><code>or</code> executes <code>COMMAND2</code> only if <code>COMMAND1</code> failed (non-zero exit status).</p>
                </div>
                """.trimIndent(),
            "not" to
                """
                <div class='definition'><pre><b>not</b> - negate the exit status of a command</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>not COMMAND</pre>
                <p><b>Description</b></p>
                <p><code>not</code> negates the exit status of a job. If the job exits with status 0,
                <code>not</code> returns 1. If it exits with non-zero, <code>not</code> returns 0.</p>
                </div>
                """.trimIndent(),
            "builtin" to
                """
                <div class='definition'><pre><b>builtin</b> - run a builtin command</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>builtin COMMAND [ARGUMENTS ...]</pre>
                <p><b>Description</b></p>
                <p><code>builtin</code> forces fish to use the builtin version of <code>COMMAND</code>,
                even if a function with the same name exists.</p>
                </div>
                """.trimIndent(),
            "command" to
                """
                <div class='definition'><pre><b>command</b> - run a command</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>command [OPTIONS] COMMAND [ARGUMENTS ...]</pre>
                <p><b>Description</b></p>
                <p><code>command</code> forces fish to use the external command <code>COMMAND</code>,
                bypassing any function with the same name.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-s</code> or <code>--search</code> - Print the path to the command</li>
                <li><code>-q</code> or <code>--query</code> - Return 0 if command exists</li>
                </ul>
                </div>
                """.trimIndent(),
            "status" to
                """
                <div class='definition'><pre><b>status</b> - query fish runtime information</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status [SUBCOMMAND]</pre>
                <p><b>Description</b></p>
                <p><code>status</code> provides information about the current fish session.</p>
                <p><b>Subcommands</b></p>
                <ul>
                <li><code>status is-interactive</code> - Test if shell is interactive</li>
                <li><code>status is-login</code> - Test if shell is a login shell</li>
                <li><code>status is-block</code> - Test if currently in a block</li>
                <li><code>status is-command-substitution</code> - Test if in command substitution</li>
                <li><code>status current-command</code> - Print currently running command</li>
                <li><code>status filename</code> - Print current script filename</li>
                <li><code>status line-number</code> - Print current line number</li>
                <li><code>status basename</code> - Print basename of current script</li>
                <li><code>status dirname</code> - Print directory of current script</li>
                <li><code>status fish-path</code> - Print path to fish executable</li>
                <li><code>status function</code> - Print name of current function</li>
                <li><code>status stack-trace</code> - Print stack trace</li>
                <li><code>status job-control</code> - Print or set job control mode</li>
                <li><code>status features</code> - List available feature flags</li>
                <li><code>status test-feature</code> - Test if a feature flag is enabled</li>
                </ul>
                </div>
                """.trimIndent(),
            "status is-interactive" to
                """
                <div class='definition'><pre><b>status is-interactive</b> - test if shell is interactive</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status is-interactive</pre>
                <p><b>Description</b></p>
                <p>Returns 0 if fish is an interactive shell, or 1 otherwise. An interactive shell reads user input from the terminal.</p>
                <p><b>Example</b></p>
                <pre>if status is-interactive
    # Code that only runs in interactive mode
end</pre>
                </div>
                """.trimIndent(),
            "status is-login" to
                """
                <div class='definition'><pre><b>status is-login</b> - test if shell is a login shell</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status is-login</pre>
                <p><b>Description</b></p>
                <p>Returns 0 if fish is a login shell, or 1 otherwise. A login shell is started when you log in to the system.</p>
                <p><b>Example</b></p>
                <pre>if status is-login
    # Environment setup for login shells
end</pre>
                </div>
                """.trimIndent(),
            "status is-block" to
                """
                <div class='definition'><pre><b>status is-block</b> - test if currently in a block</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status is-block</pre>
                <p><b>Description</b></p>
                <p>Returns 0 if currently executing inside a block scope (begin/end, if/end, for/end, etc.), or 1 otherwise.</p>
                </div>
                """.trimIndent(),
            "status is-command-substitution" to
                """
                <div class='definition'><pre><b>status is-command-substitution</b> - test if in command substitution</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status is-command-substitution</pre>
                <p><b>Description</b></p>
                <p>Returns 0 if currently executing inside a command substitution <code>(...)</code>, or 1 otherwise.</p>
                </div>
                """.trimIndent(),
            "status is-no-job-control" to
                """
                <div class='definition'><pre><b>status is-no-job-control</b> - test if job control is disabled</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status is-no-job-control</pre>
                <p><b>Description</b></p>
                <p>Returns 0 if job control is disabled, or 1 otherwise.</p>
                </div>
                """.trimIndent(),
            "status is-full-job-control" to
                """
                <div class='definition'><pre><b>status is-full-job-control</b> - test if full job control is active</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status is-full-job-control</pre>
                <p><b>Description</b></p>
                <p>Returns 0 if full job control is active, or 1 otherwise.</p>
                </div>
                """.trimIndent(),
            "status is-interactive-job-control" to
                """
                <div class='definition'><pre><b>status is-interactive-job-control</b> - test if interactive job control is active</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status is-interactive-job-control</pre>
                <p><b>Description</b></p>
                <p>Returns 0 if job control is enabled only for interactive shells, or 1 otherwise.</p>
                </div>
                """.trimIndent(),
            "status current-command" to
                """
                <div class='definition'><pre><b>status current-command</b> - print currently running command</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status current-command</pre>
                <p><b>Description</b></p>
                <p>Prints the name of the currently running command or function. Useful for debugging and logging.</p>
                <p><b>Example</b></p>
                <pre>function myfunc
    echo "Running: "(status current-command)
end</pre>
                </div>
                """.trimIndent(),
            "status filename" to
                """
                <div class='definition'><pre><b>status filename</b> - print current script filename</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status filename</pre>
                <p><b>Description</b></p>
                <p>Prints the full path of the currently executing script, or <code>stdin</code> if reading from standard input.</p>
                <p><b>Example</b></p>
                <pre>set script_dir (dirname (status filename))</pre>
                </div>
                """.trimIndent(),
            "status basename" to
                """
                <div class='definition'><pre><b>status basename</b> - print basename of current script</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status basename</pre>
                <p><b>Description</b></p>
                <p>Prints just the filename (without directory) of the currently executing script.</p>
                </div>
                """.trimIndent(),
            "status dirname" to
                """
                <div class='definition'><pre><b>status dirname</b> - print directory of current script</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status dirname</pre>
                <p><b>Description</b></p>
                <p>Prints the directory containing the currently executing script.</p>
                <p><b>Example</b></p>
                <pre>source (status dirname)/lib.fish</pre>
                </div>
                """.trimIndent(),
            "status line-number" to
                """
                <div class='definition'><pre><b>status line-number</b> - print current line number</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status line-number</pre>
                <p><b>Description</b></p>
                <p>Prints the line number of the currently executing statement. Useful for debugging.</p>
                </div>
                """.trimIndent(),
            "status fish-path" to
                """
                <div class='definition'><pre><b>status fish-path</b> - print path to fish executable</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status fish-path</pre>
                <p><b>Description</b></p>
                <p>Prints the absolute path to the currently running fish binary.</p>
                </div>
                """.trimIndent(),
            "status function" to
                """
                <div class='definition'><pre><b>status function</b> - print name of current function</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status function</pre>
                <p><b>Description</b></p>
                <p>Prints the name of the currently executing function, or an empty string if not in a function.</p>
                </div>
                """.trimIndent(),
            "status stack-trace" to
                """
                <div class='definition'><pre><b>status stack-trace</b> - print stack trace</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status stack-trace</pre>
                <p><b>Description</b></p>
                <p>Prints a stack trace showing the chain of function calls that led to the current point.</p>
                </div>
                """.trimIndent(),
            "status job-control" to
                """
                <div class='definition'><pre><b>status job-control</b> - print or set job control mode</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status job-control [MODE]</pre>
                <p><b>Description</b></p>
                <p>With no argument, prints the current job control mode. With an argument, sets the mode to one of: <code>full</code>, <code>interactive</code>, or <code>none</code>.</p>
                </div>
                """.trimIndent(),
            "status features" to
                """
                <div class='definition'><pre><b>status features</b> - list available feature flags</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status features</pre>
                <p><b>Description</b></p>
                <p>Lists all available feature flags and their current state (enabled/disabled).</p>
                </div>
                """.trimIndent(),
            "status test-feature" to
                """
                <div class='definition'><pre><b>status test-feature</b> - test if a feature flag is enabled</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>status test-feature FEATURE</pre>
                <p><b>Description</b></p>
                <p>Returns 0 if the specified feature flag is enabled, 1 if disabled, or 2 if the feature is unknown.</p>
                <p><b>Example</b></p>
                <pre>if status test-feature qmark-noglob
    echo "qmark-noglob is enabled"
end</pre>
                </div>
                """.trimIndent(),
            "path" to
                """
                <div class='definition'><pre><b>path</b> - manipulate and check paths</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path SUBCOMMAND [OPTIONS] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p><code>path</code> performs operations on paths.</p>
                <p><b>Subcommands</b></p>
                <ul>
                <li><code>path basename</code> - Get last path component</li>
                <li><code>path dirname</code> - Get directory portion</li>
                <li><code>path extension</code> - Get file extension</li>
                <li><code>path filter</code> - Filter paths by type/permissions</li>
                <li><code>path is</code> - Test if paths match criteria</li>
                <li><code>path mtime</code> - Get modification time</li>
                <li><code>path normalize</code> - Normalize path (collapse .., remove //)</li>
                <li><code>path resolve</code> - Resolve to absolute path</li>
                <li><code>path change-extension</code> - Change file extension</li>
                <li><code>path sort</code> - Sort paths</li>
                </ul>
                <p><b>General Options</b></p>
                <ul>
                <li><code>-z</code> or <code>--null-in</code> - Accept NUL-delimited input</li>
                <li><code>-Z</code> or <code>--null-out</code> - Print NUL-delimited output</li>
                <li><code>-q</code> or <code>--quiet</code> - Suppress output, only return status</li>
                </ul>
                </div>
                """.trimIndent(),
            "path basename" to
                """
                <div class='definition'><pre><b>path basename</b> - get last path component</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path basename [-E | --no-extension] [-z] [-Z] [-q] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Returns the last path component (filename) by removing the directory prefix and trailing slashes.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-E</code> or <code>--no-extension</code> - Also remove the extension</li>
                </ul>
                <p><b>Example</b></p>
                <pre>path basename /usr/bin/fish  # outputs: fish
path basename -E ./foo.mp4  # outputs: foo</pre>
                </div>
                """.trimIndent(),
            "path dirname" to
                """
                <div class='definition'><pre><b>path dirname</b> - get directory portion</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path dirname [-z] [-Z] [-q] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Returns the directory portion of the path (everything before the last "/").</p>
                <p><b>Example</b></p>
                <pre>path dirname /usr/bin/fish  # outputs: /usr/bin
path dirname ./foo.mp4  # outputs: .</pre>
                </div>
                """.trimIndent(),
            "path extension" to
                """
                <div class='definition'><pre><b>path extension</b> - get file extension</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path extension [-z] [-Z] [-q] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Returns the extension of the path (the part after and including the last ".").</p>
                <p><b>Example</b></p>
                <pre>path extension ./foo.mp4  # outputs: .mp4
path extension ~/.config  # outputs nothing (no extension)</pre>
                </div>
                """.trimIndent(),
            "path filter" to
                """
                <div class='definition'><pre><b>path filter</b> - filter paths by type and permissions</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path filter [-z] [-Z] [-q] [-d] [-f] [-l] [-r] [-w] [-x] [-v] [-t TYPE] [-p PERM] [--all] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Returns paths that exist and match the given type/permission criteria.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-t TYPE</code> or <code>--type TYPE</code> - Filter by type: dir, file, link, block, char, fifo, socket</li>
                <li><code>-p PERM</code> or <code>--perm PERM</code> - Filter by permission: read, write, exec, suid, sgid, user, group</li>
                <li><code>-d</code>, <code>-f</code>, <code>-l</code> - Shortcuts for --type=dir/file/link</li>
                <li><code>-r</code>, <code>-w</code>, <code>-x</code> - Shortcuts for --perm=read/write/exec</li>
                <li><code>-v</code> or <code>--invert</code> - Invert the filter</li>
                <li><code>--all</code> - Return 0 only if all paths pass</li>
                </ul>
                <p><b>Example</b></p>
                <pre>path filter -f -x /usr/bin/*  # executable files
path filter --type dir /home/*  # directories only</pre>
                </div>
                """.trimIndent(),
            "path is" to
                """
                <div class='definition'><pre><b>path is</b> - test if paths match criteria</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path is [-z] [-Z] [-q] [-d] [-f] [-l] [-r] [-w] [-x] [-v] [-t TYPE] [-p PERM] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Shorthand for <code>path filter -q</code>. Tests if any path matches without producing output.</p>
                <p><b>Example</b></p>
                <pre>if path is -f ~/.config/fish/config.fish
    echo "Config exists"
end</pre>
                </div>
                """.trimIndent(),
            "path mtime" to
                """
                <div class='definition'><pre><b>path mtime</b> - get modification time</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path mtime [-z] [-Z] [-q] [-R | --relative] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Returns the last modification time in seconds since the Unix epoch.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-R</code> or <code>--relative</code> - Print seconds since modification (age)</li>
                </ul>
                <p><b>Example</b></p>
                <pre>path mtime /etc/passwd  # outputs: 1657213796
path mtime -R /etc/  # outputs: 4078 (seconds ago)</pre>
                </div>
                """.trimIndent(),
            "path normalize" to
                """
                <div class='definition'><pre><b>path normalize</b> - normalize path</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path normalize [-z] [-Z] [-q] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Normalizes paths by squashing duplicate "/", collapsing ".." components, and removing "." components. Does not resolve symlinks or make paths absolute.</p>
                <p><b>Example</b></p>
                <pre>path normalize /usr/bin//../../etc/fish  # outputs: /etc/fish
path normalize ./my/subdirs/../sub2  # outputs: my/sub2</pre>
                </div>
                """.trimIndent(),
            "path resolve" to
                """
                <div class='definition'><pre><b>path resolve</b> - resolve to absolute path</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path resolve [-z] [-Z] [-q] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Returns the normalized, physical, absolute path. Resolves symlinks and makes the path absolute.</p>
                <p><b>Example</b></p>
                <pre>path resolve ./script.fish  # outputs: /home/user/scripts/script.fish
path resolve /bin/sh  # outputs: /usr/bin/bash (if /bin is a symlink)</pre>
                </div>
                """.trimIndent(),
            "path change-extension" to
                """
                <div class='definition'><pre><b>path change-extension</b> - change file extension</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path change-extension [-z] [-Z] [-q] EXTENSION [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Changes the extension of each path to the new extension. One leading dot is ignored.</p>
                <p><b>Example</b></p>
                <pre>path change-extension mp4 ./foo.wmv  # outputs: ./foo.mp4
path change-extension '' ./foo.mp4  # outputs: ./foo (strips extension)</pre>
                </div>
                """.trimIndent(),
            "path sort" to
                """
                <div class='definition'><pre><b>path sort</b> - sort paths</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>path sort [-z] [-Z] [-q] [-r] [-u] [--key=KEY] [PATH ...]</pre>
                <p><b>Description</b></p>
                <p>Sorts paths alphabetically with numerical runs compared numerically (natural sort).</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-r</code> or <code>--reverse</code> - Reverse sort order</li>
                <li><code>-u</code> or <code>--unique</code> - Remove duplicates</li>
                <li><code>--key=KEY</code> - Sort by: basename, dirname, or path (default)</li>
                </ul>
                <p><b>Example</b></p>
                <pre>path sort 10-foo 2-bar  # outputs: 2-bar\n10-foo
path sort --key=basename /a/z.txt /b/a.txt  # sorts by filename</pre>
                </div>
                """.trimIndent(),
            "history" to
                """
                <div class='definition'><pre><b>history</b> - show and manipulate command history</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>history [SUBCOMMAND] [OPTIONS] [SEARCH_STRING ...]</pre>
                <p><b>Description</b></p>
                <p><code>history</code> is used to search, delete, and manipulate command history.</p>
                <p><b>Subcommands</b></p>
                <ul>
                <li><code>history search</code> - Search history (default)</li>
                <li><code>history delete</code> - Delete history items</li>
                <li><code>history merge</code> - Incorporate history from other sessions</li>
                <li><code>history save</code> - Write history to file</li>
                <li><code>history clear</code> - Clear all history</li>
                <li><code>history clear-session</code> - Clear current session history</li>
                <li><code>history append</code> - Add commands to history</li>
                </ul>
                </div>
                """.trimIndent(),
            "history search" to
                """
                <div class='definition'><pre><b>history search</b> - search command history</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>history search [--show-time] [-C] [-e|-p|-c] [-n N] [-z] [-R] [SEARCH_STRING ...]</pre>
                <p><b>Description</b></p>
                <p>Returns history items matching the search string. Default operation if no subcommand specified.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-t</code> or <code>--show-time</code> - Show date/time of each entry</li>
                <li><code>-C</code> or <code>--case-sensitive</code> - Case-sensitive search</li>
                <li><code>-e</code> or <code>--exact</code> - Exact match</li>
                <li><code>-p</code> or <code>--prefix</code> - Match prefix</li>
                <li><code>-c</code> or <code>--contains</code> - Match substring (default)</li>
                <li><code>-n N</code> or <code>--max N</code> - Limit to N results</li>
                <li><code>-R</code> or <code>--reverse</code> - Oldest first</li>
                </ul>
                </div>
                """.trimIndent(),
            "history delete" to
                """
                <div class='definition'><pre><b>history delete</b> - delete history items</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>history delete [-C] [-e|-p|-c] SEARCH_STRING ...</pre>
                <p><b>Description</b></p>
                <p>Deletes history items matching the search criteria. Without --exact, prompts for confirmation.</p>
                <p><b>Options</b></p>
                <ul>
                <li><code>-C</code> or <code>--case-sensitive</code> - Case-sensitive matching</li>
                <li><code>-e</code> or <code>--exact</code> - Exact match (default for delete)</li>
                <li><code>-c</code> or <code>--contains</code> - Match substring</li>
                </ul>
                </div>
                """.trimIndent(),
            "history merge" to
                """
                <div class='definition'><pre><b>history merge</b> - incorporate history from other sessions</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>history merge</pre>
                <p><b>Description</b></p>
                <p>Immediately incorporates history changes from other fish sessions. Normally fish ignores history from sessions started after the current one.</p>
                </div>
                """.trimIndent(),
            "history save" to
                """
                <div class='definition'><pre><b>history save</b> - save history to file</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>history save</pre>
                <p><b>Description</b></p>
                <p>Immediately writes all history changes to the history file. Normally this happens automatically.</p>
                </div>
                """.trimIndent(),
            "history clear" to
                """
                <div class='definition'><pre><b>history clear</b> - clear all history</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>history clear</pre>
                <p><b>Description</b></p>
                <p>Clears the entire history file. Prompts for confirmation unless called as <code>builtin history clear</code>.</p>
                </div>
                """.trimIndent(),
            "history clear-session" to
                """
                <div class='definition'><pre><b>history clear-session</b> - clear current session history</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>history clear-session</pre>
                <p><b>Description</b></p>
                <p>Clears only the history from the current session. If <code>history merge</code> was run, only history after that point is cleared.</p>
                </div>
                """.trimIndent(),
            "history append" to
                """
                <div class='definition'><pre><b>history append</b> - add commands to history</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>history append COMMAND ...</pre>
                <p><b>Description</b></p>
                <p>Appends commands to the history without executing them.</p>
                </div>
                """.trimIndent(),
            "printf" to
                """
                <div class='definition'><pre><b>printf</b> - display formatted text</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>printf FORMAT [ARGUMENTS ...]</pre>
                <p><b>Description</b></p>
                <p><code>printf</code> formats and prints its arguments according to <code>FORMAT</code>.</p>
                <p><b>Format Specifiers</b></p>
                <ul>
                <li><code>%s</code> - String</li>
                <li><code>%d</code> - Decimal integer</li>
                <li><code>%f</code> - Floating point</li>
                <li><code>%x</code> - Hexadecimal</li>
                <li><code>%%</code> - Literal percent sign</li>
                </ul>
                </div>
                """.trimIndent(),
            "math" to
                """
            <div class='definition'><pre><b>math</b> - perform mathematical calculations</pre></div>
            <div class='content'>
            <p><b>Synopsis</b></p>
            <pre>math [OPTIONS] EXPRESSION</pre>
            <p><b>Description</b></p>
            <p><code>math</code> evaluates mathematical expressions.</p>
            <p><b>Options</b></p>
            <ul>
            <li><code>-s N</code> or <code>--scale N</code> - Number of decimal places</li>
            <li><code>-b BASE</code> or <code>--base BASE</code> - Output base (hex, octal, binary)</li>
            </ul>
            <p><b>Example</b></p>
            <pre>math "1 + 2"  # outputs 3
math -s2 "10 / 3"  # outputs 3.33</pre>
            </div>
                """.trimIndent(),
            "in" to
                """
                <div class='definition'><pre><b>in</b> - part of for loop syntax</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>for VARNAME in [VALUES ...]; COMMANDS ...; end</pre>
                <p><b>Description</b></p>
                <p><code>in</code> is used in <code>for</code> loops to specify the list of values to iterate over.</p>
                </div>
                """.trimIndent(),
            "time" to
                """
                <div class='definition'><pre><b>time</b> - measure how long a command takes</pre></div>
                <div class='content'>
                <p><b>Synopsis</b></p>
                <pre>time COMMAND</pre>
                <p><b>Description</b></p>
                <p><code>time</code> measures and reports how long <code>COMMAND</code> takes to execute.</p>
                </div>
                """.trimIndent(),
        )

    val VARIABLE_DOCS: Map<String, String> =
        mapOf(
            "status" to
                """
                <div class='definition'><pre><b>${DOLLAR}status</b> - exit status of last command</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The exit status of the last foreground job to exit. If the job was terminated
                through a signal, the exit status will be 128 plus the signal number.</p>
                <p>A value of 0 indicates success; non-zero indicates failure.</p>
                </div>
                """.trimIndent(),
            "argv" to
                """
            <div class='definition'><pre><b>${DOLLAR}argv</b> - command arguments</pre></div>
            <div class='content'>
            <p><b>Description</b></p>
            <p>A list containing the arguments passed to the current function or script.
            Use <code>${DOLLAR}argv[1]</code> for the first argument, <code>${DOLLAR}argv[2]</code> for the second, etc.</p>
            <p><b>Example</b></p>
            <pre>function greet
    echo "Hello, ${DOLLAR}argv[1]!"
end</pre>
            </div>
                """.trimIndent(),
            "pipestatus" to
                """
            <div class='definition'><pre><b>${DOLLAR}pipestatus</b> - exit status of all commands in a pipe</pre></div>
            <div class='content'>
            <p><b>Description</b></p>
            <p>A list of the exit statuses of all commands in the most recent pipeline.
            <code>${DOLLAR}pipestatus[1]</code> contains the exit status of the first command, etc.</p>
            <p><b>Example</b></p>
            <pre>false | true | false
echo ${DOLLAR}pipestatus  # outputs: 1 0 1</pre>
            </div>
                """.trimIndent(),
            "CMD_DURATION" to
                """
                <div class='definition'><pre><b>${DOLLAR}CMD_DURATION</b> - duration of last command</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The runtime of the last command in milliseconds. Useful for displaying
                in your prompt when commands take a long time.</p>
                </div>
                """.trimIndent(),
            "fish_pid" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_pid</b> - process ID of fish</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The process ID of the current fish shell instance.</p>
                </div>
                """.trimIndent(),
            "last_pid" to
                """
                <div class='definition'><pre><b>${DOLLAR}last_pid</b> - PID of last background job</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The process ID of the last background process that was started.</p>
                </div>
                """.trimIndent(),
            "PATH" to
                """
            <div class='definition'><pre><b>${DOLLAR}PATH</b> - command search path</pre></div>
            <div class='content'>
            <p><b>Description</b></p>
            <p>A list of directories that fish searches for commands. Modify this to add
            directories containing executables you want to run by name.</p>
            <p><b>Example</b></p>
            <pre>set -gx PATH /usr/local/bin ${DOLLAR}PATH
# or use fish_add_path
fish_add_path /usr/local/bin</pre>
            </div>
                """.trimIndent(),
            "CDPATH" to
                """
                <div class='definition'><pre><b>${DOLLAR}CDPATH</b> - cd search path</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>A list of directories that <code>cd</code> will search for the target directory.
                If the target is not found relative to the current directory, these directories are checked.</p>
                </div>
                """.trimIndent(),
            "fish_user_paths" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_user_paths</b> - user path additions</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>A universal variable containing paths to prepend to <code>${DOLLAR}PATH</code>.
                Use <code>fish_add_path</code> to add to this safely.</p>
                </div>
                """.trimIndent(),
            "fish_function_path" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_function_path</b> - function search path</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>A list of directories that fish searches for autoloading functions.</p>
                </div>
                """.trimIndent(),
            "fish_complete_path" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_complete_path</b> - completion search path</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>A list of directories that fish searches for completion scripts.</p>
                </div>
                """.trimIndent(),
            "fish_greeting" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_greeting</b> - startup greeting</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The greeting message displayed when fish starts. Set to empty string to disable.</p>
                <p><b>Example</b></p>
                <pre>set -U fish_greeting ""  # disable greeting</pre>
                </div>
                """.trimIndent(),
            "fish_history" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_history</b> - history session name</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The current history session name. Set to a different value to use separate history.</p>
                </div>
                """.trimIndent(),
            "fish_trace" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_trace</b> - enable command tracing</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>When set to a non-empty value, fish will print each command before executing it.
                Useful for debugging scripts.</p>
                </div>
                """.trimIndent(),
            "version" to
                """
                <div class='definition'><pre><b>${DOLLAR}version</b> - fish version</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The version string of the currently running fish shell.</p>
                </div>
                """.trimIndent(),
            "hostname" to
                """
                <div class='definition'><pre><b>${DOLLAR}hostname</b> - system hostname</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The hostname of the system. This is set at fish startup.</p>
                </div>
                """.trimIndent(),
            "history" to
                """
                <div class='definition'><pre><b>${DOLLAR}history</b> - command history</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>A list of previously executed commands. <code>${DOLLAR}history[1]</code> is the most recent command.</p>
                </div>
                """.trimIndent(),
            "status_generation" to
                """
                <div class='definition'><pre><b>${DOLLAR}status_generation</b> - status generation count</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>The "generation" count of <code>${DOLLAR}status</code>. Incremented only when the previous
                command produced an explicit status. Background jobs do not increment this.</p>
                </div>
                """.trimIndent(),
            "fish_kill_signal" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_kill_signal</b> - signal that killed last job</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>If the last foreground job was killed by a signal, this contains the signal number.
                Otherwise, it is 0.</p>
                </div>
                """.trimIndent(),
            "fish_killring" to
                """
                <div class='definition'><pre><b>${DOLLAR}fish_killring</b> - cut/kill text storage</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>A list containing text that has been cut from the command line using keyboard shortcuts.</p>
                </div>
                """.trimIndent(),
            "_" to
                """
                <div class='definition'><pre><b>${DOLLAR}_</b> - name of current command</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Initially set to the path of the fish executable. For external commands,
                set to the command name. Reset to fish path before running each prompt.</p>
                </div>
                """.trimIndent(),
        )

    val OPERATOR_DOCS: Map<String, String> =
        mapOf(
            "|" to
                """
                <div class='definition'><pre><b>|</b> - pipe</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Connects the standard output of one command to the standard input of another.</p>
                <p><b>Example</b></p>
                <pre>ls | grep txt</pre>
                </div>
                """.trimIndent(),
            ">" to
                """
                <div class='definition'><pre><b>&gt;</b> - redirect output</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Redirects standard output to a file, overwriting it if it exists.</p>
                <p><b>Example</b></p>
                <pre>echo "hello" &gt; file.txt</pre>
                </div>
                """.trimIndent(),
            ">>" to
                """
                <div class='definition'><pre><b>&gt;&gt;</b> - append output</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Redirects standard output to a file, appending to it if it exists.</p>
                <p><b>Example</b></p>
                <pre>echo "hello" &gt;&gt; file.txt</pre>
                </div>
                """.trimIndent(),
            "<" to
                """
                <div class='definition'><pre><b>&lt;</b> - redirect input</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Redirects standard input from a file.</p>
                <p><b>Example</b></p>
                <pre>wc -l &lt; file.txt</pre>
                </div>
                """.trimIndent(),
            "&&" to
                """
                <div class='definition'><pre><b>&amp;&amp;</b> - and operator</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Execute the following command only if the previous command succeeded (exit status 0).</p>
                <p><b>Example</b></p>
                <pre>test -f file.txt &amp;&amp; echo "exists"</pre>
                </div>
                """.trimIndent(),
            "||" to
                """
                <div class='definition'><pre><b>||</b> - or operator</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Execute the following command only if the previous command failed (non-zero exit status).</p>
                <p><b>Example</b></p>
                <pre>test -f file.txt || echo "not found"</pre>
                </div>
                """.trimIndent(),
            "&" to
                """
                <div class='definition'><pre><b>&amp;</b> - background job</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Runs the command in the background, allowing you to continue using the shell.</p>
                <p><b>Example</b></p>
                <pre>sleep 10 &amp;</pre>
                </div>
                """.trimIndent(),
            "2>" to
                """
                <div class='definition'><pre><b>2&gt;</b> - redirect stderr</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Redirects standard error (file descriptor 2) to a file.</p>
                <p><b>Example</b></p>
                <pre>command 2&gt; errors.log</pre>
                </div>
                """.trimIndent(),
            "&>" to
                """
                <div class='definition'><pre><b>&amp;&gt;</b> - redirect all output</pre></div>
                <div class='content'>
                <p><b>Description</b></p>
                <p>Redirects both standard output and standard error to a file.</p>
                <p><b>Example</b></p>
                <pre>command &amp;&gt; output.log</pre>
                </div>
                """.trimIndent(),
        )

    fun getCommandDoc(name: String): String? = COMMAND_DOCS[name]

    fun getVariableDoc(name: String): String? {
        val cleanName = name.removePrefix(DOLLAR).split("[")[0]
        return VARIABLE_DOCS[cleanName]
    }

    fun getOperatorDoc(operator: String): String? = OPERATOR_DOCS[operator]
}
