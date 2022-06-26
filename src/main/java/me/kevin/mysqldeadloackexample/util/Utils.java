package me.kevin.mysqldeadloackexample.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Utils {
    private static ObjectMapper mObjectMapper = null;

    public static ObjectMapper getObjectMapper() {
        if (mObjectMapper == null) {
            mObjectMapper = new ObjectMapper();
            mObjectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mObjectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
            mObjectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mObjectMapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);
            mObjectMapper.registerModule(new JavaTimeModule());
            mObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        }
        return mObjectMapper;
    }

    public static String toJson(Object object) {
        ObjectMapper objectMapper = getObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return json;
    }
}
