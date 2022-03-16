package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
    fun addEvent(view: View?) = startActivity(Intent(this,AddReminder::class.java))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        EventStore.load(applicationContext)
        PerMinitStore.load()
        Log.d("data", EventStore.events.toString())

        val e = Event(1, "test", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
        EventStore.add(e, LocalDate.now())

        // filter and sort events to display on the to-do list
        var dataset = ArrayList<Event>()
        for ((date, array) in EventStore.events) {
            if (date >= LocalDate.now()) {
                dataset += array
            }
        }
        dataset.sortedWith(compareBy({ it.date }, { it.startTime }))

        val recyclerView = findViewById<RecyclerView>(R.id.eventList)
        recyclerView.adapter = EventAdapter(this, dataset)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)
    }
}