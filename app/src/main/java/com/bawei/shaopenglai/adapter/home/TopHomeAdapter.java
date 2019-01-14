package com.bawei.shaopenglai.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.home.TopLasBean;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Peng
 * @time 2019/1/3 11:03
 */

public class TopHomeAdapter extends RecyclerView.Adapter<TopHomeAdapter.TopRecyclerView> {

    private List<TopLasBean.ResultBean> mList;
    private Context mContext;

    public TopHomeAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();

    }

    public void setData(List<TopLasBean.ResultBean> datas) {
        mList.clear();
        if (datas!=null){
            mList.addAll(datas);

        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopRecyclerView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.popup_item_top,viewGroup,false);
        return new TopRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopRecyclerView viewHolder, final int i) {
        TopRecyclerView topRecyclerView= viewHolder;
        topRecyclerView.recycle_top_item.setText(mList.get(i).getName());
        topRecyclerView.itemView.setTag(topRecyclerView.recycle_top_item);

        topRecyclerView.itemView.setOnClickListener(new View.OnClickListener() {
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
    public static class TopRecyclerView extends RecyclerView.ViewHolder{
        public final TextView recycle_top_item;
        public TopRecyclerView(@NonNull View itemView) {
            super(itemView);
            recycle_top_item=itemView.findViewById(R.id.recycle_top_item);
        }
    }
    private Cicklistener listener;

    public void result(Cicklistener listener) {
        this.listener = listener;
    }
    public interface Cicklistener {
        void setonclicklisener(int index);
    }

}
