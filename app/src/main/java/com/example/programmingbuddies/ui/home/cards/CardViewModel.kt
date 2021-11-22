package com.example.programmingbuddies.ui.home.cards

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.programmingbuddies.models.User
import com.example.programmingbuddies.repository.AuthRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(authRepo: AuthRepo) : ViewModel() {

    private val _usersList : MutableLiveData<List<User>> = MutableLiveData()
    val usersList: LiveData<List<User>> = _usersList

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        var userList: MutableList<User> = mutableListOf(
            User(
                name = "Hey1",
                profileProgram = "printf('hello world 1')",
                language = "javascript"
            ),
            User(
                name = "Hey2",
                profileProgram = "printf('hello world 2')",
                language = "javascript"
            ),
            User(
                name = "Hey3",
                profileProgram = "printf('hello world 3')",
                language = "javascript"
            ),
            User(
                name = "Hey4",
                profileProgram = "printf('hello world 4')",
                language = "javascript"
            ),
            User(
                name = "Hey5",
                profileProgram = "printf('hello world 5')",
                language = "javascript"
            )
        )
        _usersList.postValue(userList)
    }

    fun deleteUser() {
        var userList = usersList.value
        userList?.drop(1)
        _usersList.postValue(userList)
    }

}