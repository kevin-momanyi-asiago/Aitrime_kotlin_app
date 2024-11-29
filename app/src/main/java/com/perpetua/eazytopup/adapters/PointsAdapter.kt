package com.perpetua.eazytopup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.databinding.PointsItemBinding

class PointsAdapter: RecyclerView.Adapter<PointsAdapter.ViewHolder>() {
    private val pointItems = mutableListOf<String>()

    fun setItems(items: List<String>) {
        pointItems.clear()
        pointItems.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = PointsItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pointItems[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int {
        return pointItems.size
    }

    inner class ViewHolder(private val binding: PointsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String) {
            binding.pointsTextView.text = item
        }
    }

    fun deleteItem(position: Int) {
        pointItems.removeAt(position)
        notifyItemRemoved(position)
    }



}