package com.lukmaj;

import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.sql.*;
import java.util.HashMap;

public class DbConnect {
    protected Connection con;
    private ConnectionReader connectionReader=new ConnectionReader();

    public DbConnect(ConnectionReader connectionReader) {
        this.connectionReader = connectionReader;
    }

    /**
     * <p> Method connects to database using connection string typed in connectionString.xml file
     * <p>
     * to read the file method runs ConnectionReader.readConnectionString()</p>
     */
    public void connect() {
        try {

            connectionReader.setFilePath(FileSystems.getDefault().getPath("C:\\Users\\Lukmaj\\Desktop\\dbConnect.xml"));
            String connectionString = connectionReader.getConnectionURL();
            String username = connectionReader.getUsername();
            String password = connectionReader.getPassword();
            this.con = DriverManager.getConnection(connectionString,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // TODO: 10.02.2020 assign correct path to DB
    public void setPath(String path) {
        this.connect();
        try {
            Statement stmt = this.con.createStatement();
            stmt.executeUpdate("UPDATE db.config SET FilePath='" + path + "'");
            System.out.println("Path updated!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void getLogs(HashMap<String,String> columns, String tableName) {
        try {
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + buildSelectQuery(columns) + " FROM " + tableName);
            while (rs.next()) {
                int i = 0;
                System.out.println();
                for (HashMap.Entry<String, String> hm : columns.entrySet()) {
                    System.out.printf(hm.getValue() + rs.getString(i) + " ");
                    i++;
                }
            }
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
    private String buildSelectQuery(HashMap<String,String> columns){
        String query="";
        for(HashMap.Entry<String,String> c: columns.entrySet()){
            query+= c.getKey() + ",";
        }
        return query;
    }

    private String[] buildQuery(HashMap<String,String> logContainer){
        String query[] = new String[2];
        int i=0;
        for(HashMap.Entry<String,String> entry : logContainer.entrySet()){
            query[0] = "'" + entry.getKey() + "'" + ",";
            query[1] = "'" + entry.getValue() + "'";
        }
        return query;
    }
    protected boolean putToDatabase(HashMap<String,String> logContainer,String tableName){
        this.connect();
        String query[] = buildQuery(logContainer);
        try{
            Statement stmt = this.con.createStatement();
            stmt.executeUpdate("INSERT INTO" + tableName + "(" + query[0] + ") VALUES(" + query[1] + ")");
            return true;
        } catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Log already exist in DB");
        } catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
    /**
     * <p> Methods pull path from Database </p>
     * @return path as a String
     */
    public String getPath(){
        String path=null;
        try{
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("Select * from db.config");
            while (rs.next()){
                path = rs.getString(1);
            }
        } catch(SQLException e){
            e.getNextException();
        }
        return path;
    }

    /**
     * <p> Method closes connection to database</p>
     */
    public void close(){
        try{
            con.close();
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
