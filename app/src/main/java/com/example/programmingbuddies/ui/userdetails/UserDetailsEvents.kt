package com.example.programmingbuddies.ui.userdetails

sealed class UserDetailsEvents {
    object NavigateToHomeScreen : UserDetailsEvents()
    data class ShowToast(val message:String): UserDetailsEvents()
    object ShowNoInternetDialog : UserDetailsEvents()

}