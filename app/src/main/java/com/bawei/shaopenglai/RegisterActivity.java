package com.bawei.shaopenglai;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.bawei.shaopenglai.bean.RegisterBean;
import com.bawei.shaopenglai.custom.Constants;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity implements IView {

    @BindView(R.id.activity_login_phone)
    EditText activityLoginPhone;
    @BindView(R.id.register_phone)
    ImageButton registerPhone;
    @BindView(R.id.activity_register_phone)
    EditText activityRegisterPhone;
    @BindView(R.id.login_pass)
    ImageButton loginPass;
    @BindView(R.id.activity_login_password)
    EditText activityLoginPassword;
    @BindView(R.id.Passwordswitching)
    ImageButton Passwordswitching;
    @BindView(R.id.register)
    TextView register;
    @BindView(R.id.loginSubmit)
    Button loginSubmit;
    @BindView(R.id.zhuce_group)
    RadioGroup zhuceGroup;
    @BindView(R.id.Sendverification)
    TextView Sendverification;
    @BindView(R.id.group)
    RadioGroup group;
    private IPresenterImpl iPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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


    private void initView() {
        iPresenter=new IPresenterImpl(this);
        /**
         * 密码显示与隐藏
         */
        Passwordswitching.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    activityLoginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    Passwordswitching.setBackgroundResource(R.drawable.ic_action_eye);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    activityLoginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    Passwordswitching.setBackgroundResource(R.drawable.ic_action_name);
                }
                return false;
            }
        });
    }

    @OnClick({R.id.activity_login_phone, R.id.register_phone, R.id.activity_register_phone, R.id.login_pass, R.id.activity_login_password, R.id.Passwordswitching, R.id.register, R.id.loginSubmit})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.register:
                Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.loginSubmit:
                loadData();
                break;
            case R.id.Sendverification:
                break;
            default:
                break;
        }
    }

    private void loadData() {
        Map<String,String> params=new HashMap<>();
        params.put(Constants.POST_BODY_KEY_REGISTER_PHONE,activityLoginPhone.getText().toString().trim());
        params.put(Constants.POST_BODY_KEY_REGISTER_PASSWORD,activityLoginPassword.getText().toString().trim());
        iPresenter.startRequestPost(Apis.URL_REGISTER_POST,params,RegisterBean.class);
    }


    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof RegisterBean){
            RegisterBean registerBean= (RegisterBean) data;
            if (registerBean==null||!registerBean.isSuceess()){
                Toast.makeText(RegisterActivity.this,registerBean.getMessage(),Toast.LENGTH_LONG).show();
            }else {
                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(RegisterActivity.this,error,Toast.LENGTH_LONG).show();
    }
}
