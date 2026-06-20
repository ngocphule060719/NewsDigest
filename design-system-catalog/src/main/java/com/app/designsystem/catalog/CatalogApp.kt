package com.app.designsystem.catalog

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.app.designsystem.catalog.navigation.CatalogRoutes
import com.app.designsystem.catalog.navigation.catalogSections
import com.app.designsystem.catalog.screens.BadgesScreen
import com.app.designsystem.catalog.screens.ButtonsScreen
import com.app.designsystem.catalog.screens.CardsScreen
import com.app.designsystem.catalog.screens.FoundationScreen
import com.app.designsystem.catalog.screens.InputsScreen
import com.app.designsystem.catalog.screens.NavigationScreen
import com.app.designsystem.catalog.screens.NewsComponentsScreen
import com.app.designsystem.foundation.Colors
import com.app.designsystem.foundation.Spacing
import com.app.designsystem.theme.Theme

@Composable
fun CatalogApp() {
    val navController = rememberNavController()
    Theme {
        NavHost(
            navController = navController,
            startDestination = CatalogRoutes.HOME,
        ) {
            composable(CatalogRoutes.HOME) {
                CatalogHomeScreen(
                    onSectionClick = { route ->
                        navController.navigate(route)
                    },
                )
            }
            composable(CatalogRoutes.FOUNDATION) {
                CatalogDetailScaffold(title = "Foundation", navController = navController) {
                    FoundationScreen()
                }
            }
            composable(CatalogRoutes.BUTTONS) {
                CatalogDetailScaffold(title = "Buttons", navController = navController) {
                    ButtonsScreen()
                }
            }
            composable(CatalogRoutes.INPUTS) {
                CatalogDetailScaffold(title = "Inputs", navController = navController) {
                    InputsScreen()
                }
            }
            composable(CatalogRoutes.BADGES) {
                CatalogDetailScaffold(title = "Badges", navController = navController) {
                    BadgesScreen()
                }
            }
            composable(CatalogRoutes.CARDS) {
                CatalogDetailScaffold(title = "Cards", navController = navController) {
                    CardsScreen()
                }
            }
            composable(CatalogRoutes.NAVIGATION) {
                CatalogDetailScaffold(title = "Navigation", navController = navController) {
                    NavigationScreen()
                }
            }
            composable(CatalogRoutes.NEWS) {
                CatalogDetailScaffold(title = "News", navController = navController) {
                    NewsComponentsScreen()
                }
            }
        }
    }
}

@Composable
private fun CatalogHomeScreen(
    onSectionClick: (String) -> Unit,
) {
    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            item {
                CatalogTopBar(title = "Design System Catalog")
                Text(
                    text = "Storybook-style gallery for design tokens and components.",
                    style = Theme.typography.bodyMd,
                    color = Colors.Steel,
                    modifier = Modifier.padding(
                        horizontal = Spacing.Base,
                        vertical = Spacing.Sm,
                    ),
                )
            }
            items(catalogSections) { section ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSectionClick(section.route) }
                        .padding(horizontal = Spacing.Base, vertical = Spacing.Base),
                ) {
                    Text(
                        text = section.title,
                        style = Theme.typography.headingSm,
                        color = Colors.InkDeep,
                    )
                    Text(
                        text = section.description,
                        style = Theme.typography.bodySm,
                        color = Colors.Steel,
                        modifier = Modifier.padding(top = Spacing.Xxs),
                    )
                }
                HorizontalDivider(color = Colors.Hairline)
            }
        }
    }
}

@Composable
private fun CatalogDetailScaffold(
    title: String,
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    Scaffold { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
        ) {
            CatalogTopBar(
                title = title,
                onBackClick = { navController.popBackStack() },
            )
            content()
        }
    }
}
