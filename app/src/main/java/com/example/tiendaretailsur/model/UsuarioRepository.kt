package com.example.tiendaretailsur.model

data class ConfiguracionPerfil(
    val codigo: String,
    val nombre: String,
    val descripcion: String,
    val habilitado: Boolean = true,
    val permisosActivos: List<String> = emptyList()
)

data class ConfiguracionAcceso(
    val codigo: String,
    val nombre: String,
    val ubicacion: String,
    val habilitado: Boolean = true,
    val modoOfflinePrioritario: Boolean = false
)

object UsuarioRepository {

    // Configuración de los 4 Perfiles
    private val perfilesApp = mutableListOf(
        ConfiguracionPerfil(
            codigo = "P-01",
            nombre = "Jefa de tienda",
            descripcion = "Revisa indicadores, decide acciones comerciales y reporta a gerencia zonal.",
            habilitado = true,
            permisosActivos = listOf(
                "Consultar indicadores y perfil de tránsito",
                "Consultar colaboradores",
                "Registrar meta mensual de conversión"
            )
        ),
        ConfiguracionPerfil(
            codigo = "P-02",
            nombre = "Subgerente comercial",
            descripcion = "Incorpora conteos y boletas, programa sesiones y gestiona colaboradores.",
            habilitado = true,
            permisosActivos = listOf(
                "Programar o cancelar sesiones",
                "Validar o rechazar sesiones",
                "Incorporar o corregir ingresos y transacciones",
                "Registrar eventos del día",
                "Consultar indicadores y perfil de tránsito",
                "Gestionar colaboradores y perfiles",
                "Registrar meta mensual de conversión"
            )
        ),
        ConfiguracionPerfil(
            codigo = "P-03",
            nombre = "Supervisor/a de piso",
            descripcion = "Coordina vendedores, asigna y valida sesiones de observación y registra eventos.",
            habilitado = true,
            permisosActivos = listOf(
                "Registrar y anular en sesión propia",
                "Programar o cancelar sesiones",
                "Validar o rechazar sesiones (no propias)",
                "Anular registros de sesiones finalizadas",
                "Registrar eventos del día",
                "Consultar indicadores y perfil de tránsito"
            )
        ),
        ConfiguracionPerfil(
            codigo = "P-04",
            nombre = "Colaborador observador",
            descripcion = "Se ubica en el acceso (30 a 60 min) y registra el flujo de personas.",
            habilitado = true,
            permisosActivos = listOf(
                "Registrar y anular en sesión propia en curso",
                "Consultar solo sus propias sesiones"
            )
        )
    )

    // Configuración de los 4 Accesos
    private val accesosTienda = mutableListOf(
        ConfiguracionAcceso("A1", "Acceso Principal", "Frente al pasillo central del mall", true, false),
        ConfiguracionAcceso("A2", "Acceso Lateral", "Costado del sector patio de comidas", true, false),
        ConfiguracionAcceso("A3", "Acceso Estacionamiento", "Conecta con estacionamiento subterráneo (señal débil)", true, true),
        ConfiguracionAcceso("A4", "Acceso Conexión", "Comunica con tienda vecina de mejoramiento del hogar", true, false)
    )

    // Meta mensual de conversión
    private var metaConversionMensual: Double = 10.0

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

    fun obtenerTodosLosUsuarios(): List<Usuario> = usuarios.toList()

    fun obtenerPerfiles(): List<ConfiguracionPerfil> = perfilesApp.toList()

    fun obtenerAccesos(): List<ConfiguracionAcceso> = accesosTienda.toList()

    fun obtenerMetaConversion(): Double = metaConversionMensual

    fun actualizarMetaConversion(nuevaMeta: Double) {
        metaConversionMensual = nuevaMeta.coerceIn(0.1, 100.0)
    }

    fun buscarPorCorreoORut(identificador: String): Usuario? {
        val entrada = identificador.trim().lowercase()
        val entradaRutLimpio = identificador.replace(".", "").replace("-", "").trim().uppercase()

        return usuarios.find { usuario ->
            val rutUsuarioLimpio = usuario.rut.replace(".", "").replace("-", "").uppercase()
            usuario.correo.lowercase() == entrada || rutUsuarioLimpio == entradaRutLimpio
        }
    }

