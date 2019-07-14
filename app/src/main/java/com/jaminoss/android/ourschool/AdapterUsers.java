package com.jaminoss.android.ourschool;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.firebase.storage.FirebaseStorage;
import com.jaminoss.android.ourschool.model.User;
import com.jaminoss.android.ourschool.views.ImageHelper;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Talha on 10/14/2017.
 */

public class AdapterUsers extends BaseAdapter{
    ArrayList<User> users;
    Context context;
    private static LayoutInflater inflater=null;
    public AdapterUsers(Context c, ArrayList<User> anns) {
        // TODO Auto-generated constructor stub
        context=c;
        users = anns;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return users.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder {
        TextView name_tv;
        ImageView dp;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        final Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.user_item, null);
        holder.name_tv = rowView.findViewById(R.id.user_item_username);
        holder.name_tv.setText(users.get(position).username);
        holder.dp = rowView.findViewById(R.id.user_item_dp);

        String child = "Photos/"+users.get(position).username+".jpeg";

        final File localFile;
        try {
            localFile = File.createTempFile("album2", "png");
            FirebaseStorage.getInstance().getReference().child(child)
                    .getFile(localFile).addOnSuccessListener(taskSnapshot -> {
                        Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                        bitmap = ImageHelper.getRoundedCornerBitmap(bitmap,200);
                        holder.dp.setImageBitmap(bitmap);
                Picasso.with(rowView.getContext())
                        .load(localFile)
                        .networkPolicy(NetworkPolicy.OFFLINE)
                        .into(holder.dp, new Callback() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onError() {
                                Picasso.with(rowView.getContext())
                                        .load(localFile)
                                        .into(holder.dp);
                            }
                        });
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rowView;
    }

}

