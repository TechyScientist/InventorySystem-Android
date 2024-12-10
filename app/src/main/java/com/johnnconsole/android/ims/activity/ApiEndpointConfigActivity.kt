package com.johnnconsole.android.ims.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
import com.johnnconsole.android.ims.databinding.ActivityApiEndpointConfigBinding

class ApiEndpointConfigActivity : AppCompatActivity() {
    private lateinit var binding: ActivityApiEndpointConfigBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityApiEndpointConfigBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "Configure API Endpoint"
        var prefs = getSharedPreferences("AndroidIMS", MODE_PRIVATE)

        with(binding) {
            etEndpoint.setText(prefs.getString("API_ENDPOINT", ""))
            etSignInScript.setText(prefs.getString("SIGN_IN_SCRIPT", ""))

            btSaveAPI.setOnClickListener {_ ->
                if(!etEndpoint.text.isNullOrBlank() && !etSignInScript.text.isNullOrBlank()) {
                    val editor = prefs.edit()
                    editor.putString("API_ENDPOINT", etEndpoint.text.toString())
                    editor.putString("SIGN_IN_SCRIPT", etSignInScript.text.toString())
                    editor.apply()
                    finish()
                }
                else {
                    Snackbar.make(root, "Missing API Information", LENGTH_LONG).show()
                }
            }

        }
    }
}