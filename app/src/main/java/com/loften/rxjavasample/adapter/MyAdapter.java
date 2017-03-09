package com.loften.rxjavasample.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loften.rxjavasample.R;
import com.loften.rxjavasample.model.Datas;

import java.util.List;

/**
 * Created by asus on 2016/9/22.
 */

public class MyAdapter extends RecyclerView.Adapter {
    List<Datas> datas;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder)holder;
        Datas data = datas.get(position);
        Glide.with(holder.itemView.getContext()).load(data.image_url).into(viewHolder.imageIv);
        viewHolder.descriptionTv.setText(data.description);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    public void setDatas(List<Datas> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageIv;
        private TextView descriptionTv;

        public ViewHolder(View itemView) {
            super(itemView);
            imageIv = (ImageView)itemView.findViewById(R.id.imageIv);
            descriptionTv = (TextView)itemView.findViewById(R.id.descriptionTv);
        }
    }

}
