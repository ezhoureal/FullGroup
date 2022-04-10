package edu.umich.fullgroup.perminit_frontend

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import edu.umich.fullgroup.perminit_frontend.databinding.ActivityAddReminderBinding
import toast
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*


class AddReminder : AppCompatActivity() {
    private lateinit var view: ActivityAddReminderBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = ActivityAddReminderBinding.inflate(layoutInflater)
        setContentView(view.root)

        createNotificationChannel()
        //view.Save.setOnClickListener { scheduleNotification() }

        val adapter = ArrayAdapter.createFromResource(this,
            R.array.recurFrames, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = adapter

    }

    fun getValues(view: View) {
        val spinner = findViewById<Spinner>(R.id.spinner)
        Toast.makeText(this, "Spinner 1 " + spinner.selectedItem.toString(), Toast.LENGTH_LONG).show()
    }



    private fun scheduleNotification() {
        println("in scheduleNotification()")
        val intent = Intent(applicationContext, NotificationReceiver::class.java)
        val title = "Upcoming Event"
        val message = view.NameField.text.toString()
        println("message $message")
        // TODO: are 2 lines below necessary?
        intent.putExtra(titleExtra, title)
        intent.putExtra(messageExtra, message)

        val pendingIntent = PendingIntent.getBroadcast(
            applicationContext,
            NOTIFICATION_ID_FROM_RECEIVER,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val time = getTime()
        println("time: $time")
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            time,
            pendingIntent
        )
        val pendingIntentContents = pendingIntent.describeContents()
        println("pendingContents: $pendingIntent")
        val nextAlarm = alarmManager.getNextAlarmClock()
        println("next alarm: $nextAlarm")
        // showAlert() is for testing purposes
//        showAlert(time, title, message)
    }

    private fun showAlert(time: Long, title: String, message: String) {
        println("in showAlert()")
        val date = Date(time)
        val dateFormat = android.text.format.DateFormat.getLongDateFormat(applicationContext)
        val timeFormat = android.text.format.DateFormat.getTimeFormat(applicationContext)

        AlertDialog.Builder(this)
            .setTitle("Notification Scheduled")
            .setMessage(
                "Title: " + title +
                "\nMessage: " + message +
                "\nAt: " + dateFormat.format(date) + " " + timeFormat.format(date))
            .setPositiveButton("Okay"){_,_->}
            .show()
    }

    private fun getTime(): Long {
        val dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        val date = LocalDate.parse(view.editTextDate.text, dateFormat)
        val startTime = LocalTime.parse(view.editTextStartTime.text)
        println(date.year)
        println(date.monthValue)
        println(date.dayOfMonth)
        println(startTime.hour)
        println(startTime.minute)
        val calendar = Calendar.getInstance()
        calendar.set(date.year, date.monthValue, date.dayOfMonth, startTime.hour, startTime.minute)
        return calendar.timeInMillis
    }

    private fun createNotificationChannel() {
        // TODO: "delete NotificationUtil channel creation if no longer using"
        val name = getString(R.string.event_notification_channel_name)
        val desc = getString(R.string.event_notification_channel_description)
        val channelID = getString(R.string.event_notification_channel_id)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelID, name, importance)
        channel.description = desc
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    //maybe should be a default
    var minit_selected = -1

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

    fun select_4(viewk:View){
        view.chooseText.setText("Creating new Minit")
        minit_selected=4

    }

    fun nlpSubmit (v: View){
        val nlpText = view.nlpin.text.toString()
        println ("petrol")
        val e: Event? = makeEvent(nlpText)
        if (e != null) {
            println (e.title)
        }
        if (e != null && e.title!="EVENT_CREATION_ERROR") {
            EventStore.add(e,e.date)
            EventStore.updateList()
            val returnIntent = Intent()
            setResult(RESULT_OK, returnIntent);
            finish()
        }
        else {
            toast("Failed to process input")
        }

        //todo: store it &c
    }

    fun nlpHelp (v: View){
        toast ("${getString(R.string.nlp_help)}")
    }

    // called when submit button is pushed
    fun onSubmit(v: View) {
        scheduleNotification()

        val title = view.NameField.text.toString()
        // get data from view


        //still some issues with date time format

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
        else if (perMinitId == 4){  // new Minit
            // we don't do this in select_4 to save resources
            perMinitId = PerMinitStore.generate_minit()
        }

        // 0 indexing -
        perMinitId = perMinitId-1


        //make it a little less annoying.
        var startstr_init = view.editTextStartTime.text
        var endstr_init = view.editTextEndTime.text
        var date_init = view.editTextDate.text

        var startstr_final = ""
        var endstr_final =""
        var date_final =""

        //there is an edgecase of events at 1am  but we'll fix that in mvp
        //
//        if (startstr_init[0] > '1'){
//            startstr_final = "0"
//        }

//        if (endstr_init[0] > '1'){
//            endstr_final = "0"
//        }
//
//        if (date_init [0] > '1'){
//            date_final="0"
//        }

        startstr_final += startstr_init.toString()
        endstr_final += endstr_init.toString()
        date_final += date_init.toString()

        println("times:")
        //println(startstr_final)
        //println(endstr_final)


        val dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy")

        var date = LocalDate.now()


        date = LocalDate.parse(date_final, dateFormat)


        var startTime = LocalTime.parse(startstr_final)
        var endTime = LocalTime.parse(endstr_final)

        println (startTime.toString())
        println (endTime.toString())    //TODO: endTime currently equals startTime
        println (date.toString())

        //val date=LocalDate.now()

        val description = view.descriptionbox.text.toString()

        if (view.recurringCheck.isChecked) {
            val mode = view.spinner.selectedItem.toString()
            val times = view.numTimeRecur.text.toString().toInt()
            for (i in 1..times) {
                var event = Event(EventStore.id_idx++, title, date, startTime, endTime, perMinitId)
                event.notes = description
                EventStore.add(event, date)
                date = when (mode) {
                    "Daily" -> {
                        date.plusDays(1)
                    }
                    "Weekly" -> {
                        date.plusDays(7)
                    }
                    "Monthly" -> {
                        date.plusMonths(1)
                    }
                    else -> {
                        date.plusYears(1)
                    }
                }
            }
        }
        else {
            var event = Event(EventStore.id_idx++, title, date, startTime, endTime, perMinitId)
            event.notes = description
            EventStore.add(event, date)
        }
        EventStore.updateList()



        val returnIntent = Intent()
        setResult(RESULT_OK, returnIntent);
        finish()
    }


    fun cancel(v: View) {
        finish()
    }
}