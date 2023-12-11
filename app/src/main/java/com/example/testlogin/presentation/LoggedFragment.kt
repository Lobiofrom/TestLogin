package com.example.testlogin.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.testlogin.PREFS
import com.example.testlogin.R
import com.example.testlogin.TOKEN
import com.example.testlogin.adapterDelegate.AdapterDelegate
import com.example.testlogin.databinding.FragmentLoggedBinding
import com.example.testlogin.states.States
import com.example.testlogin.viewmodels.LoggedViewModel
import com.example.testlogin.viewmodels.LoggedViewModelFactory
import kotlinx.coroutines.launch

class LoggedFragment : Fragment() {

    private var _binding: FragmentLoggedBinding? = null
    private val binding get() = _binding!!

    private val viewModel: LoggedViewModel by viewModels {
        LoggedViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoggedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPrefs = requireContext().getSharedPreferences(PREFS, Context.MODE_PRIVATE)
        val token = sharedPrefs.getString(TOKEN, "error")

        if (token != null) {
            viewModel.getPayments(token)
        }

        val adapter = AdapterDelegate()
        binding.recycler.adapter = adapter

        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.list.collect {
                    adapter.submitList(it)
                }
            }
        }

        binding.button.setOnClickListener {
            val editor = sharedPrefs.edit()
            editor.putString(TOKEN, "")
            editor.apply()
            findNavController().navigate(R.id.loginFragment)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect {
                    when (it) {
                        States.Loading -> binding.loading.visibility = View.VISIBLE
                        States.Success -> binding.loading.visibility = View.GONE
                    }

                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    requireActivity().moveTaskToBack(true)
                }
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}