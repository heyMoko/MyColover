package com.example.mycolover

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.mycolover.ui.screen.BeautyListScreen
import com.example.mycolover.ui.theme.MyColoverTheme
import com.example.mycolover.ui.viewmodel.BeautyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    // Hilt를 사용하여 BeautyViewModel 주입
    private val viewModel: BeautyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyColoverTheme {
                // 진정한 뷰티 리스트 화면으로 교체
                BeautyListScreen(viewModel = viewModel)
            }
        }
    }
}
