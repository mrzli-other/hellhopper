/*
 * The MIT License (MIT)
 * 
 * Copyright (c) 2013 Goran Mrzljak
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.turbogerm.helljump.dataaccess;

import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.XmlReader.Element;

final class ReaderUtilities {
    
    public static ObjectMap<String, String> getProperties(Element propertiesNode) {
        
        if (propertiesNode == null) {
            return null;
        }
        
        int numProperties = propertiesNode.getChildCount();
        ObjectMap<String, String> properties = new ObjectMap<String, String>(numProperties);
        for (int i = 0; i < numProperties; i++) {
            Element propertyNode = propertiesNode.getChild(i);
            String name = propertyNode.getAttribute("name");
            String value = propertyNode.getAttribute("value");
            properties.put(name, value);
        }
        
        return properties;
    }
    
    public static int getIntAttribute(Element node, String attributeName) {
        String attributeValue = node.getAttribute(attributeName);
        return Integer.parseInt(attributeValue);
    }
    
    public static int getIntAttribute(Element node, String attributeName, int defaultValue) {
        String attributeValue = node.getAttribute(attributeName, null);
        if (attributeValue != null) {
            return Integer.parseInt(attributeValue);
        } else {
            return defaultValue;
        }
    }
    
    public static float getFloatAttribute(Element node, String attributeName) {
        String attributeValue = node.getAttribute(attributeName);
        return Float.parseFloat(attributeValue);
    }
    
    public static float getFloatAttribute(Element node, String attributeName, float defaultValue) {
        String attributeValue = node.getAttribute(attributeName, null);
        if (attributeValue != null) {
            return Float.parseFloat(attributeValue);
        } else {
            return defaultValue;
        }
    }
}
