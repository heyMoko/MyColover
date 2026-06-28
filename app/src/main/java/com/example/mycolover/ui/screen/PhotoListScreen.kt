package com.example.mycolover.ui.screen

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.mycolover.ui.component.PhotoItem
import com.example.mycolover.ui.viewmodel.PhotoViewModel

/**
 * 사진 리스트 전체를 보여주는 스크린
 * ViewModel의 photos 상태를 관찰하여 리스트를 자동으로 갱신
 */
@Composable
fun PhotoListScreen(viewModel: PhotoViewModel) {
    // 뷰모델의 Flow를 Compose 상태로 변환
    val photos by viewModel.photos.collectAsState()

    LazyColumn {
        items(photos) { photo ->
            PhotoItem(photo = photo)
        }
    }
}
