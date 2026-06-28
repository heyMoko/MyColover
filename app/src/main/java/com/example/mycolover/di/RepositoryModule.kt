package com.example.mycolover.di

import com.example.mycolover.data.repository.BeautyRepository
import com.example.mycolover.data.repository.BeautyRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindBeautyRepository(
        beautyRepositoryImpl: BeautyRepositoryImpl
    ): BeautyRepository
}
