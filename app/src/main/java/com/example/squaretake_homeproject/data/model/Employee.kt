package com.example.squaretake_homeproject.data.model

import com.google.gson.annotations.SerializedName

data class Employee(
    @SerializedName("uuid") val id: String,
    @SerializedName("full_name") val name: String,
    @SerializedName("phone_number") val phoneNumber: String = "",
    @SerializedName("email_address") val email: String,
    @SerializedName("biography") val summary: String = "",
    @SerializedName("photo_url_small") val photoSmall: String = "",
    @SerializedName("photo_url_large") val photoLarge: String = "",
    @SerializedName("team") val teamName: String,
    @SerializedName("employee_type") val employeeType: EmployeeType
)

enum class EmployeeType {
    FULL_TIME,
    PART_TIME,
    CONTRACTOR
}
