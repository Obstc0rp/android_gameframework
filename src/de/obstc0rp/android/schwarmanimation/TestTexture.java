package de.obstc0rp.android.schwarmanimation;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.view.MotionEvent;
import de.obstc0rp.android.glGameFramework.Game;
import de.obstc0rp.android.glGameFramework.Level;

public class TestTexture extends Level {

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
	
	public TestTexture(Game game){
		super(game);
		
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
	
	@Override
	public void loadContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unloadContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSurfaceCreated(GL10 gl) {
		// TODO Auto-generated method stub
		
		loadGLTexture(gl);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);            //Enable Texture Mapping ( NEW )
		
		gl.glShadeModel(GL10.GL_SMOOTH);            //Enable Smooth Shading
		gl.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);    //Black Background
		gl.glClearDepthf(1.0f);                     //Depth Buffer Setup
		gl.glEnable(GL10.GL_DEPTH_TEST);            //Enables Depth Testing
		gl.glDepthFunc(GL10.GL_LEQUAL);             //The Type Of Depth Testing To Do
		
		//Really Nice Perspective Calculations
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
	}
	

	int[] textures = new int[1];
	
	public void loadGLTexture(GL10 gl){

		Bitmap bmp = BitmapFactory.decodeResource(game.getResources(), R.drawable.ic_launcher);
		gl.glGenTextures(1, textures, 0);
	    gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
	    gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

	    GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bmp, 0);
	      
	    bmp.recycle();
	}

	@Override
	public void onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

}
