package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.activities.MainActivity
import com.perpetua.eazytopup.databinding.FragmentAirtimeBinding



class AirtimeFragment : Fragment() {
    private var _binding: FragmentAirtimeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAirtimeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = parentFragment?.parentFragment?.findNavController()
        binding.btnBuySelfAirtime.setOnClickListener {
            parentFragment?.parentFragment?.findNavController()?.navigate(AirtimeHostFragmentDirections.actionHomeHostFragmentToBuyAirtimeFragment("self"))
        }
        binding.btnBuyOthersAirtime.setOnClickListener {
            parentFragment?.parentFragment?.findNavController()?.navigate(AirtimeHostFragmentDirections.actionHomeHostFragmentToBuyAirtimeFragment("others"))
        }
    }

    override fun onDestroyView() {
       super.onDestroyView()
       _binding = null
    }

}