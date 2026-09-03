package com.example.catalogo_biblioteca.Opciones_Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.catalogo_biblioteca.MainActivity
import com.example.catalogo_biblioteca.databinding.ActivityOpcionesLoginBinding
import com.google.firebase.auth.FirebaseAuth

class OpcionesLogin : AppCompatActivity() {

    private lateinit var binding: ActivityOpcionesLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOpcionesLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()
        comprobarSesion()

        // Evento botón Ingresar con Email
        binding.IngresarEmail.setOnClickListener {
            startActivity(Intent(this@OpcionesLogin, Login_email::class.java))
        }

        // Evento botón Ingresar con Google
        binding.IngresarGoogle.setOnClickListener {
            // Nota: Aquí se configura la autenticación con Google mediante Firebase Credential
            Toast.makeText(this, "Autenticación con Google (Requiere SHA-1 de Firebase)", Toast.LENGTH_SHORT).show()
        }

        // Evento para navegar como invitado
        binding.ContinuarSinCuenta.setOnClickListener {
            startActivity(Intent(this@OpcionesLogin, MainActivity::class.java))
        }
    }

    // Comprueba si el usuario ya inició sesión previamente
    private fun comprobarSesion() {
        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finishAffinity()
        }
    }
}
