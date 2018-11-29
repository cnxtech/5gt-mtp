/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mtp.common;

import com.mtp.common.objects.DomainElem;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



/**
 *
 * @author fhuser
 */
public class XMLDomainParser {
    private ArrayList<DomainElem> doms;
    
    public XMLDomainParser(String xmlFile) {
        doms = new ArrayList();
        Document document = getSAXParsedDocument(xmlFile);

        Element rootNode = document.getRootElement();
        System.out.println("XMLDomainParse --> Root Element :: " + rootNode.getName());

        List<Element> list = rootNode.getChildren("Domain");

        for (int i = 0; i < list.size(); i++) {

            Element DomainNode = (Element) list.get(i);
            //Domain type
            String type = DomainNode.getChildText("Type");
            System.out.println("XMLDomainParse --> Type : " + DomainNode.getChildText("Type"));
            //Domain Name
            String name = DomainNode.getChildText("Name");
            System.out.println("XMLDomainParse --> Name : " + DomainNode.getChildText("Name"));

            //Domain Id
            Long id = Long.parseLong(DomainNode.getChildText("Id"));
            System.out.println("XMLDomainParse --> Id : " + id);

            //Domain IP
            String ip = DomainNode.getChildText("Ip");
            System.out.println("XMLDomainParse --> Ip : " + DomainNode.getChildText("Ip"));

            //Domain Port
            Long port = Long.parseLong(DomainNode.getChildText("Port"));
            System.out.println("XMLDomainParse --> Port : " + port);

            //insert element in ArrayList
            DomainElem el = new DomainElem(type, name, ip, port, id);

            doms.add(el);
        }
    }
  
    private static Document getSAXParsedDocument(final String fileName)
    {
        SAXBuilder builder = new SAXBuilder();
        Document document = null;
        try
        {
            document = builder.build(new File(fileName));
        }catch (JDOMException |NullPointerException | NumberFormatException | IOException e)
        {
            System.out.println("XMLDomainParse --> Error Parsing xml file");
            e.printStackTrace();
        }
        return document;
    }
    
    //get/set function
    public DomainElem getDomainElem() {
        if (doms.isEmpty()) 
            return new DomainElem();
        return doms.remove(0);
    }

    public void setDomainElem(DomainElem domelem) {
        doms.add(domelem);
    }

}
