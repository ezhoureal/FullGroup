package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import getJsonDataFromAsset
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.time.LocalDate

object PerMinitStore {
    var minits = ArrayList<PerMinit>()
    // store PerMinit to local storage
    fun store(context: Context) {
        val data = Gson().toJson(this.minits)
        try {
            File(context.getFilesDir(), "minitData.json").writeText(data)
        } catch (e: IOException) {
            Log.e("Save on close", "File write failed: $e")
        }
    }

    // load PerMinits from local storage
    fun load(context: Context) {
        minits.add(PerMinit(
            "Mary",
            "Mary is a very apathetic person. She is polite, and always wants to help the user remember their tasks.",
            "Mary is a very apathetic person. She is polite, and always wants to help the user remember their tasks.\n\nThe user has an event called \"Shovel the driveway\".\nReminder: You need to shovel the driveway! Or something.\n\nThe user has an event called \"Eat a quick lunch\".\nReminder: Not that I care, but you should probably eat lunch.\n\nThe user has an event called \"Business meeting with Joanne\".\nReminder: Joanne wants to see you at the business meeting! Or whatever.\n\nThe user has an event called \"%s\".\nReminder:",
            ""
            ))

        // val jsonFileString = getJsonDataFromAsset(context, "minitData.json")
        // this.minits = Gson().fromJson(jsonFileString, ArrayList<PerMinit>()::class.java)
    }
}