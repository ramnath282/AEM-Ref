package com.mattel.ecomm.coreservices.core.enums;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

/**
 * Singleton for {@link ObjectMapper} needed for serialization and
 * de-serialization of objects in Services.
 */
public enum ResourceMapper {
    INSTANCE;
    private ObjectMapper mapper;

    /**
     * Constructor
     *
     * To configure {@link ObjectMapper} properties
     */
    private ResourceMapper() {
        mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    /**
     * @return The {@link ObjectMapper} instance.
     */
    public static ObjectMapper getInstance() {
        return ResourceMapper.INSTANCE.getMapper();
    }

    /**
     * @return A new {@link ObjectReader} instance for given {@code type}.
     */
    public static ObjectReader getReaderInstance(Class<?> type) {
        return ResourceMapper.INSTANCE.getMapper().readerFor(type);
    }

    /**
     * @return A new {@link ObjectWriter} instance for given {@code type}.
     */
    public static ObjectWriter getWriterInstance(Class<?> type) {
        return ResourceMapper.INSTANCE.getMapper().writerFor(type);
    }

    private ObjectMapper getMapper() {
        return mapper;
    }
}
