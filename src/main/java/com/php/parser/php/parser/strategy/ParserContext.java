package com.php.parser.php.parser.strategy;

import org.springframework.stereotype.Component;

@Component
public class ParserContext {

    private ParseStrategy parseStrategy;

    public ParserContext(ParseStrategy parseStrategy) {
        this.parseStrategy = parseStrategy;
    }

    public void setParseStrategy(ParseStrategy parseStrategy) {
        this.parseStrategy = parseStrategy;
    }

    public ParseStrategy getParseStrategy() {
        return parseStrategy;
    }

    public String parseVarDump(String input) {
        return parseStrategy.parseVarDump(input);
    }
}
