package org.cocos2dx.javascript;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

import android.media.MediaRecorder;
import android.util.Log;

public class VoiceRecorder {

	private static String TAG = "VoiceRecorder";
	private static MediaRecorder mRecorder;
	private static String mCurrentFilePathString;

	private static boolean isPrepared;// 是否准备好了

	/**
	 * 回调函数，准备完毕，准备好后，button才会开始显示录音框
	 * 
	 * @author nickming
	 *
	 */
	public interface AudioStageListener {
		void wellPrepared();
	}

	public static AudioStageListener mListener;

	public static void setOnAudioStageListener(AudioStageListener listener) {
		mListener = listener;
	}

	// 准备方法
	public static void prepare(String dirString, String fileNameString) {
		Log.e(TAG, "prepare: dirString=" + dirString + " fileNameString " + fileNameString);
		try {
			// 一开始应该是false的
			isPrepared = false;

			File dir = new File(dirString);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File file = new File(dir, fileNameString);

			mCurrentFilePathString = file.getAbsolutePath();

			mRecorder = new MediaRecorder();

			// 设置输出文件
			mRecorder.setOutputFile(file.getAbsolutePath());
			// 设置meidaRecorder的音频源是麦克风
			mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
			mRecorder.setAudioEncodingBitRate(4750);
			// 设置文件音频的输出格式为amr
			mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
			// 设置音频的编码格式为amr
			mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

			// 严格遵守google官方api给出的mediaRecorder的状态流程图
			mRecorder.prepare();

			mRecorder.start();
			// 准备结束
			isPrepared = true;
			// 已经准备好了，可以录制了
			if (mListener != null) {
				mListener.wellPrepared();
			}

		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	// 释放资源
	public static String release() {
		// 严格按照api流程进行
		if(mRecorder != null){
			mRecorder.stop();
			mRecorder.release();
			mRecorder = null;			
		}
		return mCurrentFilePathString;
	}

	// 取消,因为prepare时产生了一个文件，所以cancel方法应该要删除这个文件，
	// 这是与release的方法的区别
	public static void cancel() {
		release();
		if (mCurrentFilePathString != null) {
			File file = new File(mCurrentFilePathString);
			file.delete();
			mCurrentFilePathString = null;
		}

	}


}
