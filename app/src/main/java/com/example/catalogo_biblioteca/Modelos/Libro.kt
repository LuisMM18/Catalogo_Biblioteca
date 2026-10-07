package com.example.catalogo_biblioteca.Modelos

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
data class Libro(
    var id: String = "",
    var titulo: String = "",
    var autor: String = "",
    var categoria: String = "",
    var descripcion: String = "",
    var imagenUrl: String = "",
    var ejemplaresTotales: Int = 0,
    var ejemplaresDisponibles: Int = 0,
    var disponible: Boolean = true,
    var tiempoRegistro: Long = 0
)
