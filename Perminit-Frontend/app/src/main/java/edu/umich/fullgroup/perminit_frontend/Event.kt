package edu.umich.fullgroup.perminit_frontend

import android.util.Log
import java.time.LocalDate
import java.time.LocalTime


class Event (val id: Int, val title: String, var date: LocalDate,
                  var startTime: LocalTime, var endTime: LocalTime,
                  var perMinitId: Int) {
    // lateinit var notes : String
    // lateinit var location : String
    var reminderText = generate_reminder()

    /* Example prompt:

    Mary is a blah blah blah person. She is polite, and always wants to help the user remember their tasks.

    The user has an event called "Shovel the driveway".
    Reminder: You need to shovel the driveway! Or something.

    ...

    The user has an event called "This event".
    Reminder:
     */
    fun generate_reminder(): String {
        val minit = PerMinitStore.minits[perMinitId]
        val uncompleted = minit.examples.format(title)
        val completed = TextCompleter.completeText(uncompleted)
        return completed.split("\n", limit=1)[0]
    }
}