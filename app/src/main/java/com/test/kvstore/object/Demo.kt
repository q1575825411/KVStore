package com.test.kvstore.`object`

import android.content.Context
import android.util.Log
import android.util.Pair
import androidx.datastore.preferences.core.*
import com.tencent.mmkv.MMKV
import io.fastkv.FastKV
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.io.File
import java.util.*

object Demo {

    private const val TAG = "tianYou"
    private const val name =
        "After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB. After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB. After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB.After code cache collection, code=23KB, data=29KB."


    //    private const val name = "After code cache collection, code=23KB, data=29KB."
    //    private const val name = "After"
    private val size = 1000000000.0
    private const val round = 10
    private const val file_number = 100


    private val sp = GlobalConfig.appContext.getSharedPreferences(
        "sp", Context.MODE_PRIVATE
    )

    private val dataStore = PreferenceDataStoreFactory.create {
        File(
            PathManager.filesDir,
            "data_store.preferences_pb"
        )
    }
    private val mmkv = MMKV.defaultMMKV()
    private val fastkv = FastKV.Builder(PathManager.fastKVDir, "fastkv").build()

    suspend fun start(b: Boolean) {
        kvCompare(b)
    }

    private suspend fun kvCompare(b: Boolean) {
        val srcList = initList()
        Log.i(TAG, "Start test，单个文本大小为" + name.length + "，文件个数为" + file_number + "，重复次数为" + round)
        val time = LongArray(10)
        Arrays.fill(time, 0L)
        val inputList: List<Pair<String, Any>>
        inputList = ArrayList(srcList)
        var flag: Long = 0
        if (b) {
            sp.edit().clear().apply()
            dataStore.edit { it.clear() }
            mmkv.clear()
            fastkv.clear()

            for (i in 0 until round) {
                val t1 = System.nanoTime()
                applyToSp(inputList)
                val t2 = System.nanoTime()
                flag += (t2 - t1)
            }
            time[0] = flag
            flag = 0
//            for (i in 0 until round) {
//                val t1 = System.nanoTime()
//                putToMMKV(inputList)
//                val t2 = System.nanoTime()
//                flag += (t2 - t1)
//            }
//            time[1] = flag
//            flag = 0

            for (i in 0 until round) {
                val t1 = System.nanoTime()
                putToFastKV(inputList)
                val t2 = System.nanoTime()
                flag += (t2 - t1)
            }
            time[2] = flag
            flag = 0
//            for (i in 0 until round) {
//                val t1 = System.nanoTime()
//                applyToDataStore(inputList)
//                val t2 = System.nanoTime()
//                flag += (t2 - t1)
//            }
//            time[3] = flag
//            flag = 0
            Log.i(TAG, "单个文本大小为" + name.length + "，文件个数为" + file_number + "，重复次数为" + round)
            Log.i(TAG, "写入时各部分预计的平均时间如下（时间单位为纳秒）")
            Log.i(TAG, "SP: " + time[0] / round)
            Log.i(TAG, "MM: " + time[1] / round)
            Log.i(TAG, "FV: " + time[2] / round)
            Log.i(TAG, "DS: " + time[3] / round)
            Log.i(TAG, "flag: $flag")
        } else {

            for (i in 0 until round) {
                val t1 = System.nanoTime()
                readFromSp(inputList)
                val t2 = System.nanoTime()
                flag += (t2 - t1)
            }
            time[4] = flag
            flag = 0
//            for (i in 0 until round) {
//                val t1 = System.nanoTime()
//                readFromMMKV(inputList)
//                val t2 = System.nanoTime()
//                flag += (t2 - t1)
//            }
//            time[5] = flag
//            flag = 0
            for (i in 0 until round) {
                val t1 = System.nanoTime()
                readFromFastKV(inputList)
                val t2 = System.nanoTime()
                flag += (t2 - t1)
            }
            time[6] = flag
            flag = 0
//            for (i in 0 until round) {
//                val t1 = System.nanoTime()
//                readFromDataStore(inputList)
//                val t2 = System.nanoTime()
//                flag += (t2 - t1)
//            }
//            time[7] = flag
//            flag = 0
            Log.i(TAG, "单个文本大小为" + name.length + "，文件个数为" + file_number + "，重复次数为" + round)
            Log.i(TAG, "读取时各部分预计的平均时间如下（时间单位为纳秒）")
            Log.i(TAG, "SP: " + time[4] / round)
            Log.i(TAG, "MM: " + time[5] / round)
            Log.i(TAG, "FV: " + time[6] / round)
            Log.i(TAG, "DS: " + time[7] / round)
            Log.i(TAG, "flag: $flag")

        }


    }

    private fun initList(): List<Pair<String, Any>> {
        val list = ArrayList<Pair<String, Any>>(file_number)
        for (index in 0 until file_number) {
            list.add(Pair(index.toString(), name))
        }
        return list
    }

