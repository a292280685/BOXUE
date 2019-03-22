package com.example.administrator.boxue.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.boxue.R;
import com.example.administrator.boxue.utils.AnalysisUtils.AnalysisUtils;

public class MyInfoView {
    public ImageView iv_head_icon;
    private LinearLayout ll_head;
    private RelativeLayout rl_course_history,rl_setting;
    private TextView tv_user_name;
    private Activity mContext;
    private LayoutInflater mInflater;
    private View mCurrentView;
    public MyInfoView(Activity context){
        mContext=context;
        mInflater=LayoutInflater.from(mContext);
    }
    private void createView(){
        initView();
    }

    private void initView() {
        mCurrentView=mInflater.inflate(R.layout.main_view_myinfo,null);
        ll_head=(LinearLayout) mCurrentView.findViewById(R.id.ll_head);
        iv_head_icon=(ImageView) mCurrentView.findViewById(R.id.iv_head_icon);
        rl_course_history=(RelativeLayout) mCurrentView.findViewById(R.id.rl_course_history);
        rl_setting=(RelativeLayout) mCurrentView.findViewById(R.id.rl_setting);
        tv_user_name=(TextView) mCurrentView.findViewById(R.id.tv_user_name);
        mCurrentView.setVisibility(View.VISIBLE);
        setLoginParams(readLoginStatus());
        ll_head.setOnClickListener(new View.OnClickListener(){
        @Override
                public void onClick(View v){
            if(readLoginStatus()){

            }else {
                Intent intent=new Intent(mContext,LoginActivity.class);
                mContext.startActivityForResult(intent,1);
            }
            }
        });
        rl_course_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(readLoginStatus()){

                }else {
                    Toast.makeText(mContext,"您还未登录，请先登录",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        rl_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(readLoginStatus()){

                }else {
                    Toast.makeText(mContext,"您还未登录，请先登录",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void setLoginParams(boolean isLogin) {
        if(isLogin){
            tv_user_name.setText(AnalysisUtils.readLoginUserName(mContext));
        }else {
            tv_user_name.setText("点击登陆");
        }
    }
    public View getView(){
        if(mCurrentView==null){
            createView();
        }
        return mCurrentView;
    }
    public void showView(){
        if(mCurrentView ==null){
            createView();
        }
        mCurrentView.setVisibility(View.VISIBLE);
    }
    private boolean readLoginStatus() {
        SharedPreferences sp=mContext.getSharedPreferences("loginInfo",Context.MODE_PRIVATE);
        boolean isLogin=sp.getBoolean("isLogin",false);
        return isLogin;
    }



}
