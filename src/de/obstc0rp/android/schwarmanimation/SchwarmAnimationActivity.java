package de.obstc0rp.android.schwarmanimation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import de.obstc0rp.android.gameFramework.Game;

public class SchwarmAnimationActivity extends Activity {

	private static final String TAG = SchwarmAnimationActivity.class.getSimpleName();
	Game schwarmGame;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_schwarm_animation);

        schwarmGame = new Game(this);
        schwarmGame.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Schwarm schwarm = new Schwarm(schwarmGame);
        schwarmGame.setGameComponent(schwarm);
        setContentView(schwarmGame);
        
        Log.v(TAG, "view added");
    }
    
//    @Override
//    protected void onDestroy() {
//    	Log.v(TAG, "destroying...");
//    	schwarmGame.stopGame();
//    	super.onDestroy();
//    }
//    @Override
//    protected void onStop() {
//    	Log.v(TAG, "stopping...");
//    	super.onStop();
//    }
}
