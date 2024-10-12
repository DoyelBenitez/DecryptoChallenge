package com.decrypto.challenge.common._core.deseserializers;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.io.IOException;

/**
 * @Author dbenitez
 */
public class SimpleGrantedAuthorityDeserializer extends StdDeserializer<SimpleGrantedAuthority> {

    public SimpleGrantedAuthorityDeserializer() {
        this(null);
    }

    public SimpleGrantedAuthorityDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public SimpleGrantedAuthority deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JacksonException {
        JsonNode node = jp.getCodec().readTree(jp);
        String authority = node.get("authority").asText();
        return new SimpleGrantedAuthority(authority);
    }
}
