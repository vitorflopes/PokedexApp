package com.pbvitorlucas.myapplication.ui.campeaoLista

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pbvitorlucas.myapplication.databinding.FragmentCampeoesListaBinding

class CampeoesListaFragment : Fragment() {

    private var _binding: FragmentCampeoesListaBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CampeoesListaViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCampeoesListaBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(CampeoesListaViewModel::class.java)

        viewModel.campeoes.observe(viewLifecycleOwner) {
            if (!it.isNullOrEmpty()) {
                binding.listViewCampeoes.setOnItemClickListener {
                    adapterView, view, i, l -> val campeaoEnd = it.get(i)
                }
                binding.listViewCampeoes.adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_list_item_1,
                    it
                )
            }
            else {
                Log.d("PokemonCampeao", "Nenhum campeão encontrado.")
            }
        }
        return view
    }
}