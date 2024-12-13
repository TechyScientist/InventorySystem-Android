package com.johnnconsole.android.ims.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.johnnconsole.android.ims.R
import com.johnnconsole.android.ims.databinding.ActivityEditUserBinding
import com.johnnconsole.android.ims.fragment.UserSelectFragment

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "IMS Admin: Edit User"

        with(binding) {
            supportFragmentManager.beginTransaction().add(fvFragment.id, UserSelectFragment()).commitNow()
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