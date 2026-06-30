package com.example.mycolover.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycolover.data.PersonalColor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class Question(
    val id: Int,
    val text: String,
    val options: List<Option>
)

data class Option(
    val text: String,
    val resultType: PersonalColor
)

@HiltViewModel
class SelfTestViewModel @Inject constructor() : ViewModel() {

    private val questions = listOf(
        Question(
            id = 1,
            text = "당신의 피부톤은 어떤 느낌인가요?",
            options = listOf(
                Option("노란기가 도는 따뜻한 느낌", PersonalColor.SPRING_WARM),
                Option("붉은기가 도는 차가운 느낌", PersonalColor.SUMMER_COOL)
            )
        ),
        Question(
            id = 2,
            text = "잘 어울리는 액세서리 색상은?",
            options = listOf(
                Option("화사한 골드", PersonalColor.SPRING_WARM),
                Option("깨끗한 실버", PersonalColor.SUMMER_COOL)
            )
        ),
        Question(
            id = 3,
            text = "분위기가 어떤 쪽에 더 가깝나요?",
            options = listOf(
                Option("차분하고 성숙한 느낌", PersonalColor.FALL_WARM),
                Option("강렬하고 선명한 느낌", PersonalColor.WINTER_COOL)
            )
        )
    )

    private val _currentQuestionIndex = MutableStateFlow(0)
    val currentQuestionIndex: StateFlow<Int> = _currentQuestionIndex

    private val _testResult = MutableStateFlow<PersonalColor?>(null)
    val testResult: StateFlow<PersonalColor?> = _testResult

    private val scores = mutableMapOf<PersonalColor, Int>()

    // Compose가 관찰할 수 있도록 StateFlow로 노출
    val currentQuestion: StateFlow<Question?> = _currentQuestionIndex
        .map { index -> if (index < questions.size) questions[index] else null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val isFinished: StateFlow<Boolean> = _currentQuestionIndex
        .map { index -> index >= questions.size }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), false)

    fun selectOption(option: Option) {
        scores[option.resultType] = scores.getOrDefault(option.resultType, 0) + 1
        
        if (_currentQuestionIndex.value < questions.size - 1) {
            _currentQuestionIndex.value += 1
        } else {
            val result = scores.maxByOrNull { it.value }?.key ?: PersonalColor.SPRING_WARM
            _testResult.value = result
            _currentQuestionIndex.value += 1
        }
    }

    fun resetTest() {
        _currentQuestionIndex.value = 0
        _testResult.value = null
        scores.clear()
    }
}
