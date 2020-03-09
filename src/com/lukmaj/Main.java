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
        CommandList commands = new CommandList();
        path.readPath();
        FileReader fReader = new FileReader(path,markers);
        JobScheduler job = new JobScheduler(path,markers);
        System.out.println("current path: " + path.getPath());
        boolean isRunning = false;

        // loop for command listening
        while(true){
            isCmd = false;

            cmd = scan.nextLine();
            sCat ="";
            cmdSplit = cmd.split(" ");
            for(String s : cmdSplit){
                sCat.concat(s);
                if(commands.contains(s.toUpperCase())){
//                    System.out.println("here we go");
                    isCmd = true;
                    x= commands.getCommandId(s.toUpperCase());
                }
                if(commands.contains(sCat.toUpperCase())){
//                    System.out.println("here we go 2");
                    x = commands.getCommandId(s.toUpperCase());
                    isCmd = true;
                }
                sCat.concat(" ");
            }
            if(commands.contains(cmd.toUpperCase())){
                x = commands.getCommandId(cmd.toUpperCase());
                isCmd = true;
            }
            if(isCmd) {
                switch (x) {
                    case 0:
//                        "READ MARKERS","Read XML tags from the test file in selected path"
                        markersReader.readFiles(path,markers);
                        break;

                    case 1:
//                        "ADD MARKERS","Add XML tags manually")
                        String marks = "";
                        while(!marks.equals("exit")) {
                            System.out.println("Type new marker:");
                            marks = scan.nextLine();
//                            scan.nextInt();
                            if(!marks.equals("exit")) {
                                dbManager.AddMarkersManually(marks);
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
//                        scan.nextInt();
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
                    case 9:
//                        "SET LOGGING","Sets which values will be shown from logs"
                        dbManager.setLogContainer(dbManager.prepareLogPrinter());
                        break;
                    case 10:
//                        commandList.put("GET LOGS","Gets logs from database");
                        if(dbManager.isLogContainerEmpty()){
                            dbManager.setLogContainer(dbManager.prepareLogPrinter());
                        }
                        dbManager.getLogs(dbManager.getLogContainer(),dbManager.getTableName());
                        break;
                    case 11:
//                      "SHOW SELECTED COLUMNS","Shows selected columns for logging"
                        dbManager.loadLogContainer();
                        dbManager.printLogContainer();
                        break;
                    case 12:
//                     "CREATE SOA SCHEMA","Creates schema for further use with custom name"
                        String s;
                        System.out.println("Provide custom table name: ");
                        s = scan.nextLine();
//                        scan.nextInt();

                        dbManager.createSoaPreparedTable(s);
                        break;
                    case 13:
//                       "CLEAR TAGS CONTAINER","Clears XML tags used for table usage/creation"
                        dbManager.clearLogCoontainer();
                        break;
                    case 14:
//                      "CLEAR LOGS","Deletes all logs from the database"
                        dbManager.deleteLogs();
                        break;
                    case 15:
//                        "?","Shows help"
                        commands.printCommands();
                        break;
                    case 16:
//                        "HELP","Shows help"
                        commands.printCommands();
                        break;
                    case 17:
//                       "GET PATH","Raed path from the server"
                        System.out.println(path.getPath());
                        break;
                    case 18:
                        String tPath;
                        tPath = scan.nextLine();
//                        scan.nextInt();
//                        commandList.put("SET PATH","Sets path");
                        if(path.updatePath(tPath)){
                            System.out.println("Path Updated!");
                            path.readPath();
                        } else System.out.println("Wrong path!");
                        break;
                    case 19:
//                        "READ FILES","Read files once
                            dbManager.putToDatabaseT(fReader.readFiles());
                        break;
                }

            } else System.out.println("Command not found!");



        }

    }
}
