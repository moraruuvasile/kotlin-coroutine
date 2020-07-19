package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.myapplication.model.User
import com.example.myapplication.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel2: ViewModel() {

    private val _userId: MutableLiveData<String> = MutableLiveData()

    val user: LiveData<User> = Transformations
        .switchMap(_userId) {
            Repository.getUser(it)
        }


    fun getUserById():LiveData<User>{
       return Transformations.switchMap(_userId){ Repository.getUser(it)}
    }

    fun setUserId(userId: String){
        if (_userId.value == userId) {
            return
        }
        _userId.value = userId
    }

    fun cancelJobs(){
        Repository.cancelJobs()
    }
}



