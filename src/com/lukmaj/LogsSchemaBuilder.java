package com.lukmaj;

import java.util.Scanner;

public class LogsSchemaBuilder {
    private Markers markers;
    private DBManager dbManager;
    private String tableName;
    public LogsSchemaBuilder() {
    }

    public LogsSchemaBuilder(Markers markers, DBManager dbManager, String tableName) {
        this.markers = markers;
        this.dbManager = dbManager;
        this.tableName = tableName;
    }

    public void prepareSchema(){
        markers.getMarkers();
        dbManager = new DBManager(markers);
        dbManager.chooseMarkers();
        if(!dbManager.chooseFromExisting(dbManager.getCreatedName())){
            dbManager.prepareLogContainer();
            dbManager.createTable();
            System.out.println("Table created: ");
            dbManager.printSelectedTable();
            tableName = dbManager.getTableName();
        } else{
            System.out.println("Table already exist: ");
            dbManager.printSelectedTable();
            tableName = dbManager.getTableName();
        }
    }
    public void createInterfaceSchema(){
        System.out.println();

    }
    public void selectExistingTable(){
        dbManager.getExistingTables();
        Scanner scan = new Scanner(System.in);
        String tName;
        System.out.println("Type table name to chooose table from existing: ");
        tName = scan.nextLine();
        scan.nextInt();
        dbManager.chooseFromExisting(tName);
        tableName = tName;
    }

    public String getTableName() {
        return tableName;
    }

    public void createLogsSchema(){

    }


}
