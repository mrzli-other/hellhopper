package com.turbogerm.germlibrary.game;

// based on Screen class from libgdx
public interface Screen {
    public void update(float delta);
    
    public void render();
    
    public void resize(int width, int height);
    
    public void show();
    
    public void hide();
    
    public void pause();
    
    public void resume();
    
    public void dispose();
}