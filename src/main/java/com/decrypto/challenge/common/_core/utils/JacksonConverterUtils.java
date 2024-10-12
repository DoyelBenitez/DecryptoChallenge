package com.decrypto.challenge.common._core.utils;

import com.decrypto.challenge.common._core.deseserializers.SimpleGrantedAuthorityDeserializer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dbenitez
 */
@Getter
@AllArgsConstructor
@Component
public class JacksonConverterUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static {
        SimpleModule module = new SimpleModule();
        module.addDeserializer(SimpleGrantedAuthority.class, new SimpleGrantedAuthorityDeserializer());
        objectMapper.registerModule(module);
    }

    public static String toJson(Object object) throws JsonProcessingException {
        return objectMapper.writeValueAsString(object);
    }

    public static Object toJson(Object object, Class<?> classDto) throws JsonProcessingException {
        return objectMapper.convertValue(object, classDto);
    }

    public static <T> T fromJsonString(String jsonString, Class<?> clazz) throws JsonProcessingException {
        return objectMapper.readValue(jsonString, objectMapper.getTypeFactory().constructType(clazz));
    }

    public static Object convertTokenJacksonAObjeto(Object data, Class<?> classDto) throws JsonProcessingException {
        if (data instanceof List) {
            List<Object> list = new ArrayList<>();
            for (Object elemento : (List<?>) data) {
                list.add(objectMapper.convertValue(elemento, classDto));
            }
            return list;
        } else {
            return objectMapper.convertValue(data, classDto);
        }
    }
}
