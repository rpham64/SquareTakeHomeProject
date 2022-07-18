package com.example.squaretake_homeproject.data

import com.example.squaretake_homeproject.data.remote.EmployeeDirectoryRemoteDataSource
import javax.inject.Inject

interface EmployeeDirectoryRepository {
}

class DefaultEmployeeDirectoryRepository @Inject constructor(
    private val employeeDirectoryRemoteDataSource: EmployeeDirectoryRemoteDataSource
) : EmployeeDirectoryRepository {

}