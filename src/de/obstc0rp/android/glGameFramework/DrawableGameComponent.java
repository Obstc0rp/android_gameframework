package de.obstc0rp.android.glGameFramework;

import javax.microedition.khronos.opengles.GL10;

public abstract class DrawableGameComponent {

	public Game game;

	public DrawableGameComponent(Game game){
		this.game = game;
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
	public abstract void update();
	
	/**
	 * Draws the GameComponent.
	 * @param canvas
	 */
	public abstract void draw(GL10 gl);
	
}
