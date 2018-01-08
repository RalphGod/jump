package com.symbolplay.gamelibrary.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;

// based on Game class from libgdx
public abstract class GameBase implements ApplicationListener {
    
    private static final float MAX_DELTA = 0.05f;
    private static final float UPDATE_RATE = 60.0f;
    private static final float UPDATE_STEP = 1.0f / UPDATE_RATE;
    
    private static final float UPDATE_STEP_MAX = UPDATE_STEP * 1.2f;
    
    private final ScreenManager screenManager;
    
    protected final Array<GameContainerUpdateable> gameContainerUpdateables;
    
    private float deltaAccumulator;
    
    public GameBase() {
        screenManager = new ScreenManager();
        gameContainerUpdateables = new Array<GameContainerUpdateable>(true, 10);
        deltaAccumulator = 0.0f;
    }
    
    @Override
    public void dispose() {
        screenManager.dispose();
    }
    
    @Override
    public void pause() {
        screenManager.pause();
    }
    
    @Override
    public void resume() {
        screenManager.resume(null);
    }
    
    @Override
    public void render() {
        // long javaHeap = Gdx.app.getJavaHeap();
        // long nativeHeap = Gdx.app.getNativeHeap();
        // Logger.debug("Java Heap: %d; Native Heap: %d", javaHeap, nativeHeap);
        
        screenManager.startFrame();
        
        float delta = Math.min(Gdx.graphics.getDeltaTime(), MAX_DELTA);
        
        deltaAccumulator += delta;
        while (deltaAccumulator > 0.0f) {
            float deltaStep = deltaAccumulator <= UPDATE_STEP_MAX ? deltaAccumulator : UPDATE_STEP;
            updateGameContainerUpdateables(deltaStep);
            screenManager.update(deltaStep);
            deltaAccumulator -= deltaStep;
        }
        
        screenManager.render();
        
        screenManager.endFrame();
    }
    
    private void updateGameContainerUpdateables(float delta) {
        for (GameContainerUpdateable gameContainerUpdateable : gameContainerUpdateables) {
            gameContainerUpdateable.update(delta);
        }
    }
    
    @Override
    public void resize(int width, int height) {
        screenManager.resize(width, height);
    }
    
    public void addScreen(String screenName, Screen screen) {
        screenManager.add(screenName, screen);
    }
    
    public Screen peekScreen() {
        return screenManager.peek();
    }
    
    public void pushScreen(String screenName, ObjectMap<String, Object> changeParams) {
        screenManager.push(screenName, changeParams);
    }
    
    public void pushScreen(String screenName) {
        pushScreen(screenName, null);
    }
    
    public void popScreen(ObjectMap<String, Object> changeParams) {
        screenManager.pop(changeParams);
    }
    
    public void popScreen() {
        popScreen(null);
    }
    
    public void changeScreen(String screenName, ObjectMap<String, Object> changeParams) {
        screenManager.change(screenName, changeParams);
    }
    
    public void changeScreen(String screenName) {
        changeScreen(screenName, null);
    }
}