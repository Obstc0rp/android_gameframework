package de.obstc0rp.android.gameFramework;


public abstract class AbstractDrawableGameComponent implements DrawableGameComponent{

	protected Game game;
	
	/**
	 * Sets the game
	 * @param game
	 */
	public AbstractDrawableGameComponent(Game game){
		this.game = game;
	}
}
