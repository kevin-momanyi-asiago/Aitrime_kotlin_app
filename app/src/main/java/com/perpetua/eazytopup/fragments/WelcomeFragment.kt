package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.databinding.FragmentWelcomeBinding


class WelcomeFragment : Fragment() {
    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //val decorView = activity!!.window.decorView // Hide the status bar.

        //val uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        //decorView.systemUiVisibility = uiOptions
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnAirtime.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment_to_homeHostFragment)
        }
        binding.btnData.setOnClickListener {
            it.findNavController().navigate(R.id.action_welcomeFragment_to_dataHostFragment)
        }
    }

        //override fun onDestroyView() {
           // super.onDestroyView()
           // _binding = null
       // }


}