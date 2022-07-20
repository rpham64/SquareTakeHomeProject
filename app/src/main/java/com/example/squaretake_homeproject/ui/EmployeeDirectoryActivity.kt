package com.example.squaretake_homeproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.squaretake_homeproject.EmployeeDirectoryApplication
import com.example.squaretake_homeproject.R
import javax.inject.Inject

class EmployeeDirectoryActivity : AppCompatActivity() {

    @Inject lateinit var employeeDirectoryViewModel: EmployeeDirectoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inject app components
        (applicationContext as EmployeeDirectoryApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_directory)

        employeeDirectoryViewModel.results.observe(this) { result ->
            when {
                result.isSuccess -> {

                }
                result.isFailure -> {

                }
            }
        }
        employeeDirectoryViewModel.fetchEmployeesList()
    }

    private fun showError() {

    }

    private fun showEmpty() {

    }
}