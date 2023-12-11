package com.example.testlogin.presentation

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.testlogin.PREFS
import com.example.testlogin.R
import com.example.testlogin.TOKEN
import com.example.testlogin.databinding.FragmentLoginBinding
import com.example.testlogin.states.States
import com.example.testlogin.viewmodels.LoginViewModel
import com.example.testlogin.viewmodels.LoginViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val binding get() = _binding!!

    private val viewModel: LoginViewModel by viewModels {
        LoginViewModelFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.login.setOnClickListener {
            val login = binding.username.text.toString()
            val password = binding.password.text.toString()
            viewModel.login(login, password)

            viewLifecycleOwner.lifecycleScope.launch {
                lifecycle.repeatOnLifecycle(Lifecycle.State.CREATED) {
                    viewModel.loginData.collect {
                        if (it?.success == "true") {
                            val sharedPrefs =
                                requireContext().getSharedPreferences(
                                    PREFS,
                                    Context.MODE_PRIVATE
                                )
                            val editor = sharedPrefs.edit()
                            editor.putString(TOKEN, it.response.token)
                            editor.apply()
                            findNavController().navigate(R.id.loggedFragment)
                        }
                        delay(200)
                        if (it?.success == "false") {
                            val error = getString(R.string.invalid_username)
                            val appContext = context?.applicationContext ?: return@collect
                            Toast.makeText(appContext, error, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}