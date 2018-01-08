package com.symbolplay.gamelibrary.persistence;

import java.io.IOException;
import java.io.StringWriter;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ObjectMap;
import com.symbolplay.gamelibrary.util.EncryptionInputData;
import com.symbolplay.gamelibrary.util.Logger;

public final class JsonFileWriter {
    
    private final DataConverter dataConverter;
    
    public JsonFileWriter(EncryptionInputData encryptionInputData) {
        dataConverter = new DataConverter(encryptionInputData);
    }
    
    public void write(ObjectMap<String, Object> userDataValues, FileHandle file) {
        String jsonText = userDataValuesToJsonText(userDataValues);
        byte[] fileData = dataConverter.writeConvert(jsonText);
        file.writeBytes(fileData, false);
    }
    
    private static String userDataValuesToJsonText(ObjectMap<String, Object> userDataValues) {
        String jsonText = null;
        try {
            JsonWriter jsonWriter = null;
            try {
                jsonWriter = new JsonWriter(new StringWriter(10000));
                objectToJsonText(userDataValues, jsonWriter);
                jsonWriter.flush();
                jsonText = jsonWriter.getWriter().toString();
            } finally {
                if (jsonWriter != null) {
                    jsonWriter.close();
                }
            }
        } catch (IOException e) {
            Logger.error(e.getMessage());
        }
        
        return jsonText;
    }
    
    @SuppressWarnings("unchecked")
    private static void objectToJsonText(Object object, JsonWriter jsonWriter) throws IOException {
        Class<? extends Object> c = object.getClass();
        if (c == ObjectMap.class) {
            objectMapToJsonText((ObjectMap<String, Object>) object, jsonWriter);
        } else if (c == Array.class) {
            objectListToJsonText((Array<Object>) object, jsonWriter);
        } else {
            jsonWriter.value(object);
        }
    }
    
    private static void objectMapToJsonText(ObjectMap<String, Object> objectMap, JsonWriter jsonWriter) throws IOException {
        jsonWriter.object();
        
        for (String key : objectMap.keys()) {
            jsonWriter.name(key);
            objectToJsonText(objectMap.get(key), jsonWriter);
        }
        
        jsonWriter.pop();
    }
    
    private static void objectListToJsonText(Array<Object> objectList, JsonWriter jsonWriter) throws IOException {
        jsonWriter.array();
        
        for (Object object : objectList) {
            objectToJsonText(object, jsonWriter);
        }
        
        jsonWriter.pop();
    }
}
