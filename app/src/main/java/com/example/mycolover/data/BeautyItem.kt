package com.example.mycolover.data

import com.google.gson.annotations.SerializedName

/**
 * 서버의 beauty_items 테이블 구조와 완벽히 매칭
 */
data class BeautyItem(
    @SerializedName("id") 
    val id: Int, // 서버에서 bigint(int8)이므로 Int로 변경
    
    @SerializedName("name") 
    val name: String,
    
    @SerializedName("brand") 
    val brand: String,
    
    @SerializedName("image") 
    val imageUrl: String,
    
    @SerializedName("category") 
    val category: String,
    
    @SerializedName("personal_color")
    val personalColor: PersonalColor // Enum 매칭을 위해 SerializedName 추가
)
