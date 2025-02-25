package com.php.parser.php.parser.service;

import com.php.parser.php.parser.strategy.ParserContext;
import com.php.parser.php.parser.strategy.PhpParser;
import com.php.parser.php.parser.strategy.SqlParser;
import org.springframework.stereotype.Service;

@Service
public class PhpService {

//    private final PhpParser parser;
    private final ParserContext context;

    public PhpService(ParserContext context) {
        this.context = context;
    }

    public String processVarDumpString(final String inputString, final UserChoice userChoice) {
        switch (userChoice) {
            case PHP -> context.setParseStrategy(new PhpParser());
            case SQL -> context.setParseStrategy(new SqlParser());
        }
        return context.parseVarDump(inputString);

//        PhpValue parsedArray = parser.parseVarDumpString(inputString);
//        return parser.toPhpCode(parsedArray);
    }
}
