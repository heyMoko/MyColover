package com.example.mycolover.data

/**
 * 앱의 핵심인 퍼스널 컬러 타입
 * 한국인에게 가장 대중적인 4가지 타입으로 컴팩트하게 구성
 */
enum class PersonalColor(val displayName: String) {
    SPRING_WARM("봄 웜톤"),
    SUMMER_COOL("여름 쿨톤"),
    FALL_WARM("가을 웜톤"),
    WINTER_COOL("겨울 쿨톤")
}
