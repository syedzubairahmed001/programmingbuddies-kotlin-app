package com.example.programmingbuddies.ui.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.example.programmingbuddies.R
import com.example.programmingbuddies.databinding.ActivityOnBoardingBinding
import com.example.programmingbuddies.ui.adapter.OnBoardingAdapter
import com.example.programmingbuddies.ui.auth.AuthActivity
import com.example.programmingbuddies.util.makeStatusBarTransparent
import com.example.programmingbuddies.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class OnBoardingActivity : AppCompatActivity() {

    private val viewModel by viewModels<OnBoardingViewModel>()
    private val binding by viewBinding(ActivityOnBoardingBinding::inflate)
    private lateinit var onBoardingAdapter: OnBoardingAdapter
    private lateinit var animation: Animation
    private lateinit var viewPager2 : ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding)
        makeStatusBarTransparent()
        onBoardingAdapter = OnBoardingAdapter(viewModel.onboardingList)
        animation = AnimationUtils.loadAnimation(this,R.anim.scale_logo)
        viewPager2 =  findViewById<ViewPager2>(R.id.viewpagerOnBoarding)
        setUpViewPager()
        collectUiState()
        collectUiEvents()
        binding.skipButtonSplash.setOnClickListener {
            viewModel.onSkipButtonPressed()
        }
        binding.nextButtonSplash.setOnClickListener {
            viewModel.onNextButtonPressed()
        }
        binding.dotsIndicatorSplash.dotsClickable = false
    }

    private fun collectUiEvents() = lifecycleScope.launchWhenStarted {
        viewModel.uiEvents.collect { event->
            when(event){
                OnBoardingEvents.NavigateToLoginScreen -> navigateToLoginScreen()
                is OnBoardingEvents.ShowNextPage -> showNextPage(event.pageNo)
            }
        }
    }

    private fun showNextPage(pageNo: Int) {
        binding.viewpagerOnBoarding.setCurrentItem(pageNo,true)
    }

    private fun navigateToLoginScreen() {
        Intent(this,AuthActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun collectUiState() = lifecycleScope.launchWhenStarted {
        viewModel.uiState.collect {state->
            binding.titleOnBoarding.text = state.title
            binding.titleOnBoarding.startAnimation(animation)
            binding.subtitleOnBoarding.text = state.subtitle
            binding.subtitleOnBoarding.startAnimation(animation)
            binding.skipButtonSplash.isVisible = state.isSkipButtonVisible
        }
    }

    private fun setUpViewPager() {
        binding.viewpagerOnBoarding.apply {
            isUserInputEnabled = false
            adapter = onBoardingAdapter
        }
        binding.viewpagerOnBoarding.adapter = onBoardingAdapter

        viewPager2.adapter = onBoardingAdapter
        binding.dotsIndicatorSplash.setViewPager2(binding.viewpagerOnBoarding)
    }
}