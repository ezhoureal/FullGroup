package edu.umich.fullgroup.perminit_frontend

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


//add event play golf from 12:00 until 13:00 on 10/21/2022 with note bring my nine iron
val eventNameKeywords = arrayOf("add event", "new event",  "schedule","""\w?""")
val startTimeKeywords = arrayOf("from", "starting at")
val endTimeKeywords = arrayOf("until", "ending at")
val dateKeywords = arrayOf ("on date", "on", "date")
val descriptionKeywords = arrayOf ("with note", "description")

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
val findDateRegex = ("(?:$dateRegexPart)"+"""\s*(\d+\/\d+\/\d+)""").toRegex()

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
        val badTimeRegex =  """^(\d:\d\d)""".toRegex()
        val fixedStartTime = badTimeRegex.replace(startTimeString, """0\1""")
        println ("Fixed:")
        println (fixedStartTime)

        //todo - replace times of format H:MM with HH:MM (just the string with regex)
        // todo - replace dates of format M/D  or M/DD &c with MM/DD



        println ("Found date :")
        val dateRes = findDateRegex.find(input)
        val (dateString) = dateRes!!.destructured
        println(dateString)

        var descString =""

        try{
            println ("found description : ")
            val descRes = findDescRegex.find(input)
            var  (myDescString ) = descRes!!.destructured
            descString=myDescString
            println(descString)
        }
        catch (e: NullPointerException){
            //just keep the desc string as nothing
        }

        //todo: parse the times and dates
        //val goodEvent = Event (EventStore.idx++,myName,)

        //testing bs

        val dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy")
        //well it doesn't sem to like this
        val date = LocalDate.parse(dateString, dateFormatter)


        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val startTime = LocalTime.parse(startTimeString, timeFormatter)
        val endTime = LocalTime.parse(endTimeString, timeFormatter)

        println("making the event!")
        //todo - make the eventid and perminit id more better
        val perminitId = 0
        val eventID = EventStore.id_idx++

        val outEvent = Event(eventID,myName,date,startTime,endTime,perminitId)
        return outEvent
    }
    catch (e: Exception){
        return null

    }
}

