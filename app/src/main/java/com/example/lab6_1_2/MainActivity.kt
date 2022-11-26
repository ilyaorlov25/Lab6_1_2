package com.example.lab6_1_2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView
import java.util.concurrent.ExecutorService
import java.util.concurrent.FutureTask

class MainActivity : AppCompatActivity() {
    private var seconds = 0
    private lateinit var textViewSeconds: TextView
    private lateinit var execService: ExecutorService
    private lateinit var futureTask: FutureTask<Any>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textViewSeconds = findViewById(R.id.textSeconds)
    }

    override fun onResume() {
        runSignalThreadExecutor()
        Log.d("App", "is opened")
        super.onResume()
    }

    override fun onPause() {
        futureTask.cancel(true)
        Log.d("App", "is collapsed")
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        outState.putInt(getString(R.string.seconds), seconds)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        seconds = savedInstanceState.getInt(getString(R.string.seconds))
        super.onRestoreInstanceState(savedInstanceState)
    }

    private fun runSignalThreadExecutor() {
        futureTask = FutureTask({
            while (!futureTask.isDone) {
                textViewSeconds.post {
                    textViewSeconds.text = getString(R.string.seconds, seconds++)
                }
                Log.d("Executor", "is executes")
                Thread.sleep(1000)
            }
        }, null)

        execService = (application as ThreadPool).singleThreadExecutor
        execService.execute(futureTask)
    }
}