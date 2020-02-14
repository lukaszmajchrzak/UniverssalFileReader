package com.lukmaj;

import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.TimerTask;

public class FileReader extends TimerTask {
    private Path path;
    private Markers markers;

    public FileReader(Path path, Markers markers) {
        this.path = path;
        this.markers = markers;
    }

    @Override
    public void run() {
        readFiles(path.getPath(),markers.toArray());
    }

    public HashMap<String,String> readFiles(String pathname, String tags[]) {
        HashMap<String,String> records = new HashMap<>();
        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = factory.newDocumentBuilder();
            Document doc = dBuilder.parse(new File(pathname));
            for(String s : tags){
                records.put(s,doc.getElementsByTagName(s).item(0).getTextContent());
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }
        return records;
    }
}
