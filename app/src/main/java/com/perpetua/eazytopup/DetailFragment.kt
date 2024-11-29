package com.perpetua.eazytopup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.perpetua.eazytopup.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {
    private lateinit var binding: FragmentDetailBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //val itemTitle = arguments?.getString("itemTitle")

        // Use the itemTitle as needed in your fragment logic
        //binding.titleTextView.text = itemTitle
        val itemTitle = arguments?.getString("itemTitle")

        // Use the itemTitle to display in the titleTextView
        val title = if (itemTitle == "FAQ") {
            "Frequently Asked Questions"
        } else if (itemTitle == "Contact Us") {
            "Contact Us"
        } else if (itemTitle == "Terms of Service") {
            "Terms of Service"
        } else if (itemTitle == "Privacy Policy") {
            "Privacy Policy"
        } else {
            "Unknown Title"
        }
        binding.titleTextView.text = title
    }
}