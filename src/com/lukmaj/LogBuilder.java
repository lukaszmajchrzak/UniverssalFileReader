/**

 * @param records - Map of records which will be typed by user
 * @param sortedRecords - records sorted alphabetically - for identical string.hashCode()
 * @param dataTypes - Map of dataTypes available IN SQL and compared to
 *
 * @method createlogTabe - creates table where table name is hashCode() of String which contains all of the column & types
 *
 */
package com.lukmaj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

public class LogBuilder extends DbConnect {

    private Map<String, String> records = new HashMap<>();
    private Map<String, String> dataTypes = new HashMap<>();
    private Map<String, String> sortedRecords = new HashMap<>();

    public LogBuilder() {
        setdataTypes();
    }


    private void setdataTypes() {
        dataTypes.put("Integer", "int");
        dataTypes.put("Float", "float");
        dataTypes.put("Date", "DATE");
        dataTypes.put("TIME", "TIME");
        dataTypes.put("TIMESTAMP", "TIMESTAMP");
        dataTypes.put("Char", "CHAR");
        dataTypes.put("STRING", "VARCHAR(255)");
    }

    public void addRecords(String recordName, String value) {
        if (dataTypes.containsValue(value)) {
            addRecord(recordName, dataTypes.get(value));
        }
    }

    private void addRecord(String recordName, String dataType) {
        records.put(recordName, dataType);
    }

    private Map<String, String> sortRecords() {
        Map<String, String> sorted = records
                .entrySet()
                .stream()
                .sorted(comparingByKey())
                .collect(
                        toMap(e -> e.getKey(), e -> e.getValue(),
                                (e1, e2) -> e2, LinkedHashMap::new)
                );
        return sorted;
    }

    public Map<String, String> getSortedRecords() {
        sortedRecords = sortRecords();
        return sortedRecords;
    }

    private String buildCreateTableQuery() {
        sortedRecords = sortRecords();
        String query = "";
        for (Map.Entry<String, String> entry : sortedRecords.entrySet()) {
            query += entry.getKey() + " " + entry.getValue();
        }
        return query;
    }

    private boolean isExist(String tableName) {
        super.connect();
        boolean exist = false;
        try {
            Statement stmt = super.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'db' AND TABLE_NAME ='" + tableName + "'");
            while (rs.next()) {
                exist = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.close();
        return exist;
    }

    public boolean createLogTable() {
        boolean isCreated = false;

        super.connect();
        try {
            Statement stmt = super.con.createStatement();
            stmt.executeUpdate("CREATE TABLE " + Integer.toString(buildCreateTableQuery().hashCode()) + " (" + buildCreateTableQuery() + " );");
            isCreated = true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return isCreated;
    }
}
