package com.example.mycolover.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycolover.data.PersonalColor

/**
 * 찜한 아이템을 저장하기 위한 로컬 DB 엔티티
 * UI 재사용을 위해 BeautyItem과 동일한 정보를 저장하도록 개선
 */
@Entity(tableName = "favorite_items")
data class FavoriteItem(
    @PrimaryKey 
    val id: Int,
    val name: String,
    val brand: String,
    val imageUrl: String,
    val category: String,
    val personalColor: PersonalColor
)
