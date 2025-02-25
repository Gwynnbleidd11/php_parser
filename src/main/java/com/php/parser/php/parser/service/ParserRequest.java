package com.php.parser.php.parser.service;

public record ParserRequest(
        String input,
        UserChoice userStrategy
) {
}
