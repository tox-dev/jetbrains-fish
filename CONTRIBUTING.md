# Contributing

## Development

- Run `mise install` to get the JDK pinned in `mise.toml` and checksummed in `mise.lock`.
- Run `./gradlew buildPlugin` to build the plugin.
- Run `./gradlew test` to run the tests.
- Run `./gradlew runIde` to run the IDE with the plugin installed.

## Code Style

Run `./gradlew ktlintFormat` before committing to ensure consistent formatting.

## News fragments

For a user-visible change, add `changelog.d/+short-name.<category>.md`. Use `feature` for a new capability, `change` for
changed behavior, `bugfix` for a fix, and `doc` for user documentation. `security`, `performance`, `deprecation`,
`removal`, `breaking`, and `packaging` describe other user-visible results. Give separate changes separate files. Do not
write a PR number or credit into a fragment. Release preparation links each fragment to its squash PR, credits that
commit's authors and `Co-authored-by` co-authors, then runs `release-creator contributors` to list first-time
contributors. Apply `skip news` to maintenance PRs; bot PRs without fragments receive that label automatically.

## Releasing

Run **Prepare release** on `main` with `auto` after merging the intended fragments. A `breaking` fragment selects a
major version, a `feature` fragment selects a minor version, and other categories select a patch version. Towncrier
updates `CHANGELOG.md` and `prek` formats it before the repository-scoped App pushes the release commit and `v`-prefixed
tag. The dispatch does not ask for a version number.

The tag workflow renders the notes and builds an unsigned plugin ZIP without credentials. A fresh job signs the ZIP, and
another keyless job compares its members with the
[JetBrains Marketplace](https://plugins.jetbrains.com/plugin/29973-fish-shell) version. If absent, a fresh job uploads
the signed ZIP with the Marketplace token. After the upload is accepted, GitHub attaches the original signed ZIP,
verified by SHA256, to its Release. Marketplace approval can follow later; run **Verify Marketplace release** with the
tag after the version becomes public to compare the published ZIP members. After the GitHub Release is published, the
tag run checks out `main` and opens a PR for the next development version. The App key is held by `release-auth`;
signing and Marketplace credentials are held by the tag-restricted `release` environment.

If GitHub publication fails after Marketplace accepts the ZIP, run **Finalize GitHub Release** on the original tag. Pass
the source tag run ID and the artifact IDs for `marketplace-acceptance`, `plugin-distribution`, and `release-notes` from
that run. The finalizer creates or resumes the GitHub Release. It does not upload to Marketplace. A full tag rerun
cannot repeat a pending Marketplace upload; if the upload result has no acceptance receipt, reconcile it manually with
JetBrains before continuing.

## Tests

Add a test for every new feature and bug fix. A change without a test needs a reason in the pull request description.
