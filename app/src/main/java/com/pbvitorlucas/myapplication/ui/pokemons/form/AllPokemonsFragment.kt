package com.pbvitorlucas.myapplication.ui.pokemons.form

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.ads.*
import com.pbvitorlucas.myapplication.R
import com.pbvitorlucas.myapplication.adapter.AdapterPokemon
import com.pbvitorlucas.myapplication.databinding.FragmentAllPokemonsBinding
import com.pbvitorlucas.myapplication.ui.pokemons.form.AllPokemonsVMF
import com.pbvitorlucas.myapplication.ui.pokemons.form.AllPokemonsViewModel

class AllPokemonsFragment : Fragment() {

    var _binding: FragmentAllPokemonsBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: AllPokemonsViewModel

    lateinit var mAdView: AdView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllPokemonsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this, AllPokemonsVMF(requireContext())).get(
            AllPokemonsViewModel::class.java)

        MobileAds.initialize(requireContext())
        mAdView = binding.adView2
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)
        /*
        binding.adView2.adListener = object: AdListener() {
            override fun onAdClicked() {
                // Code to be executed when the user clicks on an ad.
            }

            override fun onAdClosed() {
                // Code to be executed when the user is about to return
                // to the app after tapping on an ad.
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                Toast.makeText(requireContext(), adError.message, Toast.LENGTH_LONG).show()
                Log.d("TesteAnuncio", adError.message)
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
        */

        binding.rvPokemons.layoutManager = LinearLayoutManager(context)
        binding.rvPokemons.setHasFixedSize(true)

        viewModel.pokemonsList.observe(viewLifecycleOwner) {
            if (it.isNotEmpty()) {

                val lac = LayoutAnimationController(AnimationUtils.loadAnimation(context, R.anim.item_anim))
                lac.delay = 0.10f
                lac.order = LayoutAnimationController.ORDER_NORMAL

                val adapterPokemon = AdapterPokemon(requireContext(), it)
                binding.rvPokemons.layoutAnimation = lac
                binding.rvPokemons.adapter = adapterPokemon
                adapterPokemon.setOnItemClickListener(object : AdapterPokemon.onItemClickListener {
                    override fun onItemClick(position: Int) {
                        val pokemonName = it[position].name

                        val direction = AllPokemonsFragmentDirections
                            .actionAllPokemonsFragmentToPokemonFormFragment(pokemonName)
                        findNavController().navigate(direction)
                    }
                })

                binding.btnProximaPagAP.isVisible = true
                binding.btnAnteriosPagAP.isVisible = true
                binding.progressBar.isVisible = false
            }
        }
        viewModel.offsetObs.observe(viewLifecycleOwner) {
            binding.btnAnteriosPagAP.isVisible = it != 0
        }

        viewModel.pokemonListName.observe(viewLifecycleOwner) {
            val adapterNomePokemon : ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, it)
            binding.acPokemonAP.setAdapter(adapterNomePokemon)
        }

        binding.btnPesquisarPokemonNameAP.setOnClickListener {
            if (!binding.acPokemonAP.text.isNullOrBlank()) {
                if ((viewModel.pokemonListName.value!!).contains(binding.acPokemonAP.text.toString())) {
                    val direction = AllPokemonsFragmentDirections
                        .actionAllPokemonsFragmentToPokemonFormFragment(binding.acPokemonAP.text.toString())
                    findNavController().navigate(direction)
                }
                else {
                    Toast.makeText(requireContext(), "Nenhum Pokemon encontrado com esse nome", Toast.LENGTH_LONG).show()
                    Toast.makeText(requireContext(), "Digite o nome do Pokemon completo ou selecione um nome sugerido.", Toast.LENGTH_LONG).show()
                }
            }
            else {
                Toast.makeText(requireContext(), "Preencha o nome do Pokemon antes de pesquisar.", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnProximaPagAP.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.btnProximaPagAP.isVisible = false
            binding.btnAnteriosPagAP.isVisible = false
            viewModel.retornaOffsetAdd(requireContext())
        }

        binding.btnAnteriosPagAP.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.btnProximaPagAP.isVisible = false
            binding.btnAnteriosPagAP.isVisible = false
            viewModel.retornaOffsetMin(requireContext())
        }

        binding.btnVoltarAllPokemons.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }
}