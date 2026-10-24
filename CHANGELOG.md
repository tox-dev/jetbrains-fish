# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

<!-- towncrier release notes start -->

<a name="release-0-1-10"></a>

## [0.1.10] - 2026-08-03

<a name="release-0-1-9"></a>

## [0.1.9] - 2026-07-29

<a name="release-0-1-9-added"></a>

### Added

- Support IntelliJ Platform 2026.2 ([#108](https://github.com/tox-dev/jetbrains-fish/pull/108)) by
  [@gaborbernat](https://github.com/gaborbernat).

<a name="release-0-1-8"></a>

## [0.1.8] - 2026-02-21

<a name="release-0-1-8-fixed"></a>

### Fixed

- Parse escaped backslashes in single-quoted Fish strings ([#36](https://github.com/tox-dev/jetbrains-fish/pull/36)) by
  [@gaborbernat](https://github.com/gaborbernat).

<a name="release-0-1-7"></a>

## [0.1.7] - 2026-01-29

<a name="release-0-1-6"></a>

## [0.1.6] - 2026-01-29

<a name="release-0-1-5"></a>

## [0.1.5] - 2026-01-29

<a name="release-0-1-4"></a>

## [0.1.4] - 2026-01-29

<a name="release-0-1-3"></a>

## [0.1.3] - 2026-01-29

<a name="release-0-1-2"></a>

## [0.1.2] - 2026-01-29

<a name="release-0-1-2-added"></a>

### Added

- Run a Fish file from the IDE toolbar or gutter icon ([#12](https://github.com/tox-dev/jetbrains-fish/pull/12)) by
  [@gaborbernat](https://github.com/gaborbernat).

<a name="release-0-1-2-fixed"></a>

### Fixed

- Keep LSP support working in PyCharm 2025.3 ([#12](https://github.com/tox-dev/jetbrains-fish/pull/12)) by
  [@gaborbernat](https://github.com/gaborbernat).
- Restore Fish color previews and icons ([#12](https://github.com/tox-dev/jetbrains-fish/pull/12)) by
  [@gaborbernat](https://github.com/gaborbernat).

<a name="release-0-1-1"></a>

## [0.1.1] - 2026-01-29

<a name="release-0-1-1-added"></a>

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

<a name="release-0-1-0"></a>

## [0.1.0] - Initial Release

<a name="release-0-1-0-added"></a>

### Added

- Basic Fish shell language plugin structure.

[0.1.0]: https://github.com/tox-dev/jetbrains-fish/commits/v0.1.0
[0.1.1]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.0...v0.1.1
[0.1.10]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.9...v0.1.10
[0.1.2]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.1...v0.1.2
[0.1.3]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.2...v0.1.3
[0.1.4]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.3...v0.1.4
[0.1.5]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.4...v0.1.5
[0.1.6]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.5...v0.1.6
[0.1.7]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.6...v0.1.7
[0.1.8]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.7...v0.1.8
[0.1.9]: https://github.com/tox-dev/jetbrains-fish/compare/v0.1.8...v0.1.9
