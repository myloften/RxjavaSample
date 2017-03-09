package com.loften.rxjavasample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loften.rxjavasample.R;
import com.loften.rxjavasample.model.ZhuangbiImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by asus on 2017/3/6.
 */

public class Main2Adapter extends RecyclerView.Adapter<Main2Adapter.ViewHolder> {

    private List<ZhuangbiImage> datas = new ArrayList<>();

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new Main2Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        ZhuangbiImage data = datas.get(position);
        Glide.with(holder.itemView.getContext()).load(data.image_url).into(viewHolder.imageIv);
        viewHolder.descriptionTv.setText(data.description);
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public void setDatas(List<ZhuangbiImage> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imageIv;
        private TextView descriptionTv;

        public ViewHolder(View itemView) {
            super(itemView);
            imageIv = (ImageView)itemView.findViewById(R.id.imageIv);
            descriptionTv = (TextView)itemView.findViewById(R.id.descriptionTv);
        }
    }
}
