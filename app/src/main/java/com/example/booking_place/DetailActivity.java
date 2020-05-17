package com.example.booking_place;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.booking_place.model.Place;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class DetailActivity extends Activity {

    FirebaseConnector firebaseConnector;
    TextView textViewPrice, textViewLocationName, textViewTitleName, textViewGuestNum, textViewBedNum,textViewBathNum, textViewType, textViewDetail, textViewPlaceDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

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

        //conect to Firebase
        firebaseConnector = new FirebaseConnector(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance());


        //Method record Detail
        firebaseConnector.getPlaceDetailById("aNNKw5HAwmCz0SnioKWG").get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Map<String, Object> objectResponse = documentSnapshot.getData();
                textViewTitleName.setText(objectResponse.get("name").toString());
                textViewPrice.setText("$" + objectResponse.get("price").toString() + " per Night");
                textViewLocationName.setText(objectResponse.get("address").toString());
                textViewGuestNum.setText(objectResponse.get("guest").toString() + "  Guest");
                textViewBedNum.setText(objectResponse.get("bedroom").toString() + "  Bed");
                textViewBathNum.setText(objectResponse.get("bathroom").toString() + "  Bathroom");
                textViewType.setText(objectResponse.get("type").toString());
                textViewDetail.setText(objectResponse.get("details").toString());
                textViewPlaceDescription.setText(objectResponse.get("description").toString());
            }
        });

    }
}
