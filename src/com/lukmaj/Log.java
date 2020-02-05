package com.lukmaj;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class Log {
    private String action;
    private String iban;
    private String seq;
    private String fileName;
    private String openingBalance;
    private String closingBalance;
    private String XML;


    private final SimpleDateFormat sdf= new SimpleDateFormat("yyyy.MM.dd.HH.mm");
    private Timestamp timestamp;
    public Log(String action, String iban, String seq, String fileName, String openingBalance, String closingBalance, String XML) {
        this.timestamp = new Timestamp(System.currentTimeMillis());
        this.action = action;
        this.iban = iban;
        this.seq = seq;
        this.fileName = fileName;
        this.openingBalance = openingBalance;
        this.closingBalance = closingBalance;
        this.XML = XML;
    }

    public String getXML() {
        return XML;
    }

    public String getDateStamp(){
        String onlyDate;
        onlyDate = this.timestamp.toString();
        onlyDate = onlyDate.substring(0, 9);
        return onlyDate;
    }
    public String getTime(){
        String onlyTime;
        onlyTime = this.timestamp.toString();
        onlyTime = onlyTime.substring(11,19);
        return onlyTime;
    }
    public String getFileName() {
        return fileName;
    }

    public String getIban() {
        return iban;
    }

    public String getOpeningBalance() {
        return openingBalance;
    }

    public String getClosingBalance() {
        return closingBalance;
    }
    public String getSeq() {
        return seq;
    }
}
