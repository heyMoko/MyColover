package com.example.mycolover.di

import com.example.mycolover.data.repository.PhotoRepository
import com.example.mycolover.data.repository.PhotoRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt Repository 모듈
 * 어떤 구현체를 주입할지 결정함
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindPhotoRepository(
        photoRepositoryImpl: PhotoRepositoryImpl
    ): PhotoRepository
}
