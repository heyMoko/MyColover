package com.example.mycolover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.mycolover.ui.screen.PhotoListScreen
import com.example.mycolover.ui.theme.MyColoverTheme
import com.example.mycolover.ui.viewmodel.PhotoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    // Hilt를 사용하여 ViewModel 주입
    private val viewModel: PhotoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyColoverTheme {
                // 앱의 진입점에서 사진 리스트 화면을 표시
                PhotoListScreen(viewModel = viewModel)
            }
        }
    }
}
