package com.example.squaretake_homeproject.data.source.remote.service

import com.example.squaretake_homeproject.data.model.Employee
import retrofit2.http.GET

interface EmployeeDirectoryRetrofitService {

    @GET("employees.json")
    suspend fun getEmployeesList(): List<Employee>

    @GET("employees_malformed.json")
    suspend fun getMalformedEmployeesList(): List<Employee>

    @GET("employees_empty.json")
    suspend fun getEmptyEmployeesList(): List<Employee>
}