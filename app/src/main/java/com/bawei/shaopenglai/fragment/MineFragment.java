package com.bawei.shaopenglai.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.Loginbean;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.ui.mineui.AddAddressActivity;
import com.bawei.shaopenglai.ui.mineui.MainFootActivity;
import com.bawei.shaopenglai.ui.mineui.MineMoneyActivity;
import com.bawei.shaopenglai.ui.mineui.MyGroupActivity;
import com.bawei.shaopenglai.ui.mineui.PersonalInformationActivity;
import com.bawei.shaopenglai.view.IView;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Peng
 * @time 2018/12/29 19:09
 */

public class MineFragment extends Fragment implements IView {

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

    Unbinder unbinder;
    @BindView(R.id.mine_icon)
    SimpleDraweeView mineIcon;
    private IPresenterImpl iPresenter;
    private Loginbean loginbean;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        iPresenter = new IPresenterImpl(this);
        sharedPreferences = getActivity().getSharedPreferences("Address", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        loadData();

    }

    private void loadData() {

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
                Intent intent = new Intent(getActivity(), PersonalInformationActivity.class);
                intent.putExtra("Name", loginbean.getResult().getNickName());
                intent.putExtra("headIcon", loginbean.getResult().getHeadPic());
                startActivity(intent);
                break;
            case R.id.mycircle:
                Intent intent1 = new Intent(getActivity(), MyGroupActivity.class);
                startActivity(intent1);
                break;
            case R.id.footprint:
                Intent intent3=new Intent(getActivity(),MainFootActivity.class);
                startActivity(intent3);
                break;
            case R.id.Wallet:
                Intent intent4=new Intent(getActivity(),MineMoneyActivity.class);
                startActivity(intent4);
                break;
            case R.id.shippingaddress:
                Intent intent5 = new Intent(getActivity(), AddAddressActivity.class);
                editor.putString("nickName", loginbean.getResult().getNickName());
                editor.putString("phone", loginbean.getResult().getPhone());

                editor.commit();
                startActivity(intent5);
                break;
            case R.id.mine_icon:

                break;
            default:
                break;
        }
    }

    @Override
    public void getDataSuccess(Object data) {

    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(getActivity(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("main")) {
            loginbean = (Loginbean) evBean.getClazz();
            Uri uri = Uri.parse(loginbean.getResult().getHeadPic());
            mineIcon.setImageURI(uri);
            //Glide.with(getActivity()).load(uri).into(mineIcon);
            nickname.setText(loginbean.getResult().getNickName());
        }
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }


    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}