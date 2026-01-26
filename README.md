# Fish Shell Plugin for JetBrains IDEs

[![Build](https://github.com/tox-dev/jetbrains-fish/actions/workflows/build.yml/badge.svg)](https://github.com/tox-dev/jetbrains-fish/actions/workflows/build.yml)
[![Version](https://img.shields.io/jetbrains/plugin/v/com.github.toxdev.fish.svg)](https://plugins.jetbrains.com/plugin/com.github.toxdev.fish)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/com.github.toxdev.fish.svg)](https://plugins.jetbrains.com/plugin/com.github.toxdev.fish)

This plugin provides comprehensive language support for [Fish shell](https://fishshell.com/) scripts in JetBrains IDEs.

## Features

### Syntax Highlighting

The plugin provides rich syntax highlighting with customizable colors for all Fish language elements. This includes
keywords like `if`, `else`, `for`, `while`, `function`, `switch`, `begin`, and `end`. Variables are highlighted
including indexed access like `$var[index]` and special variables such as `$status`, `$argv`, and `$PATH`. Both single
and double quoted strings are supported with escape sequence highlighting. Comments, operators (`&&`, `||`, `|`, `&`),
redirections (`>`, `>>`, `<`, `2>`, `&>`), and command substitution syntax are all properly highlighted.

You can customize colors via **Settings → Editor → Color Scheme → Fish**.

### Code Intelligence

The plugin offers code completion for keywords, builtins, and user-defined functions. You can navigate to function
definitions with Ctrl+Click using the go to definition feature. The find usages feature allows you to find all
references to a function. Quick documentation is available on hover for builtins, keywords, operators, and variables,
including detailed documentation for subcommands of `string`, `status`, `path`, and `history`.

### Navigation and Structure

The structure view displays all functions in the current file for easy navigation. Breadcrumbs show your current
location within nested blocks. Code folding is available for functions and control structures. Brace matching highlights
matching parentheses, braces, and brackets.

### Editing Support

You can toggle comments with `Ctrl+/` or `Cmd+/`. Code formatting is available via `fish_indent` when it is installed on
your system. Run configurations allow you to execute Fish scripts directly from the IDE. A run gutter icon appears next
to scripts for quick execution.

### Code Quality

The plugin includes inspections that warn about deprecated Fish syntax, including deprecated functions, variables, and
flags. It also detects unused local variables.

## Installation

### From JetBrains Marketplace

Open **Settings → Plugins → Marketplace**, search for **Fish Shell**, and click **Install**.

### Manual Installation

Download the plugin from the [JetBrains Marketplace](https://plugins.jetbrains.com/plugin/com.github.toxdev.fish). Then
open **Settings → Plugins → ⚙️ → Install Plugin from Disk...** and select the downloaded file.

## Requirements

The plugin requires IntelliJ IDEA 2024.3 or later, or any compatible JetBrains IDE. For code formatting, `fish_indent`
must be available in your system PATH.

## File Association

The plugin automatically recognizes files with the `.fish` extension. It also recognizes files with a Fish shebang such
as `#!/usr/bin/env fish` or `#!/usr/bin/fish`.

## Configuration

### Color Scheme

You can customize syntax highlighting colors at **Settings → Editor → Color Scheme → Fish**.

### External Formatter

The plugin uses `fish_indent` for code formatting. Ensure Fish is installed and `fish_indent` is available in your PATH.

## Known Limitations

Brace expansion syntax like `echo file.{txt,md}` is lexed as regular words because expansion only occurs at runtime.
Glob patterns like `ls *.fish` are not specially highlighted since they require runtime evaluation. Command arguments
are not semantically analyzed because each command has its own unique option syntax.

## Contributing

Contributions are welcome. Please feel free to submit issues and pull requests on
[GitHub](https://github.com/tox-dev/jetbrains-fish).

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
