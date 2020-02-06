/**
    Application for Universal File Reading:
        1. Finding keywords in files
        2. Grouping files by keywords / marks
        3. Building logs queries

 **/


package com.lukmaj;

public class Main {

    public static void main(String[] args) {
        Markers markers = new Markers();
        DBManager dbManager = new DBManager(markers);
    }
}
