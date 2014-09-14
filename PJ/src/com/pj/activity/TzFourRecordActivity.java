package com.pj.activity;

import java.io.File;

import com.pj.handler.RegulationHandler;
import com.pj.handler.RegulationHandler.RegulationType;
import com.pj.regulation.TZRegulation;
import com.pj.source.TzStatusDatas;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class TzFourRecordActivity extends Activity implements SurfaceHolder.Callback {

	/**
	 * 屏幕键盘输入数
	 */
	public int input_num;
	// 屏幕中输入数字按钮
	private ImageButton input_num_bt_0;
	private Button input_num_bt_1;
	private Button input_num_bt_2;
	private Button input_num_bt_3;
	private Button input_num_bt_4;
	private Button input_num_bt_5;
	private Button input_num_bt_6;
	private Button input_num_bt_7;
	private Button input_num_bt_8;
	private Button input_num_bt_9;

	// 输出窗口
	public EditText out_num_et;
	public EditText[] out_et = new EditText[4];
	// 输出数组对应下标
	public int arr_out = 0;
	/*
	 * 判断是否第一个数是否为“1" 若 p="1"则继续加数 反之则直接输出
	 */
	public int p = 0;
	// 删除按钮
	private Button clearButton;
	// 坐庄，闲的按钮
	private Button judge_btn;
	// 四组牌与五组牌的转换按钮
	private Button fourTofive;
	// 规则按钮
	private Button regulationButton;
	// 空白键的转换
	private Button space_change;
	// 规则判断
	private int regulation = 1;
	private int regulation_judge = 0;
	// 做庄与做闲判断
	private int judge = 1;
	// 规则信息输入框
	private EditText editText_1;
	// 规则信息输出按钮
	private Button enter_text_btn;
	// 规则信息
	private String regulation_text = "00最大，同点分大小，0点不分大小";

	private int edit_judge = 0;
	
	private static final String TAG = "TzFourRecordActivity";

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
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_tz_four);
		initWiget();
		eventCliskListeners();
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
				i.setClass(TzFourRecordActivity.this, TzFourPhotoActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

	// 初始化控件
	private void initWiget() {
		// 输入按钮实例化
		input_num_bt_0 = (ImageButton) findViewById(R.id.input_num_btn_0);
		input_num_bt_1 = (Button) findViewById(R.id.input_num_btn_1);
		input_num_bt_2 = (Button) findViewById(R.id.input_num_btn_2);
		input_num_bt_3 = (Button) findViewById(R.id.input_num_btn_3);
		input_num_bt_4 = (Button) findViewById(R.id.input_num_btn_4);
		input_num_bt_5 = (Button) findViewById(R.id.input_num_btn_5);
		input_num_bt_6 = (Button) findViewById(R.id.input_num_btn_6);
		input_num_bt_7 = (Button) findViewById(R.id.input_num_btn_7);
		input_num_bt_8 = (Button) findViewById(R.id.input_num_btn_8);
		input_num_bt_9 = (Button) findViewById(R.id.input_num_btn_9);
		// 输出窗口实例化
		out_num_et = (EditText) findViewById(R.id.output_et_0);
		int i = 0;
		out_et[i++] = (EditText) findViewById(R.id.output_et_1);
		out_et[i++] = (EditText) findViewById(R.id.output_et_2);
		out_et[i++] = (EditText) findViewById(R.id.output_et_3);
		out_et[i++] = (EditText) findViewById(R.id.output_et_4);
		// 删除按钮
		clearButton = (Button) findViewById(R.id.delete_btn);
		// 规则按钮
		regulationButton = (Button) findViewById(R.id.regulationButton);
		// 坐庄闲按钮
		judge_btn = (Button) findViewById(R.id.judge_btn);
		// 空白按键
		space_change = (Button) findViewById(R.id.space_change);
		// 规则信息输入框
		editText_1 = (EditText) findViewById(R.id.ed_text_1);
		// 规则信息输出按钮
		enter_text_btn = (Button) findViewById(R.id.enter_text_btn);
		// 四组牌与五组牌的转换按钮
		fourTofive = (Button) findViewById(R.id.pj_or_tongzi);
	}

	private void eventCliskListeners() {
		// 输入按钮监听事件
		ButtonInputListener biLisenter = new ButtonInputListener();
		input_num_bt_0.setOnClickListener(biLisenter);
		input_num_bt_1.setOnClickListener(biLisenter);
		input_num_bt_2.setOnClickListener(biLisenter);
		input_num_bt_3.setOnClickListener(biLisenter);
		input_num_bt_4.setOnClickListener(biLisenter);
		input_num_bt_5.setOnClickListener(biLisenter);
		input_num_bt_6.setOnClickListener(biLisenter);
		input_num_bt_7.setOnClickListener(biLisenter);
		input_num_bt_8.setOnClickListener(biLisenter);
		input_num_bt_9.setOnClickListener(biLisenter);

		clearButton.setOnClickListener(new ButtonDeleteListener());
		clearButton.setOnLongClickListener(new BtnDelteAllListener());

		regulationButton
				.setOnClickListener(new RegulationButtonActionListener());

		judge_btn.setOnClickListener(new JudgeButtonListener());

		enter_text_btn.setOnClickListener(new RegulationTextListenser());

		space_change.setOnClickListener(new ChangeTzToPjListenser());

		fourTofive.setOnClickListener(new ChangeFourOrFiveListener());
	}

	// ----------------------------------------------------------------------------

	/**
	 * 自动换行函数
	 * 
	 * */
	private void switchLine() {
		if (arr_out == 4) {
			int index = out_num_et.getSelectionStart();
			Editable editable = out_num_et.getText();
			editable.insert(index, "\n");
		}
	}

	/**
	 * 
	 * 输出框输出函数
	 * 
	 * */

	private void outPut() {
		int index = out_num_et.getSelectionStart();
		Editable editable = out_num_et.getText();
		editable.insert(index, input_num + "-");

	}

	/**
	 * 这里是输入数字的按钮 响应屏幕按钮，获得对应的数字
	 */
	public class ButtonInputListener implements OnClickListener {
		public void onClick(View v) {
			if (arr_out < 8 && arr_out >= 0) {
				switch (v.getId()) {
				case R.id.input_num_btn_0:
					input_num = 0;
					outPut();
					arr_out++;
					switchLine();
					break;
				case R.id.input_num_btn_1:
					input_num = 1;
					outPut();
					arr_out++;
					switchLine();
					break;
				case R.id.input_num_btn_2:
					input_num = 2;
					outPut();
					arr_out++;
					switchLine();
					break;
				case R.id.input_num_btn_3:
					input_num = 3;
					outPut();
					arr_out++;
					switchLine();
					break;
				case R.id.input_num_btn_4:
					input_num = 4;
					outPut();
					arr_out++;
					switchLine();
					break;
				case R.id.input_num_btn_5:
					input_num = 5;
					outPut();
					arr_out++;					
					switchLine();
					break;
				case R.id.input_num_btn_6:
					input_num = 6;
					outPut();
					arr_out++;					
					switchLine();
					break;
				case R.id.input_num_btn_7:
					input_num = 7;
					outPut();
					arr_out++;					
					switchLine();
					break;
				case R.id.input_num_btn_8:
					input_num = 8;
					outPut();
					arr_out++;				
					switchLine();
					break;
				case R.id.input_num_btn_9:
					input_num = 9;
					outPut();
					arr_out++;					
					switchLine();
					break;
				default:
					break;
				}

				if (arr_out == 8) {
					defaultEvent();
				}

			}
		}
	}

	/**
	 * 短按删除事件
	 * */
	public class ButtonDeleteListener implements OnClickListener {
		public void onClick(View v) {
			if (v.getId() == R.id.delete_btn && arr_out > 0) {
				int index = out_num_et.getSelectionStart();
				Editable editable = out_num_et.getText();
				if (arr_out == 5) {
					editable.delete(index - 3, index);
					int index1 = out_num_et.getSelectionStart();
					Editable editable1 = out_num_et.getText();
					editable1.insert(index1, "\n");
				} else if (arr_out != 4) {
					editable.delete(index - 2, index);
				} else {
					out_num_et.setText("");
					arr_out = 1;
					p = 0;
				}
				arr_out--;
			}
			if (arr_out <= 0) {
				for (int i = 0; i < out_et.length; i++) {
					out_et[i].setText("");
				}
			}
			p = 0;
		}

	}

	/**
	 * 
	 * 长按删除事件
	 * */
	public class BtnDelteAllListener implements OnLongClickListener {

		public boolean onLongClick(View v) {
			if (v.getId() == R.id.delete_btn && arr_out > 0) {
				out_num_et.setText("");
				for (int i = 0; i < out_et.length; i++) {
					out_et[i].setText("");
				}
				arr_out = 0;
			}
			return false;
		}

	}

	/**
	 * （大小）运算规则选择按钮事件监听器
	 * 
	 * @author Administrator
	 * 
	 */
	private class RegulationButtonActionListener implements OnClickListener {

		public void onClick(View v) {
			if (regulation == 0) {
				regulation_judge = 0;
				if (arr_out >= 8) {
					TzStatusDatas.getInstance().setRegulationIndex(0);
					disPlayOrder();
				}
				regulationButton
						.setBackgroundResource(R.drawable.selector_pj_a);
				regulation_text = "00最大，同点分大小，0点不分大小";
				regulation = 1;
			} else if (regulation == 1) {
				regulation_judge = 1;
				if (arr_out >= 8) {
					TzStatusDatas.getInstance().setRegulationIndex(1);
					disPlayOrder();
				}
				regulationButton
						.setBackgroundResource(R.drawable.selector_pj_b);
				regulation_text = "28大于9点，同点分大小，0点不分大小";
				regulation = 2;
			} else if (regulation == 2) {
				regulation_judge = 2;
				if (arr_out >= 8) {
					TzStatusDatas.getInstance().setRegulationIndex(2);
					disPlayOrder();
				}
				regulationButton
						.setBackgroundResource(R.drawable.selector_pj_c);
				regulation_text = "28最大，同点分大小，0点不分大小";
				regulation = 3;
			} else if (regulation == 3) {
				regulation_judge = 3;
				if (arr_out >= 8) {
					TzStatusDatas.getInstance().setRegulationIndex(3);
					disPlayOrder();
				}
				regulationButton
						.setBackgroundResource(R.drawable.selector_pj_d);
				regulation_text = "19>28>00,同点分大小，0点不分大小";
				regulation = 0;
			}
		}
	}

	/**
	 * PJ转换为TZ事件
	 * 
	 * @return
	 */
	private class ChangeTzToPjListenser implements OnClickListener {

		public void onClick(View v) {
			if (v.getId() == R.id.space_change) {
				Intent i = new Intent();
				i.setClass(TzFourRecordActivity.this, PJPhotoActivity.class);
				startActivity(i);
				finish();
			}

		}

	}

	/**
	 * 四组与五组牌之间的转换
	 *           
	 * @return
	 */
	private class ChangeFourOrFiveListener implements OnClickListener {
		public void onClick(View v) {
			if (v.getId() == R.id.pj_or_tongzi) {
				Intent i = new Intent();
				i.setClass(TzFourRecordActivity.this, TzFivePhotoActivity.class);
				startActivity(i);
				finish();
			}
		}

	}

	/**
	 * 判断8张牌是否都已经输入数据了
	 * 
	 * @return
	 */
	private boolean isComplete() {
		if (out_num_et.getText() == null || out_num_et.getText().equals(""))
			return false;
		return true;
	}

	/**
	 * 显示组合规则顺序的方法
	 * 
	 * @author Administrator
	 * 
	 */
	public void disPlayOrder() {
		if (!isComplete() && arr_out < 8)
			return;
		String[] target = { "右", "平", "一", "爬" };
		for (int i = 0; i < 4; i++) {
			int[] datas = getInputNums();
			TZRegulation r = TzStatusDatas.getInstance()
					.getCurrentTZRegulation();
			if (i == 0) {
				datas = RegulationHandler.getInstance().getTzOrder(datas,
						RegulationType.REGULATION_RIGHT, r);
			} else if (i == 1) {
				datas = RegulationHandler.getInstance().getTzOrder(datas,
						RegulationType.REGULATION_FLAT, r);
			} else if (i == 2) {
				datas = RegulationHandler.getInstance().getTzOrder(datas,
						RegulationType.REGULATION_ONE, r);
			} else if (i == 3) {
				datas = RegulationHandler.getInstance().getTzOrder(datas,
						RegulationType.REGULATION_CLIMB, r);
			}
			if (judge == 0) {// 做闲顺序显示顺序调转
				int t = 0;
				t = datas[0];
				datas[0] = datas[3];
				datas[3] = t;
				t = datas[1];
				datas[1] = datas[2];
				datas[2] = t;
				judge_btn.setBackgroundResource(R.drawable.selector_pj_x);
				out_et[i].setText(target[i] + "\n" + datas[0] + datas[1]
						+ datas[2] + datas[3] + judgeX(comparator(datas), i));
			} else if (judge == 1) {
				judge_btn.setBackgroundResource(R.drawable.selector_pj_z);
				out_et[i].setText(target[i] + "\n" + datas[0] + datas[1]
						+ datas[2] + datas[3] + judgeZ(comparator(datas), i));

			}
		}

	}

	/**
	 * 做庄的判断事件
	 * 
	 * @author LZB
	 * 
	 */
	public class JudgeButtonListener implements OnClickListener {
		public void onClick(View v) {
			if (!isComplete() && arr_out <= 0)
				return;
			if (judge == 0) {
				judge_btn.setBackgroundResource(R.drawable.selector_pj_z);
				judge = 1;
				if (arr_out >= 8) {
					disPlayOrder();
				}

			} else if (judge == 1) {
				judge = 0;
				judge_btn.setBackgroundResource(R.drawable.selector_pj_x);
				if (arr_out >= 8) {
					disPlayOrder();
				}
			}

		}
	}

	/**
	 * 做庄的判断函数
	 * 
	 * @param a
	 *            [] 循环比较后获的数组
	 * @param comparator_num
	 *            对应的行数
	 * @author LZB
	 * 
	 */
	private String judgeZ(int[] a, int comparator_num) {
		int[] t = new int[3];
		int j = 0, q = 0;
		String z = null;
		for (int i = 0, x = 0; i < a.length; i++) {
			if (a[i] == 3) {
				t[x++] = i + 1;
				j++;
			} else if (a[i] == 4) {
				q++;
			}
		}
		if (q == 3) {
			z = "TX";
		} else if (j > 0 && j < 4) {
			if (t[2] != 0) {
				z = "X" + t[0] + t[1] + t[2];
			} else if (t[1] != 0) {
				z = "X" + t[0] + t[1];
			} else {
				z = "X" + t[0];
			}
		} else if (j == 0) {
			z = "X0";
		}

		return z;
	}

	/**
	 * 做闲的判断函数
	 * 
	 * @param a
	 *            [] 循环比较后获的数组
	 * @param comparator_num
	 *            对应的行数
	 * @author LZB
	 * 
	 */
	private String judgeX(int[] a, int comparator_num) {
		int t = 0, j = 0;
		String x = null;
		for (int i = 0; i < a.length; i++)
			if (a[i] == 1) {
				t = i;
				j++;
			}
		if (j == 1) {
			t = t + 1;
			x = "Y" + t;

		} else if (j == 0) {
			x = "Y0";
		}
		return x;
	}

	/**
	 * 判断比较函数，数组依次转换，最后一个转换到第一个，第一个依次比较后面三个
	 * 
	 * @param :a[] 比较顺序的数组
	 * @return:比较数组里第一个与后面三个数大小，若第一个大，则对应数组那个下标所在新的数组自加1.
	 * @author LZB
	 * 
	 */
	private int[] comparator(int[] a) {
		int t;
		int[] b = new int[] { 0, 0, 0, 0 };
		for (int i = 0; i < a.length; i++) {
			t = a[0];
			a[0] = a[1];
			a[1] = a[2];
			a[2] = a[3];
			a[3] = t;
			for (int j = 1; j < a.length; j++) {
				if (a[0] >= a[j])
					b[j]++;
			}
		}
		return b;
	}


	/**
	 * 开始时默认使用ZZ和规则A按钮事件
	 * 
	 */
	private void defaultEvent() {
		if (arr_out >= 8) {
			TzStatusDatas.getInstance().setRegulationIndex(regulation_judge);
			disPlayOrder();
		}
	}

	/**
	 * 规则信息输出事件类
	 * 
	 */
	private class RegulationTextListenser implements OnClickListener {

		public void onClick(View v) {
			if (0 == edit_judge) {
				editText_1.setText(regulation_text);
				edit_judge = 1;
			} else if (1 == edit_judge) {
				editText_1.setText("见谅，我现在正忙，待会联系你");
				edit_judge = 0;
			}
		}

	}

	/**
	 * 获得输入牌的点数对应的PorkNum码
	 * 
	 * @return
	 */
	private int[] getInputNums() {
		int[] result = new int[8];
		String[] o = { "\n0", "\n1", "\n2", "\n3", "\n4", "\n5", "\n6", "\n7",
				"\n8", "\n9" };
		String[] p = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		String[] inputNum = out_num_et.getText().toString().split("-");
		if (arr_out > 4) {
			for (int j = 0; j < o.length; j++) {
				if (inputNum[4].equals(o[j])) {
					inputNum[4] = p[j];
				}
			}
		}
		for (int i = 0; i < inputNum.length; i++) {
			result[i] = Integer.parseInt(inputNum[i]);
		}
		return result;
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
