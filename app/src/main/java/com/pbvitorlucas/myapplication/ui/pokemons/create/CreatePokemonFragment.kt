package com.pbvitorlucas.myapplication.ui.pokemons.create

import android.os.Bundle
import android.text.InputFilter
import android.text.Spanned
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.pbvitorlucas.myapplication.R
import com.pbvitorlucas.myapplication.dao.AuthDao
import com.pbvitorlucas.myapplication.databinding.FragmentCreatePokemonBinding
import com.pbvitorlucas.myapplication.model.CreatedPokemon
import com.pbvitorlucas.myapplication.model.apiModel.Pokemon
import com.pbvitorlucas.myapplication.ui.pokemons.create.CreatePokemonViewModel
import com.squareup.picasso.Picasso

class CreatePokemonFragment : Fragment() {

    var _binding: FragmentCreatePokemonBinding? = null
    val binding get() = _binding!!
    private lateinit var viewModel: CreatePokemonViewModel
    private val argumentos by navArgs<CreatePokemonFragmentArgs>()

    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "TAGAdd"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreatePokemonBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(CreatePokemonViewModel()::class.java)

        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(requireContext(),"ca-app-pub-1754457021414641/6193044380", adRequest, object : InterstitialAdLoadCallback() {
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

        viewModel.retornaPokemon(argumentos.pokemonName, requireContext())

        viewModel.listNature.observe(viewLifecycleOwner) {
            val natArray = ArrayList<String>()

            for(n in it){
                natArray.add("${n.name} (+${n.increased_stat?.name}/-${n.decreased_stat?.name})")
            }

            val spinnernat: Spinner = binding.spinnerNat
            val natArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, natArray)
            spinnernat.adapter = natArrayAdapter
            spinnernat
                .onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val selectednature = nature(p2)
                        val atkplus = listOf("lonely","brave","adamant","naughty")
                        val defplus = listOf("bold", "relaxed", "impish", "lax")
                        val speedplus = listOf("timid", "hasty", "jolly", "naive")
                        val spatkplus = listOf("modest", "mild", "quiet", "rash")
                        val spdefplus = listOf("calm", "gentle", "sassy", "careful")
                        val atkminus = listOf("bold","timid", "modest", "calm" )
                        val defminus = listOf("lonely", "hasty", "mild", "gentle")
                        val speedminus = listOf("brave", "relaxed", "quiet", "sassy")
                        val spatkminus = listOf("adamant", "impish", "jolly", "careful")
                        val spdefminus = listOf("naughty", "lax", "naive", "rash")
                        //increasestat
                        if(atkplus.contains(selectednature)){
                            binding.tvAtknum.text = ((binding.tvAtknum.text.toString().toInt())*1.1).toInt().toString()
                        }
                        if(defplus.contains(selectednature)){
                            binding.tvDefnum.text = ((binding.tvDefnum.text.toString().toInt())*1.1).toInt().toString()
                        }
                        if(speedplus.contains(selectednature)){
                            binding.tvSpeednum.text = ((binding.tvSpeednum.text.toString().toInt())*1.1).toInt().toString()
                        }
                        if(spatkplus.contains(selectednature)){
                            binding.tvSpatknum.text = ((binding.tvSpatknum.text.toString().toInt())*1.1).toInt().toString()
                        }
                        if(spdefplus.contains(selectednature)){
                            binding.tvSpdefnum.text = ((binding.tvSpdefnum.text.toString().toInt())*1.1).toInt().toString()
                        }
                        //decreasestat
                        if(atkminus.contains(selectednature)){
                            binding.tvAtknum.text = ((binding.tvAtknum.text.toString().toInt())*0.9).toInt().toString()
                        }
                        if(defminus.contains(selectednature)){
                            binding.tvDefnum.text = ((binding.tvDefnum.text.toString().toInt())*0.9).toInt().toString()
                        }
                        if(speedminus.contains(selectednature)){
                            binding.tvSpeednum.text = ((binding.tvSpeednum.text.toString().toInt())*0.9).toInt().toString()
                        }
                        if(spatkminus.contains(selectednature)){
                            binding.tvSpatknum.text = ((binding.tvSpatknum.text.toString().toInt())*0.9).toInt().toString()
                        }
                        if(spdefminus.contains(selectednature)){
                            binding.tvSpdefnum.text = ((binding.tvSpdefnum.text.toString().toInt())*0.9).toInt().toString()
                        }
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                        TODO("Not yet implemented")
                    }
                }
        }

        viewModel.pokemon.observe(viewLifecycleOwner) {
            Picasso.get().load(it.sprites.front_default).into(binding.ivPkmn)
            binding.inputName.setText(it.name)
            binding.acSpecies.setText(it.species.name)

            binding.tvType1.text = it.types[0].type.name
            if (it.types.size > 1) {
                binding.tvType2.text = it.types[1].type.name
            }

            val listAbilit = ArrayList<String>()
            for (abilit in it.abilities) {
                listAbilit.add(abilit.ability.name)
            }
            val spinnerAbi: Spinner = binding.spinnerAbi
            val abiArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_dropdown_item, listAbilit)
            spinnerAbi.adapter = abiArrayAdapter

        }

        viewModel.listItens.observe(viewLifecycleOwner) {
            val listNameItem = arrayListOf<String>()

            for (item in it) {
                listNameItem.add(item.name)
            }

            val adapterItem : ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_dropdown_item_1line, listNameItem)

            val autoTextItem : AutoCompleteTextView = binding.acItem
            autoTextItem.setAdapter(adapterItem)
        }

        viewModel.listGames.observe(viewLifecycleOwner) {
            val listGames = ArrayList<String>()
            for (game in it) {
                listGames.add(game.name)
            }
            val spinnerGame : Spinner = binding.spinnerGame
            val gameArrayAdapter: ArrayAdapter<String> =
                ArrayAdapter<String>(requireContext(),
                    android.R.layout.simple_spinner_dropdown_item,
                    listGames)
            spinnerGame.adapter = gameArrayAdapter

            val earlygen = listOf("red-blue", "yellow", "gold-silver", "crystal")
            spinnerGame
                .onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        p0: AdapterView<*>?,
                        p1: View?,
                        p2: Int,
                        p3: Long
                    ) {
                        val versionGroup = versionGroup(p2)
                        val pokemon = viewModel.pokemon.value
                        moveBindingSetup(pokemon, versionGroup)

                        val hp = pokemon!!.stats[0].base_stat
                        val attack = pokemon.stats[1].base_stat
                        val defense = pokemon.stats[2].base_stat
                        val spAttack = pokemon.stats[3].base_stat
                        var spDefense = pokemon.stats[4].base_stat
                        var speed = pokemon.stats[5].base_stat
                        binding.inputHpev.filters = arrayOf(MinMaxFilter(0,255))
                        binding.inputAtkev.filters = arrayOf(MinMaxFilter(0,255))
                        binding.inputDefev.filters = arrayOf(MinMaxFilter(0,255))
                        binding.inputSpatkev.filters = arrayOf(MinMaxFilter(0,255))
                        binding.inputSpdefev.filters = arrayOf(MinMaxFilter(0,255))
                        binding.inputSpdev.filters = arrayOf(MinMaxFilter(0,255))

                        if(earlygen.contains(versionGroup)){
                            spDefense = spAttack
                            //hpfilter
                            binding.inputHpiv.filters = arrayOf(MinMaxFilter(0,15))
                            //atkfilter
                            binding.inputAtkiv.filters = arrayOf(MinMaxFilter(0,15))
                            //deffilter
                            binding.inputDefiv.filters = arrayOf(MinMaxFilter(0,15))
                            //spfilter
                            binding.inputSpatkiv.filters = arrayOf(MinMaxFilter(0,15))
                            binding.inputSpdefiv.filters = arrayOf(MinMaxFilter(0,15))
                            //speedfilter
                            binding.inputSpdiv.filters = arrayOf(MinMaxFilter(0,15))

                            val hpcalc = ((((((hp + hpiv().toInt())*2)+hpev().toInt())*lvl().toInt())/100)+lvl().toInt()+10)
                            val atkcalc = ((((((attack + atkiv().toInt())*2)+atkev().toInt())*lvl().toInt())/100)+5)
                            val defcalc = ((((((defense + defiv().toInt())*2)+defev().toInt())*lvl().toInt())/100)+5)
                            val spatkcalc = ((((((spAttack + spatkiv().toInt())*2)+spatkev().toInt())*lvl().toInt())/100)+5)
                            val spdefcalc = ((((((spDefense + spdefiv().toInt())*2)+spdefev().toInt())*lvl().toInt())/100)+5)
                            val speedcalc = ((((((speed + speediv().toInt())*2)+speedev().toInt())*lvl().toInt())/100)+5)
                            binding.tvHpnum.text = hpcalc.toString()
                            binding.tvAtknum.text = atkcalc.toString()
                            binding.tvDefnum.text = defcalc.toString()
                            binding.tvSpatknum.text = spatkcalc.toString()
                            binding.tvSpdefnum.text = spdefcalc.toString()
                            binding.tvSpeednum.text = speedcalc.toString()
                        }else{
                            //hpfilter
                            binding.inputHpiv.filters = arrayOf(MinMaxFilter(1,31))
                            //atkfilter
                            binding.inputAtkiv.filters = arrayOf(MinMaxFilter(1,31))
                            //deffilter
                            binding.inputDefiv.filters = arrayOf(MinMaxFilter(1,31))
                            //spfilter
                            binding.inputSpatkiv.filters = arrayOf(MinMaxFilter(1,31))
                            binding.inputSpdefiv.filters = arrayOf(MinMaxFilter(1,31))
                            //speedfilter
                            binding.inputSpdiv.filters = arrayOf(MinMaxFilter(1,31))

                            val hpcalc = ((((((2*hp)+hpiv().toInt()+(hpev().toInt()/4))*lvl().toInt()))/100)+lvl().toInt()+10)
                            binding.tvHpnum.text = hpcalc.toString()
                            val atkcalc = ((((((2*attack)+atkiv().toInt()+(atkev().toInt()/4))*lvl().toInt()))/100)+5)
                            val defcalc = (((((2*defense)+defiv().toInt()+(defev().toInt()/4))*lvl().toInt())/100)+5)
                            val spatkcalc = (((((2*spAttack)+spatkiv().toInt()+(spatkev().toInt()/4))*lvl().toInt())/100)+5)
                            val spdefcalc = (((((2*spDefense)+spdefiv().toInt()+(spdefev().toInt()/4))*lvl().toInt())/100)+5)
                            val speedcalc = ((((((2*speed)+speediv().toInt())+(speedev().toInt()/4))*lvl().toInt())/100)+5)
                            binding.tvAtknum.text = atkcalc.toString()
                            binding.tvDefnum.text = defcalc.toString()
                            binding.tvSpatknum.text = spatkcalc.toString()
                            binding.tvSpdefnum.text = spdefcalc.toString()
                            binding.tvSpeednum.text = speedcalc.toString()

                        }
                    }

                    private fun moveBindingSetup(
                        pokemon: Pokemon?,
                        versionGroup: String
                    ) {
                        val listMoves = arrayListOf<String>()
                        for (move in pokemon!!.moves) {
                            for (versionG in move.version_group_details) {
                                if (versionG.version_group.name == versionGroup) {
                                    listMoves.add(move.move.name)
                                }
                            }
                        }
                        val adapterMove: ArrayAdapter<String> =
                            ArrayAdapter<String>(
                                requireContext(),
                                android.R.layout.simple_dropdown_item_1line,
                                listMoves
                            )

                        val autoTextM1: AutoCompleteTextView = binding.acMove1
                        autoTextM1.setAdapter(adapterMove)
                        val autoTextM2: AutoCompleteTextView = binding.acMove2
                        autoTextM2.setAdapter(adapterMove)
                        val autoTextM3: AutoCompleteTextView = binding.acMove3
                        autoTextM3.setAdapter(adapterMove)
                        val autoTextM4: AutoCompleteTextView = binding.acMove4
                        autoTextM4.setAdapter(adapterMove)
                    }

                    override fun onNothingSelected(p0: AdapterView<*>?) {
                        Toast.makeText(
                            requireContext(),
                            "Selecione alguma opção.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }

        var gendername = true
        binding.ivPkmnGender.setOnClickListener {
            val genderimg = binding.ivPkmnGender
            if (gendername == true){
                gendername = false
                genderimg.setImageResource(R.drawable.ic_baseline_female_24)
            }else{
                gendername = true
                genderimg.setImageResource(R.drawable.ic_baseline_male_24)
            }
        }

        binding.btnSalvarPokemon.setOnClickListener {
            val nickname = binding.inputName.text.toString()
            val species = binding.acSpecies.text.toString()
            val lvl = binding.inputLvl.text.toString()
            val gender = true
            val ability = binding.spinnerAbi.selectedItem.toString()
            val item = binding.acItem.text.toString()

            val listIv = arrayListOf<String>()
            listIv.add(binding.inputHpiv.text.toString())
            listIv.add(binding.inputAtkiv.text.toString())
            listIv.add(binding.inputDefiv.text.toString())
            listIv.add(binding.inputSpatkiv.text.toString())
            listIv.add(binding.inputSpdefiv.text.toString())
            listIv.add(binding.inputSpdiv.text.toString())
            val iv = listIv

            val listEv = arrayListOf<String>()
            listEv.add(binding.inputHpev.text.toString())
            listEv.add(binding.inputAtkev.text.toString())
            listEv.add(binding.inputDefev.text.toString())
            listEv.add(binding.inputSpatkev.text.toString())
            listEv.add(binding.inputSpdefev.text.toString())
            listEv.add(binding.inputSpdev.text.toString())
            val ev = listEv

            val nature = binding.spinnerNat.selectedItem.toString()

            val listMoves = arrayListOf<String>()
            listMoves.add(binding.acMove1.text.toString())
            listMoves.add(binding.acMove2.text.toString())
            listMoves.add(binding.acMove3.text.toString())
            listMoves.add(binding.acMove4.text.toString())
            val moves = listMoves

            val idUsuario = AuthDao.getCurrentUser()!!.uid


            val lvl2 = lvl()
            val hpiv2 = hpiv()
            val atkiv2 = atkiv()
            val defiv2 = defiv()
            val spatkiv2 = spatkiv()
            val spdefiv2 = spdefiv()
            val speediv2 = speediv()
            val listIv2 = arrayListOf(hpiv2, atkiv2, defiv2, spatkiv2, spdefiv2, speediv2)

            val hpev2 = hpev()
            val atkev2 = atkev()
            val defev2 = defev()
            val spatkev2 = spatkev()
            val spdefev2 = spdefev()
            val speedev2 = speedev()

            val spriteFront = viewModel.pokemon.value!!.sprites.front_default.toString()
            val spriteBack = viewModel.pokemon.value!!.sprites.back_default.toString()
            val color = viewModel.species.value!!.color.name
            val id = viewModel.pokemon.value!!.id

            viewModel.retornaItem(item, requireContext())
            viewModel.item.observe(viewLifecycleOwner) {
                val spriteItem = it.sprites.default

                val pokemon =
                    CreatedPokemon(spriteFront , spriteBack, nickname, species, color, lvl2, gendername, ability, item, spriteItem, listIv2, ev, nature, moves, id, idUsuario)

                viewModel.inserirPokemon(pokemon)
            }

            if (mInterstitialAd != null) {
                mInterstitialAd?.show(requireActivity())
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }

        viewModel.status.observe(viewLifecycleOwner) {
            if (it) {
                Toast.makeText(context, "Pokemon criado!", Toast.LENGTH_LONG).show()
                findNavController().navigate(R.id.perfilFragment)
            }
        }

        return view
    }

    private fun nature(p2: Int): String {
        return viewModel.listNature.value?.get(p2)?.name!!.toString()
    }

    private fun speedev(): String {
        return if(binding.inputSpdev.text!!.isNotEmpty())
            binding.inputSpdev.text.toString()
        else "0"
    }

    private fun spdefev(): String {
        return if(binding.inputSpdefev.text!!.isNotEmpty())
            binding.inputSpdefev.text.toString()
        else "0"
    }

    private fun spatkev(): String {
        return if(binding.inputSpatkev.text!!.isNotEmpty())
            binding.inputSpatkev.text.toString()
        else "0"
    }

    private fun defev(): String {
        return if(binding.inputDefev.text!!.isNotEmpty())
            binding.inputDefev.text.toString()
        else "0"
    }

    private fun atkev(): String {
        return if(binding.inputAtkev.text!!.isNotEmpty())
            binding.inputAtkev.text.toString()
        else "0"
    }

    private fun hpev(): String {
        return if(binding.inputHpev.text!!.isNotEmpty())
            binding.inputHpev.text.toString()
        else "0"
    }

    private fun speediv(): String {
        return if(binding.inputSpdiv.text!!.isNotEmpty())
            binding.inputSpdiv.text.toString()
        else "31"
    }

    private fun spdefiv(): String {
        return if(binding.inputSpdefiv.text!!.isNotEmpty())
            binding.inputSpdefiv.text.toString()
        else "31"
    }

    private fun spatkiv(): String {
        return if(binding.inputSpatkiv.text!!.isNotEmpty())
            binding.inputSpatkiv.text.toString()
        else "31"
    }

    private fun defiv(): String {
        return if(binding.inputDefiv.text!!.isNotEmpty())
            binding.inputDefiv.text.toString()
        else "31"
    }

    private fun atkiv(): String {
        return if(binding.inputAtkiv.text!!.isNotEmpty())
            binding.inputAtkiv.text.toString()
        else "31"
    }

    private fun hpiv(): String {
        return if(binding.inputHpiv.text!!.isNotEmpty())
            binding.inputHpiv.text.toString()
        else "31"
    }

    private fun lvl(): String {
        return if(binding.inputLvl.text!!.isNotEmpty())
            binding.inputLvl.text.toString()
        else "100"
    }

    private fun versionGroup(p2: Int): String {
        val versionGroup = viewModel.listGames.value?.get(p2)?.name!!.toString()
        return versionGroup
    }

    // Custom class to define min and max for the edit text
    inner class MinMaxFilter() : InputFilter {
        private var intMin: Int = 0
        private var intMax: Int = 0

        // Initialized
        constructor(minValue: Int, maxValue: Int) : this() {
            this.intMin = minValue
            this.intMax = maxValue
        }

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dStart: Int, dEnd: Int): CharSequence? {
            try {
                val input = Integer.parseInt(dest.toString() + source.toString())
                if (isInRange(intMin, intMax, input)) {
                    return null
                }
            } catch (e: NumberFormatException) {
                e.printStackTrace()
            }
            return ""
        }

        // Check if input c is in between min a and max b and
        // returns corresponding boolean
        private fun isInRange(a: Int, b: Int, c: Int): Boolean {
            return if (b > a) c in a..b else c in b..a
        }
    }
}