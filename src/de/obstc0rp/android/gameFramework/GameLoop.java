package de.obstc0rp.android.gameFramework;

import android.graphics.Canvas;

public class GameLoop extends Thread{
	
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
		
		Canvas c = null;
		
		while(running){
			c = null;
			startTime = System.currentTimeMillis();
			try{
				c = game.getHolder().lockCanvas(null);

				synchronized (game.getHolder()) {
				
					game.draw(c);
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
}
