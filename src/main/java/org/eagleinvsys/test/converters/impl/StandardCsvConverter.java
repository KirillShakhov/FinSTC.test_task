package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.ConvertibleCollection;
import org.eagleinvsys.test.converters.ConvertibleMessage;
import org.eagleinvsys.test.converters.StandardConverter;

import java.io.OutputStream;
import java.util.*;

public class StandardCsvConverter implements StandardConverter {

    private final CsvConverter csvConverter;

    public StandardCsvConverter(CsvConverter csvConverter) {
        this.csvConverter = csvConverter;
    }

    /**
     * Converts given {@link List<Map>} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format. All maps must have the same set of keys
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(List<Map<String, String>> collectionToConvert, OutputStream outputStream) {
        csvConverter.convert(convertMapToConvertibleCollection(collectionToConvert), outputStream);
    }

    private Collection<String> getHeaders(List<Map<String, String>> collectionToConvert) {
        Set<String> headers = new TreeSet<>();
        collectionToConvert.forEach(map -> headers.addAll(map.keySet()));
        return headers;
    }

    private Iterable<ConvertibleMessage> getRecords(List<Map<String, String>> collectionToConvert) {
        LinkedList<ConvertibleMessage> records = new LinkedList<>();
        collectionToConvert.forEach(map -> records.add(map::get));
        return records;
    }

    private ConvertibleCollection convertMapToConvertibleCollection(List<Map<String, String>> collectionToConvert) {
        Collection<String> header = getHeaders(collectionToConvert);
        Iterable<ConvertibleMessage> records = getRecords(collectionToConvert);

        return new ConvertibleCollection() {
            @Override
            public Collection<String> getHeaders() {
                return header;
            }

            @Override
            public Iterable<ConvertibleMessage> getRecords() {
                return records;
            }
        };
    }
}