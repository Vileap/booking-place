package com.example.booking_place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.booking_place.data.Result;
import com.example.booking_place.model.Place;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class DetailActivity extends Activity {

    FirebaseConnector firebaseConnector;
    TextView textViewPrice, textViewLocationName, textViewTitleName, textViewGuestNum, textViewBedNum,textViewBathNum, textViewType, textViewDetail, textViewPlaceDescription;
    ImageView backgroundImage;
    CalendarView calendarView;
    Button confirmButton;

    public static long SelectedDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageButton imageButton = findViewById(R.id.backMainDetail);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        // View
        textViewPrice = findViewById(R.id.placePrice);
        textViewLocationName = findViewById(R.id.placeLocationName);
        textViewTitleName = findViewById(R.id.placeTitleName);
        textViewGuestNum = findViewById(R.id.placeGuestNum);
        textViewBedNum = findViewById(R.id.placeBedNum);
        textViewBathNum = findViewById(R.id.placeBathNum);
        textViewType = findViewById(R.id.placeType);
        textViewDetail =findViewById(R.id.placeDetails);
        textViewPlaceDescription = findViewById(R.id.placeDescription);
        backgroundImage = findViewById(R.id.backgroundDetail);

        textViewPlaceDescription.setMovementMethod(new ScrollingMovementMethod());


        //conect to Firebase
        firebaseConnector = new FirebaseConnector(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance());

        Intent intent = getIntent();
        Bundle bundle  = intent.getExtras();

        if (bundle != null) {
            String placeId = (String) bundle.get("PLACEID");

            firebaseConnector.getPlaceDetailById(placeId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Map<String, Object> objectResponse = documentSnapshot.getData();
                    //backgroundImage.setImageBitmap(objectResponse.get("image").toString());
                    textViewTitleName.setText(objectResponse.get("name").toString());
                    textViewPrice.setText("$" + objectResponse.get("price").toString() + " per Night");
                    textViewLocationName.setText(objectResponse.get("address").toString());
                    textViewGuestNum.setText(objectResponse.get("guest").toString() + "  Guest");
                    textViewBedNum.setText(objectResponse.get("bedroom").toString() + "  Bed");
                    textViewBathNum.setText(objectResponse.get("bathroom").toString() + "  Bathroom");
                    textViewType.setText(objectResponse.get("type").toString());
                    textViewDetail.setText(objectResponse.get("details").toString());
                    textViewPlaceDescription.setText(objectResponse.get("description").toString());

                    Model model = new Model();
                    model.setImage(objectResponse.get("image").toString());
                    Picasso.get().load(model.getImage()).into(backgroundImage);
                }
            });
        }


        calendarView = findViewById(R.id.calenderView);
        confirmButton = findViewById(R.id.CalenderConfirmButton);


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Toast.makeText(getApplicationContext(), dayOfMonth + "/" + month + "/" + year, Toast.LENGTH_LONG).show();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                String selectedDates = sdf.format(new Date(calendarView.getDate()));
                SelectedDate = calendarView.getDate();

            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), BookActivity.class);
                intent1.putExtra("mSelectedDate", SelectedDate);
                startActivity(intent1);
            }
        });

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

    private URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
