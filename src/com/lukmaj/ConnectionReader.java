
package com.lukmaj;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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
      * connectionDetails is loaded in constructor
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
    public void readConnectionString() {
        try {

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File("connectionString.xml"));
            connectionDetails.replace("IP", doc.getElementsByTagName("IP").item(0).getTextContent());
            connectionDetails.replace("Username", doc.getElementsByTagName("Username").item(0).getTextContent());
            connectionDetails.replace("Password", doc.getElementsByTagName("Password").item(0).getTextContent());
            connectionDetails.replace("Database", doc.getElementsByTagName("Database").item(0).getTextContent());
            connectionDetails.replace("jdbc", doc.getElementsByTagName("jdbc").item(0).getTextContent());
            buildConnectionString();

        } catch(ParserConfigurationException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(SAXException e){
            e.printStackTrace();
        }
    }

    public String getAddress(){
        return "jdbc:mysql://" + connectionDetails.get("IP") + "/" + connectionDetails.get("Database");
    }
    public String getUsername(){
        return connectionDetails.get("Username");
    }
    public String getPassword(){
        return connectionDetails.get("Password");

    }
    private void buildConnectionString(){
        //                this.con =DriverManager.getConnection("jdbc:mysql://10.13.135.10:3306/db", "LukMaj", "LukMaj123$%^");
        this.connectionString = ((char) 34) + connectionDetails.get("jdbc") + "://" + connectionDetails.get("IP")+ "/" + connectionDetails.get("Database")
                + ((char) 34) + ", " +  ((char) 34) + connectionDetails.get("Username") + ((char) 34) +  ", " + ((char) 34) + connectionDetails.get("Password") + ((char) 34);

    }

     /**
      *
      * @return connectionString which contains connection string built from connectionsString.xml file
      */
        public String getConnectionString () {
            return connectionString;
        }
    }