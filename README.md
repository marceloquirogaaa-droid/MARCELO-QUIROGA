# GymFlow V1.2 Java + Supabase

Versión conectada a Supabase. Incluye registro/login por email, aislamiento de datos por dueño mediante RLS, socios, pagos, equipamiento, profesores, rutinas, progreso y configuración online.

V1.2 renueva automáticamente la sesión cuando vence el token, cierra la sesión actual también en Supabase y corrige la combinación de Android Gradle Plugin/Gradle usada por la compilación automática.

## Backend
Proyecto Supabase configurado con tablas `gf_*`. La app usa únicamente la publishable key; nunca incluye claves secretas.

La carpeta `supabase/migrations` conserva los cambios de base aplicados para que el proyecto y la nube sigan sincronizados.

## Compilar APK
Abrir la carpeta GymFlow en Android Studio, esperar sincronización de Gradle y usar Build > Build APK(s). Requiere JDK 17.
