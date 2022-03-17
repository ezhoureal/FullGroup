package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import getJsonDataFromAsset
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
            var eventData = File(context.getFilesDir(), "eventData.json")
            eventData.writeText(data)
        } catch (e: IOException) {
            Log.e("Save on close", "File write failed: $e")
        }
        /*
        PrintWriter(FileWriter("eventData.json", Charset.defaultCharset()))
            .use { it.write(data.toString()) }
        */
        // store id
        val preferences = context.getSharedPreferences("state", Context.MODE_PRIVATE)
        preferences.edit().putInt("id", this.id_idx).apply()
    }

    // load events from local storage
    fun load(context: Context) {
        val jsonFileString = getJsonDataFromAsset(context, "eventData.json")
        this.events = Gson().fromJson(jsonFileString, HashMap<LocalDate, ArrayList<Event>>()::class.java)
        // Log.d("store", "start loading")
        // try {
        //     var eventData = File(context.getFilesDir(), "eventData.json")
        //     if (eventData.length() != 0L) {
        //         val bufferedReader: BufferedReader = eventData.bufferedReader()
        //         val inputString = bufferedReader.use { it.readText() }
        //         this.events = Gson().fromJson(inputString, HashMap<LocalDate, ArrayList<Event>>()::class.java)
        //     }
        // } catch (e: IOException){
        //     Log.e("load on startup", "File read failed: $e")
        // }
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