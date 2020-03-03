package com.lukmaj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandList {
//    private HashMap<String,String> commandList = new HashMap<>();
    private ArrayList<Command> commandList = new ArrayList<Command>();

    public CommandList() {
        initCommands();
    }

    private void initCommands(){
        commandList.add(new Command("READ MARKERS","Read XML tags from the test file in selected path"));
        commandList.add(new Command("ADD MARKERS","Add XML tags manually"));
        commandList.add(new Command("PRINT TABLES","Print created log tables from the database with XML tags "));
        commandList.add(new Command("SELECT TABLE","Selects existing table"));
        commandList.add(new Command("CREATE TABLE","Creates table from chosen markers"));
        commandList.add(new Command("CHOOSE MARKERS","Selects available markes for SQL table build"));
        commandList.add(new Command("START READING","Launches read process"));
        commandList.add(new Command("CREATE INTERFACE SCHEMA","Creates pre-made interface log table"));
        commandList.add(new Command("STOP READING","Stops read process"));
        commandList.add(new Command("SET LOGGING","Sets which values will be shown from logs"));
        commandList.add(new Command("GET LOGS","Gets logs from database"));
        commandList.add(new Command("SHOW SELECTED COLUMNS","Shows selected columns for logging"));
        commandList.add(new Command("CREATE SOA SCHEMA","Creates schema for further use with custom name"));
        commandList.add(new Command("CLEAR TAGS CONTAINER","Clears XML tags used for table usage/creation"));
        commandList.add(new Command("CLEAR LOGS","Deletes all logs from the database"));
        commandList.add(new Command("?","Shows help"));
        commandList.add(new Command("HELP","Shows help"));
        commandList.add(new Command("GET PATH","Raed path from the server"));
        commandList.add(new Command("SET PATH","Sets path"));
    }
    public ArrayList<Command> getCommandList(){
        return new ArrayList<>(commandList);
    }
    public void printCommands(){
        for(Command cmd : commandList){
            System.out.println(cmd.getCommand() + " - " + cmd.getCommandDescription());
        }
    }

    public boolean contains(String key){
        for(Command cmd : commandList){
            if(cmd.getCommand().equals(key)){
                return true;
            }
        }
        return false;
    }
    public int getCommandId(String key){
        int i=0;;
        for(Command cmd : commandList){
//            System.out.println(cmd.getCommand());
            if (cmd.getCommand().equals(key))
                return i;
            i++;
        }
        return -1;
    }
}
