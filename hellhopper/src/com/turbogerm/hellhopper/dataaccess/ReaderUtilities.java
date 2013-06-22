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
}
