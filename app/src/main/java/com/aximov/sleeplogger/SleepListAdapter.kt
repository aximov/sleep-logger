package com.aximov.sleeplogger

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SleepListAdapter internal constructor(
    context: Context
) : RecyclerView.Adapter<SleepListAdapter.SleepViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sleeps = emptyList<Sleep>()

    inner class SleepViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val sleepItemView: TextView = itemView.findViewById(R.id.textview_recycler_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SleepViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return SleepViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: SleepViewHolder, position: Int) {
        val current = sleeps[position]
        holder.sleepItemView.text = "${current.length}, ${current.date.year}-${current.date.month + 1}-${current.date.date}"
    }

    internal fun setSleeps(sleeps: List<Sleep>) {
        this.sleeps = sleeps
        notifyDataSetChanged()
    }

    override fun getItemCount() = sleeps.size
}