package edu.umich.fullgroup.perminit_frontend

import java.time.LocalDate
import java.time.LocalTime

fun makeEvent (input : String): Event {


    println ("makign the event!")
    var newe = Event (1,"name", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
    return newe
}

