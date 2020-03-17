package com.example.myapplication_v3;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class Oravalaszt extends AppCompatActivity {
    ArrayList<String> listData;
    ArrayList<String> listData2;
    private boolean flag, sw;

    private boolean flag3=false;
    private int i1;
    private String[] oranev={"Ének", "Olvasás", "Rajz", "Testnevelés", "Technika", "Írás", "Matematika"};
    int []images={R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d, R.drawable.e, R.drawable.f, R.drawable.g};
    SharedPreferences sharedPreferences;
    DatabaseHelper _DatabaseHelper;
    private Button button_add, button_view;
    private EditText editText, editText2, editText3, editText7, editText8;
    private Spinner s, spinner;

    private Switch s1;

    Fragment f1;
    FragmentManager fm=getSupportFragmentManager();
    Spinner_Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        listData = new ArrayList<>();
        listData2 = new ArrayList<>();
        setContentView(R.layout.activity_oravalaszt);
        sharedPreferences=getSharedPreferences("MyPrefs", MODE_PRIVATE);
        flag=sharedPreferences.getBoolean("flag",false);
        flag3=sharedPreferences.getBoolean("flag3",false);
        editText = findViewById(R.id.editText);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText7 = findViewById(R.id.editText11);
        editText8 = findViewById(R.id.editText12);
        button_add = findViewById(R.id.button5);
        button_view = findViewById(R.id.btnView);
        Button fbtn = findViewById(R.id.fbtn);
        Intent receivedIntent = getIntent();

        sw=receivedIntent.getBooleanExtra("switch", false);
        Button fbtn3 =  findViewById(R.id.fbtn3);
        s=findViewById(R.id.spinner1);
        spinner=findViewById(R.id.spinner);
        adapter=new Spinner_Adapter(this,oranev,images);
        s1=findViewById(R.id.switch1);

        editText2.setShowSoftInputOnFocus(false);
        editText3.setShowSoftInputOnFocus(false);
        editText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog _TimePicker;

                _TimePicker = new TimePickerDialog(Oravalaszt.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour<10&&selectedMinute>9) editText2.setText("0"+selectedHour+":"+selectedMinute);
                        else if(selectedMinute<10&&selectedHour>9) editText2.setText(selectedHour+":0"+selectedMinute);
                        else if(selectedMinute < 10) editText2.setText("0"+selectedHour+":0"+selectedMinute);
                        else editText2.setText( selectedHour + ":" + selectedMinute);

                    }
                }, hour, minute, true);

                _TimePicker.setTitle("Select Time");
                _TimePicker.show();


            }
        });
        editText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog _TimePicker;
                _TimePicker = new TimePickerDialog(Oravalaszt.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedHour<10&&selectedMinute>9) editText3.setText("0"+selectedHour+":"+selectedMinute);
                        else if(selectedMinute<10&&selectedHour>9) editText3.setText(selectedHour+":0"+selectedMinute);
                        else if(selectedMinute < 10) editText3.setText("0"+selectedHour+":0"+selectedMinute);
                        else editText3.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);
                _TimePicker.setTitle("Select Time");
                _TimePicker.show();


            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // Toast.makeText(getApplicationContext(), oranev[position], Toast.LENGTH_LONG).show();
                i1=images[position];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        f1= fm.findFragmentById(R.id.Fragment);
        if (flag){
            Iskola kisiskolas=new Kisiskolas();
            kisiskolas.Kivalaszt(editText,spinner,adapter);

            FragmentTransaction ft=fm.beginTransaction();
            ft.hide(f1);
            ft.commit();
            button_add.setEnabled(true);
            button_add.setVisibility(View.VISIBLE);
            button_view.setEnabled(true);
            button_view.setVisibility(View.VISIBLE);
        }
        if (flag3){
            Iskola normal=new Normal();
            normal.Kivalaszt(editText,spinner,adapter);
            FragmentTransaction ft=fm.beginTransaction();
            ft.hide(f1);
            ft.commit();
            editText7.setEnabled(true);
            editText8.setEnabled(true);
            button_add.setEnabled(true);
            button_add.setVisibility(View.VISIBLE);
            button_view.setEnabled(true);
            button_view.setVisibility(View.VISIBLE);
        }
        _DatabaseHelper = new DatabaseHelper(this);

        fbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.VISIBLE);
                flag=true;
                Iskola kisiskolas=new Kisiskolas();
                kisiskolas.Kivalaszt(editText,spinner,adapter);
                FragmentTransaction ft=fm.beginTransaction();
               ft.hide(f1);
               ft.commit();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("flag",flag);
                editor.apply();

               button_add.setEnabled(true);
               button_add.setVisibility(View.VISIBLE);
               button_view.setEnabled(true);
               button_view.setVisibility(View.VISIBLE);

            }
        });

        fbtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setVisibility(View.INVISIBLE);
                flag3=true;
                Iskola normal=new Normal();
                normal.Kivalaszt(editText,spinner,adapter);
                FragmentTransaction ft=fm.beginTransaction();
                ft.hide(f1);
                ft.commit();
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("flag3",flag3);
                editor.apply();
                button_add.setEnabled(true);
                button_add.setVisibility(View.VISIBLE);
                button_view.setEnabled(true);
                button_view.setVisibility(View.VISIBLE);
            }
        });

        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String newEntry = String.valueOf(i1);
                String newEntry2 = s.getSelectedItem().toString();
                String newEntry3 = editText2.getText().toString();
                String newEntry4 = editText3.getText().toString();
                String newEntry5=editText.getText().toString();
                String newEntry6;
                String newEntry8=editText7.getText().toString();
                String newEntry9=editText8.getText().toString();
                boolean b1=s1.isChecked();

                if(b1){
                    newEntry6="true";
                }
                else newEntry6="false";

                if (newEntry5.isEmpty()&& !newEntry2.isEmpty() && !newEntry3.isEmpty()&& !newEntry4.isEmpty()) {
                    String newEntry7=Negyzet(Konvertal(editText2.getText().toString()), Konvertal(editText3.getText().toString()));
                    AddData(newEntry, newEntry2, newEntry3, newEntry4, newEntry6, newEntry7, newEntry8, newEntry9);
                    Osszehasonlit(newEntry6);
                }
              else
                  if(!newEntry2.isEmpty() && !newEntry3.isEmpty()&&!newEntry4.isEmpty()){

                      String newEntry7=Negyzet(Konvertal(editText2.getText().toString()), Konvertal(editText3.getText().toString()));
                    AddData(newEntry5, newEntry2, newEntry3, newEntry4, newEntry6, newEntry7, newEntry8, newEntry9);
                      Osszehasonlit(newEntry6);
                }


            }
        });

        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oravalaszt.this, Valaszt_seged.class);

                startActivity(intent);

            }
        });

    }
    private boolean Osszehasonlit(String ne6){
        Cursor data = _DatabaseHelper.getData3(s.getSelectedItem().toString(), ne6);
        while(data.moveToNext()) {
            listData.add(data.getString(1));
            listData2.add(data.getString(2));
        }
        for (int i=0;i<listData.size();i++){
            if (Konvertal(listData.get(i))<=Konvertal(editText2.getText().toString())&&Konvertal(editText2.getText().toString())<=Konvertal(listData2.get(i))){
                return false;

            }
            else if(Konvertal(listData.get(i))<=Konvertal(editText3.getText().toString())&&Konvertal(editText3.getText().toString())<=Konvertal(listData2.get(i))){
                return false;
            }
            else if(Konvertal(listData.get(i))>=Konvertal(editText2.getText().toString())&&Konvertal(editText3.getText().toString())>=Konvertal(listData2.get(i))){
                return false;
            }
        }

       return true;
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
    public void AddData(String newEntry ,String newEntry2, String newEntry3, String newEntry4, String newEntry5, String newEntry6, String newEntry7, String newEntry8
                        ) {


    if (spinner.getVisibility()==View.VISIBLE||!editText.getText().toString().isEmpty()) {
        if (checktimings(newEntry3, newEntry4)) {
            if (checkothertimings(newEntry3)) {
                if (checkothertimings1(newEntry4)) {
                    if (Osszehasonlit(newEntry5)) {
                        boolean insertData = _DatabaseHelper.addData(newEntry, newEntry2, newEntry3, newEntry4, newEntry5, newEntry6, newEntry7, newEntry8);
                        if (insertData) {
                            toastMessage("Sikeres adatbevitel!");
                            Intent refresh = new Intent(this, MainActivity.class);
                            refresh.putExtra("switch", sw);
                            startActivity(refresh);
                            finish();
                        }
                    } else {

                        toastMessage("Óraütközés");
                        Intent intent = getIntent();
                        finish();
                        startActivity(intent);
                    }
                } else toastMessage("Az óra vége maximum 20:00 lehet!");
            } else toastMessage("Az óra kezdete minimum 8:00 lehet!");
        } else {
            toastMessage("Az óra kezdetének minimum 20 perccel hamarabb kell lennie, mint az óra végének");
        }
    } else  toastMessage("Minden *-gal jelölt mező kitöltése kötelező!");



    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    }

