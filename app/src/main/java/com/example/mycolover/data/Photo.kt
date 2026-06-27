package com.example.mycolover.data

import com.google.gson.annotations.SerializedName

/**
 * 포트폴리오용 신규 데이터 모델
 * 레거시의 FeedImage 및 ImageInfo 구조를 참고하여 간소화 및 현대화
 */
data class Photo(
    @SerializedName("id")
    val id: String,
    @SerializedName("author")
    val author: String,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("url")
    val url: String,
    @SerializedName("download_url")
    val downloadUrl: String
)

data class PhotoResponse(
    val photos: List<Photo>,
    val total: Int,
    val page: Int
)
