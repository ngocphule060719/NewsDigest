package com.app.designsystem.catalog.navigation

object CatalogRoutes {
    const val HOME = "home"
    const val FOUNDATION = "foundation"
    const val BUTTONS = "buttons"
    const val INPUTS = "inputs"
    const val BADGES = "badges"
    const val CARDS = "cards"
    const val NAVIGATION = "navigation"
    const val NEWS = "news"
}

data class CatalogSection(
    val route: String,
    val title: String,
    val description: String,
)

val catalogSections = listOf(
    CatalogSection(
        route = CatalogRoutes.FOUNDATION,
        title = "Foundation",
        description = "Color swatches, type ramp, spacing and shape rulers",
    ),
    CatalogSection(
        route = CatalogRoutes.BUTTONS,
        title = "Buttons",
        description = "Primary, buy CTA, secondary, ghost, pill tabs, icon buttons",
    ),
    CatalogSection(
        route = CatalogRoutes.INPUTS,
        title = "Inputs",
        description = "Text fields, search pill, radio options",
    ),
    CatalogSection(
        route = CatalogRoutes.BADGES,
        title = "Badges",
        description = "Promo, attention, success, and critical badges",
    ),
    CatalogSection(
        route = CatalogRoutes.CARDS,
        title = "Cards",
        description = "Product feature and icon feature cards",
    ),
    CatalogSection(
        route = CatalogRoutes.NAVIGATION,
        title = "Navigation",
        description = "Top nav bar, breadcrumb, promo banner",
    ),
    CatalogSection(
        route = CatalogRoutes.NEWS,
        title = "News",
        description = "ArticleCard, ErrorState, LoadingState",
    ),
)
