package com.example.tiendaretailsur.model

data class SesionResumen(
    val id: Long,
    val observador: String,
    val hora: String,
    val conteo: Int,
    val estado: Int
)