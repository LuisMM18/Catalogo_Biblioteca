package com.example.catalogo_biblioteca.Fragmentos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.catalogo_biblioteca.Constantes
import com.example.catalogo_biblioteca.Opciones_Login.OpcionesLogin
import com.example.catalogo_biblioteca.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FragmentPerfil : Fragment() {

    private var _binding: FragmentPerfilBinding? = null
    private val binding get() = _binding!!

    // Variables de Firebase y Contexto
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        mContext = context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPerfilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
        cargarDatosUsuario()

        // Lógica para Cerrar Sesión
        binding.BtnCerrarSesion.setOnClickListener {

            firebaseAuth.signOut()
            val intent = Intent(mContext, OpcionesLogin::class.java)
            startActivity(intent)
            activity?.finishAffinity()
        }

        // Opciones del perfil secundarias
        binding.OpcionNotificaciones.setOnClickListener {
            Toast.makeText(mContext, "No hay notificaciones pendientes", Toast.LENGTH_SHORT).show()
        }
        binding.OpcionHistorial.setOnClickListener {
            Toast.makeText(mContext, "Mostrando historial de lectura", Toast.LENGTH_SHORT).show()
        }
        binding.OpcionPreferencias.setOnClickListener {
            Toast.makeText(mContext, "Preferencias de la aplicación", Toast.LENGTH_SHORT).show()
        }
        binding.OpcionAyuda.setOnClickListener {
            Toast.makeText(mContext, "Soporte de biblioteca: soporte@biblioteca.com", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cargarDatosUsuario() {
        val currentUser = firebaseAuth.currentUser
        if (currentUser != null) {
            val email = currentUser.email ?: ""
            binding.TvEmailPerfil.text = email

            // Cargar datos en tiempo real de Firebase Realtime Database
            val ref = FirebaseDatabase.getInstance().getReference(Constantes.BD_USUARIOS)
            ref.child(currentUser.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nombres = snapshot.child("nombres").getValue(String::class.java)
                    if (!nombres.isNullOrEmpty()) {
                        binding.TvNombrePerfil.text = nombres
                    } else if (email.isNotEmpty()) {
                        binding.TvNombrePerfil.text = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    }

                    val leidos = snapshot.child("librosLeidos").getValue(Long::class.java) ?: 14
                    val activos = snapshot.child("prestamosActivos").getValue(Long::class.java) ?: 2
                    val favs = snapshot.child("favoritos").getValue(Long::class.java) ?: 8

                    binding.TvStatLeidos.text = leidos.toString()
                    binding.TvStatActivos.text = activos.toString()
                    binding.TvStatFavoritos.text = favs.toString()
                }

                override fun onCancelled(error: DatabaseError) {
                    if (email.isNotEmpty()) {
                        binding.TvNombrePerfil.text = email.substringBefore("@").replaceFirstChar { it.uppercase() }
                    }
                }
            })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}