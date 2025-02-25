package com.php.parser.php.parser.strategy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class SqlParserTest {

    private static void commonCode(String expectedInput, String expectedOutput) throws IOException {
        //Given
        String inputString = Files.readString(Path.of(expectedInput),
                StandardCharsets.UTF_8);
        String outputString = Files.readString(Path.of(expectedOutput),
                StandardCharsets.UTF_8);
//        SqlParser parser = new SqlParser(new VarDumpParser());
        SqlParser parser = new SqlParser(new PhpParser());

        //When
        String parsedToSql = parser.parseVarDump(inputString);

        System.err.println(parsedToSql);
        System.err.println(outputString);

        //Then
        assertEquals(
                outputString.replaceAll("\\r\\n?", "\n"),
                parsedToSql.replaceAll("\\r\\n?", "\n")
        );
    }

    @Test
    void shouldParseToSqlInsertQueryStringSingleKey() throws IOException {
        //Given
        commonCode("src/test/resources/test_input_4_single_key",
                "src/test/resources/test_sql_output_4_single_key");
    }

    @Test
    void shouldParseToSqlInsertQueryStringMixedDataTypes() throws IOException {
        //Given
        commonCode("src/test/resources/test_input_5_mixed_data_types",
                "src/test/resources/test_sql_output_5_mixed_data_types");
    }

    @Test
    void shouldParseToSqlInsertQueryStringNumericIndexedArray() throws IOException {
        //Given
        commonCode("src/test/resources/test_input_6_numeric_indexed_array",
                "src/test/resources/test_sql_output_6_numeric_indexed_array");
    }

    @Test
    void shouldParseToSqlInsertQueryString() throws IOException {
        //Given
        commonCode("src/test/resources/test_input_1",
                "src/test/resources/test_sql_output_1");
    }

    @Test
    void shouldParseToSqlInsertQueryStringSingleLine() throws IOException {
        //Given
        commonCode("src/test/resources/test_input_7_single_line_simple",
                "src/test/resources/test_sql_output_7_single_line_simple");
    }

    @Test
    void shouldParseToSqlInsertQueryStringSingleLineComplex() throws IOException {
        //Given
        commonCode("src/test/resources/test_input_2_single_line_complex",
                "src/test/resources/test_sql_output_2_single_line_complex");
    }

    @Test
    void shouldParseToSqlInsertQueryStringEmptyArray() throws IOException {
        //Given
        commonCode("src/test/resources/test_input_3_empty_array",
                "src/test/resources/test_sql_output_3_empty_array");
    }

}