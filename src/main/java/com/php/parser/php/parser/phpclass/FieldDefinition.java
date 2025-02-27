package com.php.parser.php.parser.phpclass;

public class FieldDefinition {

    private String name;
    private String type;
    private String capitalizedName;
    private String separator = "";

    public String getName() {
        return name;
    }

    public void setName(String name) {
        String lowerName = name.toLowerCase();
        StringBuilder formattedName = new StringBuilder();
        boolean capitalizeNext = false;

        for (char c : lowerName.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else {
                formattedName.append(capitalizeNext ? Character.toUpperCase(c) : Character.toLowerCase(c));
                capitalizeNext = false;
            }
        }

        this.name = formattedName.toString();
        this.capitalizedName = name.substring(0, 1).toUpperCase() + name.substring(1).toLowerCase();
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCapitalizedName() {
        return capitalizedName;
    }

    public String getSeparator() {
        return separator;
    }

    public void setSeparator(String separator) {
        this.separator = separator;
    }
}
