package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    lateinit var viewModel2: MainViewModel2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       ////I view model
       viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
       viewModel.initUserId("1")
       viewModel.currentUser.observe(this, Observer<User> {
           println("DEBUG: $it")
       })

       ////II view model
       viewModel2 = ViewModelProvider(this).get(MainViewModel2::class.java)
       viewModel2.user.observe(this, Observer {
           println("DEBUG2: $it")
       })
       viewModel2.getUserById().observe(this, Observer {
           println("DEBUG3: $it")
       })

       CoroutineScope(Main).launch() {
           delay(4000)
           viewModel2.setUserId("1")
       }


        //       println("DEBUG: ExampleSingleton: ${ExampleSingleton.singletonUser}")
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancelJobs()
        }

}



