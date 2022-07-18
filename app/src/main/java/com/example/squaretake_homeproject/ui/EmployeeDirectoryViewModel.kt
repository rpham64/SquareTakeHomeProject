package com.example.squaretake_homeproject.ui

import androidx.lifecycle.ViewModel
import com.example.squaretake_homeproject.data.EmployeeDirectoryRepository
import javax.inject.Inject

class EmployeeDirectoryViewModel : ViewModel() {

    @Inject lateinit var employeeDirectoryRepository: EmployeeDirectoryRepository
}