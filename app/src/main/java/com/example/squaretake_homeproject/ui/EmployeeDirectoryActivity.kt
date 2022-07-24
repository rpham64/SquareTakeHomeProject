package com.example.squaretake_homeproject.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.squaretake_homeproject.EmployeeDirectoryApplication
import com.example.squaretake_homeproject.R
import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.model.EmployeeListResult
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
        binding.apply {
            setContentView(root)
            recyclerView.adapter = employeeListAdapter
        }

        employeeDirectoryViewModel.results.observe(this) { result ->
            when (result) {
                is EmployeeListResult.Loading -> showLoading()
                is EmployeeListResult.Success -> handleResult(result.employeesList)
                is EmployeeListResult.Error -> showError()
            }
        }

        if (savedInstanceState == null) {
            employeeDirectoryViewModel.fetchEmployeesList()
        }
    }

    private fun showLoading() {
        binding.apply {
            loadingProgressBar.visibility = View.VISIBLE

            // Hide all other views
            recyclerView.visibility = View.GONE
            viewError.visibility = View.GONE
            viewEmpty.visibility = View.GONE
        }
    }

    private fun hideLoading() {
        binding.loadingProgressBar.visibility = View.GONE
    }

    private fun handleResult(employeesList: List<Employee>) {
        when {
            employeesList.isNotEmpty() -> {
                updateAdapter(employeesList)
                showContent()
            }
            employeesList.isEmpty() -> {
                updateAdapter(employeesList)
                showEmpty()
            }
            else -> showError()
        }

        hideLoading()
    }

    private fun updateAdapter(employeesList: List<Employee>) {
        employeeListAdapter.employeesList = employeesList
        binding.recyclerView.adapter!!.notifyDataSetChanged()
    }

    private fun showContent() {
        binding.apply {
            recyclerView.visibility = View.VISIBLE
            viewError.visibility = View.GONE
            viewEmpty.visibility = View.GONE
        }
    }

    private fun showError() {
        binding.apply {
            recyclerView.visibility = View.GONE
            viewError.visibility = View.VISIBLE
            viewEmpty.visibility = View.GONE
        }
    }

    private fun showEmpty() {
        binding.apply {
            recyclerView.visibility = View.GONE
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