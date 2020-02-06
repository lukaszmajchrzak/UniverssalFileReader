/**
 * @param logContainer - map used for values which will be stored as logs
 *                     first is record name
 *                     second is record value
 *                     all taken from XML file.
 * @param xmlMarker - markers < and /> used in XML files
 */
package com.lukmaj;

import java.util.HashMap;
import java.util.Map;


public class DBManager extends LogBuilder {
    private Map<String, String> logContainer = new HashMap<>();
    private static final String[] xmlMarker = {"<", "/>"};

    public void getAvailableMarkers(){

    }

}
