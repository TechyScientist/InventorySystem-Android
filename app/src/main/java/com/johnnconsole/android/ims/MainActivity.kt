package com.johnnconsole.android.ims

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View.*
import com.google.android.material.snackbar.Snackbar
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
            }
        }

        override fun doInBackground(vararg params: String) {
            username = params[0]
            val password = params[1]
            val url = URL("https://api.johnnyconsole.com/ims/signin.php?username=$username&password=$password")
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
                    ApplicationSession.create(username, last_name, first_name, access)
                    finish()
                    startActivity(Intent(this@MainActivity, DashboardActivity::class.java))
                }
                etUsername.isEnabled = true
                etPassword.isEnabled = true
            }
        }
    }

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            btSignIn.setOnClickListener {_ ->
                if(etUsername.text.isNullOrBlank()) {
                    Snackbar.make(root, "Error: Username Missing", Snackbar.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                else if(etPassword.text.isNullOrBlank()) {
                    Snackbar.make(root, "Error: Password Missing", Snackbar.LENGTH_LONG).show()
                    return@setOnClickListener
                }
                else {
                    SignInTask().execute(etUsername.text.toString(), etPassword.text.toString())
                }
            }
        }

    }
}