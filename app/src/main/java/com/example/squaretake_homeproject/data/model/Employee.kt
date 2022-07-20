package com.example.squaretake_homeproject.data.model

import com.google.gson.annotations.SerializedName

data class Employee(
    private val uuid: String,
    @SerializedName("full_name") private val name: String,
    @SerializedName("phone_number") private val phoneNumber: String = "",
    @SerializedName("email_address") private val email: String,
    private val biography: String = "",
    @SerializedName("photo_url_small") private val photoSmall: String = "",
    @SerializedName("photo_url_large") private val photoLarge: String = "",
    private val team: String,
    @SerializedName("employee_type") private val employeeType: EmployeeType
)

enum class EmployeeType {
    FULL_TIME,
    PART_TIME,
    CONTRACTOR
}
