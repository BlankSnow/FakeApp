package com.blank.fakeapp.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.blank.fakeapp.domain.model.User
import com.blank.fakeapp.domain.usecase.GetFavoritesUseCase
import com.blank.fakeapp.domain.usecase.GetUserUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getUserUseCase: GetUserUseCase,
    private val getFavoritesUseCase: GetFavoritesUseCase
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Loading)
    
    val uiState: StateFlow<ProfileUiState> = combine(
        _userState,
        getFavoritesUseCase()
    ) { userState, favorites ->
        ProfileUiState(
            userState = userState,
            favoritesCount = favorites.size
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ProfileUiState(UserState.Loading, 0)
    )

    init {
        loadUser()
    }

    fun loadUser() {
        viewModelScope.launch {
            _userState.value = UserState.Loading
            getUserUseCase(userId = 8).onSuccess { user ->
                _userState.value = UserState.Success(user)
            }.onFailure { error ->
                _userState.value = UserState.Error(error.message ?: "Unknown error")
            }
        }
    }
}

data class ProfileUiState(
    val userState: UserState,
    val favoritesCount: Int
)

sealed interface UserState {
    data object Loading : UserState
    data class Success(val user: User) : UserState
    data class Error(val message: String) : UserState
}
