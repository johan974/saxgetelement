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

    String searchPaths[];
    int searchIndex[];
    int nextings[];


    public String getElementFromInputSource( InputSource inputSource) {
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(inputSource, handler);
            return printEmployees(handler);
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
            return printEmployees(handler);
        } catch( Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    public String getElementDataByPath(String[] path, String xml) {
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            prepareSearchingFromPath( path);
            XmlPathElementHandler handler = new XmlPathElementHandler( searchPaths, searchIndex, nextings);
            saxParser.parse(new InputSource(new StringReader(xml)), handler);
            return handler.getResult();
        } catch( Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    public String getElementDataByPath(String path, String xml) {
        String parts[] = path.substring( 1).split( "/");
        return getElementDataByPath( parts, xml);
    }

    public String getElementFromInputStream( InputStream input) {
        try {
            SAXParser saxParser = saxParserFactory.newSAXParser();
            MyHandler handler = new MyHandler();
            saxParser.parse(input, handler);
            return printEmployees(handler);
        } catch( Exception e) {
            e.printStackTrace();
            return "NOK";
        }
    }

    private void prepareSearchingFromPath( String[] searchCommand) {
        searchPaths = new String[0];
        searchIndex = new int[0];
        if( searchCommand == null || searchCommand.length < 1) {
            return ;
        }
        searchPaths = new String[ searchCommand.length];
        searchIndex = new int[ searchCommand.length];
        nextings = new int[ searchCommand.length];
        for( int i = 0; i < searchCommand.length; i++) {
            String searchCommandCurrent = searchCommand[i];
            String[] parts = searchCommandCurrent.split( ">");
            if( parts.length > 1) {
                searchPaths[i] = parts[0];
                searchIndex[i] = 0;
                nextings[i] = Integer.parseInt( parts[1]);
            } else {
                parts = searchCommandCurrent.split("#");
                if (parts.length <= 1) {
                    searchPaths[i] = searchCommandCurrent;
                    searchIndex[i] = 0;
                } else {
                    searchPaths[i] = parts[0];
                    searchIndex[i] = Integer.parseInt(parts[1]) - 1;
                }
            }
        }
    }

    private String printEmployees(MyHandler handler) {
        List<Employee> empList = handler.getEmpList();
        for (Employee emp : empList) {
            System.out.println(emp);
        }
        return "OK";
    }


}
