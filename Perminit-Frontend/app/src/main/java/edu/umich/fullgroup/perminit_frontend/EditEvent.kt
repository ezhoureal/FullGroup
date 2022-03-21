package edu.umich.fullgroup.perminit_frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class EditEvent : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_event)
        val event_id = intent.extras?.get("EVENT_ID")
    }
}