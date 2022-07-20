package com.example.squaretake_homeproject.data.di

import com.example.squaretake_homeproject.data.source.remote.service.EmployeeDirectoryRetrofitService
import dagger.Module
import dagger.Provides
import retrofit2.Converter
import retrofit2.Retrofit

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://s3.amazonaws.com/sq-mobile-interview/")
            .build()
    }

    @Provides
    fun provideSquareMobileInterviewRetrofitService(retrofit: Retrofit): EmployeeDirectoryRetrofitService {
        return retrofit.create(EmployeeDirectoryRetrofitService::class.java)
    }
}