package com.turbogerm.hellhopper.dataaccess;

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
