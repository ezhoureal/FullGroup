package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.LocalTime

class CalendarActivity : AppCompatActivity() {
    var data = mutableListOf<Event>()
    private lateinit var recyclerView: RecyclerView
    fun toCalendar(view: View?) = finish()
    fun addEvent(view: View?) = startActivity(Intent(this, AddReminder::class.java))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        if (!EventStore.events[LocalDate.now()].isNullOrEmpty()) {
            for (e in EventStore.events[LocalDate.now()]!!) {
                data.add(e)
            }
        }
        recyclerView = findViewById(R.id.calendarMiniTodo)
        recyclerView.adapter = CalendarEventAdapter(this, data)
        recyclerView.setHasFixedSize(true)

        var calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            var date = LocalDate.of(i, i2 + 1, i3)
            data.clear()
            if (!EventStore.events[date].isNullOrEmpty()) {
                for (e in EventStore.events[date]!!) {
                    data.add(e)
                }
            }
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

}