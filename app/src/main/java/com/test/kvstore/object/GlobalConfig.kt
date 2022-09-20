package com.test.kvstore.`object`

import android.content.Context
import com.test.kvstore.BuildConfig

object GlobalConfig {
    val APPLICATION_ID: String = BuildConfig.APPLICATION_ID

    lateinit var appContext: Context

}