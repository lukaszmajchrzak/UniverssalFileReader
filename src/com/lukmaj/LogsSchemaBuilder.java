package com.lukmaj;

import java.util.Scanner;

public class LogsSchemaBuilder {
    Markers markers = new Markers();
    DBManager dbManager;
    public LogsSchemaBuilder() {
    }
    public void prepareSchema(){
        markers.getMarkers();
        dbManager = new DBManager(markers);
        dbManager.chooseMarkers();
        if(!dbManager.chooseFromExisting(dbManager.getCreatedName())){
            dbManager.createTable();
            System.out.println("Table created: ");
            dbManager.printSelectedTable();
        } else{
            System.out.println("Table already exist: ");
            dbManager.printSelectedTable();
        }
    }

    public void selectExistingTable(){
        dbManager.getExistingTables();
        Scanner scan = new Scanner(System.in);
        String tName;
        System.out.println("Type table name to chooose table from existing: ");
        tName = scan.nextLine();
        scan.nextInt();
        dbManager.chooseFromExisting(tName);
    }

    public void createLogsSchema(){

    }


}
