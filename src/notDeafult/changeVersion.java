package notDeafult;
import java.io.File;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import javax.xml.xpath.*;

import javafx.concurrent.Task;
import org.w3c.dom.*;



public class changeVersion
{
    public void change(File inputFile, int newVersion) throws Exception
    {
        Task<Void> task = new Task<Void>(){
            @Override protected Void call() throws Exception {
                File outputFile = new File(inputFile.getParent()+"/updatedVersion.xml");
                int updatedVersion = newVersion;

                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = domFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(inputFile);

                XPathFactory xpathFactory = XPathFactory.newInstance();
                XPath xpath = xpathFactory.newXPath();
                XPathExpression expr = xpath.compile("/PremiereData/Project/@Version");

                NodeList versionAttrNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

                for (int i = 0; i < versionAttrNodes.getLength(); i++) {
                    Attr versionAttr = (Attr) versionAttrNodes.item(i);
                    System.out.println(versionAttr.getValue());
                    versionAttr.setNodeValue(String.valueOf(updatedVersion));
                }

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                Transformer transformer = transformerFactory.newTransformer();

                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.transform(new DOMSource(doc), new StreamResult(outputFile));

                return null;
             }
        };
        task.run();


    }

    public int getInfo(File inputFile) throws Exception{
        final int[] currentVersion = {0};
        Task<Void> task2 = new Task<Void>(){
            @Override protected Void call() throws Exception {


                DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder docBuilder = domFactory.newDocumentBuilder();
                Document doc = docBuilder.parse(inputFile);

                XPathFactory xpathFactory = XPathFactory.newInstance();
                XPath xpath = xpathFactory.newXPath();
                XPathExpression expr = xpath.compile("/PremiereData/Project/@Version");

                NodeList versionAttrNodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);

                for (int i = 0; i < versionAttrNodes.getLength(); i++) {
                    Attr versionAttr = (Attr) versionAttrNodes.item(i);
                    currentVersion[0] = Integer.parseInt(versionAttr.getValue());
                }
                return null;
            }
        };
        task2.run();


        return currentVersion[0];

    }
}