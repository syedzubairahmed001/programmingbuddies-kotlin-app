package com.example.programmingbuddies.repository

import android.util.Log
import com.example.programmingbuddies.api.BuddyApi
import com.example.programmingbuddies.api.ServiceBuilder
import com.example.programmingbuddies.models.User
import com.example.programmingbuddies.models.UserUpdate
import com.example.programmingbuddies.models.UserUpdateResponse
import com.example.programmingbuddies.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Response
import javax.inject.Inject
import javax.security.auth.callback.Callback

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

    suspend fun saveUserIntoPreferences(user: User){
        Log.d("AuthViewModel", "saveUser: "+user)
        preferencesRepo.saveUserData(user)
        //TODO-Uncomment the previous and comment the below line later
//        preferencesRepo.saveUserData("User")
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




//    suspend fun isUserRegistered(user: User): Resource<Boolean> {}

}