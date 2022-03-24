package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import edu.umich.fullgroup.perminit_frontend.databinding.ActivityAddReminderBinding
import edu.umich.fullgroup.perminit_frontend.databinding.ActivityEditEventBinding
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class EditEvent : AppCompatActivity() {
    private lateinit var view: ActivityEditEventBinding
    private var eventId: Int = 0
    lateinit var event: Event
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(view.root)
        eventId = intent.getIntExtra("EVENT_ID", 0)
        // find corresponding event
        for (e in EventStore.list) {
            if (e.id == eventId) {
                this.event = e
                view.NameField.setText(event.title)
                view.editTextDate.setText(event.date.toString())
                view.editTextStartTime.setText(event.startTime.toString())
                view.editTextEndTime.setText(event.endTime.toString())
                view.descriptionbox.setText(event.notes)
                break
            }
        }
    }

    var minit_selected = -1

    fun select_1(viewk: View){
        minit_selected=1
        view.chooseText.setText("Selected " + view.pm1Name.text)
    }
    fun select_2(viewk :View){
        minit_selected=2
        view.chooseText.setText("Selected " + view.pm2Name.text)
    }
    fun select_3(viewk:View){
        view.chooseText.setText("Selected " + view.pm3Name.text)
        minit_selected=3
    }

    // called when submit button is pushed
    fun onSubmit(v: View) {
        val title = view.NameField.text.toString()
        // validation check
        if (title == "") {
            Toast.makeText(this, "Title is empty", Toast.LENGTH_SHORT).show()
            return
        }

        var perMinitId = minit_selected
        if (perMinitId == -1){
            Toast.makeText(this, "Please select a Minit", Toast.LENGTH_SHORT).show()
            return
        }
        // 0 indexing -
        perMinitId = perMinitId-1


        //make it a little less annoying.
        var startstr = view.editTextStartTime.text.toString()
        var endstr = view.editTextEndTime.text.toString()
        var datestr = view.editTextDate.text.toString()

        val dateFormat = DateTimeFormatter.ofPattern("uuuu-MM-dd")
        var date = LocalDate.parse(datestr, dateFormat)
        var startTime = LocalTime.parse(startstr)
        var endTime = LocalTime.parse(endstr)
        val description = view.descriptionbox.text.toString()

        // delete old event
        var newList = ArrayList<Event>()
        for (e in EventStore.events[this.event.date]!!) {
            if (e.id != this.event.id) {
                newList.add(e)
            }
        }
        EventStore.events[this.event.date] = newList

        var newEvent = Event(EventStore.id_idx++, title, date, startTime, endTime, perMinitId)
        newEvent.notes = description
        EventStore.add(newEvent, date)
        // add event to list
        EventStore.updateList()

        setResult(RESULT_OK, Intent());
        finish()
    }


    fun cancel(v: View) {
        finish()
    }
}