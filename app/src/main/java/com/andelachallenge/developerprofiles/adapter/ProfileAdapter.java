package com.andelachallenge.developerprofiles.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.andelachallenge.developerprofiles.R;
import com.andelachallenge.developerprofiles.activity.MainActivity;
import com.andelachallenge.developerprofiles.model.Items;
import com.andelachallenge.developerprofiles.service.ImageLoadTask;

import java.util.ArrayList;

public class ProfileAdapter extends ArrayAdapter<Items> {
    MainActivity main;

    public ProfileAdapter(Context context, ArrayList<Items> items) {
        super(context, 0, items);
    }

    public static class ViewHolderItem {
        TextView username;
        ImageView profileImage;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItem holder = new ViewHolderItem();

        if (convertView == null){

            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.contents, parent, false);

            holder.username = (TextView) convertView.findViewById(R.id.login);
            holder.profileImage = (ImageView) convertView.findViewById(R.id.profile_photo);

            convertView.setTag(holder);
        }else{
            holder = (ViewHolderItem) convertView.getTag();
        }

        ImageLoadTask loadImage = new ImageLoadTask(getContext());
        Items  currentItem = getItem(position);

        holder.username.setText(currentItem.login);
        try {
            holder.profileImage.setImageBitmap(loadImage.execute(currentItem.avatarUrl).get());
        }catch(Exception e){
            e.printStackTrace();
        }
        return convertView;
    }



}
