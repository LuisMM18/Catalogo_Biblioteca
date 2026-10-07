package com.example.catalogo_biblioteca.Adaptadores

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.catalogo_biblioteca.Modelos.Libro
import com.example.catalogo_biblioteca.R
import com.example.catalogo_biblioteca.databinding.ItemLibroBinding

class AdaptadorLibro(
    private val context: Context,
    private val listaLibros: ArrayList<Libro>,
    private val onPrestarClick: (Libro) -> Unit
) : RecyclerView.Adapter<AdaptadorLibro.LibroViewHolder>() {

    inner class LibroViewHolder(val binding: ItemLibroBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibroViewHolder {
        val binding = ItemLibroBinding.inflate(LayoutInflater.from(context), parent, false)
        return LibroViewHolder(binding)
    }

    override fun getItemCount(): Int = listaLibros.size

    override fun onBindViewHolder(holder: LibroViewHolder, position: Int) {
        val libro = listaLibros[position]

        holder.binding.TvTituloLibro.text = libro.titulo
        holder.binding.TvAutorLibro.text = libro.autor

        Glide.with(context)
            .load(libro.imagenUrl)
            .placeholder(R.drawable.ic_libro)
            .error(R.drawable.ic_libro)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.binding.IvPortadaLibro)

        if (libro.disponible && libro.ejemplaresDisponibles > 0) {
            holder.binding.BtnPrestar.isEnabled = true
            holder.binding.BtnPrestar.text = context.getString(R.string.btn_prestar)
            holder.binding.BtnPrestar.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.coral_light)
            holder.binding.BtnPrestar.setTextColor(
                ContextCompat.getColor(context, R.color.coral_primary)
            )
            holder.binding.BtnPrestar.setOnClickListener {
                onPrestarClick(libro)
            }
        } else {
            holder.binding.BtnPrestar.isEnabled = false
            holder.binding.BtnPrestar.text = "Agotado"
            holder.binding.BtnPrestar.backgroundTintList =
                ContextCompat.getColorStateList(context, R.color.chip_unselected)
            holder.binding.BtnPrestar.setTextColor(
                ContextCompat.getColor(context, R.color.text_secondary)
            )
            holder.binding.BtnPrestar.setOnClickListener(null)
        }
    }
}
