# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added

- Initial Fish shell language support.
- Syntax highlighting for Fish shell scripts.
- File type detection for `.fish` extension.
- Shebang detection (`#!/usr/bin/env fish`, `#!/usr/bin/fish`).
- Comment and uncomment support via `Ctrl+/` or `Cmd+/`.
- Brace matching for parentheses, braces, and brackets.
- Customizable color schemes via `Settings > Editor > Color Scheme > Fish`.
- Support for Fish syntax elements including keywords (`if`, `for`, `function`, etc.), variables (`$var`,
  `$var[index]`), strings (single and double quoted with escape sequences), comments, operators (`&&`, `||`, `|`, `&`),
  redirections (`>`, `>>`, `<`, `2>`, `&>`), and command substitution with parentheses.
- Code formatting via `fish_indent` when available on the system.
- Inspections for deprecated syntax (deprecated functions like `__fish_git_prompt`, deprecated variables like `$_`,
  deprecated history flags like `--search`).
- Inspections for unused local variables.
- Run configurations to execute Fish scripts directly from the IDE.

## [0.1.0] - Initial Release

### Added

- Basic Fish shell language plugin structure.
