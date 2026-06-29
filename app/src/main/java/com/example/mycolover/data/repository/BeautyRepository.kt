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
    // 서버 데이터 가져오기
    suspend fun getItemsByColor(color: PersonalColor?): List<BeautyItem>

    // 로컬 찜 목록 관련
    fun getAllFavorites(): Flow<List<FavoriteItem>>
    suspend fun addFavorite(item: FavoriteItem)
    suspend fun removeFavorite(item: FavoriteItem)
    suspend fun isFavorite(id: Int): Boolean
}

@Singleton
class BeautyRepositoryImpl @Inject constructor(
    private val api: MyColoverApi,
    private val favoriteDao: FavoriteDao // Hilt가 자동으로 주입해줌
) : BeautyRepository {
    
    override suspend fun getItemsByColor(color: PersonalColor?): List<BeautyItem> {
        val queryColor = color?.let { "eq.${it.name}" }
        return api.getBeautyItems(colorType = queryColor)
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
