package edu.umich.fullgroup.perminit_frontend

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

val RE = "((?<=event ).*) from (.*(?= on)) on (.*(?= to)) to (.*)$".toRegex()


//add event play golf from 12:00 until 13:00 on 10/21/2022 with note bring my nine iron
val eventNameKeywords = arrayOf("add event", "new event")
val startTimeKeywords = arrayOf("from")
val endTimeKeywords = arrayOf("until")
val dateKeywords = arrayOf ("on")
val descriptionKeywords = arrayOf ("with note")

val nameRegexPart = eventNameKeywords.joinToString ( "|")
val startTimeRegexPart = startTimeKeywords.joinToString  ("|")
val endTimeRegexPart = endTimeKeywords.joinToString("|")
val dateRegexPart = dateKeywords.joinToString("|")
val descriptionRegexPart = descriptionKeywords.joinToString("|")



//logic for this is that the name is rather free form
val endNameRegex = startTimeRegexPart+ "|" + endTimeRegexPart +"|"+ dateRegexPart +"|" + descriptionRegexPart

//logic for this is that the name is rather free form
//problem here - it's difficult to make it actually minimal - might need 2 different regexes
// basically, it neeeds to be bot hnon greedy and mandatory  - so if we want more flexibility, moar regexes
val findNameRegex = ("(?:$nameRegexPart)"+"""\s*(.+?)""" + "(?:$endNameRegex)").toRegex()
val findDateRegex = ("(?:$dateRegexPart)"+"""\s*(\d+\/\d+)""").toRegex()

val findStartTimeRegex = ("(?:$startTimeRegexPart)"+"""\s*(\d+\:\d+)""").toRegex()
val findEndTimeRegex = ("(?:$endTimeRegexPart)"+    """\s*(\d+:\d+)""").toRegex()

val findDescRegex = ("(?:$descriptionRegexPart)"+"""\s*(.+)$""").toRegex()




//maybe we'll do this the same way as event name???
//val findDescRegex = ("(?:$endTimeRegexPart)"+"""(\d+):(\d+)""").toRegex()

/*
    format MUST be:
    create event EVENT NAME from HH:MM AM on MMM DD to HH:MM AM
    either of the AM's can be PM
    MMM = month abbreviation (Jan, Feb, ...). Cannot write full month name
 */

fun makeEvent (input : String): Event? {
    try {

        //val nameParsed = findNameRegex.find(input.lowercase())?.groupValues?.get(1)?.lowercase()
        println("found name:")
        val nameRes = findNameRegex.find(input)
        val (myName) = nameRes!!.destructured
        println(myName)

        println ("Found start time:")
        val startTimeRes = findStartTimeRegex.find(input)
        val (startTimeString) = startTimeRes!!.destructured
        println(startTimeString)



        println ("Found date :")
        val dateRes = findDateRegex.find(input)
        val (dateString) = dateRes!!.destructured
        println(dateString)

        try{
            println ("found description : ")
            val descRes = findDescRegex.find(input)
            val (descString ) = descRes!!.destructured
            println(descString)
        }
        catch (e: NullPointerException){
            println ("no thing")
        }
        println ("be better")


        //testing bs
        val dume = Event(1, "sample", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
        return dume

        val parsed = RE.find(input.lowercase())


        // groupValues: 1- event name, 2- start time, 3- start date, 4- end time
        val name = parsed?.groupValues?.get(1)
        val startTimeStr = parsed?.groupValues?.get(2)?.uppercase()
        val dateStr = parsed?.groupValues?.get(3) + " " + LocalDate.now().year
        val endTimeStr = parsed?.groupValues?.get(4)?.uppercase()

        val dateFormatter = DateTimeFormatter.ofPattern("MM dd yyyy")
        //well it doesn't sem to like this
        val date = LocalDate.parse(dateStr, dateFormatter)


        val timeFormatter = DateTimeFormatter.ofPattern("h[:mm] a")
        val startTime = LocalTime.parse(startTimeStr, timeFormatter)
        val endTime = LocalTime.parse(endTimeStr, timeFormatter)

        println("making the event!")
        //todo - make the eventid and perminit id more better
        val newe = name?.let { Event(1, it, date, startTime, endTime, 0) }
        return newe
    }
    catch (e: Exception){
        //so when it sees an event with id -1
        val bad_ending = Event(-1, "EVENT_CREATION_ERROR", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
        return bad_ending

    }
}

