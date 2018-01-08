package com.symbolplay.gamelibrary.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.ObjectMap;

public final class ScreenManager {
    
    private static final int OPERATION_TYPE_PUSH = 0;
    private static final int OPERATION_TYPE_POP = 1;
    private static final int OPERATION_TYPE_CHANGE = 2;
    
    private final ArrayMap<String, Screen> screens;
    private final Array<Screen> screenStack;
    
    private boolean isInFrame;
    private ScreenChangeOperation queuedOperation;
    
    public ScreenManager() {
        screens = new ArrayMap<String, Screen>(20);
        screenStack = new Array<Screen>(true, 5);
        isInFrame = false;
        queuedOperation = null;
    }
    
    public void add(String screenName, Screen screen) {
        screens.put(screenName, screen);
    }
    
    public void startFrame() {
        isInFrame = true;
    }
    
    public void endFrame() {
        isInFrame = false;
        executeQueuedOperation();
    }
    
    private void executeQueuedOperation() {
        if (queuedOperation == null) {
            return;
        }
        
        if (queuedOperation.operationType == OPERATION_TYPE_PUSH) {
            push(queuedOperation.screenName, queuedOperation.changeParams);
        } else if (queuedOperation.operationType == OPERATION_TYPE_POP) {
            pop(queuedOperation.changeParams);
        } else if (queuedOperation.operationType == OPERATION_TYPE_CHANGE) {
            change(queuedOperation.screenName, queuedOperation.changeParams);
        }
        queuedOperation = null;
    }
    
    public void update(float delta) {
        if (screenStack.size > 0) {
            screenStack.peek().update(delta);
        }
    }
    
    public void render() {
        if (screenStack.size == 0) {
            return;
        }
        
        int firstRenderIndex = getFirstRenderIndex();
        for (int i = firstRenderIndex; i < screenStack.size; i++) {
            screenStack.get(i).render();
        }
    }
    
    private int getFirstRenderIndex() {
        for (int i = screenStack.size - 1; i >= 0; i--) {
            if (!screenStack.get(i).isTransparent()) {
                return i;
            }
        }
        
        return 0;
    }
    
    public void resize(int width, int height) {
        for (int i = 0; i < screenStack.size; i++) {
            screenStack.get(i).resize(width, height);
        }
    }
    
    public void resume(ObjectMap<String, Object> changeParams) {
        if (screenStack.size > 0) {
            screenStack.peek().resume(changeParams);
        }
    }
    
    public void pause() {
        if (screenStack.size > 0) {
            screenStack.peek().pause();
        }
    }
    
    public Screen peek() {
        return screenStack.size > 0 ? screenStack.peek() : null;
    }
    
    public void push(String screenName, ObjectMap<String, Object> changeParams) {
        if (!isInFrame) {
            pause();
            pushInternal(screenName, changeParams);
        } else if (queuedOperation == null) {
            queuedOperation = new ScreenChangeOperation(OPERATION_TYPE_PUSH, screenName, changeParams);
        }
    }
    
    public void pop(ObjectMap<String, Object> changeParams) {
        if (!isInFrame) {
            popInternal();
            resume(changeParams);
        } else if (queuedOperation == null) {
            queuedOperation = new ScreenChangeOperation(OPERATION_TYPE_POP, "", changeParams);
        }
    }
    
    public void change(String screenName, ObjectMap<String, Object> changeParams) {
        if (!isInFrame) {
            popInternal();
            pushInternal(screenName, changeParams);
        } else if (queuedOperation == null) {
            queuedOperation = new ScreenChangeOperation(OPERATION_TYPE_CHANGE, screenName, changeParams);
        }
    }
    
    private void pushInternal(String screenName, ObjectMap<String, Object> changeParams) {
        Screen screen = screens.get(screenName);
        screenStack.add(screen);
        screen.show(changeParams);
        screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }
    
    private void popInternal() {
        if (screenStack.size == 0) {
            return;
        }
        screenStack.peek().hide();
        screenStack.pop();
    }
    
    public void dispose() {
        while (screenStack.size > 0) {
            popInternal();
        }
        
        for (Screen screen : screens.values()) {
            screen.dispose();
        }
    }
    
    private static class ScreenChangeOperation {
        private final int operationType;
        private final String screenName;
        private final ObjectMap<String, Object> changeParams;
        
        public ScreenChangeOperation(int operationType, String screenName, ObjectMap<String, Object> changeParams) {
            this.operationType = operationType;
            this.screenName = screenName;
            this.changeParams = changeParams;
        }
    }
}
