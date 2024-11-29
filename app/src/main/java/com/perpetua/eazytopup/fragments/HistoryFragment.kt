package com.perpetua.eazytopup.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.SharedPreferencesManager
import com.perpetua.eazytopup.adapters.HistoryAdapter
import com.perpetua.eazytopup.databinding.FragmentHistoryBinding


class HistoryFragment : Fragment() {
    private lateinit var binding: FragmentHistoryBinding
    private lateinit var sharedPreferencesManager: SharedPreferencesManager
    private val historyAdapter = HistoryAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferencesManager = SharedPreferencesManager(requireContext())

        val historyRecyclerView = binding.historyRecyclerView
        binding.historyRecyclerView.adapter = historyAdapter
        binding.historyRecyclerView.layoutManager = LinearLayoutManager(context)

        val historyItems = sharedPreferencesManager.getHistoryItems()
        historyAdapter.setItems(historyItems)
        Log.d("HistoryFragment", "History items size: ${historyItems.size}")

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                historyAdapter.deleteItem(position)

                // Also remove the item from the SharedPreferences
                sharedPreferencesManager.removeHistoryItem(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(historyRecyclerView)


    }

}