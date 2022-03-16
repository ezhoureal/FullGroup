package edu.umich.fullgroup.perminit_frontend

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class TodoList : AppCompatActivity() {
    fun addEvent(view: View?) = startActivity(Intent(this,AddReminder::class.java))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
    }


}