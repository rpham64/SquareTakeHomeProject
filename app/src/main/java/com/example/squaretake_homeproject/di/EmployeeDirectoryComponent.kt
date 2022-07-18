package com.example.squaretake_homeproject.di

import com.example.squaretake_homeproject.ui.EmployeeDirectoryActivity
import dagger.Component

@Component
interface EmployeeDirectoryComponent {
    fun inject(activity: EmployeeDirectoryActivity)
}