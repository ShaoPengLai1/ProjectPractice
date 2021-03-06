package com.bawei.shaopenglai.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bawei.shaopenglai.api.Apis;
import com.bawei.shaopenglai.bean.shopping.AddShopping;
import com.bawei.shaopenglai.bean.shopping.GoodsBean;
import com.bawei.shaopenglai.bean.shopping.ShoppingBean;
import com.bawei.shaopenglai.bean.shopping.ShoppingCarBean;
import com.bawei.shaopenglai.custom.CustomJiaJian;
import com.bawei.shaopenglai.custom.EventBean;
import com.bawei.shaopenglai.custom.MyDialog;
import com.bawei.shaopenglai.presenter.IPresenterImpl;
import com.bawei.shaopenglai.view.IView;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;
import com.bawei.shaopenglai.R;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GoodsActivity extends AppCompatActivity implements IView {

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
    private IPresenterImpl iPresenter;
    private int commodityId;
    private int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        ButterKnife.bind(this);
        iPresenter = new IPresenterImpl(this);

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
            commodityId = goodsBean.getResult().getCommodityId();
            load();
        }
    }
    @SuppressLint("JavascriptInterface")
    private void load() {
        String details = goodsBean.getResult().getDetails();
        String picture = goodsBean.getResult().getPicture();
        String[] split = picture.split(",");
        List<String> list = Arrays.asList(split);
        WebSettings settings = webview.getSettings();
        settings.setJavaScriptEnabled(true);//支持JS
        String js = "<script type=\"text/javascript\">"+
                "var imgs = document.getElementsByTagName('img');" + // 找到img标签
                "for(var i = 0; i<imgs.length; i++){" +  // 逐个改变
                "imgs[i].style.width = '100%';" +  // 宽度改为100%
                "imgs[i].style.height = 'auto';" +
                "}" +
                "</script>";
        webview.loadDataWithBaseURL(null, details+js, "text/html", "utf-8", null);
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
                View v = getLayoutInflater().inflate(R.layout.dialog_goods, null);

                myDialog = new MyDialog(GoodsActivity.this, 0, 0, v, R.style.DialogTheme);
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
                        String string = count.getText().toString();
                        num = Integer.parseInt(string);
                        selShopCar();
                        myDialog.dismiss();
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

    private void selShopCar() {

        iPresenter.startRequestGet(Apis.URL_FIND_SHOPPING_CART_GET, null, ShoppingBean.class);
    }

    @Override
    public void getDataSuccess(Object data) {
        if (data instanceof ShoppingBean) {
            ShoppingBean shoppingBean = (ShoppingBean) data;
            if (shoppingBean.getMessage().equals("查询成功")) {
                List<ShoppingCarBean> list = new ArrayList<>();
                List<ShoppingBean.ResultBean> result = shoppingBean.getResult();
                for (ShoppingBean.ResultBean results : result) {
                    list.add(new ShoppingCarBean(results.getCommodityId(), results.getCount()));
                }
                addShopCar(list);
            }
        }
        if (data instanceof AddShopping) {
            AddShopping addShopping = (AddShopping) data;
            Toast.makeText(GoodsActivity.this, addShopping.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void addShopCar(List<ShoppingCarBean> list) {
//        String string="[";
        if(list.size()==0){
            list.add(new ShoppingCarBean(Integer.valueOf(commodityId),num));
        }else {
            for (int i=0;i<list.size();i++){
                if(Integer.valueOf(commodityId)==list.get(i).getCommodityId()){
                    int count = list.get(i).getCount();
                    count+=num;
                    list.get(i).setCount(count);
                    break;
                }else if(i==list.size()-1){
                    list.add(new ShoppingCarBean(Integer.valueOf(commodityId),num));
                    break;
                }
            }
        }


        String s = new Gson().toJson(list);
        Map<String, String> map = new HashMap<>();
        map.put("data", s);
        iPresenter.startRequestPut(Apis.URL_SYNC_SHOPPING_CART_PUT, map, AddShopping.class);
    }

    @Override
    public void getDataFail(String error) {
        Toast.makeText(GoodsActivity.this, error, Toast.LENGTH_LONG).show();
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
