package com.example.squaretake_homeproject.data.source.remote

import com.example.squaretake_homeproject.data.model.Employee
import com.example.squaretake_homeproject.data.model.EmployeeListResult
import com.example.squaretake_homeproject.data.source.remote.service.EmployeeDirectoryRetrofitService
import javax.inject.Inject

interface EmployeeDirectoryRemoteDataSource {
    suspend fun getEmployeesList(): EmployeeListResult
    suspend fun getMalformedEmployeesList(): EmployeeListResult
    suspend fun getEmptyEmployeesList(): EmployeeListResult
}

class DefaultEmployeeDirectoryRemoteDataSource @Inject constructor(
    private val service: EmployeeDirectoryRetrofitService
): EmployeeDirectoryRemoteDataSource {

    override suspend fun getEmployeesList(): EmployeeListResult =
        kotlin.runCatching { service.getEmployeesList().employees }.toEmployeeListResult()

    override suspend fun getMalformedEmployeesList(): EmployeeListResult =
        kotlin.runCatching { service.getMalformedEmployeesList().employees }.toEmployeeListResult()

    override suspend fun getEmptyEmployeesList(): EmployeeListResult =
        kotlin.runCatching { service.getEmptyEmployeesList().employees }.toEmployeeListResult()
}

fun Result<List<Employee>>.toEmployeeListResult(): EmployeeListResult {
    return if (isSuccess) {
        EmployeeListResult.Success(getOrDefault(emptyList()))
    } else {
        EmployeeListResult.Error(exceptionOrNull()!!)
    }
}