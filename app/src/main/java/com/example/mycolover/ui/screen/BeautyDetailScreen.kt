package com.example.mycolover.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.mycolover.ui.viewmodel.BeautyViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BeautyDetailScreen(
    itemId: Int,
    viewModel: BeautyViewModel,
    onBackClick: () -> Unit
) {
    // 상품 정보 관찰
    val item by viewModel.selectedItem.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()
    val isFavorite = favoriteIds.contains(itemId)

    // 화면이 켜질 때 상품 정보 불러오기
    LaunchedEffect(itemId) {
        viewModel.fetchItemDetail(itemId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("제품 상세 정보") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "뒤로가기")
                    }
                },
                actions = {
                    IconButton(onClick = { item?.let { viewModel.toggleFavorite(it) } }) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "찜하기",
                            tint = if (isFavorite) Color.Red else Color.Unspecified
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        item?.let { beautyItem ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()) // 스크롤 가능하게 설정
            ) {
                AsyncImage(
                    model = beautyItem.imageUrl,
                    contentDescription = beautyItem.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    contentScale = ContentScale.Crop
                )

                Column(modifier = Modifier.padding(24.dp)) {
                    Text(
                        text = beautyItem.brand,
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = beautyItem.name,
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Text(
                            text = beautyItem.personalColor.displayName,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        text = "카테고리: ${beautyItem.category}",
                        style = MaterialTheme.typography.bodyLarge
                    )
                    
                    Spacer(modifier = Modifier.height(40.dp))
                    Button(
                        onClick = { /* 구매 링크 이동 등 */ },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("제품 정보 더보기")
                    }
                }
            }
        }
    }
}
