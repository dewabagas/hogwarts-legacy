package com.dewabagas.hogwartslegacy.di

import com.dewabagas.hogwartslegacy.data.remotes.ApiService
import com.dewabagas.hogwartslegacy.data.repositories.StudentRepositoryImpl
import com.dewabagas.hogwartslegacy.domain.repositories.StudentRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://hp-api.onrender.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient.Builder().build())
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit) : ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideStudentRepository(
        apiService: ApiService
    ): StudentRepository {
        return StudentRepositoryImpl(apiService)
    }
}