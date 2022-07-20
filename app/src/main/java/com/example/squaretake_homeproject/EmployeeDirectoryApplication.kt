package com.example.squaretake_homeproject

import android.app.Application
import com.example.squaretake_homeproject.di.DaggerEmployeeDirectoryComponent
import com.example.squaretake_homeproject.di.EmployeeDirectoryComponent

class EmployeeDirectoryApplication : Application() {

    val appComponent: EmployeeDirectoryComponent = DaggerEmployeeDirectoryComponent.create()
}