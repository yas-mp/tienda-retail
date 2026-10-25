package com.example.tiendaretailsur.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
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
        private const val AVATAR_USUARIO = "avatar"

        private const val TABLA_CONTEO = "formulario_conteo"
        private const val ID_CONTEO = "id_conteo"
        private const val PUERTA_ACCESO = "puerta_acceso"
        private const val CANT_TRANSITO = "cantidad_transito"
        private const val CANT_CLIENTES = "cantidad_clientes"
        private const val RANGO_ETARIO = "rango_etario"
        private const val COMPANIA = "compania"
        private const val OBSERVADOR = "observador"
        private const val HORA = "hora"

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
                    "$CONTRASENA TEXT NOT NULL, " +
                    "$AVATAR_USUARIO BLOB, " +
                    "$ROL_USUARIO INTEGER NOT NULL REFERENCES $TABLA_ROLES($ID_ROL))"

        private const val SQL_AGREGAR_AVATAR =
            "ALTER TABLE $TABLA_USUARIO ADD COLUMN $AVATAR_USUARIO BLOB"

        private const val SQL_PRECARGAR_ROLES =
        "INSERT INTO $TABLA_ROLES ($ID_ROL,$CODIGO_ROL, $NOMBRE_ROL, $DESCRIPCION_ROL) VALUES " +
                "(1, 'P-02','SUBGERENTE', 'Encargado de los reportes y gestión'), " +
                "(2, 'P-04','OBSERVADOR', 'Toma datos de conteo en terreno')"

        private const val SQL_PRECARGAR_USUARIOS =
            "INSERT INTO $TABLA_USUARIO ($RUT_USUARIO, $NOMBRE_USUARIO, $CORREO_USUARIO, $CONTRASENA, $ROL_USUARIO) VALUES " +
                    "('12345678-9', 'Gabriel Gómez', 'admin@rincondelsur.cl', '1234', 1), " +
                    "('98765432-1', 'Juan Pérez', 'juan@rincondelsur.cl', '1234', 2), " +
                    "('18111222-3', 'Camila Soto', 'c.soto@rincondelsur.cl', '1234', 2), " +
                    "('19222333-4', 'Mateo Silva', 'm.silva@rincondelsur.cl', '1234', 2), " +
                    "('17333444-5', 'Valentina Rojas', 'v.rojas@rincondelsur.cl', '1234', 2)"

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
        db.execSQL(SQL_PRECARGAR_ROLES)
        db.execSQL(SQL_PRECARGAR_USUARIOS)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        if(oldVersion < 2){
            db.execSQL(SQL_AGREGAR_AVATAR)
        }
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
        }
        return db.insert(TABLA_USUARIO, null, valores)

    }

    fun registrarFormularioConteo(
        uiState: FormularioConteoUiState,
        nombreObservador: String,
        turnoHora: String
    ) : Long{
        val db = writableDatabase

        val valores = ContentValues().apply {
            put(PUERTA_ACCESO, uiState.puertaAcceso)
            put(CANT_TRANSITO, uiState.cantidadTransito)
            put(CANT_CLIENTES, uiState.cantidadClientes)
            put(RANGO_ETARIO, uiState.rangoEtario)
            put(COMPANIA, uiState.compania)
            put(OBSERVADOR, nombreObservador)
            put(HORA, turnoHora)
        }
        return db.insert(TABLA_CONTEO, null, valores)
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

