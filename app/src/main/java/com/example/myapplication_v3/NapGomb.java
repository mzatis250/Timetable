package com.example.myapplication_v3;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;


public class NapGomb extends AppCompatActivity {
    private static final String TAG = "ListDataActivity";
    DatabaseHelper _DatabaseHelper;
    private ListView _ListView;
    Intent i;
    boolean getbool;
    private String[] oranev={"Ének", "Olvasás", "Rajz", "Testnevelés", "Technika", "Írás", "Matematika"};
    private int []images={R.drawable.a,R.drawable.b,R.drawable.c, R.drawable.d,R.drawable.e,R.drawable.f, R.drawable.g};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_valaszt_seged);
        _ListView =  findViewById(R.id.listView);
        _DatabaseHelper = new DatabaseHelper(this);


        populateListView();





    }


    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");
        Cursor data = _DatabaseHelper.getData2();
        ArrayList<String> listData = new ArrayList<>();
        ArrayList<String> listData2 = new ArrayList<>();
        i=getIntent();
        String a= i.getStringExtra("ertek");
        while(data.moveToNext()){
            if(a.contains(data.getString(2))){
                if(data.getString(5).contains("fal")){
                    listData.add(data.getString(1));
                    listData.add(data.getString(3));
                    listData.add(data.getString(4));
                    getbool=false;
                   // listData.add(data.getString(5));
                    if(!data.getString(7).equals("")) {
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
                else {
                    listData2.add(data.getString(1));
                    listData2.add(data.getString(3));
                    listData2.add(data.getString(4));
                    getbool=true;
                  //  listData2.add(data.getString(5));
                    if (!data.getString(7).equals("")) {
                        listData2.add(data.getString(7));
                    }
                    else {
                        listData2.add("Nincs tanár hozzáadva!");
                    }
                    if(!data.getString(8).equals("")) {
                        listData2.add(data.getString(8));
                    }
                    else {
                        listData2.add("Nincs tanterem hozzáadva!");
                    }
                }
        }
        }

        for(int i=0;i<listData.size();i++){
            for(int j=0;j<images.length;j++){
                if(String.valueOf(images[j]).equals(listData.get(i))){
                    listData.set(i, oranev[j]);
                }
            }
        }
        for(int i=0;i<listData2.size();i++){
            for(int j=0;j<images.length;j++){
                if(String.valueOf(images[j]).equals(listData2.get(i))){
                    listData2.set(i, oranev[j]);
                }
            }
        }

        if (a.contains("A")){
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
                    View view=super.getView(position, convertView, parent);
                    if(position%5==0){
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                    else view.setBackgroundColor(getResources().getColor(android.R.color.white));

                    return view;
                }

            };
            _ListView.setAdapter(adapter);
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listData2){
                @NonNull
                @Override
                public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
                    View view=super.getView(position, convertView, parent);
                    if(position%5==0){
                        view.setBackgroundColor(getResources().getColor(android.R.color.holo_red_dark));
                    }
                    else view.setBackgroundColor(getResources().getColor(android.R.color.white));
                    return view;
                }

            };
            _ListView.setAdapter(adapter);

        }


        _ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String name = adapterView.getItemAtPosition(i).toString();
               // Log.d(TAG, "onItemClick: You Clicked on " + name);
                Cursor data = _DatabaseHelper.getID(name);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                //    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent editScreenIntent = new Intent(NapGomb.this, Szerkeszt_seged2.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("switch", getbool);
                    //editScreenIntent.putExtra("name",name);
                    startActivity(editScreenIntent);
                }

            }
        });
    }


}
