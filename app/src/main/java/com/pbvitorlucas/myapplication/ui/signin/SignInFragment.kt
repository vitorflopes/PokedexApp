package com.pbvitorlucas.myapplication.ui.signin

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.pbvitorlucas.myapplication.R
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pbvitorlucas.myapplication.databinding.FragmentSignInBinding
import com.google.android.gms.ads.*
import com.google.android.material.snackbar.Snackbar

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignInViewModel

    lateinit var mAdView: AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(SignInViewModel::class.java)

        MobileAds.initialize(requireContext())
        mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        binding.adView.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                Log.d("TesteAnuncio", adError.message)
                Toast.makeText(requireContext(), adError.message, Toast.LENGTH_LONG).show()
            }

            override fun onAdImpression() {
                // Code to be executed when an impression is recorded
                // for an ad.
            }

            override fun onAdLoaded() {
                Log.d("TesteAnuncio", "carregado")
            }

            override fun onAdOpened() {
                // Code to be executed when an ad opens an overlay that
                // covers the screen.
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(R.id.perfilFragment)
            }
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            binding.progressBar3.isVisible = false
            if (it.isNotBlank())
                Snackbar.make(view, it, Snackbar.LENGTH_LONG).show()
        }

        lerPref(binding.edtTxtSigninEmail)

        binding.btnSignInAcessar.setOnClickListener {
            binding.progressBar3.isVisible = true
            val email = binding.edtTxtSigninEmail.text.toString()
            val password = binding.edtTxtSigninSenha.text.toString()
            val lembrar = binding.cbSigninLembrar.isChecked
            if (lembrar) {
                salvarPref(email)
            }
            viewModel.autenticar(email, password)
        }

        binding.txtVwSignupLink.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment)
        }

        return view
    }

    fun lerPref (edtText: EditText) {
        val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val email = pref.getString("email", null)
        edtText.setText(email)
    }

    fun salvarPref (email: String) {
        val pref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with (pref.edit()) {
            putString("email", email)
            apply()
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = viewModel.retornaUsuarioLogado()
        if (currentUser != null) {
            val direction = SignInFragmentDirections.actionSignInFragmentToPerfilFragment()
            findNavController().navigate(direction)
        }
    }
}