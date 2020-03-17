package com.example.myapplication_v3;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

public class Kisiskolas extends Iskola{
   @Override
    public  void Oranev(EditText editText, Spinner spinner, Spinner_Adapter adapter){
       spinner.setEnabled(true);
       spinner.setVisibility(View.VISIBLE);
       spinner.setAdapter(adapter);
  }
}
