package com.example.squaretake_homeproject.data

import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.source.remote.EmployeeDirectoryRemoteDataSource
import javax.inject.Inject

interface EmployeeDirectoryRepository {
    suspend fun getEmployeesList(): Result<List<Employee>>
    suspend fun getMalformedEmployeesList(): Result<List<Employee>>
    suspend fun getEmptyEmployeesList(): Result<List<Employee>>
}

class DefaultEmployeeDirectoryRepository @Inject constructor(
    private val employeeDirectoryRemoteDataSource: EmployeeDirectoryRemoteDataSource
) : EmployeeDirectoryRepository {
    
    override suspend fun getEmployeesList(): Result<List<Employee>> = employeeDirectoryRemoteDataSource.getEmployeesList()

    override suspend fun getMalformedEmployeesList(): Result<List<Employee>> = employeeDirectoryRemoteDataSource.getMalformedEmployeesList()

    override suspend fun getEmptyEmployeesList(): Result<List<Employee>> = employeeDirectoryRemoteDataSource.getEmptyEmployeesList()
}