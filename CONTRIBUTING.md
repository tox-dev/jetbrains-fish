# Contributing

## Development

- Run `mise install` to get the JDK pinned in `mise.toml` and checksummed in `mise.lock`.
- Run `./gradlew buildPlugin` to build the plugin.
- Run `./gradlew test` to run the tests.
- Run `./gradlew runIde` to run the IDE with the plugin installed.

## Code Style

Run `./gradlew ktlintFormat` before committing to ensure consistent formatting.
