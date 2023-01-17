package com.rmaproject.myqoran.ui

import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rmaproject.myqoran.components.MyQoranDrawer
import com.rmaproject.myqoran.ui.navigation.MyQoranNavigationActions
import com.rmaproject.myqoran.ui.navigation.Screen
import com.rmaproject.myqoran.ui.screen.home.HomeScreen
import com.rmaproject.myqoran.ui.screen.read.ReadQoranScreen
import com.rmaproject.myqoran.ui.screen.settings.SettingsScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyQoranApp(
    navController: NavHostController = rememberNavController(),
    scope: CoroutineScope = rememberCoroutineScope()
) {
    val navActions = remember(navController) {
        MyQoranNavigationActions(navController)
    }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route ?: Screen.Home.route
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            MyQoranDrawer(
                currentRoute = currentRoute,
                closeDrawer = { scope.launch { drawerState.close() } },
                navigateToHome = navActions.navigateToHome,
                navigateToSettings = navActions.navigateToSettings
            )
        },
        drawerState = drawerState,
        gesturesEnabled = currentRoute != Screen.ReadQoran.route
    ) {
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToReadQoran = { surahNumber, juzNumber, pageNumber ->
                        navController.navigate(
                            Screen.ReadQoran.createRoute(
                                surahNumber,
                                juzNumber,
                                pageNumber
                            )
                        )
                    },
                    navigateToSearch = {},
                    openDrawer = { scope.launch { drawerState.open() } }
                )
            }
            composable(
                route = Screen.ReadQoran.route,
                arguments = listOf(
                    navArgument("surahNumber") {
                        type = NavType.IntType
                        defaultValue = 1
                    },
                    navArgument("juzNumber") {
                        type = NavType.IntType
                        defaultValue = 1
                    },
                    navArgument("pageNumber") {
                        type = NavType.IntType
                        defaultValue = 1
                    }
                )
            ) {
                ReadQoranScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}

@Preview
@Composable
fun MyQoranAppPreview() {
    MyQoranApp()
}