package de.obstc0rp.android.schwarmanimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import de.obstc0rp.android.gameFramework.AbstractLevel;
import de.obstc0rp.android.gameFramework.Game;

public class Schwarm extends AbstractLevel {

	List<Boid> boids;
	int numberOfBoids = 30;

	public static double MAX_X;	//screen-width
	public static double MIN_X = 0;
	public static double MAX_Y;	//screen-height
	public static double MIN_Y = 0;

	public Schwarm(Game game) {
		super(game);
		
		MAX_X = game.getDisplayWidth();
		MAX_Y = game.getDisplayHeight();
		
		boids = new ArrayList<Boid>();
		Random r = new Random();
		
		
        for (int i = 0; i < numberOfBoids; i++) {
        	boids.add(new Boid(game));
        	
            //setting the x-coordinate
            boids.get(i).positionX = MAX_X/2 + ((r.nextDouble() - r.nextDouble()) * 100);
            //setting the y-coordinate
            boids.get(i).positionY = MAX_Y/2 + ((r.nextDouble() - r.nextDouble()) * 10);
            //setting x-velocity
            boids.get(i).velocityX = (r.nextDouble() - r.nextDouble())*5;
            //setting y-velocity
            boids.get(i).velocityY = (r.nextDouble() - r.nextDouble())*5;
        }
        for (Boid b : boids) {
            b.setBoids(boids);
            this.addDrawableGameComponent(b);
        }
	}

	@Override
	public void loadContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadContent() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		super.update();
	}
	
	@Override
	public void draw(Canvas canvas) {
		canvas.drawColor(Color.BLACK);
		super.draw(canvas);
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		float mouseX = event.getX();
		float mouseY = event.getY();

		for(Boid b : boids){
			if(b.isCollision(mouseX, mouseY)){
				boids.remove(b);
	            this.deleteDrawableGameComponent(b);
				break;
			}
		}
	}
}
