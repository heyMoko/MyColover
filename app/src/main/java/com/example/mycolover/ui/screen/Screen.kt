package com.example.mycolover.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 앱의 화면 경로를 정의하는 클래스
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "홈", Icons.Filled.Home)
    object Favorites : Screen("favorites", "찜", Icons.Filled.Favorite)
}
