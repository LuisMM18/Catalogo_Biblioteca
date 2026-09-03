package com.example.catalogo_biblioteca.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.catalogo_biblioteca.MainActivity
import com.example.catalogo_biblioteca.R
import com.example.catalogo_biblioteca.databinding.FragmentInicioBinding
import com.google.firebase.auth.FirebaseAuth

class FragmentInicio : Fragment() {

    private var _binding: FragmentInicioBinding? = null
    private val binding get() = _binding!!
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInicioBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser
        if (user != null && !user.email.isNullOrEmpty()) {
            val username = user.email!!.substringBefore("@").replaceFirstChar { it.uppercase() }
            binding.TvSaludoUsuario.text = "¡Hola de nuevo, $username!"
        }

        // Navegación a Préstamos
        binding.TvVerTodosPrestamos.setOnClickListener {
            (activity as? MainActivity)?.verFragmentPrestamos()
        }

        // Navegación a Catálogo
        binding.TvExplorarCatalogo.setOnClickListener {
            (activity as? MainActivity)?.verFragmentCatalogo()
        }

        // Filtros de categorías
        binding.ChipTodos.setOnClickListener {
            seleccionarChip(binding.ChipTodos)
        }
        binding.ChipFiccion.setOnClickListener {
            seleccionarChip(binding.ChipFiccion)
        }
        binding.ChipCiencia.setOnClickListener {
            seleccionarChip(binding.ChipCiencia)
        }
        binding.ChipHistoria.setOnClickListener {
            seleccionarChip(binding.ChipHistoria)
        }
    }

    private fun seleccionarChip(chipSeleccionado: View) {
        val chips = listOf(binding.ChipTodos, binding.ChipFiccion, binding.ChipCiencia, binding.ChipHistoria)
        chips.forEach { chip ->
            if (chip == chipSeleccionado) {
                chip.setBackgroundResource(R.drawable.bg_chip_selected)
                chip.setTextColor(requireContext().getColor(R.color.white))
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_unselected)
                chip.setTextColor(requireContext().getColor(R.color.text_primary))
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}