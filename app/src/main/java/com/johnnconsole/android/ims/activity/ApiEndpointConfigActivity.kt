package com.johnnconsole.android.ims.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.johnnconsole.android.ims.databinding.ActivityApiEndpointConfigBinding
import com.johnnconsole.android.ims.R

class ApiEndpointConfigActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApiEndpointConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiEndpointConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Configure API Endpoint"
        val prefs = getSharedPreferences("AndroidIMS", MODE_PRIVATE)

        with(binding) {
            etEndpoint.setText(prefs.getString("API_ENDPOINT", ""))
            etSignInScript.setText(prefs.getString("SIGN_IN_SCRIPT", ""))
            etAddUserScript.setText(prefs.getString("ADD_USER_SCRIPT", ""))
            etGetUserListScript.setText(prefs.getString("GET_USER_LIST_SCRIPT", ""))
            etDeleteUserScript.setText(prefs.getString("DELETE_USER_SCRIPT", ""))

            btSaveAPI.setOnClickListener {_ ->
                if(!(etEndpoint.text.isNullOrBlank() && etSignInScript.text.isNullOrBlank() &&
                    etAddUserScript.text.isNullOrBlank() && etGetUserListScript.text.isNullOrBlank() &&
                    etDeleteUserScript.text.isNullOrBlank())) {
                    val editor = prefs.edit()
                    editor.putString("API_ENDPOINT", etEndpoint.text.toString())
                    editor.putString("SIGN_IN_SCRIPT", etSignInScript.text.toString())
                    editor.putString("ADD_USER_SCRIPT", etAddUserScript.text.toString())
                    editor.putString("GET_USER_LIST_SCRIPT", etGetUserListScript.text.toString())
                    editor.putString("DELETE_USER_SCRIPT", etDeleteUserScript.text.toString())
                    editor.apply()
                    finish()
                }
                else {
                    Snackbar.make(root, "Missing API Information", LENGTH_LONG).show()
                }
            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return if(!intent.getBooleanExtra("MENU_FLAG", false)) {
            menuInflater.inflate(R.menu.menu_back, menu)
            true
        }
        else false
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.Back) {
            finish()
            true
        }
        else super.onOptionsItemSelected(item)
    }
}