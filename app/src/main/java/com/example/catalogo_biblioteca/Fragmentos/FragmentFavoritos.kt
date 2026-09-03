package com.example.catalogo_biblioteca.Fragmentos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.catalogo_biblioteca.R
import com.example.catalogo_biblioteca.databinding.FragmentFavoritosBinding

class FragmentFavoritos : Fragment() {

    private var _binding: FragmentFavoritosBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritosBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Eventos para alternar favoritos
        configurarBotonFavorito(binding.BtnFav1)
        configurarBotonFavorito(binding.BtnFav2)
        configurarBotonFavorito(binding.BtnFav3)
        configurarBotonFav4(binding.BtnFav4)
    }

    private fun configurarBotonFavorito(imageView: ImageView) {
        var esFavorito = true
        imageView.setOnClickListener {
            esFavorito = !esFavorito
            if (esFavorito) {
                imageView.setImageResource(R.drawable.ic_corazon_lleno)
                Toast.makeText(requireContext(), "Agregado a favoritos", Toast.LENGTH_SHORT).show()
            } else {
                imageView.setImageResource(R.drawable.ic_corazon)
                Toast.makeText(requireContext(), "Eliminado de favoritos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun configurarBotonFav4(imageView: ImageView) {
        configurarBotonFavorito(imageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}