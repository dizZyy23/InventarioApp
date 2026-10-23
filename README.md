# InventarioApp - Taller Automotriz

## ¿De qué trata este proyecto?
Esta es nuestra entrega para la Evaluación Parcial 2 de la asignatura Desarrollo de Aplicaciones Móviles (DSY1105)[cite: 28, 29]. Desarrollamos una app funcional para gestionar el stock, registrar productos y armar pedidos de reposición en un taller mecánico[cite: 6]. 

## Integrantes
* Brayan Pino
* Gabriel Millán

## Lo que implementamos (Funcionalidades)
* **Arquitectura y Datos:** Estructuramos el código siguiendo el patrón MVVM y usamos Room para guardar los productos de forma local (persistencia de datos)[cite: 31].
* **Diseño y Navegación:** Armamos las vistas con Jetpack Compose y Material 3, conectando la pantalla del listado con la del formulario de registro[cite: 28, 30].
* **Validaciones:** Los formularios revisan los datos desde el ViewModel. Si hay algún error (como dejar un campo vacío), la interfaz muestra íconos de alerta y textos de ayuda animados[cite: 30, 31].
* **Recursos Nativos:** Integramos dos funciones del teléfono para cumplir con los requisitos[cite: 32]:
  1. **Cámara:** Un botón que abre la cámara para simular el escaneo de un código QR al agregar un producto.
  2. **Correo Electrónico:** Un acceso rápido que abre la app de mail del celular con un mensaje prearmado para pedirle stock a los proveedores.

## ¿Cómo probar la app?
1. Clona este repositorio en tu equipo.
2. Abre la carpeta del proyecto desde Android Studio.
3. Espera a que termine de cargar la sincronización de Gradle.
4. Ejecuta la app en un emulador o en tu celular físico.
