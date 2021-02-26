# Reconocimiento de ADN Mutante

Esta aplicación permite reconocer si una muestra de ADN pertenece a un mutante o no.
Se accede por medio de un endpoint (/mutantRecognition/mutant) de tipo POST que recibe en el body un arreglo de cadenas con la configuracion del ADN.

# Tecnologias

Se realiza el desarrollo en Java 8 con framework spring boot.
Para las pruebas unitarias se utiliza JUnit.
Como repositorio de datos se utiliza MongoDB.

# Ejecución en ambiente local

1. Clonar o descargar el código fuente del proyecto
2. Importar en el IDE deseado (eclipse o intellij), actualizar las dependencias de gradle 
3. La conexion con la BD se realiza con la configuración del archivo application.yml, se debe tener instalado el MongoDB. Cambiar la cadena de conexión.
4. Ejecutar el proyecto, este sera desplegado en el puerto 80.

# Ejecución en AWS

Realizar el consumo de los servicios en los endpoints
Estadisticas:
http://ec2-3-133-101-174.us-east-2.compute.amazonaws.com/mutantRecognition/stats
Validacion ADN:
http://ec2-3-133-101-174.us-east-2.compute.amazonaws.com/mutantRecognition/mutant
Body:
{
    "dnaSample": [
        "ATGCGA",
        "CAGTGC",
        "TTATGT",
        "AGAAGG",
        "CCCCTA",
        "TCACTG"
    ]
}

