package com.lukmaj;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class DbConnect{
    protected Connection con;
    private String tableName;

    /**
     * <p> Method connects to database using connection string typed in connectionString.xml file
     *
     * to read the file method runs ConnectionReader.readConnectionString()</p>
     */
    public void connect() {
        ConnectionReader conReader = new ConnectionReader();
        conReader.readConnectionString();


        try {
            this.con = DriverManager.getConnection(conReader.getConnectionString());
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    // TODO: 10.02.2020 assign correct path to DB
        public void setPath(String path){
        this.connect();
        try{
                Statement stmt = this.con.createStatement();
                stmt.executeUpdate("UPDATE db.config SET path='"+ path + "'");
        } catch(SQLException e){
            e.printStackTrace();
        }
        }
    protected HashMap<String,String> getLogs(HashMap<String,String> columns, String tableName){
        try{
            Statement stmt = this.con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + buildSelectQuery(columns) + " FROM " + tableName);
            while(rs.next()){

            }
        } catch(SQLException e){
            e.printStackTrace();
        }

    }
    private String buildSelectQuery(HashMap<String,String> columns){
        String query="";
        for(HashMap.Entry<String,String> c: columns.entrySet()){
            query+= c + ",";
        }
        return query;
    }

    private String[] buildQuery(HashMap<String,String> logContainer){
        String query[] = new String[2];
        for(HashMap.Entry<String,String> entry : logContainer.entrySet()){
            query[1] = "'" + entry.getKey() + "'" + ",";
            query[2] = "'" + entry.getValue() + "'" + ",";
        }
        return query;
    }
    protected boolean putToDatabase(HashMap<String,String> logContainer,String tableName){
        String query[] = buildQuery(logContainer);
        try{
            Statement stmt = this.con.createStatement();
            stmt.executeQuery("INSERT INTO" + tableName + "(" + query[0] + ") VALUES(" + query[1] + ")");
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
            ResultSet rs = stmt.executeQuery("Select * from config");
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
