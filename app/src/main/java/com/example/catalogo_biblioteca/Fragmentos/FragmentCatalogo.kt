package com.example.catalogo_biblioteca.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.catalogo_biblioteca.R
import com.example.catalogo_biblioteca.databinding.FragmentCatalogoBinding

class FragmentCatalogo : Fragment() {

    private var _binding: FragmentCatalogoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Eventos de botones Prestar
        val botonesPrestar = listOf(
            binding.BtnPrestar1,
            binding.BtnPrestar2,
            binding.BtnPrestar3,
            binding.BtnPrestar4
        )

        botonesPrestar.forEach { boton ->
            boton.setOnClickListener {
                Toast.makeText(
                    requireContext(),
                    "Solicitud de préstamo registrada con éxito",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Eventos de Chips
        binding.ChipCatTodos.setOnClickListener { seleccionarChip(binding.ChipCatTodos) }
        binding.ChipCatFiccion.setOnClickListener { seleccionarChip(binding.ChipCatFiccion) }
        binding.ChipCatCiencia.setOnClickListener { seleccionarChip(binding.ChipCatCiencia) }
        binding.ChipCatHistoria.setOnClickListener { seleccionarChip(binding.ChipCatHistoria) }
    }

    private fun seleccionarChip(chipSeleccionado: View) {
        val chips = listOf(
            binding.ChipCatTodos,
            binding.ChipCatFiccion,
            binding.ChipCatCiencia,
            binding.ChipCatHistoria
        )
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