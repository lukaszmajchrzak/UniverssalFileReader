package com.lukmaj;

import java.util.ArrayList;
import java.util.Date;
@// TODO: 14.02.2020  
public class Log {
    private ArrayList<String> textData;
    private ArrayList<Date> dateData;
    private ArrayList<Double> doubleData;
    private ArrayList<Integer> intData;

    public Log() {
    }

    public void addTextData(String text){
        textData.add(text);
    }

    public void addDateData(Date date){
        dateData.add(date);
    }

}
