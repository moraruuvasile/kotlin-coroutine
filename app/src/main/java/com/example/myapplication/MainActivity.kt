package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlin.system.measureTimeMillis

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            setNewText("Click!")

            CoroutineScope(IO).launch {
                fakeApiRequestI()
            }
        }

    }


    private fun setNewText(input: String) {
        val newText = text.text.toString() + "\n$input"
        text.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            setNewText(input)
        }
    }

    private fun fakeApiRequestI() {
        CoroutineScope(IO).launch {
            val execitonTime = measureTimeMillis {
                val result1 = async {
                    println("debug: launching async job1 in thread: ${Thread.currentThread().name}")
                    getResult1FromApi()
                }.await()

                val result2 = async {
                    println("debug: launching async job2 in thread: ${Thread.currentThread().name}")

                    try{
                        getResult2FromApi(result1+"a")
                    } catch (e: CancellationException){
                        e.message
                    }
                }.await()

                println("debug: got result2: $result2 ")
            }
            println("debug: compeleted job2 in $execitonTime ms.")

        }

    }


    private suspend fun getResult1FromApi(): String {
        delay(1000)
        return "Result #1"
    }

    private suspend fun getResult2FromApi(result: String): String {
        delay(2500)
        if(result.equals("Result #1"))
            return "Result #2"
        throw CancellationException("Result 1 was incorect Baiiii")
    }

}

