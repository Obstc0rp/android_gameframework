package de.obstc0rp.android.schwarmanimation;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import de.obstc0rp.android.glGameFramework.DrawableGameComponent;
import de.obstc0rp.android.glGameFramework.Game;

public class Boid extends DrawableGameComponent{

	private List<Boid> boids;
	public Bitmap bmp;
	
	public double positionX;
	public double positionY;
	
	private FloatBuffer vertexBuffer;
	private float vertices[] = {
			 -1.0f, -1.0f,  0.0f,        // V1 - bottom left
			 -1.0f,  1.0f,  0.0f,        // V2 - top left
			 1.0f, -1.0f,  0.0f,        // V3 - bottom right
		     1.0f,  1.0f,  0.0f         // V4 - top right
	};
	
	private FloatBuffer textureBuffer;
	private float texture[] = {
			
			0.0f, 1.0f,
			0.0f, 0.0f,
			1.0f, 1.0f,
			1.0f, 0.0f
	};
	
	public double velocityX = 0;
	public double velocityY = 0;
	public double nextVelocityX = 0;
	public double nextVelocityY = 0;

	public static double MAX_X;	//screen-width
	public static double MIN_X = 0;
	public static double MAX_Y;	//screen-height
	public static double MIN_Y = 0;
	
	/**
	 * Intatiates a Boid
	 * @param game
	 */
	public Boid(Game game) {
		super(game);

		MAX_X = game.getDisplayWidth();
		MAX_Y = game.getDisplayHeight();
		
		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(vertices.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = byteBuffer.asFloatBuffer();
		vertexBuffer.put(vertices);
		vertexBuffer.position(0);

		
		byteBuffer = ByteBuffer.allocateDirect(texture.length * 4);
		byteBuffer.order(ByteOrder.nativeOrder());
		textureBuffer = byteBuffer.asFloatBuffer();
		textureBuffer.put(texture);
		textureBuffer.position(0);
	}
	
	/**
	 * Sets the Boid-Array
	 * @param boids
	 */
	public void setBoids(List<Boid> boids){
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
        if (positionX + (bmp.getWidth()/2) >= MAX_X) {
            velocityX *= -1.0f;
            positionX = MAX_X - (bmp.getWidth()/2) - 0.01f;
        }
        
        if (positionX - (bmp.getWidth()/2) <= MIN_X) {
            velocityX *= -1.0f;
            positionX = MIN_X + (bmp.getWidth()/2) + 0.01f;
        }
        
        if (positionY + (bmp.getHeight()/2) >= MAX_Y) {
            velocityY *= -1.0f;
            positionY = MAX_Y - (bmp.getHeight()/2) - 0.01f;
        }
        
        if (positionY - (bmp.getHeight()/2) <= MIN_Y) {
        	velocityY *= -1.0f;
            positionY = MIN_Y + (bmp.getHeight()/2) + 0.01f;
        }
	}

//	@Override
//	public void draw(Canvas canvas) {
//
//		canvas.drawBitmap(bmp, (int)positionX - bmp.getWidth()/2, (int)positionY - bmp.getHeight()/2, null);
//	}
	
	int frame = 0;
	@Override
	public void draw(GL10 gl) {
		
		if(frame == 0){
			loadGLTexture(gl);
		}
		frame++;
		
		gl.glBindTexture(GL10.GL_TEXTURE, textures[0]);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		gl.glFrontFace(GL10.GL_CW);
		
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
		gl.glVertexPointer(2, GL10.GL_FLOAT, 0, textureBuffer);
		
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, vertices.length / 3);
		
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	//TEST
	int[] textures = new int[1];
	
	public void loadGLTexture(GL10 gl) {

//      gl.glTranslatef((int)positionX - bmp.getWidth()/2, (int)positionY - bmp.getHeight()/2, -5);
      gl.glGenTextures(1, textures, 0);
      gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
      gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

      GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
      
      System.out.println("BMP height: " + bmp.getHeight() + " Position: " + positionX + " " + positionY);
      
      bmp.recycle();
	}
	
	@Override
	public void loadContent() {

		bmp = BitmapFactory.decodeResource(game.getResources(), R.drawable.ic_launcher);
//		bmp = Bitmap.createScaledBitmap(bmp, (int)(MAX_X * 0.1), (int)(MAX_X * 0.1), false);

	}

	@Override
	public void unloadContent() {
		bmp.recycle();
//		this.bmp = null;
	}

	/**
	 * detects if the given parameters collidate with the boid
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public boolean isCollision(float mouseX, float mouseY) {

		if(mouseX < positionX + bmp.getWidth()/2 &&  mouseX > positionX - bmp.getWidth()/2 &&
				mouseY < positionY + bmp.getHeight()/2 &&  mouseY > positionY - bmp.getHeight()/2){
			return true;
		}else{
			return false;
		}
	}
}
