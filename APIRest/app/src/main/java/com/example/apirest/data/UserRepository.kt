package com.example.apirest.data

import com.example.apirest.domain.UserRep

class UserRepository(private val apiService: ApiService) : UserRep {
    override suspend fun getUserData(): ApiResponse<ApiData> {
        return apiService.getData()
    }
}
