package com.mattel.ecomm.coreservices.core.interfaces;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import com.mattel.ecomm.coreservices.core.exceptions.ServiceException;

public interface DataImporterService<I, O> {
    O readInputData(I requestBody, Map<String, Object> requestAttributes) throws ServiceException;

    default ByteArrayInputStream toByteArrayInputStream(final StringBuilder builder) {
        return new ByteArrayInputStream(builder.toString().getBytes(StandardCharsets.UTF_8));
    }

    default InputStream writeDataToStream(BufferedReader reader) throws IOException {
        final StringBuilder builder = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }

        return toByteArrayInputStream(builder);
    }

    default InputStream writeDataToStream(Reader reader) throws IOException {
        final StringBuilder builder = new StringBuilder();
        int numCharsRead;

        while ((numCharsRead = reader.read()) != -1) {
            builder.append(numCharsRead);
        }

        return toByteArrayInputStream(builder);
    }

    default InputStream writeDataToStream(Reader reader, char[] charBuffer) throws IOException {
        final StringBuilder builder = new StringBuilder();
        int numCharsRead;

        while ((numCharsRead = reader.read(charBuffer, 0, charBuffer.length)) != -1) {
            builder.append(charBuffer, 0, numCharsRead);
        }

        return toByteArrayInputStream(builder);
    }
}
