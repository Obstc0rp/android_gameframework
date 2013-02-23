package de.obstc0rp.android.glGameFramework;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import de.obstc0rp.android.schwarmanimation.SchwarmAnimationActivity;

public class Game extends GLSurfaceView implements Renderer {

	private int displayHeight;
	private int displayWidth;
	Activity activity;
	
	Level level;

	private long FPS = 30;
	long ticksPS = 1000/FPS;
	long startTime;
	long sleepTime;
	
	public Game(Context context){
		super(context);
		
		activity = (Activity)context;
		activity.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Log.v(SchwarmAnimationActivity.class.getSimpleName(), "Window feature is: " + Window.FEATURE_NO_TITLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    	Log.v(SchwarmAnimationActivity.class.getSimpleName(), "windows changed to fullscreen");
		
        //TODO: check dis shit
        //gets the actual screen resolution
        DisplayMetrics display = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(display);
        this.displayHeight = display.heightPixels;
        this.displayWidth = display.widthPixels;
        
        setRenderer(this);
        
        Log.v(SchwarmAnimationActivity.class.getSimpleName(), "displayHeight is: " + displayHeight);
    	Log.v(SchwarmAnimationActivity.class.getSimpleName(), "displayWidth is: " + displayWidth);
	}

	/**
	 * Sets the orientation of the game via ActivityInfo.
	 * @param activityInfo is an attribute of ActivityInfo.
	 */
    public void setOrientation(int activityInfo){
    	activity.setRequestedOrientation(activityInfo);
    }

    /**
	 * Changes the frames per second.
	 * @param framesPerSecond
	 */
	public void setFramesPerSecond(long framesPerSecond){
		this.FPS = framesPerSecond;
	}
	
	@Override
	public void onDrawFrame(GL10 gl) {
		//TODO
		
		if(level != null){
			level.update();
			level.draw(gl);
		}
		
		sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
		try{
			if(sleepTime > 0){
				Thread.sleep(sleepTime);
			}else{
//				Thread.sleep(10);	//i think it's useless...
			}
		}catch(Exception e){}
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height) {

    	Log.v(SchwarmAnimationActivity.class.getSimpleName(), "Renderer: Surface changed");
    	
    	//TODO: update screen size... not necessary for this i think...
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    	Log.v(SchwarmAnimationActivity.class.getSimpleName(), "Renderer: Surface createt to perspective");
		
    	//TODO
    	//TODO: resources laden. bitmaps, etc
    	level.onSurfaceCreated(gl);
	}
	
	public void onPause(){
		//TODO
	}
	
	public void onResume(){
		//TODO
	}
	
	/**
	 * Adds a GameComponent which you want to be drawn.
	 * @param level
	 */
	public void setLevel(Level level) {
		
		if(this.level != null){
			this.level.unloadContent();
			this.level = null;
			this.level= level;
			this.level.loadContent();
		}else{
			this.level= level;
			this.level.loadContent();
		}
	}
	
	/**
	 * Deletes the GameComponent which should expire.
	 * @param level
	 */
	@Deprecated
	public void deleteLevel(Level level) {
		synchronized (getHolder()) {
			level.unloadContent();
			this.level = null;
		}
	}
	
	public boolean onTouchEven(MotionEvent event){
		
		level.onTouchEvent(event);
		
		return true;
	}
	
	public int getDisplayHeight(){
		return displayHeight;
	}
	public int getDisplayWidth(){
		return displayWidth;
	}
}
