package de.obstc0rp.android.gameFramework;

import android.graphics.Canvas;
import android.view.MotionEvent;

public interface Level {

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

	/**
	 * Will react when the Game class gets an touchEvent.
	 * @param event
	 */
	public void onTouchEvent(MotionEvent event);
}
