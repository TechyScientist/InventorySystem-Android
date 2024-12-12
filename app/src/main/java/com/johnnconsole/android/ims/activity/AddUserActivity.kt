package com.johnnconsole.android.ims.activity

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.johnnconsole.android.ims.R
import com.johnnconsole.android.ims.databinding.ActivityAddUserBinding
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class AddUserActivity : AppCompatActivity() {

    private inner class AddUserTask: AsyncTask<String, Unit, Unit>() {
        var error = ""

        override fun onPreExecute() {
            super.onPreExecute()
            with(binding) {
                pbIndicator.visibility = VISIBLE
                tvError.text = ""
                tvError.visibility = GONE
                etUsername.isEnabled = false
                etFirstName.isEnabled = false
                etLastName.isEnabled = false
                etPassword.isEnabled = false
                spAccess.isEnabled = false
            }
        }

        override fun doInBackground(vararg params: String) {
            val username = params[0]
            val firstName = params[1]
            val lastName = params[2]
            val password = params[3]
            val access = params[4].toInt()
            val url = URL(
                "${prefs.getString("API_ENDPOINT", "")}/${prefs.getString("ADD_USER_SCRIPT", "")}?username=$username&first_name=$firstName&last_name=$lastName&password=$password&access=$access"
            )
            Log.d("URL", url.toString())
            val connection = url.openConnection() as HttpsURLConnection
            connection.connect()
            val obj = JSONObject(BufferedReader(InputStreamReader(url.openStream())).readLine())
            if (obj.has("error")) {
                error = obj.getString("error")
            }
        }

        override fun onPostExecute(result: Unit?) {
            super.onPostExecute(result)
            with(binding) {
                snackbar?.dismiss()
                etUsername.isEnabled = true
                etFirstName.isEnabled = true
                etLastName.isEnabled = true
                etPassword.isEnabled = true
                spAccess.isEnabled = true
                pbIndicator.visibility = INVISIBLE
                if (error.isNotBlank()) {
                    tvError.text = error
                    tvError.visibility = VISIBLE
                } else {
                    etUsername.text.clear()
                    etFirstName.text.clear()
                    etLastName.text.clear()
                    etPassword.text.clear()
                    spAccess.setSelection(0)
                    Snackbar.make(root, "User Added Successfully", Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }

    private lateinit var binding: ActivityAddUserBinding
    private lateinit var prefs: SharedPreferences
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "IMS Admin: Add User"
        prefs = getSharedPreferences("AndroidIMS", MODE_PRIVATE)

        with(binding) {
            btAddUser.setOnClickListener {_ ->
                if(etUsername.text.isNullOrBlank()) {
                    tvError.text = getString(R.string.Missing, "Username")
                    tvError.visibility = VISIBLE
                    return@setOnClickListener
                }
                else if(etFirstName.text.isNullOrBlank()) {
                    tvError.text = getString(R.string.Missing, "First Name")
                    tvError.visibility = VISIBLE
                    return@setOnClickListener
                }
                else if(etLastName.text.isNullOrBlank()) {
                    tvError.text = getString(R.string.Missing, "Last Name")
                    tvError.visibility = VISIBLE
                    return@setOnClickListener
                }
                else if(etPassword.text.isNullOrBlank()) {
                    tvError.text = getString(R.string.Missing, "Password")
                    tvError.visibility = VISIBLE
                    return@setOnClickListener
                }
                else {
                    AddUserTask().execute(
                        etUsername.text.toString(),
                        etFirstName.text.toString(),
                        etLastName.text.toString(),
                        etPassword.text.toString(),
                        spAccess.selectedItemPosition.toString()
                    )
                }
            }
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