package com.bawei.shaopenglai.adapter.home;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.home.HomeBean;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

public class PinAdapter extends RecyclerView.Adapter<PinAdapter.ViewHolder> {

    private List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> list;
    private Context context;

    public PinAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setData(List<HomeBean.ResultBean.PzshBean.CommodityListBeanX> datas) {
        list.clear();
        if (datas!=null){
            list.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.item_pin,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Uri uri=Uri.parse(list.get(i).getMasterPic());
        viewHolder.imageView.setImageURI(uri);
        //Glide.with(context).load(list.get(i).getMasterPic()).into(viewHolder.imageView);
        viewHolder.name.setText(list.get(i).getCommodityName());
        viewHolder.price.setText("￥"+list.get(i).getPrice()+"");
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.setonclicklisener(i);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{
        private SimpleDraweeView imageView;
        private TextView name,price;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
        }
    }
    private TopHomeAdapter.Cicklistener listener;

    public void result(TopHomeAdapter.Cicklistener listener) {
        this.listener = listener;
    }
    public interface Cicklistener {
        void setonclicklisener(int index);
    }
}