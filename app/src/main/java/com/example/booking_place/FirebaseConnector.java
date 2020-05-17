package com.example.booking_place;

import android.util.Log;

import com.example.booking_place.model.Place;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseConnector {

    private FirebaseAuth instantAuth;
    private FirebaseFirestore instantDB;

    FirebaseConnector() {

    }

    FirebaseConnector(FirebaseAuth mAuth, FirebaseFirestore instantDB) {
        this.instantDB = instantDB;
        this.instantAuth = mAuth;
    }


    public DocumentReference getPlaceDetailById(String placeId) {
        DocumentReference docRef = instantDB.collection("places").document(placeId);
        return docRef;
    }




}
