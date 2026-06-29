package com.example.mycolover.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * 앱 전체 로컬 데이터베이스
 */
@Database(entities = [FavoriteItem::class], version = 2, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}
