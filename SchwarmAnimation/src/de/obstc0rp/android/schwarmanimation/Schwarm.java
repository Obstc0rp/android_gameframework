package de.obstc0rp.android.schwarmanimation;

import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import de.obstc0rp.android.gameFramework.AbstractGameComponent;
import de.obstc0rp.android.gameFramework.Game;

public class Schwarm extends AbstractGameComponent {

	Boid[] boids;
	int numberOfBoids = 30;
	
	public Schwarm(Game game) {
		super(game);

		Random r = new Random();

        boids = new Boid[numberOfBoids];

        for (int i = 0; i < numberOfBoids; i++) {
            boids[i] = new Boid(game);
            //setting the x-coordinate
            boids[i].positionX = SchwarmConstants.MAX_X/2 + ((r.nextDouble() - r.nextDouble()) * 100);
            //setting the y-coordinate
            boids[i].positionY = SchwarmConstants.MAX_Y/2 + ((r.nextDouble() - r.nextDouble()) * 100);
            //setting x-velocity
            boids[i].velocityX = (r.nextDouble() - r.nextDouble())*5;
            //setting y-velocity
            boids[i].velocityY = (r.nextDouble() - r.nextDouble())*5;
        }
        for (Boid boid : boids) {
            boid.setBoids(boids);
            
            this.addDrawableGameComponent(boid);
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
}
