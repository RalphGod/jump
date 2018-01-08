package com.symbolplay.gamelibrary.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public final class CameraData {
    
    private final float minViewportWidth;
    private final float minViewportHeight;
    private final float pixelToMeter;
    
    private final Rectangle guiCameraRect;
    private final Rectangle nonOffsetedGameCameraRect;
    
    private final OrthographicCamera guiCamera;
    private final OrthographicCamera gameAreaCamera;
    private final Viewport guiViewport;
    
    private final Vector2 initialGameAreaCameraPosition;
    
    public CameraData(float screenWidth, float screenHeight, float minViewportWidth, float minViewportHeight, float pixelToMeter) {
        
        this.minViewportWidth = minViewportWidth;
        this.minViewportHeight = minViewportHeight;
        this.pixelToMeter = pixelToMeter;
        
        guiCameraRect = new Rectangle();
        nonOffsetedGameCameraRect = new Rectangle();
        
        guiCamera = new OrthographicCamera();
        gameAreaCamera = new OrthographicCamera();
        guiViewport = new ExtendViewport(minViewportWidth, minViewportHeight, guiCamera);
        
        initialGameAreaCameraPosition = new Vector2();
        
        resize(screenWidth, screenHeight);
    }
    
    public void resize(float screenWidth, float screenHeight) {
        float screenAspectRatio = screenWidth / screenHeight;
        float gameAreaAspectRatio = minViewportWidth / minViewportHeight;
        if (screenAspectRatio <= gameAreaAspectRatio) {
            guiCameraRect.width = minViewportWidth;
            guiCameraRect.height = guiCameraRect.width / screenAspectRatio;
            guiCameraRect.x = 0.0f;
            guiCameraRect.y = (minViewportHeight - guiCameraRect.height) / 2.0f;
        } else {
            guiCameraRect.height = minViewportHeight;
            guiCameraRect.width = guiCameraRect.height * screenAspectRatio;
            guiCameraRect.x = (minViewportWidth - guiCameraRect.width) / 2.0f;
            guiCameraRect.y = 0.0f;
        }
        
        guiCamera.setToOrtho(false, guiCameraRect.width, guiCameraRect.height);
        guiCamera.translate(guiCameraRect.x, guiCameraRect.y);
        //guiCamera.update(); // viewport update will also update camera
        
        guiViewport.update((int) screenWidth, (int) screenHeight);
        
        nonOffsetedGameCameraRect.x = guiCameraRect.x * pixelToMeter;
        nonOffsetedGameCameraRect.y = guiCameraRect.y * pixelToMeter;
        nonOffsetedGameCameraRect.width = guiCameraRect.width * pixelToMeter;
        nonOffsetedGameCameraRect.height = guiCameraRect.height * pixelToMeter;
        
        gameAreaCamera.setToOrtho(false, nonOffsetedGameCameraRect.width, nonOffsetedGameCameraRect.height);
        gameAreaCamera.translate(nonOffsetedGameCameraRect.x, nonOffsetedGameCameraRect.y);
        gameAreaCamera.update();
        
        initialGameAreaCameraPosition.set(gameAreaCamera.position.x, gameAreaCamera.position.y);
    }
    
    public void setGameAreaPosition(float x, float y) {
        gameAreaCamera.position.x = initialGameAreaCameraPosition.x + x;
        gameAreaCamera.position.y = initialGameAreaCameraPosition.y + y;
        gameAreaCamera.update();
    }
    
    public Rectangle getGuiCameraRect() {
        return guiCameraRect;
    }
    
    public Rectangle getNonOffsetedGameCameraRect() {
        return nonOffsetedGameCameraRect;
    }
    
    public OrthographicCamera getGuiCamera() {
        return guiCamera;
    }
    
    public Viewport getGuiViewport() {
        return guiViewport;
    }
    
    public Matrix4 getGuiMatrix() {
        return guiCamera.combined;
    }
    
    public Matrix4 getGameAreaMatrix() {
        return gameAreaCamera.combined;
    }
}
