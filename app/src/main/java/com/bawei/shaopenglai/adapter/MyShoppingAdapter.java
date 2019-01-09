package com.bawei.shaopenglai.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.ShowShoppingBean;
import com.bawei.shaopenglai.custom.CustomJiaJian;
import com.bawei.shaopenglai.custom.MyCustomView;
import com.bumptech.glide.Glide;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyShoppingAdapter extends XRecyclerView.Adapter<XRecyclerView.ViewHolder> {
    private List<ShowShoppingBean.ResuleBean> mlist;
    private Context context;

    public MyShoppingAdapter(Context context) {
        this.context = context;
        mlist=new ArrayList<>();
    }

    public void setList(List<ShowShoppingBean.ResuleBean> list) {
        mlist.clear();
        mlist.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyShoppingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_shop,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final XRecyclerView.ViewHolder viewHolder, final int i) {
        final ViewHolder holder= (ViewHolder) viewHolder;
        holder.tv_shop_name.setText(mlist.get(i).getCommodityName());
        holder.tv_shop_price.setText("￥"+mlist.get(i).getPrice()+"");
        Uri parse = Uri.parse(mlist.get(i).getPic());
        Glide.with(context).load(mlist.get(i).getPic()).into(holder.sd_shop_sim);
        holder.che_box.setChecked(mlist.get(i).isItem_check());
        holder.che_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mlist.get(i).setItem_check(holder.che_box.isChecked());
                if (shopCarListener!=null){
                    shopCarListener.callBack(mlist);
                }
            }
        });
        holder.cus_view.setCustomListener(new CustomJiaJian.CustomListener() {
            @Override
            public void jiajian(int count) {
                //改变数据源中的数量
                mlist.get(i).setCount(count);
                notifyDataSetChanged();
                sum(mlist);
            }

            @Override
            public void shuRuZhi(int count) {

            }
        });
        //商品的item_checkbox点击事件,控制商铺的shop_checkbox
        holder.che_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //先改变数据源中的item_checkbox
                mlist.get(i).setItem_check(holder.che_box.isChecked());
                //更新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(mlist);
            }
        });
    }
    //view层调用这个方法, 点击quanxuan按钮的操作
    public void quanXuan(boolean checked) {
        for (int i=0;i<mlist.size();i++){
            mlist.get(i).setItem_check(checked);
        }
        notifyDataSetChanged();
        sum(mlist);
    }

    private void sum(List<ShowShoppingBean.ResuleBean> mlist) {
        //初始的总价为0
        int totalNum = 0;
        float totalMoney = 0.0f;
        boolean allCheck = true;
        for (int i=0;i<mlist.size();i++){
            //把 已经选中的 条目 计算价格
            if (mlist.get(i).isItem_check()){
                totalNum += mlist.get(i).getCount();
                totalMoney += mlist.get(i).getCount() * mlist.get(i).getPrice();
            }else{
                //如果有个未选中,就标记为false
                allCheck = false;
            }
        }
        updateListener.setTotal(totalMoney+"",totalNum+"",allCheck);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.cus_view)
        CustomJiaJian cus_view;
        @BindView(R.id.sd_shop_sim)
        ImageView sd_shop_sim;
        @BindView(R.id.tv_shop_name)
        TextView tv_shop_name;
        @BindView(R.id.tv_shop_price)
        TextView tv_shop_price;
        @BindView(R.id.che_box)
        CheckBox che_box;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
    UpdateListener updateListener;
    public void setUpdateListener(UpdateListener updateListener){
        this.updateListener = updateListener;
    }
    //接口
    public interface UpdateListener{
        public void setTotal(String total, String num, boolean allCheck);
    }
    /**
     * 定义条目点击接口
     */
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    /**
     * 定义条目长按事件的接口
     */
    public interface OnLongItemClickListener {
        void onItemLongClick(View itemView, int position);
    }
    private OnItemClickListener clickListener;
    private OnLongItemClickListener longItemClickListener;

    /**
     * 点击事件回调
     * @param clickListener
     */
    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    /**
     * 长按事件回调
     * @param longItemClickListener
     */
    public void setOnLongItemClickListener(OnLongItemClickListener longItemClickListener) {
        this.longItemClickListener = longItemClickListener;
    }
    private ShopCarListener shopCarListener;
    public void setShopCarListener(ShopCarListener shopCarListener) {
        this.shopCarListener = shopCarListener;
    }
    public interface ShopCarListener {
        void callBack(List<ShowShoppingBean.ResuleBean> mlist);
    }
}
