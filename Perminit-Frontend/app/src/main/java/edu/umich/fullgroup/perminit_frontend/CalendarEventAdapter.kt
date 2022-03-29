package edu.umich.fullgroup.perminit_frontend

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView


/**
 * Adapter for the [RecyclerView] in [To-do List]. Displays [events] data object.
 */
class CalendarEventAdapter(
    private val context: Context,
    private val dataset: MutableList<Event>
) : RecyclerView.Adapter<CalendarEventAdapter.EventViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    class EventViewHolder(@NonNull val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.eventName)
        val time: TextView = view.findViewById(R.id.eventTime)
        val minitText: TextView = view.findViewById(R.id.minitText)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.calendar_todo_item, parent, false)

        Log.d("data", EventStore.events.toString())

        return EventViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val item = dataset[holder.adapterPosition]
        holder.name.text = item.title
        holder.time.text = item.startTime.toString()
        holder.minitText.text = item.reminderText
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}