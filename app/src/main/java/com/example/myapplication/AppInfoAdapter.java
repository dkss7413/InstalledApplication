package com.example.myapplication;

import android.content.pm.ApplicationInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AppInfoAdapter extends BaseAdapter {
    private List<ApplicationInfo> mInfos;

    AppInfoAdapter(List<ApplicationInfo> mInfos){
        this.mInfos = mInfos;
    }

    @Override
    public int getCount() {
        return mInfos.size();
    }

    @Override
    public Object getItem(int i) {
        return mInfos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, final ViewGroup viewGroup) {
        ViewHolder holder;

        if(view == null) {
            holder = new ViewHolder();

            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_app, viewGroup, false);

            holder.imageView = view.findViewById(R.id.imageView);
            holder.textView = view.findViewById(R.id.textView);

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final ApplicationInfo info = mInfos.get(i);

        Drawable icon = info.loadIcon(viewGroup.getContext().getPackageManager());
        holder.imageView.setImageDrawable(icon);

        String text = (String) info.loadLabel(viewGroup.getContext().getPackageManager());
        holder.textView.setText(text);

        return view;
    }

    private static class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}