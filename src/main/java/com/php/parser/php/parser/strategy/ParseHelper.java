package com.php.parser.php.parser.strategy;

import com.php.parser.php.parser.php.PhpValue;

import java.util.Arrays;
import java.util.List;

import static com.php.parser.php.parser.strategy.PhpOutput.parseValue;

public class ParseHelper {

    public static PhpValue parseVarDumpString(String input) {
        PhpOutput.index = 0;
        List<String> lines = preprocessInput(input);
        return parseValue(lines);
    }

    private static List<String> preprocessInput(String input) {
        String[] rawLines = input.split("\n");
        if (rawLines.length == 1) {
            input = input
                    .replaceAll("(?<=\\{)", "\n")
                    .replaceAll("(?=\\})", "\n")
                    .replaceAll("(?<=\\})", "\n")
                    .replaceAll("(?<==>)", "\n")
                    .replaceAll("(?=\\[)", "\n")
                    .replaceAll("\\n+", "\n");
        }
        return Arrays.asList(input.split("\n"));
    }
}
