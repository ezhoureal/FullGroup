package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import getJsonDataFromAsset
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.time.LocalDate
import kotlin.text.split

object PerMinitStore {
    var minits = ArrayList<PerMinit>()
    var example_events = listOf<String>(
        "The user has an event called \"Shovel the driveway\".",
        "The user has an event called \"Eat a quick lunch\".",
        "The user has an event called \"Business meeting with Joanne\"."
    )

    // store PerMinit to local storage
    suspend fun store(context: Context) {
        withContext(Dispatchers.IO) {              // Dispatchers.IO (main-safety block)
            var f = File(context.filesDir, "minitData.json")
            if (!f.exists()) {
                f.createNewFile()
            }

            var data = ""
            for (minit in minits) {
                data += Json.encodeToString(minit) + "\n"
            }
            f.writeText(data)
        }
    }

    // load PerMinits from local storage
    fun load(context: Context) {
        var f = File(context.filesDir, "minitData.json")

        if (f.exists() and f.readLines().isNotEmpty()) {
            try {
                val data = f.readLines()
                for (line in data) {
                    val minit = Json.decodeFromString<PerMinit>(line)
                    minits.add(minit)
                }
            } catch (e:Exception) {
                print(e)
            }
        } else {
            minits.add(PerMinit(
                "Reginald",
                "Reginald is a British ape with a taste for bananas and tea. He is polite, and always wants to help the user remember their tasks.",
                "Reginald is a British ape with a taste for bananas and tea. He is polite, and always wants to help the user remember their tasks.\n\nThe user has an event called \"Shovel the driveway\".\nReminder: How do you expect the banana shipment to arrive on an unshoveled drive?\n\nThe user has an event called \"Eat a quick lunch\".\nReminder: Oh, an afternoon tea! What a delight.\n\nThe user has an event called \"Business meeting with Joanne\".\nReminder: You really should invite her over for tea sometime.\n\nThe user has an event called \"%s\".\nReminder:",
                ""
            ))

            minits.add(PerMinit(
                "Norman",
                "Norman is an American TV watcher who is a big fan of Fonzie. He likes sunglasses and motorcycles and thinks the user should do their tasks in the coolest way possible.",
                "Norman is an American TV watcher who is a big fan of Fonzie. He likes sunglasses and motorcycles and thinks the user should do their tasks in the coolest way possible.\n\nThe user has an event called \"Shovel the driveway\".\nReminder: Ayy, you have to shovel the driveway today. Or invite me over, and I'll push it away with 2 quick taps. \n\nThe user has an event called \"Eat a quick lunch\".\nReminder: Ayy, grab some food or you won't be nothing but a hound dog..\n\nThe user has an event called \"Business meeting with Joanne\".\nAyy, you gotta talk business with Joanne. Don't be late, or you'll be up a creek!\n\nThe user has an event called \"%s\".\nReminder:",
                ""
            ))
            
            minits.add(PerMinit(
                "Sabrina",
                "Sabrina is an Australian poet and she knows it. She likes to rhyme, and her favorite spice is thyme. She drinks coffee and eats toffee. She wears wool and stays cool.",
                "Sabrina is an Australian poet and she knows it. She likes to rhyme, and her favorite spice is thyme. She drinks coffee and eats toffee. She wears wool and stays cool.\n\nThe user has an event called \"Shovel the driveway\".\nReminder: The snow pile comes down to tile. Go pick it up with a shovel or your mansion will be a hovel. \n\nThe user has an event called \"Eat a quick lunch\".\n Your stomach growls like something fierce. But a sandwich would that anguish pierce.\n\nThe user has an event called \"Business meeting with Joanne\".\nJoanne calls for you to see. Financial records of the company. So don now your business suit. And pluck this meeting like ripe fruit.\n\nThe user has an event called \"%s\".\nReminder:",
                ""
            ))
        }
    }

    fun generate_minit(): Int {
        // 1.loop from ArrayList to get every Perminit
        // 2.convert each Perminit to a string, add index at the start 
        // 3.send the result to the text completer for GPT-3 completion
        // 4.save the first word in the result as the Minit's name X
        // 5.get each saved Minit's example reminders
        // 6.generate new example reminders for the new Minit
        // 7.save the created Minit to our ArrayList minits
        // 8.return the length of the array (i.e. the new Minit's id)
        var uncompleted = ""
        var list_index = 1
        for (minit in minits) {
            uncompleted += "$list_index. ${minit.personality}\n"
            list_index += 1
        }
        uncompleted += "$list_index."

        // text = "Harry is a German shepherd who loves to play fetch..."
        val text = TextCompleter.completeText(uncompleted).drop(1)
        val parts = text.split(" ")
        val name = parts[0]
        var examples = text
        // 1.for each minit, get example, and save to example list
        // 2.turn example list into example string for the new minit
        var count = 0
        for (event in example_events) {
            var uncompleted_examples = event
            for (minit in minits) {
                uncompleted_examples += "\n\n" + minit.personality
                uncompleted_examples += "\n" + minit.get_example(count)
            }
            uncompleted_examples += "\n\n" + text + "\nReminder:"

            val exam = TextCompleter.completeText(uncompleted_examples)
            examples += "\n\n" + event + "\nReminder:" + exam

            count += 1
        }
        examples += "\n\nThe user has an event called \"%s\".\nReminder:"

        minits.add(PerMinit(
            name,
            text,
            examples,
            ""
        ))
        
        return minits.size
    }
}