package com.php.parser.php.parser.php;

import java.util.LinkedHashMap;
import java.util.Map;

public class PhpArray implements PhpValue {

    private final Map<Object, PhpValue> elements = new LinkedHashMap<>();

    public void put(Object key, PhpValue value) {
        elements.put(key, value);
    }

    public Map<Object, PhpValue> getElements() {
        return elements;
    }

    @Override
    public String toPhpString(int indent) {
        StringBuilder sb = new StringBuilder();
        String indentStr = "    ".repeat(indent);
        if (elements.isEmpty()) {
            sb.append("[]");
        } else {
            sb.append("[\n");
            int count = 0;
            int size = elements.size();
            for (Map.Entry<Object, PhpValue> entry : elements.entrySet()) {
                sb.append(indentStr).append("    ");
                if (entry.getKey() instanceof String) {
                    sb.append("'").append(entry.getKey()).append("' => ");
                }
                sb.append(entry.getValue().toPhpString(indent + 1));
                count++;
                if (count < size) {
                    sb.append(",\n");
                } else {
                    sb.append("\n");
                }
            }
            sb.append(indentStr).append("]");
        }
        return sb.toString();
    }
}
