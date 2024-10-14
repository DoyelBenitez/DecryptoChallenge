# Proyecto DecryptoChallenge
![Build Status](https://github.com/DoyelBenitez/DecryptoChallenge/actions/workflows/update-deployment-status.yml/badge.svg)  ![Deployment Status](https://img.shields.io/badge/Estado-Desplegado-brightgreen)

## Decisiones Tomadas

#### Estructura Modular:
- Se decidió dividir el proyecto en módulos independientes (Autenticación, Common, Country, y Client-Market) para facilitar la mantenibilidad. Esta estructura también permite una migración futura a microservicios si el proyecto lo requiere.

#### Modelo de Base de Datos:
- Se eligió una base de datos relacional para asegurar la integridad de los datos, desacoplando la relación entre Mercado y País para tener mayor flexibilidad en la evolución del proyecto, manteniendo el control desde el código.

#### Documentación con Swagger:
- Se integró Swagger para documentar la API y facilitar el uso de los endpoints. La página de Swagger fue protegida con autenticación para asegurar el acceso restringido y el uso controlado de la API.

#### Seguridad y Autenticación:
- Se implementó Spring Security se implementó para asegurar todos los endpoints, excepto el de login. Se eligió JWT como método de autenticación, asegurando un acceso seguro a través de tokens.

#### Escalabilidad y Despliegue:
- Se seleccionó Railway.app para el despliegue debido a su integración sencilla con Docker. El proyecto se dockerizó para garantizar la portabilidad y escalabilidad en cualquier entorno.

#### Pruebas:
- Se optó por JUnit 5 para pruebas de integración y Mockito para pruebas unitarias, lo que permite asegurar que los módulos se comporten correctamente de forma independiente y conjunta.

#### Patrones de Diseño y Buenas Prácticas:
- Se aplicaron principios SOLID y patrones como Builder, Adapter y Strategy para mejorar la flexibilidad y modularidad del sistema, facilitando el mantenimiento y las futuras extensiones.


## Indice

- [Autor](#Autor)
- [Tecnologías](#Tecnologías)
- [Rutas](#Rutas)
- [PreRequisitos](#Pre-requisites)
- [RunAPP](#Run-APP)
- [RunTEST](#Run-TEST)
- [Estándares](#Estándares)

## Autor

Benitez Doyel Franco

- LinkedIn: https://www.linkedin.com/in/doyel-franco-benitez/

## Tecnologías
- Lenguaje de programación: Java
- Framework: Spring Boot
- Contenedores: Docker, Docker-compose
- Deployment: Railway.app

## Rutas
- API swagger (local): http://localhost:8080/challenge/api/login
- API swagger (Railway): https://decryptochallenge-production.up.railway.app/challenge/api/login

## Pre-requisitos

- Tener instalado Docker y docker compose. Version minima de Docker 4.31.1 (153621)
- Tener libre el puerto 8080 para la API y el puerto 5440 para la BD

## Run-APP

1. Clonar el proyecto desde el repositorio y posicionarse en la raíz del proyecto.

#### IMPORTANTE!
      - Si utiliza Windows, al momento de clonar el repositorio seguramente se les cambió el EOF de los scripts de docker-compose-build-up.sh y docker-compose-build-up.sh a CRLF.
      - Para poder continuar es necesario que cambien el EOF a LF. Se debe abrir el archivo con un editor de texto y modificarlo.

2. En la raiz del proyecto se encuentra un script llamado "docker-compose-build-up.sh" que se encarga de construir la imagen y levantar el contenedor.
   Para eso es necesario darle al script permisos de ejecución en caso de que no lo tenga.
   Para darle permisos al script se debe correr el siguiente comando:
   
```
chmod 777 ./docker-compose-build-up.sh
```

3. Luego de verificar que el script tiene los permisos necesarios, se procede a ejecutarlo con el siguiente comando:

```
bash docker-compose-build-up.sh docker
```

4. Una vez ejecutado el script, la aplicación estará disponible en http://localhost:8080/challenge/api/login


5. Para acceder a la API es necesario que utilice las siguientes credenciales:
   - Usuario: "decrypto"
   - Contraseña: "challenge"


6. Una vez colocadas las credenciales va a poder acceder a swagger y probar los endpoints. 

Para probar la totalidad de los endpoints es necesario que primero obtenga un token de autenticación en la sección "Autenticación".

Ejecuta la consulta "/auth/v1/sigIn" y en la respuesta va a obtener un token que debe ser colocado en el botón verde (Authorize) que se encuentra arriba a la derecha de la pantalla del home de swagger.

   En este botón se debe colocar el token. Esto permite que pueda probar los endpoints de la API sin necesidad de tener que ponerlo para cada endpoint.

#### Nota:
En caso de que este probando en Railway y salgan problemas de CORS, revisar en swagger que hay un botón select para poder cambiar de ruta, debe colocar la ruta "https://decryptochallenge-production.up.railway.app/challenge/api/"


## Run-TEST

Aunque el docker-compose-build-up.sh ejecuta los test. Si se quisiera correr los test manualmente se necesita tener instalado localmente: 
- java 21.0.2 o superior.
- gradle 8.10.2 o superior.

Una vez que se tenga instalado los pre-requisitos, se debe ejecutar el siguiente comando en la raiz del proyecto:

```
.\gradlew test
```

### Estándares
- RESTful
- JSON Web Tokens (JWT)
- JsonAPI
- OpenAPI
- HTTP Status Codes