    private fun applyToSp(list: List<Pair<String, Any>>) {
        val editor = sp.edit()
        for (pair in list) {
            val key = pair.first
            val value = pair.second
            if (value is String) {
                editor.putString(key, value).apply()
            } else if (value is Boolean) {
                editor.putBoolean(key, value).apply()
            } else if (value is Int) {
                editor.putInt(key, value).apply()
            } else if (value is Long) {
                editor.putLong(key, value).apply()
            } else if (value is Float) {
                editor.putFloat(key, value).apply()
            } else if (value is Set<*>) {
                editor.putStringSet(key, value as Set<String?>).apply()
            }

        }
//        editor.apply()
    }

    private suspend fun applyToDataStore(list: List<Pair<String, Any>>) {
        for (pair in list) {
            val key = pair.first
            val value = pair.second
            if (value is String) {
                dataStore.edit { it[stringPreferencesKey(key)] = value }
            } else if (value is Boolean) {
                dataStore.edit { it[booleanPreferencesKey(key)] = value }
            } else if (value is Int) {
                dataStore.edit { it[intPreferencesKey(key)] = value }
            } else if (value is Long) {
                dataStore.edit { it[longPreferencesKey(key)] = value }
            } else if (value is Float) {
                dataStore.edit { it[floatPreferencesKey(key)] = value }
            } else if (value is Set<*>) {
                dataStore.edit { it[stringSetPreferencesKey(key)] = value as Set<String> }
            }
        }
    }

    private suspend fun readFromDataStore(list: List<Pair<String, Any>>) {
        val value = dataStore.data.map { setting ->
            for (pair in list) {
                val key = pair.first
                val value = pair.second
                if (value is String) {
                    setting[stringPreferencesKey(key)]
                } else if (value is Boolean) {
                    setting[booleanPreferencesKey(key)]
                } else if (value is Int) {
                    setting[intPreferencesKey(key)]
                } else if (value is Long) {
                    setting[longPreferencesKey(key)]
                } else if (value is Float) {
                    setting[floatPreferencesKey(key)]
                } else if (value is Set<*>) {
                    setting[stringSetPreferencesKey(key)]
                }
            }
        }
        val v = value.first()
    }

    private fun putToMMKV(list: List<Pair<String, Any>>) {
        for (pair in list) {
            val key = pair.first
            val value = pair.second
            if (value is String) {
                mmkv.putString(key, value)
            } else if (value is Boolean) {
                mmkv.putBoolean(key, value)
            } else if (value is Int) {
                mmkv.putInt(key, value)
            } else if (value is Long) {
                mmkv.putLong(key, value)
            } else if (value is Float) {
                mmkv.putFloat(key, value)
            } else if (value is Set<*>) {
                mmkv.putStringSet(key, value as Set<String?>)
            }
        }
    }

    private fun putToFastKV(list: List<Pair<String, Any>>) {
        for (pair in list) {
            val key = pair.first
            val value = pair.second
            if (value is String) {
                fastkv.putString(key, value)
            } else if (value is Boolean) {
                fastkv.putBoolean(key, value)
            } else if (value is Int) {
                fastkv.putInt(key, value)
            } else if (value is Long) {
                fastkv.putLong(key, value)
            } else if (value is Float) {
                fastkv.putFloat(key, value)
            } else if (value is Set<*>) {
                fastkv.putStringSet(key, value as Set<String?>)
            }
        }
    }

    private fun readFromSp(list: List<Pair<String, Any>>) {
        for (pair in list) {
            val key = pair.first
            val value = pair.second
            if (value is String) {
                sp.getString(key, "")
            } else if (value is Boolean) {
                sp.getBoolean(key, false)
            } else if (value is Int) {
                sp.getInt(key, 0)
            } else if (value is Long) {
                sp.getLong(key, 0L)
            } else if (value is Float) {
                sp.getFloat(key, 0f)
            } else if (value is Set<*>) {
                sp.getStringSet(key, null)
            }
        }
    }

    private fun readFromMMKV(list: List<Pair<String, Any>>) {
        for (pair in list) {
            val key = pair.first
            val value = pair.second
            if (value is String) {
                mmkv.getString(key, "")
            } else if (value is Boolean) {
                mmkv.getBoolean(key, false)
            } else if (value is Int) {
                mmkv.getInt(key, 0)
            } else if (value is Long) {
                mmkv.getLong(key, 0L)
            } else if (value is Float) {
                mmkv.getFloat(key, 0f)
            } else if (value is Set<*>) {
                mmkv.getStringSet(key, null)
            }
        }
    }

    private fun readFromFastKV(list: List<Pair<String, Any>>) {
        for (pair in list) {
            val key = pair.first
            val value = pair.second
            if (value is String) {
                fastkv.getString(key, "")
            } else if (value is Boolean) {
                fastkv.getBoolean(key, false)
            } else if (value is Int) {
                fastkv.getInt(key, 0)
            } else if (value is Long) {
                fastkv.getLong(key, 0L)
            } else if (value is Float) {
                fastkv.getFloat(key, 0f)
            } else if (value is Set<*>) {
                fastkv.getStringSet(key)
            }
        }
    }

}