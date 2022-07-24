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

    override suspend fun getEmployeesList(): Result<List<Employee>> =
        kotlin.runCatching { service.getEmployeesList().employees }

    override suspend fun getMalformedEmployeesList(): Result<List<Employee>> =
        kotlin.runCatching { service.getMalformedEmployeesList().employees }

    override suspend fun getEmptyEmployeesList(): Result<List<Employee>> =
        kotlin.runCatching { service.getEmptyEmployeesList().employees }
}