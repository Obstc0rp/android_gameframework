package de.obstc0rp.android.glGameFramework;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.view.MotionEvent;

public abstract class Level {
	
	public Game game;
	private List<DrawableGameComponent> drawableGameComponents;
	
	public Level(Game game){
		this.game = game;
		drawableGameComponents = new ArrayList<DrawableGameComponent>();
	}
	
	/**
	 * Loads all content this GameComponent needs.
	 */
	public abstract void loadContent();
	
	/**
	 * Unloads the content when game is over or GameComponent isn't needed anymore.
	 */
	public abstract void unloadContent();
	
	/**
	 * Updates the GameComponent.
	 */
	public void update(){
		for(DrawableGameComponent dgc: drawableGameComponents){
			dgc.update();
		}
	}
	
	/**
	 * Draws the GameComponent.
	 * @param canvas
	 */
	public void draw(GL10 gl){
		for(DrawableGameComponent dgc: drawableGameComponents){
			dgc.draw(gl);
		}
	}
	
	public abstract void onSurfaceCreated(GL10 gl);
//	{
//		for(DrawableGameComponent dgc: drawableGameComponents){
//			dgc.onSurfaceCreated(gl);
//		}
		
		
//	}

	/**
	 * Will react when the Game class gets an touchEvent.
	 * @param event
	 */
	public abstract void onTouchEvent(MotionEvent event);
	
	/**
	 * Adds a DrawableGameComponent
	 * @param gameComponent
	 */
	public void addDrawableGameComponent(DrawableGameComponent gameComponent) {
		synchronized (game.getHolder()) {
			gameComponent.loadContent();
			this.drawableGameComponents.add(gameComponent);
		}
	}
	
	/**
	 * Deletes the GameComponent which should expire.
	 * @param gameComponent
	 */
	public void deleteDrawableGameComponent(DrawableGameComponent gameComponent) {
		synchronized (game.getHolder()) {
			gameComponent.unloadContent();
			this.drawableGameComponents.remove(gameComponent);
		}
	}
}
