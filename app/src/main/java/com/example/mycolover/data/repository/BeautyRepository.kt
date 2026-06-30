package com.example.mycolover.data.repository

import com.example.mycolover.api.MyColoverApi
import com.example.mycolover.data.BeautyItem
import com.example.mycolover.data.PersonalColor
import com.example.mycolover.data.local.FavoriteDao
import com.example.mycolover.data.local.FavoriteItem
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 서버(API)와 로컬 DB(Room)를 모두 관리하는 통합 저장소
 */
interface BeautyRepository {
    // 서버 데이터 가져오기 (컬러 필터 + 검색어 조합)
    suspend fun getItems(color: PersonalColor? = null, query: String? = null): List<BeautyItem>

    // 로컬 찜 목록 관련
    fun getAllFavorites(): Flow<List<FavoriteItem>>
    suspend fun addFavorite(item: FavoriteItem)
    suspend fun removeFavorite(item: FavoriteItem)
    suspend fun isFavorite(id: Int): Boolean
}

@Singleton
class BeautyRepositoryImpl @Inject constructor(
    private val api: MyColoverApi,
    private val favoriteDao: FavoriteDao
) : BeautyRepository {
    
    override suspend fun getItems(color: PersonalColor?, query: String?): List<BeautyItem> {
        val colorFilter = color?.let { "eq.${it.name}" }
        // 수파베이스 ilike 문법: *검색어* (앞뒤에 별표를 붙여 '포함' 검색)
        val nameFilter = if (!query.isNullOrBlank()) "ilike.*$query*" else null
        
        return api.getBeautyItems(colorType = colorFilter, nameQuery = nameFilter)
    }

    override fun getAllFavorites(): Flow<List<FavoriteItem>> {
        return favoriteDao.getAllFavorites()
    }

    override suspend fun addFavorite(item: FavoriteItem) {
        favoriteDao.addFavorite(item)
    }

    override suspend fun removeFavorite(item: FavoriteItem) {
        favoriteDao.removeFavorite(item)
    }

    override suspend fun isFavorite(id: Int): Boolean {
        return favoriteDao.isFavorite(id)
    }
}
