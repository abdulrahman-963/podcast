package com.podcast.podcast.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * Json utility
 * convert object to jsn and json to object
 */
@Slf4j
public abstract class JsonUtil {

    private JsonUtil() {
    }

    public static String toJson(ObjectMapper objectMapper, Object obj) {
        if (obj == null) {
            return null;
        }

        if (Objects.isNull(objectMapper)) {
            objectMapper = new ObjectMapper();
        }
        objectMapper.registerModule(new JavaTimeModule());
        String jsonStr = null;
        try {
            jsonStr = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
        }

        return jsonStr;
    }

    public static <T> T toObject(ObjectMapper objectMapper, String jsonData, Class<T> tClazz) {
        if (jsonData == null) {
            return null;
        }

        T t = null;
        try {
            t = objectMapper.readValue(jsonData.getBytes(StandardCharsets.UTF_8), tClazz);
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        return t;
    }


}
