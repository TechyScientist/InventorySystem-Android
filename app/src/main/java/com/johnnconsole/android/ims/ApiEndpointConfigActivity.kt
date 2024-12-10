package com.johnnconsole.android.ims

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.johnnconsole.android.ims.databinding.ActivityApiEndpointConfigBinding

class ApiEndpointConfigActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApiEndpointConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiEndpointConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Configure API Endpoint"
    }
}