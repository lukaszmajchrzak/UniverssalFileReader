package com.lukmaj;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DBManager extends TableBuilder {
    private String tableName;
    private Map<String, String> logContainer = new HashMap<>();
    private static final String[] xmlMarker = {"<", "/>"};
    private Markers markers;

    /**
     * <p> Constructor for DBManager which initialize Markers class </p>
     * @param markers - all of markers found in example file
     */
    public DBManager(Markers markers) {
        this.markers = markers;
    }

    public void getMarkers(){
        getMarkersFromColumns(this.tableName);
    }

    /**
     * <p> Method for choosing markers which will be used for logging in future.
     * Markers are taken from example XML file</p>
     */
    public void chooseMarkers(){
        Scanner scan = new Scanner(System.in);
        System.out.println("Available Markers: ");
        for(String marker : markers.getMarkers()){
            System.out.println(marker);
        }
        String dType;
        String scanning="";
        while(!scanning.equals("exit")){
            scanning = scan.nextLine();
            scan.nextInt();

            if(markers.getMarkers().contains(scanning)){
                for(String dataType : super.getDataType().keySet()){
                    System.out.println(dataType);
                }
                System.out.println("Choose data type: ");
                    dType = scan.nextLine();
                    scan.nextInt();

                    if(super.getDataType().containsValue(dType)){
                        super.addRecords(scanning,dType);
                    } else System.out.println("data type not found");
            } else System.out.println("Marker not found!");
        }
    }

    /**
     * <p> Creates table in SQL database, where columns = markers chosen for logigng</p>
     */
    public void createTable(){
        if(!isTableExisting(Integer.toString(buildCreateTableQuery().hashCode()))){
            createLogTable();
            tableName = super.getTableName();
        }
    }

    public void printSelectedTable(){
        super.getExistingLogTable(this.tableName);
    }
    public String getCreatedName(){
        return Integer.toString(buildCreateTableQuery().hashCode());
    }
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
