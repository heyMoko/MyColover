package com.example.mycolover.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycolover.data.Photo
import com.example.mycolover.data.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * UI 상태를 관리하는 ViewModel
 * UI는 오직 이 ViewModel의 'photos' 상태만 관찰함
 */
@HiltViewModel
class PhotoViewModel @Inject constructor(
    private val repository: PhotoRepository
) : ViewModel() {

    // 내부에서 변경 가능한 상태
    private val _photos = MutableStateFlow<List<Photo>>(emptyList())
    // 외부(UI)에 노출되는 읽기 전용 상태
    val photos: StateFlow<List<Photo>> = _photos

    init {
        fetchPhotos()
    }

    private fun fetchPhotos() {
        viewModelScope.launch {
            try {
                // 첫 번째 페이지에서 30개만 가져오기 (테스트용)
                val result = repository.getPhotos(page = 1, limit = 30)
                _photos.value = result
            } catch (e: Exception) {
                // TODO: 에러 처리 UI 추가 예정
                e.printStackTrace()
            }
        }
    }
}
