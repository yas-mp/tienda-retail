package com.example.tiendaretailsur.model

import java.security.MessageDigest

object HashUtils {
    fun generarHash(textoPlano: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(textoPlano.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun verificarHash(textoPlano: String, hashGuardado: String): Boolean {
        return generarHash(textoPlano) == hashGuardado
    }

    fun generarHashRespuesta(respuesta: String): String {
        val limpia = respuesta
            .trim()
            .lowercase()
            .replace("á", "a")
            .replace("é", "e")
            .replace("í", "i")
            .replace("ó", "o")
            .replace("ú", "u")
        return generarHash(limpia)
    }
}