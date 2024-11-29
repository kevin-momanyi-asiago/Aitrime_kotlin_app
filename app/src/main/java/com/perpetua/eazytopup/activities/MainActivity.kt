package com.perpetua.eazytopup.activities


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import com.perpetua.eazytopup.R
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.perpetua.eazytopup.fragments.HistoryFragment


class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    lateinit var appBarConfiguration: AppBarConfiguration
    //private val historyFragment = HistoryFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        val decorView: View =window.decorView
        val uiOption:Int=View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        decorView.setSystemUiVisibility(uiOption)
        decorView.setBackgroundColor(resources.getColor(android.R.color.white))

        setContentView(R.layout.activity_main)


        //to enable bottom navigation bar to naviate
        val navHostFragment=supportFragmentManager.findFragmentById(R.id.fragmentContainerView)as NavHostFragment
        navController=navHostFragment.navController

        val bottomNavigationView=findViewById<BottomNavigationView>(R.id.bottom_nav)
        bottomNavigationView.setupWithNavController(navController)

        //to enable navigation view navigate

        val navigationView: NavigationView =findViewById<NavigationView>(R.id.nav_view)
        navigationView.setupWithNavController(navController)

        //to enable display toolbar on the layout(this code must be in this order)
        val drawerL:DrawerLayout=findViewById(R.id.drawer_layout)
        appBarConfiguration=AppBarConfiguration(navController.graph,drawerL)
        setSupportActionBar(findViewById(R.id.tool_bar))
        setupActionBarWithNavController(navController,drawerL)

    }

    override fun onSupportNavigateUp(): Boolean {
        //navController=findNavController(R.id.fragmentContainerView)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }



}

