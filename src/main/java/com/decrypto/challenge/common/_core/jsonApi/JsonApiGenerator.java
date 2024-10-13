package com.decrypto.challenge.common._core.jsonApi;

import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiId;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiRelationships;
import com.decrypto.challenge.common._core.jsonApi.anotations.JsonApiType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.StreamSupport;

/**
 * Generador de estructuras JSON API para objetos de transferencia de datos (DTO).
 * Este generador recorre los campos de un DTO para identificar atributos y relaciones,
 * y construye la estructura JSON API correspondiente.
 *
 * @Author dbenitez
 */
@Slf4j
public class JsonApiGenerator {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final Set<Long> includedIds = new HashSet<>();

    private final HashMap<String, HashSet<Long>> mapRepeated = new HashMap<String, HashSet<Long>>();

    private JsonApiGenerator() {}

    /**
     * Método de fábrica para crear una nueva instancia de JsonApiGenerator.
     *
     * @return Una nueva instancia de JsonApiGenerator.
     */
    public static JsonApiGenerator create() {
        return new JsonApiGenerator();
    }

    /**
     * Genera una respuesta JSON API basada en un objeto.
     *
     * @param object El objeto que se utilizará para generar el nodo JSON 'data'.
     * @return Un ObjectNode que representa la estructura JSON API, incluyendo 'data' e 'included' si es necesario.
     */
    public ObjectNode generateJsonApi(Object object) {
        return generateJsonApi(object, Optional.empty());
    }

    /**
     * Genera una respuesta JSON API que contiene solo un mensaje dentro del nodo 'meta'.
     *
     * @param message El mensaje a incluir en el nodo 'meta' del JSON API.
     * @return Un ObjectNode que representa la estructura JSON API con el mensaje en 'meta' y sin 'data'.
     */
    public ObjectNode generateJsonApi(String message) {
        return generateJsonApi(null, Optional.ofNullable(message));
    }

    /**
     * Genera una respuesta JSON API basada en un objeto y un mensaje opcional.
     *
     * @param object  El objeto para el nodo 'data'.
     * @param message Un mensaje opcional que se incluirá en el nodo 'meta' del JSON API.
     * @return Un ObjectNode que representa la estructura JSON API, incluyendo 'data', 'meta' y 'included' según corresponda.
     */
    public ObjectNode generateJsonApi(Object object, String message) {
        return generateJsonApi(object, Optional.ofNullable(message));
    }

    /**
     * Genera la estructura completa JSON API con soporte para objetos, colecciones, y mensajes adicionales.
     *
     * @param object  El objeto o colección de objetos para el nodo 'data'.
     * @param message Un mensaje opcional para el nodo 'meta'.
     * @return Un ObjectNode que representa la estructura completa JSON API, incluyendo 'data', 'meta', y 'included'.
     */
    private ObjectNode generateJsonApi(Object object, Optional<String> message) {
        try {
            ObjectNode jsonApiNode = objectMapper.createObjectNode();
            // Añade el mensaje al nodo 'meta' si está presente
            message.ifPresent(msg -> addMeta(msg, jsonApiNode));
            // Si no hay objeto, solo devuelve el nodo con 'meta'
            if (object == null) {
                return jsonApiNode;
            }
            ArrayNode dataArray = null;
            ObjectNode dataNode = object instanceof Collection ? null : jsonApiNode.putObject("data");

            // Procesa la colección o el objeto individual para agregar 'data' e 'included'
            if (object instanceof Collection) {
                dataArray = jsonApiNode.putArray("data");
                for (Object item : (Collection<?>) object) {
                    dataNode = dataArray.addObject();
                    this.processObjectOrMap(item, dataNode, jsonApiNode);
                }
            } else {
                this.processObjectOrMap(object, dataNode, jsonApiNode);
            }
            return jsonApiNode;
        } catch (IllegalAccessException ex) {
            log.error("Error en JsonApiGenerator: {}", ex.getMessage());
            return null;
        }
    }

