package com.example.lab6_1_2

import android.app.Application
import android.util.Log
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class ThreadPool: Application() {
    val singleThreadExecutor: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        Log.d("App", "is created")
    }
}