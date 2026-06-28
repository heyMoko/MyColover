package com.example.mycolover.data.repository

import com.example.mycolover.api.MyColoverApi
import com.example.mycolover.data.BeautyItem
import com.example.mycolover.data.PersonalColor
import javax.inject.Inject
import javax.inject.Singleton

interface BeautyRepository {
    suspend fun getItemsByColor(color: PersonalColor?): List<BeautyItem>
}

@Singleton
class BeautyRepositoryImpl @Inject constructor(
    private val api: MyColoverApi
) : BeautyRepository {
    
    override suspend fun getItemsByColor(color: PersonalColor?): List<BeautyItem> {
        // 이제 가짜 데이터 대신 진짜 서버(API)를 호출합니다!
        // 수파베이스 쿼리 방식: ?personal_color=eq.SPRING_WARM
        val queryColor = color?.let { "eq.${it.name}" }
        return api.getBeautyItems(colorType = queryColor)
    }
}
