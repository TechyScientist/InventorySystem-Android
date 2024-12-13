package com.johnnconsole.android.ims.activity

import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View.*
import com.johnnconsole.android.ims.R
import com.johnnconsole.android.ims.data.ApplicationSession
import com.johnnconsole.android.ims.databinding.ActivityMainBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class MainActivity : AppCompatActivity() {

    private inner class SignInTask: AsyncTask<String, Unit, Unit>() {

        var error = ""
        var username = ""
        var first_name = ""
        var last_name = ""
        var access = 0

        override fun onPreExecute() {
            super.onPreExecute()
            with(binding) {
                pbIndicator.visibility = VISIBLE
                tvError.text = ""
                tvError.visibility = GONE
                etUsername.isEnabled = false
                etPassword.isEnabled = false
                btSignIn.isEnabled = false
            }
        }

        override fun doInBackground(vararg params: String) {
            username = params[0]
            val password = params[1]
            val url = URL("${prefs.getString("API_ENDPOINT", "")}/${prefs.getString("SIGN_IN_SCRIPT", "")}?username=$username&password=$password")
            val connection = url.openConnection() as HttpsURLConnection
            connection.connect()
            val obj = JSONObject(BufferedReader(InputStreamReader(url.openStream())).readLine())
            if(obj.has("error")) {
                error = obj.getString("error")
            }
           else {
                first_name = obj.getString("first_name")
                last_name = obj.getString("last_name")
                access = obj.getInt("access")
            }
        }

        override fun onPostExecute(result: Unit) {
            with(binding) {
                pbIndicator.visibility = INVISIBLE
                if(error.isNotBlank()) {
                    tvError.text = error
                    tvError.visibility = VISIBLE
                }
                else {
                    etUsername.text.clear()
                    etPassword.text.clear()
                    ApplicationSession.create(username, last_name, first_name, access)
                    startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
                }
                etUsername.isEnabled = true
                etPassword.isEnabled = true
                btSignIn.isEnabled = true
            }
        }
    }

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btSignIn.setOnClickListener {_ ->
                if(etUsername.text.isNullOrBlank()) {
                    tvError.text = getString(R.string.Missing, "Username")
                    tvError.visibility = VISIBLE
                    return@setOnClickListener
                }
                else if(etPassword.text.isNullOrBlank()) {
                    tvError.text = getString(R.string.Missing, "Password")
                    tvError.visibility = VISIBLE
                    return@setOnClickListener
                }
                else {
                    SignInTask().execute(etUsername.text.toString(), etPassword.text.toString())
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        prefs = getSharedPreferences("AndroidIMS", MODE_PRIVATE)
        if(missingAPIFields(prefs)) {
            startActivity(Intent(this, ApiEndpointConfigActivity::class.java).putExtra("MENU_FLAG", true))
        }
    }

    private fun missingAPIFields(prefs: SharedPreferences): Boolean {
        return !(
            prefs.contains("API_ENDPOINT") &&
            prefs.contains("SIGN_IN_SCRIPT") &&
            prefs.contains("ADD_USER_SCRIPT") &&
            prefs.contains("GET_USER_LIST_SCRIPT") &&
            prefs.contains("UPDATE_USER_SCRIPT") &&
            prefs.contains("DELETE_USER_SCRIPT")
        )
    }
}