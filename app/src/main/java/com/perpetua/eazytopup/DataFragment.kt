package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.perpetua.eazytopup.DataHostFragmentDirections
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.databinding.FragmentAirtimeBinding
import com.perpetua.eazytopup.databinding.FragmentDataBinding



class DataFragment : Fragment() {
    private var _binding: FragmentDataBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = parentFragment?.parentFragment?.findNavController()
        binding.btnBuySelfData.setOnClickListener {
            parentFragment?.parentFragment?.findNavController()?.navigate(DataHostFragmentDirections.actionDataHostFragmentToBuyDataFragment2("self"))
        }
        binding.btnBuyOthersData.setOnClickListener {
            parentFragment?.parentFragment?.findNavController()?.navigate(DataHostFragmentDirections.actionDataHostFragmentToBuyDataFragment2("others"))
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}