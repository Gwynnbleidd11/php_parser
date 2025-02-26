package com.php.parser.php.parser.strategy;

import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class OutputContext {

    private OutputStrategy outputStrategy;

    public OutputContext(OutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    public void setParseStrategy(OutputStrategy outputStrategy) {
        this.outputStrategy = outputStrategy;
    }

    public OutputStrategy getParseStrategy() {
        return outputStrategy;
    }

    public String parseVarDump(String input) throws IOException {
        return outputStrategy.parseVarDump(input);
    }
}
