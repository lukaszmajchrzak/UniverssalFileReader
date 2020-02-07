
package com.lukmaj;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
 // "jdbc:mysql://127.0.0.10:3306/db", "lukmaj", "LukMaj123$%^");
public class ConnectionReader {
    private Map<String,String> connectionDetails = new HashMap<>();

     /**
      * @param connectionString - String which contains built connectionString for DBConnect class
      */
    private String connectionString;

     /**
      * @map connectionDetails is loaded in constructor
      */
     public ConnectionReader() {
         connectionDetails.put("IP","");
         connectionDetails.put("Username","");
         connectionDetails.put("Password","");
         connectionDetails.put("Database","");
         connectionDetails.put("jdbc","");
     }

     /**
      * <p> Method reads connection string details from connectionString.xml file and builds connectionString  </p>
      */
     public void readConnectionString(){
        File file = new File("connectionString.xml");
        readFromFile(file);
    }

    private void readFromFile(File file){
        try{
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()){
                createConnectionString(sc.nextLine());
            }
        } catch (IOException e){
            e.printStackTrace();
        }
       buildConnectionString();
    }
    private void buildConnectionString(){
        this.connectionString = connectionDetails.get("jdbc") + "://" + connectionDetails.get("IP")+ "/" + connectionDetails.get("Database")
                + ((char) 34) + "," + connectionDetails.get("Username") + "," + connectionDetails.get("Passwowrd") + ((char) 34);
     }

    private void createConnectionString(String line) {
        int firstIndex, lastIndex;
        String mapKey;
        while (true) {
            if (line.contains("<")) {
                firstIndex = line.indexOf("<");
                if (line.charAt(firstIndex + 1) != '/') {
                    lastIndex = line.indexOf(">");
                    mapKey = line.substring(firstIndex, lastIndex);
                    line = line.substring(++lastIndex);
                    firstIndex = lastIndex;
                    lastIndex = line.indexOf("</");
                    connectionDetails.replace(mapKey, line.substring(firstIndex, lastIndex));
                }
            }
        }

    }

     /**
      *
      * @return connectionString which contains connection string built from connectionsString.xml file
      */
        public String getConnectionString () {
            return connectionString;
        }
    }