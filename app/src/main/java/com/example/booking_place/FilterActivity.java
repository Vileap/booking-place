package com.example.booking_place;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatRadioButton;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.addisonelliott.segmentedbutton.SegmentedButtonGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterActivity extends Activity implements AdapterView.OnItemSelectedListener {

    Button bRbuttonAny, bRbutton1, bRbutton2, bRbutton3, bRbutton4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        ImageButton backButton = findViewById(R.id.backMainFilter);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Spinner spinner = findViewById(R.id.filterSpinner);
        String[] prices = new String[]{
                "Any",
                "$10 - $19",
                "$20 - $29",
                "$30 - $40",
        };

        final List<String> priceList = new ArrayList<>(Arrays.asList(prices));
        final ArrayAdapter<String> Spinneradapter = new ArrayAdapter<String>(this, R.layout.simple_spinner_item, priceList){
            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position == 0)
                {
                    tv.setTextColor(Color.GRAY);
                }
                else
                {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }

            @Override
            public boolean isEnabled(int position) {
                if(position == 0) {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        };

        Spinneradapter.setDropDownViewResource(R.layout.simple_spinner_item);
        spinner.setAdapter(Spinneradapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String selectedText = (String) parent.getItemAtPosition(position);
        if(position == 0){
            Toast.makeText(getApplicationContext(), "Selected :" + selectedText, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }




}
