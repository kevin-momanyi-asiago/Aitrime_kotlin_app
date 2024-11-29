package com.perpetua.eazytopup.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.SharedPreferencesManager
import com.perpetua.eazytopup.adapters.PointsAdapter
import com.perpetua.eazytopup.adapters.RedeemPointsAdapter
import com.perpetua.eazytopup.databinding.FragmentFavoritesBinding
import com.perpetua.eazytopup.databinding.FragmentPointsBinding
import com.perpetua.eazytopup.models.RedeemPoints


class PointsFragment : Fragment() {
    private lateinit var binding: FragmentPointsBinding
    private lateinit var sharedPreferenceManager: SharedPreferencesManager
    private val pointsAdapter = PointsAdapter()


    override fun onAttach(context: Context) {
        super.onAttach(context)
        sharedPreferenceManager = SharedPreferencesManager(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPointsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Display total points
        val points = sharedPreferenceManager.getPoints()
        binding.totalPointsTextView.text = "Total Points: $points"

        sharedPreferenceManager = SharedPreferencesManager(requireContext())

        val pointsRecyclerView = binding.pointsRecyclerView
        pointsRecyclerView.adapter = pointsAdapter
        pointsRecyclerView.layoutManager = LinearLayoutManager(context)

        val pointItems = sharedPreferenceManager.getAmounts()
        pointsAdapter.setItems(pointItems)

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
                pointsAdapter.deleteItem(position)

                // Also remove the item from SharedPreferences
                sharedPreferenceManager.removeAmount(position)
            }
        })

        itemTouchHelper.attachToRecyclerView(pointsRecyclerView)
    }
}