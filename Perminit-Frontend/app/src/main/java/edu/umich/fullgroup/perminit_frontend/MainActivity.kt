package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import toast


class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    fun addEvent(view: View?) = startActivityForResult(Intent(this,AddReminder::class.java), 1)
    fun toCalendar(view: View?) = startActivity(Intent(this, CalendarActivity::class.java))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        PerMinitStore.load(applicationContext)
        EventStore.load(applicationContext)

//        val e = Event(1, "Record 441 Video", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
//        EventStore.add(e, LocalDate.now())
//        EventStore.updateList()

        recyclerView = findViewById<RecyclerView>(R.id.eventList)
        recyclerView.adapter = EventAdapter(this, EventStore.list)

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                recyclerView.adapter?.notifyDataSetChanged()
            }
        }
    } //onActivityResult

    override fun onPause() {
        super.onPause()
        MainScope().async { EventStore.store(applicationContext) }
        MainScope().async { PerMinitStore.store(applicationContext) }
    }

    //when the submit from text box button is clicked

    fun nlpSubmitTodo(view: View) {
        val e : Event?  = makeEvent (findViewById<TextView>(R.id.nlpAddTodo).text.toString())
        if (e != null && e.title!="EVENT_CREATION_ERROR") {
            EventStore.add(e,e.date)
            EventStore.updateList()
            finish()
        }
        if (e == null) {
            toast("Failed to process input")
        }


    }
//    private fun createNotificationChannel(channelId: String, channelName: String) {
//        // Create the NotificationChannel, but only on API 26+ because
//        // the NotificationChannel class is new and not in the support library
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val name = channelName
//            val descriptionText = getString(R.string.event_notification_channel_description)
//            val importance = NotificationManager.IMPORTANCE_DEFAULT
//            val channel = NotificationChannel(channelId, name, importance).apply {
//                description = descriptionText
//            }
//
////            TODO: maybe code below is better
////            val notificationManager: NotificationManager = getSystemService(
////                NotificationManager::class.java
////            )
//            // Register the channel with the system
//            val notificationManager: NotificationManager =
//                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            notificationManager.createNotificationChannel(channel)
//        }
//    }


}