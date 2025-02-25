package com.php.parser.php.parser.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.php.parser.php.parser.service.ParserRequest;
import com.php.parser.php.parser.service.ParserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static com.php.parser.php.parser.service.UserChoice.PHP;
import static com.php.parser.php.parser.service.UserChoice.SQL;

@SpringJUnitWebConfig
@WebMvcTest(PhpController.class)
class PhpControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ParserService parserService;

    @Test
    void shouldParseVarDumpStringPhp() throws Exception {

        String inputString = Files.readString(
                Path.of("src/test/resources/test_input_1"), StandardCharsets.UTF_8);
        ParserRequest request = new ParserRequest(inputString, PHP);

        ObjectMapper mapper = new ObjectMapper();
        String jsonRequest = mapper.writeValueAsString(request);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/php_parser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldParseVarDumpStringSql() throws Exception {

        String inputString = Files.readString(
                Path.of("src/test/resources/test_input_1"), StandardCharsets.UTF_8);
        ParserRequest request = new ParserRequest(inputString, SQL);

        ObjectMapper mapper = new ObjectMapper();
        String jsonRequest = mapper.writeValueAsString(request);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/php_parser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonRequest))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}