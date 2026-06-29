package com.example.mycolover.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycolover.data.BeautyItem
import com.example.mycolover.data.PersonalColor
import com.example.mycolover.data.local.FavoriteItem
import com.example.mycolover.data.repository.BeautyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeautyViewModel @Inject constructor(
    private val repository: BeautyRepository
) : ViewModel() {

    // 서버 아이템 리스트
    private val _items = MutableStateFlow<List<BeautyItem>>(emptyList())
    val items: StateFlow<List<BeautyItem>> = _items

    // 현재 선택된 컬러 필터
    private val _selectedColor = MutableStateFlow<PersonalColor?>(null)
    val selectedColor: StateFlow<PersonalColor?> = _selectedColor

    // 찜한 아이템 전체 목록
    val favoriteItems: StateFlow<List<FavoriteItem>> = repository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 찜한 아이템 ID 리스트 (UI에서 하트 색상을 결정하기 위함)
    val favoriteIds: StateFlow<Set<Int>> = favoriteItems
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    init {
        fetchItems()
    }

    fun fetchItems(color: PersonalColor? = null) {
        _selectedColor.value = color
        viewModelScope.launch {
            try {
                _items.value = repository.getItemsByColor(color)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 찜하기 토글 (이미 있으면 삭제, 없으면 추가)
    fun toggleFavorite(item: BeautyItem) {
        viewModelScope.launch {
            val isFav = repository.isFavorite(item.id)
            if (isFav) {
                repository.removeFavorite(
                    FavoriteItem(item.id, item.name, item.brand, item.imageUrl, item.category, item.personalColor)
                )
            } else {
                repository.addFavorite(
                    FavoriteItem(item.id, item.name, item.brand, item.imageUrl, item.category, item.personalColor)
                )
            }
        }
    }
}
