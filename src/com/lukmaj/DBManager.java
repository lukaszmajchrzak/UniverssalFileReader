/**
 * @param logContainer - map used for values which will be stored as logs
 *                     first is record name
 *                     second is record value
 *                     all taken from XML file.
 * @param xmlMarker - markers < and /> used in XML files
 */
package com.lukmaj;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class DBManager extends LogBuilder {
    private String tableName;
    private Map<String, String> logContainer = new HashMap<>();
    private static final String[] xmlMarker = {"<", "/>"};
    private Markers markers;

    public DBManager(Markers markers) {
        this.markers = markers;
    }
    public void getMarkers(){
        getMarkersFromColumns(this.tableName);
    }
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

    public void getExistingTables(){
        super.getAllExisting();
    }

    public boolean chooseFromExisting(String tName){
        if(super.isTableExisting(tName)){
            tableName = tName;
            return true;
        } return false;
    }

}
