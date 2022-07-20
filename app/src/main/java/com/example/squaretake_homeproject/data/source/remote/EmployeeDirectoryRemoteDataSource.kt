package com.example.squaretake_homeproject.data.source.remote

import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.source.remote.service.EmployeeDirectoryRetrofitService
import javax.inject.Inject

interface EmployeeDirectoryRemoteDataSource {
    suspend fun getEmployeesList(): Result<List<Employee>>
    suspend fun getMalformedEmployeesList(): Result<List<Employee>>
    suspend fun getEmptyEmployeesList(): Result<List<Employee>>
}

class DefaultEmployeeDirectoryRemoteDataSource @Inject constructor(
    private val service: EmployeeDirectoryRetrofitService
): EmployeeDirectoryRemoteDataSource {

    override suspend fun getEmployeesList(): Result<List<Employee>> = service.getEmployeesList()

    override suspend fun getMalformedEmployeesList(): Result<List<Employee>> = service.getMalformedEmployeesList()

    override suspend fun getEmptyEmployeesList(): Result<List<Employee>> = service.getEmptyEmployeesList()
}