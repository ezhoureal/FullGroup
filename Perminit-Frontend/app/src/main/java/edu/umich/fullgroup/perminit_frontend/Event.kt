package edu.umich.fullgroup.perminit_frontend

import java.time.LocalDate
import java.time.LocalTime


data class Event (val id: Int, val title: String, var date: LocalDate,
                  var startTime: LocalTime, var endTime: LocalTime,
                  var perMinitId: Int) {
    var notes = ""
    var location = ""
}