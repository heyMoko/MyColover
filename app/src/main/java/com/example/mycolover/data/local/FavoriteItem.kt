package com.example.mycolover.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 찜한 아이템을 저장하기 위한 로컬 DB 엔티티
 */
@Entity(tableName = "favorite_items")
data class FavoriteItem(
    @PrimaryKey 
    val id: Int,
    val name: String,
    val brand: String,
    val imageUrl: String
)
