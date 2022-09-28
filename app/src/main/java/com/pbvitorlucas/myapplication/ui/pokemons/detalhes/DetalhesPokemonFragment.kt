package com.pbvitorlucas.myapplication.ui.pokemons.detalhes

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.pbvitorlucas.myapplication.R
import com.pbvitorlucas.myapplication.databinding.FragmentDetalhesPokemonBinding
import com.pbvitorlucas.myapplication.ui.pokemons.detalhes.DetalhesPokemonViewModel
import com.squareup.picasso.Picasso

class DetalhesPokemonFragment : Fragment() {

    var _binding: FragmentDetalhesPokemonBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: DetalhesPokemonViewModel
    private val argumentos by navArgs<DetalhesPokemonFragmentArgs>()

    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "TAGAdd"

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetalhesPokemonBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(DetalhesPokemonViewModel::class.java)

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),"ca-app-pub-1754457021414641/2818574459", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                adError?.toString()?.let { Log.d(TAG, it) }
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.")
            }

            override fun onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                Log.d(TAG, "Ad dismissed fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdFailedToShowFullScreenContent(p0: AdError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.")
                mInterstitialAd = null
            }

            override fun onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.")
            }

            override fun onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.")
            }
        }

        viewModel.retornaPokemon(argumentos.pokemonId)

        viewModel.msg.observe(viewLifecycleOwner) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }

        viewModel.pokemon.observe(viewLifecycleOwner) {
            if (it != null) {
                val pokemonColor = resources.getColor(retornaCor(it.color!!))
                binding.clCardPokemonDP.setBackgroundColor(pokemonColor)

                Picasso.get().load(it.spriteFront).into(binding.ivSpritePokemonDP)
                if (!it.gender!!) {
                    binding.imGenderDP.setImageResource(R.drawable.ic_baseline_female_24)
                }

                binding.tvLevelDP.text = it.lvl
                binding.tvSpeciesDP.text = it.species
                binding.tvHbilidadeDP.text = it.ability
                binding.tvNatureDP.text = it.nature

                binding.tvHpDP.text = it.iv!![0]
                ObjectAnimator.ofInt(binding.pbHpDP, "progress", it.iv!![0].toInt())
                    .setDuration(2000).start()
                binding.tvAttackDP.text = it.iv!![1]
                ObjectAnimator.ofInt(binding.pbAttackDP, "progress", it.iv!![1].toInt())
                    .setDuration(2000).start()
                binding.tvDefenseDP.text = it.iv!![2]
                ObjectAnimator.ofInt(binding.pbDefenseDP, "progress", it.iv!![2].toInt())
                    .setDuration(2000).start()
                binding.tvSpAttackDP.text = it.iv!![3]
                ObjectAnimator.ofInt(binding.pbSpAttackDP, "progress", it.iv!![3].toInt())
                    .setDuration(2000).start()
                binding.tvSpDefenseDP.text = it.iv!![4]
                ObjectAnimator.ofInt(binding.pbSpDefenseDP, "progress", it.iv!![4].toInt())
                    .setDuration(2000).start()
                binding.tvSpeedDP.text = it.iv!![5]
                ObjectAnimator.ofInt(binding.pbSpeedDP, "progress", it.iv!![5].toInt())
                    .setDuration(2000).start()

                binding.tvEvHpDP.text = it.ev!![0]
                binding.tvEvAtkDP.text = it.ev!![1]
                binding.tvEvDefDP.text = it.ev!![2]
                binding.tvEvSpAtkDP.text = it.ev!![3]
                binding.tvEvSpDefDP.text = it.ev!![4]
                binding.tvEvSpeedDP.text = it.ev!![5]

                binding.tvMove1DP.text = it.moves!![0]
                binding.tvMove2DP.text = it.moves!![1]
                binding.tvMove3DP.text = it.moves!![2]
                binding.tvMove4DP.text = it.moves!![3]

                Picasso.get().load(it.spriteItem).into(binding.ivSpriteItemDP)
                binding.tvItemNameDP.text = it.item
            }
        }

        binding.btnExcluirPokemonDP.setOnClickListener {
            viewModel.excluirPokemon()
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
            Toast.makeText(requireContext(), "Pokemon excluido!", Toast.LENGTH_LONG).show()
            val direction = DetalhesPokemonFragmentDirections.actionDetalhesPokemonFragmentToPerfilFragment()
            findNavController().navigate(direction)
        }

        binding.btnVoltarDP.setOnClickListener {
            findNavController().popBackStack()
        }

        return view
    }

    private fun retornaCor(nomeCor: String): Int {
        if (nomeCor == "black")
            return R.color.black_pokemon
        else if (nomeCor == "blue") {
            return R.color.blue_pokemon
        }
        else if (nomeCor == "brown") {
            return R.color.brown_pokemon
        }
        else if (nomeCor == "gray") {
            return R.color.gray_pokemon
        }
        else if (nomeCor == "green") {
            return R.color.green_pokemon
        }
        else if (nomeCor == "pink") {
            return R.color.pink_pokemon
        }
        else if (nomeCor == "purple") {
            return R.color.purple_pokemon
        }
        else if (nomeCor == "red") {
            return R.color.red_pokemon
        }
        else if (nomeCor == "white") {
            return R.color.white_pokemon
        }
        else if (nomeCor == "yellow") {
            return R.color.yellow_pokemon
        }

        return R.color.white_pokemon
    }
}