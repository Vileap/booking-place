package com.example.booking_place;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.text.Layout;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<MyHolder> {

    private Activity activity;
    private ArrayList<Model> models;
    private AdapterView.OnItemClickListener onItemClickListener;
    private Context applicationContext;


    public Adapter(Activity c, ArrayList<Model> models, Context appContext) {
        this.activity = c;
        this.models = models;
        this.applicationContext = appContext;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_customer, null);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        holder.mTitle.setText(models.get(position).getTitle());
        holder.mDesc.setText(models.get(position).getDescription());
        holder.mPrice.setText(models.get(position).getPrice());
        String paths = models.get(position).getImage();
        new DownloadTask(holder.mImageView).execute(stringToURL(paths));

        holder.mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("LOG65", holder.getAdapterPosition()+"");
                Intent intent = new Intent(applicationContext, DetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("PLACEID", models.get(holder.getAdapterPosition()).getPlaceId());
                applicationContext.startActivity(intent);
            }
        });

    }

    private URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private class DownloadTask extends AsyncTask<URL, Void, Bitmap> {
        private ImageView imageView;

        public DownloadTask(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                return BitmapFactory.decodeStream(bufferedInputStream);
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Disconnect the http url connection
                connection.disconnect();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            if (result != null) {
                imageView.setImageBitmap(result);
            }
        }
    }

    @Override
    public int getItemCount() {
        return models.size();
    }




}
