package com.blank.fakeapp.ui.screens.profile

import com.blank.fakeapp.domain.model.User

data class ProfileUiState(
    val userState: UserState,
    val favoritesCount: Int
)

sealed interface UserState {
    data object Loading : UserState
    data class Success(val user: User) : UserState
    data class Error(val message: String) : UserState
}
