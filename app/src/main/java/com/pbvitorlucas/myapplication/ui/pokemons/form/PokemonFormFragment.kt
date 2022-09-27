package com.pbvitorlucas.myapplication.ui.pokemons.form

import android.animation.ObjectAnimator
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.pbvitorlucas.myapplication.databinding.FragmentPokemonFormBinding
import com.pbvitorlucas.myapplication.ui.pokemons.form.PokemonFormViewModel
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso

class PokemonFormFragment : Fragment() {

    var _binding: FragmentPokemonFormBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: PokemonFormViewModel
    private val argumentos by navArgs<PokemonFormFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPokemonFormBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(PokemonFormViewModel()::class.java)

        viewModel.retornaPokemon(argumentos.pokemonName, requireContext())

        viewModel.pokemon.observe(viewLifecycleOwner) {
            Picasso.get().load(it.sprites.front_default).into(binding.ivDexImg)
            binding.ivDexImg.animate().apply {
                duration = 0
                scaleX(0.00f)
                scaleY(0.00f)
            }.withEndAction {
                binding.ivDexImg.animate().apply {
                    duration = 1000
                    scaleX(1.00f)
                    scaleY(1.00f)
                }
            }

            binding.textView.text = it.name
            binding.tvDexspeciesname.text = it.species.name
            binding.tvHtnum.text = ((it.height)*10).toString() + " cm"
            binding.tvWtnum.text = ((it.weight)/10).toString() + " kg"
            binding.tvExpnum.text = it.base_experience.toString()

            binding.ivDextype1.text = it.types[0].type.name
            if (it.types.size > 1) {
                binding.ivDextype2.text = it.types[1].type.name
            }

            for (abilit in it.abilities) {
                if (abilit.slot == 3) {
                    binding.tvHiddenAbi.text = abilit.ability.name
                }
                if (abilit.slot == 1) {
                    binding.tvAbi1.text = abilit.ability.name
                }
                if (abilit.slot == 2) {
                    binding.textView39.text = abilit.ability.name
                }
            }

            //Stats
            val hp = it.stats[0].base_stat
            val hpEffort = it.stats[0].effort

            val attack = it.stats[1].base_stat
            val attackEffort = it.stats[1].effort

            val defense = it.stats[2].base_stat
            val attackDefense = it.stats[2].effort

            val spAttack = it.stats[3].base_stat
            val spAttackEffort = it.stats[3].effort

            val spDefense = it.stats[4].base_stat
            val spDefenseEffort = it.stats[4].effort

            val speed = it.stats[5].base_stat
            val speedEffort = it.stats[5].effort

            val bst = hp + attack + defense + spAttack + spDefense + speed

            binding.tvDexbstnum.text = bst.toString()

            binding.tvHpevvalue.text = hpEffort.toString()
            binding.tvDexhpnum.text = hp.toString()
            //binding.pbHp.progress = hp
            ObjectAnimator.ofInt(binding.pbHp, "progress", hp)
                .setDuration(2000).start()

            binding.tvAtkevvalue.text = attackEffort.toString()
            binding.tvDexatknum.text = attack.toString()
            //binding.pbAtk.progress = attack
            ObjectAnimator.ofInt(binding.pbAtk, "progress", attack)
                .setDuration(2000).start()

            binding.tvDefevvalue.text = attackDefense.toString()
            binding.tvDexdefnum.text = defense.toString()
            //binding.pbDef.progress = defense
            ObjectAnimator.ofInt(binding.pbDef, "progress", defense)
                .setDuration(2000).start()

            binding.tvSpatkevvalue.text = spAttackEffort.toString()
            binding.tvDexspatknum.text = spAttack.toString()
            //binding.pbSpatk.progress = spAttack
            ObjectAnimator.ofInt(binding.pbSpatk, "progress", spAttack)
                .setDuration(2000).start()

            binding.tvSpdefevvalue.text = spDefenseEffort.toString()
            binding.tvDexspdefnum.text = spDefense.toString()
            //binding.pbSpdef.progress = spDefense
            ObjectAnimator.ofInt(binding.pbSpdef, "progress", spDefense)
                .setDuration(2000).start()

            binding.tvSpdevvalue.text = speedEffort.toString()
            binding.tvDexspdnum.text = speed.toString()
            //binding.pbSpd.progress = speed
            ObjectAnimator.ofInt(binding.pbSpd, "progress", speed)
                .setDuration(2000).start()
        }

        viewModel.species.observe(viewLifecycleOwner) {
            binding.tvGroup1.text = it.egg_groups[0].name
            if (it.egg_groups.size > 1) {
                binding.tvGroup2.text = it.egg_groups[1].name
            }

            binding.tvHatchnum.text = ((it.hatch_counter + 1)*255).toString()

            binding.textView11.text = it.gender_rate.toString()

            binding.tvGrowthratetype.text = it.growth_rate.name

            binding.tvCatchrate.text = it.capture_rate.toString()

            binding.tvFrienshipnum.text = it.base_happiness.toString()

            binding.textView2.text = it.pokedex_numbers[0].entry_number.toString()

            binding.tvColorname.text = it.color.name

            binding.textView3.text = it.flavor_text_entries[0].flavor_text
        }

        viewModel.listMoves.observe(viewLifecycleOwner) {
            binding.progressBar2.isVisible = false
        }

        viewModel.msg.observe(viewLifecycleOwner) {
            if (it.isNotBlank()) {
                showSnackbar(view, it)
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().popBackStack()
            }
        }

        /*
        binding.btnCriar.setOnClickListener {
            val nomePokemon = binding.ptNomePokemon.text.toString()
            val categoria = binding.ptCategoria.text.toString()
            val hp = binding.ptHP.text.toString()
            val ataque = binding.ptAtaque.text.toString()
            val defesa = binding.ptDefesa.text.toString()

            //val pokemon = Pokemon(nomePokemon, categoria, hp.toInt(), ataque.toInt(), defesa.toInt())
            //viewModel.salvar(pokemon)
        }
         */

        binding.btnAddPokemon.setOnClickListener {
            val direction = PokemonFormFragmentDirections
                .actionPokemonFormFragmentToCreatePokemonFragment(argumentos.pokemonName)
            findNavController().navigate(direction)
        }

        return view
    }

    private fun showSnackbar(view: View, msg: String) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()
    }
}