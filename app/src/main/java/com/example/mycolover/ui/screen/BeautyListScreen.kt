package com.example.mycolover.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mycolover.data.PersonalColor
import com.example.mycolover.ui.component.BeautyItemCard
import com.example.mycolover.ui.viewmodel.BeautyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeautyListScreen(
    viewModel: BeautyViewModel,
    onItemClick: (Int) -> Unit
) {
    val items by viewModel.items.collectAsState()
    val selectedColor by viewModel.selectedColor.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    
    // 찜한 아이템 ID 목록 관찰
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("MyColover 뷰티 추천") })

        // 🔍 검색창
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { viewModel.onSearchQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            placeholder = { Text("제품명 또는 브랜드를 검색하세요") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            singleLine = true,
            shape = MaterialTheme.shapes.medium
        )
        
        // 컬러 필터 칩 리스트
        LazyRow(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            item {
                FilterChip(
                    selected = selectedColor == null,
                    onClick = { viewModel.onColorFilterChange(null) },
                    label = { Text("전체") }
                )
            }
            items(PersonalColor.entries) { color ->
                FilterChip(
                    selected = selectedColor == color,
                    onClick = { viewModel.onColorFilterChange(color) },
                    label = { Text(color.displayName) }
                )
            }
        }

        // 아이템 리스트
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(bottom = 16.dp)
        ) {
            items(items) { item ->
                BeautyItemCard(
                    item = item,
                    isFavorite = favoriteIds.contains(item.id),
                    onFavoriteClick = { viewModel.toggleFavorite(item) },
                    onItemClick = { onItemClick(item.id) }
                )
            }
        }
    }
}
