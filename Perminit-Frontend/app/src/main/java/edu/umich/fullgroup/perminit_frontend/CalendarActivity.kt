package edu.umich.fullgroup.perminit_frontend

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder

class CalendarActivity : AppCompatActivity() {
    var dataset = ArrayList<Event>()
    private lateinit var recyclerView: RecyclerView
    fun toCalendar(view: View?) = finish()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        recyclerView = findViewById<RecyclerView>(R.id.calendarMiniTodo)
        recyclerView.adapter = CalendarEventAdapter(this, dataset)

        var calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            var date = LocalDate.of(i, i2 + 1, i3)
            if (EventStore.events[date].isNullOrEmpty()) {
                dataset.clear()
            } else {
                dataset = EventStore.events[date]!!
            }
            recyclerView.adapter?.notifyDataSetChanged()
        }
    }

}