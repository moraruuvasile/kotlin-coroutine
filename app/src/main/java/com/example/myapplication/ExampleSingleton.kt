package com.example.myapplication

import com.example.myapplication.model.User

object ExampleSingleton {

    val singletonUser: User by lazy{
        User("vasae", "ion", "some_image_url.png")
    }
}

