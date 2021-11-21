package com.example.programmingbuddies.ui.userdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingbuddies.api.BuddyApi
import com.example.programmingbuddies.api.ServiceBuilder
import com.example.programmingbuddies.models.User
import com.example.programmingbuddies.models.UserUpdate
import com.example.programmingbuddies.models.UserUpdateResponse
import com.example.programmingbuddies.repository.AuthRepo
import com.example.programmingbuddies.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserDetailsViewModel @Inject constructor(val authRepo: AuthRepo) : ViewModel() {

    private val _user: MutableLiveData<User> = MutableLiveData()
    val user: LiveData<User> = _user

    private val _uiState = MutableStateFlow(UserDetailsState())
    val uiState: StateFlow<UserDetailsState> = _uiState

    private val _events = MutableSharedFlow<UserDetailsEvents>()
    val events: SharedFlow<UserDetailsEvents> = _events

    private val _userUpdated: MutableLiveData<Boolean> = MutableLiveData()
    val userUpdated: LiveData<Boolean> = _userUpdated

    private var isCodeCompleted = false

    fun getUser() = viewModelScope.launch {
        _user.postValue(authRepo.getCurrentUser())
        Log.d("Current", "getUser: " + user)
    }

    fun onLinkedInChange(linkedin: String) = viewModelScope.launch {
        val isValid =
            linkedin.isNotBlank() && linkedin.isNotEmpty() && !uiState.value.isLoading && isCodeCompleted
        _uiState.emit(_uiState.value.copy(linkedin = linkedin, isNextButtonEnabled = isValid))
    }

    fun onGithubChange(github: String) = viewModelScope.launch {
        val isValid =
            github.isNotBlank() && github.isNotEmpty() && !uiState.value.isLoading && isCodeCompleted
        _uiState.emit(_uiState.value.copy(github = github, isNextButtonEnabled = isValid))
    }

    fun onLanguageChange(language: String) = viewModelScope.launch {
        val isValid =
            language.isNotBlank() && language.isNotEmpty() && !uiState.value.isLoading && isCodeCompleted
        _uiState.emit(_uiState.value.copy(language = language, isNextButtonEnabled = isValid))
    }

    fun onCodeChange(code: String) = viewModelScope.launch {
        val isValid = code.isNotBlank() && code.isNotEmpty() && !uiState.value.isLoading
        isCodeCompleted = isValid
        _uiState.emit(_uiState.value.copy(code = code, isNextButtonEnabled = isValid))
    }

    fun onNextButtonClicked() = viewModelScope.launch {
        _uiState.emit(uiState.value.copy(isLoading = true, isNextButtonEnabled = false))
        _user.postValue(
            user.value?.copy(
                linkedin = uiState.value.linkedin,
                github = uiState.value.github,
                language = uiState.value.language,
                profileProgram = uiState.value.code
            )
        )
//        val usertemp = user.value
//        if (usertemp != null) {
//            authRepo.saveUserIntoPreferences(usertemp)
//            authRepo.saveUserDataEntryCompleted()
//        }
//        user.value?.let { authRepo.saveUserIntoPreferences(it) }
        if (user.value == null) {
            _uiState.emit(uiState.value.copy(isNextButtonEnabled = true))
            _events.emit(UserDetailsEvents.ShowToast("There's been an error"))
        }
        _uiState.emit(uiState.value.copy(isLoading = false))
        _events.emit(UserDetailsEvents.NavigateToHomeScreen)
    }

    fun saveUser(user: User) = viewModelScope.launch {
        authRepo.saveUserIntoPreferences(user)
        authRepo.saveUserDataEntryCompleted()
    }





}