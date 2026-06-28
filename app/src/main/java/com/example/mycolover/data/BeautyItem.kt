package com.example.mycolover.data

import com.google.gson.annotations.SerializedName

/**
 * 레거시의 Goods 모델을 컴팩트하게 재구성
 * 포트폴리오 면접 시 핵심 도메인 지식을 보여주기 위한 모델
 */
data class BeautyItem(
    @SerializedName("id") 
    val id: String,
    
    @SerializedName("name") 
    val name: String,
    
    @SerializedName("brand") 
    val brand: String,
    
    @SerializedName("image") 
    val imageUrl: String,
    
    @SerializedName("category") 
    val category: String,
    
    val personalColor: PersonalColor
)