    /**
     * Procesa un objeto o un Map genérico para añadir 'data' y 'included' al nodo JSON API.
     *
     * @param object       El objeto o Map a procesar.
     * @param dataNode     El nodo JSON donde se agregará el 'data' del objeto.
     * @param jsonApiNode  El nodo JSON raíz que contiene toda la respuesta JSON API.
     * @throws IllegalAccessException Si se produce un error de acceso durante la reflexión.
     */
    private void processObjectOrMap(Object object, ObjectNode dataNode, ObjectNode jsonApiNode) throws IllegalAccessException {
        if (object instanceof Map<?, ?>) {
            processMap((Map<?, ?>) object, dataNode);
        } else {
            ArrayNode includedNode = null;
            if (jsonApiNode.get("included") != null && !jsonApiNode.get("included").isEmpty()) {
                includedNode = (ArrayNode) jsonApiNode.get("included");
            } else {
                includedNode = jsonApiNode.putArray("included");
            }

            addIdAndType(object, dataNode);
            addAttributes(object, dataNode);
            this.processRelationships(object, dataNode, includedNode);
            if (jsonApiNode.get("included").isEmpty()) {
                // jsonApiNode.remove("included");
            }
        }
    }

    /**
     * Procesa un Map para convertir sus entradas en atributos JSON API dentro del nodo 'data'.
     *
     * @param map      El Map que contiene las claves y valores a incluir como atributos.
     * @param dataNode El nodo JSON donde se añadirán los atributos del Map.
     */
    private void processMap(Map<?, ?> map, ObjectNode dataNode) {
        dataNode.put("type", "info");
        ObjectNode attributesNode = dataNode.putObject("attributes");
        map.forEach((key, value) ->
                attributesNode.put(key.toString(), value != null ? value.toString() : "")
        );
    }


    /**
     * Agrega un nodo 'meta' con un mensaje personalizado al objeto JSON.
     * Este método es útil para incluir información adicional en el JSON API,
     * como mensajes de bienvenida o confirmaciones, sin interferir con los datos principales.
     *
     * @param message  El mensaje a incluir en el nodo 'meta'. Si es nulo o vacío, no se agregará el nodo.
     * @param metaNode El nodo JSON al que se añadirá el 'meta' con el mensaje. Este debe ser el nodo raíz o
     *                 un nodo relevante donde se necesita agregar el 'meta'.
     */
    private void addMeta(String message, ObjectNode metaNode) {
        if (message != null && !message.isEmpty()) {
            metaNode = metaNode.putObject("meta");
            metaNode.put("message", message);
        }
    }

