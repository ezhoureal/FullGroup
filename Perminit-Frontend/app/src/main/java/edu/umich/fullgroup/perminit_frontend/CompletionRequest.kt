package edu.umich.fullgroup.perminit_frontend

import com.aallam.openai.api.completion.CompletionRequest
import com.aallam.openai.api.engine.Davinci
import com.aallam.openai.api.engine.Engine
import com.aallam.openai.client.OpenAI
import android.util.Log
import kotlinx.coroutines.*

object TextCompleter { 
    val openAI = OpenAI("")
    
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
        return completed.choices[0].text
    }
}