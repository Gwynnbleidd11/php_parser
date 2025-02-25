package com.php.parser.php.parser.strategy;

import org.springframework.stereotype.Component;

@Component
public class SqlParser implements ParseStrategy {

    @Override
    public String parseVarDump(String input) {
        return "Printing some text for test";
    }
}
