package com.php.parser.php.parser.strategy;

import java.io.IOException;

public interface OutputStrategy {

    String parseVarDump(String input) throws IOException;
}
