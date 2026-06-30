package com.example.mycolover.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycolover.data.BeautyItem
import com.example.mycolover.data.PersonalColor
import com.example.mycolover.data.local.FavoriteItem
import com.example.mycolover.data.repository.BeautyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class BeautyViewModel @Inject constructor(
    private val repository: BeautyRepository
) : ViewModel() {

    // 검색어 상태
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    // 현재 선택된 컬러 필터
    private val _selectedColor = MutableStateFlow<PersonalColor?>(null)
    val selectedColor: StateFlow<PersonalColor?> = _selectedColor

    // 서버 아이템 리스트 (검색어와 컬러 필터가 변경될 때마다 자동 갱신)
    private val _items = MutableStateFlow<List<BeautyItem>>(emptyList())
    val items: StateFlow<List<BeautyItem>> = _items

    // 찜한 아이템 전체 목록
    val favoriteItems: StateFlow<List<FavoriteItem>> = repository.getAllFavorites()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // 찜한 아이템 ID 리스트
    val favoriteIds: StateFlow<Set<Int>> = favoriteItems
        .map { list -> list.map { it.id }.toSet() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptySet())

    // 상세 화면용 아이템 정보
    private val _selectedItem = MutableStateFlow<BeautyItem?>(null)
    val selectedItem: StateFlow<BeautyItem?> = _selectedItem

    init {
        // [현업 기술] 검색어와 필터가 바뀔 때마다 서버 요청을 보냄
        viewModelScope.launch {
            combine(_searchQuery, _selectedColor) { query, color ->
                Pair(query, color)
            }
            .debounce(500) // 0.5초 동안 입력이 멈췄을 때만 서버 호출 (서버 과부하 방지)
            .distinctUntilChanged() // 값이 실제로 바뀌었을 때만 진행
            .collect { (query, color) ->
                fetchItems(color, query)
            }
        }
    }

    fun onSearchQueryChange(query: String) {
        _searchQuery.value = query
    }

    fun onColorFilterChange(color: PersonalColor?) {
        _selectedColor.value = color
    }

    // 아이템 상세 정보 가져오기
    fun fetchItemDetail(itemId: Int) {
        viewModelScope.launch {
            // 현재 리스트에 있는 상품이라면 서버 호출 없이 바로 가져옴
            val localItem = _items.value.find { it.id == itemId }
            if (localItem != null) {
                _selectedItem.value = localItem
            } else {
                // 리스트에 없다면 (예: 딥링크 진입 등) 추후 서버에서 1개만 가져오는 API 연동 가능
            }
        }
    }

    private suspend fun fetchItems(color: PersonalColor?, query: String?) {
        try {
            _items.value = repository.getItems(color, query)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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
