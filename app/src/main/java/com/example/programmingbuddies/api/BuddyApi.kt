package com.example.programmingbuddies.api

import com.example.programmingbuddies.models.UserUpdate
import com.example.programmingbuddies.models.UserUpdateResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface BuddyApi {

    @Headers("Content-Type: application/json")
    @POST("updateProfile")
    suspend fun updateProfile(
        @Header("Authorization")
        emailHeader: String,
        @Body
        userUpdate: UserUpdate
    ) : Call<UserUpdateResponse>

}