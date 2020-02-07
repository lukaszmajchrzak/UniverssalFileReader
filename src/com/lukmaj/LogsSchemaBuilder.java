package com.lukmaj;

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



    public void selectExisting(){

    }

    public void createLogsSchema(){

    }


}
