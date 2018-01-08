package com.symbolplay.gamelibrary.util;

public final class Profiling {
    
    private static final int NUM_GROUPS = 10;
    
    private static long[] startTimes;
    private static long[] maxTimes;
    
    static {
        startTimes = new long[NUM_GROUPS];
        maxTimes = new long[NUM_GROUPS];
    }
    
    public static void startTime(int group) {
        startTimes[group] = System.nanoTime();
    }
    
    public static void endTime(int group, String message, boolean isShowMaxTime) {
        long endTime = System.nanoTime();
        long duration = endTime - startTimes[group];
        Logger.info("%s: %f ms", message, duration / 1e6);
        
        if (isShowMaxTime) {
            maxTimes[group] = Math.max(duration, maxTimes[group]);
            Logger.info("%s (max time): %f ms", message, maxTimes[group] / 1e6);
        }
    }
    
    public static void resetMaxTime(int group) {
        maxTimes[group] = 0;
    }
}
