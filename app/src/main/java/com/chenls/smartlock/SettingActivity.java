package com.chenls.smartlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chenls.smartlock.com.chenls.smarlock.setting.Choose;
import com.chenls.smartlock.com.chenls.smarlock.setting.Input;

public class SettingActivity extends Activity {
    private static final int REQUEST_SETPSD = 1;
    private static final int REQUEST_AUTO_CONNECT = 2;
    private static final int REQUEST_CHANGE_NAME = 3;
    public static final String AUTO_CONNECT = "isAutoConnect";
    public static final String CHANGE_NAME = "changeName";
    public static final String SURE_PSD = "surePSD";
    private TextView setPsd, psd, changeName, autoConnect, isAutoConnect;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_setting);
        setPsd = (TextView) findViewById(R.id.setPsd);
        psd = (TextView) findViewById(R.id.psd);
        changeName = (TextView) findViewById(R.id.changeName);
        autoConnect = (TextView) findViewById(R.id.autoConnect);
        isAutoConnect = (TextView) findViewById(R.id.isAutoConnect);
        setPsd.setOnClickListener(new OnClickListener());
        changeName.setOnClickListener(new OnClickListener());
        autoConnect.setOnClickListener(new OnClickListener());
        try {
            sharedPreferences = this.getSharedPreferences(Input.MY_DATA,
                    Context.MODE_PRIVATE);
        } catch (Exception e) {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sharedPreferences.getBoolean(Input.IS_NEED_PSD, false)) {
            psd.setText(R.string.numPsd);
        } else {
            psd.setText(R.string.noPsd);
        }
        if (sharedPreferences.getBoolean(Choose.IS_AUTO_CONNECT, false)) {
            isAutoConnect.setText(R.string.yes);
        } else {
            isAutoConnect.setText(R.string.no);
        }

    }

    public class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v == setPsd) {
                if (sharedPreferences.getBoolean(Input.IS_NEED_PSD, false)) {
                    Intent newIntent = new Intent(SettingActivity.this, Input.class);
                    Bundle bundle = new Bundle(); //创建Bundle对象
                    bundle.putBoolean(SURE_PSD, true);     // 标示是autoConnect 启动的新Activity
                    newIntent.putExtras(bundle);
                    startActivity(newIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    return;
                }
                Intent newIntent = new Intent(SettingActivity.this, Choose.class);
                startActivityForResult(newIntent, REQUEST_SETPSD);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if (v == changeName) {
                Intent newIntent = new Intent(SettingActivity.this, Input.class);
                Bundle bundle = new Bundle(); //创建Bundle对象
                bundle.putBoolean(CHANGE_NAME, true);     // 标示是autoConnect 启动的新Activity
                newIntent.putExtras(bundle);
                startActivityForResult(newIntent, REQUEST_CHANGE_NAME);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if (v == autoConnect) {
                Intent newIntent = new Intent(SettingActivity.this, Choose.class);
                Bundle bundle = new Bundle(); //创建Bundle对象
                bundle.putBoolean(AUTO_CONNECT, true);     // 标示是autoConnect 启动的新Activity
                newIntent.putExtras(bundle);
                startActivityForResult(newIntent, REQUEST_AUTO_CONNECT);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_SETPSD:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String result = data.getStringExtra(Choose.CHOOSE_RESULT);
                    if ((Choose.NO_PSD).equals(result)) {
                        psd.setText(getString(R.string.noPsd));
                    } else if ((Choose.NUM_PSD).equals(result)) {
                        psd.setText(getString(R.string.numPsd));
                    }
                }
                break;
            case REQUEST_AUTO_CONNECT:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String result = data.getStringExtra(Choose.CHOOSE_RESULT);
                    if ((Choose.NO).equals(result)) {
                        isAutoConnect.setText(getString(R.string.no));
                    } else if ((Choose.YES).equals(result)) {
                        isAutoConnect.setText(getString(R.string.yes));
                    }
                }
                break;
            case REQUEST_CHANGE_NAME:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    String value = data.getStringExtra(Input.CHANGE_NAME);
                    Bundle b = new Bundle();
                    b.putString(CHANGE_NAME, value);
                    Intent intent = new Intent();
                    intent.putExtras(b);
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }

                break;
            default:
                break;
        }
    }

    private void closeSetting() {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            closeSetting();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_MENU) { //监控/拦截菜单键
            closeSetting();
            return true;
        } else
            return super.onKeyDown(keyCode, event);
    }
}
