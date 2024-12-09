package com.johnnconsole.android.ims

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.johnnconsole.android.ims.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            //TODO: Add UI Code
        }

    }
}