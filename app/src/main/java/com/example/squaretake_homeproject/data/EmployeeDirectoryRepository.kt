package com.example.squaretake_homeproject.data

import com.example.squaretake_homeproject.data.model.EmployeeListResult
import com.example.squaretake_homeproject.data.source.remote.EmployeeDirectoryRemoteDataSource
import javax.inject.Inject

interface EmployeeDirectoryRepository {
    suspend fun getEmployeesList(): EmployeeListResult
    suspend fun getMalformedEmployeesList(): EmployeeListResult
    suspend fun getEmptyEmployeesList(): EmployeeListResult
}

class DefaultEmployeeDirectoryRepository @Inject constructor(
    private val employeeDirectoryRemoteDataSource: EmployeeDirectoryRemoteDataSource
) : EmployeeDirectoryRepository {

    override suspend fun getEmployeesList(): EmployeeListResult =
        employeeDirectoryRemoteDataSource.getEmployeesList()

    override suspend fun getMalformedEmployeesList(): EmployeeListResult =
        employeeDirectoryRemoteDataSource.getMalformedEmployeesList()

    override suspend fun getEmptyEmployeesList(): EmployeeListResult =
        employeeDirectoryRemoteDataSource.getEmptyEmployeesList()
}