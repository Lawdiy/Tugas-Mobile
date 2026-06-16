package com.example.apirest.domain

import com.example.apirest.data.ApiData
import com.example.apirest.data.ApiResponse

interface UserRep {
    suspend fun getUserData(): ApiResponse<ApiData>
}
