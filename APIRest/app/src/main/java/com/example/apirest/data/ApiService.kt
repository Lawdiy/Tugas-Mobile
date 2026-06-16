package com.example.apirest.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.http.GET

@Serializable
data class ApiResponse<T>(
    val message: String,
    val code: String,
    val data: T
)

@Serializable
data class ApiData(
    val nickname: String,
    val id: String,
    val server: String
)

interface ApiService {
    @GET("data")
    suspend fun getData(): ApiResponse<ApiData>

    companion object {
        private var apiService: ApiService? = null

        @OptIn(ExperimentalSerializationApi::class)
        fun getInstance(): ApiService {
            if (apiService == null) {
                val contentType = MediaType.get("application/json")
                val json = Json { 
                    ignoreUnknownKeys = true 
                    coerceInputValues = true
                    allowTrailingComma = true
                }

                apiService = Retrofit.Builder()
                    .baseUrl("https://mobile-api.free.beeceptor.com/")
                    .addConverterFactory(json.asConverterFactory(contentType))
                    .build()
                    .create(ApiService::class.java)
            }
            return apiService!!
        }
    }
}
