package com.example.programmingbuddies.ui.onboarding

sealed class OnBoardingEvents {
    object NavigateToLoginScreen : OnBoardingEvents()
    class ShowNextPage(val pageNo:Int) : OnBoardingEvents()
}