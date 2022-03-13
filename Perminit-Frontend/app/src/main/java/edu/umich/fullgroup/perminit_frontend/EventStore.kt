package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import java.io.*
import java.time.LocalDate
import java.time.LocalTime


object EventStore {
    var events: HashMap<LocalDate, ArrayList<Event>> = HashMap<LocalDate, ArrayList<Event>>()
    var id = 0

    // store events to local storage
    fun store(context: Context) {
        val data = Gson().toJson(this.events)
        try {
            File("eventData").writeText(data)
        } catch (e: IOException) {
            Log.e("Save on close", "File write failed: $e")
        }
        // store id
        val preferences = context.getSharedPreferences("state", Context.MODE_PRIVATE)
        preferences.edit().putInt("id", this.id).apply()
    }

    // load events from local storage
    fun load(context: Context) {
        try {
            val bufferedReader: BufferedReader = File("eventData").bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            this.events = Gson().fromJson(inputString, HashMap<LocalDate, ArrayList<Event>>()::class.java)
        } catch (e: IOException){
            Log.e("load on startup", "File read fiailed: $e")
        }
        // load id
        val preferences = context.getSharedPreferences("state", Context.MODE_PRIVATE)
        this.id = preferences.getInt("id", 0)
    }

    fun add(event: Event, date: LocalDate) {
        if (!this.events.containsKey(date)) {
            this.events[date] = ArrayList()
        }
        this.events[date]?.add(event)
    }
}