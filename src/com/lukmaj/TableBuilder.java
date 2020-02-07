package com.lukmaj;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

public class TableBuilder extends DbConnect {
    private String tableName="";
    private Map<String, String> records = new HashMap<>();
    private Map<String, String> dataTypes = new HashMap<>();
    private Map<String, String> sortedRecords = new HashMap<>();

    public TableBuilder() {
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
    public Map<String,String> getDataType(){
        return dataTypes;
    }

    /**
     * <p> Adds record from Markers which include: marker (column in database), SQL data type</p>
     * @param recordName - XML Marker
     * @param value - SQL Data type
     */
    public void addRecords(String recordName, String value) {
        if (dataTypes.containsValue(value)) {
            addRecord(recordName, dataTypes.get(value));
        }
    }

    public String getTableName() {
        return tableName;
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

    /**
     * <p>< Method created for further usage /p><
     * @return alphabetically sorted map of records (marker ; dataType)
     */
    public Map<String, String> getSortedRecords() {
        sortedRecords = sortRecords();
        return sortedRecords;
    }

    /**
     * <p> builds query string using records </p>
     * @return query string which will be used for SQL Create table query
     */
    protected String buildCreateTableQuery() {
        sortedRecords = sortRecords();
        String query = "";
        for (Map.Entry<String, String> entry : sortedRecords.entrySet()) {
            query += entry.getKey() + " " + entry.getValue();
        }
        return query;
    }
    protected void getExistingLogTable(String tableName){
        super.connect();
        try{
            Statement stmt = super.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='"+tableName+"'");
            while(rs.next()){
                System.out.println(rs);
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        super.close();
    }

    /**
     * <p> Method reads all column names from specified table</p>
     * @param TableName - table name, which is hashCode of query string
     */
    protected void getMarkersFromColumns(String TableName){
        ArrayList<String> markers = new ArrayList<>();

        super.connect();
        try{
            Statement stmt = super.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME='"+tableName+"'");
            while(rs.next()){
                markers.add(rs.getString(1));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        super.close();
    }

    /**
     * <p> Method prints all existing log tables in database</p>
     */
    public void getAllExisting(){
        super.connect();
        try{
            Statement stmt = super.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM INFROMATION_SCHEMA.TABLES");
            while(rs.next()){
                System.out.println(rs.getString(1));
                getExistingLogTable(rs.getString(1));
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        super.close();
    }

    /**
     * <p> Method creates log table using pre-built query
     *  query is builded from records (records are sorted alphabetically). Each record is a new column in dabatase
     *  table name is hashCode() of query </p>
     * @return true if table is created, false if not
     */
    public boolean createLogTable() {
        super.connect();
        try {
            Statement stmt = super.con.createStatement();
            stmt.executeUpdate("CREATE TABLE " + Integer.toString(buildCreateTableQuery().hashCode()) + " (" + buildCreateTableQuery() + " );");
            this.tableName = Integer.toString(buildCreateTableQuery().hashCode());
           return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        super.close();
        return false;
    }

    /**
     * <p> Method checks if table already exist in database</p>
     * @param tableName - table name which will be checked in database
     * @return true if table is found in database, false if not
     */
    public boolean isTableExisting(String tableName){
        try{
            Statement stmt = super.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM INFORMATION_SCHEMA.TABLES");
            while(rs.next()){
                if(rs.getString(1).equals(tableName))
                    return true;
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
        super.close();
        return false;
    }



}
