package com.gaffy.apps.mvvm.data.api

import com.gaffy.apps.mvvm.data.dto.ExerciseResponseDTO
import com.gaffy.apps.mvvm.data.dto.MuscleResponseDTO
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Used to connect to the Unsplash API to fetch photos
 */
interface ApiService {

    @GET("/api/v2/muscle")
    suspend fun fetchMuscle(): Response<MuscleResponseDTO>

    @GET("/api/v2/exercise")
    suspend fun fetchExercise(@Query("muscles") muscles: Int): Response<ExerciseResponseDTO>

    companion object {
        const val BASE_URL = "https://wger.de"

        fun create(): com.gaffy.apps.mvvm.data.api.ApiService {
            val logger = HttpLoggingInterceptor().apply { level = Level.BODY }

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()

            return Retrofit.Builder()
                .baseUrl(com.gaffy.apps.mvvm.data.api.ApiService.Companion.BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(com.gaffy.apps.mvvm.data.api.ApiService::class.java)
        }
    }
}
