package com.johnnconsole.android.ims.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.johnnconsole.android.ims.databinding.FragmentUserFunctionsBinding

class UserFunctionsFragment : Fragment() {

    private lateinit var binding: FragmentUserFunctionsBinding
    private lateinit var activity: FragmentActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentUserFunctionsBinding.inflate(inflater)
        activity = requireActivity()

        return binding.root
    }

}