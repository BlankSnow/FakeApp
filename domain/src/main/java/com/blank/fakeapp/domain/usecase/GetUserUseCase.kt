package com.blank.fakeapp.domain.usecase

import com.blank.fakeapp.domain.model.User
import com.blank.fakeapp.domain.repository.UserRepository

class GetUserUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(userId: Int): Result<User> {
        return repository.getUser(userId)
    }
}
