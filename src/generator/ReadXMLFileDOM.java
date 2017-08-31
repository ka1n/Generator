/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator;

/**
 *
 * @author a.kirillov
 */
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ReadXMLFileDOM {
    public static ArrayList<Column> settings = new ArrayList<Column>();
    public static Page page = new Page();
    public static void readXML(String SettingsPath) {
        try {
 
            // Строим объектную модель исходного XML файла
            final File xmlFile = new File(SettingsPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
 
            // Выполнять нормализацию не обязательно, но рекомендуется
            doc.getDocumentElement().normalize();
      
            

            NodeList nodeList = doc.getElementsByTagName("page");
            for (int i = 0; i < nodeList.getLength(); i++) 
            {
                // Выводим информацию по каждому из найденных элементов
                Node node = nodeList.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    page.w=Integer.parseInt(element
                            .getElementsByTagName("width").item(0)
                            .getTextContent());
                    page.h=Integer.parseInt(element
                            .getElementsByTagName("height").item(0)
                            .getTextContent());

                    
                }
            }
            
            NodeList nodeListColumns = doc.getElementsByTagName("column");
            for (int i = 0; i < nodeListColumns.getLength(); i++) 
            {
                Column s = new Column();
                // Выводим информацию по каждому из найденных элементов
                Node node = nodeListColumns.item(i);
                if (Node.ELEMENT_NODE == node.getNodeType()) {
                    Element element = (Element) node;
                    s.title=element
                            .getElementsByTagName("title").item(0)
                            .getTextContent();
                    s.w=Integer.parseInt(element
                            .getElementsByTagName("width").item(0)
                            .getTextContent());
                    settings.add(s);
                   
                    
                }
            }
        } catch (ParserConfigurationException | SAXException
                | IOException ex) {
            Logger.getLogger(ReadXMLFileDOM.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
