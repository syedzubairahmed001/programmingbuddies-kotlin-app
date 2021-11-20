package com.example.programmingbuddies.ui.auth

import androidx.lifecycle.ViewModel
import com.example.programmingbuddies.repository.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(private val authRepo: AuthRepo): ViewModel() {

    companion object {
        private const val FAILED_TO_LOGIN = "Failed to log you in. Please try again"
        private const val LOGIN_SUCCESS = "User logged in successfully"
    }

}