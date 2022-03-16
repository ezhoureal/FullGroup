package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate
import java.time.LocalTime

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    fun addEvent(view: View?) = startActivityForResult(Intent(this,AddReminder::class.java), 1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        EventStore.load(applicationContext)
        PerMinitStore.load()
        Log.d("data", EventStore.events.toString())

        val e = Event(1, "test", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
        EventStore.add(e, LocalDate.now())

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

}