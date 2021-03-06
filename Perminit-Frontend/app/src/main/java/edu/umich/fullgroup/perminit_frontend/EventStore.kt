package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import java.io.FileNotFoundException
import java.time.LocalDate
import java.time.LocalTime


object EventStore {
    var events = HashMap<LocalDate, ArrayList<Event>>()
    var list = mutableListOf<Event>()
    var id_idx = 0

    // store events to local storage
    suspend fun store(context: Context) {
        withContext(Dispatchers.IO) {              // Dispatchers.IO (main-safety block)
            var f = File(context.filesDir, "eventData.json")
            if (!f.exists()) {
                f.createNewFile()
            }

            var data = ""
            for ((date, list) in EventStore.events) {
                for (event in list) {
                    data += Json.encodeToString(event) + "\n"
                }
            }

            f.writeText(data)

            // store id
            val preferences = context.getSharedPreferences("state", Context.MODE_PRIVATE)
            preferences.edit().putInt("id", id_idx).apply()
        }
    }

    // load events from local storage
    fun load(context: Context) {

        var f = File(context.filesDir, "eventData.json")
        try {
                val data = f.readLines()
                try {
                for (line in data) {
                    val event = Json.decodeFromString<Event>(line)
                    this.add(event, event.date)
                }
                this.updateList()
            } catch (e:Exception) {
                print(e)
            }
        } catch (f: FileNotFoundException) {
            val e = Event(-1, "Record 441 Video", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
            this.add(e, LocalDate.now())
            this.updateList()
        }

        // load id
        val preferences = context.getSharedPreferences("state", Context.MODE_PRIVATE)
        this.id_idx = preferences.getInt("id", 0)

    }

    fun add(event: Event, date: LocalDate) {
        if (!this.events.containsKey(date)) {
            this.events[date] = ArrayList()
        }
        this.events[date]?.add(event)

    }

    fun updateList() {
        list.clear()
        // filter and sort events to display on the to-do list
        for ((date, array) in events) {
            if (date >= LocalDate.now()) {
                for (e in array) {
                    // only add completed events
                    if (!e.completed) {
                        list.add(e)
                    }
                }
            }
        }
        list.sortBy{ it.date }
    }
}