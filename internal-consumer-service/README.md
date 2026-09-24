# internal-consumer-service (fixture module)

Not a real service — this module exists purely so `tech-stack-advisor` has
a realistic internal parent-POM relationship to detect. Its `pom.xml`
`<parent>` references `com.example:sample-legacy-app-parent-pom`, a
separate real GitHub repo (`manikantan-24/sample-legacy-app-parent-pom`)
rather than a public Maven Central artifact.

To see the detection fire, scan `sample-legacy-app` and
`sample-legacy-app-parent-pom` together in the same batch — the report's
`internal_repo_dependencies` should show `sample-legacy-app-parent-pom`
with `is_parent_pom: true`, consumed by `sample-legacy-app`.

This is also the fixture that caught a real bug in `impact_analyzer.py`:
the matching logic originally compared the full `groupId:artifactId`
string against scanned repo names, which can never match since a GitHub
repo name can't contain a colon. Fixed to match on the artifactId alone
for Maven dependencies — see `tech-stack-advisor/SPEC.md`'s changelog.