    /**
     * Agrega los atributos del objeto principal al nodo JSON API, excluyendo aquellos que están anotados como relaciones.
     *
     * @param object      El objeto de transferencia de datos (DTO) cuyos atributos se agregarán.
     * @param dataNode El nodo donde se almacenarán los atributos del objeto.
     * @throws IllegalAccessException Si ocurre un error al acceder a los campos del objeto.
     */
    private void addAttributes(Object object, ObjectNode dataNode) throws IllegalAccessException {
        ObjectNode attributesNode = dataNode.putObject("attributes");
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!field.isAnnotationPresent(JsonApiRelationships.class)) {
                field.setAccessible(true);
                Object fieldValue = field.get(object);
                if (fieldValue != null) {
                    attributesNode.put(field.getName(), fieldValue.toString());
                }
            }
        }
    }

    /**
     * Agrega el ID y el tipo JSON API al nodo de datos del objeto principal.
     *
     * @param object      El objeto de transferencia de datos (DTO) cuyos campos ID y tipo se agregarán.
     * @param dataNode El nodo donde se almacenarán el ID y el tipo del objeto.
     * @throws IllegalAccessException Si ocurre un error al acceder al campo ID del objeto.
     */
    private void addIdAndType(Object object, ObjectNode dataNode) throws IllegalAccessException {
        Field idField = findFieldWithAnnotation(object.getClass(), JsonApiId.class);
        if (idField != null) {
            idField.setAccessible(true);
            Object idValue = idField.get(object);
            dataNode.put("id", idValue != null ? idValue.toString() : "");
        }
        dataNode.put("type", this.getNameJsonApiType(object.getClass()));
    }

    /**
     * Procesa las relaciones de un objeto DTO, agregando las relaciones encontradas al nodo 'relationships'
     * y los objetos relacionados al nodo 'included'.
     *
     * @param object            El objeto de transferencia de datos (DTO) que contiene las relaciones a procesar.
     * @param dataNode       El nodo donde se almacenarán las relaciones del objeto principal.
     * @param includedNode   El nodo 'included' donde se añadirán los objetos relacionados para cumplir con JSON API.
     * @throws IllegalAccessException Si ocurre un error al acceder a los campos del objeto a través de reflexión.
     */
    private void processRelationships(Object object, ObjectNode dataNode, ArrayNode includedNode) throws IllegalAccessException {
        ObjectNode relationshipsNode = objectMapper.createObjectNode();
        for (Field field : object.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(JsonApiRelationships.class)) {
                field.setAccessible(true);
                Object relatedObject = field.get(object);
                if (relatedObject != null) {
                    this.addRelationship(field, relatedObject, relationshipsNode, includedNode);
                }
            }
        }
        if (!relationshipsNode.isEmpty()) { //  && this.hasNoEmptyRelationships(relationshipsNode)
            dataNode.set("relationships", relationshipsNode);
        }
    }

    /**
     * Añade una relación al nodo de relaciones y procesa el nodo 'included' para incluir los objetos relacionados.
     *
     * @param field            El campo que representa la relación.
     * @param relatedObject    El objeto relacionado que se procesará.
     * @param relationshipsNode Nodo donde se almacenarán las relaciones del objeto principal.
     * @param includedNode     Nodo donde se añadirán los objetos relacionados.
     * @throws IllegalAccessException Si ocurre un error al acceder al campo del objeto relacionado.
     */
    private void addRelationship(Field field, Object relatedObject, ObjectNode relationshipsNode, ArrayNode includedNode) throws IllegalAccessException {
        String relationshipName = this.getNameJsonApiRelationships(field);
        if (relatedObject instanceof Collection) {
            ArrayNode relationshipArray = relationshipsNode.putObject(relationshipName).putArray("data");
            for (Object item : (Collection<?>) relatedObject) {
                this.processRelationshipAndInclude(item, relationshipArray, includedNode);
            }
        } else {
            ObjectNode relationshipDataNode = relationshipsNode.putObject(relationshipName).putObject("data");
            this.processRelationshipAndInclude(relatedObject, relationshipDataNode, includedNode);
        }
    }

    /**
     * Procesa cada relación y agrega los datos correspondientes al nodo 'included'.
     *
     * @param relatedObject     El objeto relacionado que será procesado.
     * @param relationshipNode  Nodo donde se almacenarán los datos de la relación.
     * @param includedNode      Nodo donde se incluirán los objetos relacionados.
     * @throws IllegalAccessException Si ocurre un error al acceder a los campos del objeto.
     */
    private void processRelationshipAndInclude(Object relatedObject, Object relationshipNode, ArrayNode includedNode) throws IllegalAccessException {
        Long id = null;
        String nameClazz = relatedObject.getClass().getSimpleName();
        try {
            Field field = relatedObject.getClass().getField("id");
            field.setAccessible(true);
            id = (Long) field.get(relatedObject);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            log.error("Error al acceder al campo 'id' del objeto relacionado: {}", e.getMessage());
        }
        if (!this.mapRepeated.containsKey(nameClazz)) {
            this.mapRepeated.put(nameClazz, new HashSet<>());
        }
        this.createRelationshipNode(relatedObject, relationshipNode);
        if (this.mapRepeated.get(nameClazz).add(id)) {
        ObjectNode includedItem = createIncludedItem(relatedObject, includedNode);
        ObjectNode attributesNode = includedItem.putObject("attributes");
        ObjectNode relationshipsNode = objectMapper.createObjectNode();
        this.processFields(relatedObject, attributesNode, relationshipsNode, includedNode);
            if (!relationshipsNode.isEmpty()) {
                includedItem.set("relationships", relationshipsNode);
            }
        }
    }

    /**
     * Crea y configura el nodo de relación para el objeto relacionado.
     *
     * @param relatedObject     El objeto relacionado que será procesado.
     * @param relationshipNode  Nodo donde se almacenarán los datos de la relación.
     * @return Un ObjectNode que representa el nodo de la relación configurado.
     * @throws IllegalAccessException Si ocurre un error al acceder a los campos del objeto.
     */
    private ObjectNode createRelationshipNode(Object relatedObject, Object relationshipNode) throws IllegalAccessException {
        Field idField = findFieldWithAnnotation(relatedObject.getClass(), JsonApiId.class);
        Object idValue = idField.get(relatedObject);
        ObjectNode relationshipDataNode = (relationshipNode instanceof ArrayNode)
                ? ((ArrayNode) relationshipNode).addObject()
                : (ObjectNode) relationshipNode;
        relationshipDataNode.put("id", idValue != null ? idValue.toString() : "");
        relationshipDataNode.put("type", this.getNameJsonApiType(relatedObject.getClass()));
        return relationshipDataNode;
    }

    /**
     * Crea un nodo 'included' para el objeto relacionado y lo configura con ID y tipo.
     *
     * @param relatedObject El objeto relacionado que se agregará al nodo 'included'.
     * @param includedNode  Nodo donde se incluirán los objetos relacionados.
     * @return Un ObjectNode que representa el nodo 'included' configurado.
     * @throws IllegalAccessException Si ocurre un error al acceder a los campos del objeto.
     */
    private ObjectNode createIncludedItem(Object relatedObject, ArrayNode includedNode) throws IllegalAccessException {
        Field idField = findFieldWithAnnotation(relatedObject.getClass(), JsonApiId.class);
        Object idValue = idField.get(relatedObject);
        ObjectNode includedItem = includedNode.addObject();
        includedItem.put("id", idValue != null ? idValue.toString() : "");
        includedItem.put("type", this.getNameJsonApiType(relatedObject.getClass()));
        return includedItem;
    }

    /**
     * Procesa los campos de un objeto, manejando tanto atributos como relaciones, y los agrega a los nodos correspondientes.
     *
     * @param relatedObject     El objeto relacionado cuyos campos serán procesados.
     * @param attributesNode    Nodo donde se almacenarán los atributos del objeto.
     * @param relationshipsNode Nodo donde se almacenarán las relaciones del objeto.
     * @param includedNode      Nodo donde se incluirán los objetos relacionados.
     * @throws IllegalAccessException Si ocurre un error al acceder a los campos del objeto.
     */
    private void processFields(Object relatedObject, ObjectNode attributesNode, ObjectNode relationshipsNode, ArrayNode includedNode) throws IllegalAccessException {
        for (Field field : relatedObject.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(JsonApiRelationships.class)) {
                this.processRelationshipField(field, relatedObject, relationshipsNode, includedNode);
            } else {
                this.processAttributeField(field, relatedObject, attributesNode);
            }
        }
    }

    /**
     * Procesa un campo que representa una relación y lo agrega al nodo de relaciones y al nodo 'included'.
     *
     * @param field             El campo que representa la relación.
     * @param relatedObject     El objeto relacionado que contiene el campo.
     * @param relationshipsNode Nodo donde se almacenarán las relaciones del objeto.
     * @param includedNode      Nodo donde se incluirán los objetos relacionados.
     * @throws IllegalAccessException Si ocurre un error al acceder al campo del objeto.
     */
    private void processRelationshipField(Field field, Object relatedObject, ObjectNode relationshipsNode, ArrayNode includedNode) throws IllegalAccessException {
        Object nestedObject = field.get(relatedObject);
        if (nestedObject != null) {
            if (nestedObject instanceof Collection) {
                ArrayNode nestedRelationshipArray = relationshipsNode.putObject(this.getNameJsonApiRelationships(field)).putArray("data");
                for (Object item : (Collection<?>) nestedObject) {
                    this.processRelationshipAndInclude(item, nestedRelationshipArray, includedNode);
                }
            } else {
                this.processRelationshipAndInclude(nestedObject, relationshipsNode.putObject(this.getNameJsonApiRelationships(field)).putObject("data"), includedNode);
            }
        }
    }

    /**
     * Procesa un campo que representa un atributo y lo agrega al nodo de atributos.
     *
     * @param field          El campo que representa el atributo.
     * @param relatedObject  El objeto relacionado que contiene el campo.
     * @param attributesNode Nodo donde se almacenarán los atributos del objeto.
     * @throws IllegalAccessException Si ocurre un error al acceder al campo del objeto.
     */
    private void processAttributeField(Field field, Object relatedObject, ObjectNode attributesNode) throws IllegalAccessException {
        Object fieldValue = field.get(relatedObject);
        if (fieldValue != null) {
            attributesNode.put(field.getName(), fieldValue.toString());
        }
    }

    /**
     * Encuentra un campo en la superclase de la clase dada que esté anotado con una anotación específica.
     *
     * @param clazz      La clase que se analizará.
     * @param annotation La anotación que se busca en los campos de la superclase.
     * @return El campo que contiene la anotación, o null si no se encuentra.
     */
    private Field findFieldWithAnnotation(Class<?> clazz, Class<? extends Annotation> annotation) {
        for (Field field : clazz.getSuperclass().getDeclaredFields()) {
            if (field.isAnnotationPresent(annotation)) {
                return field;
            }
        }
        return null;
    }

    /**
     * Obtiene el tipo JSON API de una clase según la anotación @JsonApiType.
     * Si la anotación está presente y tiene un valor, se devuelve en minúsculas;
     * de lo contrario, se utiliza el nombre simple de la clase.
     *
     * @param clazz La clase de la cual se obtendrá el tipo JSON API.
     * @return El nombre del tipo JSON API en minúsculas.
     */
    private String getNameJsonApiType(Class<?> clazz) {
        if (clazz.isAnnotationPresent(JsonApiType.class)) {
            JsonApiType annotation = clazz.getAnnotation(JsonApiType.class);
            if (!annotation.value().isEmpty()) {
                return annotation.value().toLowerCase();
            }
        }
        return clazz.getSimpleName().toLowerCase();
    }

    /**
     * Obtiene el nombre de la relación JSON API de un campo basado en la anotación @JsonApiRelationships.
     * Si la anotación tiene un valor, se devuelve en minúsculas; de lo contrario, se usa el nombre del campo.
     *
     * @param field El campo que se analizará para obtener el nombre de la relación.
     * @return El nombre de la relación JSON API en minúsculas.
     */
    private String getNameJsonApiRelationships(Field field) {
        if (field.isAnnotationPresent(JsonApiRelationships.class)) {
            JsonApiRelationships annotation = field.getAnnotation(JsonApiRelationships.class);
            if (!annotation.value().isEmpty()) {
                return annotation.value().toLowerCase();
            }
        }
        return field.getName().toLowerCase();
    }

    /**
     * Verifica si 'relationshipsNode' contiene alguna relación cuyo nodo 'data' no esté vacío.
     *
     * @param relationshipsNode el nodo de relaciones a verificar
     * @return true si hay al menos una relación con datos en el nodo 'data', de lo contrario, false
     */
    private Boolean hasNoEmptyRelationships(ObjectNode relationshipsNode) {
        // Recorre cada relación en 'relationshipsNode' y verifica que 'data' no esté vacío
        return relationshipsNode.fields().hasNext() && StreamSupport.stream(relationshipsNode.spliterator(), false)
                .anyMatch(field -> {
                    JsonNode dataNode = field.get("data");
                    return dataNode != null && dataNode.isArray() && !dataNode.isEmpty();
                });
    }

}
