package com.payoda.mediaplayerpoc;

import java.io.IOException;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import android.support.v4.app.NavUtils;

public class TyrionPlayer extends Activity implements OnPreparedListener, MediaController.MediaPlayerControl{
	MediaController mc;
	Handler handler;
	private MediaPlayer mp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tyrion_player);
        mp=new MediaPlayer();
        handler=new Handler();
        try 
        {
			mp.setDataSource("R.raw.po");
			mp.prepare();
		}
        catch (IllegalArgumentException e) 
        {
			e.printStackTrace();
		}
        catch (IllegalStateException e) 
        {
			e.printStackTrace();
		}
        catch (IOException e) 
        {
			e.printStackTrace();
		}        
        mc=new MediaController(this);
        
    }
    @Override
       	public boolean onTouchEvent(MotionEvent event) {
            mc.show();
            return false;
    }
        public void onPrepared(MediaPlayer mediaPlayer) {
            mc.setMediaPlayer((MediaPlayerControl) this);
            
            handler.post(new Runnable() {
              public void run() {
                mc.setEnabled(true);
                mc.show();
              }
            });
          }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_tyrion_player, menu);
        return true;
    }
	public boolean canPause() {
		
	return false;
	}
	public boolean canSeekBackward() {
		// TODO Auto-generated method stub
		return false;
	}
	public boolean canSeekForward() {
		// TODO Auto-generated method stub
		return false;
	}
	public int getBufferPercentage() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getCurrentPosition() {
		// TODO Auto-generated method stub
		return 0;
	}
	public int getDuration() {
		// TODO Auto-generated method stub
		return 0;
	}
	public boolean isPlaying() {
		// TODO Auto-generated method stub
		return false;
	}
	public void pause() {
		mp.pause();
	}
	public void seekTo(int pos) {
		// TODO Auto-generated method stub
		
	}
	public void start() {
		mp.start();		
	}

    
}
