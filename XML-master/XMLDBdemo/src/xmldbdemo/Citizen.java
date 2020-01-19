/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xmldbdemo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Dagi
 */
public class Citizen {
    int ssn;
    String firstName;
    String lastName;
    String address;
    String bdate;
    
    Citizen(int ssn, String fn, String ln, String ad, String bd){
        this.ssn = ssn;
        this.firstName = fn;
        this.lastName = ln;
        this.address = ad;
        this.bdate = bd;
    }
    
    public static String[] convertToStrings(ArrayList<Citizen> cz){
        String[] citizens = new String[cz.size()];
        for(int i =0; i < cz.size(); i++){
            String k = "SSN: "+ cz.get(i).ssn + "--" 
                    + "FIRST NAME: "+cz.get(i).firstName + "--"
                    + "LAST NAME: "+cz.get(i).lastName + "--"
                    + "ADDRESS: "+cz.get(i).address + "--"
                    + "BIRTHDATE: "+cz.get(i).bdate;
            
            citizens[i] = k;
        }
        
        return citizens;
    }
    
    public static ArrayList<Citizen> getCitizens(){
        
       ArrayList<Citizen> citizens = new ArrayList<Citizen>();
       try {
         File inputFile = new File("db.txt");
         DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder;

         dBuilder = dbFactory.newDocumentBuilder();

         Document doc = dBuilder.parse(inputFile);
         doc.getDocumentElement().normalize();

         XPath xPath =  XPathFactory.newInstance().newXPath();

         String expression = "/class/citizen";	        
         NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(
            doc, XPathConstants.NODESET);
         
         
         for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);
            System.out.println("\nCurrent Element :" + nNode.getNodeName());
            
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
               
                citizens.add(new Citizen(
                        Integer.parseInt(eElement.getAttribute("socialnumber")),
                        eElement.getElementsByTagName("firstname").item(0).getTextContent(),
                        eElement.getElementsByTagName("lastname").item(0).getTextContent(),
                        eElement.getElementsByTagName("address").item(0).getTextContent(),
                        eElement.getElementsByTagName("birthdate").item(0).getTextContent()
                ));
            }
         }
      } catch (ParserConfigurationException e) {
         e.printStackTrace();
      } catch (SAXException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      } catch (XPathExpressionException e) {
         e.printStackTrace();
      }
      return citizens;
   }

    
}
