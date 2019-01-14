package com.bawei.shaopenglai;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bawei.shaopenglai.fragment.HomeFragment;
import com.bawei.shaopenglai.fragment.MineFragment;
import com.bawei.shaopenglai.fragment.MomentsFragment;
import com.bawei.shaopenglai.fragment.PurchaseOrderFragment;
import com.bawei.shaopenglai.fragment.ShoppingTrolleyFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Peng
 */
public class ShoppingTrolleyActivity extends AppCompatActivity {

    @BindView(R.id.contents)
    ViewPager contents;
    @BindView(R.id.homeRadio)
    RadioButton homeRadio;
    @BindView(R.id.momentsRadio)
    RadioButton momentsRadio;
    @BindView(R.id.shoppingTrolleyRadio)
    RadioButton shoppingTrolleyRadio;
    @BindView(R.id.purchaseOrderRadio)
    RadioButton purchaseOrderRadio;
    @BindView(R.id.mineRadio)
    RadioButton mineRadio;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    private List<Fragment> fragmentList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_trolley);
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
        fragmentList=new ArrayList<>();
        fragmentList.add(new HomeFragment());
        fragmentList.add(new MomentsFragment());
        fragmentList.add(new ShoppingTrolleyFragment());
        fragmentList.add(new PurchaseOrderFragment());
        fragmentList.add(new MineFragment());
        contents.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.homeRadio:
                        contents.setCurrentItem(0);
                        break;
                    case R.id.momentsRadio:
                        contents.setCurrentItem(1);
                        break;
                    case R.id.shoppingTrolleyRadio:
                        contents.setCurrentItem(2);
                        break;
                    case R.id.purchaseOrderRadio:
                        contents.setCurrentItem(3);
                        break;
                    case R.id.mineRadio:
                        contents.setCurrentItem(4);
                        break;
                }
            }
        });
        contents.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        radioGroup.check(R.id.homeRadio);
                        break;
                    case 1:
                        radioGroup.check(R.id.momentsRadio);
                        break;
                    case 2:
                        radioGroup.check(R.id.shoppingTrolleyRadio);
                        break;
                    case 3:
                        radioGroup.check(R.id.purchaseOrderRadio);
                        break;
                    case 4:
                        radioGroup.check(R.id.mineRadio);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }
}
