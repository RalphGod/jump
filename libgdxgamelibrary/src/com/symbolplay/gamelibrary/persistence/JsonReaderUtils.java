package com.symbolplay.gamelibrary.persistence;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;

public final class JsonReaderUtils {
    
    // READING
    public static Object jsonValueToObject(JsonValue jsonValue) {
        switch (jsonValue.type()) {
            case object:
                return jsonValueToObjectMap(jsonValue);
            case array:
                return jsonValueToObjectList(jsonValue);
            case stringValue:
                return jsonValue.asString();
            case doubleValue:
                return jsonValue.asDouble();
            case longValue:
                return jsonValue.asLong();
            case booleanValue:
                return jsonValue.asBoolean();
            case nullValue:
            default:
                return null;
        }
    }
    
    private static ObjectMap<String, Object> jsonValueToObjectMap(JsonValue jsonValue) {
        ObjectMap<String, Object> objectMap = new ObjectMap<String, Object>(jsonValue.size);
        
        for (JsonValue currentValue = jsonValue.child; currentValue != null; currentValue = currentValue.next) {
            String name = currentValue.name();
            Object object = jsonValueToObject(currentValue);
            objectMap.put(name, object);
        }
        
        return objectMap;
    }
    
    private static Array<Object> jsonValueToObjectList(JsonValue jsonValue) {
        Array<Object> objectList = new Array<Object>(true, jsonValue.size);
        
        for (JsonValue currentValue = jsonValue.child; currentValue != null; currentValue = currentValue.next) {
            Object object = jsonValueToObject(currentValue);
            objectList.add(object);
        }
        
        return objectList;
    }
    // END READING
}
