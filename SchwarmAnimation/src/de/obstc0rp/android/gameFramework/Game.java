package de.obstc0rp.android.gameFramework;

import java.util.ArrayList;
import java.util.List;

import de.obstc0rp.android.schwarmanimation.SchwarmAnimationActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

public class Game extends SurfaceView implements Callback{

	private SurfaceHolder holder;
	private GameLoop gameLoop;
	//TODO: only ONE GameComponent please....
	List<GameComponent> gameComponents;

	private int displayHeight;
	private int displayWidth;
	Activity activity;
	
	public Game(Context context) {
		super(context);
		
		activity = (Activity)context;
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
        //TODO: check dis shit
        //gets the actual screen resolution
        DisplayMetrics display = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(display);
        this.displayHeight = display.heightPixels;
        this.displayWidth = display.widthPixels;

    	Log.v(SchwarmAnimationActivity.class.getSimpleName(), "displayHeight is: " + displayHeight);
    	Log.v(SchwarmAnimationActivity.class.getSimpleName(), "displayWidth is: " + displayWidth);
    	
        gameComponents = new ArrayList<GameComponent>();
		
		gameLoop = new GameLoop(this);
		
		holder = getHolder();
		holder.addCallback(this);
		setFocusable(true);
	}
    
	/**
	 * Sets the orientation of the game via ActivityInfo.
	 * @param activityInfo is an attribute of ActivityInfo.
	 */
    public void setOrientation(int activityInfo){
    	activity.setRequestedOrientation(activityInfo);
    	
    	//TODO: maybe case, activityInfo == ActivityInfo.SCREEN_ORIENTATION_ANDSCAPE
//    	int buffer = displayHeight;
//    	displayHeight = displayWidth;
//    	displayWidth = buffer;
    	
    	Log.v(SchwarmAnimationActivity.class.getSimpleName(), "displayHeight is: " + displayHeight);
    	Log.v(SchwarmAnimationActivity.class.getSimpleName(), "displayWidth is: " + displayWidth);
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
		synchronized (holder) {
			gameLoop.setRunning(false);
			gameLoop = null;
		}

//		gameLoop.setRunning(false);
//		gameLoop.interrupt();
	}
	
	public void stopGame(){
		//TODO: aufr�umen
		gameLoop.setRunning(false);
//		gameLoop.interrupt();
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

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {

		gameLoop.setRunning(true);
		gameLoop.start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		boolean retry = true;
		while (retry) {
			try {
				gameLoop.join();
				retry = false;
			} catch (InterruptedException e) {
				// try again shutting down the thread
			}
		}
	}
	
	public int getDisplayHeight(){
		return displayHeight;
	}
	public int getDisplayWidth(){
		return displayWidth;
	}
}
