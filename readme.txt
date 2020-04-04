Arquitectura Usada MVP


Clases
Artista. Clase referencia datos Artista descargado
Imagenes. Clase referencia lista datos imagenes descargadas por artista
Inicio. Activity de seleccion de pais y desino de la aplicacion.
	destino 0: Descarga de artistas
	destino 1: Descarga de Canciones
MainActivity. Capa presentación, proceso de descarga atachado a esta capa
Negocio. Ejecuta las funcionalidades desde la capa presentación hasta la capa de datos.
RestClient. Clase encargada de la ejecución de respuesta del Json y la devolución de los datos respectivos
SQLite. Capa de datos
Tracks. Clase referencia de las canciones
Ubicacion. Clase referencia de la descarga de datos de paginacion
VariablesGenerales. Capa de datos, variables globales utilizadas por la aplicación.
VisorPaginas. Clase especializada en la presentación de fragmentos de las paginas
VpAdapter. Clase referencia de la pagian presentada en VisorPaginas