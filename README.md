# Escuela Colombiana de Ingeniería

### Arquitecturas de Software

## API REST para la gestión de planos.

Este programa es una API REST en Java con Spring Boot que permite la gestión de planos. Los usuarios pueden crear, consultar, modificar y listar planos, los cuales son almacenados en una capa de persistencia basada en memoria. La API sigue un enfoque modular con separación de responsabilidades en controladores, servicios y persistencia, facilitando futuras mejoras como la integración con bases de datos o la optimización del rendimiento.

## Funcionalidades Principales

1. Obtener todos los planos → GET /blueprints
* Devuelve una lista de todos los planos registrados en el sistema.
2. Obtener un plano específico → GET /blueprints/{author}/{name}
* Busca un plano por su autor y nombre.
* Responde con 404 Not Found si no existe.
3. Obtener todos los planos de un autor → GET /blueprints/{author}
* Retorna todos los planos de un autor específico.
4. Registrar un nuevo plano → POST /blueprints
* Recibe un JSON con los detalles del plano y lo guarda en la base de datos.
* Si el plano ya existe, responde con 409 Conflict.
5. Actualizar un plano existente → PUT /blueprints/{author}/{name} (Pendiente de implementación)
* Permitirá modificar un plano previamente registrado.

## Estructura del código

1️. Controlador: BlueprintAPIController

Define los endpoints de la API y maneja las solicitudes HTTP.
Usa BlueprintsServices para procesar los datos.
Maneja errores y responde con códigos HTTP adecuados.

2️. Servicio: BlueprintsServices

Implementa la lógica de negocio.
Valida datos y realiza operaciones sobre la capa de persistencia.

3️. Persistencia: BlueprintsPersistence (Interfaz)

Define los métodos de almacenamiento y recuperación de planos.
Permite desacoplar la implementación específica de la persistencia.

4️. Implementación en Memoria: InMemoryBlueprintPersistence

Usa un HashMap para almacenar planos en memoria.
Contiene datos de prueba predefinidos para simular una base de datos.
Implementa la interfaz BlueprintsPersistence.

5️. Modelo de Datos: Blueprint y Point

Blueprint representa un plano con un autor, nombre y una lista de puntos.
Point representa una coordenada (x, y) dentro de un plano.

## Ejecución
```
git clone https://github.com/Pau993/TallerARSW05.git
cd TallerARSW05
git checkout main
mvn clean compile
mvn spring-boot:run
```

# Parte I

1. Integre al proyecto base suministrado los Beans desarrollados en el ejercicio anterior. Sólo copie las clases, NO los archivos de configuración. Rectifique que se tenga correctamente configurado el esquema de inyección de dependencias con las anotaciones @Service y @Autowired.

