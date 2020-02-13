package com.lukmaj;

import java.sql.*;

public class DbConnect {
    protected Connection con;

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

        private String buildQuery(Log log){
        String query;
        double transactionAmount;
        try {
            transactionAmount = Double.valueOf(log.getClosingBalance()) - Double.valueOf(log.getOpeningBalance());
        } catch(NullPointerException e){
            transactionAmount = 0;
        }
        query = ((char)39) + log.getIban() + ((char)39) + "," + ((char)39) + log.getSeq() + ((char)39) + "," + ((char)39) + log.getFileName() + ((char)39) + "," + ((char)39) + log.getDateStamp() + ((char)39) + "," + ((char)39) + log.getOpeningBalance() + ((char)39) + "," +
                ((char)39) + log.getClosingBalance() + ((char)39) + "," + ((char)39) + transactionAmount + ((char)39) + "," + ((char) 39) + log.getTime() + ((char) 39 + "," + ((char) 39) + log.getXML() + ((char)39));
        return query;
    }

    /**
     * <p> Method will be deleted soon </p>
     * @param log - 2b deleted
     */
    public void insertLog(Log log){
        try {
            Statement stmt = this.con.createStatement();
//            System.out.println("INSERT INTO logs(IBAN,seq,FileName,timestamp,openingBalance,closingBalance,transactionAmount) VALUES(" + buildQuery(log) +  ")");
            stmt.executeUpdate("INSERT INTO logs(IBAN,seq,FileName,timestamp,openingBalance,closingBalance,transactionAmount,timeStampTime,XML) VALUES(" + buildQuery(log) +  ")");
//            while (rs.next()) {
//                System.out.println(rs.getString(1) + " " + rs.getInt(2) + " " + rs.getString(3) + " " + rs.getString(4) + " " + rs.getDouble(5) + " " + rs.getDouble(6));
//            }
        }catch(SQLIntegrityConstraintViolationException e){
            System.out.println("Constraint value already exist in DB!");
            e.getNextException();
        } catch(SQLException e){
            e.printStackTrace();
        }
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
