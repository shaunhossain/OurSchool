package com.jaminoss.android.ourschool.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jaminoss.android.ourschool.R;

/**
 * Created by kodenerd on 2/15/18.
 */

public class ResultViewHolder extends RecyclerView.ViewHolder {

    View view;

    public ResultViewHolder(View itemView) {
        super(itemView);
        view = itemView;
    }
    public void setEng(String eng) {
        TextView engTV = (TextView) view.findViewById(R.id.name);
    }
}
