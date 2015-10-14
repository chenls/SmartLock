package com.chenls.smartlock.com.chenls.smarlock.setting;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chenls.smartlock.R;
import com.chenls.smartlock.SettingActivity;

public class Choose extends Activity {
    public static final String NO_PSD = "0";
    public static final String CHOOSE_RESULT = "result";
    private static final int REQUEST_NUM_PSD = 1;
    public static final String NUM_PSD = "1";
    public static final String NO = "no";
    public static final String YES = "yes";
    public static final String IS_AUTO_CONNECT = "isAutoConnect";
    public static final String IS_MANUAL_SET_NOT_AUTO_CONNECT = "isManual";
    private TextView title, isTrue, isFalse;
    private boolean isAutoConnect;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_choose);
        title = (TextView) findViewById(R.id.title);
        isTrue = (TextView) findViewById(R.id.isTrue);
        isFalse = (TextView) findViewById(R.id.isFalse);
        title.setOnClickListener(new OnClickListener());
        isTrue.setOnClickListener(new OnClickListener());
        isFalse.setOnClickListener(new OnClickListener());
        try {
            sharedPreferences = this.getSharedPreferences(Input.MY_DATA,
                    Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = this.getIntent();        //获取已有的intent对象
        Bundle bundle = intent.getExtras();    //获取intent里面的bundle对象
        try {
            isAutoConnect = bundle.getBoolean(SettingActivity.AUTO_CONNECT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isAutoConnect) {
            title.setText(getString(R.string.autoConnect));
            isTrue.setText(getString(R.string.yes));
            isFalse.setText(getString(R.string.no));
        }
    }

    public void titleImageButton(View view) {
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public class OnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (isAutoConnect) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (v == isTrue) {
                    editor.putBoolean(IS_AUTO_CONNECT, true);
                    editor.putBoolean(IS_MANUAL_SET_NOT_AUTO_CONNECT, false);
                    finishAndPutData(YES);
                } else if (v == isFalse) {
                    editor.putBoolean(IS_AUTO_CONNECT, false);
                    editor.putBoolean(IS_MANUAL_SET_NOT_AUTO_CONNECT, true);
                    finishAndPutData(NO);
                }
                editor.commit();
                return;
            }
            if (v == isTrue) {
                Intent newIntent = new Intent(Choose.this, Input.class);
                startActivityForResult(newIntent, REQUEST_NUM_PSD);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else if (v == isFalse) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(Input.IS_NEED_PSD, false);
                editor.putString(Input.PSD, null);
                editor.commit();
                finishAndPutData(NO_PSD);
            }
        }
    }

    private void finishAndPutData(String noPsd) {
        Bundle b = new Bundle();
        b.putString(CHOOSE_RESULT, noPsd);
        Intent result = new Intent();
        result.putExtras(b);
        setResult(Activity.RESULT_OK, result);
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            case REQUEST_NUM_PSD:
                if (resultCode == Activity.RESULT_OK && data != null) {
                    boolean result = data.getBooleanExtra(Input.NUM_PSD, false);
                    if (result) {
                        finishAndPutData(NUM_PSD);
                    }
                }
                break;
        }
    }
}
