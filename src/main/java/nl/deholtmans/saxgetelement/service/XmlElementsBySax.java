package nl.deholtmans.saxgetelement.service;

import nl.deholtmans.saxgetelement.XMLParserSAX;
import nl.deholtmans.saxgetelement.model.Employee;
import org.xml.sax.InputSource;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.*;
import java.util.List;

public class XmlElementsBySax {
    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();

    public String getElementFromInputSource( InputSource inputSource) {
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(inputSource, handler);
            List<Employee> empList = handler.getEmpList();
            for (Employee emp : empList) {
                System.out.println(emp);
            }
            return "OK";
        } catch( Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }


    public String getElementFromString( String xml) {
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(new InputSource(new StringReader(xml)), handler);
            List<Employee> empList = handler.getEmpList();
            for (Employee emp : empList) {
                System.out.println(emp);
            }
            return "OK";
        } catch( Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    public String getElementFromInputStream( InputStream input) {
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(input, handler);
            List<Employee> empList = handler.getEmpList();
            for (Employee emp : empList) {
                System.out.println(emp);
            }
            return "OK";
        } catch( Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

}
