package com.example.programmingbuddies.ui.auth

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingbuddies.models.User
import com.example.programmingbuddies.repository.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo: AuthRepo): ViewModel() {

    companion object {
        private const val FAILED_TO_LOGIN = "Failed to log you in. Please try again"
        private const val LOGIN_SUCCESS = "User logged in successfully"
    }

    private val user = MutableStateFlow<User?>(null)

    private val _uiState = MutableStateFlow(AuthScreenState())
    val uiState: StateFlow<AuthScreenState> = _uiState

    private val _uiEvents = MutableSharedFlow<AuthScreenEvents>()
    val uiEvents: SharedFlow<AuthScreenEvents> = _uiEvents

    fun saveUser(useri:User) = viewModelScope.launch {
//        Log.d("AuthViewModel", "saveUser: "+useri)
        user.emit(useri)
    }

    fun startLoading() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isLoading = true,isButtonEnabled = false))
    }

    fun stopLoading() = viewModelScope.launch {
        _uiState.emit(_uiState.value.copy(isLoading = false,isButtonEnabled = true))
    }

    fun sendError(message:String) = viewModelScope.launch {
        stopLoading()
        _uiEvents.emit(AuthScreenEvents.ShowToast(message = message))
    }
//
    fun loginComplete(userh: User) = viewModelScope.launch {

    Log.d("AuthViewModel", "saveUser: "+userh)
        userh.let {
            Log.d("AuthViewModel", "saveUser: "+it)
            authRepo.saveUserIntoPreferences(it)
            if (it.linkedin==""){
                _uiEvents.emit(AuthScreenEvents.NavigateToUserDetailsScreen)
            }else{
                _uiEvents.emit(AuthScreenEvents.NavigateToHomeScreen)
            }
        }
    }



}