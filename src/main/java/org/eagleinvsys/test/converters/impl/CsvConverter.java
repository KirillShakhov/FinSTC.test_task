package org.eagleinvsys.test.converters.impl;

import org.eagleinvsys.test.converters.Converter;
import org.eagleinvsys.test.converters.ConvertibleCollection;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.stream.Collectors;


public class CsvConverter implements Converter {
    private final String delimiter;
    private final Charset codepage;

    public CsvConverter() {
        this.delimiter = ",";
        this.codepage = StandardCharsets.UTF_8;
    }

    public CsvConverter(String delimiter) {
        this.delimiter = delimiter;
        this.codepage = StandardCharsets.UTF_8;
    }

    public CsvConverter(String delimiter, Charset codepage) {
        this.delimiter = delimiter;
        this.codepage = codepage;
    }

    /**
     * Converts given {@link ConvertibleCollection} to CSV and outputs result as a text to the provided {@link OutputStream}
     *
     * @param collectionToConvert collection to convert to CSV format
     * @param outputStream        output stream to write CSV conversion result as text to
     */
    @Override
    public void convert(ConvertibleCollection collectionToConvert, OutputStream outputStream) {
        final StringBuilder sb = new StringBuilder();

        Collection<String> headers = collectionToConvert.getHeaders();
        sb.append(String.join(delimiter, headers)).append(System.getProperty("line.separator"));


        collectionToConvert.getRecords().forEach((convertibleMessage) -> {
            String str = headers.stream()
                    .map(convertibleMessage::getElement)
                    .collect(Collectors.joining(delimiter));
            sb.append(str).append(System.getProperty("line.separator"));
        });

        try {
            outputStream.write(sb.toString().getBytes(codepage));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}