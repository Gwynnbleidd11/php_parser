package com.php.parser.php.parser.strategy;

import com.php.parser.php.parser.php.PhpArray;
import com.php.parser.php.parser.php.PhpObject;
import com.php.parser.php.parser.php.PhpValue;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class SqlParser implements ParseStrategy {

    private final PhpParser parser;

    public SqlParser(PhpParser parser) {
        this.parser = parser;
    }

    @Override
    public String parseVarDump(String inputString) {
        StringBuilder sqlString = new StringBuilder();
        PhpValue rootValue = parser.parseVarDumpString(inputString);
        PhpArray rootArray = (PhpArray) rootValue;

        if (rootArray.getElements().isEmpty()) {
            return "No data in var_dump() output to create SQL QUERY";
        }

        for (Map.Entry<Object, PhpValue> tableEntry : rootArray.getElements().entrySet()) {
            String tableName;
            PhpArray tableData;

            if (tableEntry.getValue() instanceof PhpArray tempArray) {
                Map<Object, PhpValue> innerElements = tempArray.getElements();
                if (innerElements.size() == 1) {
                    Object innerKey = innerElements.keySet().iterator().next();
                    PhpValue possibleTableData = innerElements.get(innerKey);
                    if (innerKey instanceof String && possibleTableData instanceof PhpArray) {
                        tableName = (String) innerKey;
                        tableData = (PhpArray) possibleTableData;
                    } else {
                        tableName = (String) tableEntry.getKey();
                        tableData = tempArray;
                    }
                } else {
                    tableName = (String) tableEntry.getKey();
                    tableData = tempArray;
                }
            } else {
                continue;
            }

            List<Map<String, PhpValue>> rowList = extractRows(tableData);

            Set<String> allColumnsSet = new LinkedHashSet<>();
            for (Map<String, PhpValue> row : rowList) {
                allColumnsSet.addAll(row.keySet());
            }
            List<String> allColumns = new ArrayList<>(allColumnsSet);

            sqlString.append("INSERT INTO ")
                    .append(tableName)
                    .append(" (")
                    .append(String.join(", ", allColumns))
                    .append(") VALUES ");
            List<String> rowStrings = new ArrayList<>();
            for (Map<String, PhpValue> row : rowList) {
                List<String> values = new ArrayList<>();
                for (String column : allColumns) {
                    values.add(extractSqlValue(row.get(column)));
                }
                rowStrings.add("(" + String.join(", ", values) + ")");
            }
            sqlString.append(String.join(",\n", rowStrings))
                    .append(";\n");
        }
        return sqlString.toString();
    }

    private PhpArray toArray(PhpValue value) {
        if (value instanceof PhpArray array) {
            return array;
        } else if (value instanceof PhpObject) {
            PhpArray array = new PhpArray();
            array.put("0", value);
            return array;
        }
        return null;
    }

    private List<Map<String, PhpValue>> extractRows(PhpArray tableData) {
        Map<Object, PhpValue> dataElements = tableData.getElements();
        boolean isRowCollection = isRowCollection(dataElements);
        List<Map<String, PhpValue>> rows = new ArrayList<>();

        if (!isRowCollection) {
            Map<String, PhpValue> row = new LinkedHashMap<>();
            for (Map.Entry<Object, PhpValue> entry : dataElements.entrySet()) {
                if (entry.getKey() instanceof String) {
                    row.put((String) entry.getKey(), entry.getValue());
                }
            }
            rows.add(row);
        } else {
            for (PhpValue rowValue : dataElements.values()) {
                PhpArray rowArray = toArray(rowValue);
                Map<String, PhpValue> row = new LinkedHashMap<>();
                assert rowArray != null;
                if (rowArray.getElements().size() == 1 && rowArray.getElements().containsKey("0")) {
                    PhpValue singleValue = rowArray.getElements().get("0");
                    if (singleValue instanceof PhpObject) {
                        Object underlying = ((PhpObject) singleValue).getValue();
                        if (underlying instanceof String) {
                            row.put("string", singleValue);
                        } else {
                            row.put("value", singleValue);
                        }
                    } else {
                        row.put("value", singleValue);
                    }
                } else {
                    for (Map.Entry<Object, PhpValue> entry : rowArray.getElements().entrySet()) {
                        if (entry.getKey() instanceof String) {
                            row.put((String) entry.getKey(), entry.getValue());
                        }
                    }
                }
                rows.add(row);
            }
        }
        return rows;
    }

    private static boolean isRowCollection(Map<Object, PhpValue> dataElements) {
        for (Object key : dataElements.keySet()) {
            if (!(key instanceof Number)) {
                return false;
            }
        }
        return true;
    }

    private String extractSqlValue(PhpValue value) {
        if (value == null) {
            return "NULL";
        }
        if (value instanceof PhpArray array) {
            if (array.getElements().size() == 1 && array.getElements().containsKey("0")) {
                return extractSqlValue(array.getElements().get("0"));
            } else {
                String arrayString = array.toString().replace("'", "''");
                return "'" + arrayString + "'";
            }
        }
        if (value instanceof PhpObject) {
            Object object = ((PhpObject) value).getValue();
            if (object == null) {
                return "NULL";
            } else if (object instanceof Boolean) {
                return (Boolean) object ? "TRUE" : "FALSE";
            } else if (object instanceof Number) {
                return object.toString();
            } else {
                String string = object.toString().replace("'", "''");
                return "'" + string + "'";
            }
        }
        String fallback = value.toString().replace("'", "''");
        return "'" + fallback + "'";
    }

}
