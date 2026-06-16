package com.example.apirest.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.apirest.data.ApiData
import com.example.apirest.data.ApiResponse
import com.example.apirest.ui.viewmodel.ApiState
import com.example.apirest.ui.viewmodel.ApiViewModel

@Composable
fun MainScreen(viewModel: ApiViewModel) {
    val state by viewModel.uiState

    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val currentState = state) {
                is ApiState.Loading -> CircularProgressIndicator()
                is ApiState.Success<ApiData> -> UserInfo(currentState.data)
                is ApiState.Error -> Text(text = "Error: ${currentState.message}")
            }
        }
    }
}

@Composable
fun UserInfo(response: ApiResponse<ApiData>) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Message: ${response.message}")
        Text(text = "Nickname: ${response.data.nickname}")
        Text(text = "ID: ${response.data.id}")
        Text(text = "Server: ${response.data.server}")
    }
}