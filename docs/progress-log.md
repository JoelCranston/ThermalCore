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

---

## Phase A complete — 1.21.1 (2026-09-22)

575 errors on the first real compile → 0, in seventeen commits, one per root cause, each with
its before/after count. Order: `c:` tag constants → event bus and spawn placements → recipe
lookups → ItemStack NBT to components → `HolderLookup.Provider` threading → enchantment
components → recipe serializers on MapCodec/StreamCodec → synched data and mobs → armour →
vertex API → potions/enchantments/food → projectiles and dispensing → Patchouli → datagen
registries → brewing and block hooks → recipe/loot JSON.

Three places the translation was not mechanical:

- **Armour.** `ArmorMaterial` is a registered record and `ArmorItem` takes a `Holder`, so the
  three suits moved into a `DeferredRegisterCoFH`; durability left the material for the item's
  `MAX_DAMAGE` component, and the diving suit's swim-speed modifier became part of its
  attribute component (its deferred `setup()` pass is gone).
- **Brewing.** `PotionBrewing.POTION_MIXES` is gone and a server's mixes are not enumerable, so
  the Imbuer's default conversions are now *discovered* — mix each potion against each item the
  running `PotionBrewing` accepts as an ingredient.
- **Frost Walker.** A datapack enchantment effect with no class to call; the Blizz reproduces
  the level-1 `replace_disk` disk inline.

### Caught only by booting

The first headless run logged **198 recipe parse failures**. Fixing the result key (`item` →
`id`) left 30; the round trip was over ingredients — a first pass converted them to the
bare-string form, which is **1.21.2, not 1.21.1**, and had to be reverted wholesale.
`minecraft:looting_enchant` also became `minecraft:enchanted_count_increase`, naming the
enchantment explicitly.

Then every CoFH item threw when the Stirling dynamo enumerated furnace fuels: NeoForge throws on
a negative burn time now, and CoFH's `-1` "no opinion" default has to fall through to the
`neoforge:furnace_fuels` data map. Fixed in CoFHCore.

### Owed

The client pass, and `runData` (the committed generated output was hand-migrated). Shapes are in
`../CoFHCore/docs/api-notes-1.21.1.md`.
