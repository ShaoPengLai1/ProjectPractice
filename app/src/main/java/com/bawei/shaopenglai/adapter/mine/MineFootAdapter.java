package com.bawei.shaopenglai.adapter.mine;

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
import com.bawei.shaopenglai.adapter.home.TopHomeAdapter;
import com.bawei.shaopenglai.bean.mine.addr.MineFootBean;
import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MineFootAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<MineFootBean.ResultBean> mList;
    private Context mContext;

    public MineFootAdapter(Context mContext) {
        this.mContext = mContext;
        mList=new ArrayList<>();
    }

    public void setList(List<MineFootBean.ResultBean> datas) {
        mList.clear();
        if (datas!=null){
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }
    public void addList(List<MineFootBean.ResultBean> datas) {
        if (datas!=null){
            mList.addAll(datas);
        }
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public XRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_myfoot,
                viewGroup,false);
        return new MineFootViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull XRecyclerView.ViewHolder viewHolder, final int i) {
        MineFootViewHolder footViewHolder= (MineFootViewHolder) viewHolder;
        Uri uri=Uri.parse(mList.get(i).getMasterPic());
        footViewHolder.imageView.setImageURI(uri);
        //Glide.with(mContext).load(mList.get(i).getMasterPic()).into(footViewHolder.imageView);
        footViewHolder.name.setText(mList.get(i).getCommodityName());
        footViewHolder.price.setText("￥"+mList.get(i).getPrice()+"");
        footViewHolder.llcs.setText("已浏览"+mList.get(i).getBrowseNum()+"次");
        footViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.setonclicklisener(i);
                }
            }
        });
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(mList.get(i).getBrowseTime()));
        footViewHolder.llsj.setText(date);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static class MineFootViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView imageView;
        TextView name;
        TextView price;
        TextView llcs;
        TextView llsj;
        public MineFootViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageView);
            name=itemView.findViewById(R.id.name);
            price=itemView.findViewById(R.id.price);
            llcs=itemView.findViewById(R.id.llcs);
            llsj=itemView.findViewById(R.id.llsj);
        }
    }
    private MineFootAdapter.Cicklistener listener;

    public void result(MineFootAdapter.Cicklistener listener) {
        this.listener = listener;
    }
    public interface Cicklistener {
        void setonclicklisener(int index);
    }
}
