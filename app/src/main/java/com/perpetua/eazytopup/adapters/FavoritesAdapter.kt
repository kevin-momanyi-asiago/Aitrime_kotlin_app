package com.perpetua.eazytopup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.databinding.ItemFavoriteBinding
import com.perpetua.eazytopup.models.Contact

class FavoritesAdapter(private val contactList: List<Contact>,
                       private val onDeleteClick: (Int) -> Unit

) : RecyclerView.Adapter<FavoritesAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = contactList[position]
        holder.binding.textName.text = contact.name
        holder.binding.textPhoneNumber.text = contact.phoneNumber

        holder.binding.root.setOnLongClickListener {
            onDeleteClick(holder.adapterPosition)
            true
        }
    }

    override fun getItemCount() = contactList.size

}