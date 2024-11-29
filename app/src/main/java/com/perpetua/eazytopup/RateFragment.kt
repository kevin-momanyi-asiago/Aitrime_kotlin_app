package com.perpetua.eazytopup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.perpetua.eazytopup.databinding.FragmentHelpBinding
import com.perpetua.eazytopup.databinding.FragmentRateBinding


class RateFragment : Fragment() {

    private var _binding: FragmentRateBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentRateBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.btnRateUs.setOnClickListener {
            val rating = binding.ratingBar.rating
            binding.textView.text = "Rating: $rating"
        }

        return view
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}