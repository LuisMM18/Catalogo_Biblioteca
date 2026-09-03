package com.example.catalogo_biblioteca

object Constantes {
    // Retorna la fecha/hora actual en milisegundos (estándar Unix)
    fun obtenerTiempoDis(): Long {
        return System.currentTimeMillis()
    }

    // Nodos para Firebase Realtime Database
    const val BD_USUARIOS = "Usuarios"
    const val BD_LIBROS = "Libros"
    const val BD_PRESTAMOS = "Prestamos"
    const val BD_FAVORITOS = "Favoritos"
}
