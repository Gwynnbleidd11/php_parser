package com.php.parser.php.parser.strategy;

import com.php.parser.php.parser.php.PhpArray;
import com.php.parser.php.parser.php.PhpObject;
import com.php.parser.php.parser.php.PhpValue;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.php.parser.php.parser.strategy.ParseHelper.parseVarDumpString;

@Component
@Primary
public class PhpOutput implements OutputStrategy {

    static int index;

    @Override
    public String parseVarDump(String input) {
        return toPhpCode(parseVarDumpString(input));
    }

    public static PhpValue parseValue(List<String> lines) {
        String line = lines.get(index).trim();
        if (line.startsWith("array(")) {
            return parseArray(lines);
        } else if (line.startsWith("string(")) {
            Pattern pattern = Pattern.compile("string\\(\\d+\\) \"(.*?)\"");
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                index++;
                return new PhpObject(m.group(1));
            }
        } else if (line.startsWith("int(")) {
            Pattern pattern = Pattern.compile("int\\((\\d+)\\)");
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                index++;
                return new PhpObject(Integer.parseInt(m.group(1)));
            }
        } else if (line.startsWith("bool(")) {
            Pattern pattern = Pattern.compile("bool\\((true|false)\\)");
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                index++;
                return new PhpObject(Boolean.parseBoolean(m.group(1)));
            }
        } else if (line.startsWith("float(")) {
            Pattern pattern = Pattern.compile("float\\((\\d+\\.\\d+)\\)");
            Matcher m = pattern.matcher(line);
            if (m.find()) {
                index++;
                return new PhpObject(Double.parseDouble(m.group(1)));
            }
        } else if (line.startsWith("NULL")) {
            index++;
            return new PhpObject(null);
        }
        index++;
        return new PhpObject(null);
    }

    private static PhpArray parseArray(List<String> lines) {
        index++;
        PhpArray array = new PhpArray();

        while (index < lines.size() && lines.get(index).trim().isEmpty()) {
            index++;
        }

        while (index < lines.size()) {
            String line = lines.get(index).trim();
            if (line.equals("}")) {
                index++;
                return array;
            }
            Pattern keyPattern = Pattern.compile("\\[(\"(.+?)\"|(\\d+))]=>");
            Matcher m = keyPattern.matcher(line);
            if (m.find()) {
                Object key;
                if (m.group(2) != null) {
                    key = m.group(2);
                } else {
                    key = Integer.parseInt(m.group(3));
                }
                index++;
                PhpValue value = parseValue(lines);
                array.put(key, value);
            } else {
                index++;
            }
        }
        return array;
    }

    public String toPhpCode(PhpValue value) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?php\n\n");
        sb.append("$array = ");
        sb.append(value.toPhpString(0));
        sb.append(";\n\n");
        sb.append("var_dump($array);");
        return sb.toString();
    }
}
