/**
 * @param markers - List of markers found in files
 */
package com.lukmaj;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Markers {
    private ArrayList<String> markers = new ArrayList<>();

    /**
     * <p> Adds marker to list of markers</p>
     * @param marker - XML marker as String
     */
    public void addMarker(String marker){
        markers.add(marker);
    }

    public ArrayList<String> getMarkers(){
        return markers;
    }

    /**
     * <p> Checks if marker is already stored in markers array list</p>
     * @param marker - marker stored as string
     * @return true if marker is already added, false if not
     */
    public boolean contains(String marker){
        for(String m: markers){
            if (m.equals(marker)){
                return true;
            }
        }
        return false;
    }
    public boolean isEmpty(){
        if(markers.isEmpty()){
            return true;
        }
        return false;
    }
    public boolean clearMarkers(){
        markers.clear();
        if(markers.isEmpty())
            return true;
        return false;
    }
    public String[] toArray(){
        String[] s = new String[markers.size()];
        int i=0;
        for(String m : markers){
            i++;
            s[i] = m;
        }
        return s;
    }
}
