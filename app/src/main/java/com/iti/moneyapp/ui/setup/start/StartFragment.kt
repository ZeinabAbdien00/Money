package com.iti.moneyapp.ui.setup.start

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.iti.moneyapp.databinding.FragmentStartBinding

class StartFragment : Fragment() {

    private lateinit var binding: FragmentStartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnClickListener()

    }

    private fun setOnClickListener() {
        binding.apply {
            btnLogin.setOnClickListener {
                findNavController().navigate(StartFragmentDirections.actionStartFragmentToLoginFragment())
            }
            btnSignup.setOnClickListener {
                findNavController().navigate(StartFragmentDirections.actionStartFragmentToSignUpFragment())
            }
        }
    }

}