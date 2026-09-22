# TODO — ThermalCore

Current, outstanding work only. See [progress-log.md](progress-log.md) for what's
already done and why.

## Next up

The plan for all four repos is `../CoFHCore/docs/port-plan.md`; this repo's steps are §4 (Phase 0,
per repo), §5 A.2/A.3 (1.21.1) and §6 B.10 (26.1.2).

1. **Blocked on CoFHCore's Phase A** (`../CoFHCore/docs/TODO.md`). Nothing here compiles
   independently of it.
2. ~~Phase 0.3 (ModDevGradle 2.0.147), 0.4 (`neoforge.mods.toml`), A.0 (1.21.1 values)~~ —
   **done 2026-09-22**. Gradle configures; `createMinecraftArtifacts` succeeds; the build stops
   at `:CoFHCore:compileJava`, which is the expected blocked state. This repo's own
   `accesstransformer.cfg` was replaced with CoFHCore's (A.1 item 14) — MDG's
   `validateAccessTransformers` was rejecting 13 stale 1.20.x targets and failing
   `:createMinecraftArtifacts` outright.
3. ~~Resources sweep (§5 A.1.15)~~ — **done 2026-09-22**, plus a follow-up commit correcting the
   six `c:` tags NeoForge renamed in the plural (`c:glass` → `c:glass_blocks`, `sand` → `sands`,
   `string` → `strings`, `leather` → `leathers`, `gunpowder` → `gunpowders`,
   `obsidian` → `obsidians`). See the Inbox for what it turned up.
4. When unblocked: `diff -ru src ../ThermalCoreForNeoForge/src` and apply the 1.21.1 hunks;
   compile for a baseline count; fix what remains by category, reusing CoFHCore's confirmed
   shapes (`../CoFHCore/docs/api-notes-1.21.1.md`) rather than re-deriving them.
5. `../Pyronetics/scripts/verify_runserver.sh` passes; Joel's `runClient` check; commit.

## Inbox

- **24 `c:` tags are referenced but defined by nobody.** Not a NeoForge convention, not emitted
  by this repo's datagen: `c:dusts/{lead,silver,tin}`,
  `c:gears/{bronze,constantan,electrum,lead,nickel,silver,tin}`,
  `c:ingots/{electrum,invar,lead,silver,tin}`, `c:nuggets/{lead,tin}`,
  `c:plates/{constantan,electrum,invar,lead,silver}`, `c:gems/{ruby,sapphire}`. These are the
  alloys and metals Thermal Foundation used to add and other mods may still provide — the
  recipes using them are compat recipes and were already dead without those mods on 1.20.4, so
  this is pre-existing, not something the sweep caused. Decide per tag whether to define it
  under `data/c/tags/item/` or drop the recipe. (The mod's own `c:slag`, `c:tar`, `c:bitumen`,
  `c:coal_coke`, `c:rosin`, `c:sawdust`, `c:coins/*`, `c:plates/*`, `c:gears/*` etc. *are*
  emitted under `src/main/generated/data/c/tags/` — those are fine.)
- **When adding or checking a `c:` tag, use two oracles, not memory**: `Tags.java` in
  `neoforge-21.1.251-sources.jar` for the constants, and the `data/c/tags/**.json` that
  `neoforge-21.1.251.jar` itself ships for what is actually populated. Some conventions are not
  literal `tag("...")` calls — `c:dyes/<colour>` and `c:dyed/<colour>` come from NeoForge's
  `DyeColor` patch — so grepping `Tags.java` alone gives false "does not exist" answers.
  `c:slimeballs` still works but is deprecated in favour of `c:slime_balls`.
- **`data/thermal/recipe/tools/guidebook.json` is still on the pre-1.20.5 recipe shape** — a
  `"nbt"` string in `result` and `{"item": …}` ingredients. Needs `components` /
  `DataComponents` when recipes are done (A.2). Its condition type was fixed to
  `neoforge:mod_loaded` during the sweep (it was `forge:mod_loaded` — a condition, not a tag,
  so it is `neoforge:`, not `c:`).
- **`settings.gradle` dropped a `mixingradle` `resolutionStrategy` and the Sponge/Parchment
  plugin repos.** Neither plugin was applied and this repo has no mixins — re-add if that
  changes.
