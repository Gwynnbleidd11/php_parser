package com.php.parser.php.parser.phpclass;

import java.util.List;

public class PhpClassDefinition {

    private String className;
    private List<FieldDefinition> fields;

    public String getClassName() {
        String formattedClassName = className;
        StringBuilder formattedName = new StringBuilder();
        boolean capitalizeNext = true;

        for (char c : formattedClassName.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else {
                formattedName.append(capitalizeNext ? Character.toUpperCase(c) : Character.toLowerCase(c));
                capitalizeNext = false;
            }
        }
        return formattedName.toString();
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }

    public void setFields(List<FieldDefinition> fields) {
        if (fields != null && !fields.isEmpty()) {
            fields.remove(0);
        }

        this.fields = fields;

        for (int i = 0; i < this.fields.size() - 1; i++) {
            this.fields.get(i).setSeparator(", ");
        }
    }
}
