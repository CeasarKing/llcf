package com.lin.llcf.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.List;

public class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public static String writeToString(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        }catch (Exception e) {
            return null;
        }
    }

    public static <T> T read(String jsonString, Class<T> cls) {
        try {
            return OBJECT_MAPPER.readValue(jsonString, cls);
        }catch (Exception e) {
            return null;
        }
    }

    public static <T> List<T> readList(String json, Class<T> cls) {
        TypeReference<List<T>> typeReference = new TypeReference<List<T>>() {};
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        }catch (Exception e) {
            return null;
        }
    }

}
