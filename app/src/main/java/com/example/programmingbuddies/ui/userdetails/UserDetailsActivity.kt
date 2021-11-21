package com.example.programmingbuddies.ui.userdetails

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.example.programmingbuddies.ui.home.MainActivity
import com.example.programmingbuddies.R
import com.example.programmingbuddies.databinding.ActivityUserDetailsBinding
import com.example.programmingbuddies.util.makeStatusBarTransparent
import com.example.programmingbuddies.util.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityUserDetailsBinding::inflate)
    private val viewModel by viewModels<UserDetailsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_details)
        makeStatusBarTransparent()

        collectUiState()
        collectUIEvents()
        binding.linkedinDetailsEdit.doOnTextChanged { text, _, _, _ ->
            viewModel.onLinkedInChange(text.toString())
        }

        binding.githubDetailsEdit.doOnTextChanged { text, _, _, _ ->
            viewModel.onGithubChange(text.toString())
        }

        binding.languageDetailsEdit.doOnTextChanged { text, _, _, _ ->
            viewModel.onLanguageChange(text.toString())
        }

        binding.codeDetailsEdit.doOnTextChanged { text, _, _, _ ->
            viewModel.onCodeChange(text.toString())
        }

        binding.nextButton.setOnClickListener {
            viewModel.onNextButtonClicked()
        }

        viewModel.getUser()
        Log.d("Current", "onCreate: "+viewModel.user.value)
        viewModel.user.observe(this){
            Log.d("Current", "onCreate: "+viewModel.user.value)
//            showToast(viewModel.user.value.toString())
//            binding.linkedinDetailsEdit.setText(it.linkedin)
//            binding.githubDetailsEdit.setText(it.github)
//            binding.languageDetailsEdit.setText(it.language)
//            binding.codeDetailsEdit.setText(it.profileProgram)
            viewModel.saveUser(it)
        }

//        Toast.makeText(this,viewModel.user?.profilePictureURL,Toast.LENGTH_SHORT).show()

    }

    private fun collectUIEvents() {
        lifecycleScope.launchWhenStarted {
            viewModel.events.collect {
                when(it){
                    UserDetailsEvents.NavigateToHomeScreen -> navigateToHomeScreen()
                    is UserDetailsEvents.ShowToast -> showToast(it.message)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private fun navigateToHomeScreen() {
        Intent(this, MainActivity::class.java).also {
            startActivity(it)
            finish()
        }
    }

    private fun collectUiState() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                binding.nextButton.isEnabled = it.isNextButtonEnabled
                binding.loadingLayout.loadingLayoutAnim.isVisible = it.isLoading
            }
        }
    }
}