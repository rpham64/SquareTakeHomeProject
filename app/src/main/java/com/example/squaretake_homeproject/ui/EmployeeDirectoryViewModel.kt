package com.example.squaretake_homeproject.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.squaretake_homeproject.data.EmployeeDirectoryRepository
import com.example.squaretake_homeproject.data.model.Employee
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EmployeeDirectoryViewModel @Inject constructor(
    private val employeeDirectoryRepository: EmployeeDirectoryRepository,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private var _results: MutableLiveData<Result<List<Employee>>> = MutableLiveData(Result.success(emptyList()))
    val results: LiveData<Result<List<Employee>>> = _results

    fun fetchEmployeesList() {
        viewModelScope.launch(ioDispatcher) {
            val result = employeeDirectoryRepository.getEmployeesList()

            _results.postValue(result)
        }
    }

    fun fetchEmptyEmployeesList() {
        viewModelScope.launch(ioDispatcher) {
            val result = employeeDirectoryRepository.getEmptyEmployeesList()

            _results.postValue(result)
        }
    }

    fun fetchMalformedEmployeesList() {
        viewModelScope.launch(ioDispatcher) {
            val result = employeeDirectoryRepository.getMalformedEmployeesList()

            _results.postValue(result)
        }
    }
}