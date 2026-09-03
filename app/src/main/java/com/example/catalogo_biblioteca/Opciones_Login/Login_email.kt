package com.example.catalogo_biblioteca.Opciones_Login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.catalogo_biblioteca.MainActivity
import com.example.catalogo_biblioteca.R
import com.example.catalogo_biblioteca.databinding.ActivityLoginEmailBinding
import com.google.firebase.auth.FirebaseAuth

class Login_email : AppCompatActivity() {

    private lateinit var binding: ActivityLoginEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var email = ""
    private var password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance()

        // Configuración de diálogo de progreso (Paso 10-11 del PDF)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(getString(R.string.msg_espere_por_favor))
        progressDialog.setCanceledOnTouchOutside(false)

        // Botón para ir a Registro de nuevo usuario (Paso 22-23 del PDF)
        binding.TxtRegistrarme.setOnClickListener {
            startActivity(Intent(this@Login_email, Registro_email::class.java))
        }

        // Botón Ingresar
        binding.BtnIngresar.setOnClickListener {
            validarInfo()
        }
    }

    private fun validarInfo() {
        email = binding.EtEmail.text.toString().trim()
        password = binding.EtPassword.text.toString().trim()

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.EtEmail.error = getString(R.string.msg_email_invalido)
            binding.EtEmail.requestFocus()
        } else if (email.isEmpty()) {
            binding.EtEmail.error = getString(R.string.msg_ingrese_email)
            binding.EtEmail.requestFocus()
        } else if (password.isEmpty()) {
            binding.EtPassword.error = getString(R.string.msg_ingrese_password)
            binding.EtPassword.requestFocus()
        } else {
            iniciarSesionUsuario()
        }
    }

    private fun iniciarSesionUsuario() {
        progressDialog.setMessage(getString(R.string.msg_iniciando_sesion))
        progressDialog.show()

        // Autenticación con Firebase Auth
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Login_email, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se pudo iniciar sesión debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
