package com.php.parser.php.parser.service;

import com.php.parser.php.parser.strategy.OutputContext;
import com.php.parser.php.parser.strategy.PhpClassOutput;
import com.php.parser.php.parser.strategy.PhpOutput;
import com.php.parser.php.parser.strategy.SqlOutput;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ParserService {

    private final OutputContext context;

    public ParserService(OutputContext context) {
        this.context = context;
    }

    public String processVarDumpString(final String inputString, final UserChoice userChoice) throws IOException {
        switch (userChoice) {
            case PHP -> context.setParseStrategy(new PhpOutput());
            case SQL -> context.setParseStrategy(new SqlOutput());
            case PHP_CLASS -> context.setParseStrategy(new PhpClassOutput());
        }
        return context.parseVarDump(inputString);
    }
}
