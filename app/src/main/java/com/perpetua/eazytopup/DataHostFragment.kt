package com.perpetua.eazytopup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.perpetua.eazytopup.databinding.FragmentDataHostBinding
import com.perpetua.eazytopup.databinding.FragmentHomeHostBinding


class DataHostFragment : Fragment() {
    var _binding: FragmentDataHostBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDataHostBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

}