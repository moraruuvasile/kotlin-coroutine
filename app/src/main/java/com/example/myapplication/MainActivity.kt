package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn.setOnClickListener {
            this.setNewText("Click!")

            CoroutineScope(IO).launch {
                fakeApiRequest()
            }
            this.setNewText("Finish!")
        }

    }

    private fun setNewText(input: String) {
        val newText = tv.text.toString() + "\n$input"
        tv.text = newText
    }

    private suspend fun setTextOnMainThread(input: String) {
        withContext(Main) {
            setNewText(input)
        }
    }

    private suspend fun fakeApiRequest() {
        logThread("fakeApiRequest")
        withContext(IO) {
            withTimeoutOrNull(2050){
                setTextOnMainThread("Got ${getResult1FromApi()}")
                setTextOnMainThread("Got ${getResult2FromApi()}")
            } ?: setTextOnMainThread("Job time out MF")
        }

    }


    private suspend fun getResult1FromApi(): String {
        logThread("getResult1FromApi")
        delay(1000) // Does not block thread. Just suspends the coroutine inside the thread
        return "Result #1"
    }

    private suspend fun getResult2FromApi(): String {
        logThread("getResult2FromApi")
        delay(1000)
        return "Result #2"
    }

    private fun logThread(methodName: String) {
        println("debug: ${methodName}: ${Thread.currentThread().name}")
    }

}
