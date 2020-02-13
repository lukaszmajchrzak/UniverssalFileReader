package com.lukmaj;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Documented;
import java.util.Scanner;

public class Asd {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(new File("connectionString.xml"));
        NodeList nodeList;
        String a;
        NamedNodeMap n;
        n=doc.getAttributes();

        System.out.println(a);


//        for(int i=0;i<nodeList.getLength();i++){
//            System.out.println(nodeList.item(i).getLocalName());
//        }

        System.out.println(doc.getElementsByTagName("Username").item(0)

    }
}
