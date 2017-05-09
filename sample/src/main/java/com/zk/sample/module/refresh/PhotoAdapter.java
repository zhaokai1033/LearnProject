package com.zk.sample.module.refresh;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zk.baselibrary.util.ToastUtil;
import com.zk.sample.R;

import java.util.ArrayList;
import java.util.List;


public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoHolder> {

    private List<Photo> dataList = new ArrayList<>();

    @Override
    public PhotoAdapter.PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_photo, parent, false);
        return PhotoHolder.getHolder(v);
    }

    @Override
    public void onBindViewHolder(PhotoAdapter.PhotoHolder holder, int position) {
        holder.bindData(dataList.get(position));
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void setDataList(List<Photo> datas) {
        dataList.clear();
        if (null != datas) {
            dataList.addAll(datas);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加数据到尾部
     */
    public void addItems(List<Photo> datas) {
        if (null == datas) return;
        dataList.addAll(datas);
        notifyDataSetChanged();
    }

    public static class PhotoHolder extends RecyclerView.ViewHolder {

        private final ImageView iv;
        private final TextView tv;

        public PhotoHolder(View view) {
            super(view);
            iv = ((ImageView) view.findViewById(R.id.iv_pic));
            tv = ((TextView) view.findViewById(R.id.tv_info));
        }

        public static PhotoHolder getHolder(View view) {
            PhotoHolder holder = ((PhotoHolder) view.getTag());
            if (holder == null) {
                holder = new PhotoHolder(view);
                view.setTag(holder);
            }
            return holder;
        }

        public void bindData(Photo photo) {
            iv.setImageResource(photo.imgSrc);
            tv.setText(photo.name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastUtil.showToast(itemView.getContext(), "item clicked!");
                }
            });
        }
    }
}