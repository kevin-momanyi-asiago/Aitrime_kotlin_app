package com.perpetua.eazytopup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.databinding.ItemFavoriteBinding
import com.perpetua.eazytopup.databinding.ItemRedeemBinding
import com.perpetua.eazytopup.models.Contact
import com.perpetua.eazytopup.models.RedeemPoints

class RedeemPointsAdapter: RecyclerView.Adapter<RedeemPointsAdapter.FavoriteViewHolder>() {
    private var _binding: ItemRedeemBinding? = null
    private val binding get() = _binding!!

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        _binding = ItemRedeemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val redeemPoint = differ.currentList[position]
        holder.itemView.apply {
            binding.redeemAirtimeAmount.text = redeemPoint.airtimeAmount
            binding.redeemPointsAmount.text = redeemPoint.points
            setOnItemClickListener{
                onItemClickListener?.let { it(redeemPoint) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    val diffCallback = object: DiffUtil.ItemCallback<RedeemPoints>(){
        override fun areItemsTheSame(oldItem: RedeemPoints, newItem: RedeemPoints): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RedeemPoints, newItem: RedeemPoints): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, diffCallback)

    //listener
    private var onItemClickListener: ((RedeemPoints) -> Unit)? = null

    fun setOnItemClickListener(listener: (RedeemPoints) -> Unit){
        onItemClickListener = listener
    }
}