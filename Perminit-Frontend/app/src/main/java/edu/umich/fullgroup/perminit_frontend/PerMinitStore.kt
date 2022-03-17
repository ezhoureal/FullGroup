package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import getJsonDataFromAsset
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.time.LocalDate

object PerMinitStore {
    var minits = HashMap<String, PerMinit>()
    // store PerMinit to local storage
    fun store(context: Context) {
        val data = Gson().toJson(this.minits)
        try {
            File(context.getFilesDir(), "minitData.json").writeText(data)
        } catch (e: IOException) {
            Log.e("Save on close", "File write failed: $e")
        }
    }

    // load PerMinits from local storage
    fun load(context: Context) {
        val jsonFileString = getJsonDataFromAsset(context, "minitData.json")
        this.minits = Gson().fromJson(jsonFileString, HashMap<String, PerMinit>()::class.java)
    }
}