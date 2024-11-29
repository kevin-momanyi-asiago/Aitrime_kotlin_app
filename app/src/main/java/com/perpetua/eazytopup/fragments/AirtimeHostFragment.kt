package com.perpetua.eazytopup.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.perpetua.eazytopup.R
import com.perpetua.eazytopup.databinding.FragmentHomeHostBinding


class AirtimeHostFragment : Fragment() {
    var _binding: FragmentHomeHostBinding? = null
    private val binding get() = _binding!!
    //lateinit var toggle: ActionBarDrawerToggle
    //private lateinit var navController: NavController
    //private lateinit var drawerLayout: DrawerLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeHostBinding.inflate(inflater, container, false)
        val rootView = binding.root

       // drawerLayout = rootView.findViewById(R.id.drawerLayout)
        //val navigationView = binding.navView

        // Get the NavController
        //val navController = findNavController()

        // Set up the NavigationView with the NavController
        //navigationView.setupWithNavController(navController)

        return rootView

    }

    //override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //val bottomNavigationView = binding.bottomNavigationView
        //val hostFragment = childFragmentManager.findFragmentById(binding.homeHostNavHostFragment.id)
        //val navController = hostFragment?.findNavController()
        //if(navController != null){
            //bottomNavigationView.setupWithNavController(navController)
        //}

        //val drawerLayout = binding.drawerLayout
       // val toolBar = binding.toolbar
        //toggle = ActionBarDrawerToggle(activity, drawerLayout,toolBar, R.string.drawer_open, R.string.drawer_close)
        //drawerLayout.addDrawerListener(toggle)
    }

    //override fun onResume() {
      //  super.onResume()
       // toggle.syncState()
    //}

    //override fun onDestroyView() {
       // super.onDestroyView()
       // _binding = null
    //}
