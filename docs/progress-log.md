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

## Phase 2, revised (2026-09-21)

The route is now 1.21.1 → 26.1.2 with no other intermediates, ModDevGradle from Phase 0, and
the 1.20.6 hop abandoned before this repo ever started it — see `../CoFHCore/docs/port-plan.md`
and the matching entry in `../CoFHCore/docs/progress-log.md` for what re-verification changed.
Branch `1.21.1` created today. `../ThermalCoreForNeoForge` (SPLIGAN's 1.21.1 port of this repo) is the Phase A
worklist; `../Pyronetics` is the 26.1.2 reference. The uncommitted 1.20.6 build bump is left
uncommitted on purpose.
