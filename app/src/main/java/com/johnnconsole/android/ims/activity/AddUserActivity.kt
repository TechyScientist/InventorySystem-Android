package com.johnnconsole.android.ims.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.johnnconsole.android.ims.R
import com.johnnconsole.android.ims.databinding.ActivityAddUserBinding

class AddUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}