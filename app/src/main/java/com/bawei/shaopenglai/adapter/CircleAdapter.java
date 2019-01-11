package com.bawei.shaopenglai.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bawei.shaopenglai.R;
import com.bawei.shaopenglai.bean.CircleBean;
import com.bumptech.glide.Glide;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

public class CircleAdapter extends RecyclerView.Adapter<CircleAdapter.ViewHolder> {
    private Context context;
    private List<CircleBean.ResultBean> list;

    public CircleAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
    }

    public void setList(List<CircleBean.ResultBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.item_circle, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Glide.with(context).load(list.get(i).getHeadPic()).into(viewHolder.circleSimpleHead);
        viewHolder.circleSimpleTitle.setText(list.get(i).getNickName());
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
                new java.util.Date(list.get(i).getCreateTime()));
        viewHolder.circleSimpleTime.setText(date);
        viewHolder.circleSimpleZhu.setText(list.get(i).getContent());
        viewHolder.circleTextGive.setText(list.get(i).getWhetherGreat()+"");
        String image = list.get(i).getImage();
        if (image!=null){
            String[] split = image.split("\\,");
            final List<String> lists = Arrays.asList(split);
            Glide.with(context).load(lists.get(0)).into(viewHolder.circleSimplePic);
        }
        if (this.list.get(i).isGreat()){
            viewHolder.circleSimpleGive.setBackgroundResource(R.mipmap.common_btn_prise_s);
        }else {
            viewHolder.circleSimpleGive.setBackgroundResource(R.mipmap.common_btn_prise_n);
        }
        viewHolder.circleSimpleGive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (greatClick!=null){
                    greatClick.click(list.get(i).getId());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView circleSimpleHead;
        TextView circleSimpleTitle;
        TextView circleSimpleTime;
        TextView circleSimpleZhu;
        ImageView circleSimplePic;
        ImageView circleSimpleGive;
        TextView circleTextGive;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleSimpleHead=itemView.findViewById(R.id.circle_simple_head);
            circleSimpleTitle=itemView.findViewById(R.id.circle_simple_title);
            circleSimpleTime=itemView.findViewById(R.id.circle_simple_time);
            circleSimpleZhu=itemView.findViewById(R.id.circle_simple_zhu);
            circleSimplePic=itemView.findViewById(R.id.circle_simple_pic);
            circleSimpleGive=itemView.findViewById(R.id.circle_simple_give);
            circleTextGive=itemView.findViewById(R.id.circle_text_give);
        }
    }

    private GreatClick greatClick;

    public void setGreatClick(GreatClick greatClick) {
        this.greatClick = greatClick;
    }

    public interface GreatClick{
        void click(int circleId);
    }

}
