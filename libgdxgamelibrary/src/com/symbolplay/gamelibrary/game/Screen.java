package com.symbolplay.gamelibrary.game;

import com.badlogic.gdx.utils.ObjectMap;

// based on Screen class from libgdx
public interface Screen {
    public void update(float delta);
    
    public void render();
    
    public void resize(int width, int height);
    
    public void show(ObjectMap<String, Object> changeParams);
    
    public void hide();
    
    public void pause();
    
    public void resume(ObjectMap<String, Object> changeParams);
    
    public void dispose();
    
    public boolean isTransparent();
}