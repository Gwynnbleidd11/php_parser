package com.php.parser.php.parser.phpclass;

public class FieldDefinition {

    private String name;
    private String type;
    private String capitalizedName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
