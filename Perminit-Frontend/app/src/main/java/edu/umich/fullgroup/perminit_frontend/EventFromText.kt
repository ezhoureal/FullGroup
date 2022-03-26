package edu.umich.fullgroup.perminit_frontend

import io.ktor.util.*
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val RE = "((?<=event ).*) from (.*(?= on)) on (.*(?= to)) to (.*)$".toRegex()

/*
    format MUST be:
    create event EVENT NAME from HH:MM AM on MMM DD to HH:MM AM
    either of the AM's can be PM
    MMM = month abbreviation (Jan, Feb, ...). Cannot write full month name
 */
fun makeEvent (input : String): Event? {
    val parsed = RE.find(input.lowercase())
    // groupValues: 1- event name, 2- start time, 3- start date, 4- end time
    val name = parsed?.groupValues?.get(1)
    val startTimeStr = parsed?.groupValues?.get(2)?.uppercase()
    val dateStr = parsed?.groupValues?.get(3) + " " + LocalDate.now().year
    val endTimeStr = parsed?.groupValues?.get(4)?.uppercase()

    val dateFormatter = DateTimeFormatter.ofPattern("MMM d yyyy")
    val date = LocalDate.parse(dateStr, dateFormatter)

    val timeFormatter = DateTimeFormatter.ofPattern("h[:mm] a")
    val startTime = LocalTime.parse(startTimeStr, timeFormatter)
    val endTime = LocalTime.parse(endTimeStr, timeFormatter)

    println ("making the event!")
    val newe = name?.let { Event (1, it, date, startTime, endTime, 0) }
    return newe
}

