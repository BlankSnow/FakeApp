package com.blank.fakeapp.data.repository

import com.blank.fakeapp.data.mapper.toDomain
import com.blank.fakeapp.data.remote.api.FakeStoreApi
import com.blank.fakeapp.domain.model.User
import com.blank.fakeapp.domain.repository.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepositoryImpl(
    private val api: FakeStoreApi
) : UserRepository {

    override suspend fun getUser(userId: Int): Result<User> = withContext(Dispatchers.IO) {
        try {
            val response = api.getUser(userId)
            Result.success(response.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
