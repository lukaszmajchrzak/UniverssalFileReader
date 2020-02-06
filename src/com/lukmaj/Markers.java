/**
 * @param markers - List of markers found in files
 */
package com.lukmaj;

import java.util.ArrayList;

public class Markers {
    private ArrayList<String> markers = new ArrayList<>();

    public void addMarker(String marker){
        markers.add(marker);
    }

    public ArrayList<String> getMarkers(){
        return markers;
    }

    public boolean contains(String marker){
        for(String m: markers){
            if (m.equals(marker)){
                return true;
            }
        }
        return false;
    }
}
