package com.bawei.shaopenglai;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.home.Loginbean;
import com.bawei.shaopenglai.custom.Constants;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * @author Peng
 * @time 2018/12/29 15:10
 */

public class MainActivity extends AppCompatActivity implements IView {

    @BindView(R.id.activity_login_phone)
    EditText activityLoginPhone;
    @BindView(R.id.login_pass)
    ImageButton loginPass;
    @BindView(R.id.activity_login_password)
    EditText activityLoginPassword;
    @BindView(R.id.Passwordswitching)
    ImageButton Passwordswitching;
    @BindView(R.id.Rememberpassword)
    AppCompatCheckBox Rememberpassword;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.loginSubmit)
    Button loginSubmit;
    @BindView(R.id.login_phone)
    ImageButton loginPhone;
    @BindView(R.id.group)
    RadioGroup group;
    @BindView(R.id.buju1)
    ConstraintLayout buju1;
    @BindView(R.id.pass_group)
    RadioGroup passGroup;
    @BindView(R.id.buju2)
    ConstraintLayout buju2;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private IPresenterImpl iPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        initView();
    }


    private void initView(){
        sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = sharedPreferences.edit();
        iPresenter=new IPresenterImpl(this);
        //将记住密码状态值取出
        boolean r_check = sharedPreferences.getBoolean("r_check", false);
        if (r_check){
            //取出值
            String phones = sharedPreferences.getString("phones", null);
            String pwds = sharedPreferences.getString("pwds", null);
            //设置值
            activityLoginPhone.setText(phones);
            activityLoginPassword.setText(pwds);
            Rememberpassword.setChecked(true);
        }
        /**
         * 密码显示与隐藏
         */
        Passwordswitching.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction()==MotionEvent.ACTION_DOWN){
                    activityLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.
                            getInstance());
                    Passwordswitching.setBackgroundResource(R.drawable.ic_action_eye);
                }else if (event.getAction()==MotionEvent.ACTION_UP){
                    activityLoginPassword.setTransformationMethod(PasswordTransformationMethod.
                            getInstance());
                    Passwordswitching.setBackgroundResource(R.drawable.ic_action_name);
                }
                return false;
            }
        });
        //loadData();
    }

    @OnClick({R.id.activity_login_phone, R.id.login_pass, R.id.activity_login_password,
            R.id.Passwordswitching, R.id.Rememberpassword, R.id.register, R.id.loginSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.activity_login_phone:
                break;
            case R.id.login_pass:
                break;
            case R.id.activity_login_password:
                break;
            case R.id.Rememberpassword:

                break;
            case R.id.register:
                /**
                 * 点击跳转注册页面
                 */
                Intent intent=new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.loginSubmit:
                String phones = activityLoginPhone.getText().toString();
                String pwds = activityLoginPassword.getText().toString();
                //判断记住密码是否勾选
                if (Rememberpassword.isChecked()){
                    //将值存入
                    editor.putString("phones",phones);
                    editor.putString("pwds",pwds);
                    //存入一个勾选状态
                    editor.putBoolean("r_check",true);
                    //提交
                    editor.commit();
                }else {
                    //清空
                    editor.clear();
                    //提交
                    editor.commit();
                }
                loadData();
                break;
                default:
                    break;
        }
    }

    private void loadData() {
        Map<String,String> params=new HashMap<>();
        params.put(Constants.POST_BODY_KEY_LOGIN_PHONE,activityLoginPhone.
                getText().toString().trim());
        params.put(Constants.POST_BODY_KEY_LOGIN_PASSWORD,activityLoginPassword.
                getText().toString().trim());
        iPresenter.startRequestPost(Apis.URL_LOGIN_POST,params,Loginbean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof Loginbean){
            Loginbean loginbean= (Loginbean) data;
            if (loginbean==null||!loginbean.isSuceess()){
                Toast.makeText(MainActivity.this,loginbean.getMessage(),
                        Toast.LENGTH_LONG).show();
            }else {
                SharedPreferences preferences = getSharedPreferences("UserShao", MODE_PRIVATE);
                preferences.edit().putString("userId",loginbean.getResult().getUserId()+"").
                        putString("sessionId",loginbean.getResult().getSessionId()).commit();
                EventBus.getDefault().postSticky(new EventBean("main",data));
                Intent intet=new Intent(MainActivity.this,
                        ShoppingTrolleyActivity.class);
                startActivity(intet);
                finish();
            }

        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(MainActivity.this,error,Toast.LENGTH_LONG).show();
    }
}
