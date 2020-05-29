package com.example.booking_place;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    ImageView mImageView;
    TextView mTitle, mDesc, mPrice;
    private  int id;
    private String name, image;
     View mView;

    public MyHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;

        this.mImageView = itemView.findViewById(R.id.image);
        this.mTitle = itemView.findViewById(R.id.titleView);
        this.mDesc = itemView.findViewById(R.id.locationText);
        this.mPrice = itemView.findViewById(R.id.locationPrice);
    }

    @Override
    public void onClick(View v) {
        Log.i("LOG44", getAdapterPosition()+ "");
    }

}
