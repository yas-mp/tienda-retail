package com.example.tiendaretailsur.model
object UsuarioRepository {

    // Lista mutable en memoria con los 7 colaboradores
    private val usuarios = mutableListOf(
        Usuario(
            codigo = "C-01",
            nombreCompleto = "Carolina Fuentes Araya",
            rut = "15.234.871-1",
            cargo = "Jefa de tienda",
            perfil = "P-01",
            correo = "carolina.fuentes@rinconsur-ficticio.ejemplo",
            telefono = "900000011",
            passwordHash = HashUtils.generarHash("Rincon2026"),
            activo = true,
            preguntaSeguridad1 = "¿Cuál es el código de tu perfil en la tienda?",
            respuestaHash1 = HashUtils.generarHashRespuesta("P-01"),
            preguntaSeguridad2 = "¿En qué comuna o sector se ubica la tienda?",
            respuestaHash2 = HashUtils.generarHashRespuesta("Norte")
        ),
        Usuario(
            codigo = "C-02",
            nombreCompleto = "Rodrigo Saavedra Muñoz",
            rut = "13.987.442-0",
            cargo = "Subgerente comercial",
            perfil = "P-02",
            correo = "rodrigo.saavedra@rinconsur-ficticio.ejemplo",
            telefono = "900000012",
            passwordHash = HashUtils.generarHash("Rincon2026"),
            activo = true,
            preguntaSeguridad1 = "¿Cuál es tu primer apellido?",
            respuestaHash1 = HashUtils.generarHashRespuesta("Saavedra"),
            preguntaSeguridad2 = "¿Cuántos accesos tiene la tienda?",
            respuestaHash2 = HashUtils.generarHashRespuesta("4")
        ),
        Usuario(
            codigo = "C-03",
            nombreCompleto = "Daniela Pizarro Toro",
            rut = "17.654.320-5",
            cargo = "Supervisora de piso (turno mañana)",
            perfil = "P-03",
            correo = "daniela.pizarro@rinconsur-ficticio.ejemplo",
            telefono = "900000013",
            passwordHash = HashUtils.generarHash("Rincon2026"),
            activo = true,
            preguntaSeguridad1 = "¿Qué turno supervisas (mañana o tarde)?",
            respuestaHash1 = HashUtils.generarHashRespuesta("mañana"),
            preguntaSeguridad2 = "¿Cuál es tu segundo apellido?",
            respuestaHash2 = HashUtils.generarHashRespuesta("Toro")
        ),
        Usuario(
            codigo = "C-04",
            nombreCompleto = "Matías Contreras Vidal",
            rut = "16.443.019-7",
            cargo = "Supervisor de piso (turno tarde)",
            perfil = "P-03",
            correo = "matias.contreras@rinconsur-ficticio.ejemplo",
            telefono = "900000014",
            passwordHash = HashUtils.generarHash("Rincon2026"),
            activo = true,
            preguntaSeguridad1 = "¿Qué turno supervisas (mañana o tarde)?",
            respuestaHash1 = HashUtils.generarHashRespuesta("tarde"),
            preguntaSeguridad2 = "¿Cuál es tu segundo apellido?",
            respuestaHash2 = HashUtils.generarHashRespuesta("Vidal")
        ),
        Usuario(
            codigo = "C-05",
            nombreCompleto = "Tamara Rojas Espinoza",
            rut = "19.876.345-4",
            cargo = "Vendedora, Vestuario Mujer",
            perfil = "P-04",
            correo = "tamara.rojas@rinconsur-ficticio.ejemplo",
            telefono = "900000015",
            passwordHash = HashUtils.generarHash("Rincon2026"),
            activo = true,
            preguntaSeguridad1 = "¿En qué departamento trabajas?",
            respuestaHash1 = HashUtils.generarHashRespuesta("Vestuario Mujer"),
            preguntaSeguridad2 = "¿Cuál es tu segundo apellido?",
            respuestaHash2 = HashUtils.generarHashRespuesta("Espinoza")
        ),
        Usuario(
            codigo = "C-06",
            nombreCompleto = "Felipe Ibarra Soto",
            rut = "20.145.678-9",
            cargo = "Vendedor, Hogar y Deco",
            perfil = "P-04",
            correo = "felipe.ibarra@rinconsur-ficticio.ejemplo",
            telefono = "900000016",
            passwordHash = HashUtils.generarHash("Rincon2026"),
            activo = true,
            preguntaSeguridad1 = "¿En qué departamento trabajas?",
            respuestaHash1 = HashUtils.generarHashRespuesta("Hogar y Deco"),
            preguntaSeguridad2 = "¿Cuál es tu segundo apellido?",
            respuestaHash2 = HashUtils.generarHashRespuesta("Soto")
        ),
        Usuario(
            codigo = "C-07",
            nombreCompleto = "Javiera Morales Cáceres",
            rut = "18.322.904-4",
            cargo = "Cajera, Cajas Centrales",
            perfil = "P-04",
            correo = "javiera.morales@rinconsur-ficticio.ejemplo",
            telefono = "900000017",
            passwordHash = HashUtils.generarHash("Rincon2026"),
            activo = true,
            preguntaSeguridad1 = "¿En qué área trabajas?",
            respuestaHash1 = HashUtils.generarHashRespuesta("Cajas Centrales"),
            preguntaSeguridad2 = "¿Cuál es tu segundo apellido?",
            respuestaHash2 = HashUtils.generarHashRespuesta("Caceres")
        )
    )

