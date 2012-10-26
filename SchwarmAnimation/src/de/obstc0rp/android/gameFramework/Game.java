package de.obstc0rp.android.gameFramework;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class Game extends SurfaceView{

	private SurfaceHolder holder;
	private GameLoop gameLoop;
	//TODO: only ONE GameComponent please....
	List<GameComponent> gameComponents;
	//TODO: dimensions (800x480 etc)
	Activity activity;
	public Game(Context context) {
		super(context);
		activity = (Activity)context;
		((Activity)context).requestWindowFeature(Window.FEATURE_NO_TITLE);
        ((Activity)context).getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
    
	/**
	 * Sets the orientation of the game via ActivityInfo.
	 * @param activityInfo is an attribute of ActivityInfo.
	 */
    public void setOrientation(int activityInfo){
    	activity.setRequestedOrientation(activityInfo);
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
	public void addGameComponent(GameComponent gameComponent) {
		synchronized (getHolder()) {
			gameComponent.loadContent();
			this.gameComponents.add(gameComponent);
		}
	}
	
	/**
	 * Deletes the GameComponent which should expire.
	 * @param gameComponent
	 */
	public void deleteGameComponent(GameComponent gameComponent) {
		synchronized (getHolder()) {
			gameComponent.unloadContent();
			this.gameComponents.remove(gameComponent);
		}
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
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		//TODO: maybe a delay... maybe in ConcreteGameComponent
		synchronized (getHolder()) {
			for(GameComponent gc : gameComponents){

				gc.onTouchEvent(event);	
			}
		}
		
		return true;
	}
}
