package com.example.myapplication_v3;

import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;


public class Normal extends Iskola{
   @Override
    public void Oranev(EditText editText, Spinner spinner, Spinner_Adapter adapter){
       editText.setEnabled(true);
       editText.setVisibility(View.VISIBLE);
    }
}
