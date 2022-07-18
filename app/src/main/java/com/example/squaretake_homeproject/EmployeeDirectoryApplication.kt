package com.example.squaretake_homeproject

import android.app.Application
import com.example.squaretake_homeproject.di.DaggerEmployeeDirectoryComponent

class EmployeeDirectoryApplication : Application() {

    val appComponent = DaggerEmployeeDirectoryComponent.create()
}