package edu.umich.fullgroup.perminit_frontend

import java.io.File
import java.io.FileInputStream
import java.util.*
import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.engine.Davinci
import com.aallam.openai.api.engine.Engine
import com.aallam.openai.client.OpenAI
import android.util.Log
import kotlinx.coroutines.*

object TextCompleter {
//    val prop = Properties().apply {
//        load(FileInputStream(File(System.getProperty("user.dir"), "local.properties")))
//    }
    val openAI = OpenAI(BuildConfig.API_KEY)
    
    fun onCreate() = runBlocking {
        launch {
            val davinci: Engine = openAI.engine(Davinci)
        }
    }

    fun completeText(prompt: String) : String {
        val completionRequest = CompletionRequest(prompt=prompt)
        val completed = runBlocking {
            openAI.completion(Davinci, completionRequest)
        }
        return completed.choices[0].text.split("\n")[0]
    }
}