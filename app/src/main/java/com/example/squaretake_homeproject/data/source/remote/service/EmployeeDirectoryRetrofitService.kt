package com.example.squaretake_homeproject.data.source.remote.service

import com.example.squaretake_homeproject.data.model.Response
import retrofit2.http.GET

interface EmployeeDirectoryRetrofitService {

    @GET("employees.json")
    suspend fun getEmployeesList(): Response

    @GET("employees_malformed.json")
    suspend fun getMalformedEmployeesList(): Response

    @GET("employees_empty.json")
    suspend fun getEmptyEmployeesList(): Response
}