package com.github.toxdev.fish.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.github.toxdev.fish.psi.FishTypes;
import com.github.toxdev.fish.psi.FishTokenTypes;

%%

%class _FishLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%line

%xstate IN_DOUBLE_QUOTE
%xstate IN_SINGLE_QUOTE
%xstate IN_ANSI_QUOTE

// Whitespace
WHITE_SPACE=[ \t]+
NEWLINE=\r?\n

// Comments and shebang (order matters - shebang before comment)
SHEBANG="#!"[^\r\n]*
COMMENT="#"([^!\r\n][^\r\n]*)?

// Variables
VARIABLE=\$\$?[a-zA-Z_][a-zA-Z0-9_]*(\[[^\]]*\])?
SPECIAL_VAR=\$[0-9@*#?$!-]

// Number
NUMBER=[0-9]+

// Line continuation
LINE_CONTINUATION=\\[ \t]*\r?\n

// Operators
PIPE=\|
AND_AND=&&
OR_OR=\|\|
BACKGROUND=&

// Redirects - ordered from most specific to least specific
// File descriptor duplication: n>&m, n<&m, >&n, <&n, n>&-, n<&-, >&-, <&-
REDIRECT_DUP=[0-9]?[<>]&[0-9-]
// Stderr redirects with fd: 2>, 2>>, 2>|
REDIRECT_ERR=[0-9][>][>]?[\|]?
// Combined stdout+stderr: &>, >&
REDIRECT_ALL=[&>][>&]
// Append redirect: >>
REDIRECT_APPEND=>>
// Pipe redirect (clobber): >|
REDIRECT_CLOBBER=>\|
// Basic redirects: >, <
REDIRECT_OUT=>
REDIRECT_IN=<

// Escape sequence in unquoted context
UNQUOTED_ESCAPE=\\[^]

// Word parts and brace expansion patterns
// WP = word part (no braces)
WP=[^ \t\r\n\|\&\;\(\)\{\}\[\]\"\'\\#$<>]+
// BC = brace content (non-nested)
BC=[^{}\r\n]+
// Compound word: word + brace expansion combinations (e.g., foo{bar,baz}, {a,b}foo, foo{a,b}bar)
COMPOUND_WORD=({WP}(\{{BC}\}{WP}?)*)|((\{{BC}\})+{WP}(\{{BC}\}|{WP})*)
// Simple word
WORD={WP}

%%

<YYINITIAL> {
    {LINE_CONTINUATION} { return FishTokenTypes.WHITE_SPACE; }
    {WHITE_SPACE}       { return FishTokenTypes.WHITE_SPACE; }
    {NEWLINE}           { return FishTypes.NEWLINE; }

    // Shebang must come before comment (both start with #)
    {SHEBANG}           { return FishTypes.SHEBANG; }
    {COMMENT}           { return FishTypes.COMMENT; }

    // Variables and numbers
    {VARIABLE}          { return FishTypes.VARIABLE; }
    {SPECIAL_VAR}       { return FishTypes.VARIABLE; }
    "$("{NUMBER}?")"    { return FishTypes.VARIABLE; }

    // Command substitution
    "$("                { return FishTypes.LPAREN; }

    // String delimiters
    \"                  { yybegin(IN_DOUBLE_QUOTE); return FishTypes.DOUBLE_QUOTE; }
    \'                  { yybegin(IN_SINGLE_QUOTE); return FishTypes.SINGLE_QUOTE; }
    "$'"                { yybegin(IN_ANSI_QUOTE); return FishTypes.SINGLE_QUOTE; }

    // Operators (order matters - longer/more specific patterns first)
    {AND_AND}           { return FishTypes.AND_AND; }
    {OR_OR}             { return FishTypes.OR_OR; }

    // Redirects (order matters - most specific first)
    {REDIRECT_DUP}      { return FishTypes.REDIRECT; }
    {REDIRECT_ERR}      { return FishTypes.REDIRECT; }
    {REDIRECT_ALL}      { return FishTypes.REDIRECT; }
    {REDIRECT_APPEND}   { return FishTypes.REDIRECT; }
    {REDIRECT_CLOBBER}  { return FishTypes.REDIRECT; }
    {REDIRECT_OUT}      { return FishTypes.REDIRECT; }
    {REDIRECT_IN}       { return FishTypes.REDIRECT; }

    // Other operators
    {PIPE}              { return FishTypes.PIPE; }
    {BACKGROUND}        { return FishTypes.BACKGROUND; }

    // Numbers (must come after redirects so 2> is handled as redirect)
    {NUMBER}            { return FishTypes.NUMBER; }

    // Separators
    ";"                 { return FishTypes.SEMICOLON; }

    // Brackets
    "("                 { return FishTypes.LPAREN; }
    ")"                 { return FishTypes.RPAREN; }
    "{"                 { return FishTypes.LBRACE; }
    "}"                 { return FishTypes.RBRACE; }
    "["                 { return FishTypes.LBRACKET; }
    "]"                 { return FishTypes.RBRACKET; }

    // Unquoted escape sequences (for regex patterns, special chars, etc.)
    {UNQUOTED_ESCAPE}   { return FishTypes.WORD; }

    // Compound words with brace expansions (e.g., foo{bar,baz}) - must come before WORD
    {COMPOUND_WORD}     { return FishTypes.WORD; }

    // Simple words (catch-all for commands and arguments)
    {WORD}              { return FishTypes.WORD; }

    // Fallback for any unrecognized character
    [^]                 { return FishTokenTypes.BAD_CHARACTER; }
}

<IN_DOUBLE_QUOTE> {
    // Variable expansion inside double quotes
    {VARIABLE}          { return FishTypes.VARIABLE; }
    {SPECIAL_VAR}       { return FishTypes.VARIABLE; }

    // Escape sequences
    \\[ntr\\\"$]        { return FishTypes.ESCAPE; }
    \\x[0-9a-fA-F]{2}   { return FishTypes.ESCAPE; }
    \\u[0-9a-fA-F]{4}   { return FishTypes.ESCAPE; }
    \\U[0-9a-fA-F]{8}   { return FishTypes.ESCAPE; }

    // End of double-quoted string
    \"                  { yybegin(YYINITIAL); return FishTypes.DOUBLE_QUOTE; }

    // Explicit newline handling
    \r?\n               { return FishTypes.STRING_CONTENT; }

    // String content (anything except special chars and newlines)
    [^\\\"\$\r\n]+      { return FishTypes.STRING_CONTENT; }

    // Fallback for single special chars that aren't part of escapes
    [^]                 { return FishTypes.STRING_CONTENT; }
}

<IN_SINGLE_QUOTE> {
    // Escaped single quote inside single-quoted string (Fish 3.0+)
    \\'                 { return FishTypes.ESCAPE; }

    // End of single-quoted string
    \'                  { yybegin(YYINITIAL); return FishTypes.SINGLE_QUOTE; }

    // Explicit newline handling - JFlex character classes may not match newlines
    \r?\n               { return FishTypes.STRING_CONTENT; }

    // Everything else is literal content (except newlines which are handled above)
    [^\\'\r\n]+         { return FishTypes.STRING_CONTENT; }

    // One or more backslashes not followed by quote are literal
    \\+                 { return FishTypes.STRING_CONTENT; }
}

<IN_ANSI_QUOTE> {
    // Escape sequences in ANSI-C quotes
    \\[abefnrtv\\\'\"?]  { return FishTypes.ESCAPE; }
    \\[0-7]{1,3}        { return FishTypes.ESCAPE; }
    \\x[0-9a-fA-F]{1,2} { return FishTypes.ESCAPE; }
    \\u[0-9a-fA-F]{4}   { return FishTypes.ESCAPE; }
    \\U[0-9a-fA-F]{8}   { return FishTypes.ESCAPE; }

    // End of ANSI-C quoted string
    \'                  { yybegin(YYINITIAL); return FishTypes.SINGLE_QUOTE; }

    // Explicit newline handling
    \r?\n               { return FishTypes.STRING_CONTENT; }

    // String content (except newlines)
    [^\\'\r\n]+         { return FishTypes.STRING_CONTENT; }

    // Fallback for unrecognized escapes
    [^]                 { return FishTypes.STRING_CONTENT; }
}
