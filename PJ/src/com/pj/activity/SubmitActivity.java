package com.pj.activity;

import java.io.File;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.telephony.TelephonyManager;

import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TableLayout;


public class SubmitActivity extends Activity {

	/**
	 * @params first_code是获取到IMEI的初始值
	 */
	private static int first_code;
	/**
	 * @params login_code是注册码
	 */
	private static int login_code;
	/**
	 * 注册码的输入框
	 */
	private EditText edit_imei;
	/**
	 * 定义一个手机管理变量，用来获得手机IMEI
	 */
	private TelephonyManager tm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.submit);
		//
		tm = (TelephonyManager) this.getSystemService(TELEPHONY_SERVICE);
		first_code = Integer.parseInt(tm.getDeviceId().substring(9, 15));
		
		// 用来判断是否是第一次运行
		if (fileIsExists()) {// 文件存在则读取
			// 这个是文件读取
			SharedPreferences sharedata1 = getSharedPreferences("data", 0);
			String data = sharedata1.getString("code", null);
			//获得注册码
			login_code = getLoginCode(first_code);		
			if (data.equals(login_code + "")) {// 判断注册码是否正确
				runNext();
			} else {
				iniDialog();
			}
		} else {
			runNext();
			//iniDialog();
		}
	}

	/**
	 * 第一个获得初始码的窗口
	 */
	private void iniDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("请将验证码：" + "“" + first_code + "”" + "提交到注册码生成软件上得到注册码")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						getIMEIDialog();
					}
				})
				.setNegativeButton("退出", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						exit();
					}
				}).create().show();
	}

	/**
	 * 用来输入注册码的窗口
	 */
	private void getIMEIDialog() {
		TableLayout loginForm = (TableLayout) getLayoutInflater().inflate(
				R.layout.edit_imei, null);

		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("请输入注册码：").setView(loginForm);
		edit_imei = (EditText) loginForm.findViewById(R.id.code);
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (edit_imei.getText()
						.toString().equals("")) 
					reGetIMEIDialog();
				else {
					Integer edit_code = Integer.parseInt(edit_imei.getText()
							.toString());
					login_code = getLoginCode(first_code);					
					if (edit_code == login_code) {
						saveCode();
						runNext();
					} else {
						reGetIMEIDialog();
					}
					
				}
				
				
			}
		}).setNegativeButton("退出", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				exit();
			}
		}).create().show();
	}

	/**
	 * 如果输入错误的提示窗口
	 */
	private void reGetIMEIDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("注册码有误").setMessage("请重新输入注册码")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						getIMEIDialog();
					}
				})
				.setNegativeButton("退出", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						exit();
					}
				}).create().show();
	}

	/**
	 * 保存注册码，并且生成第一个文件、
	 */
	private void saveCode() {
		// 保存文件
		SharedPreferences.Editor sharedata = getSharedPreferences("data", 0)
				.edit();
		sharedata.putString("code", login_code + "");
		sharedata.commit();

	}

	/**
	 * 判断是否是第一次使用
	 */
	public boolean fileIsExists() {
		File file = new File("/data/data/com.pj.activity/shared_prefs/data.xml");// 必须是完全路径
		if (!file.exists()) {
			return false;
		}
		return true;
	}
	/**
	 * 注册码的计算方法
	 */
	private int getLoginCode(int firstcode){
		int i = 1;
		int h = firstcode;
		int k;
		for (int j = 0; j < 6; j++) {
			i = i*10;
			k = h%i;
			h = (h-k)/i;
			firstcode = firstcode+(k+j)*i/10%i;
		}	
		return firstcode;
	}

	/**
	 * 跳转到下一个界面
	 */
	private void runNext() {

		new Handler().postDelayed(new Runnable() {

			public void run() {

				Intent i = new Intent();

				i.setClass(SubmitActivity.this, PJPhotoActivity.class);

				startActivity(i);

				finish();

			}

		}, 2000);

	}
	// 退出按钮
		public boolean dispatchKeyEvent(KeyEvent event) {
			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				if (event.getAction() == KeyEvent.ACTION_DOWN
						&& event.getRepeatCount() == 0) {
					exit();
					return true;
				}
			}
			return super.dispatchKeyEvent(event);
		}

	/**
	 * 退出方法
	 */
	private void exit() {
		finish();
	}
}
