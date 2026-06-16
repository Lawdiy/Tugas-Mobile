package com.example.apirest.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.apirest.data.ApiData
import com.example.apirest.data.ApiResponse
import com.example.apirest.data.ApiService
import com.example.apirest.data.UserRepository
import com.example.apirest.domain.UserRep
import kotlinx.coroutines.launch

sealed class ApiState<out T> {
    data object Loading : ApiState<Nothing>()
    data class Success<T>(val data: ApiResponse<T>) : ApiState<T>()
    data class Error(val message: String) : ApiState<Nothing>()
}

class ApiViewModel(
    private val repository: UserRep = UserRepository(ApiService.getInstance())
) : ViewModel() {
    
    private val _uiState = mutableStateOf<ApiState<ApiData>>(ApiState.Loading)
    val uiState: State<ApiState<ApiData>> = _uiState

    init {
        fetchData()
    }

    fun fetchData() {
        viewModelScope.launch {
            _uiState.value = ApiState.Loading
            try {
                val response = repository.getUserData()
                _uiState.value = ApiState.Success(response)
            } catch (e: Exception) {
                _uiState.value = ApiState.Error(e.message ?: "Error tak diketahui")
            }
        }
    }
}
