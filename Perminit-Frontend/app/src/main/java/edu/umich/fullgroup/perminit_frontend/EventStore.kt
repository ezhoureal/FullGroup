package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.*
import java.time.LocalDate


object EventStore {
    var events: HashMap<LocalDate, ArrayList<Event>> = HashMap<LocalDate, ArrayList<Event>>()

    // store events to local storage
    fun store() {
        val data = Gson().toJson(this.events)
        try {
            File("eventData").writeText(data)
        } catch (e: IOException) {
            Log.e("Save on close", "File write failed: $e")
        }
    }

    // load events from local storage
    fun load() {
        try {
            val bufferedReader: BufferedReader = File("eventData").bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            this.events = Gson().fromJson(inputString, HashMap<LocalDate, ArrayList<Event>>()::class.java)
        } catch (e: IOException){
            Log.e("load on startup", "File read fiailed: $e")
        }
    }
}