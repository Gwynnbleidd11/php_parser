package com.php.parser.php.parser.phpclass;

import java.util.List;

public class PhpClassDefinition {

    private String className;
    private List<FieldDefinition> fields;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public List<FieldDefinition> getFields() {
        return fields;
    }

    public void setFields(List<FieldDefinition> fields) {
        this.fields = fields;
    }
}
