package de.obstc0rp.android.schwarmanimation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;
import de.obstc0rp.android.gameFramework.Game;

public class SchwarmAnimationActivity extends Activity {

	Game schwarmGame;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_schwarm_animation);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        schwarmGame = new Game(this);
        Schwarm schwarm = new Schwarm(schwarmGame);
        schwarmGame.addGameComponent(schwarm);
        setContentView(schwarmGame);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.activity_schwarm_animation, menu);
        return true;
    }
    
    //TEST
    @Override
    protected void onPause() {
    	schwarmGame.pauseGame();
    	super.onPause();
    }
    @Override
    protected void onDestroy() {
    	schwarmGame.stopGame();
    	schwarmGame = null;
    	super.onDestroy();
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    }
}
