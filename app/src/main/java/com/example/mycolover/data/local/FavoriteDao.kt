package com.example.mycolover.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * 로컬 DB 조작을 위한 데이터 접근 객체 (DAO)
 */
@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite_items")
    fun getAllFavorites(): Flow<List<FavoriteItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(item: FavoriteItem)

    @Delete
    suspend fun removeFavorite(item: FavoriteItem)

    @Query("SELECT EXISTS(SELECT * FROM favorite_items WHERE id = :id)")
    suspend fun isFavorite(id: Int): Boolean
}
