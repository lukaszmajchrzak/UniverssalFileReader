package com.lukmaj;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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

    private void getMarker(String line, Markers markers) {
        String marker;
        int firstIndex, lastIndex;
        while (true) {
            if (line.contains("<")) {
                firstIndex = line.indexOf("<");
                if(line.charAt(firstIndex+1) != '/') {
                    lastIndex = line.substring(firstIndex).indexOf(">") - 1;
                    marker = line.substring(firstIndex, lastIndex);
                    if (!markers.contains(marker)) {
                        markers.addMarker(marker);
                    }
                } else {
                    firstIndex++;
                    lastIndex = firstIndex;
                }
            } else break;
            line = line.substring(lastIndex);
        }
    }
}