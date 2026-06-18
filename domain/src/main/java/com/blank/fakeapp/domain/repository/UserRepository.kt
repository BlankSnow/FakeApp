package com.blank.fakeapp.domain.repository

import com.blank.fakeapp.domain.model.User

interface UserRepository {
    suspend fun getUser(userId: Int): Result<User>
}
