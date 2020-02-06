/**
 * @param logContainer - map used for values which will be stored as logs
 *                     first is record name
 *                     second is record value
 *                     all taken from XML file.
 * @param xmlMarker - markers < and /> used in XML files
 * @param records - Map of records which will be taken from the file and put into logContainer as record name & value
 * @param datatypes - Map of datatypes available IN SQL and compared to
 */
package com.lukmaj;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static java.util.Map.Entry.comparingByKey;
import static java.util.stream.Collectors.toMap;

public class logBuilder extends DbConnect{
    private static final String[] xmlMarker = {"<","/>"};
    private Map<String,String> records = new HashMap<>();
    private Map<String,String> logContainer = new HashMap<>();
    private Map<String,String> datatypes = new HashMap<>();

    private void setDatatypes() {
        datatypes.put("int","Integer");
        datatypes.put("float","Float");
        datatypes.put("DATE","Date");
        datatypes.put("TIME","Time");
        datatypes.put("TIMESTAMP","TIMESTAMP");
        datatypes.put("CHAR","Char");
        datatypes.put("VARCHAR(255)","STRING");
    }

    public void addRecords(String recordName, String value){
        if(datatypes.containsKey(value)){
            addRecord(recordName, datatypes.get(value));
        }
    }
    private void addRecord(String recordName, String dataType){
        records.put(recordName,dataType);
    }

    public Map<String,String> getRecords(){
        return records;
    }
    private Map<String,String> sortRecords() {
        Map<String,String> sorted = records
                .entrySet()
                .stream()
                .sorted(comparingByKey())
                .collect(
                        toMap(e -> e.getKey(), e-> e.getValue(),
                                (e1,e2) -> e2, LinkedHashMap::new)
                );
        return sorted;
    }
    private String buildCreateTableQuery(){
        Map<String,String> sorted = sortRecords();
        String query = "";
        for(Map.Entry<String,String> entry : sorted.entrySet()) {
            query += entry.getKey() + " " + entry.getValue();
        }

        return query;
    }
    public void createLogTable(){
        super.connect();
        try{
            Statement stmt = super.con.createStatement();
            stmt.executeUpdate("CREATE TABLE " + buildCreateTableQuery().hashCode() + " (" + buildCreateTableQuery() + " );");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
