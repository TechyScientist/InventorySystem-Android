package com.johnnconsole.android.ims.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout
import com.johnnconsole.android.ims.fragment.AdminFunctionsFragment
import com.johnnconsole.android.ims.R
import com.johnnconsole.android.ims.fragment.UserFunctionsFragment
import com.johnnconsole.android.ims.data.ApplicationSession
import com.johnnconsole.android.ims.databinding.ActivityDashboardBinding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Dashboard"

        with(binding) {
            tvWelcome.text = getString(R.string.DashboardWelcome, ApplicationSession.first_name)
            val userTab = tlFunctions.newTab().setText("User Functions")
            val adminTab = tlFunctions.newTab().setText("Admin Functions")
            tlFunctions.addTab(userTab)
            if(ApplicationSession.access == 1) tlFunctions.addTab(adminTab)
            supportFragmentManager.beginTransaction().add(fvTabContent.id, UserFunctionsFragment()).commitNow()
            tlFunctions.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    supportFragmentManager.beginTransaction().replace(fvTabContent.id,
                        if(tlFunctions.selectedTabPosition == 0) UserFunctionsFragment()
                        else AdminFunctionsFragment()
                    ).commitNow()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // Intentionally Blank
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Intentionally Blank
                }

            })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_dashboard, menu)
        return true

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.SignOut -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}