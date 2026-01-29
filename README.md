# Fish Shell Plugin for JetBrains IDEs

[![Build](https://github.com/tox-dev/jetbrains-fish/actions/workflows/check.yaml/badge.svg)](https://github.com/tox-dev/jetbrains-fish/actions/workflows/check.yaml)
[![Version](https://img.shields.io/jetbrains/plugin/v/29973.svg)](https://plugins.jetbrains.com/plugin/29973-fish-shell)
[![Downloads](https://img.shields.io/jetbrains/plugin/d/29973.svg)](https://plugins.jetbrains.com/plugin/29973-fish-shell)

This plugin provides comprehensive language support for [Fish shell](https://fishshell.com/) scripts in JetBrains IDEs.

[![Install Plugin](https://img.shields.io/badge/Install-JetBrains%20Marketplace-000?style=for-the-badge&logo=jetbrains)](https://plugins.jetbrains.com/plugin/29973-fish-shell)

## Features

### Syntax Highlighting

The plugin provides rich syntax highlighting with customizable colors for all Fish language elements. This includes
keywords like `if`, `else`, `for`, `while`, `function`, `switch`, `begin`, and `end`. Variables are highlighted
including indexed access like `$var[index]` and special variables such as `$status`, `$argv`, and `$PATH`. Both single
and double quoted strings are supported with escape sequence highlighting. Comments, operators (`&&`, `||`, `|`, `&`),
redirections (`>`, `>>`, `<`, `2>`, `&>`), and command substitution syntax are all properly highlighted.

You can customize colors via **Settings → Editor → Color Scheme → Fish**.

### Code Intelligence (via fish-lsp)

When [fish-lsp](https://github.com/ndonfris/fish-lsp) is installed, the plugin provides advanced code intelligence:

- Context-aware code completion for functions, variables, commands, and arguments.
- Go to definition for functions and variables with Ctrl+Click.
- Find all usages of a function or variable across your workspace.
- Hover documentation for builtins, functions, and variables.
- Real-time diagnostics for error detection and warnings.
- Code actions with quick fixes and refactoring suggestions.
- Safe rename refactoring across files.
- Code formatting via fish-lsp.

Without fish-lsp, the plugin still provides basic code completion for keywords and builtins, plus quick documentation
for Fish language elements.

### Navigation and Structure

The structure view displays all functions in the current file for easy navigation. Breadcrumbs show your current
location within nested blocks. Code folding is available for functions and control structures. Brace matching highlights
matching parentheses, braces, and brackets.

### Editing Support

You can toggle comments with `Ctrl+/` or `Cmd+/`. Code formatting is available via fish-lsp when installed. Run
configurations allow you to execute Fish scripts directly from the IDE. A run gutter icon appears next to scripts for
quick execution. Live templates provide snippets for common Fish constructs like functions, loops, and conditionals.
Color preview shows inline color swatches for `set_color` commands.

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

The plugin requires IntelliJ IDEA 2024.3 or later, or any compatible JetBrains IDE.

### fish-lsp (Recommended)

For full code intelligence features, install [fish-lsp](https://github.com/ndonfris/fish-lsp#installation). The plugin
will automatically detect fish-lsp if it's in your PATH, or you can configure the path manually in **Settings → Tools →
Fish Shell**.

### LSP4IJ Plugin (Community Edition)

If you're using a Community Edition IDE (IntelliJ IDEA Community, PyCharm Community, etc.), you need to install the
[LSP4IJ plugin](https://plugins.jetbrains.com/plugin/23257-lsp4ij) for LSP support. Ultimate editions have built-in LSP
support.

## File Association

The plugin automatically recognizes files with the `.fish` extension. It also recognizes files with a Fish shebang such
as `#!/usr/bin/env fish` or `#!/usr/bin/fish`.

## Configuration

### Color Scheme

You can customize syntax highlighting colors at **Settings → Editor → Color Scheme → Fish**.

### fish-lsp Settings

Configure fish-lsp at **Settings → Tools → Fish Shell**. You can specify a custom path to the fish-lsp executable if
it's not in your PATH, or toggle LSP features on/off.

## Known Limitations

Brace expansion syntax like `echo file.{txt,md}` is lexed as regular words because expansion only occurs at runtime.
Glob patterns like `ls *.fish` are not specially highlighted since they require runtime evaluation. Command arguments
are not semantically analyzed because each command has its own unique option syntax.

## Contributing

Contributions are welcome. Please feel free to submit issues and pull requests on
[GitHub](https://github.com/tox-dev/jetbrains-fish).

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
