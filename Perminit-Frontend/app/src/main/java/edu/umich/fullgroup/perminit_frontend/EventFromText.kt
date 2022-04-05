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
//        println("found name:")
        val nameRes = findNameRegex.find(input)
        val (myName) = nameRes!!.destructured
        println(myName)

 //       println ("Found start time:")
        val startTimeRes = findStartTimeRegex.find(input)
        val (startTimeString) = startTimeRes!!.destructured
        println(startTimeString)

        var endTimeString = startTimeString
        try{
  //          println ("Found end time:")
            val endTimeRes = findEndTimeRegex.find(input)
            val (myEndTimeString) = endTimeRes!!.destructured
            endTimeString = myEndTimeString
            println(endTimeString)

        }
        catch (e: NullPointerException){
            //if there's no end time provided, we assume none exists for now - keep it at zero
        }



        //println ("Found date :")
        val dateRes = findDateRegex.find(input)
        val (dateString) = dateRes!!.destructured
        //println(dateString)

        var descString =""

        try{
            //println ("found description : ")
            val descRes = findDescRegex.find(input)
            var  (myDescString ) = descRes!!.destructured
            descString=myDescString
            //println(descString)
        }
        catch (e: NullPointerException){
            //just keep the desc string as nothing

        }
        //println ("be better")

        //todo: parse the times and dates
        //val goodEvent = Event (EventStore.idx++,myName,)

        //testing bs
        val dume = Event(1, "sample", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
        return dume

        val dateFormatter = DateTimeFormatter.ofPattern("MM dd yyyy")
        //well it doesn't sem to like this
        val date = LocalDate.parse(dateString, dateFormatter)


        val timeFormatter = DateTimeFormatter.ofPattern("h[:mm] a")
        val startTime = LocalTime.parse(startTimeString, timeFormatter)
        val endTime = LocalTime.parse(endTimeString, timeFormatter)

        //println("making the event!")
        //todo - make the eventid and perminit id more better
        val perminitId = 0
        val eventID = EventStore.id_idx++

        val outEvent = Event(eventID,myName,date,startTime,endTime,perminitId)
        return outEvent
    }
    catch (e: Exception){
        //so when it sees an event with id -1
        val bad_ending = Event(-1, "EVENT_CREATION_ERROR", LocalDate.now(), LocalTime.now(), LocalTime.now(), 0)
        return bad_ending

    }
}

