package com.bawei.shaopenglai.ui.mineui;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bawei.shaopenglai.MainActivity;
import com.bawei.shaopenglai.R;
import com.lljjcoder.citypickerview.widget.CityPicker;

public class CityListActivity extends AppCompatActivity {

    private CityPicker cityPicker;
    private TextView adresss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
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
            String province = citySelected[0];
            //城市
            String city = citySelected[1];
            //区县（如果设定了两级联动，那么该项返回空）
            String district = citySelected[2];
            //邮编
            String code = citySelected[3];
            adresss.setText(province + city + district);
}

        @Override
        public void onCancel() {


            }
        });

    }
}
