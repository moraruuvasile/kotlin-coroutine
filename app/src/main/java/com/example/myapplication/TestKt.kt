package com.example.myapplication

fun main() {
    for (i in 1..10){
        if (i%2 == 0) {
            return
        }
        println(i)
    }

}