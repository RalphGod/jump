package com.symbolplay.gamelibrary.persistence;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.ObjectMap;
import com.symbolplay.gamelibrary.util.EncryptionInputData;
import com.symbolplay.gamelibrary.util.Logger;

public abstract class UserDataBase {
    
    private final FileHandle file;
    private final int version;
    
    private ObjectMap<String, Object> values;
    
    private final JsonFileReader jsonFileReader;
    private final JsonFileWriter jsonFileWriter;
    
    public UserDataBase(FileHandle fileHandle, int version, EncryptionInputData encryptionInputData) {
        file = fileHandle;
        this.version = version;
        
        jsonFileReader = new JsonFileReader(encryptionInputData);
        jsonFileWriter = new JsonFileWriter(encryptionInputData);
    }
    
    public final void read() {
        if (file.exists()) {
            try {
                values = jsonFileReader.read(file);
                values = update(values, version);
            } catch (Exception e) {
                
                // TODO: remove this after all people update from tria app version 1.0.0
                Logger.info("Error reading user data - will try special read on user data if possible...");
                values = readSpecial(file);
                if (values != null) {
                    Logger.info("Special read of user data successful!");
                    values = update(values, version);
                    return;
                }
                
                Logger.error("Exception while reading user data: " + e.getMessage());
                values = getDefault();
                write();
            }
        } else {
            values = getDefault();
            write();
        }
    }
    
    public final void write() {
        jsonFileWriter.write(values, file);
    }
    
    // TODO: remove this after all people update from version 1
    protected ObjectMap<String, Object> readSpecial(FileHandle file) {
        return null;
    }
    
    private final ObjectMap<String, Object> update(ObjectMap<String, Object> values, int targetVersion) {
        int currentVersion = getVersion(values);
        while (currentVersion < targetVersion) {
            values = updateBySingleVersion(values, currentVersion);
            currentVersion = getVersion(values);
        }
        
        return values;
    }
    
    private static int getVersion(ObjectMap<String, Object> userDataValues) {
        return ((Long) userDataValues.get("version")).intValue();
    }
    
    protected abstract ObjectMap<String, Object> updateBySingleVersion(ObjectMap<String, Object> values, int currentVersion);
    
    protected abstract ObjectMap<String, Object> getDefault();
    
    public final ObjectMap<String, Object> getValues() {
        return values;
    }
}
