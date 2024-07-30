package org.cocos2dx.javascript;



import java.io.IOException;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.util.Log;

import org.cocos2dx.javascript.AppActivity;
import org.cocos2dx.javascript.SDKWrapper;
import org.cocos2dx.lib.Cocos2dxJavascriptJavaBridge;

public class VoicePlayer {

	private static String TAG = "VoicePlayer";
	private static MediaPlayer mPlayer;
	
	private static boolean isPause;

	public static  int play(String filePathString) {

		Log.e(TAG, "play: " + filePathString );
		// TODO Auto-generated method stub
		if (mPlayer==null) {
			mPlayer=new MediaPlayer();
			//保险起见，设置报错监听
			mPlayer.setOnErrorListener(new OnErrorListener() {
				
				@Override
				public boolean onError(MediaPlayer mp, int what, int extra) {
					// TODO Auto-generated method stub
					mPlayer.reset();
					return false;
				}
			});
		}else {
			mPlayer.reset();//就回复
		}
		
		try {
			mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				
				@Override
				public void onCompletion(MediaPlayer mp) {
					// TODO Auto-generated method stu

					AppActivity appActivity = (AppActivity) (SDKWrapper.getInstance().getContext());
					appActivity.runOnGLThread(new Runnable() {
						@Override
						public void run() {
							Cocos2dxJavascriptJavaBridge.evalString("gm.sdkMgr.onStopPlayHttp()");
						}
					});
				}
			});
			mPlayer.setDataSource(filePathString);
			mPlayer.prepare();
			mPlayer.start();
			return mPlayer.getDuration();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	
	//停止函数
	public static void pause(){
		if (mPlayer!=null&&mPlayer.isPlaying()) {
			mPlayer.pause();
			isPause=true;
		}
	}
	
	public static void stop(){
		if (mPlayer!=null&&mPlayer.isPlaying()) {
			mPlayer.stop();
			isPause=false;
		}
	}
	
	//继续
	public static void resume()
	{
		if (mPlayer!=null&&isPause) {
			mPlayer.start();
			isPause=false;
		}
	}
	

	public  static void release()
	{
		if (mPlayer!=null) {
			mPlayer.release();
			mPlayer=null;
		}
	}
}
