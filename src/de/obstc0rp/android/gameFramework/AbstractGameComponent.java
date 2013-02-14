package de.obstc0rp.android.gameFramework;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;

public abstract class AbstractGameComponent implements GameComponent{

	protected Game game;
	private List<DrawableGameComponent> drawableGameComponents;
	
	/**
	 * Sets the game
	 * @param game
	 */
	public AbstractGameComponent(Game game){
		this.game = game;
		this.drawableGameComponents = new ArrayList<DrawableGameComponent>();
	}
	
	@Override
	public void update() {
	
		for(DrawableGameComponent dgc: drawableGameComponents){
			dgc.update();
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		
		for(DrawableGameComponent dgc: drawableGameComponents){
			dgc.draw(canvas);
		}
	}
	
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
