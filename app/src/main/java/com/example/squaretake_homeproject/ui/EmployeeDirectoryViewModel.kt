package com.example.squaretake_homeproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.squaretake_homeproject.data.EmployeeDirectoryRepository
import com.example.squaretake_homeproject.data.model.EmployeeListResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

private const val LOADING_DURATION = 2000L

@Singleton
class EmployeeDirectoryViewModel @Inject constructor(
    private val employeeDirectoryRepository: EmployeeDirectoryRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _results: MutableLiveData<EmployeeListResult> = MutableLiveData()
    val results: LiveData<EmployeeListResult> = _results

    fun fetchEmployeesList() {
        showLoading()

        viewModelScope.launch(ioDispatcher) {
            // Small delay to simulate network request and show loading state
            delay(LOADING_DURATION)

            val result = employeeDirectoryRepository.getEmployeesList()

            _results.postValue(result)
        }
    }

    fun fetchEmptyEmployeesList() {
        showLoading()

        viewModelScope.launch(ioDispatcher) {
            // Small delay to simulate network request and show loading state
            delay(LOADING_DURATION)

            val result = employeeDirectoryRepository.getEmptyEmployeesList()

            _results.postValue(result)
        }
    }

    fun fetchMalformedEmployeesList() {
        showLoading()

        viewModelScope.launch(ioDispatcher) {
            // Small delay to simulate network request and show loading state
            delay(LOADING_DURATION)

            val result = employeeDirectoryRepository.getMalformedEmployeesList()

            _results.postValue(result)
        }
    }

    private fun showLoading() {
        _results.value = EmployeeListResult.Loading
    }
}