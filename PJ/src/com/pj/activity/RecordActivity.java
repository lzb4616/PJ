package com.pj.activity;

import java.io.File;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class RecordActivity extends Activity implements SurfaceHolder.Callback {
	private static final String TAG = "RecordVideo";

	private MediaRecorder record = null;
	private static final String OUTPUT_FILE = "/sdcard/videooutput.mp4";
	private VideoView vv;
	private Button startBtn = null;
	private Thread thread;
	private SurfaceHolder holder;
	private SurfaceView surfaceView;
	Camera mCameraDevice;
	private AudioManager mAudioManager;
	private long mExitTime;
	private Button change_photo_record_btn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.ui_pj);
		// =========================================================================
		startBtn = (Button) findViewById(R.id.begin_photo_record_btn);
		Button endBtn = (Button) findViewById(R.id.show_image_stoprecord_btn);
		Button playRecordingBtn = (Button) findViewById(R.id.play_record_btn);
		Button stpPlayingrecordingBtn = (Button) findViewById(R.id.stop_record_goback_btn);

		vv = (VideoView) findViewById(R.id.videoview);
		surfaceView = (SurfaceView) findViewById(R.id.mSurfaceView);
		//=======================================================================
		mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);		
		//========================================================================
		
		holder = surfaceView.getHolder();
		holder.addCallback(this);
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		// ==================================================================

		startBtn.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					beginRecording();
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});

		endBtn.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					stopRecording();
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});

		playRecordingBtn.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					playRecording();
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});

		stpPlayingrecordingBtn.setOnClickListener(new Button.OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					stopPlayingRecording();
				} catch (Exception e) {
					Log.e(TAG, e.toString());
					e.printStackTrace();
				}
			}
		});
		
		change_photo_record_btn = (Button)findViewById(R.id.change_photo_record_btn);
		change_photo_record_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent();
				i.setClass(RecordActivity.this, TakePhotoActivity.class);
				startActivity(i);
				finish();
				
			}
		});
	}

	public void surfaceCreated(SurfaceHolder sholder) {
		// TODO Auto-generated method stub
		startBtn.setEnabled(true);
	}

	public void surfaceChanged(SurfaceHolder sholder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		Log.v(TAG, "Width x Height = " + width + "x" + height);
	}

	public void surfaceDestroyed(SurfaceHolder sholder) {
		// TODO Auto-generated method stub

	}

	protected void beginRecording() throws Exception {
		// TODO Auto-generated method stub
		if (record != null) {
			record.stop();
			record.release();
			record = null;
			Log.i(TAG, "已经删除" + 1);
		}
		// ========================================================
		deleteFile();
		changePreview();
		// ========================================================
		vv.setVisibility(View.GONE);
		surfaceView.setVisibility(View.VISIBLE);
		// ========================================================
		thread = new Thread() {
			@Override
			public void run() {
				try {
					record = new MediaRecorder();
					record.setCamera(mCameraDevice);
					record.setVideoSource(MediaRecorder.VideoSource.CAMERA);
					record.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
					record.setVideoSize(320, 240);
					record.setVideoFrameRate(20);
					record.setVideoEncoder(MediaRecorder.VideoEncoder.MPEG_4_SP);
					record.setPreviewDisplay(holder.getSurface());
					record.setOrientationHint(90);
					record.setOutputFile(OUTPUT_FILE);
					try {
						record.prepare();
					} catch (Exception e) {
						Log.i(TAG, e.getMessage());
					}
					try {
						record.start();
					} catch (Exception e) {
						Log.e(TAG, e.getMessage() + e.getCause());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			};
		};
		thread.start();
		thread = null;
	}

	protected void stopRecording() {
		// TODO Auto-generated method stub
		vv.setVisibility(View.GONE);
		if (record != null) {
			try {
				record.stop();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, e.getMessage() + 1);
			}
			try {
				record.release();
			} catch (Exception e) {
				// TODO: handle exception
				Log.e(TAG, e.getMessage() + 2);
			}
			record = null;
		}
		try {
			mCameraDevice.release();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage() + 4);
		}
	}

	protected void playRecording() {
		// TODO Auto-generated method stub
		MediaController mc = new MediaController(this);
		surfaceView.setVisibility(View.GONE);
		vv = (VideoView) findViewById(R.id.videoview);
		vv.setVisibility(View.VISIBLE);
		vv.setMediaController(mc);
		vv.setVideoPath(OUTPUT_FILE);
		vv.start();
	}

	protected void stopPlayingRecording() throws Exception {
		// TODO Auto-generated method stub
		vv.stopPlayback();
	}

	private void deleteFile() {
		File outFile = new File(OUTPUT_FILE);
		if (outFile.exists()) {
			outFile.delete();
		}
	}

	private void changePreview() {
		if (mCameraDevice.CAMERA_ERROR_UNKNOWN == 1) {
			try {				
				mCameraDevice.release();
			} catch (Exception e) {
				Log.e(TAG, e.getMessage() + 6);
			}
		}
		try {
			mCameraDevice = Camera.open();
			mCameraDevice.setDisplayOrientation(90);
			mCameraDevice.unlock();

		} catch (Exception e) {
			Log.e(TAG, e.getMessage() + e.getCause());
		}
	}
	/** 退出按钮 */
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				if ((System.currentTimeMillis() - mExitTime) > 2000) {
					 Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
					 mExitTime = System.currentTimeMillis();
					 vv.setVisibility(View.GONE);	
					 surfaceView.setVisibility(View.GONE);
				}else {
                    finish();
            }									
			return true;
			}
		}
		return super.dispatchKeyEvent(event);
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);	
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		deleteFile();
		mAudioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
		try {
			mCameraDevice.lock();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage() + 3);
		}
		try {
			mCameraDevice.release();
		} catch (Exception e) {
			Log.e(TAG, e.getMessage() + 4);
		}
	}
}
