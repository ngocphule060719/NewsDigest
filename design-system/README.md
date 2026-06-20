# `:design-system`

Android library (`com.app.designsystem`) that owns design tokens, `Theme`, reusable Compose
components, and News Digest UI primitives. Source of truth for visual specs: [
`sample/DESIGN-meta.md`](../sample/DESIGN-meta.md).

## Dependency rule

- **`:app` → `:design-system`** — presentation screens import `com.app.designsystem.*` directly.
- **Not wired through `:appDependencies`** — this is UI infrastructure (like Compose), not DI/data
  wiring.
- **`:design-system` must not depend on any other project module** — Compose BOM only.

```kotlin
// app/build.gradle.kts
dependencies {
    implementation(project(":design-system"))
}
```

Apply `Theme` once at the NavHost root in `app/presentation/navigation/` (Phase 4+).

## Foundation tokens

All tokens map YAML names from `DESIGN-meta.md` to typed Kotlin objects under `foundation/`.

| Object       | Source (`DESIGN-meta.md`) | Notes                                                         |
|--------------|---------------------------|---------------------------------------------------------------|
| `Colors`     | `colors.*`                | 20+ brand, semantic, surface, and text colors                 |
| `Typography` | `typography.*`            | Full scale from `heroDisplay` (64sp) through `caption` (12sp) |
| `Spacing`    | `spacing.*`               | `Xxs` (4dp) through `Hero` (120dp)                            |
| `Shapes`     | `rounded.*`               | `Xs` (2dp) through `Full` (100dp pill) and `Circle`           |
| `Elevation`  | elevation levels 0–2      | Flat cards; level-2 shadow for sticky panels only             |

### Typography & fonts

Source spec uses **Optimistic VF**. This module uses the documented fallback stack:

**Montserrat → Helvetica → Arial → Noto Sans** (`res/font/` + system sans-serif).

OpenType `ss01` / `ss02` from the source are **not** applied — unavailable on Montserrat and system
fallbacks.

### Key fidelity rules

- **Cobalt vs black CTA:** `BuyCtaButton` = `#0064E0` (`primary`); `PrimaryButton` = black (
  `ink-button`) — never swap.
- **Pill shape:** buttons, badges, and tabs use `Shapes.Full` (100dp radius).
- **Flat elevation:** marketing cards have no shadow; level-2 shadow only for sticky panels.
- **Light mode only** in this phase.

Access typography via `Theme.typography.*` inside `Theme { }`.

## Components

Stateless composables — parameters and `onXxx` callbacks only; no ViewModel or repository types.

### Buttons (`component/button/`)

| Composable           | Meta source            | States                     |
|----------------------|------------------------|----------------------------|
| `PrimaryButton`      | `button-primary`       | default, pressed, disabled |
| `BuyCtaButton`       | `button-buy-cta`       | default, pressed           |
| `SecondaryButton`    | `button-secondary`     | default                    |
| `GhostButton`        | `button-ghost`         | default                    |
| `PillTab`            | `button-pill-tab`      | inactive, active           |
| `IconCircularButton` | `button-icon-circular` | default                    |

### Inputs (`component/input/`)

| Composable    | Meta source    | States                  |
|---------------|----------------|-------------------------|
| `TextField`   | `text-input`   | default, focused, error |
| `SearchPill`  | `search-pill`  | default                 |
| `RadioOption` | `radio-option` | unselected, selected    |

### Badges (`component/badge/`)

`BadgePromo`, `BadgeAttention`, `BadgeSuccess`, `BadgeCritical`

### Cards (`component/card/`)

`ProductFeatureCard`, `IconFeatureCard`

### Navigation (`component/navigation/`)

`TopNavBar`, `Breadcrumb`, `PromoBanner`

### News primitives (`component/news/`)

| Composable     | Purpose                                                                           |
|----------------|-----------------------------------------------------------------------------------|
| `ArticleCard`  | Thumbnail + title + source/date + bookmark toggle; skeleton and bookmarked states |
| `ErrorState`   | Message + optional retry (`PrimaryButton` / `SecondaryButton`)                    |
| `LoadingState` | Full-screen, inline, and `ArticleCard` skeleton variants                          |

## Catalog app (`:design-system-catalog`)

Standalone gallery for visual QA — **not shipped** with production `:app`.

```bash
./gradlew :design-system-catalog:installDebug
```

Screens: Foundation (colors, type ramp, spacing/shape rulers), Buttons, Inputs, Badges, Cards,
Navigation, News Components. Entire catalog wrapped in `Theme`.

Application ID: `com.app.designsystem.catalog`

## Tests

| Area                    | Location                           | Status                                                        |
|-------------------------|------------------------------------|---------------------------------------------------------------|
| Foundation token values | `src/test/.../foundation/*Test.kt` | Unit tests for colors, typography, spacing, shapes, elevation |
| Component UI tests      | —                                  | **Not yet implemented**                                       |
| Catalog                 | manual                             | Visual QA via catalog app                                     |

Run unit tests:

```bash
./gradlew :design-system:testDebugUnitTest
```

## Known gaps

- **Dark mode** — light `ColorScheme` only
- **Animation timings** — not tokenized
- **E-commerce components** — deferred (`product-gallery-pdp`, `color-sku-picker-row`,
  `hero-band-marketing`, etc.; see [`_plans/design_system_plan.md`](../_plans/design_system_plan.md)
  §7)
- **Compose UI tests** for button/input states — planned, not yet added
- **`Theme` in `:app`** — Gradle dependency wired; NavHost root wrapping deferred until Phase 4 (
  Article List)
- **`DesignSystem.kt`** — placeholder object; module entry is package-level composables/tokens

## Related docs

- Plan: [`_plans/design_system_plan.md`](../_plans/design_system_plan.md)
- Architecture: [`_plans/overall_plan.md`](../_plans/overall_plan.md) §2, §5
- Task tracker: [`_plans/design_system_tasks.md`](../_plans/design_system_tasks.md)
