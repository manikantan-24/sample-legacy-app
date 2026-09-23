# legacy-service (Java / Spring Boot 2.6)

A deliberately outdated Spring Boot service, added as a real Java target for
`tech-stack-advisor`'s Part B (OpenRewrite) pipeline and the Part C autonomous
agent — the other fixtures in this repo family (`sample-legacy-app`,
`sample-dashboard-app`, `sample-notification-service`) are all Python/JS, so
none of them could actually exercise the Java-specific recipes.

## What's outdated on purpose

| File | Pattern | Recipe that fixes it |
|---|---|---|
| `pom.xml` | `spring-boot-starter-parent` pinned to `2.6.7` | `UpgradeSpringBoot_2_7`, then `UpgradeSpringBoot_3_0` |
| `User.java` | `javax.persistence.*` imports | `UpgradeSpringBoot_3_0` (pulls in the javax→jakarta migration automatically), or `JavaxMigrationToJakarta` standalone |
| `SecurityConfig.java` | `WebSecurityConfigurerAdapter` (removed in Spring Security 6) + `.authorizeRequests()` (renamed `.authorizeHttpRequests()`) | `UpgradeSpringBoot_3_0` |

This is intentionally the exact recipe matrix documented in `tech-stack-advisor/SPEC.md` Part B — this app exists to actually run those workflows against, not just describe them.

## Note on this fixture

This module was authored without a local JDK/Maven to compile-check it against (not available in the environment it was written in) — review it like you would any other code before running the real pipeline against it, particularly the Spring Security config shape.
