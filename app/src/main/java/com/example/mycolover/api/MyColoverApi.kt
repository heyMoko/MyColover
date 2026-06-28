package com.example.mycolover.api

import com.example.mycolover.data.BeautyItem
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Supabase 전용 API 인터페이스
 * 테이블 이름인 'beauty_items'가 엔드포인트가 됩니다.
 */
interface MyColoverApi {
    @GET("beauty_items")
    suspend fun getBeautyItems(
        @Query("personal_color") colorType: String? = null,
        @Query("select") select: String = "*"
    ): List<BeautyItem>
}
