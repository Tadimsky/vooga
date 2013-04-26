package util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * This utility creates an XML document.
 * 
 * The use of attributes is avoided in this utility because they are more difficult to read
 * and to maintain. (Reference: w3schools.com)
 * 
 * @author Yoshida
 */
public class XMLTool {
    
    private static final String XML_PARAM_YES = "yes";
    private static final String DOC_CREATION_EXCEPTION =
            "Could not create a new instance of a Document.";
    private static final String INDENT_REFERENCE = "{http://xml.apache.org/xslt}indent-amount";
    private static final String CLOSE_EXCEPTION = "The writer could not be closed";
    private static final String CONVERSION_EXCEPTION =
            "The document could not be converted into a string";
    private static final String WRITING_EXCEPTION = "File could not be written.";
    private static final String USER_DIR = System.getProperty("user.dir");
    private Document myDoc;
    
    /**
     * The constructor of this XML file builder automatically creates a buffered
     * document with the destination of the argument path, ready to receive elements.
     */
    public XMLTool() {
        makeDoc();
    }
    
    /**
     * This constructor reads in an XML file from an XMLFilePath
     * 
     * @param xmlFilePath The relative path with "filename.xml".
     *        For example, if path = "/src/example.xml",
     *        the file example.xml will be saved in the source folder.
     */
    public XMLTool(String xmlFilePath) {
        readDoc(xmlFilePath);
    }
    
