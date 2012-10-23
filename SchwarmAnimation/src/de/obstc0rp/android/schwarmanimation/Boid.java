package de.obstc0rp.android.schwarmanimation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import de.obstc0rp.android.gameFramework.AbstractDrawableGameComponent;
import de.obstc0rp.android.gameFramework.Game;

public class Boid extends AbstractDrawableGameComponent{

	private Boid[] boids;
	public Bitmap bmp;
	
	public double positionX;
	public double positionY;
	
	public double velocityX = 0;
	public double velocityY = 0;
	public double nextVelocityX = 0;
	public double nextVelocityY = 0;
	
	/**
	 * Intatiates a Boid
	 * @param game
	 */
	public Boid(Game game) {
		super(game);
	}
	
	/**
	 * Sets the Boid-Array
	 * @param boids
	 */
	public void setBoids(Boid[] boids){
		this.boids = boids;
	}

	@Override
	public void update() {
		this.setVelocity();
		this.setPosition();
	}
	
	private void setVelocity() {

		double meanPositionX = 0;
		double meanPositionY = 0;
        double meanSpeedX = 0;
        double meanSpeedY = 0;
        double forceDistanceX = 0;
        double forceDistanceY = 0;
        double forcePositionX = 0;
        double forcePositionY = 0;
        double forceSpeedX = 0;
        double forceSpeedY = 0;

        int botCount = 1;
        meanPositionX = this.positionX;
        meanPositionY = this.positionY;
        meanSpeedX = this.velocityX;
        meanSpeedY = this.velocityY;

        for (Boid otherBoid : boids)
        {
            if (otherBoid != this)
            {
                double distanceVectorX = this.positionX - otherBoid.positionX;
                double distanceVectorY = this.positionY - otherBoid.positionY;
                double distance = Math.sqrt(
                		Math.pow(distanceVectorX, 2) + Math.pow(distanceVectorY, 2)
                );

                if (distance < SchwarmConstants.MAX_DISTANCE) {
                    botCount++;
                    meanPositionX += otherBoid.positionX;
                    meanPositionY += otherBoid.positionY;
                    meanPositionX += otherBoid.velocityX;
                    meanPositionY += otherBoid.velocityY;

                    if (distance > 0) {
                        forceDistanceX += (1.0f / Math.pow(distance, 2)) * distanceVectorX;
                        forceDistanceY += (1.0f / Math.pow(distance, 2)) * distanceVectorY;
                    }
                }
            }
        }

        meanPositionX /= botCount;
        meanPositionY /= botCount;
        meanSpeedX /= botCount;
        meanSpeedY /= botCount;

        forcePositionX = meanPositionX - this.positionX;
        forcePositionY = meanPositionY - this.positionY;
        forceSpeedX = meanSpeedX - this.velocityX;
        forceSpeedY = meanSpeedY - this.velocityY;

        this.nextVelocityX = this.velocityX +
            (SchwarmConstants.FACTOR_ENVIRONMENT *
                (
                SchwarmConstants.FACTOR_MEAN_POSITION * forcePositionX +
                SchwarmConstants.FACTOR_MEAN_SPEED * forceSpeedX +
                SchwarmConstants.FACTOR_MEAN_DISTANCE * forceDistanceX
                )
            );
        
        this.nextVelocityY = this.velocityY +
        (SchwarmConstants.FACTOR_ENVIRONMENT *
            (
            SchwarmConstants.FACTOR_MEAN_POSITION * forcePositionY +
            SchwarmConstants.FACTOR_MEAN_SPEED * forceSpeedY +
            SchwarmConstants.FACTOR_MEAN_DISTANCE * forceDistanceY
            )
        );
	}
	
	public void setPosition(){
		this.velocityX = (this.velocityX + this.nextVelocityX) / 2;
		this.velocityY = (this.velocityY + this.nextVelocityY) / 2;

        this.positionX += this.velocityX;
        this.positionY += this.velocityY;

        //Im "Gehäuse" halten
        if (positionX + (bmp.getWidth()/2) >= SchwarmConstants.MAX_X) {
            velocityX *= -1.0f;
            positionX = SchwarmConstants.MAX_X - (bmp.getWidth()/2) - 0.01f;
        }
        
        if (positionX - (bmp.getWidth()/2) <= SchwarmConstants.MIN_X) {
            velocityX *= -1.0f;
            positionX = SchwarmConstants.MIN_X + (bmp.getWidth()/2) + 0.01f;
        }
        
        if (positionY + (bmp.getHeight()/2) >= SchwarmConstants.MAX_Y) {
            velocityY *= -1.0f;
            positionY = SchwarmConstants.MAX_Y - (bmp.getHeight()/2) - 0.01f;
        }
        
        if (positionY - (bmp.getHeight()/2) <= SchwarmConstants.MIN_Y) {
        	velocityY *= -1.0f;
            positionY = SchwarmConstants.MIN_Y + (bmp.getHeight()/2) + 0.1f;
        }
	}

	@Override
	public void draw(Canvas canvas) {

		canvas.drawBitmap(bmp, (int)positionX - bmp.getWidth()/2, (int)positionY - bmp.getHeight()/2, null);
	}

	@Override
	public void loadContent() {

		bmp = BitmapFactory.decodeResource(game.getResources(), R.drawable.ic_launcher);
	}

	@Override
	public void unloadContent() {
		this.bmp = null;
	}
}
