package edu.umich.fullgroup.perminit_frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CalendarView
import java.time.LocalDate

class CalendarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        var calendarView = findViewById<CalendarView>(R.id.calendarView)
        calendarView.setOnDateChangeListener { calendarView, i, i2, i3 ->
            var date: LocalDate = LocalDate.parse("$i3-$i2-$i")

        }
    }

}