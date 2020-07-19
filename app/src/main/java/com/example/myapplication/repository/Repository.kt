package com.example.myapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.api.RetrofitBuilder
import com.example.myapplication.model.User
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

object Repository {

    var job: CompletableJob? = null


    fun getUser(userId: String): MutableLiveData<User> {
        job = Job()
        return object : MutableLiveData<User>() {
            override fun onActive() {
                super.onActive()
                job?.let { theJob ->
                    CoroutineScope(IO + theJob).launch {
                        val user = RetrofitBuilder.apiService.getUser(userId)
                        //                     withContext(Main){
                        postValue(user)
                        //                           value = user
                        theJob.complete()
                        //                       }
                    }
                }
            }
        }
    }

    fun cancelJobs() {
        job?.cancel()
    }

}