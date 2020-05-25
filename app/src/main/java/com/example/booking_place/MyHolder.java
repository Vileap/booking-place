package com.example.booking_place;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder {

    ImageView mImageView;
    TextView mTitle, mDesc, mPrice;

    public MyHolder(@NonNull View itemView) {
        super(itemView);


        this.mImageView = itemView.findViewById(R.id.image);
        this.mTitle = itemView.findViewById(R.id.titleView);
        this.mDesc = itemView.findViewById(R.id.locationText);
        this.mPrice = itemView.findViewById(R.id.locationPrice);
    }


}
