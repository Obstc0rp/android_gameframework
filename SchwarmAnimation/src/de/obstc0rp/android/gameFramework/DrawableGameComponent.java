package de.obstc0rp.android.gameFramework;

import android.graphics.Canvas;

public interface DrawableGameComponent {

	/**
	 * Loads all content this GameComponent needs.
	 */
	public void loadContent();
	
	/**
	 * Unloads the content when game is over or GameComponent isn't needed anymore.
	 */
	public void unloadContent();
	
	/**
	 * Updates the GameComponent.
	 */
	public void update();
	
	/**
	 * Draws the GameComponent.
	 * @param canvas
	 */
	public void draw(Canvas canvas);
}
