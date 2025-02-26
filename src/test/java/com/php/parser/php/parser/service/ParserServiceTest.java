package com.php.parser.php.parser.service;

import com.php.parser.php.parser.strategy.OutputContext;
import com.php.parser.php.parser.strategy.PhpOutput;
import com.php.parser.php.parser.strategy.SqlOutput;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class ParserServiceTest {

    @Test
    void shouldProcessVarDumpStringPhp() throws IOException {
        //Given
        String inputString = Files.readString(Path.of("src/test/resources/test_input_1"),
                StandardCharsets.UTF_8);
        String outputString = Files.readString(Path.of("src/test/resources/test_output_1"),
                StandardCharsets.UTF_8);
        OutputContext parseContext = new OutputContext(new PhpOutput());
        ParserService service = new ParserService(parseContext);

        //When
        String testOutput = service.processVarDumpString(inputString, UserChoice.PHP);

        //Then
        assertEquals(
                outputString.replaceAll("\\r\\n?", "\n"),
                testOutput.replaceAll("\\r\\n?", "\n")
        );
    }

    @Test
    void shouldProcessVarDumpStringSql() throws IOException {
        //Given
        String inputString = Files.readString(Path.of("src/test/resources/test_input_1"),
                StandardCharsets.UTF_8);
        String outputString = Files.readString(Path.of("src/test/resources/test_sql_output_1"),
                StandardCharsets.UTF_8);
        OutputContext parseContext = new OutputContext(new SqlOutput());
        ParserService service = new ParserService(parseContext);

        //When
        String testOutput = service.processVarDumpString(inputString, UserChoice.SQL);

        //Then
        assertEquals(
                outputString.replaceAll("\\r\\n?", "\n"),
                testOutput.replaceAll("\\r\\n?", "\n")
        );
    }
}