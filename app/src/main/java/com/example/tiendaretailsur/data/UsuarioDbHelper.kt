package com.example.tiendaretailsur.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tiendaretailsur.model.AccesoTienda
import com.example.tiendaretailsur.model.DatoGrafico
import com.example.tiendaretailsur.model.FormularioConteoUiState
import com.example.tiendaretailsur.model.FormularioRegistroUiState
import com.example.tiendaretailsur.model.RolUsuario
import com.example.tiendaretailsur.model.Usuario

class UsuarioDbHelper(context: Context)
    : SQLiteOpenHelper(context, NOMBRE_BD,null, VERSION_DB){

    companion object {
        private const val NOMBRE_BD = "tiendaretailsur.db"
        private const val VERSION_DB = 2

        private const val TABLA_ROLES = "roles"
        private const val ID_ROL = "id_rol"
        private const val CODIGO_ROL = "codigo_rol"
        private const val NOMBRE_ROL = "nombre_rol"
        private const val DESCRIPCION_ROL = "descripcion_rol"

        private const val TABLA_USUARIO = "usuario"
        private const val ID_USUARIO = "id_usuario"
        private const val RUT_USUARIO = "rut_usuario"
        private const val NOMBRE_USUARIO = "nombre_usuario"
        private const val CORREO_USUARIO = "correo_usuario"
        private const val TELEFONO = "telefono"
        private const val CONTRASENA = "contrasena"
        private const val ROL_USUARIO = "id_rol"
        private const val PREGUNTA_SEG =  "pregunta_seguridad"
        private const val RESPUESTA_SEG = "respuesta_seguridad"
        private const val AVATAR_USUARIO = "avatar"
        private const val TURNO_USUARIO = "turno"


        private const val TABLA_PUERTAS = "puertas"
        private const val ID_PUERTA = "id_puerta"
        private const val NOMBRE_PUERTA = "nombre_puerta"
        private const val DESCRIPCION_PUERTA = "descripcion_puerta"

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
                    "$DESCRIPCION_ROL TEXT NOT NULL) "

        private const val SQL_CREAR_USUARIOS =
            "CREATE TABLE $TABLA_USUARIO (" +
                    "$ID_USUARIO INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$RUT_USUARIO TEXT NOT NULL UNIQUE, " +
                    "$NOMBRE_USUARIO TEXT NOT NULL, " +
                    "$CORREO_USUARIO TEXT NOT NULL, " +
                    "$TELEFONO TEXT, " +
                    "$CONTRASENA TEXT NOT NULL, " +
                    "$PREGUNTA_SEG TEXT, " +
                    "$RESPUESTA_SEG TEXT, " +
                    "$AVATAR_USUARIO BLOB, " +
                    "$TURNO_USUARIO TEXT DEFAULT 'N/A', " +
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

        private const val SQL_AGREGAR_AVATAR =
            "ALTER TABLE $TABLA_USUARIO ADD COLUMN $AVATAR_USUARIO BLOB"

        private const val SQL_PRECARGAR_ROLES =
        "INSERT INTO $TABLA_ROLES ($ID_ROL,$CODIGO_ROL, $NOMBRE_ROL, $DESCRIPCION_ROL) VALUES " +
                "(1, 'P-02','SUBGERENTE', 'Encargado de los reportes y gestión'), " +
                "(2, 'P-04','OBSERVADOR', 'Toma datos de conteo en terreno')"

        private const val SQL_PRECARGAR_USUARIOS =
            "INSERT INTO $TABLA_USUARIO ($RUT_USUARIO, $NOMBRE_USUARIO, $CORREO_USUARIO, $CONTRASENA, $TURNO_USUARIO, $PREGUNTA_SEG, $RESPUESTA_SEG, $ROL_USUARIO) VALUES " +
                    "('12345678-9', 'Gabriel Gómez', 'admin@rincondelsur.cl', '1234', 'N/A', '¿Mascota?', 'perro', 1), " +
                    "('98765432-1', 'Juan Pérez', 'juan@rincondelsur.cl', '1234', 'Turno Diurno', '¿Mascota?', 'gato', 2), " +
                    "('18111222-3', 'Camila Soto', 'c.soto@rincondelsur.cl', '1234', 'Turno Vespertino', '¿Mascota?', 'perro', 2), " +
                    "('19222333-4', 'Mateo Silva', 'm.silva@rincondelsur.cl', '1234', 'Turno Diurno', '¿Mascota?', 'gato', 2), " +
                    "('17333444-5', 'Valentina Rojas', 'v.rojas@rincondelsur.cl', '1234', 'Turno Vespertino', '¿Mascota?', 'perro', 2)"

        private const val SQL_PRECARGAR_PUERTAS =
            "INSERT INTO $TABLA_PUERTAS ($ID_PUERTA, $NOMBRE_PUERTA, $DESCRIPCION_PUERTA) VALUES " +
                    "('A1', 'Acceso Principal', 'Patio de comidas del mall / Pasillo central'), " +
                    "('A2', 'Acceso Lateral', 'Pasillo del mall, lado norte'), " +
                    "('A3', 'Acceso Estacionamiento', 'Estacionamiento subterráneo (señal de red débil)'), " +
                    "('A4', 'Acceso Conexión', 'Conexión directa con tienda vecina de mejoramiento del hogar')"

        private const val SQL_PRECARGAR_CONTEOS =
            "INSERT INTO $TABLA_CONTEO (" +
                    "$PUERTA_ACCESO, $CANT_TRANSITO, $CANT_CLIENTES, $RANGO_ETARIO, $COMPANIA, $OBSERVADOR, $HORA) VALUES " +
                    "('A1', 45, 12, '26–40', 'Solo', 'Juan Pérez', '10h'), " +
                    "('A1', 65, 28, '18–25', 'Pareja', 'Juan Pérez', '12h'), " +
                    "('A2', 80, 35, '26–40', 'Familia', 'Camila Soto', '14h'), " +
                    "('A3', 50, 18, '41–60', 'Solo', 'Mateo Silva', '16h'), " +
                    "('A1', 95, 42, '26–40', 'Pareja', 'Valentina Rojas', '18h')"


        private const val SQL_SELECT_USUARIOS =
            "SELECT u.*, r.$NOMBRE_ROL AS $NOMBRE_ROL " +
                    "FROM $TABLA_USUARIO u " +
                    "INNER JOIN $TABLA_ROLES r ON u.$ROL_USUARIO = r.$ID_ROL"

    }

    override fun onConfigure(db: SQLiteDatabase) {
        super.onConfigure(db)
        db.setForeignKeyConstraintsEnabled(true)
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREAR_ROLES)
        db.execSQL(SQL_CREAR_USUARIOS)
        db.execSQL(SQL_CREAR_CONTEOS)
        db.execSQL(SQL_PRECARGAR_ROLES)
        db.execSQL(SQL_PRECARGAR_USUARIOS)
        db.execSQL(SQL_PRECARGAR_CONTEOS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion < 2){
            db.execSQL(SQL_AGREGAR_AVATAR)
        }
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

    fun actualizarAvatar(id: Long, avatar: ByteArray): Int {
        val valores = ContentValues().apply { put(AVATAR_USUARIO, avatar) }
        return writableDatabase.update(
            TABLA_USUARIO,
            valores,
            "$ID_USUARIO =?",
            arrayOf(id.toString())
        )
    }
    fun registroUsuarioFormularioRegistro(uiState: FormularioRegistroUiState) : Long{
        val db = writableDatabase

        val valores = ContentValues().apply {
            put(RUT_USUARIO, uiState.rut)
            put(NOMBRE_USUARIO, uiState.nombre)
            put(CORREO_USUARIO, uiState.correo)
            put(TELEFONO, uiState.telefono)
            put(CONTRASENA, uiState.contrasena)
            put(ROL_USUARIO, uiState.rolSeleccionado.id)
            put(PREGUNTA_SEG, uiState.preguntaSeleccionada)
            put(RESPUESTA_SEG, uiState.respuestaSeguridad.trim().lowercase())
            put(TURNO_USUARIO, "Turno Diurno")
        }
        return db.insert(TABLA_USUARIO, null, valores)

    }
    fun obtenerPreguntaSeguridad(rutOCorreo: String) : String? {
        val db = readableDatabase
        val sql = "SELECT $PREGUNTA_SEG FROM $TABLA_USUARIO WHERE $RUT_USUARIO = ? OR $CORREO_USUARIO = ?"
        db.rawQuery(sql, arrayOf(rutOCorreo)).use { cursor ->
            if (cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndexOrThrow(PREGUNTA_SEG))
            }
        }
        return null
    }
    fun verificarRespuesta(rutOCorreo: String, respuestaIngresada: String) : Boolean{
        val db = readableDatabase
        val sql = "SELECT $RESPUESTA_SEG FROM $TABLA_USUARIO WHERE $RUT_USUARIO = ? OR $CORREO_USUARIO = ?"
        db.rawQuery(sql, arrayOf(rutOCorreo, rutOCorreo)).use { cursor ->
            if (cursor.moveToFirst()) {
                val respuestaGuardada = cursor.getString(cursor.getColumnIndexOrThrow(RESPUESTA_SEG))
                return respuestaGuardada.equals(respuestaIngresada.trim().lowercase(), ignoreCase = true)
            }
        }
        return false
    }
    fun actualizarContrasena(rutOCorreo: String, nuevaContrasena: String): Int {
        val db = writableDatabase
        val valores = ContentValues().apply {
            put(CONTRASENA, nuevaContrasena)
        }
        return db.update(
            TABLA_USUARIO,
            valores,
            "$RUT_USUARIO = ? OR $CORREO_USUARIO = ?",
            arrayOf(rutOCorreo, rutOCorreo)
        )
    }

    fun obtenerDatosGrafico() : Map<String, Int> {
        val datosGrafico = mutableMapOf<String, Int>()

        val sql = "SELECT $PUERTA_ACCESO, SUM($CANT_CLIENTES) AS total_clientes " +
                "FROM $TABLA_CONTEO " +
                "GROUP BY $PUERTA_ACCESO"

        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                val etiqueta = cursor.getString(cursor.getColumnIndexOrThrow(PUERTA_ACCESO))
                val total = cursor.getInt(cursor.getColumnIndexOrThrow("total_clientes"))
                datosGrafico[etiqueta] = total
            }
        }
        return datosGrafico
    }
    fun obtenerFlujoPorHora() : List<DatoGrafico> {
        val lista = mutableListOf<DatoGrafico>()
        val sql = "SELECT $HORA, SUM($CANT_CLIENTES) AS total " +
                "FROM $TABLA_CONTEO " +
                "GROUP BY $HORA " +
                "ORDER BY $ID_CONTEO ASC"

        readableDatabase.rawQuery(sql, null).use { cursor ->
            while (cursor.moveToNext()) {
                val hora = cursor.getString(cursor.getColumnIndexOrThrow(HORA))
                val total = cursor.getInt(cursor.getColumnIndexOrThrow("total"))
                lista.add(DatoGrafico(hora,total))

            }
        }
        return lista
    }
    fun obtenerSegmentacion() : List<DatoGrafico> {
        val lista = mutableListOf<DatoGrafico>()
        val sql = "SELECT SUM($CANT_TRANSITO) AS total_transito, " +
                "SUM($CANT_CLIENTES) AS total_clientes " +
                "FROM $TABLA_CONTEO"

        readableDatabase.rawQuery(sql, null).use { cursor ->
            if(cursor.moveToFirst()) {
                val totalTransito = cursor.getInt(cursor.getColumnIndexOrThrow("total_transito"))
                val totalClientes = cursor.getInt(cursor.getColumnIndexOrThrow("total_clientes"))

                lista.add(DatoGrafico("Transito (Pasillo)", totalTransito))
                lista.add(DatoGrafico("Clientes / Visita", totalClientes))
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

    fun historialConteo() : List<FormularioConteoUiState>{
        return listarConteos()
    }

    fun listarRoles() : List<RolUsuario>{
        return RolUsuario.obtenerListaRoles()
    }

    fun autenticarUsuario(correo: String, contrasena: String): Usuario? {
        val inicioSesion = "SELECT u.*, r.$NOMBRE_ROL AS $NOMBRE_ROL " +
                "FROM $TABLA_USUARIO u " +
                "INNER JOIN $TABLA_ROLES r ON u.$ROL_USUARIO = r.$ID_ROL " +
                "WHERE u.$CORREO_USUARIO = ? AND u.$CONTRASENA = ?"

        readableDatabase.rawQuery(inicioSesion, arrayOf(correo, contrasena)).use {cursor ->
            if(cursor.moveToFirst()){
                return leerUsuario(cursor)
            }
        }
        return null

    }
    private fun leerUsuario(cursor : Cursor) = Usuario(
        nombreCompleto = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_USUARIO)),
        rut = cursor.getString(cursor.getColumnIndexOrThrow(RUT_USUARIO)),
        correo = cursor.getString(cursor.getColumnIndexOrThrow(CORREO_USUARIO)),
        passwordHash = cursor.getString(cursor.getColumnIndexOrThrow(CONTRASENA)),
        perfil = cursor.getString(cursor.getColumnIndexOrThrow(NOMBRE_ROL)),
        avatar = cursor.getColumnIndexOrThrow(AVATAR_USUARIO).let { columna ->
            if(cursor.isNull(columna)) null else cursor.getBlob(columna)
        }
    )



}