![image](https://github.com/user-attachments/assets/66371694-25fa-42f3-b8f4-931dcba40303)

Clases utilizadas.

2. Modifique el bean de persistecia 'InMemoryBlueprintPersistence' para que por defecto se inicialice con al menos otros tres planos, y con dos asociados a un mismo autor.

![image](https://github.com/user-attachments/assets/fd886ad7-614b-41b3-9ef8-c4abaa975cb1)


3. Configure su aplicación para que ofrezca el recurso "/blueprints", de manera que cuando se le haga una petición GET, retorne -en formato jSON- el conjunto de todos los planos. Para esto:

	* Modifique la clase BlueprintAPIController teniendo en cuenta el siguiente ejemplo de controlador REST hecho con SpringMVC/SpringBoot:

	```java
	@RestController
	@RequestMapping(value = "/url-raiz-recurso")
	public class XXController {
    
        
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> manejadorGetRecursoXX(){
        try {
            //obtener datos que se enviarán a través del API
            return new ResponseEntity<>(data,HttpStatus.ACCEPTED);
        } catch (XXException ex) {
            Logger.getLogger(XXController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla",HttpStatus.NOT_FOUND);
        }        
	}

	```
	* Haga que en esta misma clase se inyecte el bean de tipo BlueprintServices (al cual, a su vez, se le inyectarán sus dependencias de persisntecia y de filtrado de puntos).
![image](https://github.com/user-attachments/assets/620abaad-4e64-4b2e-97c2-6a6b6ea5bf44)


4. Verifique el funcionamiento de a aplicación lanzando la aplicación con maven:

	```bash
	$ mvn compile
	$ mvn spring-boot:run
	
	```
	Y luego enviando una petición GET a: http://localhost:8080/blueprints. Rectifique que, como respuesta, se obtenga un objeto jSON con una lista que contenga el detalle de los planos suministados por defecto, y que se haya aplicado el filtrado de puntos correspondiente.

![image](https://github.com/user-attachments/assets/f480f731-83c1-4aa2-9eda-3a7e48a8efd6)

![image](https://github.com/user-attachments/assets/0c1f1c97-bc90-483e-808e-dd78096db31e)

![image](https://github.com/user-attachments/assets/88eb34be-9bc3-49c1-8c02-fb39aad9a024)



6. Modifique el controlador para que ahora, acepte peticiones GET al recurso /blueprints/{author}, el cual retorne usando una representación jSON todos los planos realizados por el autor cuyo nombre sea {author}. Si no existe dicho autor, se debe responder con el código de error HTTP 404. Para esto, revise en [la documentación de Spring](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/mvc.html), sección 22.3.2, el uso de @PathVariable. De nuevo, verifique que al hacer una petición GET -por ejemplo- a recurso http://localhost:8080/blueprints/juan, se obtenga en formato jSON el conjunto de planos asociados al autor 'juan' (ajuste esto a los nombres de autor usados en el punto 2).

![image](https://github.com/user-attachments/assets/fb0c18e5-ff7f-4ac7-8a41-5e18189faa0f)
![image](https://github.com/user-attachments/assets/b648908a-c0cc-4f34-a581-8e4937a39242)

7. Modifique el controlador para que ahora, acepte peticiones GET al recurso /blueprints/{author}/{bpname}, el cual retorne usando una representación jSON sólo UN plano, en este caso el realizado por {author} y cuyo nombre sea {bpname}. De nuevo, si no existe dicho autor, se debe responder con el código de error HTTP 404.

![image](https://github.com/user-attachments/assets/1daa491f-9957-40ce-8277-d2d39112ac9a)

![image](https://github.com/user-attachments/assets/e362dd7a-b9e5-4f9b-ae2e-b5a5ec6221b1)

# Parte II

1.  Agregue el manejo de peticiones POST (creación de nuevos planos), de manera que un cliente http pueda registrar una nueva orden haciendo una petición POST al recurso ‘planos’, y enviando como contenido de la petición todo el detalle de dicho recurso a través de un documento jSON. Para esto, tenga en cuenta el siguiente ejemplo, que considera -por consistencia con el protocolo HTTP- el manejo de códigos de estados HTTP (en caso de éxito o error):

	```	java
	@RequestMapping(method = RequestMethod.POST)	
	public ResponseEntity<?> manejadorPostRecursoXX(@RequestBody TipoXX o){
        try {
            //registrar dato
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (XXException ex) {
            Logger.getLogger(XXController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("Error bla bla bla",HttpStatus.FORBIDDEN);            
        }        
 	
	}
	```

 ![image](https://github.com/user-attachments/assets/9e1165b6-c582-4f6f-9b12-d10e167b2ae8)




2.  Para probar que el recurso ‘planos’ acepta e interpreta
    correctamente las peticiones POST, use el comando curl de Unix. Este
    comando tiene como parámetro el tipo de contenido manejado (en este
    caso jSON), y el ‘cuerpo del mensaje’ que irá con la petición, lo
    cual en este caso debe ser un documento jSON equivalente a la clase
    Cliente (donde en lugar de {ObjetoJSON}, se usará un objeto jSON correspondiente a una nueva orden:

	```	
	$ curl -i -X POST -HContent-Type:application/json -HAccept:application/json http://URL_del_recurso_ordenes -d '{ObjetoJSON}'
	```	

![image](https://github.com/user-attachments/assets/a7c0a480-4582-4446-a622-fa861b9a71ce)

	
 Con lo anterior, registre un nuevo plano (para 'diseñar' un objeto jSON, puede usar [esta herramienta](http://www.jsoneditoronline.org/)):
	

	Nota: puede basarse en el formato jSON mostrado en el navegador al consultar una orden con el método GET.


3. Teniendo en cuenta el autor y numbre del plano registrado, verifique que el mismo se pueda obtener mediante una petición GET al recurso '/blueprints/{author}/{bpname}' correspondiente.

![image](https://github.com/user-attachments/assets/68e5d119-da97-4396-b294-eecaf80e9ebc)


4. Agregue soporte al verbo PUT para los recursos de la forma '/blueprints/{author}/{bpname}', de manera que sea posible actualizar un plano determinado.

![image](https://github.com/user-attachments/assets/ffabf090-005f-48ae-bc7a-2e2f22d41ea5)



### Parte III

El componente BlueprintsRESTAPI funcionará en un entorno concurrente. Es decir, atederá múltiples peticiones simultáneamente (con el stack de aplicaciones usado, dichas peticiones se atenderán por defecto a través múltiples de hilos). Dado lo anterior, debe hacer una revisión de su API (una vez funcione), e identificar:

* Qué condiciones de carrera se podrían presentar?
* Cuales son las respectivas regiones críticas?

Ajuste el código para suprimir las condiciones de carrera. Tengan en cuenta que simplemente sincronizar el acceso a las operaciones de persistencia/consulta DEGRADARÁ SIGNIFICATIVAMENTE el desempeño de API, por lo cual se deben buscar estrategias alternativas.

Escriba su análisis y la solución aplicada en el archivo ANALISIS_CONCURRENCIA.txt

#### Criterios de evaluación

1. Diseño.
	* Al controlador REST implementado se le inyectan los servicios implementados en el laboratorio anterior.
	* Todos los recursos asociados a '/blueprint' están en un mismo Bean.
	* Los métodos que atienden las peticiones a recursos REST retornan un código HTTP 202 si se procesaron adecuadamente, y el respectivo código de error HTTP si el recurso solicitado NO existe, o si se generó una excepción en el proceso (dicha excepción NO DEBE SER de tipo 'Exception', sino una concreta)	
2. Funcionalidad.
	* El API REST ofrece los recursos, y soporta sus respectivos verbos, de acuerdo con lo indicado en el enunciado.
3. Análisis de concurrencia.
	* En el código, y en las respuestas del archivo de texto, se tuvo en cuenta:
		* La colección usada en InMemoryBlueprintPersistence no es Thread-safe (se debió cambiar a una con esta condición).
		* El método que agrega un nuevo plano está sujeta a una condición de carrera, pues la consulta y posterior agregación (condicionada a la anterior) no se realizan de forma atómica. Si como solución usa un bloque sincronizado, se evalúa como R. Si como solución se usaron los métodos de agregación condicional atómicos (por ejemplo putIfAbsent()) de la colección 'Thread-Safe' usada, se evalúa como B.

## Autores ✒️
**Paula Natalia Paez Vega* **Manuel Felipe Barrera Barrera* - *Initial work* - [Paulinguis993](https://github.com/Paulinguis993)