    /**
     * Busca un usuario por su correo electrónico o por su RUT.
     */
    fun buscarPorCorreoORut(identificador: String): Usuario? {
        val entrada = identificador.trim().lowercase()
        val entradaRutLimpio = identificador.replace(".", "").replace("-", "").trim().uppercase()

        return usuarios.find { usuario ->
            val rutUsuarioLimpio = usuario.rut.replace(".", "").replace("-", "").uppercase()
            usuario.correo.lowercase() == entrada || rutUsuarioLimpio == entradaRutLimpio
        }
    }

    /**
     * Valida las credenciales de inicio de sesión comparando el hash SHA-256.
     */
    fun autenticar(identificador: String, contrasenaPlana: String): Result<Usuario> {
        val usuario = buscarPorCorreoORut(identificador)
            ?: return Result.failure(Exception("Usuario o correo no registrado"))

        if (!usuario.activo) {
            return Result.failure(Exception("Esta cuenta se encuentra desactivada"))
        }

        if (!HashUtils.verificarHash(contrasenaPlana, usuario.passwordHash)) {
            return Result.failure(Exception("Contraseña incorrecta"))
        }

        return Result.success(usuario)
    }

    /**
     * Actualiza la contraseña del usuario guardando únicamente el nuevo hash SHA-256.
     */
    fun actualizarContrasena(correo: String, nuevaContrasenaPlana: String): Boolean {
        val index = usuarios.indexOfFirst { it.correo.equals(correo, ignoreCase = true) }
        if (index == -1) return false

        val nuevoHash = HashUtils.generarHash(nuevaContrasenaPlana)
        usuarios[index] = usuarios[index].copy(passwordHash = nuevoHash)
        return true
    }

    /**
     * Permite registrar un nuevo usuario desde FormularioRegistroViewModel con contraseña hasheada.
     */
    fun registrarNuevoUsuario(
        rut: String,
        nombre: String,
        correo: String,
        telefono: String,
        contrasenaPlana: String
    ): Usuario {
        val nuevo = Usuario(
            codigo = "C-0${usuarios.size + 1}",
            nombreCompleto = nombre,
            rut = rut,
            cargo = "Colaborador observador",
            perfil = "P-04",
            correo = correo,
            telefono = telefono,
            passwordHash = HashUtils.generarHash(contrasenaPlana),
            activo = true,
            preguntaSeguridad1 = "¿Cuál es tu RUT sin puntos (con guion)?",
            respuestaHash1 = HashUtils.generarHashRespuesta(rut.replace(".", "")),
            preguntaSeguridad2 = "¿Cuáles son los últimos 4 dígitos de tu teléfono?",
            respuestaHash2 = HashUtils.generarHashRespuesta(telefono.takeLast(4))
        )
        usuarios.add(nuevo)
        return nuevo
    }
}