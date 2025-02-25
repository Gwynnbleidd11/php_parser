package com.php.parser.php.parser.php;

public class PhpObject implements PhpValue {

    private final Object value;

    public PhpObject(Object value) {
        this.value = value;
    }

    @Override
    public String toPhpString(int indent) {
        if (value == null) {
            return "null";
        } else if (value instanceof String) {
            return "'" + value + "'";
        } else {
            return value.toString();
        }
    }

    public Object getValue() {
        return value;
    }
}
