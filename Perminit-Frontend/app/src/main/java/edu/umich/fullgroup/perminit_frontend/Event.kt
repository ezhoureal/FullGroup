package edu.umich.fullgroup.perminit_frontend

import android.util.Log
import kotlinx.serialization.KSerializer
import java.time.LocalDate
import java.time.LocalTime
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@Serializable
class Event (val id: Int,
             val title: String,
             @Serializable(with = LocalDateSerializer::class)
             var date: LocalDate,
             @Serializable(with = LocalTimeSerializer::class)
             var startTime: LocalTime,
             @Serializable(with = LocalTimeSerializer::class)
             var endTime: LocalTime,
                  var perMinitId: Int) {
    var notes = ""
    var location = ""
    var reminderText = generate_reminder()

    /* Example prompt:

    Mary is a blah blah blah person. She is polite, and always wants to help the user remember their tasks.

    The user has an event called "Shovel the driveway".
    Reminder: You need to shovel the driveway! Or something.

    ...

    The user has an event called "This event".
    Reminder:
     */
    fun generate_reminder(): String {
        val minit = PerMinitStore.minits[perMinitId]
        val uncompleted = minit.examples.format(title)
        val completed = TextCompleter.completeText(uncompleted)
        val message = completed.split("\n", limit=1)[0]
        return "%s:%s".format(minit.name, message)
    }
}

object LocalDateSerializer : KSerializer<LocalDate> {
    override val descriptor = PrimitiveSerialDescriptor("LocalDate", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalDate {
        return LocalDate.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(value.toString())
    }
}

object LocalTimeSerializer : KSerializer<LocalTime> {
    override val descriptor = PrimitiveSerialDescriptor("LocalTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): LocalTime {
        return LocalTime.parse(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: LocalTime) {
        encoder.encodeString(value.toString())
    }
}