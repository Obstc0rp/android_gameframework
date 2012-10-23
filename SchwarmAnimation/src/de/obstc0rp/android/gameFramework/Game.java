package de.obstc0rp.android.gameFramework;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class Game extends SurfaceView{

	private SurfaceHolder holder;
	private GameLoop gameLoop;
	//TODO: only ONE GameComponent please....
	List<GameComponent> gameComponents;
	//TODO: fullScreen
	//TODO: dimensions (800x480 etc)
	//TODO: orientation
	
	public Game(Context context) {
		super(context);

		gameComponents = new ArrayList<GameComponent>();
		gameLoop = new GameLoop(this);
		
		holder = getHolder();
		holder.addCallback(new Callback() {
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				gameLoop.setRunning(true);
				gameLoop.start();
			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height) {
				
			}
		});
	}

	@Override
	protected void onDraw(Canvas canvas) {
		
		for(GameComponent gc: gameComponents) {
			gc.update();
		}
		for(GameComponent gc: gameComponents) {
			gc.draw(canvas);
		}
	}
	
	/**
	 * Adds a GameComponent which you want to be drawn.
	 * @param gameComponent
	 */
	public void addGameComponent(GameComponent gameComponent){
		gameComponent.loadContent();
		this.gameComponents.add(gameComponent);
	}
	
	/**
	 * Deletes the GameComponent which should expire.
	 * @param gameComponent
	 */
	public void deleteGameComponent(GameComponent gameComponent){
		gameComponent.unloadContent();
		this.gameComponents.remove(gameComponent);
	}
	
	public void pauseGame(){
		gameLoop.setRunning(false);
	}
	
	public void resumeGame(){
		gameLoop.setRunning(true);
		gameLoop.run();
	}
	
	public void stopGame(){
		//TODO: aufräumen
		gameLoop.setRunning(false);
		gameLoop = null;
		holder = null;
		
		for(GameComponent gc : gameComponents){
			gc.unloadContent();
			gc = null;
		}
	}
}
