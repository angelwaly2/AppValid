Arquitectura Usada MVP


Clases
Artista. Clase referencia datos Artista descargado
Imagenes. Clase referencia lista datos imagenes descargadas por artista
Inicio. Activity de seleccion de pais y desino de la aplicacion.
	destino 0: Descarga de artistas
	destino 1: Descarga de Canciones
MainActivity. Capa presentaci�n, proceso de descarga atachado a esta capa
Negocio. Ejecuta las funcionalidades desde la capa presentaci�n hasta la capa de datos.
RestClient. Clase encargada de la ejecuci�n de respuesta del Json y la devoluci�n de los datos respectivos
SQLite. Capa de datos
Tracks. Clase referencia de las canciones
Ubicacion. Clase referencia de la descarga de datos de paginacion
VariablesGenerales. Capa de datos, variables globales utilizadas por la aplicaci�n.
VisorPaginas. Clase especializada en la presentaci�n de fragmentos de las paginas
VpAdapter. Clase referencia de la pagian presentada en VisorPaginas