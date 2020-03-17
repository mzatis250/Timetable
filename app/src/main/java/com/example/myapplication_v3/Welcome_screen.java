package com.example.myapplication_v3;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Welcome_screen extends Activity {
    private LinearLayout linLayout;
    private ViewPager _viewPager;
    private int[] _layouts;
    private Button btnSkip, btnNext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        setContentView(R.layout.activity_welcome_screen);

        SharedPreferences shrd=getSharedPreferences("PREFS",MODE_PRIVATE);
        if(shrd.getInt("INTR", 0)==1){
            startActivity(new Intent(Welcome_screen.this, MainActivity.class));
            finish();
        }

        _viewPager = findViewById(R.id.view_pager);
        linLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);

        _layouts = new int[]{
                R.layout.slider0,
                R.layout.slider1,
                R.layout.slider2,
                R.layout.slider3,
                R.layout.slider4
        };


        addpoints(0);
        ViewPagerAdapter _viewPagerAdapter = new ViewPagerAdapter();
        _viewPager.setAdapter(_viewPagerAdapter);
        _viewPager.addOnPageChangeListener(viewPagerPageChangeListener);





    }

    public  void btnSkipClick(View v)
    {
        launchHomeScreen();
    }

    public  void btnNextClick(View v)
    {

        int current = getItem();
        if (current < _layouts.length) {

            _viewPager.setCurrentItem(current);
        } else {
            launchHomeScreen();
        }
    }


    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void onPageSelected(int position) {
            addpoints(position);


            if (position == _layouts.length - 1) {

                btnNext.setText("Kezdés");
                btnSkip.setVisibility(View.GONE);
            } else {

                btnNext.setText("Tovább");
                btnSkip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {
        }
    };


    private void addpoints(int currentPage) {
        TextView[] points = new TextView[_layouts.length];

        linLayout.removeAllViews();
        for (int i = 0; i < points.length; i++) {
            points[i] = new TextView(this);
            points[i].setText(Html.fromHtml("&#8226;"));
            points[i].setTextSize(35);
            points[i].setTextColor(getResources().getColor(R.color.dot_inactive));
            linLayout.addView(points[i]);
        }

        if (points.length > 0)
            points[currentPage].setTextColor(getResources().getColor(R.color.dot_active));
    }


    private int getItem() {
        return _viewPager.getCurrentItem() + 1;
    }

    private void launchHomeScreen() {
        SharedPreferences shrd=getSharedPreferences("PREFS",MODE_PRIVATE);
        SharedPreferences.Editor edtr=shrd.edit();
        edtr.putInt("INTR", 1);
        edtr.apply();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    public class ViewPagerAdapter extends PagerAdapter {
        ViewPagerAdapter() {

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(_layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return _layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, @NonNull Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

}
