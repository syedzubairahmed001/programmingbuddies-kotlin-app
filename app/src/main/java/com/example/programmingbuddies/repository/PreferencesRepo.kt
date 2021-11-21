package com.example.programmingbuddies.repository

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.example.programmingbuddies.models.User
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PreferencesRepo @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val USER_KEY = stringPreferencesKey("User")
        private val USER_DATA_COMPLETED_KEY = booleanPreferencesKey("UserData")
        private val ON_BOARDING_KEY = booleanPreferencesKey("OnBoarding")
    }

    suspend fun saveUserData(user: User) = withContext(Dispatchers.IO) {
        Log.d("AuthViewModel", "saveUser: "+user)
        val userSerialized = Gson().toJson(user)
        Log.d("AuthViewModel", "saveUser: "+userSerialized)
        dataStore.edit {
            Log.d("AuthViewModel", "saveUserInEdit: "+userSerialized)
            it[USER_KEY] = userSerialized
        }
    }

    suspend fun removeUserData() = withContext(Dispatchers.IO) {
        dataStore.edit {
            it.remove(USER_KEY)
        }
    }

    suspend fun getUserData(): User? = withContext(Dispatchers.IO) {
        dataStore.data.map {
            Log.d("AuthViewModel", "getUser: "+it[USER_KEY])
            val userSerialize = it[USER_KEY]
            return@map userSerialize?.let { sUser->
                Log.d("AuthViewModel", "getUser: "+sUser)
                Log.d("AuthViewModel", "getUser: "+Gson().fromJson(sUser,User::class.java))
                Gson().fromJson(sUser,User::class.java)
            }
        }.first()
    }

    fun getUserDataFLow() = dataStore.data.map {
        return@map it[USER_KEY]
    }

    suspend fun saveUserDataCompleted() = withContext(Dispatchers.IO) {
        dataStore.edit {
            it[USER_DATA_COMPLETED_KEY] = true
        }
    }

    suspend fun removeUserDataCompleted() = withContext(Dispatchers.IO) {
        dataStore.edit {
            it[USER_DATA_COMPLETED_KEY] = false
        }
    }

    suspend fun getUserDataCompleted(): Boolean? = withContext(Dispatchers.IO) {
        dataStore.data.map {
            return@map it[USER_DATA_COMPLETED_KEY]
        }.first()
    }

    suspend fun saveOnBoardingCompleted() = withContext(Dispatchers.IO) {
        dataStore.edit {
            it[ON_BOARDING_KEY] = true
        }
    }

    suspend fun removeOnBoarding() = withContext(Dispatchers.IO) {
        dataStore.edit {
            it.remove(ON_BOARDING_KEY)
        }
    }

    suspend fun getOnBoardingCompleted() = dataStore.data.map {
        it[ON_BOARDING_KEY]
    }.first()

}