    fun autenticar(identificador: String, contrasenaPlana: String): Result<Usuario> {
        val usuario = buscarPorCorreoORut(identificador)
            ?: return Result.failure(Exception("Usuario o correo no registrado"))

        if (!usuario.activo) {
            return Result.failure(Exception("Esta cuenta de colaborador se encuentra deshabilitada"))
        }

        val perfilConfig = perfilesApp.find { it.codigo == usuario.perfil }
        if (perfilConfig != null && !perfilConfig.habilitado) {
            return Result.failure(Exception("El perfil ${usuario.perfil} (${perfilConfig.nombre}) está deshabilitado por el administrador"))
        }

        if (!HashUtils.verificarHash(contrasenaPlana, usuario.passwordHash)) {
            return Result.failure(Exception("Contraseña incorrecta"))
        }

        return Result.success(usuario)
    }

    fun actualizarContrasena(correo: String, nuevaContrasenaPlana: String): Boolean {
        val index = usuarios.indexOfFirst { it.correo.equals(correo, ignoreCase = true) }
        if (index == -1) return false

        val nuevoHash = HashUtils.generarHash(nuevaContrasenaPlana)
        usuarios[index] = usuarios[index].copy(passwordHash = nuevoHash)
        return true
    }

    fun cambiarEstadoUsuario(codigoUsuario: String, activo: Boolean) {
        val index = usuarios.indexOfFirst { it.codigo == codigoUsuario }
        if (index != -1) {
            usuarios[index] = usuarios[index].copy(activo = activo)
        }
    }

    fun actualizarDatosUsuario(
        codigoOriginal: String,
        nuevoNombre: String,
        nuevoRut: String,
        nuevoCorreo: String,
        nuevoTelefono: String,
        nuevoCargo: String,
        nuevoPerfil: String,
        activo: Boolean,
        nuevaContrasenaOpcional: String? = null
    ): Boolean {
        val index = usuarios.indexOfFirst { it.codigo == codigoOriginal }
        if (index == -1) return false

        val actual = usuarios[index]
        val hashActualizado = if (!nuevaContrasenaOpcional.isNullOrBlank()) {
            HashUtils.generarHash(nuevaContrasenaOpcional)
        } else {
            actual.passwordHash
        }

        usuarios[index] = actual.copy(
            nombreCompleto = nuevoNombre.trim(),
            rut = nuevoRut.trim(),
            correo = nuevoCorreo.trim(),
            telefono = nuevoTelefono.trim(),
            cargo = nuevoCargo.trim(),
            perfil = nuevoPerfil,
            activo = activo,
            passwordHash = hashActualizado
        )
        return true
    }

    fun cambiarEstadoPerfil(codigoPerfil: String, habilitado: Boolean) {
        val index = perfilesApp.indexOfFirst { it.codigo == codigoPerfil }
        if (index != -1) {
            perfilesApp[index] = perfilesApp[index].copy(habilitado = habilitado)
        }
    }

    fun actualizarPerfilConfig(
        codigoPerfil: String,
        nuevoNombre: String,
        nuevaDescripcion: String,
        habilitado: Boolean,
        aplicarEstadoAUsuariosDelPerfil: Boolean = false
    ) {
        val index = perfilesApp.indexOfFirst { it.codigo == codigoPerfil }
        if (index != -1) {
            perfilesApp[index] = perfilesApp[index].copy(
                nombre = nuevoNombre.trim(),
                descripcion = nuevaDescripcion.trim(),
                habilitado = habilitado
            )
        }
        if (aplicarEstadoAUsuariosDelPerfil) {
            for (i in usuarios.indices) {
                if (usuarios[i].perfil == codigoPerfil) {
                    usuarios[i] = usuarios[i].copy(activo = habilitado)
                }
            }
        }
    }

    fun cambiarEstadoAcceso(codigoAcceso: String, habilitado: Boolean) {
        val index = accesosTienda.indexOfFirst { it.codigo == codigoAcceso }
        if (index != -1) {
            accesosTienda[index] = accesosTienda[index].copy(habilitado = habilitado)
        }
    }

    fun registrarNuevoUsuario(
        rut: String,
        nombre: String,
        correo: String,
        telefono: String,
        contrasenaPlana: String,
        perfil: String = "P-04",
        cargo: String = "Colaborador observador"
    ): Usuario {
        val nuevo = Usuario(
            codigo = "C-0${usuarios.size + 1}",
            nombreCompleto = nombre,
            rut = rut,
            cargo = cargo,
            perfil = perfil,
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