package com.kualagames.shared.utils

import co.touchlab.kermit.Logger

object Log {

    init {
        Logger.setTag("MainLogger")
    }

    fun i(message: () -> String) {
        Logger.Companion.i(message)
    }

    fun i(message: String) {
        i { message }
    }

    fun d(message: () -> String) {
        Logger.Companion.d(message)
    }

    fun e(t: Throwable, message: () -> String) {
        Logger.Companion.e(t, message)
    }
}