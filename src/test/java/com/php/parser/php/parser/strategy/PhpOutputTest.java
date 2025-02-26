package com.php.parser.php.parser.strategy;

import com.php.parser.php.parser.php.PhpValue;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.php.parser.php.parser.strategy.ParseHelper.parseVarDumpString;
import static org.junit.jupiter.api.Assertions.*;

class PhpOutputTest {

    private static void commonCode(String expectedInput, String expectedOutput) throws IOException {
        //Given
        String inputString = Files.readString(Path.of(expectedInput),
                StandardCharsets.UTF_8);
        String outputString = Files.readString(Path.of(expectedOutput),
                StandardCharsets.UTF_8);
        PhpOutput parser = new PhpOutput();

        //When
        PhpValue parsedArray = parseVarDumpString(inputString);
        String phpCode = parser.toPhpCode(parsedArray);

        //Then
        assertEquals(
                outputString.replaceAll("\\r\\n?", "\n"),
                phpCode.replaceAll("\\r\\n?", "\n")
        );
    }

    @Test
    void shouldParsePHPOutputToPHPCode() throws IOException {
        commonCode("src/test/resources/test_input_1",
                "src/test/resources/test_output_1");
    }

    @Test
    void shouldParseSingleLineComplexString() throws IOException {
        commonCode("src/test/resources/test_input_2_single_line_complex",
                "src/test/resources/test_output_2_single_line_complex");
    }

    @Test
    void shouldParseEmptyArray() throws IOException {
        commonCode("src/test/resources/test_input_3_empty_array",
                "src/test/resources/test_output_3_empty_array");
    }

    @Test
    void shouldParseSingleKey() throws IOException {
        commonCode("src/test/resources/test_input_4_single_key",
                "src/test/resources/test_output_4_single_key");
    }

    @Test
    void shouldParseArrayWithMixedDataTypes() throws IOException {
        commonCode("src/test/resources/test_input_5_mixed_data_types",
                "src/test/resources/test_output_5_mixed_data_types");
    }

    @Test
    void shouldParseArrayWithNumericIndexes() throws IOException {
        commonCode("src/test/resources/test_input_6_numeric_indexed_array",
                "src/test/resources/test_output_6_numeric_indexed_array");
    }

    @Test
    void shouldParseSingleLine() throws IOException {
        commonCode("src/test/resources/test_input_7_single_line_simple",
                "src/test/resources/test_output_7_single_line_simple");
    }

}