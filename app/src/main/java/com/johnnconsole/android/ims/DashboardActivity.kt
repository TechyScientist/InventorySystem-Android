package com.johnnconsole.android.ims

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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
            tlFunctions.addTab(tlFunctions.newTab().setText("User Functions"))
            if(ApplicationSession.access == 1) tlFunctions.addTab(tlFunctions.newTab().setText("Admin Functions"))
        }

    }
}