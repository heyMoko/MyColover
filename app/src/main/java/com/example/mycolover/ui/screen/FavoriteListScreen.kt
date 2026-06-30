package com.example.mycolover.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.mycolover.data.BeautyItem
import com.example.mycolover.ui.component.BeautyItemCard
import com.example.mycolover.ui.viewmodel.BeautyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteListScreen(
    viewModel: BeautyViewModel,
    onItemClick: (Int) -> Unit
) {
    val favoriteItems by viewModel.favoriteItems.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        TopAppBar(title = { Text("나의 찜 목록") })
        
        if (favoriteItems.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "아직 찜한 상품이 없어요.", style = MaterialTheme.typography.bodyLarge)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(favoriteItems) { item ->
                    val beautyItem = BeautyItem(
                        id = item.id,
                        name = item.name,
                        brand = item.brand,
                        imageUrl = item.imageUrl,
                        category = item.category,
                        personalColor = item.personalColor
                    )
                    
                    BeautyItemCard(
                        item = beautyItem,
                        isFavorite = true,
                        onFavoriteClick = { viewModel.toggleFavorite(beautyItem) },
                        onItemClick = { onItemClick(item.id) }
                    )
                }
            }
        }
    }
}
