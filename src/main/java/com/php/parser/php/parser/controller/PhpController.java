package com.php.parser.php.parser.controller;

import com.php.parser.php.parser.service.ParserService;
import com.php.parser.php.parser.service.ParserRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("/php_parser")
public class PhpController {

    private final ParserService phpService;

    public PhpController(ParserService parserService) {
        this.phpService = parserService;
    }

    @PostMapping(consumes = MediaType.ALL_VALUE)
    public ResponseEntity<String> parseVarDumpString(@RequestBody ParserRequest request) throws IOException {
        return ResponseEntity.ok(phpService.processVarDumpString(request.input(), request.userStrategy()));
    }
}
