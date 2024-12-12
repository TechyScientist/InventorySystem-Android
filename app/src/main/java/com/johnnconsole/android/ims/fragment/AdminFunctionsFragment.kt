package com.johnnconsole.android.ims.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.johnnconsole.android.ims.activity.AddUserActivity
import com.johnnconsole.android.ims.activity.ApiEndpointConfigActivity
import com.johnnconsole.android.ims.databinding.FragmentAdminFunctionsBinding

class AdminFunctionsFragment : Fragment() {

    private lateinit var binding: FragmentAdminFunctionsBinding
    private lateinit var activity: FragmentActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAdminFunctionsBinding.inflate(inflater)
        activity = requireActivity()
        with(binding) {
            btConfigureAPI.setOnClickListener {_ ->
                activity.startActivity(Intent(activity, ApiEndpointConfigActivity::class.java))
            }

            btAddUser.setOnClickListener {_ ->
                activity.startActivity(Intent(activity, AddUserActivity::class.java))
            }
        }
        return binding.root
    }

}