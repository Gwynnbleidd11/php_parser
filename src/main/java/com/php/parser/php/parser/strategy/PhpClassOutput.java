package com.php.parser.php.parser.strategy;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import com.php.parser.php.parser.phpclass.FieldDefinition;
import com.php.parser.php.parser.phpclass.PhpClassDefinition;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhpClassOutput implements OutputStrategy {

    @Override
    public String parseVarDump(String input) throws IOException {
        if (input.trim().startsWith("array(0)")) {
            return "No data!";
        }
        PhpClassDefinition classDefinition = new PhpClassDefinition();
        Pattern classNamePattern = Pattern.compile("\\[\"(\\w+)\"\\]\\s*=>");
        Matcher classNameMatcher = classNamePattern.matcher(input);
        if (classNameMatcher.find()) {
            classDefinition.setClassName(classNameMatcher.group(1));
        }

        Pattern fieldPattern = Pattern.compile(
                "\\[\"(\\w+)\"\\]\\s*=>\\s*(\\w+)\\([^)]*\\)"
        );
        Matcher fieldMatcher = fieldPattern.matcher(input);

        Map<String, String> fieldTypes = new LinkedHashMap<>();
        while (fieldMatcher.find()) {
            String fieldName = fieldMatcher.group(1);
            if (fieldName.equals(classDefinition.getClassName())) {
                continue;
            }
            String typeIndicator = fieldMatcher.group(2);
            String phpType = mapPhpType(typeIndicator);
            if (!fieldTypes.containsKey(fieldName)) {
                fieldTypes.put(fieldName, phpType);
            }
        }

        List<FieldDefinition> fields = new ArrayList<>();
        for (Map.Entry<String, String> entry : fieldTypes.entrySet()) {
            FieldDefinition fieldDefinition = new FieldDefinition();
            fieldDefinition.setName(entry.getKey());
            fieldDefinition.setType(entry.getValue());
            fields.add(fieldDefinition);
        }
        classDefinition.setFields(fields);
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("templates/phpclass/phpClass.mustache");
        StringWriter writer = new StringWriter();
        mustache.execute(writer, classDefinition).flush();
        return writer.toString();
    }

    private String mapPhpType(String typeIndicator) {
        return switch (typeIndicator) {
            case "string" -> "string";
            case "int" -> "int";
            case "bool" -> "bool";
            case "float" -> "float";
            default -> "mixed";
        };
    }
}
