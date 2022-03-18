package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.umich.fullgroup.perminit_frontend.databinding.ActivityAddReminderBinding
import java.io.Console
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


class AddReminder : AppCompatActivity() {
    private lateinit var view: ActivityAddReminderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(view.root)
    }

    //maybe should be a default
    var minit_selected = 0

    fun select_1(viewk: View){

        minit_selected=1


        view.chooseText.setText("Selected " + view.pm1Name.text)

        //R.id.


        println(minit_selected)

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
        // get data from view
        val dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val date = LocalDate.parse(view.editTextDate.text, dateFormat)
        val startTime = LocalTime.parse(view.editTextStartTime.text)
        val endTime = LocalTime.parse(view.editTextEndTime.text)
        val title = view.NameField.text.toString()
        val perMinitId = minit_selected

        val description = view.descriptionbox.text.toString()

        // validation check
        if (title == "") {
            Toast.makeText(this, "Title is empty", Toast.LENGTH_SHORT).show()
            return
        }

        var event = Event(EventStore.id_idx++, title, date, startTime, endTime, perMinitId)
//        if (view.notes) {
//            event.notes = notes
//        }
//        if (location) {
//            event.location = location
//        }
        EventStore.add(event, date)
        val returnIntent = Intent()
        setResult(RESULT_OK, returnIntent);
        finish()
    }


    fun cancel(v: View) {
        finish()
    }
}