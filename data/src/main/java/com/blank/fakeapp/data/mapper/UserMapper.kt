package com.blank.fakeapp.data.mapper

import com.blank.fakeapp.data.remote.dto.UserDto
import com.blank.fakeapp.domain.model.User

fun UserDto.toDomain(): User {
    return User(
        id = id,
        email = email,
        username = username,
        firstName = name.firstname,
        lastName = name.lastname,
        phone = phone
    )
}
