package nl.deholtmans.saxgetelement.service;

import org.junit.Test;
import org.xml.sax.InputSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import static org.junit.Assert.*;

public class XmlElementsBySaxTest {
    @Test
    public void testFromFile( ) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("data/Employees.xml").getFile());
            XmlElementsBySax xmlElementsBySax = new XmlElementsBySax();
            xmlElementsBySax.getElementFromInputStream(new FileInputStream(file));
        } catch( Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFromString() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            String content = new Scanner(new File(classLoader.getResource("data/Employees.xml").getFile())).useDelimiter("\\Z").next();
            XmlElementsBySax xmlElementsBySax = new XmlElementsBySax();
            xmlElementsBySax.getElementFromString( content);
        } catch( Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testFromInputStream() {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            File f= new File(classLoader.getResource("data/Employees.xml").getFile());
            InputSource inputSource = new InputSource(new InputStreamReader(new FileInputStream(f)));
            XmlElementsBySax xmlElementsBySax = new XmlElementsBySax();
            xmlElementsBySax.getElementFromInputSource( inputSource);
        } catch( Exception e) {
            e.printStackTrace();
        }
    }



}