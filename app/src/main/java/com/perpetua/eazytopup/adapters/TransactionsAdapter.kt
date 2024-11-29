package com.perpetua.eazytopup.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.databinding.ItemTransactionStatementBinding
import com.perpetua.eazytopup.models.Contact
import com.perpetua.eazytopup.models.TransactionStatement

class TransactionsAdapter: RecyclerView.Adapter<TransactionsAdapter.TransactionsViewHolder>() {
    private var _binding: ItemTransactionStatementBinding? = null
    private val binding get() = _binding!!

    inner class TransactionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionsViewHolder {
        _binding = ItemTransactionStatementBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TransactionsViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: TransactionsViewHolder, position: Int) {
        val transactionStatement = differ.currentList[position]
        holder.itemView.apply {
            binding.transactionName.text = transactionStatement.name
            binding.transactionNumber.text = transactionStatement.phoneNumber
            binding.transactionAmount.text = transactionStatement.amount
            binding.transactionTime.text = transactionStatement.date
            setOnItemClickListener {
                onItemClickListener?.let { it(transactionStatement) }
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    val differCallback = object : DiffUtil.ItemCallback<TransactionStatement>(){
        override fun areItemsTheSame(
            oldItem: TransactionStatement,
            newItem: TransactionStatement
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: TransactionStatement,
            newItem: TransactionStatement
        ): Boolean {
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this, differCallback)

    //listener
    private var onItemClickListener: ((TransactionStatement) -> Unit)? = null

    fun setOnItemClickListener(listener: (TransactionStatement) -> Unit){
        onItemClickListener = listener
    }

}