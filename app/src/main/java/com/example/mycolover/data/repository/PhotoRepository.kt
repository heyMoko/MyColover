package com.example.mycolover.data.repository

import com.example.mycolover.api.MyColoverApi
import com.example.mycolover.data.Photo
import javax.inject.Inject
import javax.inject.Singleton

/**
 * 사진 데이터를 관리하는 저장소
 * Interface를 두어 나중에 Fake 데이터를 쓰거나 테스트하기 쉽게 만듦
 */
interface PhotoRepository {
    suspend fun getPhotos(page: Int, limit: Int): List<Photo>
}

@Singleton
class PhotoRepositoryImpl @Inject constructor(
    private val api: MyColoverApi
) : PhotoRepository {
    override suspend fun getPhotos(page: Int, limit: Int): List<Photo> {
        return api.getPhotos(page, limit)
    }
}
