package com.example.mycolover.ui.screen

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mycolover.ui.viewmodel.SelfTestViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelfTestScreen(
    viewModel: SelfTestViewModel,
    onResultClick: (String) -> Unit
) {
    // 모든 상태를 collectAsState로 관찰하여 리컴포지션 유도
    val currentQuestionIndex by viewModel.currentQuestionIndex.collectAsState()
    val testResult by viewModel.testResult.collectAsState()
    val currentQuestion by viewModel.currentQuestion.collectAsState()
    val isFinished by viewModel.isFinished.collectAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("퍼스널 컬러 진단") }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(targetState = isFinished, label = "test_content") { finished ->
                if (!finished && currentQuestion != null) {
                    // 질문 화면
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Q. ${currentQuestionIndex + 1}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = currentQuestion!!.text, // null이 아님이 보장됨
                            style = MaterialTheme.typography.headlineSmall,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        currentQuestion!!.options.forEach { option ->
                            Button(
                                onClick = { viewModel.selectOption(option) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Text(text = option.text, modifier = Modifier.padding(8.dp))
                            }
                        }
                    }
                } else if (testResult != null) {
                    // 결과 화면
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "진단 결과", style = MaterialTheme.typography.titleMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "당신은 ${testResult?.displayName} 입니다!",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 28.sp
                        )
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        Button(
                            onClick = { onResultClick(testResult?.name ?: "") },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text("내 톤에 맞는 제품 보러가기")
                        }
                        
                        TextButton(onClick = { viewModel.resetTest() }) {
                            Text("다시 테스트하기")
                        }
                    }
                }
            }
        }
    }
}
