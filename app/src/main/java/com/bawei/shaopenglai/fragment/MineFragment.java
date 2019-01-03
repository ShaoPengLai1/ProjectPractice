package com.bawei.shaopenglai.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.ui.mineui.MyGroupActivity;
import com.bawei.shaopenglai.ui.mineui.PersonalInformationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author Peng
 * @time 2018/12/29 19:09
 */

public class MineFragment extends Fragment {

    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.personaldata)
    TextView personaldata;
    @BindView(R.id.mycircle)
    TextView mycircle;
    @BindView(R.id.footprint)
    TextView footprint;
    @BindView(R.id.Wallet)
    TextView Wallet;
    @BindView(R.id.shippingaddress)
    TextView shippingaddress;
    @BindView(R.id.mine_icon)
    ImageView mineIcon;
    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //当系统版本为4.4或者4.4以上时可以使用沉浸式状态栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.nickname, R.id.personaldata, R.id.mycircle, R.id.footprint, R.id.Wallet, R.id.shippingaddress, R.id.mine_icon})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.nickname:
                break;
            case R.id.personaldata:
                Intent intent=new Intent(getActivity(),PersonalInformationActivity.class);
                startActivity(intent);
                break;
            case R.id.mycircle:
                Intent intent1=new Intent(getActivity(),MyGroupActivity.class);
                startActivity(intent1);
                break;
            case R.id.footprint:
                break;
            case R.id.Wallet:
                break;
            case R.id.shippingaddress:
                break;
            case R.id.mine_icon:
                break;
                default:
                    break;
        }
    }
}
