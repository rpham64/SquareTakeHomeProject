package com.example.squaretake_homeproject.di

import com.example.squaretake_homeproject.data.di.NetworkModule
import com.example.squaretake_homeproject.ui.EmployeeDirectoryActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [EmployeeDirectoryModule::class, NetworkModule::class])
interface EmployeeDirectoryComponent {
    fun inject(activity: EmployeeDirectoryActivity)
}