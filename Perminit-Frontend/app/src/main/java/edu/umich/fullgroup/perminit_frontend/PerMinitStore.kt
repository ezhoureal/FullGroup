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
            "Reginald",
            "Reginald is a British ape with a taste for bananas and tea. He is polite, and always wants to help the user remember their tasks.",
            "Reginald is a British ape with a taste for bananas and tea. He is polite, and always wants to help the user remember their tasks.\n\nThe user has an event called \"Shovel the driveway\".\nReminder: How do you expect the banana shipment to arrive on an unshoveled drive?\n\nThe user has an event called \"Eat a quick lunch\".\nReminder: Oh, an afternoon tea! What a delight.\n\nThe user has an event called \"Business meeting with Joanne\".\nReminder: You really should invite her over for tea sometime.\n\nThe user has an event called \"%s\".\nReminder:",
            ""
            ))

        // val jsonFileString = getJsonDataFromAsset(context, "minitData.json")
        // this.minits = Gson().fromJson(jsonFileString, ArrayList<PerMinit>()::class.java)
    }
}