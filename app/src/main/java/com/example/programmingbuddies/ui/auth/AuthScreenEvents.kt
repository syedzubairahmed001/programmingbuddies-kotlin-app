package com.example.programmingbuddies.ui.auth

sealed class AuthScreenEvents{
    data class ShowToast(val message:String) : AuthScreenEvents()
    object NavigateToUserDetailsScreen : AuthScreenEvents()
    object NavigateToHomeScreen : AuthScreenEvents()
    object Logout : AuthScreenEvents()
}
