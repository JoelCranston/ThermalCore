# TODO — ThermalCore

Current, outstanding work only. See [progress-log.md](progress-log.md) for what's
already done and why.

## Next up

The plan for all four repos is `../CoFHCore/docs/port-plan.md`; this repo's steps are §4 (Phase 0,
per repo), §5 A.2/A.3 (1.21.1) and §6 B.10 (26.1.2).

Phase B (26.1.2) is done for this repo on branch `26.1.2` (2026-09-22): 0 errors, `runData` matches the
committed output, and the dedicated server boots to `Done` with 1760 recipes and no data errors. The
shapes, decisions and forced behaviour changes are in `../CoFHCore/docs/api-notes-26.1.2.md` ("B.10
ThermalCore") and `../CoFHCore/docs/TODO.md` (Inbox, "B.10 ThermalCore behaviour changes"). This repo
builds against whatever branch `../CoFHCore` has checked out; both are on `26.1.2` now.

1. **Joel's `runClient` pass** (port plan §A.4 / §B.10) — everything client-side is unverified on both
   hops: the six baked models and their item forms, the fluid models and fog, the entity renderers,
   the screens, the armour equipment assets, the `range_dispatch` item definitions.
2. **Florbs show no fluid tint** until CoFH's fluid-container items expose `Capabilities.Fluid.ITEM`
   (CoFHCore TODO, "After the port", item 1).

## Inbox

- ~~24 `c:` tags are referenced but defined by nobody~~ **emitted empty on 26.1.2** (a missing tag is a
  hard recipe error there); decide per tag whether to keep the compat recipe. Originally: Not a NeoForge convention, not emitted
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
- ~~`data/thermal/recipe/tools/guidebook.json` is still on the pre-1.20.5 recipe shape~~ fixed on 26.1.2
  (`components` + string ingredients). Originally: — a
  `"nbt"` string in `result` and `{"item": …}` ingredients. Needs `components` /
  `DataComponents` when recipes are done (A.2). Its condition type was fixed to
  `neoforge:mod_loaded` during the sweep (it was `forge:mod_loaded` — a condition, not a tag,
  so it is `neoforge:`, not `c:`).
- **`settings.gradle` dropped a `mixingradle` `resolutionStrategy` and the Sponge/Parchment
  plugin repos.** Neither plugin was applied and this repo has no mixins — re-add if that
  changes.
