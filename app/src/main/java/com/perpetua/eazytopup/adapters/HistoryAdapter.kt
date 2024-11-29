package com.perpetua.eazytopup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.HistoryItem
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.databinding.HistoryItemBinding

class HistoryAdapter : RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private val historyItems = mutableListOf<HistoryItem>()

    fun setItems(items: List<HistoryItem>) {
        historyItems.clear()
        historyItems.addAll(items)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = HistoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = historyItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return historyItems.size
    }

    inner class ViewHolder(private val binding: HistoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: HistoryItem) {
            binding.numberTextView.text = item.number
        }
    }

    fun deleteItem(position: Int) {
        historyItems.removeAt(position)
        notifyItemRemoved(position)
    }

}