/**
 * @method readFiles - Open files in location and read all of the markers and puts them arrayList in Markers class
 */
package com.lukmaj;

import java.io.*;
import java.util.Scanner;

public class FileReader {

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
                lastIndex = line.substring(firstIndex).indexOf("/>") - 2;
                marker = line.substring(firstIndex, lastIndex);
                if (!markers.contains(marker)) {
                    markers.addMarker(marker);
                }
            } else break;
            line = line.substring(lastIndex);
        }
    }
}