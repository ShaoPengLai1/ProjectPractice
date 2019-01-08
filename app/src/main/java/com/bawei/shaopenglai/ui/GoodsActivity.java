package com.bawei.shaopenglai.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.AddShopping;
import com.bawei.shaopenglai.bean.GoodsBean;
import com.bawei.shaopenglai.custom.Constants;
import com.bawei.shaopenglai.custom.CustomJiaJian;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.custom.MyDialog;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;
import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsActivity extends AppCompatActivity{

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.price)
    TextView price;
    @BindView(R.id.saleNum)
    TextView saleNum;
    @BindView(R.id.commodityName)
    TextView commodityName;
    @BindView(R.id.weight)
    TextView weight;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.shopAdd)
    ImageView shopAdd;
    @BindView(R.id.shopBuy)
    ImageView shopBuy;
    private GoodsBean goodsBean;
    private MyDialog myDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    public void onEvent(EventBean evBean) {
        if (evBean.getName().equals("goods")) {
            goodsBean = (GoodsBean) evBean.getClazz();
            load();
        }
    }

    private void load() {
        String details = goodsBean.getResult().getDetails();
        String picture = goodsBean.getResult().getPicture();
        String[] split = picture.split(",");
        List<String> list = Arrays.asList(split);
        webview.loadDataWithBaseURL(null, details, "text/html", "utf-8", null);
        banner.setImageLoader(new GlideImageLoader());
        banner.setImages(list);
        banner.start();
        price.setText("￥" + goodsBean.getResult().getPrice() + "");
        commodityName.setText(goodsBean.getResult().getCommodityName());
        weight.setText(goodsBean.getResult().getWeight() + "kg");

    }

    @OnClick({R.id.shopAdd, R.id.shopBuy})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.shopAdd:
                //goodsBean.getResult().getCategoryId();
                View v=getLayoutInflater().inflate(R.layout.dialog_goods,null);

                myDialog = new MyDialog(GoodsActivity.this,0,0,v,R.style.DialogTheme);

                TextView recycle_price = v.findViewById(R.id.recycle_price);
                TextView recycle_title = v.findViewById(R.id.recycle_title);
                Button production=v.findViewById(R.id.production);
                Button cancel=v.findViewById(R.id.cancel);
                final CustomJiaJian customJiaJian = v.findViewById(R.id.customjiajian);
                final EditText count=customJiaJian.findViewById(R.id.count);
                String[] split = goodsBean.getResult().getPicture().split("\\|");
                recycle_title.setText(goodsBean.getResult().getCommodityName());
                recycle_price.setText("￥" + goodsBean.getResult().getPrice()+"");
                production.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBean myDialog = new EventBean("myDialog", goodsBean);
                        String s = count.getText().toString();
                        myDialog.setNum(Integer.parseInt(s));
                        EventBus.getDefault().postSticky(myDialog);

                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myDialog.dismiss();
                    }
                });
                myDialog.setCancelable(true);
                myDialog.show();
                break;
            case R.id.shopBuy:
                break;
        }
    }



    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);

    }


}
