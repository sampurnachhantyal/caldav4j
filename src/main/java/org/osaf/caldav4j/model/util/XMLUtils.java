/*
 * Copyright 2005 Open Source Applications Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.osaf.caldav4j.model.util;

import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.DOMStringList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSSerializer;

public class XMLUtils {
    private static final Log log = LogFactory.getLog(XMLUtils.class);
    
    private static DOMImplementation implementation = null;
    
    static {
        try {
            DOMImplementationRegistry registry = DOMImplementationRegistry
                    .newInstance();
            implementation = registry.getDOMImplementation("XML 3.0");
        } catch (Exception e) {
            throw new RuntimeException(
                    "Could not instantiate a DOMImplementation!", e);
        }
    }
    
    /**
     * Creates a new xml DOM Document using a DOM 3.0 DOM Implementation
     * 
     * @param namespaceURI
     *            the default XML Namespace for the document
     * @param qualifiedName
     *            the qualified name of the root element
     * @return a new document
     */
    public static Document createNewDocument(String namespaceURI,
            String qualifiedName) {

        Document document = implementation.createDocument(namespaceURI,
                qualifiedName, null);
        return document;
    }
    
    /**
     * Serializes a DOM Document to XML 
     * @param document a DOM document
     * @return the Document serialized to XML
     */
    public static String toXML(Document document){
        DOMImplementationLS domLS = (DOMImplementationLS) implementation;
        LSSerializer serializer = domLS.createLSSerializer();
        String s = serializer.writeToString(document);
        
        return s;
    }    
    
    public static String toPrettyXML(Document document) {
        StringWriter stringWriter = new StringWriter();
        OutputFormat outputFormat = new OutputFormat(document, null, true);
        XMLSerializer xmlSerializer = new XMLSerializer(stringWriter,
                outputFormat);
        xmlSerializer.setNamespaces(true);
        try {
            xmlSerializer.asDOMSerializer().serialize(document);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return stringWriter.toString();

    }
    
    public static DOMImplementation getDOMImplementation(){
        return implementation;
    }
    
}