package com.bawei.shaopenglai.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.home.BottomTasBean;

import java.util.ArrayList;
import java.util.List;

public class BottomHomeAdapter extends RecyclerView.Adapter<BottomHomeAdapter.BotmRecyclerView> {

    private List<BottomTasBean.ResultBean> mList;
    private Context mContext;

    public BottomHomeAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();
    }

    public void setData(List<BottomTasBean.ResultBean> datas) {
        mList.clear();
        if (datas!=null){
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BotmRecyclerView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.popup_item_bottom,viewGroup,false);
        return new BotmRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BotmRecyclerView viewHolder, final int i) {
        BotmRecyclerView botmRecyclerView=viewHolder;
        viewHolder.bottomTv1.setText(mList.get(i).getName());
        botmRecyclerView.itemView.setOnClickListener(new View.OnClickListener() {
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
        return mList.size();
    }
    public static class BotmRecyclerView extends RecyclerView.ViewHolder{
        public  TextView bottomTv1;
        public BotmRecyclerView(@NonNull View itemView) {
            super(itemView);
            bottomTv1=itemView.findViewById(R.id.bottomTv1);
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
