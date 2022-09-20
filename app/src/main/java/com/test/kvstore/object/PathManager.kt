package com.test.kvstore.`object`

object PathManager {
    val filesDir: String = GlobalConfig.appContext.filesDir.absolutePath
    val fastKVDir: String = "$filesDir/fastkv"
}