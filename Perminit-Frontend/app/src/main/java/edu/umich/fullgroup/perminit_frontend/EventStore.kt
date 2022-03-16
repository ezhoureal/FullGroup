package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import java.io.*
import java.time.LocalDate


object EventStore {
    var events = HashMap<LocalDate, ArrayList<Event>>()
    var list = ArrayList<Event>()
    var id_idx = 0

    // store events to local storage
    fun store(context: Context) {
        val data = Gson().toJson(this.events)
        try {
            File(context.filesDir, "eventData").printWriter().use { out ->
                out.println(data)
            }
        } catch (e: IOException) {
            Log.e("Save on close", "File write failed: $e")
        }
        // store id
        val preferences = context.getSharedPreferences("state", Context.MODE_PRIVATE)
        preferences.edit().putInt("id", this.id_idx).apply()
    }

    // load events from local storage
    fun load(context: Context) {
        Log.d("store", "start loading")
        try {
            val bufferedReader: BufferedReader = File("eventData").bufferedReader()
            val inputString = bufferedReader.use { it.readText() }
            this.events = Gson().fromJson(inputString, HashMap<LocalDate, ArrayList<Event>>()::class.java)
        } catch (e: IOException){
            Log.e("load on startup", "File read fiailed: $e")
        }
        // load id
        val preferences = context.getSharedPreferences("state", Context.MODE_PRIVATE)
        this.id_idx = preferences.getInt("id", 0)

        updateList()
    }

    fun add(event: Event, date: LocalDate) {
        if (!this.events.containsKey(date)) {
            this.events[date] = ArrayList()
        }
        this.events[date]?.add(event)

        // add event to list
        updateList()
    }

    fun updateList() {
        list.clear()
        // filter and sort events to display on the to-do list
        for ((date, array) in EventStore.events) {
            if (date >= LocalDate.now()) {
                list += array
            }
        }
        list.sortedWith(compareBy({ it.date }, { it.startTime }))
    }
}