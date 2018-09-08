package com.example.ananthgarg.memorableplaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.ArrayList;
import java.util.FormatFlagsConversionMismatchException;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> places;
    static   ArrayList<LatLng> locations;

    static ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = findViewById(R.id.listView);

        SharedPreferences sharedPreferences1 = this.getSharedPreferences("com.example.ananthgarg.memorableplaces", Context.MODE_PRIVATE);

        places = new ArrayList<String>();
        locations = new ArrayList<LatLng>();
       ArrayList<String> Latitude = new ArrayList<String>();
        ArrayList<String> Longitude = new ArrayList<String>();


        places.clear();
        Longitude.clear();
        Latitude.clear();
        locations.clear();

            try {

                places = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences1.getString("Places", ObjectSerializer.serialize(new ArrayList<String>())));
                Latitude = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences1.getString("Latitude", ObjectSerializer.serialize(new ArrayList<String>())));
                Longitude = (ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences1.getString("Longitude", ObjectSerializer.serialize(new ArrayList<String>())));

            } catch (IOException e) {
                e.printStackTrace();
            }
            if(places.size()>0 && Latitude.size()>0 && Longitude.size()>0)
            {
                if(places.size() == Latitude.size() && places.size() == Longitude.size())
                {
                    for(int i=0;i < Latitude.size();i++)
                    {
                        locations.add(new LatLng(Double.parseDouble(Latitude.get(i)),Double.parseDouble(Longitude.get(i))));
                    }
                }
            }else {
                locations.add(new LatLng(0, 0));
                places.add("Add a new Place");
            }
        arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,places);
 listView.setAdapter(arrayAdapter);
 listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
     @Override
     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

         Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
         intent.putExtra("placeNumber",position);
         startActivity(intent);
     }
 });
    }
}
