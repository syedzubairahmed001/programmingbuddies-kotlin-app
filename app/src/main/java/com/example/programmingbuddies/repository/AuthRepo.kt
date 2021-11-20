package com.example.programmingbuddies.repository

import com.example.programmingbuddies.models.User
import javax.inject.Inject

class AuthRepo @Inject constructor(
    private val preferencesRepo: PreferencesRepo
) {

    companion object {
        private const val USER_NOT_LOGGED_IN = "User is not logged in"
    }

    suspend fun getCurrentUser() = preferencesRepo.getUserData()

    fun getUserDataAsFlow() = preferencesRepo.getUserDataFLow()

    suspend fun isUserLoggedIn() = getCurrentUser()!=null

    suspend fun isUserOnBoardingCompleted() = preferencesRepo.getOnBoardingCompleted() != null

    suspend fun isUserDataEntryCompleted() = preferencesRepo.getUserDataCompleted() != null

    suspend fun saveUserOnBoardingCompleted() = preferencesRepo.saveOnBoardingCompleted()

    suspend fun saveUserDataEntryCompleted() = preferencesRepo.saveUserDataCompleted()

    private suspend fun saveUserIntoPreferences(user: User){
//        preferencesRepo.saveUserData(user)
        //TODO-Uncomment the previous and comment the below line later
        preferencesRepo.saveUserData("User")
    }

    private suspend fun removeUserFromPreferences(){
        preferencesRepo.removeUserData()
    }

    private suspend fun removeUserDataCompleted(){
        preferencesRepo.removeUserDataCompleted()
    }

    private suspend fun removeOnBoarding(){
        preferencesRepo.removeOnBoarding()
    }

}