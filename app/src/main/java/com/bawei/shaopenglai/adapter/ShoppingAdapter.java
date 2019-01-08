package com.bawei.shaopenglai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.ShoppingBean;
import com.bawei.shaopenglai.custom.CustomJiaJian;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.MyViewHolder> {

    private List<ShoppingBean.ResuleBean> mData;
    private Context mContext;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.recycle_cart_item,
                viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
        String[] split = mData.get(i).getPic().split("\\|");
        Glide.with(mContext).load(split[0]).into(myViewHolder.recycle_icon);
        myViewHolder.recycle_title.setText(mData.get(i).getCommodityName());
        myViewHolder.recycle_price.setText("￥" +mData.get(i).getPrice()+"");
        myViewHolder.item_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mData.get(i).setItem_check(myViewHolder.item_checkbox.isChecked());
                //更新适配器
                notifyDataSetChanged();
                //调用求和的方法
                sum(mData);

            }

        });

    }

    private void sum(List<ShoppingBean.ResuleBean> mData) {
        //初始的总价为0
        int totalNum = 0;
        float totalMoney = 0.0f;
        boolean allCheck = true;
        for (int i=0;i<mData.size();i++){
            //把 已经选中的 条目 计算价格
            if (mData.get(i).isItem_check()){
                totalNum += mData.get(i).getCount();
                totalMoney += mData.get(i).getCount() * mData.get(i).getPrice();
            }else{
                //如果有个未选中,就标记为false
                allCheck = false;
            }
        }
        updateListener.setTotal(totalMoney+"",totalNum+"",allCheck);
    }

    //view层调用这个方法, 点击quanxuan按钮的操作
    public void quanXuan(boolean checked) {
        for (int i=0;i<mData.size();i++){
            mData.get(i).setItem_check(checked);
        }
        notifyDataSetChanged();
        sum(mData);
    }
    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private CheckBox item_checkbox;
        private ImageView recycle_icon;
        private TextView recycle_title;
        private TextView recycle_price;
        private CustomJiaJian customJiaJian;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_checkbox=itemView.findViewById(R.id.item_checkbox);
            recycle_icon=itemView.findViewById(R.id.recycle_icon);
            recycle_title=itemView.findViewById(R.id.recycle_title);
            recycle_price=itemView.findViewById(R.id.recycle_price);
            customJiaJian=itemView.findViewById(R.id.customjiajian);
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
}
