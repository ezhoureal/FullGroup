package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    fun addEvent(view: View?) = startActivity(Intent(this,AddReminder::class.java))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)

        val recyclerView = findViewById<RecyclerView>(R.id.RecyclerView)
        recyclerView.adapter = EventAdapter(this, ArrayList())

        // Use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true)

        EventStore.load(applicationContext)
        PerMinitStore.load()
        Log.d("data", EventStore.events.toString())
    }
}