package com.lukmaj;

import java.util.HashMap;
import java.util.Map;

public class commandList {
    private Map<String,String> commandList = new HashMap<>();

    public commandList() {
        initCommands();
    }

    private void initCommands(){
        commandList.put("READ MARKERS","Read XML tags from the test file in selected path");
        commandList.put("ADD MARKERS","Add XML tags manually");
        commandList.put("PRINT TABLES","Print created log tables from the database with XML tags ");
        commandList.put("SELECT TABLE","Selects existing table");
        commandList.put("CREATE TABLE","Creates table from chosen markers");
        commandList.put("CHOOSE MARKERS","Selects available markes for SQL table build");
        commandList.put("START READING","Launches read process");
        commandList.put("CREATE INTERFACE SCHEMA","Creates pre-made interface log table");
        commandList.put("STOP READING","Stops read process");
    }
    public void printCommands(){
        for(Map.Entry<String,String> cmd : commandList.entrySet()){
            System.out.println(cmd.getKey() + " - " + cmd.getValue());
        }
    }

    public boolean contains(String key){
        if(commandList.containsKey(key))
            return true;
        return false;
    }
    public int getCommandId(String key){
        int i=0;
        for(Map.Entry<String,String> cmd : commandList.entrySet()){
            if (cmd.getKey().equals(key))
                return i;
            i++;
        }
        return 0;
    }


}