    /**
     * Creates a new document.
     */
    public void makeDoc () {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            myDoc = dbFactory.newDocumentBuilder().newDocument();
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(DOC_CREATION_EXCEPTION, e);
        }
    }
    
    /**
     * Reads a new document from an XML file.
     * 
     * @param path The relative path with "filename.xml". For example, if path = "/src/example.xml",
     *        the file example.xml will be saved in the source folder.
     */
    public void readDoc (String path) {
        File file = new File(USER_DIR + path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        try {
            myDoc = dbFactory.newDocumentBuilder().parse(file);
            
        }
        catch (IOException e) {
            throw new RuntimeException("Could not open file.", e);
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(DOC_CREATION_EXCEPTION, e);
        }
        catch (SAXException e) {
            throw new RuntimeException("XML document is corrupted.", e);
        }
    }
    
    /**
     * Gets the document being written in this XML Builder.
     * 
     * @return A document with an XML format.
     */
    public Document getDoc () {
        return myDoc;
    }
    
    /*
     * Creating new elements
     */
    
    /**
     * Creates the root of this document.
     * 
     * @param tag This parameter is the tag of the root
     * @return the root
     */
    public Element makeRoot (String tag) {
        Element root = myDoc.createElement(tag);
        myDoc.appendChild(root);
        return root;
    }
    
    /**
     * Creates a new empty element in the Doc.
     * 
     * @param tag The tag of the element
     * @return The recently created element.
     */
    public Element makeElement (String tag) {
        Element element = myDoc.createElement(tag);
        return element;
    }
    
    /**
     * Creates a new element in the Doc with a content.
     * 
     * @param tag The tag of the element
     * @param content The content of the element.
     * @return The recently created element.
     */
    public Element makeElement (String tag, String content) {
        Element element = makeElement(tag);
        element.setTextContent(content);
        return element;
    }
    
    /**
     * Creates a new elements tree from a Map.
     * 
     * @param parentTag The tag of the parent
     * @param elementsMap The keys of this map will be converted into tags and the values of the map
     *        into its values
     * @return the parent element with its respective tag.
     */
    public Element makeElementsFromMap (String parentTag, Map<String, String> elementsMap) {
        Element parent = makeElement(parentTag);
        return addChildrenFromMap(parent, elementsMap);
    }
    
    /*
     * Adding elements to parent element
     */
    
    /**
     * Adds a element child to an specific parent element
     * 
     * @param parent The parent element to which this method is going to add a child
     * @param child The element to be added as a child
     * @return The recently modified parent.
     */
    public Element addChild (Element parent, Element child) {
        parent.appendChild(child);
        return parent;
    }
    
    /**
     * Creates a new child element from a tag and adds it to an specific parent element
     * 
     * @param parent The parent element to which this method is going to add a child
     * @param tag The tag of the child node
     * @return The recently modified parent.
     */
    public Element addChild (Element parent, String tag) {
        Element child = makeElement(tag);
        return addChild(parent, child);
    }
    
    /**
     * Creates a new child element from a tag, adds a value to it and adds it to a parent element
     * 
     * @param parent The parent element to which this method is going to add a child
     * @param tag The tag of the child
     * @param value The value of the child
     * @return The recently modified parent.
     */
    public Element addChild (Element parent, String tag, String value) {
        Element child = makeElement(tag, value);
        return addChild(parent, child);
    }
    
    /**
     * Creates children and appends/adds them to a particular
     * parent element (passed in as a parameter)
     * 
     * @param parent The parent element to which this method is going to add children
     * @param elementsMap The keys of this map will be converted into tags and the values of the map
     *        into its values
     * @return The parent element.
     */
    public Element addChildrenFromMap (Element parent, Map<String, String> elementsMap) {
        for (String name : elementsMap.keySet()) {
            String value = elementsMap.get(name);
            addChild(parent, name, value);
        }
        return parent;
    }
    
    /*
     * Getter methods.
     */
    
    /**
     * This method returns the first element in the document with an specific tag.
     * Be careful with this method! If you have many instances of the same tag, use
     * getElementsListByTagName, which will return a list of elements with that same tag, in the
     * top-down reading order.
     * 
     * @param tag The name of the tag to match on. The special value "*" matches all tags. For XML,
     *        the tag parameter is case-sensitive, otherwise it depends on the case-sensitivity
     *        of the mark up language in use.
     * @return The FIRST element with the tag.
     */
    public Element getElement (String tag) {
        return (Element) myDoc.getElementsByTagName(tag).item(0);
    }
    
    /**
     * Returns a list of elements with the tag from an specific element.
     * 
     * @param parent The parent element from which the element
     * @param tag The name of the tag to match on. The special value "*" matches all tags. For XML,
     *        the tag parameter is case-sensitive, otherwise it depends on the case-sensitivity
     *        of the mark up language in use.
     * 
     * @return A list of elements with the tag.
     */
    public List<Element> getElementList (Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        return convertNodeList(nodes);
    }
    
    /**
     * Returns a list of elements with the tag from the XML document.
     * 
     * @param tag The name of the tag to match on. The special value "*" matches all tags. For XML,
     *        the tag parameter is case-sensitive, otherwise it depends on the case-sensitivity
     *        of the mark up language in use.
     * @return A list of elements with the tag.
     */
    public List<Element> getElementList (String tag) {
        NodeList nodes = myDoc.getElementsByTagName(tag);
        return convertNodeList(nodes);
    }
    
    /**
     * This method return the tag name of an element.
     * 
     * @param element The element containing a tag
     * @return the tag of the element as a string
     */
    public String getTagName (Element element) {
        return element.getTagName();
    }
    
    /**
     * This method returns the value of an element as a String.
     * 
     * @param element The element from which the content is being extracted.
     * @return A string stores in the element.
     */
    public String getContent (Element element) {
        return element.getTextContent();
    }
    
    /**
     * Gets the content of the FIRST element associated with the referent tag.
     * If the tag refers to a non-leaf node, it appends all the values from its children.
     * 
     * @param tag A string with the tag of the element.
     * @return the content(value) of the element.
     */
    public String getContent (String tag) {
        return getContent(getElement(tag));
    }
    
    /**
     * Creates a map with the child tag (as a map key) and a child element (as a map value) of all
     * the
     * children elements of a particular parent element.
     * If the parent element does not contain children, this method returns an empty map.
     * 
     * @param parent The parent element node from which the children elements will be created.
     * @return a map with the child tag (as a map key) and a child element (as a map value) of all
     *         the children elements of a particular parent element.
     */
    public Map<String, Element> getChildrenElementMap (Element parent) {
        Map<String, Element> map = new HashMap<String, Element>();
        NodeList nodes = parent.getChildNodes();
        for (int i = 0; i < nodes.getLength(); i++) {
            if (nodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element node = (Element) nodes.item(i);
                map.put(node.getTagName(), node);
            }
        }
        return map;
    }
    
    /**
     * Creates a map with the child tag (as a map key) and a child element (as a map value) of all
     * the children elements of a particular parent element tag.
     * If the parent element does not contain children, this method returns an empty map.
     * 
     * @param parentTag The tag of the parent element node.
     * @return a map with the child tag (as a map key) and a child element (as a map value) of all
     *         the children elements of a particular parent element.
     */
    public Map<String, Element> getChildrenElementMap (String parentTag) {
        Element parent = getElement(parentTag);
        return getChildrenElementMap(parent);
    }
    
    /**
     * Creates a map with the tag (as a map key) and the content (as a map value) of all the
     * children elements of a particular node.
     * If the parent element does not contain children, this method returns an empty map.
     * 
     * @param parent The parent element node.
     * @return a map with the tag (as a map key) and the content (as a map value) of all the
     *         children elements of a particular node.
     */
    public Map<String, String> getChildrenStringMap (Element parent) {
        Map<String, String> stringMap = new HashMap<String, String>();
        Map<String, Element> elementMap = getChildrenElementMap(parent);
        for (String key : elementMap.keySet()) {
            stringMap.put(key, elementMap.get(key).getTextContent());
        }
        return stringMap;
    }
    
    /**
     * Creates a map with the tag (as a map key) and the content (as a map value) of all the
     * children elements of the first node with a particular tag.
     * If the parent element does not contain children, this method returns an empty map.
     * 
     * @param parentTag The tag of the parent element node.
     * @return a map with the tag (as a map key) and the content (as a map value) of all the
     *         children elements of a particular node.
     */
    public Map<String, String> getChildrenStringMap (String parentTag) {
        Element parent = getElement(parentTag);
        return getChildrenStringMap(parent);
    }
    
    /**
     * Searches for all parent elements with the same tag and then generates a list containing maps
     * of their respective children.
     * Creates maps with the tag (as a map key) and the content (as a map value) of all the
     * children elements of a particular node.
     * The list preserves the top-down numerical order in which the tag is found in the XML
     * document.
     * 
     * @param parentsTag The tag of the parent elements to be searched.
     * @return a list of maps. The map contains the tag of the children elements as a key and the
     *         children content as a value. Map<key, value> = Map<childTag, childValue> =
     *         Map<String, String>;
     */
    public List<Map<String, Element>> getMapList (String parentsTag) {
        List<Element> nodeList = getElementList(parentsTag);
        List<Map<String, Element>> listOfMaps =
                new ArrayList<Map<String, Element>>(nodeList.size());
        for (int i = 0; i < nodeList.size(); i++) {
            listOfMaps.add(getChildrenElementMap(nodeList.get(i)));
        }
        return listOfMaps;
    }
    
    /*
     * Other functionalities
     */
    
    /**
     * Converts a NodeList into a list of Elements.
     * 
     * @param nodeList NodeList containing elements
     * @return A list with the elements of nodeList.
     */
    public List<Element> convertNodeList (NodeList nodeList) {
        List<Element> elementList = new ArrayList<Element>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeType() == Node.ELEMENT_NODE) {
                elementList.add((Element) nodeList.item(i));
            }
        }
        return elementList;
    }
    
    /*
     * Writing to file, will be replaced by the secretary program
     */
    /**
     * Translates a document into an XML formatted String.
     * 
     * @param doc document with its elements.
     * @return String with XML formatting
     * @throws TransformerException This exception is thrown when a Document object cannot be
     *         converted into a String
     */
    public String translateToXMLString (Document doc) throws TransformerException {
        DOMSource source = new DOMSource(doc);
        
        TransformerFactory factory = TransformerFactory.newInstance();
        Transformer transformer = factory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, XML_PARAM_YES);
        transformer.setOutputProperty(OutputKeys.INDENT, XML_PARAM_YES);
        transformer.setOutputProperty(INDENT_REFERENCE, "4");
        
        StringWriter sw = new StringWriter();
        StreamResult result = new StreamResult(sw);
        transformer.transform(source, result);
        String xmlString = sw.toString();
        return xmlString;
    }
    
    /**
     * This <code>FileWriter</code> creates the document in an specific location,
     * form an String.
     * 
     * @param path The relative path with "filename.xml". For example, if path = "/src/example.xml",
     *        the file example.xml will be saved in the source folder.
     */
    public void writeFile (String path) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(USER_DIR + path);
            writer.write(translateToXMLString(myDoc));
            writer.close();
        }
        catch (TransformerException e) {
            try {
                writer.close();
            }
            catch (IOException e1) {
                throw new RuntimeException(CLOSE_EXCEPTION, e);
            }
            throw new RuntimeException(CONVERSION_EXCEPTION, e);
        }
        catch (IOException e) {
            throw new RuntimeException(WRITING_EXCEPTION, e);
        }
    }
    
}
