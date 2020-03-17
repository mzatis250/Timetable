package com.example.myapplication_v3;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;

public class Szerkeszt_seged2 extends AppCompatActivity {
    private EditText editable_item, edit_startTime, edit_endTime, edit_tanar, edit_tanterem;

    DatabaseHelper _DatabaseHelper;
    ArrayList<String> listData;
    ArrayList<String> listData2;
    private int selectedID, i1;
    boolean sw;
    private String selectedName, startTime, endTime, nap, tanar, tanterem, ABhet, negyzet;
    private Switch switch1;
    private Spinner spinner, spinner1;
    private String[] oranev={"Ének", "Olvasás", "Rajz", "Testnevelés", "Technika", "Írás", "Matematika"};
    int []images={R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g};
    Spinner_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_szerkeszt_seged2);
        listData = new ArrayList<>();
        listData2 = new ArrayList<>();
        edit_tanar=findViewById(R.id.editText9);
        edit_tanterem=findViewById(R.id.editText10);
        spinner=findViewById(R.id.spinner2);
        spinner1=findViewById(R.id.spinner3);
        switch1=findViewById(R.id.switch3);
        Button btnSave = findViewById(R.id.button);
        Button btnDelete = findViewById(R.id.button2);
        editable_item = findViewById(R.id.editText4);
        edit_startTime=findViewById(R.id.editText5);
        edit_endTime=findViewById(R.id.editText6);
        _DatabaseHelper = new DatabaseHelper(this);
        adapter=new Spinner_Adapter(this,oranev,images);
        spinner.setAdapter(adapter);
        Cursor data=_DatabaseHelper.getData();
        Intent receivedIntent = getIntent();
        selectedID = receivedIntent.getIntExtra("id",-1);
        sw=receivedIntent.getBooleanExtra("switch", false);
        if(sw){
            switch1.setChecked(true);

        }
        else switch1.setChecked(false);
        edit_startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String []unitsa=edit_startTime.getText().toString().split(":");
                int hour=Integer.parseInt(unitsa[0]);
                int minute=Integer.parseInt(unitsa[1]);
                TimePickerDialog mTimePicker;

                mTimePicker = new TimePickerDialog(Szerkeszt_seged2.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour<10&&selectedMinute>9) edit_startTime.setText("0"+selectedHour+":"+selectedMinute);
                        else if(selectedMinute<10&&selectedHour>9) edit_startTime.setText(selectedHour+":0"+selectedMinute);
                        else if(selectedMinute < 10) edit_startTime.setText("0"+selectedHour+":0"+selectedMinute);
                        else edit_startTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);

                mTimePicker.setTitle("Select Time");
                mTimePicker.show();


            }
        });
        edit_endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String []unitsa=edit_endTime.getText().toString().split(":");
                int hour=Integer.parseInt(unitsa[0]);
                int minute=Integer.parseInt(unitsa[1]);
                TimePickerDialog _TimePicker;
                _TimePicker = new TimePickerDialog(Szerkeszt_seged2.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour<10&&selectedMinute>9) edit_endTime.setText("0"+selectedHour+":"+selectedMinute);
                        else if(selectedMinute<10&&selectedHour>9) edit_endTime.setText(selectedHour+":0"+selectedMinute);
                        else if(selectedMinute < 10) edit_endTime.setText("0"+selectedHour+":0"+selectedMinute);
                        else edit_endTime.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                _TimePicker.setTitle("Select Time");
                _TimePicker.show();


            }
        });
        while (data.moveToNext()) {
            if(data.getInt(0)==selectedID){
                selectedName=data.getString(1);
                startTime=data.getString(3);
                endTime=data.getString(4);
                ABhet=data.getString(5);
                nap=data.getString(2);
                tanar=data.getString(7);
                tanterem=data.getString(8);
                negyzet=data.getString(6);
            }
        }
        editable_item.setText(selectedName);
        edit_startTime.setText(startTime);
        edit_endTime.setText(endTime);
        edit_tanar.setText(tanar);
        edit_tanterem.setText(tanterem);
        for(int i=0;i<images.length;i++){
            try{
                int j=Integer.parseInt(selectedName);
                    if(j==(images[i])){
                        Iskola kisiskolas=new Kisiskolas();
                        kisiskolas.Kivalaszt(editable_item,spinner,adapter);
                        spinner.setSelection(i);
                        break;
                    }

            }
            catch (NumberFormatException nfe){
                Iskola normal=new Normal();
                normal.Kivalaszt(editable_item,spinner,adapter);
            }
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.Nap, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        int spinnerPosition = adapter.getPosition(nap);
        spinner1.setSelection(spinnerPosition);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String item = editable_item.getText().toString();
                String item2 = edit_startTime.getText().toString();
                String item3 = edit_endTime.getText().toString();
                String item4 = edit_tanar.getText().toString();
                String item5 = edit_tanterem.getText().toString();
                String item6;
                String item7 = spinner1.getSelectedItem().toString();
                String item8 = Negyzet(Konvertal(item2), Konvertal(item3));
                if (switch1.isChecked()) {
                    item6 = "true";
                } else item6 = "false";
                if ((spinner.getVisibility()==View.VISIBLE||!item.isEmpty())) {
                    if (checktimings(item2, item3)) {
                        if (checkothertimings(item2)) {
                            if (checkothertimings1(item3)) {
                                if (Osszehasonlit(item6)) {
                                    _DatabaseHelper.updateData(item, selectedID, selectedName, item7, nap, item2, startTime, item3, endTime, item6, ABhet, item8, negyzet, item4, tanar, item5, tanterem);
                                    AddData();
                                } else {

                                    toastMessage("Óraütközés");
                                    Intent intent = getIntent();
                                    finish();
                                    startActivity(intent);
                                }
                            } else toastMessage("Az óra vége maximum 20:00 lehet!");
                        } else toastMessage("Az óra kezdete minimum 8:00 lehet!");
                    } else
                        toastMessage("Az óra kezdetének minimum 20 perccel hamarabb kell lennie, mint az óra végének!");
                }else toastMessage("Minden *-gal jelölt mező kitöltése kötelező!");
            }

        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //  Toast.makeText(getApplicationContext(), oranev[position], Toast.LENGTH_LONG).show();
                i1=images[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _DatabaseHelper.deleteName(selectedID,selectedName);
                editable_item.setText("");
                toastMessage("Törölve");
                RemoveData();
            }
        });
    }
    private boolean Osszehasonlit(String ne6){
        Cursor data = _DatabaseHelper.getData3(spinner1.getSelectedItem().toString(), ne6);
        while(data.moveToNext()) {

            if(data.getInt(0)!=selectedID) {
                listData.add(data.getString(1));
                listData2.add(data.getString(2));
            }

        }
        for (int i=0;i<listData.size();i++){
            if (Konvertal(listData.get(i))<=Konvertal(edit_startTime.getText().toString())&&Konvertal(edit_startTime.getText().toString())<=Konvertal(listData2.get(i))){
                return false;
            }
            else if(Konvertal(listData.get(i))<=Konvertal(edit_endTime.getText().toString())&&Konvertal(edit_endTime.getText().toString())<=Konvertal(listData2.get(i))){
                return false;
            }
          

            else if(Konvertal(listData.get(i))>=Konvertal(edit_startTime.getText().toString())&&Konvertal(edit_endTime.getText().toString())>=Konvertal(listData2.get(i))){
                return false;
            }
        }
        return true;
    }
    private boolean checktimings(String time, String endtime) {
        String []unitsa=time.split(":");
        String []unitsb=endtime.split(":");
        int hoursa=Integer.parseInt(unitsa[0]);
        int minutesa=Integer.parseInt(unitsa[1]);
        int durationa=60*hoursa+minutesa;
        int hoursb=Integer.parseInt(unitsb[0]);
        int minutesb=Integer.parseInt(unitsb[1]);
        int durationb=60*hoursb+minutesb;
        return durationa + 20 <= durationb;

    }

    private boolean checkothertimings(String time) {
        String []unitsa=time.split(":");

        int hoursa=Integer.parseInt(unitsa[0]);
        int minutesa=Integer.parseInt(unitsa[1]);
        int durationa=60*hoursa+minutesa;
        return durationa >= 480;

    }
    private boolean checkothertimings1(String time) {
        String []unitsa=time.split(":");

        int hoursa=Integer.parseInt(unitsa[0]);
        int minutesa=Integer.parseInt(unitsa[1]);
        int durationa=60*hoursa+minutesa;
        return durationa <= 1200;

    }
    private int Konvertal(String a){

        String[] unitsa = a.split(":");

        int hoursa = Integer.parseInt(unitsa[0]);
        int minutesa = Integer.parseInt(unitsa[1]);
        return 60 * hoursa + minutesa - 480;
    }
    private String Negyzet(int durationa, int durationb){

        String zzz;

        if (spinner.getVisibility()==View.VISIBLE) {
            Rect x = new Rect(1, durationa, 129, durationb);
            zzz=x.toString();
            return zzz;
        }
        else{
            RectF x = new RectF(1, durationa, 129, durationb);
            zzz=x.toString();
            return zzz;
        }

    }

    public void RemoveData(){
        Intent refresh = new Intent(this, MainActivity.class);
        refresh.putExtra("switch", sw);
        startActivity(refresh);
        finish();
    }
    public void AddData(){
        Intent refresh = new Intent(this, MainActivity.class);
        refresh.putExtra("switch", sw);
        startActivity(refresh);
        finish();
    }
    public void onBackPressed(){

        Intent refresh = new Intent(this, MainActivity.class);
        refresh.putExtra("switch", sw);
        startActivity(refresh);
        finish();
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

}
