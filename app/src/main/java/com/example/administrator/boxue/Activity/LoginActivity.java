package com.example.administrator.boxue.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.boxue.R;
import com.example.administrator.boxue.utils.MD5Utils;


public class                                                                                                                        LoginActivity extends AppCompatActivity {
    private TextView tv_main_title;
    private TextView tv_back,tv_register,tv_find_psw;
    private Button btn_login;
    private String userName,psw,spPsw;
    private EditText et_user_name,et_psw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        tv_main_title=(TextView) findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_back=(TextView) findViewById(R.id.tv_back);
        tv_register=(TextView) findViewById(R.id.tv_register);
        tv_find_psw=(TextView) findViewById(R.id.tv_find_psw);
        btn_login=(Button) findViewById(R.id.btn_login);
        et_user_name=(EditText) findViewById(R.id.et_user_name);
        et_psw=(EditText) findViewById(R.id.et_psw );
        //返回按钮点击时间
        tv_back.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginActivity.this.finish();
            }
        });
        //立即注册的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //找回密码控件的点击事件
        tv_find_psw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
         //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = et_user_name.getText().toString().trim();
                psw = et_psw.getText().toString().trim();
                String md5Psw = MD5Utils.md5(psw);
                spPsw = readPsw(userName);
                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(psw)) {
                    Toast.makeText(LoginActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                    return;
                } else if (md5Psw.equals(spPsw)) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //保存登录状态和登录的用户名
                    savaLofinStatus(true, userName);
                    //吧登录状态传递到MainActivity中
                    Intent data = new Intent();
                    data.putExtra("isLogin", true);
                    setResult(RESULT_OK, data);
                    LoginActivity.this.finish();
                    return;
                } else if ((!TextUtils.isEmpty(spPsw) && !md5Psw.equals(spPsw))) {
                    Toast.makeText(LoginActivity.this, "输入的用户名和密码不一致", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    Toast.makeText(LoginActivity.this, "此用户名不存在", Toast.LENGTH_SHORT).show();
                }
            }
                });
            }
//从sharedPreferences中根据用户名读取密码
            private String readPsw(String userName) {
                SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
                return sp.getString(userName,"");
            }

//保存登录状态和登录用户名到Sharedpreferences中
    private void savaLofinStatus(boolean status, String userName) {
        SharedPreferences sp=getSharedPreferences("LoginInfo",MODE_PRIVATE);
        SharedPreferences.Editor editor=sp.edit();//获取编辑器
        editor.putBoolean("isLogin",status);
        editor.putString("loginUserName",userName);//存入登录时的用户名
        editor.commit();//提交修改
    }
    @Override
    protected void onActivityResult(int requestCode,int resultcode,Intent data){
        super.onActivityResult(requestCode,requestCode,data);
        if(data!=null){
            String userName=data.getStringExtra("userName");
            if(!TextUtils.isEmpty(userName)){
                et_user_name.setText(userName);
                //设置光标的位置
                et_user_name.setSelection(userName.length());
            }
        }
    }
}
