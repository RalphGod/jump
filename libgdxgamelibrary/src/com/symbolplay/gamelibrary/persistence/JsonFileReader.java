package com.symbolplay.gamelibrary.persistence;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.ObjectMap;
import com.symbolplay.gamelibrary.util.EncryptionInputData;

public final class JsonFileReader {
    
    private final DataConverter dataConverter;
    
    public JsonFileReader(EncryptionInputData encryptionInputData) {
        dataConverter = new DataConverter(encryptionInputData);
    }
    
    @SuppressWarnings("unchecked")
    public ObjectMap<String, Object> read(FileHandle file) {
        byte[] fileData = file.readBytes();
        String jsonText = dataConverter.readConvert(fileData);
        JsonValue jsonValue = new JsonReader().parse(jsonText);
        return (ObjectMap<String, Object>) JsonReaderUtils.jsonValueToObject(jsonValue);
    }
}
