package com.bawei.shaopenglai.ui.mineui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.lljjcoder.citypickerview.widget.CityPicker;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityListActivity extends AppCompatActivity {

    @BindView(R.id.myName)
    TextView myName;
    @BindView(R.id.viewTwo)
    View viewTwo;
    @BindView(R.id.myPassword)
    TextView myPassword;
    @BindView(R.id.viewThree)
    View viewThree;
    @BindView(R.id.mycity)
    TextView mycity;
    @BindView(R.id.adresss)
    TextView adresss;
    @BindView(R.id.viewFour)
    View viewFour;
    @BindView(R.id.myXiangxicity)
    TextView myXiangxicity;
    @BindView(R.id.viewFive)
    View viewFive;
    @BindView(R.id.youxiao)
    TextView youxiao;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.myPhone)
    TextView myPhone;
    private CityPicker cityPicker;
    private String province;
    private String city;
    private String district;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        adresss = (TextView) findViewById(R.id.adresss);
        adresss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCityPicker();
                cityPicker.show();
            }
        });
        Intent intent=getIntent();
        String Name = intent.getStringExtra("nickName");
        String phone = intent.getStringExtra("phone");
        nickname.setText(Name);
        myPhone.setText(phone);

    }

    private void initCityPicker() {
        //滚轮文字的大小
        //滚轮文字的颜色
        //省份滚轮是否循环显示
        //城市滚轮是否循环显示
        //地区（县）滚轮是否循环显示
        //滚轮显示的item个数
        //滚轮item间距
        cityPicker = new CityPicker.Builder(CityListActivity.this)
                //滚轮文字的大小
                .textSize(20)
                .title("地址选择")
                .backgroundPop(0xa0000000)
                .titleBackgroundColor("#0CB6CA")
                .titleTextColor("#000000")
                .backgroundPop(0xa0000000)
                .confirTextColor("#000000")
                .cancelTextColor("#000000")
                .province("xx省")
                .city("xx市")
                .district("xx区")
                //滚轮文字的颜色
                .textColor(Color.parseColor("#000000"))
                //省份滚轮是否循环显示
                .provinceCyclic(true)
                //城市滚轮是否循环显示
                .cityCyclic(false)
                //地区（县）滚轮是否循环显示
                .districtCyclic(false)
                //滚轮显示的item个数
                .visibleItemsCount(7)
                //滚轮item间距
                .itemPadding(10)
                .onlyShowProvinceAndCity(false)
                .build();
        //监听方法，获取选择结果
        cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
            @Override
            public void onSelected(String... citySelected) {
                //省份
                province = citySelected[0];
                //城市
                city = citySelected[1];
                //区县（如果设定了两级联动，那么该项返回空）
                district = citySelected[2];
                //邮编
                code = citySelected[3];
                adresss.setText(province + city + district);
                youxiao.setText(code);
            }

            @Override
            public void onCancel() {


            }
        });

    }

}