package edu.umich.fullgroup.perminit_frontend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AddReminder : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_reminder)
    }

    // called when submit button is pushed
    fun onSubmit() {
        // validation check


        EventStore.add()
    }
}