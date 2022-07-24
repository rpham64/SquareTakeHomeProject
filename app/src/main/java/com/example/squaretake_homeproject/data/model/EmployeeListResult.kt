package com.example.squaretake_homeproject.data.model

sealed class EmployeeListResult {
    data class Success(val employeesList: List<Employee>) : EmployeeListResult()
    data class Error(val error: Throwable) : EmployeeListResult()
    object Loading : EmployeeListResult()
}
