package nl.deholtmans.saxgetelement;

import nl.deholtmans.saxgetelement.service.XmlElementsBySax;

import java.io.File;
import java.io.FileInputStream;

public class XMLParserSAX {

    public static void main(String[] args) {
        try {
            String filename = "Employees.xml";
            ClassLoader classLoader = new XMLParserSAX().getClass().getClassLoader();
            File file = new File(classLoader.getResource(filename).getFile());
            XmlElementsBySax xmlElementsBySax = new XmlElementsBySax();
            xmlElementsBySax.getElementFromInputStream(new FileInputStream(file));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
