package com.example.squaretake_homeproject.data.di

import com.example.squaretake_homeproject.data.source.remote.service.EmployeeDirectoryRetrofitService
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://s3.amazonaws.com/sq-mobile-interview/")
            .build()
    }

    @Singleton
    @Provides
    fun provideSquareMobileInterviewRetrofitService(retrofit: Retrofit): EmployeeDirectoryRetrofitService {
        return retrofit.create(EmployeeDirectoryRetrofitService::class.java)
    }
}