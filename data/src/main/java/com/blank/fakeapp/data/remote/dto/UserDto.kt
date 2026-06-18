package com.blank.fakeapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id") val id: Int,
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("name") val name: UserNameDto,
    @SerializedName("phone") val phone: String
)

data class UserNameDto(
    @SerializedName("firstname") val firstname: String,
    @SerializedName("lastname") val lastname: String
)
