package com.example.catalogo_biblioteca.Opciones_Login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.catalogo_biblioteca.Constantes
import com.example.catalogo_biblioteca.MainActivity
import com.example.catalogo_biblioteca.R
import com.example.catalogo_biblioteca.databinding.ActivityRegistroEmailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class Registro_email : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroEmailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog: ProgressDialog

    private var nombres = ""
    private var email = ""
    private var password = ""
    private var r_password = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Auth (Paso 11 del PDF)
        firebaseAuth = FirebaseAuth.getInstance()

        // Configurar cuadro de diálogo (Paso 10-11 del PDF)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle(getString(R.string.msg_espere_por_favor))
        progressDialog.setCanceledOnTouchOutside(false)

        // Evento botón Registrar (Paso 13 del PDF)
        binding.BtnRegistrar.setOnClickListener {
            validarInfo()
        }

        // Evento para volver al login
        binding.TxtIniciarSesion.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    // Validar campos según condiciones del PDF (Paso 19 del PDF)
    private fun validarInfo() {
        nombres = binding.EtNombres.text.toString().trim()
        email = binding.EtEmail.text.toString().trim()
        password = binding.EtPassword.text.toString().trim()
        r_password = binding.EtRPassword.text.toString().trim()

        if (nombres.isEmpty()) {
            binding.EtNombres.error = "Ingrese su nombre"
            binding.EtNombres.requestFocus()
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.EtEmail.error = getString(R.string.msg_email_invalido)
            binding.EtEmail.requestFocus()
        } else if (email.isEmpty()) {
            binding.EtEmail.error = getString(R.string.msg_ingrese_email)
            binding.EtEmail.requestFocus()
        } else if (password.isEmpty()) {
            binding.EtPassword.error = getString(R.string.msg_ingrese_password)
            binding.EtPassword.requestFocus()
        } else if (password.length < 6) {
            binding.EtPassword.error = getString(R.string.msg_password_corto)
            binding.EtPassword.requestFocus()
        } else if (r_password.isEmpty()) {
            binding.EtRPassword.error = getString(R.string.msg_repita_password)
            binding.EtRPassword.requestFocus()
        } else if (password != r_password) {
            binding.EtRPassword.error = getString(R.string.msg_no_coinciden)
            binding.EtRPassword.requestFocus()
        } else {
            registrarUsuario()
        }
    }

    // Registro de usuario en Firebase Authentication (Paso 21-23 del PDF)
    private fun registrarUsuario() {
        progressDialog.setMessage(getString(R.string.msg_creando_cuenta))
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                llenarInfoBD()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se registró el usuario debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    // Guardado de datos del usuario en Firebase Realtime Database (Paso 30-35 del PDF)
    private fun llenarInfoBD() {
        progressDialog.setMessage(getString(R.string.msg_guardando_info))

        val tiempo = Constantes.obtenerTiempoDis()
        val emailUsuario = firebaseAuth.currentUser!!.email
        val uidUsuario = firebaseAuth.uid

        // Estructura HashMap para Firebase (Paso 31-32 del PDF)
        val hashMap = HashMap<String, Any>()
        hashMap["nombres"] = nombres
        hashMap["codigoTelefono"] = ""
        hashMap["telefono"] = ""
        hashMap["urlImagenPerfil"] = ""
        hashMap["proveedor"] = "Email"
        hashMap["tiempo"] = tiempo
        hashMap["online"] = true
        hashMap["email"] = "${emailUsuario}"
        hashMap["uid"] = "${uidUsuario}"
        hashMap["fecha_nac"] = ""

        // Campos específicos de la Biblioteca
        hashMap["librosLeidos"] = 0
        hashMap["prestamosActivos"] = 0
        hashMap["favoritos"] = 0

        /* 
         * Referencia a la Realtime Database de Firebase (Paso 33 del PDF)
         * NOTA: Si tu base de datos tiene una URL específica en Firebase Console,
         * puedes usar: FirebaseDatabase.getInstance("https://TU-DATABASE.firebaseio.com").getReference("Usuarios")
         */
        val ref = FirebaseDatabase.getInstance().getReference(Constantes.BD_USUARIOS)
        ref.child(uidUsuario!!)
            .setValue(hashMap)
            .addOnSuccessListener {
                progressDialog.dismiss()
                startActivity(Intent(this@Registro_email, MainActivity::class.java))
                finishAffinity()
            }
            .addOnFailureListener { e ->
                progressDialog.dismiss()
                Toast.makeText(
                    this,
                    "No se guardó la información debido a ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
}
