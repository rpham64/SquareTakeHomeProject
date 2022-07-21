package com.example.squaretake_homeproject.di

import com.example.squaretake_homeproject.data.DefaultEmployeeDirectoryRepository
import com.example.squaretake_homeproject.data.EmployeeDirectoryRepository
import com.example.squaretake_homeproject.data.source.remote.DefaultEmployeeDirectoryRemoteDataSource
import com.example.squaretake_homeproject.data.source.remote.EmployeeDirectoryRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
class EmployeeDirectoryModule {

    @Provides
    fun provideEmployeeDirectoryRemoteDataSource(impl: DefaultEmployeeDirectoryRemoteDataSource): EmployeeDirectoryRemoteDataSource = impl

    @Singleton
    @Provides
    fun provideEmployeeDirectoryRepository(impl: DefaultEmployeeDirectoryRepository): EmployeeDirectoryRepository = impl

    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO
}