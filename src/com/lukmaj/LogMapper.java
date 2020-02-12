package com.lukmaj;

import java.util.HashMap;
import java.util.Map;

public class LogMapper {
    private String mappedTableNAme;
    private TableBuilder tBuilder;
    private Map<String,String> sortedRecords;

    public LogMapper(String mappedTableNAme, TableBuilder tBuilder) {
        this.mappedTableNAme = mappedTableNAme;
        this.tBuilder = tBuilder;
        sortedRecords = tBuilder.getSortedRecords();
    }
}

/*
log maper - should adjust logs from the files to the table
 */