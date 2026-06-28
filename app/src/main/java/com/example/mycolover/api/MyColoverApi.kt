package com.example.mycolover.api

import com.example.mycolover.data.BeautyItem
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 뷰티 아이템 관련 API
 * 실제 서버가 생기기 전까지는 Mock 데이터를 활용할 예정
 */
interface MyColoverApi {
    @GET("beauty/items")
    suspend fun getBeautyItems(
        @Query("colorType") colorType: String? = null
    ): List<BeautyItem>
}
