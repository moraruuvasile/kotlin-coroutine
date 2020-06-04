package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

        val result1 = getResult1FromApi() // wait until job is done

        setTextOnMainThread("Got $result1")

        val result2 = getResult2FromApi() // wait until job is done

        setTextOnMainThread("Got $result2")


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


