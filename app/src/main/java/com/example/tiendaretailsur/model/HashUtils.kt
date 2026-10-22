package com.example.tiendaretailsur.model

import java.security.MessageDigest

object HashUtils {

    /**
     * Genera un hash SHA-256 en formato hexadecimal a partir de un texto plano.
     */
    fun generarHash(textoPlano: String): String {
        val bytes = MessageDigest
            .getInstance("SHA-256")
            .digest(textoPlano.toByteArray(Charsets.UTF_8))
        return bytes.joinToString("") { "%02x".format(it) }
    }

    /**
     * Verifica si una contraseña en texto plano coincide con el hash almacenado.
     */
    fun verificarHash(textoPlano: String, hashGuardado: String): Boolean {
        return generarHash(textoPlano) == hashGuardado
    }

    /**
     * Normaliza respuestas de seguridad antes de hashear.
     */
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