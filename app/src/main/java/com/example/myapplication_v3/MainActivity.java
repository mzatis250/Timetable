package com.example.myapplication_v3;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener {
    private DatabaseHelper _DatabaseHelper;
    private Button hetfo, kedd, szerda, csutortok, pentek, szombat, vasarnap;
    private Switch s2;
    boolean blink=false;
    ArrayList<ArrayList<Rect>>drawable=new ArrayList<>(7);
    ArrayList<ArrayList<RectF>>rectangles= new ArrayList<>(7);
    final ArrayList<ArrayList<String>>ahet=new ArrayList<>(7);
    final ArrayList<ArrayList<String>>bhet=new ArrayList<>(7);
    final ArrayList<ArrayList<Integer>>ahetid=new ArrayList<>(7);
    final ArrayList<ArrayList<Integer>>bhetid=new ArrayList<>(7);
    String dateString;

    ImageView imageView, imageView2x, imageView3x, imageView4x, imageView5x, imageView6x, imageView7x;



    Bitmap bitmap  = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_8888);
    Bitmap bitmap2 = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_8888);
    Bitmap bitmap3 = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_8888);
    Bitmap bitmap4 = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_8888);
    Bitmap bitmap5 = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_8888);
    Bitmap bitmap6 = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_8888);
    Bitmap bitmap7 = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_8888);
    Bitmap bitmap8 = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_4444);


    Canvas canvas  = new Canvas(bitmap );
    Canvas canvas2 = new Canvas(bitmap2);
    Canvas canvas3 = new Canvas(bitmap3);
    Canvas canvas4 = new Canvas(bitmap4);
    Canvas canvas5 = new Canvas(bitmap5);
    Canvas canvas6 = new Canvas(bitmap6);
    Canvas canvas7 = new Canvas(bitmap7);
    Canvas canvas8 = new Canvas(bitmap8);
    ArrayList<ImageView>imgvw;
    SharedPreferences preferences;
    int []images={R.drawable.a,R.drawable.b,R.drawable.c, R.drawable.d,R.drawable.e,R.drawable.f, R.drawable.g};
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for(int i=0;i<8;i++){
            drawable.add(new ArrayList<Rect>());
            rectangles.add(new ArrayList<RectF>());
            ahet.add(new ArrayList<String>());
            bhet.add(new ArrayList<String>());
            ahetid.add(new ArrayList<Integer>());
            bhetid.add(new ArrayList<Integer>());
        }
        setContentView(R.layout.activity_main);
        imageView=findViewById(R.id.image1);
        imageView2x=findViewById(R.id.image2);
        imageView3x=findViewById(R.id.image3);
        imageView4x=findViewById(R.id.image4);
        imageView5x=findViewById(R.id.image5);
        imageView6x=findViewById(R.id.image6);
        imageView7x=findViewById(R.id.image7);
        s2=findViewById(R.id.switch2);

        hetfo=findViewById(R.id.hetfo);
        kedd=findViewById(R.id.kedd);
        szerda=findViewById(R.id.szerda);
        csutortok=findViewById(R.id.csutortok);
        pentek=findViewById(R.id.pentek);
        szombat=findViewById(R.id.szombat);
        vasarnap=findViewById(R.id.vasarnap);
        Toolbar toolbar =  findViewById(R.id.toolbar);

        FloatingActionButton flbutton =  findViewById(R.id.flbutton);
        flbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent editScreenIntent = new Intent(MainActivity.this, Oravalaszt.class);
                if(s2.isChecked()){
                    editScreenIntent.putExtra("switch", true);
                }
                else {
                    editScreenIntent.putExtra("switch", false);
                }
                startActivity(editScreenIntent);
            }
        });

        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        imgvw=new ArrayList<>(7);
        imgvw.add(imageView);
        imgvw.add(imageView2x);
        imgvw.add(imageView3x);
        imgvw.add(imageView4x);
        imgvw.add(imageView5x);
        imgvw.add(imageView6x);
        imgvw.add(imageView7x);
        _DatabaseHelper = new DatabaseHelper(this);

        preferences = getPreferences(MODE_PRIVATE);
        boolean tgpref = preferences.getBoolean("tgpref", true);  //default is true
        if (tgpref) //if (tgpref) may be enough, not sure
        {
            s2.setChecked(true);
        }
        else
        {
            s2.setChecked(false);
        }
        Intent receivedI=getIntent();
        boolean escreen=receivedI.getBooleanExtra("switch", false);
        if (escreen) //if (tgpref) may be enough, not sure
        {
            s2.setChecked(true);
        }
        else
        {
            s2.setChecked(false);
        }


        final Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {

                        Thread.sleep(1000);
                        new Handler(Looper.getMainLooper()).post(new Runnable() {

                            @Override
                            public void run() {

                                long date = System.currentTimeMillis();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm");
                                dateString = sdformat.format(date);
                                Idovonal(dateString);
                                Update();
                                imageView.setImageBitmap(getCombinedBitmap(bitmap, bitmap8));
                                imageView2x.setImageBitmap(getCombinedBitmap(bitmap2, bitmap8));
                                imageView3x.setImageBitmap(getCombinedBitmap(bitmap3, bitmap8));
                                imageView4x.setImageBitmap(getCombinedBitmap(bitmap4, bitmap8));
                                imageView5x.setImageBitmap(getCombinedBitmap(bitmap5, bitmap8));
                                imageView6x.setImageBitmap(getCombinedBitmap(bitmap6, bitmap8));
                                imageView7x.setImageBitmap(getCombinedBitmap(bitmap7, bitmap8));
                                bitmap8.eraseColor(Color.TRANSPARENT);
                            }

                        });

                    }

                } catch (InterruptedException ignored) {

                }
            }

        };




        toggle.syncState();
        NavigationView _navigationView =  findViewById(R.id.nav_view);
        _navigationView.setNavigationItemSelectedListener(this);


        Kirajzol();
        t.start();
        Ciklus();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void Ciklus(){
        for( int j=0;j<7;j++) {
            final int i=j;
            final ImageView ggggg=findViewById(R.id.image1);
            imgvw.get(i).setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent event) {

                    float touchY = event.getY();
                    double vmis = (double) ggggg.getMeasuredHeight() / 960;
                    float tY = touchY / ((float) vmis);
                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
                        for (RectF rect : rectangles.get(i)) {
                            if (rect.contains(5, tY)) {
                                if (!s2.isChecked()) {
                                    for (String recti : ahet.get(i)) {
                                        if (recti.equals(rect.toString())) {
                                            int h = ahet.get(i).indexOf(recti);
                                            Intent editScreenIntent = new Intent(MainActivity.this, Szerkeszt_seged2.class);
                                            for (Integer xr : ahetid.get(i)) {
                                                int u = ahetid.get(i).indexOf(xr);
                                                if (h == u) {
                                                    editScreenIntent.putExtra("id", xr);
                                                    editScreenIntent.putExtra("switch", false);
                                                }
                                                startActivity(editScreenIntent);
                                            }
                                        }
                                    }
                                } else {
                                    for (String recti : bhet.get(i)) {
                                        if (recti.equals(rect.toString())) {
                                            int h = bhet.get(i).indexOf(recti);
                                            Intent editScreenIntent = new Intent(MainActivity.this, Szerkeszt_seged2.class);
                                            for (Integer xr : bhetid.get(i)) {
                                                int u = bhetid.get(i).indexOf(xr);
                                                if (h == u) {
                                                    editScreenIntent.putExtra("id", xr);
                                                    editScreenIntent.putExtra("switch", true);
                                                }
                                                startActivity(editScreenIntent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        for (Rect draw : drawable.get(i)) {
                            if (draw.contains(5, (int) tY)) {
                                if (!s2.isChecked()) {
                                    for (String recti : ahet.get(i)) {
                                        if (recti.equals(draw.toString())) {
                                            int h = ahet.get(i).indexOf(recti);
                                            Intent editScreenIntent = new Intent(MainActivity.this, Szerkeszt_seged2.class);
                                            for (Integer xr : ahetid.get(i)) {
                                                int u = ahetid.get(i).indexOf(xr);
                                                if (h == u) {
                                                    editScreenIntent.putExtra("id", xr);
                                                    editScreenIntent.putExtra("switch", false);
                                                }
                                                startActivity(editScreenIntent);
                                            }
                                        }
                                    }
                                } else {
                                    for (String recti : bhet.get(i)) {
                                        if (recti.equals(draw.toString())) {
                                            int h = bhet.get(i).indexOf(recti);
                                            Intent editScreenIntent = new Intent(MainActivity.this, Szerkeszt_seged2.class);
                                            for (Integer xr : bhetid.get(i)) {
                                                int u = bhetid.get(i).indexOf(xr);
                                                if (h == u) {
                                                    editScreenIntent.putExtra("id", xr);
                                                    editScreenIntent.putExtra("switch", true);
                                                }
                                                startActivity(editScreenIntent);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    return true;
                }
            });
        }
    }
    private void NegyzetKinyer(Cursor data){


            if (!data.getString(5).equals("true")) {
                switch (data.getString(2)) {
                    case "Hétfő":
                        ahet.get(0).add(data.getString(6));
                        ahetid.get(0).add(data.getInt(0));
                        break;
                    case "Kedd":
                        ahet.get(1).add(data.getString(6));
                        ahetid.get(1).add(data.getInt(0));
                        break;
                    case "Szerda":
                        ahet.get(2).add(data.getString(6));
                        ahetid.get(2).add(data.getInt(0));
                        break;
                    case "Csütörtök":
                        ahet.get(3).add(data.getString(6));
                        ahetid.get(3).add(data.getInt(0));
                        break;
                    case "Péntek":
                        ahet.get(4).add(data.getString(6));
                        ahetid.get(4).add(data.getInt(0));
                        break;
                    case "Szombat":
                        ahet.get(5).add(data.getString(6));
                        ahetid.get(5).add(data.getInt(0));
                        break;
                    case "Vasárnap":
                        ahet.get(6).add(data.getString(6));
                        ahetid.get(6).add(data.getInt(0));
                        break;
                }
            }

            else {
                switch (data.getString(2)) {
                    case "Hétfő":
                        bhet.get(0).add(data.getString(6));
                        bhetid.get(0).add(data.getInt(0));
                        break;
                    case "Kedd":
                        bhet.get(1).add(data.getString(6));
                        bhetid.get(1).add(data.getInt(0));
                        break;
                    case "Szerda":
                        bhet.get(2).add(data.getString(6));
                        bhetid.get(2).add(data.getInt(0));
                        break;
                    case "Csütörtök":
                        bhet.get(3).add(data.getString(6));
                        bhetid.get(3).add(data.getInt(0));
                        break;
                    case "Péntek":
                        bhet.get(4).add(data.getString(6));
                        bhetid.get(4).add(data.getInt(0));
                        break;
                    case "Szombat":
                        bhet.get(5).add(data.getString(6));
                        bhetid.get(5).add(data.getInt(0));
                        break;
                    case "Vasárnap":
                        bhet.get(6).add(data.getString(6));
                        bhetid.get(6).add(data.getInt(0));
                        break;
                }

            }
}

    @Override
    public void onBackPressed() {
       finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id=item.getItemId();
        Intent intent = new Intent(MainActivity.this, Oravalaszt.class);
        if(id==R.id.beallit){

            startActivity(intent);
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            sharedPreferences.edit().clear().apply();
        }
        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private void Orabeir(int a, int b, String c , Canvas canvas, ImageView imageView, Bitmap bitmap, ArrayList<RectF>rectangles, ArrayList<Rect>drawables) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);


        paint.setColor(Color.BLUE);

        if (tryParseInt(c)){
           int d=Integer.parseInt(c);
            for (int image : images) {
                if (d == image) {
                    Drawable drawable = getResources().getDrawable(d, null);
                    drawable.setBounds(1, a, 129, b);
                    drawable.draw(canvas);

                    imageView.setImageBitmap(bitmap);
                    drawables.add(drawable.getBounds());
                }
            }
        }

        else{
            paint.setTextSize(23);

            RectF rect=new RectF(1, a, 129, b);

            canvas.drawRoundRect(rect, 10, 10, paint);

            rectangles.add(rect);
            paint.setColor(Color.WHITE);

            if (c.length()>10&&c.length()<21){
                canvas.drawText(c,0, 9, 5, a + 20, paint);
                canvas.drawText(c,9,c.length(), 5, a + 40, paint);

            }
            else if((b-a)<40){
                paint.setTextSize(18);
                paint.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                canvas.drawText(c,5,a+15, paint);
            }

            else if ((c.length()>20&&c.length()<31)||(b-a)<60) {
                paint.setTextSize(23);
                if (c.length()<12){

                    canvas.drawText(c,5,a+20, paint);
                }else {
                    paint.setTextSize(19);
                    canvas.drawText(c, 0, 12, 5, a + 15, paint);
                    canvas.drawText(c, 12, c.length(), 5, a + 40, paint);
                }
            }
            else if ((c.length()>30&&c.length()<41)||(b-a)<80) {
                paint.setTextSize(23);
                if (c.length()<12){

                    canvas.drawText(c,5,a+20, paint);}
                else {
                    if (c.length() >= 12) {
                        c.length();
                    }
                    paint.setTextSize(19);
                    canvas.drawText(c, 0, 12, 5, a + 15, paint);
                    canvas.drawText(c, 12, 25, 5, a + 35, paint);
                    canvas.drawText(c, 25, c.length(), 5, a + 55, paint);
                }
            }
            else if (c.length()>40) {
                paint.setTextSize(19);
                canvas.drawText(c, 0, 12, 5, a + 15, paint);
                canvas.drawText(c, 12, 25, 5, a + 35, paint);
                canvas.drawText(c, 25, 38, 5, a + 55, paint);
                canvas.drawText(c, 38, c.length(), 5, a + 75, paint);
            }
            else {
                canvas.drawText(c, 5, a + 20, paint);
            }
            imageView.setImageBitmap(bitmap);
        }
    }

    private void Napvalaszt(final String i){
        hetfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NapGomb.class);
                intent.putExtra("ertek","Hétfő"+i);
                startActivity(intent);
            }
        });
        kedd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NapGomb.class);
                intent.putExtra("ertek","Kedd"+i);
                startActivity(intent);
            }
        });
        szerda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NapGomb.class);
                intent.putExtra("ertek","Szerda"+i);
                startActivity(intent);
            }
        });
        csutortok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NapGomb.class);
                intent.putExtra("ertek","Csütörtök"+i);
                startActivity(intent);
            }
        });
        pentek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NapGomb.class);
                intent.putExtra("ertek","Péntek"+i);
                startActivity(intent);
            }
        });
        szombat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NapGomb.class);
                intent.putExtra("ertek","Szombat"+i);
                startActivity(intent);
            }
        });
        vasarnap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NapGomb.class);
                intent.putExtra("ertek","Vasárnap"+i);
                startActivity(intent);
            }
        });
    }
    private void Kirajzol() {
        Cursor data = _DatabaseHelper.getData();

        final ArrayList<String> listData = new ArrayList<>();
        final ArrayList<String> listData2 = new ArrayList<>();
        final ArrayList<String> listData3 = new ArrayList<>();
        final ArrayList<String> listData4 = new ArrayList<>();
        final ArrayList<String> listData5 = new ArrayList<>();
        final ArrayList<String> listData6 = new ArrayList<>();
        final ArrayList<String> listData7 = new ArrayList<>();
        final ArrayList<String> listData8 = new ArrayList<>();
        final String i="A";
        final String j="B";

        while (data.moveToNext()) {
            NegyzetKinyer(data);

            if (!data.getString(5).equals("true")) {
                listData4.add(data.getString(1));
                listData.add(data.getString(2));
                listData2.add(data.getString(3));
                listData3.add(data.getString(4));

            }
           else{

               listData8.add(data.getString(1));
                listData5.add(data.getString(2));
                listData6.add(data.getString(3));
                listData7.add(data.getString(4));

            }

            if(s2.isChecked()){
                bitmap.eraseColor(Color.WHITE);
                bitmap2.eraseColor(Color.WHITE);
                bitmap3.eraseColor(Color.WHITE);
                bitmap4.eraseColor(Color.WHITE);
                bitmap5.eraseColor(Color.WHITE);
                bitmap6.eraseColor(Color.WHITE);
                bitmap7.eraseColor(Color.WHITE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("tgpref", false); // value to store
                editor.apply();
                Konvertal(listData5, listData6, listData7, listData8);
                Napvalaszt(j);
            }
            else{
                bitmap.eraseColor(Color.WHITE);
                bitmap2.eraseColor(Color.WHITE);
                bitmap3.eraseColor(Color.WHITE);
                bitmap4.eraseColor(Color.WHITE);
                bitmap5.eraseColor(Color.WHITE);
                bitmap6.eraseColor(Color.WHITE);
                bitmap7.eraseColor(Color.WHITE);
                Konvertal(listData, listData2, listData3, listData4);
                Napvalaszt(i);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putBoolean("tgpref", true); // value to store
                editor.apply();
            }
            s2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    if (!isChecked) {
                       bitmap.eraseColor(Color.WHITE);
                        bitmap2.eraseColor(Color.WHITE);
                        bitmap3.eraseColor(Color.WHITE);
                        bitmap4.eraseColor(Color.WHITE);
                        bitmap5.eraseColor(Color.WHITE);
                        bitmap6.eraseColor(Color.WHITE);
                        bitmap7.eraseColor(Color.WHITE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("tgpref", true); // value to store
                        editor.apply();

                    Konvertal(listData, listData2, listData3, listData4);
                    Napvalaszt(i);

                    }
                    else {
                        bitmap.eraseColor(Color.WHITE);
                        bitmap2.eraseColor(Color.WHITE);
                        bitmap3.eraseColor(Color.WHITE);
                        bitmap4.eraseColor(Color.WHITE);
                        bitmap5.eraseColor(Color.WHITE);
                        bitmap6.eraseColor(Color.WHITE);
                        bitmap7.eraseColor(Color.WHITE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("tgpref", false); // value to store
                        editor.apply();
                    Konvertal(listData5, listData6, listData7, listData8);
                    Napvalaszt(j);

                    }
                }

            });
        }






    }

    public Bitmap getCombinedBitmap(Bitmap b, Bitmap b2) {
        Bitmap drawnBitmap = null;

        try {
            drawnBitmap = Bitmap.createBitmap(130, 960, Bitmap.Config.ARGB_8888);

            Canvas canvas = new Canvas(drawnBitmap);

            canvas.drawBitmap(b, 0, 0, null);
            canvas.drawBitmap(b2, 0, 0, null);


        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return drawnBitmap;
    }
    private int Konvertal2(String Ora){
        String []unitsa=Ora.split(":");
        int hoursa=Integer.parseInt(unitsa[0]);
        int minutesa=Integer.parseInt(unitsa[1]);
        return 60*hoursa+minutesa-480;
    }
    private void Idovonal(String Ora){
        Paint p1=new Paint();
        Paint p2=new Paint();





        p2.setColor(Color.TRANSPARENT);
        p1.setColor(Color.RED);


        if(blink) {
            canvas8.drawLine(1,Konvertal2(Ora),129, Konvertal2(Ora),p1);

        }
        else {
            canvas8.drawLine(1,Konvertal2(Ora),129, Konvertal2(Ora),p2);

        }



    }
    private void Update(){
        blink = !blink;
    }
    private void Konvertal(ArrayList<String> listData, ArrayList<String> listData2, ArrayList<String> listData3,ArrayList<String> listData4) {
        ArrayList<Integer> ConvertedStart = new ArrayList<>();
        ArrayList<Integer> ConvertedEnd = new ArrayList<>();
        ArrayList<String> Name = new ArrayList<>();
        ArrayList<String> Ora=new ArrayList<>();
        for (int i = 0; i < listData.size(); i++) {
            String a = listData2.get(i);
            String b = listData3.get(i);
            String[] unitsa = a.split(":");
            String[] unitsb = b.split(":");
            int hoursa = Integer.parseInt(unitsa[0]);
            int minutesa = Integer.parseInt(unitsa[1]);
            int hoursb = Integer.parseInt(unitsb[0]);
            int minutesb = Integer.parseInt(unitsb[1]);
            int durationa = 60 * hoursa + minutesa - 480;
            int durationb = 60 * hoursb + minutesb - 480;
            ConvertedStart.add(durationa);
            ConvertedEnd.add(durationb);
            Name.add(listData.get(i));
            Ora.add(listData4.get(i));
        }
        Atad(ConvertedStart,ConvertedEnd,Name, Ora);
    }


    private void Atad(ArrayList<Integer> ConvertedStart, ArrayList<Integer> ConvertedEnd, ArrayList<String> Name,ArrayList<String>Ora) {
        imageView.setImageResource(0);
        imageView2x.setImageResource(0);
        imageView3x.setImageResource(0);
        imageView4x.setImageResource(0);
        imageView5x.setImageResource(0);
        imageView6x.setImageResource(0);
        imageView7x.setImageResource(0);


        for (int i = 0; i < ConvertedStart.size(); i++) {
            switch (Name.get(i)) {
                case "Hétfő":
                    Orabeir(ConvertedStart.get(i), ConvertedEnd.get(i), Ora.get(i), canvas, imageView,bitmap, rectangles.get(0), drawable.get(0));
                    continue;
                case "Kedd":
                    Orabeir(ConvertedStart.get(i), ConvertedEnd.get(i), Ora.get(i), canvas2, imageView2x, bitmap2, rectangles.get(1), drawable.get(1));
                    continue;
                case "Szerda":
                    Orabeir(ConvertedStart.get(i), ConvertedEnd.get(i), Ora.get(i), canvas3, imageView3x, bitmap3, rectangles.get(2), drawable.get(2));
                    continue;
                case "Csütörtök":
                    Orabeir(ConvertedStart.get(i), ConvertedEnd.get(i), Ora.get(i), canvas4, imageView4x,  bitmap4, rectangles.get(3), drawable.get(3));
                    continue;
                case "Péntek":
                    Orabeir(ConvertedStart.get(i), ConvertedEnd.get(i), Ora.get(i), canvas5, imageView5x,bitmap5, rectangles.get(4), drawable.get(4));
                    continue;
                case "Szombat":
                    Orabeir(ConvertedStart.get(i), ConvertedEnd.get(i), Ora.get(i), canvas6, imageView6x, bitmap6, rectangles.get(5), drawable.get(5));
                    continue;
                case "Vasárnap":
                    Orabeir(ConvertedStart.get(i), ConvertedEnd.get(i), Ora.get(i), canvas7, imageView7x, bitmap7, rectangles.get(6), drawable.get(6));
            }
        }

    }

    }
