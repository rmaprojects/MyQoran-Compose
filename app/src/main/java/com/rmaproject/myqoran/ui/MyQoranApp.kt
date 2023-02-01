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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rmaproject.myqoran.components.MyQoranDrawer
import com.rmaproject.myqoran.ui.navigation.MyQoranNavigationActions
import com.rmaproject.myqoran.ui.navigation.MyQoranSharedViewModel
import com.rmaproject.myqoran.ui.navigation.Screen
import com.rmaproject.myqoran.ui.screen.adzanschedule.AdzanScheduleScreen
import com.rmaproject.myqoran.ui.screen.bookmark.BookmarkScreen
import com.rmaproject.myqoran.ui.screen.findqibla.FindQiblaScreen
import com.rmaproject.myqoran.ui.screen.home.HomeScreen
import com.rmaproject.myqoran.ui.screen.read.ReadQoranScreen
import com.rmaproject.myqoran.ui.screen.search.SearchAyahScreen
import com.rmaproject.myqoran.ui.screen.search.SearchSurahScreen
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
    val totalAyahSharedViewModel: MyQoranSharedViewModel = viewModel()

    ModalNavigationDrawer(
        drawerContent = {
            MyQoranDrawer(
                currentRoute = currentRoute,
                closeDrawer = { scope.launch { drawerState.close() } },
                navigateToHome = navActions.navigateToHome,
                navigateToSettings = navActions.navigateToSettings
            )
        }, drawerState = drawerState, gesturesEnabled = currentRoute != Screen.ReadQoran.route
    ) {
        NavHost(
            navController = navController, startDestination = Screen.Home.route
        ) {
            composable(Screen.Settings.route) {
                SettingsScreen(openDrawer = { scope.launch { drawerState.open() } })
            }
            composable(Screen.AdzanSchedule.route) {
                AdzanScheduleScreen()
            }
            composable(Screen.FindQibla.route) {
                FindQiblaScreen()
            }
            composable(Screen.SearchAyah.route) {
                SearchAyahScreen(
                    navigateUp = { navController.navigateUp() }
                )
            }
            composable(Screen.SearchSurah.route) {
                SearchSurahScreen(
                    navigateUp = { navController.navigateUp() },
                    navigateToReadQoran = { indexType, surahNumber, juzNumber, pageNumber ->
                        navController.navigate(
                            Screen.ReadQoran.createRoute(
                                indexType, surahNumber, juzNumber, pageNumber, null
                            )
                        )
                    },
                )
            }
            composable(Screen.Bookmarks.route) {
                BookmarkScreen(navigateUp = { navController.navigateUp() },
                    navigateToRead = { indexType: Int, surahNumber: Int?, juzNumber: Int?, pageNumber: Int?, scrollPosition: Int? ->
                        navController.navigate(
                            Screen.ReadQoran.createRoute(
                                indexType, surahNumber, juzNumber, pageNumber, scrollPosition
                            )
                        )
                    })
            }
            composable(route = Screen.ReadQoran.route, arguments = listOf(navArgument("indexType") {
                type = NavType.IntType
                defaultValue = 1
            }, navArgument("surahNumber") {
                type = NavType.IntType
                defaultValue = 1
            }, navArgument("juzNumber") {
                type = NavType.IntType
                defaultValue = 1
            }, navArgument("pageNumber") {
                type = NavType.IntType
                defaultValue = 1
            }, navArgument("scrollPosition") {
                type = NavType.IntType
                defaultValue = 0
            })) {
                ReadQoranScreen(
                    navigateUp = { navController.navigateUp() },
                    navigateToSearchAyah = { navController.navigate(Screen.SearchAyah.route) },
                    sharedViewModel = totalAyahSharedViewModel,
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToReadQoran = { indexType, surahNumber, juzNumber, pageNumber ->
                        navController.navigate(
                            Screen.ReadQoran.createRoute(
                                indexType, surahNumber, juzNumber, pageNumber, null
                            )
                        )
                    },
                    navigateToSearch = { navController.navigate(Screen.SearchSurah.route) },
                    navigateToBookmark = { navController.navigate(Screen.Bookmarks.route) },
                    openDrawer = { scope.launch { drawerState.open() } },
                    sharedViewModel = totalAyahSharedViewModel
                )
            }
        }
    }
}

@Preview
@Composable
fun MyQoranAppPreview() {
    MyQoranApp()
}