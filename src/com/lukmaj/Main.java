/**
*
 **/


package com.lukmaj;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String cmd, sCat;
        String cmdSplit[];
        boolean isCmd = false;
        int x = 9;
        Path path = new Path();
        Markers markers = new Markers();
        DBManager dbManager = new DBManager(markers);
        LogsSchemaBuilder logsSchemaBuilder = new LogsSchemaBuilder(markers,dbManager);
        MarkersReader markersReader = new MarkersReader();
        commandList commands = new commandList();
        path.readPath();
        System.out.println("current path: " + path.getPath());
        boolean isRunning = false;

        // loop for command listening
        while(true){
            isCmd = false;
            JobScheduler job = new JobScheduler(path,markers);
            cmd = scan.nextLine();
            scan.nextInt();
            sCat ="";
            cmdSplit = cmd.split(" ");
            for(String s : cmdSplit){
                sCat.concat(" " + s);
                if(commands.contains(s.toUpperCase())){
                    isCmd = true;
                    x= commands.getCommandId(s.toUpperCase());
                }
                if(commands.contains(sCat.toUpperCase())){
                    x = commands.getCommandId(s.toUpperCase());
                    isCmd = true;
                }
            }
            if(isCmd) {
                switch (x) {
                    case 0:
//                        "READ MARKERS","Read XML tags from the test file in selected path"
                        markersReader.readFiles(path,markers);
                        break;

                    case 1:
//                        "ADD MARKERS","Add XML tags manually")
                        while(!cmd.equals("exit")) {
                            System.out.println("Type new marker:");
                            cmd = scan.nextLine();
                            scan.nextInt();
                            if(!cmd.equals("exit")) {
                                markers.addMarker(cmd);
                            }
                        }
                        break;

                    case 2:
//                        "PRINT TABLES","Print created log tables from the database with XML tags "
                        dbManager.getExistingTables();
                        break;

                    case 3:
//                        "SELECT TABLE","Selects existing table"
                        System.out.println("\n\nType table name :");
                        cmd = scan.nextLine();
                        scan.nextInt();
                        logsSchemaBuilder.selectExistingTable();
                        break;

                    case 4:
//                       "CREATE TABLE","Creates table from chosen markers"
                        logsSchemaBuilder.prepareSchema();
                        break;
                    case 5:
//                        "CHOOSE MARKERS","Selects available markes for SQL table build")
                        dbManager.chooseMarkers();
                        break;
                    case 6:
//                        "START READING","Launches read process"
                        job.run();
                        isRunning = true;
                        break;
                    case 7:
//                        "CREATE INTERFACE SCHEMA","Creates pre-made interface log table"
                        logsSchemaBuilder.createInterfaceSchema();
                        break;
                    case 8:
//                        "STOP READING","Stops read process"
                        if(isRunning) {
                            job.run();
                            isRunning = false;
                        }
                        break;
                }

            } else System.out.println("Command not found!");



        }

    }
}
