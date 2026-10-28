# Tienda Retail Sur (Rincon del sur)

Aplicación móvil desarrollada en **Jetpack Compose** y **Kotlin** bajo arquitectura **MVVM**, diseñada para la gestión de aforos, control de accesos y administración de personal en **Tienda Retail Sur**.

---

##  Características Principales

1. **Gestión de Roles (Admin y Colaborador):**
    - **Vista Administrador:** Acceso a resúmenes globales de aforo de las 4 puertas, distribución de clientes vs. tránsito, y un panel avanzado de estadísticas (*Admin Dashboard*).
    - **Vista Colaborador:** Selección de puertas de acceso y formulario de conteo en vivo.
2. **Base de Datos Local (SQLite):**
    - Implementada mediante `UsuarioDbHelper`.
    - Incluye tablas relacionales para `roles`, `usuario` (con soporte de turnos diurnos/vespertinos y avatares en `BLOB`), `puertas` (accesos A1 a A4) y `formulario_conteo`.
    - Datos precargados iniciales para pruebas inmediatas.
3. **Formulario de Conteo en Vivo:**
    - Registro de personas en tránsito y clientes con intención de compra.
    - Selección de rangos etarios y tipo de compañía mediante `FilterChip` y `FlowRow`.
    - Persistencia automática de los registros en SQLite.
4. **Perfil de Usuario con Cámara y Galería:**
    - Visualización de datos del usuario (Nombre, Correo, Turno y Cargo).
    - Actualización de la fotografía de perfil mediante permisos en tiempo real (`CAMERA` y `READ_MEDIA_IMAGES` / `READ_EXTERNAL_STORAGE`).
5. **Navegación y Temas:**
    - Navegación fluida impulsada por **Jetpack Navigation Compose**.
    - Soporte adaptativo para **Modo Claro** y **Modo Oscuro** basados en Material 3.

---

##  Arquitectura del Proyecto (MVVM)

```
com.example.tiendaretailsur/
├── data/
│   └── UsuarioDbHelper.kt        # Gestor de SQLite (Tablas y consultas)
├── model/
│   ├── FormularioConteoUiState.kt
│   ├── MenuPrincipalUiState.kt
│   ├── Perfil.kt
│   ├── RolUsuario.kt
│   ├── Usuario.kt
│   └── ...
├── navigation/
│   └── NavegacionApp.kt          # Grafo de navegación (NavHost)
├── permisos/
│   └── Permisos.kt               # Verificación y solicitud de permisos (Cámara y Galería)
├── ui/
│   ├── components/               # Componentes reutilizables (AvatarCircular, UserHeader, etc.)
│   ├── screens/                  # Pantallas (Login, MenuPrincipal, FormularioConteo, Perfil, AdminDashboard)
│   └── theme/                    # Temas, Colores, Tipografía y Dimensiones (Material 3)
└── viewmodel/
    ├── AdminDashboardViewModel.kt
    ├── FormularioConteoViewModel.kt
    ├── MenuPrincipalViewModel.kt
    ├── PerfilViewModel.kt
    └── LoginViewModel.kt
```

---

##  Tecnologías y Librerías Utilizadas

- **Kotlin** & **Jetpack Compose** (UI declarativa moderna).
- **Material 3** (Sistema de diseño oficial).
- **Jetpack Navigation Compose** (Gestión de pantallas y rutas).
- **Android SQLite / SQLiteOpenHelper** (Persistencia local relacional).
- **ViewModel & StateFlow** (Gestión reactiva de estados UI).
- **Activity Result API** (`TakePicturePreview`, `RequestPermission`, etc.).

---

## Requisitos y Ejecución

1. Clonar o abrir el proyecto en **Android Studio**.
2. Sincronizar el proyecto con Gradle (**Sync Project with Gradle Files**).
3. Ejecutar la aplicación en un emulador o dispositivo físico con Android 7.0 (API 24) o superior.
