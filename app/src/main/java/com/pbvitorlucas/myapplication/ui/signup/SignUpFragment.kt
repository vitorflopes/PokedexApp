package com.pbvitorlucas.myapplication.ui.signup

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.pbvitorlucas.myapplication.databinding.FragmentSignUpBinding
import com.pbvitorlucas.myapplication.ui.signup.SignUpViewModel

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SignUpViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(SignUpViewModel::class.java)

        viewModel.status.observe(viewLifecycleOwner) {
            if (it)
                findNavController().popBackStack()
        }
        binding.btnSignupAcessar.setOnClickListener {
            val nomeCampeao = binding.edtTxtSignupNomeCampeao.text.toString()
            val email = binding.edtTxtSignupEmail.text.toString()
            val senha = binding.edtTxtSignupSenha.text.toString()
            val repSenha = binding.edtTxtSignupReSenha.text.toString()
            if (senha == repSenha) {
                viewModel.salvarCampeao(nomeCampeao, email, senha)
            }
            else {
                Toast.makeText(context, "As senhas precisam ser iguais.", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnVoltar.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        val imagem: Bitmap = data!!.getParcelableExtra("data")!!
        //binding.imFotoCadastro.setImageBitmap(imagem)
        viewModel.pegarFoto(imagem)
    }
}