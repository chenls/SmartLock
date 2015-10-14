package com.chenls.smartlock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.chenls.smartlock.com.chenls.smarlock.setting.Input;

public class MyPhoneCardActivity extends Activity implements View.OnLongClickListener {
    public static final String PSD = "passWord";
    private EditText et_pwd;
    private Button one, two, three, four, five, six, seven, eight, nine, zero;
    private ImageButton pound;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_my_phone_card);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_pwd.setCursorVisible(false);
        et_pwd.setKeyListener(null);
        one = (Button) findViewById(R.id.one);
        one.setOnClickListener(new OnClickListener());
        two = (Button) findViewById(R.id.two);
        two.setOnClickListener(new OnClickListener());
        three = (Button) findViewById(R.id.three);
        three.setOnClickListener(new OnClickListener());
        four = (Button) findViewById(R.id.four);
        four.setOnClickListener(new OnClickListener());
        five = (Button) findViewById(R.id.five);
        five.setOnClickListener(new OnClickListener());
        six = (Button) findViewById(R.id.six);
        six.setOnClickListener(new OnClickListener());
        seven = (Button) findViewById(R.id.seven);
        seven.setOnClickListener(new OnClickListener());
        eight = (Button) findViewById(R.id.eight);
        eight.setOnClickListener(new OnClickListener());
        nine = (Button) findViewById(R.id.nine);
        nine.setOnClickListener(new OnClickListener());
        zero = (Button) findViewById(R.id.zero);
        zero.setOnClickListener(new OnClickListener());
        pound = (ImageButton) findViewById(R.id.pound);
        pound.setOnClickListener(new OnClickListener());
        pound.setOnLongClickListener(this);//注册监听
        try {
            sharedPreferences = this.getSharedPreferences(Input.MY_DATA,
                    Context.MODE_PRIVATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // handler自带方法实现定时器
            try {
                handler.postDelayed(this, 100);
                delete();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public boolean onLongClick(View v) {
        if (v == pound) {
            handler.postDelayed(runnable, 100);
        }
        return false;
    }

    private void delete() {
        String Str = et_pwd.getText().toString();
        if (Str.length() > 0) {
            et_pwd.setText(Str.substring(0, Str.length() - 1));
        }
    }

    public class OnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            if (v == one) {
                et_pwd.setText(et_pwd.getText().toString() + "1");
            } else if (v == two) {
                et_pwd.setText(et_pwd.getText().toString() + "2");
            } else if (v == three) {
                et_pwd.setText(et_pwd.getText().toString() + "3");
            } else if (v == four) {
                et_pwd.setText(et_pwd.getText().toString() + "4");
            } else if (v == five) {
                et_pwd.setText(et_pwd.getText().toString() + "5");
            } else if (v == six) {
                et_pwd.setText(et_pwd.getText().toString() + "6");
            } else if (v == seven) {
                et_pwd.setText(et_pwd.getText().toString() + "7");
            } else if (v == eight) {
                et_pwd.setText(et_pwd.getText().toString() + "8");
            } else if (v == nine) {
                et_pwd.setText(et_pwd.getText().toString() + "9");
            } else if (v == zero) {
                et_pwd.setText(et_pwd.getText().toString() + "0");
            } else if (v == pound) {
                delete();
                handler.removeCallbacks(runnable);
            }
            isRightPsd(et_pwd.getText().toString());
        }
    }

    private void isRightPsd(String msg) {

        if (!TextUtils.isEmpty(msg)) {
            String passWord = sharedPreferences.getString(Input.PSD, null);
            if (msg.equals(passWord)) {
                Bundle b = new Bundle();
                b.putBoolean(PSD, true);
                Intent result = new Intent();
                result.putExtras(b);
                setResult(Activity.RESULT_OK, result);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    }
}
