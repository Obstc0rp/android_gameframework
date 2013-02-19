package de.obstc0rp.android.schwarmanimation;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import de.obstc0rp.android.gameFramework.Game;

public class SchwarmAnimationActivity extends Activity {

	private static final String TAG = SchwarmAnimationActivity.class.getSimpleName();
	Game schwarmGame;
	
//	private boolean focusChangeFalseSeen = true;
//	private boolean resume = true;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
//        setContentView(R.layout.activity_schwarm_animation);

        schwarmGame = new Game(this, null);
        schwarmGame.setOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        Schwarm schwarm = new Schwarm(schwarmGame);
        schwarmGame.setLevel(schwarm);
        setContentView(schwarmGame);
        
        Log.v(TAG, "view added");
    }
    
//    @Override
//    protected void onPause() {
////    	schwarmGame.onPause();
//    	super.onPause();
//    }
//    
//    @Override
//    protected void onResume() {
//    	
//    	if(!focusChangeFalseSeen){
//    		schwarmGame.onResume();
//    	}
//    	resume = true;
//    	super.onResume();
//    }
    
//    @Override
//    public void onWindowFocusChanged(boolean hasFocus) {
//    	
////    	super.onWindowFocusChanged(hasFocus);
//    	
//    	if(hasFocus){
//    		if(resume){
//    			schwarmGame.onResume();
//    		}
//    		
//    		resume = false;
//    		focusChangeFalseSeen = false;
//    	}else{
//    		focusChangeFalseSeen = true;
//    	}
//    }
    
    
    
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
