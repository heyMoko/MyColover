package com.example.mycolover.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycolover.data.BeautyItem
import com.example.mycolover.data.PersonalColor
import com.example.mycolover.data.repository.BeautyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BeautyViewModel @Inject constructor(
    private val repository: BeautyRepository
) : ViewModel() {

    private val _items = MutableStateFlow<List<BeautyItem>>(emptyList())
    val items: StateFlow<List<BeautyItem>> = _items

    // 현재 선택된 컬러 필터
    private val _selectedColor = MutableStateFlow<PersonalColor?>(null)
    val selectedColor: StateFlow<PersonalColor?> = _selectedColor

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
}
