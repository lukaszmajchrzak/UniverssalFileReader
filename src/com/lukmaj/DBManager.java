package com.lukmaj;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DBManager extends TableBuilder {
    private String tableName;
    private HashMap<String, String> logContainer = new HashMap<>();
    private Markers markers;

    /**
     * <p> Constructor for DBManager which initialize Markers class </p>
     * @param markers - all of markers found in example file
     */
    public DBManager(Markers markers) {
        this.markers = markers;
    }
    /**
     * @return returns logContainers - map which will store marker + markerValue
     */
    public HashMap<String, String> getLogContainer() {
        return logContainer;
    }

    /**
     * <p> Method clears logCointaner map and input all records from DB as empty keys</p>
     */
    public boolean isTableNameEmpty(){
        if(tableName.isEmpty())
            return true;
        else return false;
    }
    public void loadLogContainer(){
        logContainer.clear();
        for (String markers: getSortedRecords().keySet()) {
            logContainer.put(markers,"");
        }
    }

    /**
     *
     * @return true if hashmap logContainer is empty
     */
    public boolean isLogContainerEmpty(){
        if(logContainer.isEmpty())
            return true;
        return false;
    }

    /**
     * <p> Method prints selected columns in logContainer</p>
     */
    public void printLogContainer(){
        if(!isLogContainerEmpty()){
            System.out.println("available columns:" );
            for(HashMap.Entry<String,String> lc : logContainer.entrySet()){
                System.out.println(lc.getKey());
            }
        } else System.out.println("no columns selected!");
    }

    /**
     *
     * @param logContainer used to push HashMap to logContainer param
     */
    public void setLogContainer(HashMap<String, String> logContainer) {
        this.logContainer = logContainer;
    }

    /**
     * <p> Method allows to choose which XML Tag values will be printed in logs and also allows to type some value will be visible as an marker before log data will be printed
     * e.g. when value will be 'IBAN:' then log will be printed as IBAN: tag.value</p>
     */
    public HashMap<String,String>  prepareLogPrinter(){
        Scanner scan = new Scanner(System.in);
        String s="",m="";
        HashMap<String,String> columns = new HashMap<>();
        Map<String,String> sortedRecords = getSortedRecords();
        for(Map.Entry<String,String> sr : sortedRecords.entrySet()){
            System.out.println(sr.getKey() + " : " + sr.getValue());
        }
        System.out.println("Choose columns to be printed and type marker which will be displayed before the value: \ntype exit to finish");
        while(!s.toUpperCase().equals("EXIT")){
            s = scan.nextLine();

            if(sortedRecords.containsKey(s)){
                System.out.println("Type marker which will be displayed before the value:");
                m=scan.nextLine();

                if(m!=null){
                    if(m.contains(":")) {
                        columns.put(s, m);
                    } else{
                        m.concat(":");
                        columns.put(s,m);
                    }
                } else{
                    m=" ";
                    columns.put(s,m);
                }
            }
            if(columns.isEmpty() && s.toUpperCase().equals("EXIT")){
                System.out.println("There are no values added!");
                s = " ";
            }
        }
        return columns;
    }
    /**
     * <p> Method clears valueSet in the logContainer map </p>
     */
   public void clearLogCoontainer(){
        logContainer.clear();
        }

    public void deleteLogs(){
       super.deleteLogs(tableName);
    }
    /**
     * Returns markers read from database
     */
    public void getMarkers(){
        getMarkersFromColumns(this.tableName);
    }

    /**
     * <p> Method for choosing markers which will be used for logging in future.
     * Markers are taken from example XML file</p>
     */
    public void chooseMarkers() {
        Scanner scan = new Scanner(System.in);
        String dType;
        String scanning;
        while (true) {
            scanning = scan.nextLine();
            if (!scanning.toUpperCase().equals("EXIT")) {
                printAvailableMarkers();
                if (markers.getMarkers().contains(scanning)) {
                    for (String dataType : super.getDataType().keySet()) {
                        System.out.println(dataType);
                    }
                    System.out.println("Choose data type: ");
                    dType = scan.nextLine();

                    if (super.getDataType().containsKey(dType)) {
                        super.addRecords(scanning, getDataType().get(dType));
                    } else System.out.println("data type not found");
                } else System.out.println("Marker not found!");

                System.out.println("\n");
            } else break;
        }
    }

    private void printAvailableMarkers(){
        System.out.println("Available Markers: ");
        int i=0;
        for(String marker : markers.getMarkers()){
            if(!super.recordsContains(marker))
                System.out.print(marker + " ");
            if(i% 5 == 0){
                System.out.println("");
            }
            i++;
        }
    }
    /**
     * <P> Method allows to create pre-made log tables for existing SOA interface </P>
     * @param tableName will be used instead of .hashCode()
     */
    public  void createSoaPreparedTable(String tableName){
        if(!super.isTableExisting(tableName))
            if(!super.isTableExisting(Integer.toString(super.buildCreateTableQuery().hashCode()))){
                super.createSoaPrepared(tableName);
            }
    }
    /**
     * <p> Creates table in SQL database, where columns = markers chosen for logigng and name is query.hashCode()</p>
     */
    public void createTable(){
        if(!super.isTableExisting(getCreatedName())){
            super.createLogTable();
            tableName = super.getTableName();
        }
    }

    /**
     * <p> Method prints all column names (XML tags) from selected table</p>
     */
    public void printSelectedTable(){
        super.getExistingLogTable(this.tableName);
    }

    /**
     *
     * @return returns .hashCode() of the created table
     */
    public String getCreatedName(){
        return super.buildName();
    }

    /**
     * <p> Method calls getAllExisting from super class - gets all existing tables from database</p>
     */
    public void getExistingTables(){
        super.getAllExisting();
    }

    /**
     * <p> Allows choosing existing table in Database for logging</p>
     * @param tName - table name for SQL connection
     * @return true if table was found in DB, false when not
     */
    public boolean chooseFromExisting(String tName){
        if(super.isTableExisting(tName)){
            tableName = tName;
            return true;
        } return false;
    }
    public void AddMarkersManually(String marker){
        markers.addMarker(marker);
    }





}
