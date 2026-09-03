package com.example.catalogo_biblioteca.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.catalogo_biblioteca.R
import com.example.catalogo_biblioteca.databinding.FragmentPrestamosBinding

class FragmentPrestamos : Fragment() {

    private var _binding: FragmentPrestamosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPrestamosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Alternancia de tabs: Activos vs Historial
        binding.TabActivos.setOnClickListener {
            binding.TabActivos.setBackgroundResource(R.drawable.bg_chip_selected)
            binding.TabActivos.setTextColor(requireContext().getColor(R.color.white))
            binding.TabHistorial.background = null
            binding.TabHistorial.setTextColor(requireContext().getColor(R.color.text_secondary))
        }

        binding.TabHistorial.setOnClickListener {
            binding.TabHistorial.setBackgroundResource(R.drawable.bg_chip_selected)
            binding.TabHistorial.setTextColor(requireContext().getColor(R.color.white))
            binding.TabActivos.background = null
            binding.TabActivos.setTextColor(requireContext().getColor(R.color.text_secondary))
        }

        // Acciones para devolver libros
        binding.BtnDevolver1.setOnClickListener {
            Toast.makeText(requireContext(), "Libro devuelto a la biblioteca", Toast.LENGTH_SHORT).show()
        }

        binding.BtnDevolver2.setOnClickListener {
            Toast.makeText(requireContext(), "Libro devuelto a la biblioteca", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}