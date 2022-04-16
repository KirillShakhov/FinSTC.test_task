package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CsvConverterTests extends Assert {
    AutoCloseable openMocks;
    private ByteArrayOutputStream buffer;
    private CsvConverter csvConverter;
    @Mock
    private ConvertibleCollection collectionToConvert;

    @Before
    public void initMocks() {
        buffer = new ByteArrayOutputStream();
        csvConverter = new CsvConverter();
        openMocks = MockitoAnnotations.openMocks(this);
    }


    @Test
    public void checkCsvConverter() {
        when(collectionToConvert.getHeaders()).thenReturn(Arrays.asList("header1", "header2", "header3"));
        Map<String, String> test1 = Map.of(
                "header1", "val1",
                "header2", "1lav",
                "header3", "l1av"
        );
        Map<String, String> test2 = Map.of(
                "header1", "val2",
                "header2", "2lav",
                "header3", "l2av"
        );
        Map<String, String> test3 = Map.of(
                "header1", "val3",
                "header2", "3lav",
                "header3", "l3av"
        );
        when(collectionToConvert.getRecords()).thenReturn(List.of(test1::get, test2::get, test3::get));

        String res = "header1,header2,header3\n" +
                "val1,1lav,l1av\n" +
                "val2,2lav,l2av\n" +
                "val3,3lav,l3av\n";


        csvConverter.convert(collectionToConvert, buffer);
        assertEquals(res, buffer.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void checkCsvConverterNullElement() {
        when(collectionToConvert.getHeaders()).thenReturn(Arrays.asList("header1", "header2", "header3"));
        Map<String, String> test1 = Map.of(
                "header1", "val1",
                "header2", "1lav"
        );
        Map<String, String> test2 = Map.of(
                "header1", "val2",
                "header2", "2lav"
        );
        Map<String, String> test3 = Map.of(
                "header1", "val3",
                "header2", "3lav"
        );
        when(collectionToConvert.getRecords()).thenReturn(List.of(test1::get, test2::get, test3::get));

        String res = "header1,header2,header3\n" +
                "val1,1lav,null\n" +
                "val2,2lav,null\n" +
                "val3,3lav,null\n";


        csvConverter.convert(collectionToConvert, buffer);
        assertEquals(res, buffer.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void checkCsvConverterNewCharset() {
        csvConverter = new CsvConverter("#", StandardCharsets.ISO_8859_1);
        when(collectionToConvert.getHeaders()).thenReturn(Arrays.asList("header1", "header2", "header3"));
        Map<String, String> test1 = Map.of(
                "header1", "val1",
                "header2", "1lav"
        );
        Map<String, String> test2 = Map.of(
                "header1", "val2",
                "header2", "2lav"
        );
        Map<String, String> test3 = Map.of(
                "header1", "val3",
                "header2", "3lav"
        );
        when(collectionToConvert.getRecords()).thenReturn(List.of(test1::get, test2::get, test3::get));

        String res = "header1#header2#header3\n" +
                "val1#1lav#null\n" +
                "val2#2lav#null\n" +
                "val3#3lav#null\n";


        csvConverter.convert(collectionToConvert, buffer);
        assertEquals(res, buffer.toString(StandardCharsets.ISO_8859_1));
    }

    @Test
    public void checkCsvConverterDelimiter() {
        csvConverter = new CsvConverter(";");
        when(collectionToConvert.getHeaders()).thenReturn(Arrays.asList("header1", "header2", "header3"));
        Map<String, String> test1 = Map.of(
                "header1", "val1",
                "header2", "1lav",
                "header3", "l1av"
        );
        Map<String, String> test2 = Map.of(
                "header1", "val2",
                "header2", "2lav",
                "header3", "l2av"
        );
        Map<String, String> test3 = Map.of(
                "header1", "val3",
                "header2", "3lav",
                "header3", "l3av"
        );
        when(collectionToConvert.getRecords()).thenReturn(List.of(test1::get, test2::get, test3::get));

        String res = "header1;header2;header3\n" +
                "val1;1lav;l1av\n" +
                "val2;2lav;l2av\n" +
                "val3;3lav;l3av\n";


        csvConverter.convert(collectionToConvert, buffer);
        assertEquals(res, buffer.toString(StandardCharsets.UTF_8));
    }

    @Test
    public void checkCsvConverterDelimiterEmpty() {
        csvConverter = new CsvConverter(";", StandardCharsets.UTF_8);
        when(collectionToConvert.getHeaders()).thenReturn(List.of());

        when(collectionToConvert.getRecords()).thenReturn(List.of());

        csvConverter.convert(collectionToConvert, buffer);
        assertEquals("\n", buffer.toString(StandardCharsets.UTF_8));
    }

    @After
    public void afterMethod() throws Exception {
        openMocks.close();
    }
}