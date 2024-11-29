package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.perpetua.eazytopup.HelpItem
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.adapters.HelpAdapter
import com.perpetua.eazytopup.adapters.RedeemPointsAdapter
import com.perpetua.eazytopup.databinding.FragmentHelpBinding
import com.perpetua.eazytopup.databinding.FragmentPointsBinding
import com.perpetua.eazytopup.models.RedeemPoints
import androidx.navigation.fragment.findNavController


class HelpFragment : Fragment() {

    private lateinit var binding: FragmentHelpBinding
    private lateinit var helpAdapter: HelpAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHelpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val helpItems = getHelpItems() // Replace with your logic to fetch help items

        helpAdapter = HelpAdapter(helpItems) { clickedItem ->
            // Handle item click
            navigateToDetailFragment(clickedItem)
        }

        binding.helpList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = helpAdapter
        }
    }

    // Example function to generate a list of help items
    private fun getHelpItems(): List<HelpItem> {
        return listOf(
            HelpItem("FAQ"),
            HelpItem("Contact Us"),
            HelpItem("Terms of Service"),
            HelpItem("Privacy Policy")
        )
    }
    private fun navigateToDetailFragment(clickedItem: HelpItem) {
        val action = HelpFragmentDirections.actionHelpFragmentToDetailFragment(clickedItem.title)
        findNavController().navigate(action)
    }

}


