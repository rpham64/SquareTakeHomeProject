package com.example.squaretake_homeproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.squaretake_homeproject.EmployeeDirectoryApplication
import com.example.squaretake_homeproject.R
import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.databinding.ActivityEmployeeDirectoryBinding
import javax.inject.Inject

class EmployeeDirectoryActivity : AppCompatActivity() {

    @Inject lateinit var employeeDirectoryViewModel: EmployeeDirectoryViewModel

    private lateinit var _binding: ActivityEmployeeDirectoryBinding
    private val binding: ActivityEmployeeDirectoryBinding = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inject app components
        (applicationContext as EmployeeDirectoryApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        _binding = ActivityEmployeeDirectoryBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_employee_directory)

        employeeDirectoryViewModel.results.observe(this) { result ->
            when {
                result.isSuccess -> handleResult(result.getOrDefault(emptyList()))
                result.isFailure -> showError()
            }
        }
        employeeDirectoryViewModel.fetchEmployeesList()
    }

    private fun handleResult(employeesList: List<Employee>) {
        when {
            employeesList.isNotEmpty() -> showContent()
            employeesList.isEmpty() -> showEmpty()
            else -> showError()
        }
    }

    private fun showContent() {
        binding.apply {
            viewContent.visibility = View.VISIBLE
            viewError.visibility = View.GONE
            viewEmpty.visibility = View.GONE
        }
    }

    private fun showError() {
        binding.apply {
            viewContent.visibility = View.GONE
            viewError.visibility = View.VISIBLE
            viewEmpty.visibility = View.GONE
        }
    }

    private fun showEmpty() {
        binding.apply {
            viewContent.visibility = View.GONE
            viewError.visibility = View.GONE
            viewEmpty.visibility = View.VISIBLE
        }
    }
}