package com.example.programmingbuddies.ui.splash

sealed class SplashScreenEvents {
    object NavigateToOnBoarding : SplashScreenEvents()
    object NavigateToLoginScreen : SplashScreenEvents()
    object NavigateToUserDetailsScreen : SplashScreenEvents()
    object NavigateToHomeScreen : SplashScreenEvents()
}