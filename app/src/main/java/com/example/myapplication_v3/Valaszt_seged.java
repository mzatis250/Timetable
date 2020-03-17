package com.example.myapplication_v3;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;



public class Valaszt_seged extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    DatabaseHelper _DatabaseHelper;
    private ListView _ListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valaszt_seged);
        _ListView = findViewById(R.id.listView);
        _DatabaseHelper = new DatabaseHelper(this);
        populateListView();
    }
    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Cursor data = _DatabaseHelper.getData();
        ArrayList<String> listData = new ArrayList<>();
        while(data.moveToNext()){
            listData.add(data.getString(1));
           listData.add(data.getString(2));
           listData.add(data.getString(3));
            listData.add(data.getString(4));
            if(!data.getString(5).equals("true")){
                listData.add("A hét");
            }
            else listData.add("B hét");
            if (!data.getString(7).equals("")) {
                listData.add(data.getString(7));
            }
            else {
                listData.add("Nincs tanár hozzáadva!");
            }
            if(!data.getString(8).equals("")) {
                listData.add(data.getString(8));
            }
            else {
                listData.add("Nincs tanterem hozzáadva!");
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
                View view=super.getView(position, convertView, parent);
                if(position%7==0){
                    view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                }
                else view.setBackgroundColor(getResources().getColor(android.R.color.white));

                return view;
            }

        };
        _ListView.setAdapter(adapter);

    }

}
