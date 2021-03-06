package com.symbolplay.gamelibrary.util;

import com.badlogic.gdx.Gdx;

public final class Logger {
    
    private static String TAG = "SymbolPlay";
    
    public static void setTag(String tag) {
        TAG = tag;
    }
    
    public static void debug(String message) {
        Gdx.app.debug(TAG, message);
    }
    
    public static void debug(String format, Object... args) {
        final String message = String.format(format, args);
        debug(message);
    }
    
    public static void info(String message) {
        Gdx.app.log(TAG, message);
    }
    
    public static void info(String format, Object... args) {
        final String message = String.format(format, args);
        info(message);
    }
    
    public static void error(String message) {
        Gdx.app.error(TAG, message);
    }
    
    public static void error(String format, Object... args) {
        final String message = String.format(format, args);
        error(message);
    }
}