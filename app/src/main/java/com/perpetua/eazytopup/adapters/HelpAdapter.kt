package com.perpetua.eazytopup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.HelpItem
import com.perpetua.eazytopup.databinding.ItemFavoriteBinding
import com.perpetua.eazytopup.databinding.ItemHelpBinding
import com.perpetua.eazytopup.databinding.ItemTransactionStatementBinding
import com.perpetua.eazytopup.models.Contact
import com.perpetua.eazytopup.models.TransactionStatement


class HelpAdapter(private val items: List<HelpItem>, private val onItemClick: (HelpItem) -> Unit) :
        RecyclerView.Adapter<HelpAdapter.HelpViewHolder>() {


        private var _binding: ItemHelpBinding? = null
        private val binding get() = _binding!!

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HelpViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemHelpBinding.inflate(inflater, parent, false)
            return HelpViewHolder(binding)
        }

        override fun onBindViewHolder(holder: HelpViewHolder, position: Int) {
            val item = items[position]
            holder.bind(item)
        }

        override fun getItemCount(): Int = items.size

        inner class HelpViewHolder(private val binding: ItemHelpBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(item: HelpItem) {
                binding.helpText.text = item.title
                binding.root.setOnClickListener { onItemClick(item) }
            }
        }


}