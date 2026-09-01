package com.example.catalogo_biblioteca

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.catalogo_biblioteca.Fragmentos.FragmentCatalogo
import com.example.catalogo_biblioteca.Fragmentos.FragmentFavoritos
import com.example.catalogo_biblioteca.Fragmentos.FragmentInicio
import com.example.catalogo_biblioteca.Fragmentos.FragmentPerfil
import com.example.catalogo_biblioteca.Fragmentos.FragmentPrestamos
import com.example.catalogo_biblioteca.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        verFragmentInicio()

        binding.BottomNV.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.Item_inicio->{
                    verFragmentInicio()
                    true
                }
                R.id.Item_Catalogo->{
                    verFragmentCatalogo()
                    true
                }
                R.id.Item_Prestamos->{
                    verFragmentPrestamos()
                    true
                }
                R.id.Item_Favoritos->{
                    verFragmentFavoritos()
                    true
                }
                R.id.Item_Perfil-> {
                    verFragmentPerfil()
                    true
                }
                else -> {
                    false
                }
            }
        }




    }

    private fun verFragmentInicio(){
        binding.TituloRL.text="Inicio"
        val fragment = FragmentInicio()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id,fragment, "FragmentInicio")
    }
    private fun verFragmentCatalogo(){
        binding.TituloRL.text="Catalogo"
        val fragment = FragmentCatalogo()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id,fragment, "FragmentCatalogo")
        fragmentTransition.commit()
    }
    private fun verFragmentPrestamos(){
        binding.TituloRL.text="Prestamos"
        val fragment = FragmentPrestamos()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id,fragment, "FragmentPrestamos")
        fragmentTransition.commit()
    }
    private fun verFragmentFavoritos(){
        binding.TituloRL.text="Favoritos"
        val fragment = FragmentFavoritos()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id,fragment, "FragmentFavoritos")
        fragmentTransition.commit()
    }
    private fun verFragmentPerfil(){
        binding.TituloRL.text="Perfil"
        val fragment = FragmentPerfil()
        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.replace(binding.Fragment1.id,fragment, "FragmentPerfil")
        fragmentTransition.commit()
    }




}