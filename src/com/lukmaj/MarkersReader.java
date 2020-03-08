package com.lukmaj;

import java.io.*;
import java.util.Scanner;

public class MarkersReader {

    /**
     * <p> Method read files from selected destination and collect all found markers </p>
     * @param path - folder in which are stored test files
     * @param markers - markers class for found markers
     */
    public void readFiles(Path path, Markers markers) {
        String cPath = path.getPath();

        File file = new File(cPath);
        for (String fName : file.list()) {
            if (fName.contains(".")) {
                openFile(new File(cPath + "\\" + fName), path, markers);
            }
        }
    }
    private void openFile(File filepath, Path path, Markers markers) {
        try {
            Scanner sc = new Scanner(filepath);
            while (sc.hasNextLine()) {
                getMarker(sc.nextLine(),markers);
            }
            sc.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    private void getMarker(String line, Markers markers){
        String marker;
        int firstindex, lastIndex;

        while(true){
            if(line.contains("<")){
                firstindex = line.indexOf("<");
//                System.out.println(firstindex);
                if(line.charAt(firstindex+1) != '/'){
                    line.substring(firstindex);
                    if(line.contains(">")){
                        lastIndex = line.indexOf(">");
                        marker=line.substring(1,lastIndex);
                        line = line.substring(lastIndex+1);
                        if(!markers.contains((marker))){
                            markers.addMarker(marker);
                        }
                    }
                } else {
                    line = line.substring(firstindex+1);
                }
            } else {
//                System.out.println(line);
                System.out.println("markers read!");
                break;
            }
        }
    }

}