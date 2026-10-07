package com.example.catalogo_biblioteca.Fragmentos

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.catalogo_biblioteca.Adaptadores.AdaptadorLibro
import com.example.catalogo_biblioteca.Constantes
import com.example.catalogo_biblioteca.Modelos.Libro
import com.example.catalogo_biblioteca.R
import com.example.catalogo_biblioteca.databinding.FragmentCatalogoBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentCatalogo : Fragment() {

    private var _binding: FragmentCatalogoBinding? = null
    private val binding get() = _binding!!

    private lateinit var mContext: Context

    private var listaLibros = ArrayList<Libro>()
    private var listaFiltrada = ArrayList<Libro>()
    private lateinit var adaptadorLibro: AdaptadorLibro

    private var categoriaActual = "Todos"

    companion object {
        private const val TAG = "FragmentCatalogo"
    }

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarRecyclerView()
        configurarChipsCategorias()

        cargarLibrosFirebase()
    }

    private fun configurarRecyclerView() {
        binding.RvLibros.layoutManager = GridLayoutManager(mContext, 2)
        adaptadorLibro = AdaptadorLibro(mContext, listaFiltrada) { libro ->
            onPrestarLibro(libro)
        }
        binding.RvLibros.adapter = adaptadorLibro
    }

    private fun configurarChipsCategorias() {
        val chips = listOf(
            binding.ChipTodos to "Todos",
            binding.ChipFiccion to "Ficción",
            binding.ChipCiencia to "Ciencia",
            binding.ChipHistoria to "Historia"
        )

        for ((chipView, categoria) in chips) {
            chipView.setOnClickListener {
                seleccionarCategoria(categoria, chips.map { it.first })
            }
        }
    }

    private fun seleccionarCategoria(categoria: String, todosLosChips: List<TextView>) {
        categoriaActual = categoria

        for (chip in todosLosChips) {
            val esElSeleccionado = chip.text.toString().equals(categoria, ignoreCase = true)
            if (esElSeleccionado) {
                chip.setBackgroundResource(R.drawable.bg_chip_selected)
                chip.setTextColor(ContextCompat.getColor(mContext, R.color.white))
            } else {
                chip.setBackgroundResource(R.drawable.bg_chip_unselected)
                chip.setTextColor(ContextCompat.getColor(mContext, R.color.text_primary))
            }
        }

        filtrarPorCategoria(categoria)
    }


    private fun cargarLibrosFirebase() {
        binding.PbCargando.visibility = View.VISIBLE

        val ref = FirebaseDatabase.getInstance().getReference(Constantes.BD_LIBROS)
        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                listaLibros.clear()

                for (ds in snapshot.children) {
                    val libro = ds.getValue(Libro::class.java)
                    if (libro != null) {
                        listaLibros.add(libro)
                    }
                }

                if (_binding != null) {
                    binding.PbCargando.visibility = View.GONE
                    filtrarPorCategoria(categoriaActual)
                }

                Log.d(TAG, "Libros cargados con éxito desde Firebase: ${listaLibros.size}")
            }

            override fun onCancelled(error: DatabaseError) {
                if (_binding != null) {
                    binding.PbCargando.visibility = View.GONE
                }
                Log.e(TAG, "Error al cargar libros de Firebase: ${error.message}")
                Toast.makeText(mContext, "Error al cargar catálogo: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun filtrarPorCategoria(categoria: String) {
        listaFiltrada.clear()
        if (categoria.equals("Todos", ignoreCase = true) || categoria.isEmpty()) {
            listaFiltrada.addAll(listaLibros)
        } else {
            listaFiltrada.addAll(listaLibros.filter {
                it.categoria.equals(categoria, ignoreCase = true)
            })
        }

        adaptadorLibro.notifyDataSetChanged()

        // Mostrar u ocultar mensaje de lista vacía
        if (listaFiltrada.isEmpty()) {
            binding.TvSinLibros.visibility = View.VISIBLE
        } else {
            binding.TvSinLibros.visibility = View.GONE
        }
    }

    private fun onPrestarLibro(libro: Libro) {
        Toast.makeText(
            mContext,
            "Solicitando préstamo de: ${libro.titulo}",
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}