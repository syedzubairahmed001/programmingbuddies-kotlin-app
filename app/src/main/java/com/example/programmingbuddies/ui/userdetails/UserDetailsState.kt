package com.example.programmingbuddies.ui.userdetails

data class UserDetailsState(
    val linkedin :String = "",
    val github:String = "",
    val language: String = "",
    val code:String = "",
    val isNextButtonEnabled: Boolean = false,
    val isLoading: Boolean = false
)