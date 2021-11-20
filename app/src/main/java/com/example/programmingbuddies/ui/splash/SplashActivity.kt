package com.example.programmingbuddies.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.programmingbuddies.MainActivity
import com.example.programmingbuddies.R
import com.example.programmingbuddies.databinding.ActivitySplashBinding
import com.example.programmingbuddies.ui.auth.AuthActivity
import com.example.programmingbuddies.ui.onboarding.OnBoardingActivity
import com.example.programmingbuddies.ui.userdetails.UserDetailsActivity
import com.example.programmingbuddies.util.makeStatusBarTransparent
import com.example.programmingbuddies.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    companion object{
        private const val SPLASH_SCREEN_DURATION = 2000L
    }

    private val viewModel by viewModels<SplashScreenViewModel>()
    private val binding by viewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        makeStatusBarTransparent()
        collectUiEvents()
        animateLogo()
        Handler().postDelayed({
            viewModel.onTimerComplete()
        }, SPLASH_SCREEN_DURATION)
    }

    private fun animateLogo() {
        val animation = AnimationUtils.loadAnimation(this,R.anim.scale_logo)
        binding.logo.apply {
            startAnimation(animation)
        }
    }

    private fun collectUiEvents() = lifecycleScope.launchWhenStarted {
        viewModel.events.collect{splashScreenEvent->
            when(splashScreenEvent){
                SplashScreenEvents.NavigateToHomeScreen -> navigateToHomeScreen()
                SplashScreenEvents.NavigateToUserDetailsScreen -> navigateToUserDetailsScreen()
                SplashScreenEvents.NavigateToLoginScreen -> navigateToLoginScreen()
                SplashScreenEvents.NavigateToOnBoarding -> navigateToOnBoarding()
            }
        }
    }

    private fun navigateToOnBoarding() {
        Intent(this, OnBoardingActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun navigateToLoginScreen() {
        Intent(this, AuthActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun navigateToUserDetailsScreen() {
        Intent(this, UserDetailsActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun navigateToHomeScreen() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }
}