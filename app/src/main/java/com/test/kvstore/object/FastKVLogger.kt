package com.test.kvstore.`object`

import android.util.Log
import io.fastkv.FastKV

object FastKVLogger : FastKV.Logger {
    private const val tag = "FastKV"

    override fun i(name: String, message: String) {
        if(message != "gc finish"){
            Log.i(tag, "$name $message")
        }
    }

    override fun w(name: String, e: Exception) {
        Log.w(tag, name, e)
    }

    override fun e(name: String, e: Exception) {
        Log.e(tag, name, e)
    }
}