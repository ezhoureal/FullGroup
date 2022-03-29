package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarActivity : AppCompatActivity() {
    var dataset = ArrayList<Event>()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        recyclerView = findViewById<RecyclerView>(R.id.calendarMiniTodo)
        recyclerView.adapter = CalendarEventAdapter(this, dataset)

        var calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            var date = LocalDate.parse("$i3-$i2-$i")
            dataset = EventStore.events[date]!!
        }

        val button: Button = findViewById(R.id.todoButton3)
        button.setOnClickListener {
            val intent = Intent(this@CalendarActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

}