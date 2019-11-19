package com.zakumi.viewModel

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class CoroutinePresenter(
    private val presenterContext: CoroutineContext
) : CoroutineScope {

    private val job = Job()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        println("Caught $throwable")
    }

    override val coroutineContext: CoroutineContext
        get() = presenterContext + job + exceptionHandler

    abstract fun onCreate()

    open fun onDestroy() {
        job.cancel()
    }

}