package com.bawei.shaopenglai.fragment.lb;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.bawei.shaopenglai.R;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SecondActivity extends AppCompatActivity {
    WebView mWebView;
    private static ArrayList<String> result;
    private static int myRand;
    private static ArrayList<String> items;
    private static Random rand;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.webview)
    WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        ButterKnife.bind(this);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        items = new ArrayList<>();
        result = new ArrayList<>();
        items.add("http://detail.1688.com/offer/562001227142.html?spm=a262fl.8287134.2826707.1.65263420I5mjkZ");
        items.add("http://detail.1688.com/offer/529055896688.html?spm=a262fl.8287134.2826707.7.65263420neaDTp");
        items.add("http://detail.1688.com/offer/44839419077.html?spm=a262fl.8287134.2826707.1.65263420w5GlLI");
        items.add("http://detail.1688.com/offer/534552216247.html?spm=a262fl.8287134.2826707.5.65263420w5GlLI");
        items.add("http://detail.1688.com/offer/558623754251.html?spm=a262fl.8287134.2826707.11.65263420w5GlLI");
        items.add("http://detail.1688.com/offer/1102717930.html?spm=a262fl.8287134.2826707.1.65263420w5GlLI");
        items.add("http://item.jd.com/100000177756.html");
        items.add("http://item.jd.com/7512626.html");
        items.add("http://item.jd.com/8758906.html");
        items.add("http://jiadian.jd.com/");
        items.add("http://pages.tmall.com/wow/a/act/21120/wupr?wh_pid=industry-155713&ali_trackid=2:mm_26632258_3504122_48284354:1547531103_128_723851279&clk1=0cd250280a40d01c68bd71e56675a029&upsid=0cd250280a40d01c68bd71e56675a029");
        items.add("http://pages.tmall.com/wow/a/act/21120/wupr?wh_pid=industry-155879&ali_trackid=2:mm_26632258_3504122_48284354:1547531136_114_1469625661&clk1=4a3e9336c6e70cd5c88e0480ad5c9b68&upsid=4a3e9336c6e70cd5c88e0480ad5c9b68");
        items.add("http://pages.tmall.com/wow/a/act/21120/wupr?wh_pid=industry-155654&ali_trackid=2:mm_26632258_3504122_48284354:1547531152_142_482658567&clk1=4ec547f766d98564eeb3ea8f35e6d9ef&upsid=4ec547f766d98564eeb3ea8f35e6d9ef");
        items.add("http://pages.tmall.com/wow/a/act/21120/wupr?wh_pid=industry-155726&ali_trackid=2:mm_26632258_3504122_48284354:1547531173_141_1462236110&clk1=f7bdb2f96756f5c1cd723a64cf9c48fb&upsid=f7bdb2f96756f5c1cd723a64cf9c48fb");
        items.add("http://pages.tmall.com/wow/a/act/21120/wupr?wh_pid=industry-155785&ali_trackid=2:mm_26632258_3504122_48284354:1547531197_186_376616905&clk1=de8fd5588b61c81e94e8b6185eebcfb3&upsid=de8fd5588b61c81e94e8b6185eebcfb3");
        // 初始化随机数
        rand = new Random();

        // 取得集合的长度，for循环使用
        int size = items.size();


        mWebView = (WebView) findViewById(R.id.webview);

        WebSettings webSettings = mWebView.getSettings();

        // 设置与Js交互的权限
        webSettings.setJavaScriptEnabled(true);
        // 设置允许JS弹窗
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);

        String url = getIntent().getStringExtra("url");
        //如果不设置WebViewClient，请求会跳转系统浏览器
        mWebView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //该方法在Build.VERSION_CODES.LOLLIPOP以前有效，从Build.VERSION_CODES.LOLLIPOP起，
                // 建议使用shouldOverrideUrlLoading(WebView, WebResourceRequest)} instead
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），
                // 均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下
                return false;
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //返回false，意味着请求过程里，不管有多少次的跳转请求（即新的请求地址），
                // 均交给webView自己处理，这也是此方法的默认处理
                //返回true，说明你自己想根据url，做新的跳转，比如在判断url符合条件的情况下
                return false;
            }
        });
        // 遍历整个items数组
        for (int i = 0; i < size; i++) {
            // 任意取一个0~size的整数，注意此处的items.size()是变化的，
            // 所以不能用前面的size会发生数组越界的异常
            myRand = rand.nextInt(items.size());
            //将取出的这个元素放到存放结果的集合中
            // 格式规定为:file:///android_asset/文件名.html
            mWebView.loadUrl(items.get(myRand));
            //result.add(items.get(myRand));
            //从原始集合中删除该元素防止重复。注意，items数组大小发生了改变
            items.remove(myRand);
        }
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        mWebView.setWebChromeClient(new WebChromeClient());
    }
}
