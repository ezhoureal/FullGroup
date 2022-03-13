package edu.umich.fullgroup.perminit_frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import java.time.LocalDate
import java.time.LocalTime

class AddReminder : AppCompatActivity() {
    private lateinit var view: ActivityPostBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
        view = ActivityPostBinding.inflate(layoutInflater)
    }

    // called when submit button is pushed
    fun onSubmit() {
        // get data from view
        val date = LocalDate.now()
        val startTime = LocalTime.now()
        val endTime = LocalTime.now()
        val title = ""
        val

        // validation check
        if (title.isEmpty()) {
            Toast.makeText(this, "Title is empty", Toast.LENGTH_SHORT).show()
            return
        } else if (startTime.) {
            Toast.makeText(this, "Time not selected", Toast.LENGTH_SHORT).show()
            return
        }

        var event = Event(EventStore.id++, title, date, startTime, endTime, perMinit)
        if (notes) {
            event.notes = notes
        }
        if (lcoation) {
            event.location = location
        }
        EventStore.add(event, date)
    }
}