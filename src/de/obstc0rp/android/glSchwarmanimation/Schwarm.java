package de.obstc0rp.android.glSchwarmanimation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.microedition.khronos.opengles.GL10;

import android.opengl.GLU;
import android.view.MotionEvent;
import de.obstc0rp.android.glGameFramework.Game;
import de.obstc0rp.android.glGameFramework.Level;

public class Schwarm extends Level {

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
	
//	@Override
//	public void draw(Canvas canvas) {
//		canvas.drawColor(Color.BLACK);
//		super.draw(canvas);
//	}
	
	@Override
	public void draw(GL10 gl) {
		//TODO
		//s.o.
		gl.glClearColor(0, 0, 0, 0.5f);
		super.draw(gl);
	}
	
	@Override
	public void onSurfaceCreated(GL10 gl) {

		gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping ( NEW )
		
		gl.glShadeModel(GL10.GL_SMOOTH);            //Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    //Black Background
		gl.glClearDepthf(1.0f);                     //Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL);             //The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		GLU.gluOrtho2D(gl, (float)0, (float)MAX_X, (float)0, (float)MAX_Y);
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
