package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.User
import com.example.myapplication.repository.Repository


class MainViewModel : ViewModel() {
    lateinit var currentUser: LiveData<User>

    fun initUserId(id: String) {
        currentUser = Repository.getUser(id)
    }

    fun cancelJobs() {
        Repository?.cancelJobs()
    }
}



