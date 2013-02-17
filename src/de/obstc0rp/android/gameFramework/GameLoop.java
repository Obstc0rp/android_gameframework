package de.obstc0rp.android.gameFramework;

import de.obstc0rp.android.schwarmanimation.SchwarmAnimationActivity;
import android.graphics.Canvas;
import android.util.Log;

public class GameLoop extends Thread{
	private static final String TAG = SchwarmAnimationActivity.class.getSimpleName();
	
	private long FPS = 30;
	private Game game;
	private boolean running = false;
	
	public GameLoop(Game view) {
		this.game = view;
	}
	
	/**
	 * Changes the frames per second.
	 * @param framesPerSecond
	 */
	public void setFramesPerSecond(long framesPerSecond){
		this.FPS = framesPerSecond;
	}
	
	/**
	 * Sets the game running.
	 * @param run
	 */
	public void setRunning(boolean run){
		this.running = run;
	}
	
	@Override
	public void run() {
		long ticksPS = 1000/FPS;
		long startTime;
		long sleepTime;
		
		while(running){
			Canvas c = null;
			startTime = System.currentTimeMillis();
			try{
				c = game.getHolder().lockCanvas();

				synchronized (game.getHolder()) {
					
					game.onDraw(c);
				}
			}finally {
				if(c != null) {
					game.getHolder().unlockCanvasAndPost(c);
				}
			}
			sleepTime = ticksPS-(System.currentTimeMillis() - startTime);
			try{
				if(sleepTime > 0){
					sleep(sleepTime);
				}else{
					sleep(10);	//i think it's useless...
				}
			}catch(Exception e){}
		}

	}
	
	/**
	 
	 public void surfaceCreated(SurfaceHolder holder) {
        Canvas canvas = null;
        try {
            canvas = holder.lockCanvas();
            synchronized(holder) {
                onDraw(canvas);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (canvas != null) {
                holder.unlockCanvasAndPost(canvas);
            }
        }
    } 
	 */
}
