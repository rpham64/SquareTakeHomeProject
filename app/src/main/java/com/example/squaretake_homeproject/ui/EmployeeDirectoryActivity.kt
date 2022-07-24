package com.example.squaretake_homeproject.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.squaretake_homeproject.EmployeeDirectoryApplication
import com.example.squaretake_homeproject.R
import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.databinding.ActivityEmployeeDirectoryBinding
import javax.inject.Inject

class EmployeeDirectoryActivity : AppCompatActivity() {

    @Inject
    lateinit var employeeDirectoryViewModel: EmployeeDirectoryViewModel

    private lateinit var binding: ActivityEmployeeDirectoryBinding

    private val employeeListAdapter: EmployeeDirectoryListAdapter =
        EmployeeDirectoryListAdapter(emptyList())

    override fun onCreate(savedInstanceState: Bundle?) {
        // Inject app components
        (applicationContext as EmployeeDirectoryApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeDirectoryBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_employee_directory)

        binding.viewContent.adapter = employeeListAdapter

        employeeDirectoryViewModel.results.observe(this) { result ->
            when {
                result.isSuccess -> handleResult(result.getOrDefault(emptyList()))
                else -> showError()
            }
        }
        employeeDirectoryViewModel.fetchEmployeesList()
    }

    private fun handleResult(employeesList: List<Employee>) {
        when {
            employeesList.isNotEmpty() -> {
                updateAdapter(employeesList)
                showContent()
            }
            employeesList.isEmpty() -> showEmpty()
            else -> showError()
        }
    }

    private fun updateAdapter(employeesList: List<Employee>) {
        employeeListAdapter.employeesList = employeesList
        binding.viewContent.adapter!!.notifyDataSetChanged()
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.activity_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_refresh -> refresh()
            R.id.action_show_empty -> getEmptyEmployeesList()
            R.id.action_show_malformed_error -> getMalformedEmployeesList()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun refresh() {
        employeeDirectoryViewModel.fetchEmployeesList()
    }

    private fun getEmptyEmployeesList() {
        employeeDirectoryViewModel.fetchEmptyEmployeesList()
    }

    private fun getMalformedEmployeesList() {
        employeeDirectoryViewModel.fetchMalformedEmployeesList()
    }
}