package com.example.mycolover.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * 앱의 화면 경로를 정의하는 클래스
 */
sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("home", "홈", Icons.Default.Home)
    object SelfTest : Screen("self_test", "진단", Icons.Default.CheckCircle)
    object Favorites : Screen("favorites", "찜", Icons.Default.Favorite)
    object Detail : Screen("detail/{itemId}", "상세", Icons.Default.Home) {
        fun createRoute(itemId: Int) = "detail/$itemId"
    }
}
