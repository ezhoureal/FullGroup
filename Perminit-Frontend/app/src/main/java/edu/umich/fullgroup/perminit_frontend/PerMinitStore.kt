package edu.umich.fullgroup.perminit_frontend

import android.util.Log
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.File
import java.io.IOException

object PerMinitStore {
    var minits = ArrayList<PerMinit>()
    // store PerMinit to local storage
    fun store() {
        val data = Gson().toJson(this.minits)
        try {
            File("minitData").writeText(data)
        } catch (e: IOException) {
            Log.e("Save on close", "File write failed: $e")
        }
    }

    // load PerMinits from local storage
    fun load() {
        try {
            val bufferedReader: BufferedReader = File("minitData").bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            this.minits = Gson().fromJson(inputString, ArrayList<PerMinit>()::class.java)
        } catch (e: IOException){
            Log.e("load on startup", "File read fiailed: $e")
        }
    }

}