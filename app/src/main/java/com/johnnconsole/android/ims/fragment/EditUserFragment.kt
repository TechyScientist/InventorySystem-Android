package com.johnnconsole.android.ims.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.johnnconsole.android.ims.databinding.FragmentEditUserBinding

class EditUserFragment : Fragment() {

    private lateinit var binding: FragmentEditUserBinding
    private lateinit var activity: FragmentActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentEditUserBinding.inflate(inflater)
        activity = requireActivity()

        return binding.root
    }

}