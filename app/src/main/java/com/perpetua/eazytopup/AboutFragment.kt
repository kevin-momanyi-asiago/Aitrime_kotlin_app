package com.perpetua.eazytopup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.perpetua.eazytopup.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {
    private var _binding: FragmentAboutBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAboutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up your organization's profile information
        binding.organizationName.text = "EazyTopup"
        binding.organizationDescription.text = "powered by CHECHI company limited"
        binding.appDescription.text = "EazyTopup is easy to transact, purchase amount of airtime/data should not be  less than KSH10 also make sure the mobile number should be valid"
        binding.servicesDescription.text = "This app enables users to buy airtime and data either for themselves or for others across all networks."
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }



}