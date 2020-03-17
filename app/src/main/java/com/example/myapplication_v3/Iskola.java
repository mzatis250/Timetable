package com.example.myapplication_v3;

import android.widget.EditText;
import android.widget.Spinner;

public abstract class Iskola {
    public void Kivalaszt(EditText editText, Spinner spinner, Spinner_Adapter adapter){
        Oranev(editText, spinner, adapter);

    }

    protected abstract void Oranev(EditText editText, Spinner spinner, Spinner_Adapter adapter);

}
