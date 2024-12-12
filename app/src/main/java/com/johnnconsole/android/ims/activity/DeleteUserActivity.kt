package com.johnnconsole.android.ims.activity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.johnnconsole.android.ims.R
import com.johnnconsole.android.ims.databinding.ActivityDeleteUserBinding

class DeleteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDeleteUserBinding
    private lateinit var prefs: SharedPreferences
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "IMS Admin: Remove User"
        prefs = getSharedPreferences("AndroidIMS", MODE_PRIVATE)

        with(binding) {

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_back, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.Back) {
            finish()
            true
        } else super.onOptionsItemSelected(item)
    }
}