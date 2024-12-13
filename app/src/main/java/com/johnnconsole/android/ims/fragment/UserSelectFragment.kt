package com.johnnconsole.android.ims.fragment

import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.FragmentActivity
import com.johnnconsole.android.ims.databinding.FragmentUserSelectBinding
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class UserSelectFragment : Fragment() {

    private lateinit var binding: FragmentUserSelectBinding
    private lateinit var activity: FragmentActivity
    private lateinit var prefs: SharedPreferences
    private var list = ArrayList<String>()

    private inner class GetUserListTask: AsyncTask<Unit, Unit, Unit>() {
        var error = ""
        var array: JSONArray? = null

        override fun onPreExecute() {
            list.clear()
            with(binding) {
                pbIndicator.visibility = VISIBLE
                tvError.text = ""
                tvError.visibility = GONE
                llUsername.visibility = GONE
                btSelectUser.visibility = GONE
            }
        }

        override fun doInBackground(vararg params: Unit) {
            val url = URL(
                "${prefs.getString("API_ENDPOINT", "")}/${prefs.getString("GET_USER_LIST_SCRIPT", "")}"
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
                    btSelectUser.visibility = VISIBLE
                    btSelectUser.isEnabled = true
                    spUsername.isEnabled = true
                }
            }
        }
    }

    private inner class UserAdapter: ArrayAdapter<String>(activity, android.R.layout.simple_spinner_dropdown_item) {

        override fun getCount(): Int {
            return list.size
        }

        override fun getItem(position: Int): String {
            return list[position]
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserSelectBinding.inflate(inflater)
        activity = requireActivity()
        prefs = activity.getSharedPreferences("AndroidIMS", MODE_PRIVATE)

        GetUserListTask().execute()

        return binding.root
    }

}