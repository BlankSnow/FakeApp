package com.blank.fakeapp.data.repository

import com.blank.fakeapp.data.mapper.toDomain
import com.blank.fakeapp.data.remote.api.FakeStoreApi
import com.blank.fakeapp.domain.model.User
import com.blank.fakeapp.domain.repository.UserRepository

class UserRepositoryImpl(
    private val api: FakeStoreApi
) : UserRepository {

    override suspend fun getUser(userId: Int): Result<User> {
        return try {
            val response = api.getUser(userId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
