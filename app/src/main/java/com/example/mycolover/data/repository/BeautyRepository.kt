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
        // 실제 서버가 없으므로 임시 가짜 데이터를 반환하도록 설계
        return listOf(
            BeautyItem("1", "촉촉 립스틱", "롬앤", "https://picsum.photos/400/300", "립", PersonalColor.SPRING_WARM),
            BeautyItem("2", "매트 틴트", "페리페라", "https://picsum.photos/400/301", "립", PersonalColor.SUMMER_COOL),
            BeautyItem("3", "가을 블러셔", "3CE", "https://picsum.photos/400/302", "치크", PersonalColor.FALL_WARM),
            BeautyItem("4", "겨울 쉐딩", "투쿨포스쿨", "https://picsum.photos/400/303", "컨투어", PersonalColor.WINTER_COOL)
        ).filter { color == null || it.personalColor == color }
    }
}
