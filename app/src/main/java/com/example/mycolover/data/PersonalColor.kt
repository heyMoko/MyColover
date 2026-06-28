package com.example.mycolover.data

import com.google.gson.annotations.SerializedName

/**
 * 앱의 핵심인 퍼스널 컬러 타입
 * @SerializedName을 추가하여 서버의 문자열 데이터와 매칭함
 */
enum class PersonalColor(val displayName: String) {
    @SerializedName("SPRING_WARM")
    SPRING_WARM("봄 웜톤"),
    
    @SerializedName("SUMMER_COOL")
    SUMMER_COOL("여름 쿨톤"),
    
    @SerializedName("FALL_WARM")
    FALL_WARM("가을 웜톤"),
    
    @SerializedName("WINTER_COOL")
    WINTER_COOL("겨울 쿨톤")
}
