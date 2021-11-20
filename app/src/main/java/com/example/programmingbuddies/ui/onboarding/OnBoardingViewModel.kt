package com.example.programmingbuddies.ui.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.programmingbuddies.repository.AuthRepo
import com.example.programmingbuddies.util.onBoardingList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(private val authRepo: AuthRepo) : ViewModel() {

    val onboardingList = onBoardingList

    private val _uiState = MutableStateFlow(OnBoardingState())
    val uiState: StateFlow<OnBoardingState> = _uiState

    private val _uiEvents = MutableSharedFlow<OnBoardingEvents>()
     val uiEvents: SharedFlow<OnBoardingEvents> = _uiEvents

    private val page = MutableStateFlow(0)

    init {
        collectPage()
    }

    private fun collectPage() = viewModelScope.launch {
        page.collect {
            val onboarding = onBoardingList[it]
            _uiState.emit(
                OnBoardingState(
                    title = onboarding.title,
                    subtitle = onboarding.subtitle,
                    isSkipButtonVisible = it!=(onboardingList.size - 1)
                )
            )
        }
    }

    fun onNextButtonPressed() = viewModelScope.launch {
        val newPage = page.value + 1
        if(newPage == onboardingList.size){
            saveOnBoardingComplete()
            _uiEvents.emit(OnBoardingEvents.NavigateToLoginScreen)
        }else{
            _uiEvents.emit(OnBoardingEvents.ShowNextPage(newPage))
            page.emit(newPage)
        }
    }

    fun onSkipButtonPressed() = viewModelScope.launch {
        saveOnBoardingComplete()
        _uiEvents.emit(OnBoardingEvents.NavigateToLoginScreen)
    }

    suspend fun saveOnBoardingComplete() {
        authRepo.saveUserOnBoardingCompleted()
    }


}