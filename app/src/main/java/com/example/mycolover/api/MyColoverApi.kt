package com.example.mycolover.api

import com.example.mycolover.data.Photo
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * 신규 프로젝트용 API 인터페이스
 * 레거시의 AuthorizationAPIs 구조를 Hilt 주입 방식으로 개선
 */
interface MyColoverApi {
    @GET("v2/list")
    suspend fun getPhotos(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<Photo>
}
