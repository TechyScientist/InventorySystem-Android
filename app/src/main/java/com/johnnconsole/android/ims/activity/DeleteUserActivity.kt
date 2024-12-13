package com.johnnconsole.android.ims.activity

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.johnnconsole.android.ims.R
import com.johnnconsole.android.ims.data.ApplicationSession
import com.johnnconsole.android.ims.databinding.ActivityDeleteUserBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class DeleteUserActivity : AppCompatActivity() {

    private inner class GetUserListTask: AsyncTask<Unit, Unit, Unit>() {
        var error = ""
        var array: JSONArray? = null

        override fun onPreExecute() {
            with(binding) {
                pbIndicator.visibility = VISIBLE
                tvError.text = ""
                tvError.visibility = GONE
                llUsername.visibility = GONE
                btDeleteUser.visibility = GONE
            }
        }

        override fun doInBackground(vararg params: Unit) {
            val url = URL(
                "${prefs.getString("API_ENDPOINT", "")}/${prefs.getString("GET_USER_LIST_SCRIPT", "")}?exclude=${ApplicationSession.username}"
            )
            val connection = url.openConnection() as HttpsURLConnection
            connection.connect()
            val obj = JSONObject(BufferedReader(InputStreamReader(url.openStream())).readLine())
            if (obj.has("error")) {
                error = obj.getString("error")
            } else {
                array = obj.getJSONArray("users")
            }
        }

        override fun onPostExecute(result: Unit) {
            super.onPostExecute(result)
            with(binding) {
                pbIndicator.visibility = INVISIBLE
                if (error.isNotBlank()) {
                    tvError.text = error
                    tvError.visibility = VISIBLE
                }
                else {
                    for(i in 0 until array!!.length()) {
                        list.add(array!!.getString(i))
                    }
                    spUsername.adapter = UserAdapter()
                    llUsername.visibility = VISIBLE
                    btDeleteUser.visibility = VISIBLE
                }
            }
        }
    }

    private inner class UserAdapter: ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item) {
        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): String {
            return list[position]
        }
    }


    private lateinit var binding: ActivityDeleteUserBinding
    private lateinit var prefs: SharedPreferences
    private val list = ArrayList<String>()
    private var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDeleteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar!!.title = "IMS Admin: Remove User"
        prefs = getSharedPreferences("AndroidIMS", MODE_PRIVATE)

        GetUserListTask().execute()

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