package com.lukmaj;

import java.util.Scanner;

public class LogsSchemaBuilder {
    private Markers markers;
    private DBManager dbManager;
    private String tableName;
    public LogsSchemaBuilder() {
    }

    public LogsSchemaBuilder(Markers markers, DBManager dbManager) {
        this.markers = markers;
        this.dbManager = dbManager;
    }

    /**
     * <P> Method prepares table schema for logs in steps:
     * 1. Choose XML tags from file / create new XML tags
     * 2. Prepare SQL query, checks if table already exist
     * 3. Creates table or Selects existing if it fits to the used Markers one-to-one </P>
     */
    public void prepareSchema(){
        markers.getMarkers();
        dbManager = new DBManager(markers);
        dbManager.chooseMarkers();
        if(!dbManager.chooseFromExisting(dbManager.getCreatedName())){
            dbManager.loadLogContainer();
            dbManager.createTable();
            System.out.println("Table created: ");
            dbManager.printSelectedTable();
            tableName = dbManager.getTableName();
        } else{
            System.out.println("Table already exist: ");
            dbManager.printSelectedTable();
            tableName = dbManager.getTableName();
            dbManager.loadLogContainer();
        }
    }
    // TODO: 13.02.2020
    public void createInterfaceSchema(){
        System.out.println();

        /**
         * <p> Method manually allows to choose existing table for logs. It will print all of available tables with all columns where column name = XML tag</p>
          */
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
}
