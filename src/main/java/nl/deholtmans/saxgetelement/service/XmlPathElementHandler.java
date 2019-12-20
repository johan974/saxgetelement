package nl.deholtmans.saxgetelement.service;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class XmlPathElementHandler extends DefaultHandler {
    private String searchPaths[];
    private int searchIndex[];
    private int readIndex[];
    private int nextings[];
    private int readLevel = 0;
    private boolean matchingGetData = false;
    private boolean ignoring = false;
    private int ignoringLevel = 1;
    private String ignoringPath = "";
    private String result = "";
    private boolean stopping = false;
    private int nexting = -1;

    private XmlPathElementHandler() {
    }

    public XmlPathElementHandler(String[] searchPaths, int[] searchIndices, int[] nextings) {
        this.searchPaths = searchPaths;
        this.searchIndex = searchIndices;
        this.nextings = nextings;
        this.readIndex = new int[30];
        for (int i = 0; i < readIndex.length; i++) {
            readIndex[i] = 0;
        }
    }

    public String getResult() {
        return result.trim();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (stopping) {
            return;
        }
        if (!ignoring) {
            Logger.info("Searching " + searchPaths[readLevel] + "[" + readLevel + "," + readIndex[readLevel] + "] " + qName);
            if (nexting == readLevel ||
                    qName.equalsIgnoreCase(searchPaths[readLevel])) {
                Logger.info("Matching [ readlevel " + readIndex[readLevel] + "] " + qName +
                        ", searchindex = " + searchIndex[readLevel]);
                if (nexting == readLevel ||
                        searchIndex[readLevel] == readIndex[readLevel]) {
                    if (nexting <= 0 && nextings[readLevel] > 0) {
                        nexting = readLevel;
                        readIndex[readLevel]++;
                    } else {
                        // Is nexting, but not the right index
                        if( nexting > 0 ) {
                            if( readIndex[readLevel] == nextings[readLevel]) {
                                nexting = 0;
                                Logger.info("Matching.index[" + readLevel + "," + readIndex[readLevel] + "] " + qName);
                                if (readLevel == searchPaths.length - 1) { // last index
                                    Logger.info("Matching final[" + readLevel + "] " + qName);
                                    matchingGetData = true;
                                }
                            }
                            readIndex[readLevel]++;
                        } else {
                            Logger.info("Matching.index[" + readLevel + "," + readIndex[readLevel] + "] " + qName);
                            if (readLevel == searchPaths.length - 1) { // last index
                                Logger.info("Matching final[" + readLevel + "] " + qName);
                                matchingGetData = true;
                            }

                        }
                    }
                } else {
                    Logger.info("Matching prior, start ignoring at [" + readLevel + "] " + qName);
                    readIndex[readLevel]++;
                    ignoring = true;
                    ignoringLevel = readLevel;
                    ignoringPath = qName;
                    nexting = 0;
                }
            } else {
                Logger.info("Ignoring start [" + readLevel + "] " + qName);
                ignoring = true;
                ignoringLevel = readLevel;
                ignoringPath = qName;
                nexting = 0;
            }
        } else {
            Logger.info("Ignoring [" + readLevel + "] " + qName);
        }
        Logger.info("Readlevel up on [" + readLevel + "] " + qName);
        if (ignoring || nexting <= 0 || nextings[readLevel] < 1) {
            readLevel++;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (stopping) {
            return;
        }
        if (readLevel == nexting) {
            return;
        }
        Logger.info("Ending [" + readLevel + "] " + qName);
        readIndex[readLevel] = 0;
        readLevel--;
        if (ignoring) {
            Logger.info("Ignoring level downs [" + readLevel + "] " + qName +
                    ". Ignorelevel = " + ignoringLevel + ", ignorepath = " + ignoringPath);
            if (readLevel == ignoringLevel) {
                if (qName.equalsIgnoreCase(ignoringPath)) {
                    Logger.info("Ignoring final [" + readLevel + "] " + qName);
                    ignoring = false;
                }
            }
        }
    }

    @Override
    public void characters(char ch[], int start, int length) throws SAXException {
        if (stopping) {
            return;
        }
        if (matchingGetData) {
            result = new String(ch, start, length);
            stopping = true;
        }
    }
}