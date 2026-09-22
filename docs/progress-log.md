# Progress log — ThermalCore

Append-only. Later entries correct earlier ones rather than editing them away. See
[TODO.md](TODO.md) for what's outstanding right now.

## Phase 0 — pre-existing state

This repo already had a working NeoForge **1.20.4** port before the current porting
effort started (`d6594dd` "1.20.4 Initial Port Work" and the follow-up porting/refactor
commits through `315dcf6` "1.20.4 Fixes and Refactors" and `bedbdf3` "Capability
work.") — ThermalCore's own 1.20.4 hop wasn't part of this effort; it was the starting
point, same as the other three repos.

## Phase 1 — modern tooling, verified running

Brought the existing 1.20.4 codebase onto modern tooling, matching CoFHCore's Phase 1
(`../CoFHCore/docs/progress-log.md`):
- `e355441` Wire composite build to local CoFHCore for NeoForge port.
- `e6a791a` Add dev run configs (client/server/data), Gradle heap settings, fix
  `gradlew` executable bit.
- `02fd5b4` Fix broken `javafml` loaderVersion requirement in `mods.toml` (same bug hit
  in all four repos).
- `dacdd58` Upgrade to Gradle 9.2.1 + NeoGradle userdev 7.1.38.

## Phase 2 — the primer climb

Full primer chain, decision to target 26.1.2, and CoFHCore-first dependency ordering:
see `../CoFHCore/docs/progress-log.md`'s Phase 2 entry — this repo follows the same
plan, one hop behind CoFHCore.

### 1.20.6 hop

Not yet started. `gradle.properties`/`build.gradle` are bumped locally but uncommitted,
waiting on CoFHCore. See [TODO.md](TODO.md).
