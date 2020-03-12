package com.lukmaj;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.TimerTask;

public class FileReader extends TimerTask {
    private Path path;
    private Markers markers;
    private DBManager dbManager;

    public FileReader(Path path, DBManager dbManager) {
        this.path = path;
        this.dbManager = dbManager;
    }

    @Override
    public void run() {
        readFiles(path.getPath(), (dbManager.getSortedRecords().keySet().toArray()));
    }

    public HashMap<String,String> readFiles(String pathname, Object tags[]) {
        HashMap<String,String> records = new HashMap<>();
        try {

            File files = new File(pathname);
            for(String f : files.list()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = factory.newDocumentBuilder();
                Document doc = dBuilder.parse(new File(pathname + ((char) 92) + f));
                for (Object s : tags) {
                    records.put(s.toString(), doc.getElementsByTagName(s.toString()).item(0).getTextContent());

                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return records;
    }

    public HashMap<String,String> readFiles() {
        String pathname = this.path.getPath();
        Object tags[] = dbManager.getSortedRecords().keySet().toArray();
        HashMap<String,String> records = new HashMap<>();
        try {

            File files = new File(pathname);
            for(String f : files.list()) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = factory.newDocumentBuilder();
                Document doc = dBuilder.parse(new File(pathname + ((char) 92) + f));
                for (Object s : tags) {
//                    System.out.println(s.toString());
                    if(doc.getElementsByTagName(s.toString()).item(0).getTextContent() != null) {
                        System.out.println(s.toString() + " " +  doc.getElementsByTagName(s.toString()).item(0).getTextContent());
                        records.put(s.toString(), doc.getElementsByTagName(s.toString()).item(0).getTextContent());
                    }
                }
            }
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
        return records;
    }
}
