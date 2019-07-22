package com.okay.sampletamplate.sample.executor

import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class TestExecutor : Executor{

    val pool:ExecutorService = Executors.newSingleThreadExecutor { runnable ->
        val thread:Thread = Thread(runnable)
        thread.name = "action_annotation_executor"
        return@newSingleThreadExecutor thread
    }

    override fun execute(command: Runnable?) {
        pool.execute(command)
    }
}