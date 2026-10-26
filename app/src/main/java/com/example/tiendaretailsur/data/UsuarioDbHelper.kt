package com.example.tiendaretailsur.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tiendaretailsur.model.AccesoTienda
import com.example.tiendaretailsur.model.ConfiguracionPerfil
import com.example.tiendaretailsur.model.DatoGrafico
import com.example.tiendaretailsur.model.FormularioConteoUiState
import com.example.tiendaretailsur.model.FormularioRegistroUiState
import com.example.tiendaretailsur.model.HashUtils
import com.example.tiendaretailsur.model.RolUsuario
import com.example.tiendaretailsur.model.Usuario

class UsuarioDbHelper(context: Context) :
    SQLiteOpenHelper(context, NOMBRE_BD, null, VERSION_DB) {

    companion object {
        private const val NOMBRE_BD = "tiendaretailsur.db"
        private const val VERSION_DB = 4

        private const val TABLA_ROLES = "roles"
        private const val ID_ROL = "id_rol"
        private const val CODIGO_ROL = "codigo_rol"
        private const val NOMBRE_ROL = "nombre_rol"
        private const val DESCRIPCION_ROL = "descripcion_rol"
        private const val HABILITADO_ROL = "habilitado_rol"

        private const val TABLA_USUARIO = "usuario"
        private const val ID_USUARIO = "id_usuario"
        private const val RUT_USUARIO = "rut_usuario"
        private const val NOMBRE_USUARIO = "nombre_usuario"
        private const val CORREO_USUARIO = "correo_usuario"
        private const val TELEFONO = "telefono"
        private const val CONTRASENA = "contrasena"
        private const val CARGO_USUARIO = "cargo"
        private const val ACTIVO_USUARIO = "activo"
        private const val ROL_USUARIO = "id_rol"
        private const val PREGUNTA_SEG = "pregunta_seguridad"
        private const val RESPUESTA_SEG = "respuesta_seguridad"
        private const val AVATAR_USUARIO = "avatar"
        private const val TURNO_USUARIO = "turno"

        private const val TABLA_PUERTAS = "puertas"
        private const val ID_PUERTA = "id_puerta"
        private const val NOMBRE_PUERTA = "nombre_puerta"
        private const val DESCRIPCION_PUERTA = "descripcion_puerta"
        private const val HABILITADA_PUERTA = "habilitada"

        private const val TABLA_CONTEO = "formulario_conteo"
        private const val ID_CONTEO = "id_conteo"
        private const val PUERTA_ACCESO = "puerta_acceso"
        private const val CANT_TRANSITO = "cantidad_transito"
        private const val CANT_CLIENTES = "cantidad_clientes"
        private const val RANGO_ETARIO = "rango_etario"
        private const val COMPANIA = "compania"
        private const val OBSERVADOR = "observador"
        private const val HORA = "hora"
        private const val ID_USUARIO_FK = "id_usuario"

        private const val SQL_CREAR_ROLES =
            "CREATE TABLE $TABLA_ROLES (" +
                    "$ID_ROL INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$NOMBRE_ROL TEXT NOT NULL UNIQUE, " +
                    "$CODIGO_ROL TEXT NOT NULL UNIQUE, " +
                    "$DESCRIPCION_ROL TEXT NOT NULL, " +
                    "$HABILITADO_ROL INTEGER NOT NULL DEFAULT 1)"

        private const val SQL_CREAR_PUERTAS =
            "CREATE TABLE $TABLA_PUERTAS (" +
                    "$ID_PUERTA TEXT PRIMARY KEY, " +
                    "$NOMBRE_PUERTA TEXT NOT NULL, " +
                    "$DESCRIPCION_PUERTA TEXT NOT NULL, " +
                    "$HABILITADA_PUERTA INTEGER NOT NULL DEFAULT 1)"

        private const val SQL_CREAR_USUARIOS =
            "CREATE TABLE $TABLA_USUARIO (" +
                    "$ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$RUT_USUARIO TEXT NOT NULL UNIQUE, " +
                    "$NOMBRE_USUARIO TEXT NOT NULL, " +
                    "$CORREO_USUARIO TEXT NOT NULL, " +
                    "$TELEFONO TEXT DEFAULT '900000000', " +
                    "$CARGO_USUARIO TEXT DEFAULT 'Colaborador', " +
                    "$ACTIVO_USUARIO INTEGER NOT NULL DEFAULT 1, " +
                    "$CONTRASENA TEXT NOT NULL, " +
                    "$PREGUNTA_SEG TEXT, " +
                    "$RESPUESTA_SEG TEXT, " +
                    "$AVATAR_USUARIO BLOB, " +
                    "$TURNO_USUARIO TEXT DEFAULT 'Turno Diurno', " +
                    "$ROL_USUARIO INTEGER NOT NULL REFERENCES $TABLA_ROLES($ID_ROL))"

        private const val SQL_CREAR_CONTEOS =
            "CREATE TABLE $TABLA_CONTEO (" +
                    "$ID_CONTEO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$PUERTA_ACCESO TEXT NOT NULL, " +
                    "$CANT_TRANSITO INTEGER NOT NULL, " +
                    "$CANT_CLIENTES INTEGER NOT NULL, " +
                    "$RANGO_ETARIO TEXT NOT NULL, " +
                    "$COMPANIA TEXT NOT NULL, " +
                    "$OBSERVADOR TEXT NOT NULL, " +
                    "$HORA TEXT NOT NULL, " +
                    "$ID_USUARIO_FK INTEGER REFERENCES $TABLA_USUARIO($ID_USUARIO))"

        private const val SQL_PRECARGAR_ROLES =
            "INSERT INTO $TABLA_ROLES ($ID_ROL, $CODIGO_ROL, $NOMBRE_ROL, $DESCRIPCION_ROL, $HABILITADO_ROL) VALUES " +
                    "(1, 'P-02', 'SUBGERENTE', 'Encargado de los reportes, conteos y gestión comercial', 1), " +
                    "(2, 'P-04', 'OBSERVADOR', 'Toma datos de conteo de flujo en terreno', 1), " +
                    "(3, 'P-01', 'JEFA_TIENDA', 'Revisa indicadores y decide acciones comerciales', 1), " +
                    "(4, 'P-03', 'SUPERVISOR_PISO', 'Coordina vendedores, programa y valida sesiones', 1)"

        private const val SQL_PRECARGAR_PUERTAS =
            "INSERT INTO $TABLA_PUERTAS ($ID_PUERTA, $NOMBRE_PUERTA, $DESCRIPCION_PUERTA, $HABILITADA_PUERTA) VALUES " +
                    "('A1', 'Acceso Principal', 'Patio de comidas del mall / Pasillo central', 1), " +
                    "('A2', 'Acceso Lateral', 'Pasillo del mall, lado norte', 1), " +
                    "('A3', 'Acceso Estacionamiento', 'Estacionamiento subterráneo (señal de red débil)', 1), " +
                    "('A4', 'Acceso Conexión', 'Conexión directa con tienda vecina de mejoramiento del hogar', 1)"

        private const val SQL_PRECARGAR_CONTEOS =
            "INSERT INTO $TABLA_CONTEO (" +
                    "$PUERTA_ACCESO, $CANT_TRANSITO, $CANT_CLIENTES, $RANGO_ETARIO, $COMPANIA, $OBSERVADOR, $HORA) VALUES " +
                    "('A1', 45, 12, '26–40', 'Solo', 'Juan Pérez', '10h'), " +
                    "('A1', 65, 28, '18–25', 'Pareja', 'Juan Pérez', '12h'), " +
                    "('A2', 80, 35, '26–40', 'Familia', 'Camila Soto', '14h'), " +
                    "('A3', 50, 18, '41–60', 'Solo', 'Mateo Silva', '16h'), " +
                    "('A1', 95, 42, '26–40', 'Pareja', 'Valentina Rojas', '18h')"
    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREAR_ROLES)
        db.execSQL(SQL_CREAR_USUARIOS)
        db.execSQL(SQL_CREAR_PUERTAS)
        db.execSQL(SQL_CREAR_CONTEOS)

        db.execSQL(SQL_PRECARGAR_ROLES)
        db.execSQL(SQL_PRECARGAR_PUERTAS)
        precargarUsuariosConHash(db)
        db.execSQL(SQL_PRECARGAR_CONTEOS)
    }

    private fun precargarUsuariosConHash(db: SQLiteDatabase) {
        val hash1234 = HashUtils.generarHash("1234")
        val hashRincon = HashUtils.generarHash("Rincon2026")
        val respPerro = HashUtils.generarHashRespuesta("perro")
        val respGato = HashUtils.generarHashRespuesta("gato")

        val usuariosIniciales = listOf(
            // Usuarios de prueba del equipo (clave: 1234)
            Triple(
                arrayOf("12.345.678-9", "Gabriel Gómez", "admin@rincondelsur.cl", "911111111", "Subgerente comercial", "Turno Diurno"),
                arrayOf(hash1234, "¿Cuál es el nombre de tu primera mascota?", respPerro),
                1
            ),
            Triple(
                arrayOf("9.876.543-1", "Juan Pérez", "juan@rincondelsur.cl", "922222222", "Observador de terreno", "Turno Diurno"),
                arrayOf(hash1234, "¿Cuál es el nombre de tu primera mascota?", respGato),
                2
            ),
            Triple(
                arrayOf("18.111.222-3", "Camila Soto", "c.soto@rincondelsur.cl", "933333333", "Observadora de terreno", "Turno Vespertino"),
                arrayOf(hash1234, "¿Cuál es el nombre de tu primera mascota?", respPerro),
                2
            ),
            Triple(
                arrayOf("19.222.333-4", "Mateo Silva", "m.silva@rincondelsur.cl", "944444444", "Observador de terreno", "Turno Diurno"),
                arrayOf(hash1234, "¿Cuál es el nombre de tu primera mascota?", respGato),
                2
            ),
            Triple(
                arrayOf("17.333.444-5", "Valentina Rojas", "v.rojas@rincondelsur.cl", "955555555", "Observadora de terreno", "Turno Vespertino"),
                arrayOf(hash1234, "¿Cuál es el nombre de tu primera mascota?", respPerro),
                2
            ),
            // Colaboradores oficiales (clave: Rincon2026)
            Triple(
                arrayOf("15.234.871-1", "Carolina Fuentes Araya", "carolina.fuentes@rinconsur-ficticio.ejemplo", "900000011", "Jefa de tienda", "Turno Diurno"),
                arrayOf(hashRincon, "¿Cuál es el código de tu perfil en la tienda?", HashUtils.generarHashRespuesta("P-01")),
                3
            ),
            Triple(
                arrayOf("13.987.442-0", "Rodrigo Saavedra Muñoz", "rodrigo.saavedra@rinconsur-ficticio.ejemplo", "900000012", "Subgerente comercial", "Turno Diurno"),
                arrayOf(hashRincon, "¿Cuál es tu primer apellido?", HashUtils.generarHashRespuesta("Saavedra")),
                1
            ),
            Triple(
                arrayOf("17.654.320-5", "Daniela Pizarro Toro", "daniela.pizarro@rinconsur-ficticio.ejemplo", "900000013", "Supervisora de piso (mañana)", "Turno Mañana"),
                arrayOf(hashRincon, "¿Qué turno supervisas (mañana o tarde)?", HashUtils.generarHashRespuesta("mañana")),
                4
            ),
            Triple(
                arrayOf("19.876.345-4", "Tamara Rojas Espinoza", "tamara.rojas@rinconsur-ficticio.ejemplo", "900000015", "Vendedora, Vestuario Mujer", "Turno Diurno"),
                arrayOf(hashRincon, "¿En qué departamento trabajas?", HashUtils.generarHashRespuesta("Vestuario Mujer")),
                2
            )
        )

        usuariosIniciales.forEach { (datos, seguridad, idRol) ->
            val valores = ContentValues().apply {
                put(RUT_USUARIO, datos[0])
                put(NOMBRE_USUARIO, datos[1])
                put(CORREO_USUARIO, datos[2])
                put(TELEFONO, datos[3])
                put(CARGO_USUARIO, datos[4])
                put(TURNO_USUARIO, datos[5])
                put(CONTRASENA, seguridad[0])
                put(PREGUNTA_SEG, seguridad[1])
                put(RESPUESTA_SEG, seguridad[2])
                put(ACTIVO_USUARIO, 1)
                put(ROL_USUARIO, idRol)
            }
            db.insert(TABLA_USUARIO, null, valores)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLA_CONTEO")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_USUARIO")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_PUERTAS")
        db.execSQL("DROP TABLE IF EXISTS $TABLA_ROLES")
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    private fun normalizarRut(texto: String): String {
        return texto.replace(".", "").replace("-", "").trim().uppercase()
    }

    fun listarPuertas(): List<AccesoTienda> {
        val lista = mutableListOf<AccesoTienda>()
        val sql = "SELECT * FROM $TABLA_PUERTAS ORDER BY $ID_PUERTA ASC"
        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                lista.add(
                    AccesoTienda(
                        id = cursor.getString(cursor.getColumnIndexOrThrow(ID_PUERTA)),
                        nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_PUERTA)),
                        descripcion = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION_PUERTA))
                    )
                )
            }
        }
        return lista
    }

    fun listarPerfiles(): List<ConfiguracionPerfil> {
        val lista = mutableListOf<ConfiguracionPerfil>()
        val sql = "SELECT * FROM $TABLA_ROLES ORDER BY $CODIGO_ROL ASC"
        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                val codigo = cursor.getString(cursor.getColumnIndexOrThrow(CODIGO_ROL))
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_ROL))
                val descripcion = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPCION_ROL))
                val habilitado = cursor.getInt(cursor.getColumnIndexOrThrow(HABILITADO_ROL)) == 1

                val permisosPorRol = when (codigo) {
                    "P-01" -> listOf("Consultar indicadores y perfil de tránsito", "Registrar meta mensual de conversión")
                    "P-02" -> listOf("Gestionar colaboradores y perfiles", "Programar y validar sesiones", "Consultar indicadores")
                    "P-03" -> listOf("Programar y validar sesiones de piso", "Registrar eventos del día")
                    else -> listOf("Registrar conteos en sesión propia en curso", "Consultar sus propias sesiones")
                }

                lista.add(
                    ConfiguracionPerfil(
                        codigo = codigo,
                        nombre = nombre,
                        descripcion = descripcion,
                        habilitado = habilitado,
                        permisosActivos = permisosPorRol
                    )
                )
            }
        }
        return lista
    }

    fun listarUsuarios(): List<Usuario> {
        val lista = mutableListOf<Usuario>()
        val sql = "SELECT u.*, r.$CODIGO_ROL AS $CODIGO_ROL, r.$NOMBRE_ROL AS $NOMBRE_ROL " +
                "FROM $TABLA_USUARIO u " +
                "INNER JOIN $TABLA_ROLES r ON u.$ROL_USUARIO = r.$ID_ROL " +
                "ORDER BY u.$ID_USUARIO ASC"

        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                lista.add(leerUsuario(cursor))
            }
        }
        return lista
    }

    fun actualizarAvatar(id: Long, avatar: ByteArray): Int {
        val valores = ContentValues().apply { put(AVATAR_USUARIO, avatar) }
        return writableDatabase.update(
            TABLA_USUARIO,
            valores,
            "$ID_USUARIO = ?",
            arrayOf(id.toString())
        )
    }

    fun registroUsuarioFormularioRegistro(uiState: FormularioRegistroUiState): Long {
        val db = writableDatabase
        val hashContrasena = HashUtils.generarHash(uiState.contrasena.trim())
        val hashRespuesta = HashUtils.generarHashRespuesta(uiState.respuestaSeguridad)

        val valores = ContentValues().apply {
            put(RUT_USUARIO, uiState.rut.trim())
            put(NOMBRE_USUARIO, uiState.nombre.trim())
            put(CORREO_USUARIO, uiState.correo.trim())
            put(TELEFONO, uiState.telefono.trim())
            put(CARGO_USUARIO, uiState.rolSeleccionado.descripcion.trim())
            put(ACTIVO_USUARIO, 1)
            put(CONTRASENA, hashContrasena)
            put(ROL_USUARIO, uiState.rolSeleccionado.id)
            put(PREGUNTA_SEG, uiState.preguntaSeleccionada)
            put(RESPUESTA_SEG, hashRespuesta)
            put(TURNO_USUARIO, "Turno Diurno")
        }
        return db.insert(TABLA_USUARIO, null, valores)
    }

    private fun buscarUsuarioPorRutOCorreo(rutOCorreo: String): Usuario? {
        val entrada = rutOCorreo.trim()
        val rutLimpio = normalizarRut(entrada)

        return listarUsuarios().find { u ->
            u.correo.equals(entrada, ignoreCase = true) ||
                    normalizarRut(u.rut) == rutLimpio
        }
    }

    fun obtenerPreguntaSeguridad(rutOCorreo: String): String? {
        val usuario = buscarUsuarioPorRutOCorreo(rutOCorreo) ?: return null
        return usuario.preguntaSeguridad1.takeIf { it.isNotBlank() }
    }

    fun verificarRespuesta(rutOCorreo: String, respuestaIngresada: String): Boolean {
        val usuario = buscarUsuarioPorRutOCorreo(rutOCorreo) ?: return false
        val hashIngresado = HashUtils.generarHashRespuesta(respuestaIngresada)
        return usuario.respuestaHash1 == hashIngresado ||
                usuario.respuestaHash1.equals(respuestaIngresada.trim(), ignoreCase = true)
    }

    fun actualizarContrasena(rutOCorreo: String, nuevaContrasena: String): Int {
        val usuario = buscarUsuarioPorRutOCorreo(rutOCorreo) ?: return 0
        val nuevoHash = HashUtils.generarHash(nuevaContrasena.trim())

        val valores = ContentValues().apply {
            put(CONTRASENA, nuevoHash)
        }
        return writableDatabase.update(
            TABLA_USUARIO,
            valores,
            "$RUT_USUARIO = ? OR $CORREO_USUARIO = ?",
            arrayOf(usuario.rut, usuario.correo)
        )
    }

    fun cambiarEstadoUsuario(rutUsuario: String, activo: Boolean): Int {
        val valores = ContentValues().apply {
            put(ACTIVO_USUARIO, if (activo) 1 else 0)
        }
        return writableDatabase.update(
            TABLA_USUARIO,
            valores,
            "$RUT_USUARIO = ?",
            arrayOf(rutUsuario)
        )
    }

    fun actualizarDatosUsuario(
        rutOriginal: String,
        nuevoNombre: String,
        nuevoRut: String,
        nuevoCorreo: String,
        nuevoTelefono: String,
        nuevoCargo: String,
        nuevoCodigoPerfil: String,
        activo: Boolean,
        nuevaContrasenaOpcional: String? = null
    ): Int {
        val idRol = when (nuevoCodigoPerfil) {
            "P-02", "SUBGERENTE" -> 1L
            "P-04", "OBSERVADOR" -> 2L
            "P-01", "JEFA_TIENDA" -> 3L
            "P-03", "SUPERVISOR_PISO" -> 4L
            else -> 2L
        }

        val valores = ContentValues().apply {
            put(NOMBRE_USUARIO, nuevoNombre.trim())
            put(RUT_USUARIO, nuevoRut.trim())
            put(CORREO_USUARIO, nuevoCorreo.trim())
            put(TELEFONO, nuevoTelefono.trim())
            put(CARGO_USUARIO, nuevoCargo.trim())
            put(ROL_USUARIO, idRol)
            put(ACTIVO_USUARIO, if (activo) 1 else 0)
            if (!nuevaContrasenaOpcional.isNullOrBlank()) {
                put(CONTRASENA, HashUtils.generarHash(nuevaContrasenaOpcional.trim()))
            }
        }

        return writableDatabase.update(
            TABLA_USUARIO,
            valores,
            "$RUT_USUARIO = ?",
            arrayOf(rutOriginal)
        )
    }

    fun cambiarEstadoPerfil(codigoPerfil: String, habilitado: Boolean): Int {
        val valores = ContentValues().apply {
            put(HABILITADO_ROL, if (habilitado) 1 else 0)
        }
        return writableDatabase.update(
            TABLA_ROLES,
            valores,
            "$CODIGO_ROL = ?",
            arrayOf(codigoPerfil)
        )
    }

    fun actualizarPerfilConfig(
        codigoPerfil: String,
        nuevoNombre: String,
        nuevaDescripcion: String,
        habilitado: Boolean,
        aplicarAUsuarios: Boolean
    ) {
        val valores = ContentValues().apply {
            put(NOMBRE_ROL, nuevoNombre.trim())
            put(DESCRIPCION_ROL, nuevaDescripcion.trim())
            put(HABILITADO_ROL, if (habilitado) 1 else 0)
        }
        writableDatabase.update(
            TABLA_ROLES,
            valores,
            "$CODIGO_ROL = ?",
            arrayOf(codigoPerfil)
        )

        if (aplicarAUsuarios) {
            val idRol = when (codigoPerfil) {
                "P-02" -> 1
                "P-04" -> 2
                "P-01" -> 3
                "P-03" -> 4
                else -> 2
            }
            val valoresUsuarios = ContentValues().apply {
                put(ACTIVO_USUARIO, if (habilitado) 1 else 0)
            }
            writableDatabase.update(
                TABLA_USUARIO,
                valoresUsuarios,
                "$ROL_USUARIO = ?",
                arrayOf(idRol.toString())
            )
        }
    }

    fun obtenerDatosGrafico(): Map<String, Int> {
        val datosGrafico = mutableMapOf<String, Int>()
        val sql = "SELECT $PUERTA_ACCESO, SUM($CANT_CLIENTES) AS total_clientes " +
                "FROM $TABLA_CONTEO GROUP BY $PUERTA_ACCESO"

        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                val etiqueta = cursor.getString(cursor.getColumnIndexOrThrow(PUERTA_ACCESO))
                val total = cursor.getInt(cursor.getColumnIndexOrThrow("total_clientes"))
                datosGrafico[etiqueta] = total
            }
        }
        return datosGrafico
    }

    fun obtenerFlujoPorHora(): List<DatoGrafico> {
        val lista = mutableListOf<DatoGrafico>()
        val sql = "SELECT $HORA, SUM($CANT_CLIENTES) AS total " +
                "FROM $TABLA_CONTEO GROUP BY $HORA ORDER BY $ID_CONTEO ASC"

        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(HORA))
                val total = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
                lista.add(DatoGrafico(hora, total))
            }
        }
        return lista
    }

    fun obtenerSegmentacion(): List<DatoGrafico> {
        val lista = mutableListOf<DatoGrafico>()
        val sql = "SELECT SUM($CANT_TRANSITO) AS total_transito, " +
                "SUM($CANT_CLIENTES) AS total_clientes FROM $TABLA_CONTEO"

        readableDatabase.rawQuery(sql, null).use { cursor ->
            if (cursor.moveToFirst()) {
                val totalTransito = cursor.getInt(cursor.getColumnIndexOrThrow("total_transito"))
                val totalClientes = cursor.getInt(cursor.getColumnIndexOrThrow("total_clientes"))
                lista.add(DatoGrafico("Transito (Pasillo)", totalTransito))
                lista.add(DatoGrafico("Clientes / Visita", totalClientes))
            }
        }
        return lista
    }

    fun obtenerFlujoPorTurno(): List<DatoGrafico> {
        val lista = mutableListOf<DatoGrafico>()
        val sql = """
            SELECT 
                CASE 
                    WHEN CAST(REPLACE($HORA, 'h', '') AS INTEGER) < 14 THEN 'Turno Mañana'
                    ELSE 'Turno Tarde'
                END AS turno,
                SUM($CANT_CLIENTES) AS total_clientes
            FROM $TABLA_CONTEO
            GROUP BY turno
            ORDER BY turno ASC
        """.trimIndent()

        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                val turno = cursor.getString(cursor.getColumnIndexOrThrow("turno"))
                val total = cursor.getInt(cursor.getColumnIndexOrThrow("total_clientes"))
                lista.add(DatoGrafico(turno, total))
            }
        }
        return lista
    }

    fun registrarFormularioConteo(
        uiState: FormularioConteoUiState,
        nombreObservador: String,
        turnoHora: String,
        idUsuario: Long = 0
    ): Long {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(PUERTA_ACCESO, uiState.puertaAcceso)
            put(CANT_TRANSITO, uiState.cantidadTransito)
            put(CANT_CLIENTES, uiState.cantidadClientes)
            put(RANGO_ETARIO, uiState.rangoEtario)
            put(COMPANIA, uiState.compania)
            put(OBSERVADOR, nombreObservador)
            put(HORA, turnoHora)
            if (idUsuario > 0) put(ID_USUARIO_FK, idUsuario)
        }
        return db.insert(TABLA_CONTEO, null, valores)
    }

    fun listarConteos(): List<FormularioConteoUiState> {
        val lista = mutableListOf<FormularioConteoUiState>()
        val sql = "SELECT * FROM $TABLA_CONTEO ORDER BY $ID_CONTEO DESC"

        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                lista.add(
                    FormularioConteoUiState(
                        puertaAcceso = cursor.getString(cursor.getColumnIndexOrThrow(PUERTA_ACCESO)),
                        cantidadTransito = cursor.getInt(cursor.getColumnIndexOrThrow(CANT_TRANSITO)),
                        cantidadClientes = cursor.getInt(cursor.getColumnIndexOrThrow(CANT_CLIENTES)),
                        rangoEtario = cursor.getString(cursor.getColumnIndexOrThrow(RANGO_ETARIO)),
                        compania = cursor.getString(cursor.getColumnIndexOrThrow(COMPANIA)),
                        observador = cursor.getString(cursor.getColumnIndexOrThrow(OBSERVADOR)),
                        hora = cursor.getString(cursor.getColumnIndexOrThrow(HORA)),
                        envioExitoso = true
                    )
                )
            }
        }
        return lista
    }

    fun historialConteo(): List<FormularioConteoUiState> = listarConteos()

    fun listarRoles(): List<RolUsuario> = RolUsuario.obtenerListaRoles()

    fun autenticarUsuario(correoORut: String, contrasena: String): Usuario? {
        val usuario = buscarUsuarioPorRutOCorreo(correoORut) ?: return null
        if (!usuario.activo) return null

        val perfilHabilitado = listarPerfiles().find {
            it.codigo == usuario.perfil || it.nombre == usuario.perfil
        }?.habilitado ?: true

        if (!perfilHabilitado) return null

        val coincideHash = HashUtils.verificarHash(contrasena.trim(), usuario.passwordHash)
        val coincideTextoPlano = usuario.passwordHash == contrasena.trim()

        return if (coincideHash || coincideTextoPlano) usuario else null
    }

    private fun leerUsuario(cursor: Cursor): Usuario {
        val id = cursor.getLong(cursor.getColumnIndexOrThrow(ID_USUARIO))
        val codigoRol = cursor.getColumnIndex(CODIGO_ROL).let { idx ->
            if (idx != -1 && !cursor.isNull(idx)) cursor.getString(idx)
            else cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_ROL))
        }
        val cargo = cursor.getColumnIndex(CARGO_USUARIO).let { idx ->
            if (idx != -1 && !cursor.isNull(idx)) cursor.getString(idx) else "Colaborador"
        }
        val telefono = cursor.getColumnIndex(TELEFONO).let { idx ->
            if (idx != -1 && !cursor.isNull(idx)) cursor.getString(idx) else "900000000"
        }
        val activo = cursor.getColumnIndex(ACTIVO_USUARIO).let { idx ->
            if (idx != -1 && !cursor.isNull(idx)) cursor.getInt(idx) == 1 else true
        }
        val pregunta = cursor.getColumnIndex(PREGUNTA_SEG).let { idx ->
            if (idx != -1 && !cursor.isNull(idx)) cursor.getString(idx) else ""
        }
        val respuesta = cursor.getColumnIndex(RESPUESTA_SEG).let { idx ->
            if (idx != -1 && !cursor.isNull(idx)) cursor.getString(idx) else ""
        }

        return Usuario(
            codigo = "C-0$id",
            nombreCompleto = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_USUARIO)),
            rut = cursor.getString(cursor.getColumnIndexOrThrow(RUT_USUARIO)),
            cargo = cargo,
            perfil = codigoRol,
            correo = cursor.getString(cursor.getColumnIndexOrThrow(CORREO_USUARIO)),
            telefono = telefono,
            passwordHash = cursor.getString(cursor.getColumnIndexOrThrow(CONTRASENA)),
            activo = activo,
            preguntaSeguridad1 = pregunta,
            respuestaHash1 = respuesta,
            avatar = cursor.getColumnIndexOrThrow(AVATAR_USUARIO).let { columna ->
                if (cursor.isNull(columna)) null else cursor.getBlob(columna)
            }
        )
    }
}