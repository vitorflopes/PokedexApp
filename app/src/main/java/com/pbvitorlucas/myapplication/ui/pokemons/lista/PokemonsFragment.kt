package com.pbvitorlucas.myapplication.ui.pokemons.lista

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.pbvitorlucas.myapplication.R
import com.pbvitorlucas.myapplication.databinding.FragmentPokemonsBinding
import com.pbvitorlucas.myapplication.ui.pokemons.lista.PokemonsViewModel
import com.google.android.material.snackbar.Snackbar

class PokemonsFragment : Fragment() {

    var _binding: FragmentPokemonsBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: PokemonsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(PokemonsViewModel::class.java)

        viewModel.pokemons.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {
                //binding.lvPokemons.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, it)
            } else {
                showSnackbar(view, "Ainda não á pokemons.")
            }
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            if (it.isNotBlank()){
                showSnackbar(view, it)
            }
        }

        binding.btnCriarPokemon.setOnClickListener {
            findNavController().navigate(R.id.pokemonFormFragment)
        }

        return view
    }

    private fun showSnackbar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }
}