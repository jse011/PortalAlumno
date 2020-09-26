package com.consultoraestrategia.ss_portalalumno.firebase.online

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

public abstract class CourtineAsync<T> : CoroutineScope {
    private var job: Job = Job()
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job // to run code in Main(UI) Thread

    // call this method to cancel a coroutine when you don't need it anymore,
    // e.g. when user closes the screen
    fun cancel() {
        job.cancel()
    }

    fun execute() = launch {
        onPreExecute()
        val result = doInBackground() // runs in background thread without blocking the Main Thread
        onPostExecute(result)
    }

    private suspend fun doInBackground(): T = withContext(Dispatchers.IO) { // to run code in Background Thread
        // do async work
        return@withContext onExecute()
    }

    abstract fun onExecute():T// show progress

    // Runs on the Main(UI) Thread
    abstract fun onPreExecute()// show progress
    // Runs on the Main(UI) Thread
    abstract fun onPostExecute(result: T);  // hide progress
}