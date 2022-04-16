package org.eagleinvsys.test.converters;

import org.eagleinvsys.test.converters.impl.CsvConverter;
import org.eagleinvsys.test.converters.impl.StandardCsvConverter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class StandardCsvConverterTests extends Assert {
    AutoCloseable openMocks;
    private ByteArrayOutputStream buffer;
    private StandardCsvConverter standardCsvConverter;
    @Spy
    private CsvConverter csvConverter;


    @Before
    public void initMocks() {
        buffer = new ByteArrayOutputStream();
        openMocks = MockitoAnnotations.openMocks(this);
        standardCsvConverter = new StandardCsvConverter(csvConverter);
    }

    @Test
    public void checkStandardCsvConverter() {
        String res = "header1,header2,header3\n" +
                "val1,1lav,l1av\n" +
                "val2,2lav,l2av\n" +
                "val3,3lav,l3av\n";

        standardCsvConverter.convert(List.of(Map.of(
                "header1", "val1",
                "header2", "1lav",
                "header3", "l1av"
        ), Map.of(
                "header1", "val2",
                "header2", "2lav",
                "header3", "l2av"
        ), Map.of(
                "header1", "val3",
                "header2", "3lav",
                "header3", "l3av"
        )), buffer);
        verify(csvConverter, atLeastOnce()).convert(any(ConvertibleCollection.class), any(OutputStream.class));
        assertEquals(res, buffer.toString(StandardCharsets.UTF_8));
    }

    @After
    public void afterMethod() throws Exception {
        openMocks.close();
    }
}