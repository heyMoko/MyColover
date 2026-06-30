package com.example.mycolover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.mycolover.data.PersonalColor
import com.example.mycolover.ui.screen.BeautyDetailScreen
import com.example.mycolover.ui.screen.BeautyListScreen
import com.example.mycolover.ui.screen.FavoriteListScreen
import com.example.mycolover.ui.screen.Screen
import com.example.mycolover.ui.screen.SelfTestScreen
import com.example.mycolover.ui.theme.MyColoverTheme
import com.example.mycolover.ui.viewmodel.BeautyViewModel
import com.example.mycolover.ui.viewmodel.SelfTestViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val beautyViewModel: BeautyViewModel by viewModels()
    private val selfTestViewModel: SelfTestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyColoverTheme {
                val navController = rememberNavController()
                val items = listOf(Screen.Home, Screen.SelfTest, Screen.Favorites)

                Scaffold(
                    bottomBar = {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.destination?.route
                        
                        // 상세 화면이 아닐 때만 바텀바 표시
                        if (items.any { it.route == currentRoute }) {
                            NavigationBar {
                                val currentDestination = navBackStackEntry?.destination
                                items.forEach { screen ->
                                    NavigationBarItem(
                                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                                        label = { Text(screen.title) },
                                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                        onClick = {
                                            navController.navigate(screen.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    )
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(bottom = innerPadding.calculateBottomPadding())
                    ) {
                        composable(Screen.Home.route) {
                            BeautyListScreen(
                                viewModel = beautyViewModel,
                                onItemClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
                            )
                        }
                        composable(Screen.SelfTest.route) {
                            SelfTestScreen(
                                viewModel = selfTestViewModel,
                                onResultClick = { colorName ->
                                    // 진단 결과를 뷰모델에 반영하고 홈으로 이동
                                    val color = PersonalColor.valueOf(colorName)
                                    beautyViewModel.onColorFilterChange(color)
                                    navController.navigate(Screen.Home.route) {
                                        popUpTo(Screen.SelfTest.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Favorites.route) {
                            FavoriteListScreen(
                                viewModel = beautyViewModel,
                                onItemClick = { id -> navController.navigate(Screen.Detail.createRoute(id)) }
                            )
                        }
                        composable(
                            route = Screen.Detail.route,
                            arguments = listOf(navArgument("itemId") { type = NavType.IntType })
                        ) { backStackEntry ->
                            val itemId = backStackEntry.arguments?.getInt("itemId") ?: return@composable
                            BeautyDetailScreen(
                                itemId = itemId,
                                viewModel = beautyViewModel,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}
