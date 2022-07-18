package com.example.squaretake_homeproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.squaretake_homeproject.R
import javax.inject.Inject

class EmployeeDirectoryActivity : AppCompatActivity() {

    @Inject lateinit var employeeDirectoryViewModel: EmployeeDirectoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_directory)
    }
}