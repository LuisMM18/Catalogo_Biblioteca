package com.example.catalogo_biblioteca

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.catalogo_biblioteca.Fragmentos.FragmentCatalogo
import com.example.catalogo_biblioteca.Fragmentos.FragmentFavoritos
import com.example.catalogo_biblioteca.Fragmentos.FragmentInicio
import com.example.catalogo_biblioteca.Fragmentos.FragmentPerfil
import com.example.catalogo_biblioteca.Fragmentos.FragmentPrestamos
import com.example.catalogo_biblioteca.Opciones_Login.OpcionesLogin
import com.example.catalogo_biblioteca.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        verFragmentInicio()

        binding.BottomNV.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.Item_inicio -> {
                    verFragmentInicio()
                    true
                }
                R.id.Item_Catalogo -> {
                    verFragmentCatalogo()
                    true
                }
                R.id.Item_Prestamos -> {
                    verFragmentPrestamos()
                    true
                }
                R.id.Item_Favoritos -> {
                    verFragmentFavoritos()
                    true
                }
                R.id.Item_Perfil -> {
                    verFragmentPerfil()
                    true
                }
                else -> false
            }
        }
    }

    private fun comprobarSesion() {
        val esInvitado = intent.getBooleanExtra("invitado", false)
        if (firebaseAuth.currentUser == null && !esInvitado) {
            startActivity(Intent(this@MainActivity, OpcionesLogin::class.java))
            finishAffinity()
        }
    }

    fun verFragmentInicio() {
        binding.ToolbarRL.visibility = android.view.View.VISIBLE
        binding.TituloRL.text = getString(R.string.Item_inicio)
        val fragment = FragmentInicio()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentInicio")
        fragmentTransition.commit()
    }

    fun verFragmentCatalogo() {
        binding.ToolbarRL.visibility = android.view.View.GONE
        val fragment = FragmentCatalogo()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentCatalogo")
        fragmentTransition.commit()
    }

    fun verFragmentPrestamos() {
        binding.ToolbarRL.visibility = android.view.View.VISIBLE
        binding.TituloRL.text = getString(R.string.Item_Prestamos)
        val fragment = FragmentPrestamos()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentPrestamos")
        fragmentTransition.commit()
    }

    fun verFragmentFavoritos() {
        binding.ToolbarRL.visibility = android.view.View.VISIBLE
        binding.TituloRL.text = getString(R.string.Item_Favoritos)
        val fragment = FragmentFavoritos()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentFavoritos")
        fragmentTransition.commit()
    }

    fun verFragmentPerfil() {
        binding.ToolbarRL.visibility = android.view.View.VISIBLE
        binding.TituloRL.text = getString(R.string.Item_Perfil)
        val fragment = FragmentPerfil()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id, fragment, "FragmentPerfil")
        fragmentTransition.commit()
    }
}