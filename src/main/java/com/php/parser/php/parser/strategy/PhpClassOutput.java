package com.php.parser.php.parser.strategy;

public class PhpClassOutput implements OutputStrategy {
    @Override
    public String parseVarDump(String input) {
        return "PHP CLASS!";
    }
}
